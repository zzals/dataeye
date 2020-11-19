package kr.co.penta.dataeye.spring.web.validate;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SysUserMForm {
    @Size(min=1)
    @JsonProperty("USER_ID")
    private String USER_ID;    
    
    @Size(min=1)
    @JsonProperty("USER_PASSWORD")
    private String USER_PASSWORD;   
    
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
    
    @Size(min=1)
    @JsonProperty("USER_NM")
    private String USER_NM;
    
    @Size(min=1)
    @JsonProperty("ORG_ID")
    private String ORG_ID;
    
    @JsonProperty("ORG_NM")
    private String ORG_NM;
    
    @JsonProperty("EMAIL_ADDR")
    private String EMAIL_ADDR;
    
    @JsonProperty("TEL_NO")
    private String TEL_NO;
    
    @JsonProperty("HP_NO")
    private String HP_NO;
    

    

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String uSER_ID) {
        USER_ID = uSER_ID;
    }
    
    public String getUSER_PASSWORD() {
        return USER_PASSWORD;
    }

    public void setUSER_PASSWORD(String uSER_PASSWORD) {
    	USER_PASSWORD = uSER_PASSWORD;
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

    public String getUSER_NM() {
        return USER_NM;
    }

    public void setUSER_NM(String uSER_NM) {
        USER_NM = uSER_NM;
    }

    public String getORG_ID() {
        return ORG_ID;
    }

    public void setORG_ID(String oRG_ID) {
    	ORG_ID = oRG_ID;
    }
    
    public String getORG_NM() {
        return ORG_NM;
    }

    public void setORG_NM(String oRG_NM) {
    	ORG_NM = oRG_NM;
    }
    
    public String getEMAIL_ADDR() {
        return EMAIL_ADDR;
    }

    public void setEMAIL_ADDR(String eMAIL_ADDR) {
    	EMAIL_ADDR = eMAIL_ADDR;
    }

    public String getTEL_NO() {
        return TEL_NO;
    }
    public void setTEL_NO(String tEL_NO) {
        TEL_NO = tEL_NO;
    }

    public void setHP_NO(String hP_NO) {
        HP_NO = hP_NO;
    }

    public String getHP_NO() {
        return HP_NO;
    }


}
