package org.wireBarley.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorCode {

  private String message;

  /* COMMON */
  public final static ErrorCode COMMON_ACCESS_DENIED = new ErrorCode("COMMON.ERROR.ACCESS_DENIED");
  public final static ErrorCode COMMON_BINDING_ERROR = new ErrorCode("COMMON.ERROR.BINDING_ERROR");
  public final static ErrorCode COMMON_DATA_NOT_FOUND = new ErrorCode(
      "COMMON.ERROR.DATA_NOT_FOUND");
  public final static ErrorCode COMMON_EXTERNAL_SYSTEM_FAILED = new ErrorCode(
      "COMMON.ERROR.EXTERNAL_SYSTEM_FAILED");
  public final static ErrorCode COMMON_INTERNAL_SERVER_ERROR = new ErrorCode(
      "COMMON.ERROR.INTERNAL_SERVER_ERROR");
  public final static ErrorCode COMMON_INVALID_REQUEST = new ErrorCode(
      "COMMON.ERROR.INVALID_REQUEST");
  public final static ErrorCode DUPLICATED_REQUEST = new ErrorCode(
      "COMMON.ERROR.DUPLICATED_REQUEST");

  /* USER */
  public final static ErrorCode COMMON_USER_NOT_FOUND = new ErrorCode(
      "USER.ERROR.USER_NOT_FOUND");
  public final static ErrorCode USER_IS_EXISTED = new ErrorCode(
      "USER.ERROR.USER_IS_EXISTED");
  public final static ErrorCode PASSWORD_IS_NOT_MATCHED = new ErrorCode(
      "USER.ERROR.PASSWORD_NOT_MATCHED");
  public final static ErrorCode USER_ALREADY_DELETED = new ErrorCode(
      "USER.ERROR.USER_ALREADY.DELETED");

  /* ACCOUNT */
  public final static ErrorCode DUPLICATE_ACCOUNT = new ErrorCode(
      "이미 존재하는 계좌번호입니다.");
  public final static ErrorCode ACCOUNT_DATA_NOT_FOUND = new ErrorCode(
      "계좌를 찾을 수 없습니다.");
  public final static ErrorCode ACCOUNT_BALANCE_NOT_ZERO = new ErrorCode(
      "계좌에 잔액이 존재합니다.");

  /* TRANSACTION */
  public final static ErrorCode OVER_DAILY_WITHDRAWAL_LIMIT = new ErrorCode(
      "당일 출금 한도를 초과하였습니다.");
  public final static ErrorCode INSUFFICIENT_ACCOUNT_BALANCE = new ErrorCode(
      "계좌 잔액이 부족합니다.");
  public final static ErrorCode CAN_NOT_TRANSFER_THIS_ACCOUNT = new ErrorCode(
      "본인 계좌에 이체할 수 없습니다.");
  public final static ErrorCode OVER_DAILY_TRANSFER_LIMIT = new ErrorCode(
      "당일 이체 한도를 초과하였습니다.");
}
