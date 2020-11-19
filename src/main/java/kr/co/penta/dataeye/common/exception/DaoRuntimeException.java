package kr.co.penta.dataeye.common.exception;

public class DaoRuntimeException extends RuntimeException
{
	public DaoRuntimeException() {}

	public DaoRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DaoRuntimeException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public DaoRuntimeException(String message)
	{
		super(message);
	}

	public DaoRuntimeException(Throwable cause)
	{
		super(cause);
	}
}