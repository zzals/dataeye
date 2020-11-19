package kr.co.penta.dataeye.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;

public class JSONUtils {
    private static final JSONUtils JSON_UTIL = new JSONUtils();

    private JSONUtils() {
        super();
    }

    public String objectToJson(final Object o) {
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        try {
            return om.writeValueAsString(o);
        } catch (Exception e) {
            return "";
        }
    }

    public Map<String, Object> jsonToMap(final String jsonStr) {
        final ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        try {
            return om.readValue(jsonStr, new TypeReference<Map<String, Object>>(){});
        } catch(Exception e) {
            return new HashMap<>();
        }
    }

    public Map<String, Object> jsonToMap(final JSONObject jsonObject) {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = jsonObject.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = jsonObject.get(key);

            if(value instanceof JSONArray) {
                value = jsonToList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = jsonToMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public List<?> jsonToList(final String jsonStr) {
        final ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        try {
            return om.readValue(jsonStr, new TypeReference<List<Map<String, Object>>>(){});
        } catch(Exception e) {
            try {
                return om.readValue(jsonStr, new TypeReference<List<String>>(){});
            } catch (Exception e1) {
                return new ArrayList<>();
            }
        }
    }

    public List<Object> jsonToList(JSONArray jsonArray) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for(int i = 0; i < jsonArray.length(); i++) {
            Object value = jsonArray.get(i);
            if(value instanceof JSONArray) {
                value = jsonToList((JSONArray) value);
            }

            else if(value instanceof JSONObject) {
                value = jsonToMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    public static JSONUtils getInstance() {
        return JSON_UTIL;
    }
}
