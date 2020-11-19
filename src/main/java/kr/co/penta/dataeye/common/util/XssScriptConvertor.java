package kr.co.penta.dataeye.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.spring.web.validate.AtrForm;

public class XssScriptConvertor {
    private static Logger LOG = LoggerFactory.getLogger(XssScriptConvertor.class);

    private final String[] WHITE_LIST = {"&", "#", "<", ">", "(", ")"};
    private final String[] WHITE_CONV_LIST = {"&amp;", "&#35;", "&lt;", "&gt;", "&#40;", "&#41;"};
    
    private static final XssScriptConvertor xssScriptConvertor = new XssScriptConvertor();
    
    public static XssScriptConvertor getInstance() {
       return xssScriptConvertor;
    }
    
    private String translate(String value) {
        System.out.println(value);
        for(int i=0; i<WHITE_LIST.length; i++) {
            value = value.replace(WHITE_LIST[i], WHITE_CONV_LIST[i]);
        }
        
        return value;
    }
    
    public void convertObject(Object obj) {
        if (obj instanceof String) {
            
        }
        Field[] fiedls = obj.getClass().getDeclaredFields();
        
        for(Field field : fiedls) {
            if (field.getType().toString().equals("class java.lang.String")) {
                field.setAccessible(true);
                try {
                    String s = (String)field.get(obj);
                    field.set(obj, translate(s));
                } catch (Exception e) {}
            }
        }
    };
    
    public void convertPenObjM(Map map) {
        if (map == null) return;
        
        Set<String> set = map.keySet();
        for(String key : set) {
            if ("OBJ_DESC".equals(key)) continue;
            
            Object obj = map.get(key);
            
            if (obj instanceof String) {
                map.put(key, translate((String)obj));
            }
        }
    }
    
    public void convertPenObjD(Map map) {
        if (map == null) return;
        
        Set<String> set = map.keySet();
        for(String key : set) {
            Object obj = map.get(key);
            
            if (obj instanceof String) {
                map.put(key, translate((String)obj));
            }
        }
    }
    
    public void convertPenObjR(Map map) {
        if (map == null) return;
        
        Set<String> set = map.keySet();
        for(String key : set) {
            Object obj = map.get(key);
            
            if (obj instanceof String) {
                map.put(key, translate((String)obj));
            }
        }
    }
    
    public void convertMap(Map map) {
        if (map == null) return;

        Set<String> set = map.keySet();
        for(String key : set) {
            Object obj = map.get(key);
            
            if (obj instanceof List) {
               convertList((List)obj);
            } else if (obj instanceof Map) {
                convertMap((Map)obj);
            } else if (obj instanceof String) {
                if (map.getClass().getSimpleName().equals("CamelMap")) {
                    ((CamelMap)map).putOri(key, translate((String)obj));
                } else {
                    map.put(key, translate((String)obj));
                }
            } else {
                convertObject(obj);
            }
        }
    }
    
    public void convertList(List list) {
        if (list == null) return;
        
        for(Object obj : list) {
            if (obj instanceof Map) {
                convertMap((Map)obj);
            } else {
                convertObject(obj);
            }
        }
    }
    
    public static void main(String[] args) {
        List list = new ArrayList<>();
        AtrForm atrForm = new AtrForm();
        atrForm.setATR_ID("<String>");
        list.add(atrForm);
        
        Map map = new CamelMap();
        map.put("cjsxowls", "<&#sdfsdf");
        map.put("ghkddmsdsldfj", "<&#sdfsdf");
        list.add(map);
        
        List sublist = new ArrayList<>();
        sublist.add("천태진<&#");
        list.add(sublist);
        
        XssScriptConvertor.getInstance().convertList(list);
    }
}
