package kr.co.penta.dataeye.spring.mybatis.dao.param;

import java.util.Map;

import kr.co.penta.dataeye.common.entities.JqGridFilters;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;

public class JqGridDaoParam extends DaoParam {
    private static final long serialVersionUID = 3651458589924945108L;
    
    private Integer page;
    private Integer rows;
    private String sidx = "1";    /* order column */
    private String sord = "asc";    /* sort type: asc, desc */
    
    private JqGridFilters jqGridFilters;
    
    public JqGridDaoParam() {
        super();
        this.page = 1;
    }
    
    public JqGridDaoParam(Map<String, Object> params) {
        this(params, null);
    }
    
    public JqGridDaoParam(Map<String, Object> params, SessionInfo sessionInfo) {
        super(params, sessionInfo);
        
        if ("".equals(CastUtils.toString(this.get("sidx")))) {
            this.put("sidx", sidx);
        }
        
        
        if (params.get("filters") != null && !"".equals(params.get("filters"))) {
            this.jqGridFilters = new JqGridFilters((String)params.get("filters"));
        }
    }

    @Override
    public JqGridDaoParam put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public Integer getPage() {
        return CastUtils.toInteger(this.get("page"));
    }
    
    public Integer getRows() {
        return CastUtils.toInteger(this.get("rows"));
    }
    
    public JqGridFilters getJgGridFilters() {
        return jqGridFilters;
    }
    
    public boolean isFilters() {
        if (jqGridFilters == null) {
            return false;
        } else {
            return true;
        }
    }
}
