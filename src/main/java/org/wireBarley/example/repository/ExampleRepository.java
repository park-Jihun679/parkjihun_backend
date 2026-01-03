package org.wireBarley.example.repository;

import java.util.Optional;
import java.util.UUID;
import org.wireBarley.example.entity.ExampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExampleRepository extends JpaRepository<ExampleEntity, UUID>,
    ExampleRepositoryCustom {

  Optional<ExampleEntity> findByExampleCode(String exampleCode);
}
