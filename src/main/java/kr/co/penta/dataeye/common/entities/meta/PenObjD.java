package kr.co.penta.dataeye.common.entities.meta;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
/**
 * Created by Administrator on 2014-09-04.
 */
public class PenObjD  extends DaoParam {
    /**
     * 
     */
    private static final long serialVersionUID = -620182293720953906L;

    public PenObjD() {
        put("timestampFormat", DataEyeSettingsHolder.getInstance().getConfig().getDbTimestampFormat());
        put("DEL_YN", "N");
    }
    
    public PenObjD(PenObjM penObjM) {
        this();
        setObjTypeId(penObjM.getObjTypeId());
        setObjId(penObjM.getObjId());
        setDelYn(penObjM.getDelYn());
        setCretDt(penObjM.getCretDt());
        setCretrId(penObjM.getCretrId());
        setChgDt(penObjM.getChgDt());
        setChgrId(penObjM.getChgrId());
        putSessionInfo(penObjM.getSessionInfo());
    }

    public PenObjD(PenObjM penObjM, CamelMap camelMap) {
        this(penObjM);
        
        setAtrIdSeq(camelMap.getInteger("atrIdSeq"));
        setAtrValSeq(camelMap.getInteger("atrValSeq"));
        setObjAtrVal(camelMap.get("objAtrVal"));
        setCnctAtrYn(camelMap.getString("cnctAtrYn"));
        setUiCompCd(camelMap.getString("uiCompCd"));
    }
    
    public String getObjTypeId() {
        return getString("objTypeId");
    }

    public void setObjTypeId(String objTypeId) {
        put("OBJ_TYPE_ID", objTypeId);
    }

    public String getObjId() {
        return getString("objId");
    }

    public void setObjId(String objId) {
        put("OBJ_ID", objId);
    }

    public Integer getAtrIdSeq() {
        return getInteger("atrIdSeq");
    }

    public void setAtrIdSeq(Integer atrIdSeq) {
        put("ATR_ID_SEQ", atrIdSeq);
    }

    public Integer getAtrValSeq() {
        return getInteger("atrValSeq");
    }

    public void setAtrValSeq(Integer atrValSeq) {
        put("ATR_VAL_SEQ", atrValSeq);
    }
    
    public String getDelYn() {
        return getString("delYn");
    }

    public void setDelYn(String delYn) {
        put("DEL_YN", delYn);
    }

    public String getCretDt() {
        return getString("cretDt");
    }

    public void setCretDt(String cretDt) {
        put("CRET_DT", cretDt);
    }

    public String getCretrId() {
        return getString("cretrId");
    }

    public void setCretrId(String cretrId) {
        put("CRETR_ID", cretrId);
    }

    public String getChgDt() {
        return getString("chgDt");
    }

    public void setChgDt(String chgDt) {
        put("CHG_DT", chgDt);
    }

    public String getChgrId() {
        return getString("chgrId");
    }

    public void setChgrId(String chgrId) {
        put("CHGR_ID", chgrId);
    }

    public String getObjAtrVal() {
        return getString("objAtrVal");
    }

    public void setObjAtrVal(Object objAtrVal) {
        put("OBJ_ATR_VAL", objAtrVal);
    }

    public String getCnctAtrYn() {
        return getString("cnctAtrYn");
    }

    public void setCnctAtrYn(String cnctAtrYn) {
        put("CNCT_ATR_YN", cnctAtrYn);
    }

    public String getUiCompCd() {
        return getString("uiCompCd");
    }

    public void setUiCompCd(String uiCompCd) {
        put("UI_COMP_CD", uiCompCd);
    }
}
