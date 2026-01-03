package org.wireBarley.example.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.wireBarley.common.exception.ErrorCode;
import org.wireBarley.common.exception.InvalidRequestException;
import org.wireBarley.example.dto.ExampleCreateDTO;
import org.wireBarley.example.dto.ExampleDTO;
import org.wireBarley.example.dto.ExampleFilterDTO;
import org.wireBarley.example.dto.ExampleUpdateDTO;
import org.wireBarley.example.entity.ExampleEntity;
import org.wireBarley.example.mapper.ExampleMapper;
import org.wireBarley.example.repository.ExampleRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExampleService {

  private final ExampleMapper exampleMapper;

  private final ExampleRepository exampleRepository;

  @Transactional(readOnly = true)
  public ExampleDTO getExample(UUID exampleId) {

    ExampleEntity exampleEntity = exampleRepository.findById(exampleId).orElseThrow(
        () -> new InvalidRequestException(ErrorCode.COMMON_DATA_NOT_FOUND)
    );

    return exampleMapper.toDTO(exampleEntity);
  }

  @Transactional(readOnly = true)
  public Page<ExampleDTO> getExampleList(ExampleFilterDTO exampleFilter) {

    Page<ExampleEntity> exampleEntities = exampleRepository.findByFilter(exampleFilter,
        exampleFilter.getPageRequest());

    return exampleEntities.map(exampleMapper::toDTO);
  }

  public ExampleDTO createExample(ExampleCreateDTO createDTO) {

    //exampleCode가 중복인지 확인 필요
    if (exampleRepository.findByExampleCode(createDTO.getExampleCode())
        .isPresent()) {
      throw new InvalidRequestException(ErrorCode.DUPLICATED_REQUEST);
    }

    ExampleEntity createdEntity = exampleRepository.save(exampleMapper.toEntity(createDTO));

    return exampleMapper.toDTO(createdEntity);
  }

  public ExampleDTO updateExample(ExampleUpdateDTO updateDTO) {

    //기존 데이터 조회
    ExampleEntity existedExample = this.findExistedExample(updateDTO.getExampleId());

    existedExample.update(updateDTO);

    ExampleEntity updatedEntity = exampleRepository.save(existedExample);

    return exampleMapper.toDTO(updatedEntity);
  }

  public void deleteExample(UUID exampleId) {

    //기존 데이터 조회
    ExampleEntity existedExample = this.findExistedExample(exampleId);

    exampleRepository.delete(existedExample);
  }

  private ExampleEntity findExistedExample(UUID exampleId) {

      return exampleRepository.findById(exampleId)
        .orElseThrow(
            () -> new InvalidRequestException(ErrorCode.COMMON_DATA_NOT_FOUND)
        );
  }
}
