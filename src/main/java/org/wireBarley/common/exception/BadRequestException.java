package org.wireBarley.common.exception;

public class BadRequestException extends BaseRuntimeException {

  public BadRequestException(String message) {
    super(message);
  }

  public BadRequestException(String message, Throwable cause) {
    super(message, cause);
  }

  public BadRequestException(ErrorCode code) {
    super(code);
  }

  public BadRequestException(ErrorCode code, Object[] args, Throwable cause) {
    super(code, args, cause);
  }
}
