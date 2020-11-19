package kr.co.penta.dataeye.common.entities.meta;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;

import java.io.Serializable;

public class PenAprvD extends DaoParam implements Serializable {
    private static final long serialVersionUID = -5231221851869205953L;

    public PenAprvD() {
        put("timestampFormat", DataEyeSettingsHolder.getInstance().getConfig().getDbTimestampFormat());
        put("DEL_YN", "N");
    }

    public PenAprvD(PenAprvM penAprvM) {
        this();
        setAprvId(penAprvM.getAprvId());
        setDelYn(penAprvM.getDelYn());
        setCretDt(penAprvM.getCretDt());
        setCretrId(penAprvM.getCretrId());
        setChgDt(penAprvM.getChgDt());
        setChgrId(penAprvM.getChgrId());
    }

    public PenAprvD(PenAprvM penAprvM, CamelMap camelMap) {
        this(penAprvM);
        setAprvDetlId(camelMap.getString("aprvDetlId"));
        setAprvLineId(camelMap.getString("aprvLineId"));
        setAprvPrcsSortNo(camelMap.getInteger("aprvPrcsSortNo"));
        setAprvPrcsNm(camelMap.getString("aprvPrcsNm"));
        setAprvUserId(camelMap.getString("aprvUserId"));
        setAprvDesc(camelMap.getString("aprvDesc"));
        setRealAprvUserId(camelMap.getString("realAprvUserId"));
        setAprvDt(camelMap.getString("aprvDt"));
        setAprvRsltCd(camelMap.getString("aprvRsltCd"));
        setAprvLineObjId(camelMap.getString("aprvLineObjId"));
        setAprvAtr01Val(camelMap.getString("aprvAtr01Val"));
        setAprvAtr02Val(camelMap.getString("aprvAtr02Val"));
        setAprvAtr03Val(camelMap.getString("aprvAtr03Val"));
        setAprvAtr04Val(camelMap.getString("aprvAtr04Val"));
        setAprvAtr05Val(camelMap.getString("aprvAtr05Val"));
        setAprvAtr06Val(camelMap.getString("aprvAtr06Val"));
        setAprvAtr07Val(camelMap.getString("aprvAtr07Val"));
        setAprvAtr08Val(camelMap.getString("aprvAtr08Val"));
        setAprvAtr09Val(camelMap.getString("aprvAtr09Val"));
        setAprvAtr10Val(camelMap.getString("aprvAtr10Val"));
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

    public String getAprvLineId() {
        return getString("aprvLineId");
    }

    public void setAprvLineId(String aprvLineId) {
        put("APRV_LINE_ID", aprvLineId);
    }

    public Integer getAprvPrcsSortNo() {
        return getInteger("aprvPrcsSortNo");
    }

    public void setAprvPrcsSortNo(Integer aprvPrcsSortNo) {
        put("APRV_PRCS_SORT_NO", aprvPrcsSortNo);
    }

    public String getAprvPrcsNm() {
        return getString("aprvPrcsNm");
    }

    public void setAprvPrcsNm(String aprvPrcsNm) {
        put("APRV_PRCS_NM", aprvPrcsNm);
    }

    public String getAprvUserId() {
        return getString("aprvUserId");
    }

    public void setAprvUserId(String aprvUserId) {
        put("APRV_USER_ID", aprvUserId);
    }

    public String getAprvDesc() {
        return getString("aprvDesc");
    }

    public void setAprvDesc(String aprvDesc) {
        put("APRV_DESC", aprvDesc);
    }

    public String getRealAprvUserId() {
        return getString("realAprvUserId");
    }

    public void setRealAprvUserId(String realAprvUserId) {
        put("REAL_APRV_USER_ID", realAprvUserId);
    }

    public String getAprvDt() {
        return getString("aprvDt");
    }

    public void setAprvDt(String aprvDt) {
        put("APRV_DT", aprvDt);
    }

    public String getAprvRsltCd() {
        return getString("aprvRsltCd");
    }

    public void setAprvRsltCd(String aprvRsltCd) {
        put("APRV_RSLT_CD", aprvRsltCd);
    }

    public String getAprvLineObjId() {
        return getString("aprvLineObjId");
    }

    public void setAprvLineObjId(String aprvLineObjId) {
        put("APRV_LINE_OBJ_ID", aprvLineObjId);
    }

    public String getAprvAtr01Val() {
        return getString("aprvAtr01Val");
    }

    public void setAprvAtr01Val(String aprvAtr01Val) {
        put("APRV_ATR01_VAL", aprvAtr01Val);
    }

    public String getAprvAtr02Val() {
        return getString("aprvAtr02Val");
    }

    public void setAprvAtr02Val(String aprvAtr02Val) {
        put("APRV_ATR02_VAL", aprvAtr02Val);
    }

    public String getAprvAtr03Val() {
        return getString("aprvAtr03Val");
    }

    public void setAprvAtr03Val(String aprvAtr03Val) {
        put("APRV_ATR03_VAL", aprvAtr03Val);
    }

    public String getAprvAtr04Val() {
        return getString("aprvAtr04Val");
    }

    public void setAprvAtr04Val(String aprvAtr04Val) {
        put("APRV_ATR04_VAL", aprvAtr04Val);
    }

    public String getAprvAtr05Val() {
        return getString("aprvAtr05Val");
    }

    public void setAprvAtr05Val(String aprvAtr05Val) {
        put("APRV_ATR05_VAL", aprvAtr05Val);
    }

    public String getAprvAtr06Val() {
        return getString("aprvAtr06Val");
    }

    public void setAprvAtr06Val(String aprvAtr06Val) {
        put("APRV_ATR06_VAL", aprvAtr06Val);
    }

    public String getAprvAtr07Val() {
        return getString("aprvAtr07Val");
    }

    public void setAprvAtr07Val(String aprvAtr07Val) {
        put("APRV_ATR07_VAL", aprvAtr07Val);
    }

    public String getAprvAtr08Val() {
        return getString("aprvAtr08Val");
    }

    public void setAprvAtr08Val(String aprvAtr08Val) {
        put("APRV_ATR08_VAL", aprvAtr08Val);
    }

    public String getAprvAtr09Val() {
        return getString("aprvAtr09Val");
    }

    public void setAprvAtr09Val(String aprvAtr09Val) {
        put("APRV_ATR09_VAL", aprvAtr09Val);
    }

    public String getAprvAtr10Val() {
        return getString("aprvAtr10Val");
    }

    public void setAprvAtr10Val(String aprvAtr10Val) {
        put("APRV_ATR10_VAL", aprvAtr10Val);
    }
}
