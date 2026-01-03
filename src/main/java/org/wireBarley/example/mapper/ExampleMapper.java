package org.wireBarley.example.mapper;

import lombok.RequiredArgsConstructor;
import org.wireBarley.example.dto.ExampleCreateDTO;
import org.wireBarley.example.dto.ExampleDTO;
import org.wireBarley.example.entity.ExampleEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExampleMapper {

  public ExampleEntity toEntity(ExampleDTO dto){
    return ExampleEntity.builder()
        .exampleId(dto.getExampleId())
        .exampleString(dto.getExampleString())
        .exampleCode(dto.getExampleCode())
        .build();
  }

  public ExampleDTO toDTO(ExampleEntity entity){
    return ExampleDTO.builder()
        .exampleId(entity.getExampleId())
        .exampleString(entity.getExampleString())
        .exampleCode(entity.getExampleCode())
        .createdBy(entity.getCreatedBy())
        .createdDate(entity.getCreatedDate())
        .updatedBy(entity.getUpdatedBy())
        .updatedDate(entity.getUpdatedDate())
        .build();
  }

  public ExampleEntity toEntity(ExampleCreateDTO dto){
    return ExampleEntity.builder()
        .exampleCode(dto.getExampleCode())
        .exampleString(dto.getExampleString())
        .build();
  }

}
