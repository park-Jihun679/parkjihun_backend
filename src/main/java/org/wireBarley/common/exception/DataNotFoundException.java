package org.wireBarley.common.exception;

public class DataNotFoundException extends BaseRuntimeException {

  public DataNotFoundException(ErrorCode code) {
    super(code);
  }

}
