package kr.co.penta.dataeye.model;

public class ResultValueObject<T> {

    private static final int CODE_NORMAL = 0;
    private static final int CODE_ERROR = -1;
    private static final String MESSAGE_NORMAL = "";
    private static final String MESSAGE_ERROR = "";

    int code;
    String message;
    int errorCode;
    String errorMessage;
    T value;

    public ResultValueObject(T value) {
        reset();
        this.value = value;
    }

    private void reset() {
        this.code = CODE_NORMAL;
        this.message = MESSAGE_NORMAL;
        this.errorCode = CODE_NORMAL;
        this.errorMessage = MESSAGE_NORMAL;
        this.value = null;
    }

    public void setError(int errorCode, String errorMessage) {
        this.code = CODE_ERROR;
        this.message = MESSAGE_NORMAL;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.value = null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
