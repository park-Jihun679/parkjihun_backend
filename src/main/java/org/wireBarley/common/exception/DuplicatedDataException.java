package org.wireBarley.common.exception;

public class DuplicatedDataException extends BaseRuntimeException {


  public DuplicatedDataException(String message) {
    super(message);
  }

  public DuplicatedDataException(String message, Throwable cause) {
    super(message, cause);
  }

  public DuplicatedDataException(ErrorCode code) {
    super(code);
  }

  public DuplicatedDataException(ErrorCode code, Object[] args, Throwable cause) {
    super(code, args, cause);
  }

  public DuplicatedDataException(ErrorCode code, Object... args) {
    super(code, args, null);
  }
}
