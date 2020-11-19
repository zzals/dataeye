package kr.co.penta.dataeye.spring.web.common.service;

import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.common.entities.JqGridEntity;
import kr.co.penta.dataeye.common.entities.meta.PenCdEntity;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;

public interface GridService {
    JqGridEntity getJqGridList(Map<String, Object> reqParam, SessionInfo sessionInfo);
}
