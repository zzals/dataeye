package kr.co.penta.dataeye.common.entities;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.base.CaseFormat;

import kr.co.penta.dataeye.common.util.CastUtils;


public class CamelMap extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = -5373693292544046720L;

    public CamelMap() {
        super();
    }
    
    public CamelMap(Map<String, Object> map) {
        putAll(map);
    }
    
    public String getString(Object key) {
        return CastUtils.toString(get(key));
    }
    
    public Integer getInteger(Object key) {
        return CastUtils.toInteger(get(key));
    }
    
    @Override
    public Object get(Object key) {
        return super.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        String camelKey = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key);
        super.put(camelKey, value);
        
        return super.put(key, value);
    }
    
    public Object putOri(String key, Object value) {
        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends String, ? extends Object> m) {
        Set<? extends String> keySet = m.keySet();
        for(String key : keySet) {
            put(key, m.get(key));
        }
        super.putAll(m);
    }
}
