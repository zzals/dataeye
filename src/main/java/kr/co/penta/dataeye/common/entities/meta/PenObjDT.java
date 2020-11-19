package kr.co.penta.dataeye.common.entities.meta;

import java.util.Map;

import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
/**
 * Created by Administrator on 2014-09-04.
 */
public class PenObjDT  extends DaoParam {
    /**
     * 
     */
    private static final long serialVersionUID = -620182293720953906L;

    public PenObjDT() {
        put("timestampFormat", DataEyeSettingsHolder.getInstance().getConfig().getDbTimestampFormat());
        setDelYn("N");
    }
    
    public PenObjDT(PenObjMT penObjMT) {
        this();
        setObjTypeId(penObjMT.getObjTypeId());
        setObjId(penObjMT.getObjId());

        setKeyObjId(penObjMT.getKeyObjId());
        setKeyObjTypeId(penObjMT.getKeyObjTypeId());

        setAprvId(penObjMT.getAprvId());
        setDelYn(penObjMT.getDelYn());
        setCretDt(penObjMT.getCretDt());
        setCretrId(penObjMT.getCretrId());
        setChgDt(penObjMT.getChgDt());
        setChgrId(penObjMT.getChgrId());
        putSessionInfo(penObjMT.getSessionInfo());
    }

    public PenObjDT(PenObjMT penObjMT, Map camelMap) {
        this(penObjMT);
        
        /*setAtrIdSeq(camelMap.getInteger("atrIdSeq"));
        setAtrValSeq(camelMap.getInteger("atrValSeq"));
        setObjAtrVal(camelMap.get("objAtrVal"));
        setCnctAtrYn(camelMap.getString("cnctAtrYn"));
        setUiCompCd(camelMap.getString("uiCompCd"));*/
        
        setAtrIdSeq(Integer.parseInt(camelMap.get("ATR_ID_SEQ").toString()));
        setAtrValSeq(Integer.parseInt(camelMap.get("ATR_VAL_SEQ").toString()));
        setObjAtrVal(camelMap.get("OBJ_ATR_VAL"));
        //setCnctAtrYn(camelMap.get("CNCTATRYN").toString());
        //setUiCompCd(camelMap.get("UI_COMP_CD").toString());
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

    public String getKeyObjId() {
        return getString("keyObjId");
    }

    public void setKeyObjId(String keyObjId) {
        put("KEY_OBJ_ID", keyObjId);
    }

    public String getKeyObjTypeId() {
        return getString("keyObjTypeId");
    }

    public void setKeyObjTypeId(String keyObjTypeId) {
        put("KEY_OBJ_TYPE_ID", keyObjTypeId);
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

    public String getAprvId() {
        return getString("aprvId");
    }

    public void setAprvId(String aprvId) {
        put("APRV_ID", aprvId);
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
