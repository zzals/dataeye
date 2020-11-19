package kr.co.penta.dataeye.common.entities.meta;

import kr.co.penta.dataeye.common.entities.CamelMap;

public class PenRelTypeM extends CamelMap {
    private static final long serialVersionUID = -2743438847788953357L;
    
    public String getRelTypeId() {
        return getString("relTypeId");
    }
    public void setRelTypeId(String relTypeId) {
        put("REL_TYPE_ID", relTypeId);
    }
    public String getRelTypeNm() {
        return getString("relTypeNm");
    }
    public void setRelTypeNm(String relTypeNm) {
        put("REL_TYPE_NM", relTypeNm);
    }
    public String getDownRelTypeNm() {
        return getString("downRelTypeNm");
    }
    public void setDownRelTypeNm(String downRelTypeNm) {
        put("DOWN_REL_TYPE_NM", downRelTypeNm);
    }
    public String getUpRelTypeNm() {
        return getString("upRelTypeNm");
    }
    public void setUpRelTypeNm(String upRelTypeNm) {
        put("UP_REL_TYPE_NM", upRelTypeNm);
    }
    public String getRelTypeDesc() {
        return getString("relTypeDesc");
    }
    public void setRelTypeDesc(String relTypeDesc) {
        put("REL_TYPE_DESC", relTypeDesc);
    }
    public String getObjTypeId() {
        return getString("objTypeId");
    }
    public void setObjTypeId(String objTypeId) {
        put("OBJ_TYPE_ID", objTypeId);
    }
    public String getRelObjTypeId() {
        return getString("relObjTypeId");
    }
    public void setRelObjTypeId(String relObjTypeId) {
        put("REL_OBJ_TYPE_ID", relObjTypeId);
    }
    public String getMetaRelCd() {
        return getString("metaRelCd");
    }
    public void setMetaRelCd(String metaRelCd) {
        put("META_REL_CD", metaRelCd);
    }
    public String getUpRelTypeId() {
        return getString("upRelTypeId");
    }
    public void setUpRelTypeId(String upRelTypeId) {
        put("UP_REL_TYPE_ID", upRelTypeId);
    }
    public String getAtrAdmCd() {
        return getString("atrAdmCd");
    }
    public void setAtrAdmCd(String atrAdmCd) {
        put("ATR_ADM_CD", atrAdmCd);
    }
    public String getSqlSbst() {
        return getString("sqlSbst");
    }
    public void setSqlSbst(String sqlSbst) {
        put("SQL_SBST", sqlSbst);
    }
}
