package kr.co.penta.dataeye.common.entities.meta;

import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;

import java.io.Serializable;

public class PenAprvF extends DaoParam implements Serializable {
    private static final long serialVersionUID = -5207671471957328078L;

    public PenAprvF() {
        put("timestampFormat", DataEyeSettingsHolder.getInstance().getConfig().getDbTimestampFormat());
        put("DEL_YN", "N");
    }

    public String getAprvId() {
        return getString("aprvId");
    }

    public void setAprvId(String aprvId) {
        put("APRV_ID", aprvId);
    }

    public String getAprvDetlId() {
        return getString("aprvDetlId");
    }

    public void setAprvDetlId(String aprvDetlId) {
        put("APRV_DETL_ID", aprvDetlId);
    }

    public Integer getFileNo() {
        return getInteger("fileNo");
    }

    public void setFileNo(Integer fileNo) {
        put("FILE_NO", fileNo);
    }

    public String getDelYn() {
        return getString("delYn");
    }

    public void setDelYn(String delYn) {
        put("DEL_YN", delYn);
    }

    public String getCretDt() {
        return getString("cretDt");
    }

    public void setCretDt(String cretDt) {
        put("CRET_DT", cretDt);
    }

    public String getCretrId() {
        return getString("cretrId");
    }

    public void setCretrId(String cretrId) {
        put("CRETR_ID", cretrId);
    }

    public String getChgDt() {
        return getString("chgDt");
    }

    public void setChgDt(String chgDt) {
        put("CHG_DT", chgDt);
    }

    public String getChgrId() {
        return getString("chgrId");
    }

    public void setChgrId(String chgrId) {
        put("CHGR_ID", chgrId);
    }

    public String getFileId() {
        return getString("fileId");
    }

    public void setFileId(String fileId) {
        put("FILE_ID", fileId);
    }

    public String getFileNm() {
        return getString("fileNm");
    }

    public void setFileNm(String fileNm) {
        put("FILE_NM", fileNm);
    }

    public String getFileSize() {
        return getString("fileSize");
    }

    public void setFileSize(String fileSize) {
        put("FILE_SIZE", fileSize);
    }

    public String getFileType() {
        return getString("fileType");
    }

    public void setFileType(String fileType) {
        put("FILE_TYPE", fileType);
    }

    public String getFilePath() {
        return getString("filePath");
    }

    public void setFilePath(String filePath) {
        put("FILE_PATH", filePath);
    }
}
