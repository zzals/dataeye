package kr.co.penta.dataeye.spring.mybatis.dao.param;

import java.util.Map;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;

public class DaoParam extends CamelMap {
    private static final long serialVersionUID = 3651458589924945108L;

    public DaoParam() {
        super();
    }

    public DaoParam(SessionInfo sessionInfo) {
        put("sessionInfo", sessionInfo);
    }
    
    public DaoParam(Map<String, Object> params, SessionInfo sessionInfo) {
        this.putAll(params);
        this.put("sessionInfo", sessionInfo);
    }
    
    public DaoParam(Map<String, Object> params) {
        this.putAll(params);
    }
    
    @Override
    public DaoParam put(String key, Object value) {
        super.put(key, value);
        return this;
    }
    
    public DaoParam putSessionInfo(SessionInfo sessionInfo) {
        this.put("sessionInfo", sessionInfo);
        return this;
    }
    
    public SessionInfo getSessionInfo() {
        return (SessionInfo)this.get("sessionInfo");
    }
}
