package kr.co.penta.dataeye.common.exception;


import java.util.List;

public class SystemException extends RuntimeException
{
  private String errorCode;
  private List detailErrorMessage;

  public SystemException(String errorCode)
  {
    super(errorCode);
  }

  public SystemException(String errorCode, Exception cause)
  {
    super(errorCode, cause);
  }

  public SystemException(String errorCode, List detailErrorMessage)
  {
    this.errorCode = errorCode;
    this.detailErrorMessage = detailErrorMessage;
  }

  public SystemException(String errorCode, List detailErrorMessage, Exception cause)
  {
    super(cause);
    this.errorCode = errorCode;
    this.detailErrorMessage = detailErrorMessage;
  }

  public SystemException(Exception cause)
  {
    super(cause);
  }

  public String getErrorCode()
  {
    return this.errorCode;
  }

  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  public List getDetailErrorMessage() {
    return this.detailErrorMessage;
  }

  public void setDetailErrorMessage(List detailErrorMessage) {
    this.detailErrorMessage = detailErrorMessage;
  }
}