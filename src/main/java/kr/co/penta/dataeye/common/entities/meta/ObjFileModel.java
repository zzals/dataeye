package kr.co.penta.dataeye.common.entities.meta;

import kr.co.penta.dataeye.common.entities.CamelMap;

public class ObjFileModel extends CamelMap {
    private static final long serialVersionUID = 679793118679808538L;

    public ObjFileModel() {

    }

    public String getFilePhyName() {
        return getString("filePhyName");
    }

    public void setFilePhyName(String filePhyName) {
        put("filePhyName", filePhyName);
    }

    public String getFileLocName() {
        return getString("fileLocName");
    }

    public void setFileLocName(String fileLocName) {
        put("fileLocName", fileLocName);
    }

    public String getFileSize() {
        return getString("fileSize");
    }

    public void setFileSize(String fileSize) {
        put("fileSize", fileSize);
    }

    public String getFileType() {
        return getString("fileType");
    }

    public void setFileType(String fileType) {
        put("fileType", fileType);
    }

    public String getPath() {
        return getString("path");
    }

    public void setPath(String path) {
        put("path", path);
    }
}
