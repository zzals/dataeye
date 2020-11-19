package kr.co.penta.dataeye.spring.web.http.response;

import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import kr.co.penta.dataeye.spring.web.MessageHolder;

public class StdResponse {
    public interface StatusCodeInterface {
        String getDisplayableType();
    }
    
    public static enum StatusCode implements StatusCodeInterface {
        SUCCESS("success"), FAIL("fail");
        public final String value;

        private StatusCode(final String value) {
            this.value = value;
        }

        public String getDisplayableType() {
            return value;
        }
    }
    
    private StatusCode status;
    private String message;
    private Object data;
    private String errorCode;
    private List<FieldError> errorMessageList;

    public StdResponse() {
    }

    public StatusCode getStatus() {
        return status;
    }
    public String getMessage() {
        return this.message;
    }
    public Object getData() {
        return this.data;
    }
    public String getErrorCode() {
        return this.errorCode;
    }
    
    public List<FieldError> getErrorMessageList() {
        return this.errorMessageList;
    }
    
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public StdResponse setSuccess() {
        this.status = StatusCode.SUCCESS;
        return this;
    }

    public StdResponse setSuccess(String message) {
        this.status = StatusCode.SUCCESS;
        this.message = message;
        return this;
    }
    
    public StdResponse setSuccess(Object o) {
        this.status = StatusCode.SUCCESS;
        this.data = o;
        return this;
    }
    
    public StdResponse setSuccess(Object o, String message) {
        this.status = StatusCode.SUCCESS;
        this.data = o;
        this.message = message;
        return this;
    }

    public StdResponse setFail(String message) {
        this.status = StatusCode.FAIL;
        this.message = message;
        return this;
    }
    
    public StdResponse setFail(Exception e) {
        this.status = StatusCode.FAIL;
        this.message = e.getMessage();
        return this;
    }
    
    public StdResponse setFail(BindingResult bindingResult) {
        this.status = StatusCode.FAIL;
        this.errorMessageList = bindingResult.getFieldErrors();
        this.errorCode = bindingResult.getFieldError().getCodes()[0];
        this.message = MessageHolder.getInstance().get(this.errorCode);
        
        return this;
    }
    
    public StdResponse setFail(List<FieldError> errorMessageList) {
        this.status = StatusCode.FAIL;
        this.errorMessageList = errorMessageList;
        return this;
    }
}