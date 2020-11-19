package kr.co.penta.dataeye.spring.mybatis.dao;

import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.entities.meta.AtrInfoEntity;
import kr.co.penta.dataeye.common.entities.meta.PenCdEntity;
import kr.co.penta.dataeye.common.entities.meta.PenObjD;
import kr.co.penta.dataeye.common.entities.meta.PenObjTypeM;

public interface MetaPublicDao {
    List<Map<String, Object>> getObjTypeTree();
    CamelMap getAtr(String objTypeId, Integer atrIdSeq);
    List<Map<String, Object>> getOrgTree();
    List<Map<String, Object>> getObjUIInfo(String objTypeId);
    List<PenObjD> getObjAtrVal(String objTypeId, String objId, Integer atrIdSeq, Integer atrValSeq);
}
