package kr.co.penta.dataeye.spring.web.validate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationForm {
    @NotNull()
    @Size(min=1)
    @JsonProperty("APP_ID")
    private String APP_ID;
    
    @NotNull()
    @Size(min=1)
    @JsonProperty("APP_NM")
    private String APP_NM;
    
    @JsonProperty("APP_DESC")
    private String APP_DESC;
    
    @Size(min=1, max=1)
    @JsonProperty("DEL_YN")
    private String DEL_YN;
    
    @Size(min=1, max=1)
    @JsonProperty("USE_YN")
    private String USE_YN;

    public String getAPP_ID() {
        return APP_ID;
    }

    public void setAPP_ID(String aPP_ID) {
        APP_ID = aPP_ID;
    }

    public String getAPP_NM() {
        return APP_NM;
    }

    public void setAPP_NM(String aPP_NM) {
        APP_NM = aPP_NM;
    }

    public String getAPP_DESC() {
        return APP_DESC;
    }

    public void setAPP_DESC(String aPP_DESC) {
        APP_DESC = aPP_DESC;
    }

    public String getDEL_YN() {
        return DEL_YN;
    }

    public void setDEL_YN(String dEL_YN) {
        DEL_YN = dEL_YN;
    }

    public String getUSE_YN() {
        return USE_YN;
    }

    public void setUSE_YN(String uSE_YN) {
        USE_YN = uSE_YN;
    }
}
