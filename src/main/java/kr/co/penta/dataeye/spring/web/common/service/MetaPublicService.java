package kr.co.penta.dataeye.spring.web.common.service;

import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.common.entities.meta.PenObjD;

public interface MetaPublicService {
    List<Map<String, Object>> getObjTypeTree();
    List<Map<String, Object>> getAtrSbstRslt(String objTypeId, Integer atrIdSeq);
    List<Map<String, Object>> getList(Map<String,Object> reqParam);
    Map<String, Object> getMap(Map<String,Object> reqParam);
    List<Map<String, Object>> getOrgTree();
    List<Map<String, Object>> getObjUIInfo(String objTypeId);
    List<PenObjD> getObjAtrVal(String objTypeId, String objId, Integer atrIdSeq, Integer atrValSeq);
}
