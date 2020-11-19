package kr.co.penta.dataeye.spring.web.validate;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ObjTypeForm {
    @JsonProperty("OBJ_TYPE_ID")
    private String OBJ_TYPE_ID;
    
    @JsonProperty("OBJ_TYPE_NM")
    private String OBJ_TYPE_NM;
    
    
    @JsonProperty("OBJ_TYPE_DESC")
    private String OBJ_TYPE_DESC;
    
    @JsonProperty("DEL_YN")
    private String DEL_YN;
        
    @JsonProperty("SORT_NO")
    private String SORT_NO;
        
    @JsonProperty("UP_OBJ_TYPE_ID")
    private String UP_OBJ_TYPE_ID;
        
    @JsonProperty("UP_OBJ_TYPE_NM")
    private String UP_OBJ_TYPE_NM;
    
    @JsonProperty("HIER_LEV_NO")
    private String HIER_LEV_NO;
    
    @JsonProperty("LST_LEV_YN")
    private String LST_LEV_YN;
    
    @JsonProperty("ATR_ADM_CD")
    private String ATR_ADM_CD;
    
    @JsonProperty("ATR_ADM_NM")
    private String ATR_ADM_NM;
    
    @JsonProperty("STLM_YN")
    private String STLM_YN;
    
    @JsonProperty("HST_YN")
    private String HST_YN;
 
    @JsonProperty("TAG_YN")
    private String TAG_YN;
 
    @JsonProperty("CMMT_YN")
    private String CMMT_YN;

	public String getOBJ_TYPE_ID() {
		return OBJ_TYPE_ID;
	}

	public void setOBJ_TYPE_ID(String oBJ_TYPE_ID) {
		OBJ_TYPE_ID = oBJ_TYPE_ID;
	}

	public String getOBJ_TYPE_NM() {
		return OBJ_TYPE_NM;
	}

	public void setOBJ_TYPE_NM(String oBJ_TYPE_NM) {
		OBJ_TYPE_NM = oBJ_TYPE_NM;
	}
	
	public String getOBJ_TYPE_DESC() {
		return OBJ_TYPE_DESC;
	}

	public void setOBJ_TYPE_DESC(String oBJ_TYPE_DESC) {
		OBJ_TYPE_DESC = oBJ_TYPE_DESC;
	}

	public String getDEL_YN() {
		return DEL_YN;
	}

	public void setDEL_YN(String dEL_YN) {
		DEL_YN = dEL_YN;
	}

	public String getSORT_NO() {
		return SORT_NO;
	}

	public void setSORT_NO(String sORT_NO) {
		SORT_NO = sORT_NO;
	}

	public String getUP_OBJ_TYPE_ID() {
		return UP_OBJ_TYPE_ID;
	}

	public void setUP_OBJ_TYPE_ID(String uP_OBJ_TYPE_ID) {
		UP_OBJ_TYPE_ID = uP_OBJ_TYPE_ID;
	}

	public String getUP_OBJ_TYPE_NM() {
		return UP_OBJ_TYPE_NM;
	}

	public void setUP_OBJ_TYPE_NM(String uP_OBJ_TYPE_NM) {
		UP_OBJ_TYPE_NM = uP_OBJ_TYPE_NM;
	}

	public String getHIER_LEV_NO() {
		return HIER_LEV_NO;
	}

	public void setHIER_LEV_NO(String hIER_LEV_NO) {
		HIER_LEV_NO = hIER_LEV_NO;
	}

	public String getLST_LEV_YN() {
		return LST_LEV_YN;
	}

	public void setLST_LEV_YN(String lST_LEV_YN) {
		LST_LEV_YN = lST_LEV_YN;
	}

	public String getATR_ADM_CD() {
		return ATR_ADM_CD;
	}

	public void setATR_ADM_CD(String aTR_ADM_CD) {
		ATR_ADM_CD = aTR_ADM_CD;
	}

	public String getATR_ADM_NM() {
		return ATR_ADM_NM;
	}

	public void setATR_ADM_NM(String aTR_ADM_NM) {
		ATR_ADM_NM = aTR_ADM_NM;
	}

	public String getSTLM_YN() {
		return STLM_YN;
	}

	public void setSTLM_YN(String sTLM_YN) {
		STLM_YN = sTLM_YN;
	}

	public String getHST_YN() {
		return HST_YN;
	}

	public void setHST_YN(String hST_YN) {
		HST_YN = hST_YN;
	}

	public String getTAG_YN() {
		return TAG_YN;
	}

	public void setTAG_YN(String tAG_YN) {
		TAG_YN = tAG_YN;
	}

	public String getCMMT_YN() {
		return CMMT_YN;
	}

	public void setCMMT_YN(String cMMT_YN) {
		CMMT_YN = cMMT_YN;
	}
    
    
    
}
