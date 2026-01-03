package org.wireBarley.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ExampleCreateDTO {

  private String exampleCode;

  private String exampleString;

}
