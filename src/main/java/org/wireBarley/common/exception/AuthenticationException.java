package org.wireBarley.common.exception;

public class AuthenticationException extends BaseRuntimeException {

  public AuthenticationException() {
    super("Authentication failed");
  }


  public AuthenticationException(ErrorCode code) {
    super(code);
  }


  public AuthenticationException(ErrorCode code, Throwable cause) {
    super(code, new String[]{}, cause);
  }


  public AuthenticationException(ErrorCode code, Object... args) {
    super(code, args, null);
  }


  public AuthenticationException(ErrorCode code, Object[] args, Throwable cause) {
    super(code, args, cause);
  }
}
