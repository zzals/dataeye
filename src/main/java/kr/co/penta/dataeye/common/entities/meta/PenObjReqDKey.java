package kr.co.penta.dataeye.common.entities.meta;

import kr.co.penta.dataeye.common.entities.CamelMap;

/**
 * Created by Administrator on 2014-09-04.
 */
public class PenObjReqDKey extends CamelMap {
    public PenObjReqDKey(PenObjReqD penObjReqD) {
        setObjTypeId(penObjReqD.getObjTypeId());
        setObjId(penObjReqD.getObjId());
        setReqId(penObjReqD.getReqId());
        setStusId(penObjReqD.getStusId());
    }
    
    public PenObjReqDKey(String objTypeId, String objId, String reqId, String stusId) {
        setObjTypeId(objTypeId);
        setObjId(objId);
        setReqId(reqId);
        setStusId(stusId);
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
}