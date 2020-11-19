package kr.co.penta.dataeye.spring.web.admin.service;

import java.util.List;
import java.util.Map;

import kr.co.penta.dataeye.spring.web.session.SessionInfo;

/**
 * Created by Administrator on 2016-11-30.
 */
public interface AdminObjService {
    void delete(List<Map<String, Object>> datas, SessionInfo sessionInfo);
    void remove(List<Map<String, Object>> datas, SessionInfo sessionInfo);
}
