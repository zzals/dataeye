package kr.co.penta.dataeye.spring.web.validate;

import java.util.UUID;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MenuForm {
    @JsonProperty("MENU_ID")
    private String MENU_ID;    
    
    @Size(min=1, max=1)
    @JsonProperty("DEL_YN")
    private String DEL_YN;    
    
    @JsonProperty("CRET_DT")
    private String CRET_DT;    
    
    @JsonProperty("CRETR_ID")
    private String CRETR_ID;    
 
    @JsonProperty("CHG_DT")
    private String CHG_DT;    
 
    @JsonProperty("CHGR_ID")
    private String CHGR_ID;    
    
    @JsonProperty("MENU_ADM_ID")
    private String MENU_ADM_ID;
    
    @Size(min=1)
    @JsonProperty("MENU_NM")
    private String MENU_NM;
    
    @JsonProperty("MENU_DESC")
    private String MENU_DESC;
    
    @JsonProperty("ICON_CLASS")
    private String ICON_CLASS;
    
    @JsonProperty("PGM_ID")
    private String PGM_ID;
    
    @JsonProperty("UP_MENU_ID")
    private String UP_MENU_ID;
    
    @Size(min=1)
    @JsonProperty("MENU_TYPE")
    private String MENU_TYPE;
    
    @Size(min=1)
    @JsonProperty("APP_ID")
    private String APP_ID;
    
    @Size(min=1)
    @JsonProperty("SORT_NO")
    private String SORT_NO;
    
    @Size(min=1, max=1)
    @JsonProperty("USE_YN")
    private String USE_YN;
    
    @Size(min=1)
    @JsonProperty("MENU_GRP_CD")
    private String MENU_GRP_CD;

	public String getMENU_ID() {
		return MENU_ID;
	}

	public void setMENU_ID(String mENU_ID) {
		MENU_ID = mENU_ID;
	}

    public void genMENU_ID() {
        MENU_ID = APP_ID+"_MENU_"+UUID.randomUUID();
    }

	public String getDEL_YN() {
		return DEL_YN;
	}

	public void setDEL_YN(String dEL_YN) {
		DEL_YN = dEL_YN;
	}

	public String getCRET_DT() {
		return CRET_DT;
	}

	public void setCRET_DT(String cRET_DT) {
		CRET_DT = cRET_DT;
	}

	public String getCRETR_ID() {
		return CRETR_ID;
	}

	public void setCRETR_ID(String cRETR_ID) {
		CRETR_ID = cRETR_ID;
	}

	public String getCHG_DT() {
		return CHG_DT;
	}

	public void setCHG_DT(String cHG_DT) {
		CHG_DT = cHG_DT;
	}

	public String getCHGR_ID() {
		return CHGR_ID;
	}

	public void setCHGR_ID(String cHGR_ID) {
		CHGR_ID = cHGR_ID;
	}

	public String getMENU_ADM_ID() {
		return MENU_ADM_ID;
	}

	public void setMENU_ADM_ID(String mENU_ADM_ID) {
		MENU_ADM_ID = mENU_ADM_ID;
	}

	public String getMENU_NM() {
		return MENU_NM;
	}

	public void setMENU_NM(String mENU_NM) {
		MENU_NM = mENU_NM;
	}

	public String getMENU_DESC() {
		return MENU_DESC;
	}

	public void setMENU_DESC(String mENU_DESC) {
		MENU_DESC = mENU_DESC;
	}

	public String getICON_CLASS() {
		return ICON_CLASS;
	}

	public void setICON_CLASS(String iCON_CLASS) {
		ICON_CLASS = iCON_CLASS;
	}

	public String getPGM_ID() {
		return PGM_ID;
	}

	public void setPGM_ID(String pGM_ID) {
		PGM_ID = pGM_ID;
	}

	public String getUP_MENU_ID() {
		return UP_MENU_ID;
	}

	public void setUP_MENU_ID(String uP_MENU_ID) {
		UP_MENU_ID = uP_MENU_ID;
	}

	public String getMENU_TYPE() {
		return MENU_TYPE;
	}

	public void setMENU_TYPE(String mENU_TYPE) {
		MENU_TYPE = mENU_TYPE;
	}

	public String getAPP_ID() {
		return APP_ID;
	}

	public void setAPP_ID(String aPP_ID) {
		APP_ID = aPP_ID;
	}

	public String getSORT_NO() {
		return SORT_NO;
	}

	public void setSORT_NO(String sORT_NO) {
		SORT_NO = sORT_NO;
	}

	public String getUSE_YN() {
		return USE_YN;
	}

	public void setUSE_YN(String uSE_YN) {
		USE_YN = uSE_YN;
	}

	public String getMENU_GRP_CD() {
		return MENU_GRP_CD;
	}

	public void setMENU_GRP_CD(String mENU_GRP_CD) {
		MENU_GRP_CD = mENU_GRP_CD;
	}
    
    
}
