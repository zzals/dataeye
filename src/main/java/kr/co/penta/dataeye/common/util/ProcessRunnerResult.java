package kr.co.penta.dataeye.common.util;


public class ProcessRunnerResult {
    private String result = "";
    private String output = "";
    private Exception exception;
    
    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public String getOutput() {
        return output;
    }
    public void setOutput(String output) {
        this.output = output;
    }
    public Exception getException() {
        return exception;
    }
    public void setException(Exception exception) {
        this.exception = exception;
    }
}