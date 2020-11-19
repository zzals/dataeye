package kr.co.penta.dataeye.common.entities.meta;

import kr.co.penta.dataeye.common.entities.CamelMap;

/**
 * Created by Administrator on 2014-09-04.
 */
public class AtrInfoEntity extends CamelMap {
	private static final long serialVersionUID = 3294504509732022318L;
    
    public String getObjTypeId() {
        return getString("objTypeId");
    }
    public void setObjTypeId(String objTypeId) {
        put("OBJ_TYPE_ID", objTypeId);
    }
    public Integer getAtrIdSeq() {
        return getInteger("atrIdSeq");
    }
    public void setAtrIdSeq(Integer atrIdSeq) {
        put("ATR_ID_SEQ", atrIdSeq);
    }
    public String getAtrId() {
        return getString("atrId");
    }
    public void setAtrId(String atrId) {
        put("ATR_ID", atrId);
    }
    public String getAtrNm() {
        return getString("atrNm");
    }
    public void setAtrNm(String atrNm) {
        put("ATR_NM", atrNm);
    }
    public String getMultiAtrYn() {
        return getString("multiAtrYn");
    }
    public void setMultiAtrYn(String multiAtrYn) {
        put("MULTI_ATR_YN", multiAtrYn);
    }
    public String getCnctAtrYn() {
        return getString("cnctAtrYn");
    }
    public void setCnctAtrYn(String cnctAtrYn) {
        put("CNCT_ATR_YN", cnctAtrYn);
    }
    public String getAtrAdmTgtYn() {
        return getString("atrAdmTgtYn");
    }
    public void setAtrAdmTgtYn(String atrAdmTgtYn) {
        put("ATR_ADM_TGT_YN", atrAdmTgtYn);
    }
    public String getMandYn() {
        return getString("mandYn");
    }
    public void setMandYn(String mandYn) {
        put("MAND_YN", mandYn);
    }
    public String getIndcYn() {
        return getString("indcYn");
    }
    public void setIndcYn(String indcYn) {
        put("INDC_YN", indcYn);
    }
    public String getAvailChkPgmId() {
        return getString("availChkPgmId");
    }
    public void setAvailChkPgmId(String availChkPgmId) {
        put("AVAIL_CHK_PGM_ID", availChkPgmId);
    }
    public String getAtrRfrnCd() {
        return getString("atrRfrnCd");
    }
    public void setAtrRfrnCd(String atrRfrnCd) {
        put("ATR_RFRN_CD", atrRfrnCd);
    }
    public String getSqlSbst() {
        return getString("sqlSbst");
    }
    public void setSqlSbst(String sqlSbst) {
        put("SQL_SBST", sqlSbst);
    }
    public String getDataTypeCd() {
        return getString("dataTypeCd");
    }
    public void setDataTypeCd(String dataTypeCd) {
        put("DATA_TYPE_CD", dataTypeCd);
    }
    public Integer getColLen() {
        return getInteger("colLen");
    }
    public void setColLen(Integer colLen) {
        put("COL_LEN", colLen);
    }
    public String getUiCompCd() {
        return getString("uiCompCd");
    }
    public void setUiCompCd(String uiCompCd) {
        put("UP_COMP_CD", uiCompCd);
    }
    public String getUiCompWidthRadio() {
        return getString("uiCompWidthRadio");
    }
    public void setUiCompWidthRadio(String uiCompWidthRadio) {
        put("UI_COMP_WIDTH_RADIO", uiCompWidthRadio);
    }
    public Integer getUiCompHeightPx() {
        return getInteger("uiCompHeightPx");
    }
    public void setUiCompHeightPx(Integer uiCompHeightPx) {
        put("UI_COMP_HEIGHT_PX", uiCompHeightPx);
    }
}
