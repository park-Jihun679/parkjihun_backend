package org.wireBarley.common.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.file.AccessDeniedException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(value = {MethodArgumentNotValidException.class, InvalidRequestException.class})
  protected ResponseEntity<ErrorResponse> handleInvalidRequestException(BaseRuntimeException ex) {
    ErrorResponse response = ErrorResponse.of(ex.getMessage(), HttpStatus.BAD_REQUEST,
        ex.getErrorCode());

    return ResponseEntity
        .status(response.getStatus())
        .body(response);
  }

  @ExceptionHandler(value = InvalidDataAccessApiUsageException.class)
  protected ResponseEntity<ErrorResponse> handleInvalidRequestException(RuntimeException ex) {
    ErrorResponse response = ErrorResponse.of(ex.getMessage(), HttpStatus.BAD_REQUEST,
        ErrorCode.COMMON_INVALID_REQUEST);

    return ResponseEntity
        .status(response.getStatus())
        .body(response);
  }

  @ExceptionHandler(value = {UnsupportedEncodingException.class, URISyntaxException.class,
      NoSuchAlgorithmException.class, InvalidKeyException.class, JsonProcessingException.class})
  protected ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
    ErrorResponse response = ErrorResponse.of(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR,
        ErrorCode.COMMON_INTERNAL_SERVER_ERROR);

    return ResponseEntity
        .status(response.getStatus())
        .body(response);
  }


  @ExceptionHandler(value = {BindingErrorException.class})
  protected ResponseEntity<ErrorResponse> handleIBindingErrorException(BindingErrorException ex) {
    ErrorResponse response = ErrorResponse.of(ex.getMessage(), HttpStatus.BAD_REQUEST,
        ex.getFieldErrors(), ex.getErrorCode());

    return ResponseEntity
        .status(response.getStatus())
        .body(response);
  }

  @ExceptionHandler(value = {DataNotFoundException.class})
  protected ResponseEntity<ErrorResponse> handleDataNotFoundException(BaseRuntimeException ex) {
    ErrorResponse response = ErrorResponse.of(ex.getMessage(), HttpStatus.NOT_FOUND,
        ex.getErrorCode());

    return ResponseEntity
        .status(response.getStatus())
        .body(response);
  }

  /**
   * 그 외 발생하는 모든 예외는 BAD_REQUEST 로 처리
   */
  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ErrorResponse> handleException(Exception ex) {
    ErrorResponse response = ErrorResponse.from(ex);

    log.error("Unhandled Exception...", ex);
    return ResponseEntity
        .status(response.getStatus())
        .body(response);
  }

}
