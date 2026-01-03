package org.wireBarley.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.wireBarley.common.entity.BaseEntity;
import org.wireBarley.example.dto.ExampleUpdateDTO;

@Entity
@Data
@Table(name = "example")
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ExampleEntity extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID exampleId;

    private String exampleCode;
    private String exampleString;

    public void update(ExampleUpdateDTO updateDTO) {
        this.exampleString = updateDTO.getExampleString();
    }

}
