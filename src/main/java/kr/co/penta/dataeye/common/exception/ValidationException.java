package kr.co.penta.dataeye.common.exception;

import org.springframework.validation.BindingResult;

public class ValidationException extends RuntimeException {
	private BindingResult bindingResult;
	
    public ValidationException() {}

	public ValidationException(BindingResult bindingResult)
	{
	    this.bindingResult = bindingResult;
	}
	
	public BindingResult getBindingResult() {
	    return bindingResult;
	}
}