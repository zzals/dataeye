package kr.co.penta.dataeye.spring.web.validate;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CdGrpForm {
    @Size(min=1)
     @JsonProperty("CD_GRP_ID")
    private String CD_GRP_ID;
    
    @Size(min=1, max=1)
    @JsonProperty("DEL_YN")
    private String DEL_YN;
    
    @Size(min=1)
    @JsonProperty("CD_GRP_NM")
    private String CD_GRP_NM;
    
    @JsonProperty("CD_GRP_DESC")
    private String CD_GRP_DESC;
    
    @JsonProperty("UP_CD_GRP_ID")
    private String UP_CD_GRP_ID;
    
    @JsonProperty("EFCT_ST_DATE")
    private String EFCT_ST_DATE;
    
    @JsonProperty("EFCT_ED_DATE")
    private String EFCT_ED_DATE;
    
    public String getCD_GRP_ID() {
        return CD_GRP_ID;
    }

    public void setCD_GRP_ID(String cdGrpId) {
        CD_GRP_ID = cdGrpId;
    }

    public String getDEL_YN() {
        return DEL_YN;
    }

    public void setDEL_YN(String delYn) {
        DEL_YN = delYn;
    }

    public String getCD_GRP_NM() {
        return CD_GRP_NM;
    }

    public void setCD_GRP_NM(String cdGrpNm) {
        CD_GRP_NM = cdGrpNm;
    }

    public String getCD_GRP_DESC() {
        return CD_GRP_DESC;
    }

    public void setCD_GRP_DESC(String cdGrpDesc) {
        CD_GRP_DESC = cdGrpDesc;
    }

    public String getUP_CD_GRP_ID() {
        return UP_CD_GRP_ID;
    }

    public void setUP_CD_GRP_ID(String upCdGrpId) {
        UP_CD_GRP_ID = upCdGrpId;
    }

    public String getEFCT_ST_DATE() {
        return EFCT_ST_DATE;
    }

    public void setEFCT_ST_DATE(String efctStDate) {
        EFCT_ST_DATE = efctStDate;
    }

    public String getEFCT_ED_DATE() {
        return EFCT_ED_DATE;
    }

    public void setEFCT_ED_DATE(String efctEdDate) {
        EFCT_ED_DATE = efctEdDate;
    }
}
