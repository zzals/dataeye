package kr.co.penta.dataeye.common.entities.meta;

import java.util.UUID;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.util.TimeUtils;

/**
 * Created by Administrator on 2014-09-04.
 */
public class PenHstEntity extends CamelMap {
    /**
     * 
     */
    private static final long serialVersionUID = 679793118679808538L;
    
    public PenHstEntity() {
        super();
        put("HST_SEQ", TimeUtils.getInstance().formatDate(TimeUtils.getInstance().getCurrentTimestamp(), "yyyyMMddHHmmssSSS")+"_"+UUID.randomUUID().toString());
    }

    public void init(String objTypeId, String objId, String reqSeq) {
        put("objTypeId", objTypeId);
        put("objId", objId);
        put("reqSeq", reqSeq);
    }
    public String getObjTypeId() {
        return getString("objTypeId");
    }

    public String getObjId() {
        return getString("objId");
    }

    public String getHstSeq() {
        return getString("hstSeq");
    }

    public String getHstCretDt() {
        return getString("hstCretDt");
    }

    public String getHstCretrId() {
        return getString("hstCretrId");
    }

    public String getReqSeq() {
        return getString("reqSeq");
    }
}