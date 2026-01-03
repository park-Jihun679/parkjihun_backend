package org.wireBarley.common.exception;

public class InvalidRequestException extends BaseRuntimeException {

  public InvalidRequestException() {
    super("Invalid Request Error");
  }


  public InvalidRequestException(ErrorCode code) {
    super(code);
  }


  public InvalidRequestException(ErrorCode code, Throwable cause) {
    super(code, null, cause);
  }


  public InvalidRequestException(ErrorCode code, Object... args) {
    super(code, args, null);
  }


  public InvalidRequestException(ErrorCode code, Object[] args, Throwable cause) {
    super(code, args, cause);
  }
}
