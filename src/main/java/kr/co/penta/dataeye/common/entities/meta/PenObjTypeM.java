package kr.co.penta.dataeye.common.entities.meta;

import kr.co.penta.dataeye.common.entities.CamelMap;

public class PenObjTypeM extends CamelMap {
    private static final long serialVersionUID = -2743438847788953357L;
    
    public String getObjTypeId() {
        return getString("objTypeId");
    }
    public void setObjTypeId(String objTypeId) {
        put("OBJ_TYPE_ID", objTypeId);
    }
    public String getObjTypeNm() {
        return getString("objTypeNm");
    }
    public void setObjTypeNm(String objTypeNm) {
        put("OBJ_TYPE_NM", objTypeNm);
    }
    public String getObjTypeDesc() {
        return getString("objTypeDesc");
    }
    public void setObjTypeDesc(String objTypeDesc) {
        put("OBJ_TYPE_DESC", objTypeDesc);
    }
    public Integer getSortNo() {
        return getInteger("sortNo");
    }
    public void setSortNo(Integer sortNo) {
        put("SORT_NO", sortNo);
    }
    public String getUpObjTypeId() {
        return getString("upObjTypeId");
    }
    public void setUpObjTypeId(String upObjTypeId) {
        put("UP_OBJ_TYPE_ID", upObjTypeId);
    }
    public String getHierLevNo() {
        return getString("hierLevNo");
    }
    public void setHierLevNo(String hierLevNo) {
        put("HIER_LEV_NO", hierLevNo);
    }
    public String getLstLevYn() {
        return getString("lstLevYn");
    }
    public void setLstLevYn(String lstLevYn) {
        put("LST_LEV_YN", lstLevYn);
    }
    public String getAtrAdmCd() {
        return getString("atrAdmCd");
    }
    public void setAtrAdmCd(String atrAdmCd) {
        put("ATR_ADM_CD", atrAdmCd);
    }
    public String getStlmYn() {
        return getString("stlmYn");
    }
    public void setStlmYn(String stlmYn) {
        put("STLM_YN", stlmYn);
    }
    public String getHstYn() {
        return getString("hstYn");
    }
    public void setHstYn(String hstYn) {
        put("HST_YN", hstYn);
    }
}
