package kr.co.penta.dataeye.common.util;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CastUtils {
    private static Logger LOG = LoggerFactory.getLogger(CastUtils.class);

    public static Integer toInteger(Object o) {
        try {
            if (o == null) {
                return null;
            } else if (o instanceof BigDecimal) {
                return ((BigDecimal)o).intValue();
            } else if (o instanceof Double) {
                return ((Double)o).intValue();
            } else if (o instanceof Float) {
                return ((Float)o).intValue();
            } else if (o instanceof Long) {
                return ((Long)o).intValue();
            } else if (o instanceof Integer) {
                return (Integer)o;
            } else {
                return Integer.valueOf((String)o);
            }
        } catch (Exception e) {
            LOG.error("toInteger error : {}", e);
            return null;
        }
    }
    
    public static Integer toInteger(Object o, int defaultInt) {
        Integer v = toInteger(o);
        if (v == null) {
        	v = 0;
        }
        return defaultInt;
    }
    
    public static String toString(Object o) {
        if (o == null) {
            return "";
        } else if (o instanceof BigDecimal) {
            return ((BigDecimal)o).toString();
        } else if (o instanceof Integer) {
            return ((Integer)o).toString();
        } else if (o instanceof Long) {
            return ((Long)o).toString();
        } else if (o instanceof Double) {
            return ((Double)o).toString();
        } else {
            return (String)o;
        }
        
    }

    public static String toString(Object o, String defaultValue) {
        String v = toString(o);
        if (v == null || "".equals(v)) v = defaultValue;
        return defaultValue;
    }
    
    public static String toString(Object o, boolean isTrim) {
        String s = "";
        if (o == null) {
            s = "";
        } else if (o instanceof BigDecimal) {
            s = ((BigDecimal)o).toString();
        } else {
            s = (String)o;
        }
        
        if (isTrim) {
            s = s.trim();
        }
        return s;
    }
    
    public static Boolean toBoolean(Object o) {
        if (o == null) {
            return false;
        } else if (o instanceof Boolean) {
            return (Boolean)o;
        } else if (o instanceof String) {
            return "true".equals(o.toString());
        } else {
            return false;
        }
    }
    
    public static Map<String, Object> toMap(Object o) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.convertValue(o, Map.class);
        } catch (Exception e) {
            LOG.error("casting error", e);
            return new HashMap<>();
        }
    }
}
