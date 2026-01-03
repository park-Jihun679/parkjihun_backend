package org.wireBarley.common.exception;


import java.util.Arrays;
import lombok.Getter;
import org.springframework.util.ObjectUtils;

@Getter
public class BaseRuntimeException extends RuntimeException {

  private ErrorCode errorCode;


    public BaseRuntimeException(String message) {
    super(message);
  }

  public BaseRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public BaseRuntimeException(ErrorCode code) {
    super("");
    errorCode = code;
  }


  public BaseRuntimeException(ErrorCode code, Object[] args, Throwable cause) {
    super("", cause);
    errorCode = code;
      String[] errorArgs =
          ObjectUtils.isEmpty(args) ? new String[0] : Arrays.stream(args).map(Object::toString).
              toArray(String[]::new);
  }


}
