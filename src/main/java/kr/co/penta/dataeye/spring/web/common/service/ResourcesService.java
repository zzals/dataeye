package kr.co.penta.dataeye.spring.web.common.service;

import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.common.entities.meta.PenCdEntity;

public interface ResourcesService {
    List<PenCdEntity> findAllCdByGroup();
    List<Map<String, Object>> getObjTypeInfo();
}
