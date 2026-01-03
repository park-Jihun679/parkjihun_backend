package org.wireBarley.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public abstract class BaseDTO implements Serializable {

    @JsonIgnore
    private UUID createdBy;
    private LocalDateTime createdDate;
    @JsonIgnore
    private UUID updatedBy;

    private LocalDateTime updatedDate;
}
