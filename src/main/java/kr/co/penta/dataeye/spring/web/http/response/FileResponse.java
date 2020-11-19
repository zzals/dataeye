package kr.co.penta.dataeye.spring.web.http.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"bytes"})
public class FileResponse {
    private String filePhyName;
    private String fileLocName;
    private String path;
    private String fileSize;
    private String fileType;
    private String status = "S";
    private String message = "";

    public String getFilePhyName() {
        return filePhyName;
    }

    public void setFilePhyName(String filePhyName) {
        this.filePhyName = filePhyName;
    }

    public String getFileLocName() {
        return fileLocName;
    }

    public void setFileLocName(String fileLocName) {
        this.fileLocName = fileLocName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private byte[] bytes;
    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
}
