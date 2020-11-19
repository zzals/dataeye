package kr.co.penta.dataeye.spring.web.validate;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SysUserGrpMForm {
    @Size(min=1)
    @JsonProperty("USER_GRP_ID")
    private String USER_GRP_ID;    
    
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
    @JsonProperty("USER_GRP_NM")
    private String USER_GRP_NM;
    
    @JsonProperty("USER_GRP_DESC")
    private String USER_GRP_DESC;
    
    @Size(min=1)
    @JsonProperty("USER_GRP_TYPE")
    private String USER_GRP_TYPE;
    
    @Size(min=1)
    @JsonProperty("PRIV_YN")
    private String PRIV_YN;
    
    @Size(min=1)
    @JsonProperty("USE_YN")
    private String USE_YN;

    public String getUSER_GRP_ID() {
        return USER_GRP_ID;
    }

    public void setUSER_GRP_ID(String uSER_GRP_ID) {
        USER_GRP_ID = uSER_GRP_ID;
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

    public String getUSER_GRP_NM() {
        return USER_GRP_NM;
    }

    public void setUSER_GRP_NM(String uSER_GRP_NM) {
        USER_GRP_NM = uSER_GRP_NM;
    }

    public String getUSER_GRP_DESC() {
        return USER_GRP_DESC;
    }

    public void setUSER_GRP_DESC(String uSER_GRP_DESC) {
        USER_GRP_DESC = uSER_GRP_DESC;
    }

    public String getUSER_GRP_TYPE() {
        return USER_GRP_TYPE;
    }

    public void setUSER_GRP_TYPE(String uSER_GRP_TYPE) {
        USER_GRP_TYPE = uSER_GRP_TYPE;
    }

    public String getPRIV_YN() {
        return PRIV_YN;
    }

    public void setPRIV_YN(String pRIV_YN) {
        PRIV_YN = pRIV_YN;
    }

    public String getUSE_YN() {
        return USE_YN;
    }

    public void setUSE_YN(String uSE_YN) {
        USE_YN = uSE_YN;
    }
}
