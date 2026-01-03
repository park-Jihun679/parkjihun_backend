package org.wireBarley.common.exception;

import java.time.ZonedDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class ErrorResponse {

  private String message;
  private HttpStatus status;
  private int code;
  private ZonedDateTime timeStamp;
  private Object detail;
  private ErrorCode errorCode;


  private ErrorResponse(String _message, HttpStatus _status) {
    message = _message;
    status = _status;
    code = status.value();
    timeStamp = ZonedDateTime.now();
  }

  private ErrorResponse(String _message, HttpStatus _status, ErrorCode _errorCode) {
    message = _message;
    status = _status;
    code = status.value();
    timeStamp = ZonedDateTime.now();
    errorCode = _errorCode;
  }

  private ErrorResponse(String _message, HttpStatus _status, Object _detail, ErrorCode _errorCode) {
    message = _message;
    status = _status;
    code = status.value();
    timeStamp = ZonedDateTime.now();
    detail = _detail;
    errorCode = _errorCode;
  }
  

  public static ErrorResponse from(Exception ex) {
    return new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  public static ErrorResponse of(String message, HttpStatus status) {
    return new ErrorResponse(message, status);
  }

  public static ErrorResponse of(String message, HttpStatus status, ErrorCode errorCode) {
    return new ErrorResponse(message, status, errorCode);
  }


  public static ErrorResponse of(String message, HttpStatus status, Object detail,
      ErrorCode errorCode) {
    return new ErrorResponse(message, status, detail, errorCode);
  }
}
