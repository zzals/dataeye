package kr.co.penta.dataeye.common.entities.meta;

import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class PenAprvM extends DaoParam implements Serializable {
    private static final long serialVersionUID = -3671363250276110291L;

    public PenAprvM() {
        put("timestampFormat", DataEyeSettingsHolder.getInstance().getConfig().getDbTimestampFormat());
        put("APRV_REQR_CD", "10");
        put("DEL_YN", "N");
    }

    public PenAprvM(Map<String, Object> map) {
        this();
        putAll(map);
    }

    public String getAprvId() {
        return getString("aprvId");
    }

    public void setAprvId(String aprvId) {
        put("APRV_ID", aprvId);
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

    public String getAprvNm() {
        return getString("aprvNm");
    }

    public void setAprvNm(String aprvNm) {
        put("APRV_NM", aprvNm);
    }

    public String getAprvDesc() {
        return getString("aprvDesc");
    }

    public void setAprvDesc(String aprvDesc) {
        put("APRV_DESC", aprvDesc);
    }

    public String getAprvTypeCd() {
        return getString("aprvTypeCd");
    }

    public void setAprvTypeCd(String aprvTypeCd) {
        put("APRV_TYPE_CD", aprvTypeCd);
    }

    public String getAprvReqrId() {
        return getString("aprvReqrId");
    }

    public void setAprvReqrId(String aprvReqrId) {
        put("APRV_REQR_ID", aprvReqrId);
    }

    public String getAprvReqrCd() {
        return getString("aprvReqrCd");
    }

    public void setAprvReqrCd(String aprvReqrCd) {
        put("APRV_REQR_CD", aprvReqrCd);
    }

    public String getAprvReqDt() {
        return getString("aprvReqDt");
    }

    public void setAprvReqDt(String aprvReqDt) {
        put("APRV_REQ_DT", aprvReqDt);
    }

    public String getLastLineId() {
        return getString("lastLineId");
    }

    public void setLastLineId(String lastLineId) {
        put("LAST_LINE_ID", lastLineId);
    }

    public String getLastStusCd() {
        return getString("lastStusCd");
    }

    public void setLastStusCd(String lastStusCd) {
        put("LAST_STUS_CD", lastStusCd);
    }

    public String getAprvObjId() {
        return getString("aprvObjId");
    }

    public void setAprvObjId(String aprvObjId) {
        put("APRV_OBJ_ID", aprvObjId);
    }

    public String getAprvLinkId() {
        return getString("aprvLinkId");
    }

    public void setAprvLinkId(String aprvLinkId) {
        put("APRV_LINK_ID", aprvLinkId);
    }

    public String getObjTypeId() {
        return getString("objTypeId");
    }

    public void setObjTypeId(String objTypeId) {
        put("OBJ_TYPE_ID", objTypeId);
    }

    public String getObjId() {
        return getString("objId");
    }

    public void setObjId(String objId) {
        put("OBJ_ID", objId);
    }

    public void genAprvId() {
        Date from = new Date();
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
        put("APRV_ID", transFormat.format(from) + UUID.randomUUID().toString().replace("-", ""));
    }

    public String getAprvAtr01Val() {
        return getString("aprvAtr01Val");
    }

    public void setAprvAtr01Val(String aprvAtr01Val) {
        put("APRV_ATR01_VAL", aprvAtr01Val);
    }
}
