package exceptions;

public class CustomException extends Exception {
  private int errorCode; // 错误码
  private String errorDescription; // 错误描述

  public CustomException(String errorDescription) {
    super(errorDescription);
    this.errorDescription = errorDescription;
  }

  public CustomException(int errorCode, String errorDescription) {
    super(errorDescription);
    this.errorCode = errorCode;
    this.errorDescription = errorDescription;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public String getErrorDescription() {
    return errorDescription;
  }

  @Override
  public String toString() {
    return "CustomException{" +
        "errorCode=" + errorCode +
        ", errorDescription='" + errorDescription + '\'' +
        '}';
  }
}
