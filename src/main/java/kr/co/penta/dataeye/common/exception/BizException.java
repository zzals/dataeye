package kr.co.penta.dataeye.common.exception;

import java.util.List;

public class BizException extends SystemException
{
  public BizException(String errorCode)
  {
    super(errorCode);
  }

  public BizException(String errorCode, Exception cause)
  {
    super(errorCode, cause);
  }

  public BizException(String errorCode, List errorDetailMessage)
  {
    super(errorCode, errorDetailMessage);
  }

  public BizException(String errorCode, List detailErrorMessage, Exception cause)
  {
    super(errorCode, detailErrorMessage, cause);
  }

  public BizException(Exception cause)
  {
    super(cause);
  }
}