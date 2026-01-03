package org.wireBarley.common.exception;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

@Getter
public class BindingErrorException extends BaseRuntimeException {

  List<FieldErrorDetail> fieldErrors = null;


  public BindingErrorException() {
    super("Binding Error");
  }

  public BindingErrorException(BindingResult bindingResult) {
    super(ErrorCode.COMMON_BINDING_ERROR);
    fieldErrors = bindingResult.getFieldErrors().stream()
        .map(err -> new FieldErrorDetail(err.getField(), err.getDefaultMessage(),
            err.getRejectedValue() != null ? err.getRejectedValue().toString() : ""))
        .collect(Collectors.toList());
  }

  public BindingErrorException(ErrorCode code) {
    super(code);
  }


  public BindingErrorException(ErrorCode code, Throwable cause) {
    super(code, null, cause);
  }


  public BindingErrorException(ErrorCode code, Object... args) {
    super(code, args, null);
  }

  public BindingErrorException(ErrorCode code, Object[] args, Throwable cause) {
    super(code, args, cause);
  }


  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class FieldErrorDetail {

    private String field;
    private String message;
    private String rejectedValue;
  }
}
