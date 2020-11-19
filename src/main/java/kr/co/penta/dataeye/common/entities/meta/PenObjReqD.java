package kr.co.penta.dataeye.common.entities.meta;

import java.util.Map;
import java.util.UUID;

import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.common.util.TimeUtils;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;

/**
 * Created by Administrator on 2014-09-04.
 */
public class PenObjReqD extends DaoParam {
    /**
     * 
     */
    private static final long serialVersionUID = 679793118679808538L;

    public PenObjReqD(SessionInfo sessionInfo) {
        put("timestampFormat", DataEyeSettingsHolder.getInstance().getConfig().getDbTimestampFormat());
        put("DEL_YN", "N");
        putSessionInfo(sessionInfo);
    }
    
    public PenObjReqD(Map<String, Object> map, SessionInfo sessionInfo) {
        put("timestampFormat", DataEyeSettingsHolder.getInstance().getConfig().getDbTimestampFormat());
        put("DEL_YN", "N");
        putAll(map);
        putSessionInfo(sessionInfo);
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

    public String getReqId() {
        return getString("reqId");
    }

    public void setReqId(String reqId) {
        put("REQ_ID", reqId);
    }

    public String getStusId() {
        return getString("stusId");
    }

    public void setStusId(String stusId) {
        put("STUS_ID", stusId);
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

    public String getStusCd() {
        return getString("stusCd");
    }

    public void setStusCd(String stusCd) {
        put("STUS_CD", stusCd);
    }

    public String getStusUserId() {
        return getString("stusUserId");
    }

    public void setStusUserId(String stusUserId) {
        put("OBJ_NM", stusUserId);
    }

    public String getStusDesc() {
        return getString("stusDesc");
    }

    public void setStusDesc(String stusDesc) {
        put("STUS_DESC", stusDesc);
    }

    public String getStusDt() {
        return getString("stusDt");
    }

    public void setStusDt(String stusDt) {
        put("STUS_DT", stusDt);
    }

    public String getStusLinkId() {
        return getString("StusLinkId");
    }

    public void setStusLikId(String stusLinkId) {
        put("STUS_LINK_ID", stusLinkId);
    }

    public void genObjId() {
        put("OBJ_ID", getObjTypeId()+"_"+UUID.randomUUID());
    }

    public void genReqId() {
        put("REQ_ID", TimeUtils.getInstance().formatDate(TimeUtils.getInstance().getCurrentTimestamp(), "yyyyMMddHHmmssSSS")+"_"+UUID.randomUUID().toString());
    }

    public void genStusId() {
        put("STUS_ID", TimeUtils.getInstance().formatDate(TimeUtils.getInstance().getCurrentTimestamp(), "yyyyMMddHHmmssSSS")+"_"+UUID.randomUUID().toString());
    }
}