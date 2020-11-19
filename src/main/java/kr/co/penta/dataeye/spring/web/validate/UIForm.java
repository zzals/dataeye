package kr.co.penta.dataeye.spring.web.validate;

import java.util.UUID;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UIForm {
    @JsonProperty("UI_ID")
    private String UI_ID;
    
    @Size(min=1)
    @JsonProperty("UI_NM")
    private String UI_NM;
    
    @Size(min=1, max=1)
    @JsonProperty("DEL_YN")
    private String DEL_YN;
    
    @Size(min=1)
    @JsonProperty("USE_TYPE_CD")
    private String USE_TYPE_CD;
    
    @Size(min=1)
    @JsonProperty("UI_TYPE_CD")
    private String UI_TYPE_CD;
    
    @Size(min=1)
    @JsonProperty("PGM_ID")
    private String PGM_ID;
    
    @JsonProperty("CONF_SBST")
    private String CONF_SBST;
    
    public String getUI_ID() {
        return UI_ID;
    }

    public void setUI_ID(String uiId) {
        UI_ID = uiId;
    }
    
    public void genUI_ID() {
        UI_ID = "UI_"+UUID.randomUUID();
    }

    public String getUI_NM() {
        return UI_NM;
    }

    public void setUI_NM(String uiNm) {
        UI_NM = uiNm;
    }

    public String getDEL_YN() {
        return DEL_YN;
    }

    public void setDEL_YN(String delYn) {
        DEL_YN = delYn;
    }

    public String getUSE_TYPE_CD() {
        return USE_TYPE_CD;
    }

    public void setUSE_TYPE_CD(String useTypeCd) {
        USE_TYPE_CD = useTypeCd;
    }

    public String getUI_TYPE_CD() {
        return UI_TYPE_CD;
    }

    public void setUI_TYPE_CD(String uiTypeCd) {
        UI_TYPE_CD = uiTypeCd;
    }

    public String getPGM_ID() {
        return PGM_ID;
    }

    public void setPGM_ID(String pgmId) {
        PGM_ID = pgmId;
    }

    public String getCONF_SBST() {
        return CONF_SBST;
    }

    public void setCONF_SBST(String confSbst) {
        CONF_SBST = confSbst;
    }
}
