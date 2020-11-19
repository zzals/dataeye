package kr.co.penta.dataeye.spring.web.validate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AtrForm {
    @Size(min=1)
    @JsonProperty("ATR_ID")
    private String ATR_ID;
    
    @Size(min=1)
    @JsonProperty("DEL_YN")
    private String DEL_YN;
    
    @Size(min=1)
    @JsonProperty("ATR_NM")
    private String ATR_NM;
    
    @JsonProperty("ATR_DESC")
    private String ATR_DESC;
    
    @Size(min=1)
    @JsonProperty("DATA_TYPE_CD")
    private String DATA_TYPE_CD;

    @NotNull
    @JsonProperty("COL_LEN")
    private Integer COL_LEN;
    
    @Size(min=1)
    @JsonProperty("ATR_RFRN_CD")
    private String ATR_RFRN_CD;
    
    @JsonProperty("SQL_SBST")
    private String SQL_SBST;
    
    @Size(min=1)
    @JsonProperty("UI_COMP_CD")
    private String UI_COMP_CD;
    
    @JsonProperty("UI_COMP_WIDTH_RADIO")
    private String UI_COMP_WIDTH_RADIO;    

    @JsonProperty("UI_COMP_HEIGHT_PX")
    private Integer UI_COMP_HEIGHT_PX;
    
    public String getATR_ID() {
        return ATR_ID;
    }

    public void setATR_ID(String atrId) {
        ATR_ID = atrId;
    }
    public String getDEL_YN() {
        return DEL_YN;
    }

    public void setDEL_YN(String delYn) {
        DEL_YN = delYn;
    }
    
    public String getATR_NM() {
        return ATR_NM;
    }

    public void setATR_NM(String atrNm) {
        ATR_NM = atrNm;
    }

    public String getATR_DESC() {
        return ATR_DESC;
    }

    public void setATR_DESC(String atrDesc) {
        ATR_DESC = atrDesc;
    }

    public String getDATA_TYPE_CD() {
        return DATA_TYPE_CD;
    }

    public void setDATA_TYPE_CD(String dataTypeCd) {
        DATA_TYPE_CD = dataTypeCd;
    }

    public Integer getCOL_LEN() {
        return COL_LEN;
    }

    public void setCOL_LEN(Integer colLen) {
        COL_LEN = colLen;
    }

    public String getATR_RFRN_CD() {
        return ATR_RFRN_CD;
    }

    public void setATR_RFRN_CD(String atrRfrnCd) {
        ATR_RFRN_CD = atrRfrnCd;
    }

    public String getSQL_SBST() {
        return SQL_SBST;
    }

    public void setSQL_SBST(String sqlSbst) {
        SQL_SBST = sqlSbst;
    }

    public String getUI_COMP_CD() {
        return UI_COMP_CD;
    }

    public void setUI_COMP_CD(String uiCompCd) {
        UI_COMP_CD = uiCompCd;
    }

    public String getUI_COMP_WIDTH_RADIO() {
        return UI_COMP_WIDTH_RADIO;
    }

    public void setUI_COMP_WIDTH_RADIO(String uiCompWidthRadio) {
        UI_COMP_WIDTH_RADIO = uiCompWidthRadio;
    }

    public Integer getUI_COMP_HEIGHT_PX() {
        return UI_COMP_HEIGHT_PX;
    }

    public void setUI_COMP_HEIGHT_PX(Integer uiCompHeightPx) {
        UI_COMP_HEIGHT_PX = uiCompHeightPx;
    }
}
