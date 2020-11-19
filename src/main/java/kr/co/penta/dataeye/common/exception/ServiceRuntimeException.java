package kr.co.penta.dataeye.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ServiceRuntimeException extends RuntimeException {
	public ServiceRuntimeException() {}

	public ServiceRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
	{
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ServiceRuntimeException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ServiceRuntimeException(String message)
	{
		super(message);
	}

	public ServiceRuntimeException(Throwable cause)
	{
		super(cause);
	}
}