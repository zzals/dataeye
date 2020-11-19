package kr.co.penta.dataeye.spring.web.validate;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SysLogForm {
	
    @JsonProperty("CRET_DT")
    private String CRET_DT;    
    
    @JsonProperty("CRETR_ID")
    private String CRETR_ID;    
    
    @Size(min=1)
    @JsonProperty("APP_ID")
    private String APP_ID;    
    
    @Size(min=1)
    @JsonProperty("USER_ID")
    private String USER_ID;   
    
    @Size(min=1)
    @JsonProperty("SESS_ID")
    private String SESS_ID;    
    
    
    @JsonProperty("USER_IP")
    private String USER_IP;    
 
    @JsonProperty("SRV_IP")
    private String SRV_IP;    
    
    @JsonProperty("SRV_PORT")
    private String SRV_PORT;
    
    @Size(min=1)
    @JsonProperty("MENU_ID")
    private String MENU_ID;
    
    @Size(min=1)
    @JsonProperty("MENU_NM")
    private String MENU_NM;
    
    @JsonProperty("URL")
    private String URL;
    
    @JsonProperty("VIEW_ID")
    private String VIEW_ID;
    
    @JsonProperty("QUERY_STR")
    private String QUERY_STR;
    
    @JsonProperty("REQ_DT")
    private String REQ_DT;
    
    @JsonProperty("REFE_INFO")
    private String REFE_INFO;
    
    @JsonProperty("HTTP_STATUS")
    private String HTTP_STATUS;
    

    @JsonProperty("END_DT")
    private String END_DT;
       
    @JsonProperty("RSLT_CD")
    private String RSLT_CD;
    
    @JsonProperty("RSLT_DESC")
    private String RSLT_DESC;
       
    @JsonProperty("CONN_TYPE")
    private String CONN_TYPE;
    
    @JsonProperty("LOGIN_TYPE")
    private String LOGIN_TYPE;
    
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

    

    public String getAPP_ID() {
        return APP_ID;
    }

    public void setAPP_ID(String aPP_ID) {
        APP_ID = aPP_ID;
    }
    
    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String uSER_ID) {
    	USER_ID = uSER_ID;
    }

    public String getSessId() {
        return SESS_ID;
    }

    public void setSESS_ID(String sESS_ID) {
    	SESS_ID = sESS_ID;
    }


    public String getUSER_IP() {
        return USER_IP;
    }

    public void setUSER_IP(String uSER_IP) {
    	USER_IP = uSER_IP;
    }

    public String getSRV_IP() {
        return SRV_IP;
    }

    public void setSRV_IP(String sRV_IP) {
    	SRV_IP = sRV_IP;
    }

    public String getSRV_PORT() {
        return SRV_PORT;
    }

    public void setSRV_PORT(String sRV_PORT) {
    	SRV_IP = sRV_PORT;
    }

    public String getMENU_ID() {
        return MENU_ID;
    }

    public void setMENU_ID(String mENU_ID) {
    	MENU_ID = mENU_ID;
    }
    
    public String getMENU_NM() {
        return MENU_NM;
    }

    public void setMENU_NM(String mENU_NM) {
    	MENU_ID = mENU_NM;
    }
    
    
    public String getURL() {
        return URL;
    }

    public void setURL(String uRL) {
    	URL = uRL;
    }

    public String getVIEW_ID() {
        return VIEW_ID;
    }
    public void setVIEW_ID(String vIEW_ID) {
    	VIEW_ID = vIEW_ID;
    }
    
    
    public String getQUERY_STR() {
        return QUERY_STR;
    }

    public void setQUERY_STR(String qUERY_STR) {
    	QUERY_STR = qUERY_STR;
    }
    
    public String getREQ_DT() {
        return REQ_DT;
    }

    public void setREQ_DT(String rEQ_DT) {
    	REQ_DT = rEQ_DT;
    }
    
    public String getREFE_INFO() {
        return REFE_INFO;
    }

    public void setREFE_INFO(String rEFE_INFO) {
    	REFE_INFO = rEFE_INFO;
    }
    
    public String getHTTP_STATUS() {
        return HTTP_STATUS;
    }

    public void setHTTP_STATUS(String hTTP_STATUS) {
    	HTTP_STATUS = hTTP_STATUS;
    }
    
    
    public String getEND_DT() {
        return END_DT;
    }

    public void setEND_DT(String eND_DT) {
    	END_DT = eND_DT;
    }
    
    public String getRSLT_CD() {
        return RSLT_CD;
    }

    public void setRSLT_CD(String rSLT_CD) {
    	RSLT_CD = rSLT_CD;
    }
    
    public String getRSLT_DESC() {
        return RSLT_DESC;
    }

    public void setRSLT_DESC(String rSLT_DESC) {
    	RSLT_DESC = rSLT_DESC;
    }
    
    public String getCONN_TYPE() {
        return CONN_TYPE;
    }

    public void setCONN_TYPE(String cONN_TYPE) {
    	CONN_TYPE = cONN_TYPE;
    }
    
    public String getLOGIN_TYPE() {
        return LOGIN_TYPE;
    }

    public void setLOGIN_TYPE(String lOGIN_TYPE) {
    	LOGIN_TYPE = lOGIN_TYPE;
    }
    

}
