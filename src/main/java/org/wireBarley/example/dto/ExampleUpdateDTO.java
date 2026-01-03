package org.wireBarley.example.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ExampleUpdateDTO {

  private UUID exampleId;

  private String exampleString;

}
