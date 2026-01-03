package org.wireBarley.example.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.wireBarley.common.dto.BaseDTO;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExampleDTO extends BaseDTO {

  @JsonIgnore
  private UUID exampleId;

  private String exampleCode;

  private String exampleString;

}
