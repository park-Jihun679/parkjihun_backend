package org.wireBarley.example.repository;

import org.wireBarley.example.dto.ExampleFilterDTO;
import org.wireBarley.example.entity.ExampleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExampleRepositoryCustom {

  Page<ExampleEntity> findByFilter(ExampleFilterDTO filterDTO, Pageable pageable);


}
