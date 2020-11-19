package kr.co.penta.dataeye.spring.mybatis;

public class Conditions {
	public static boolean isBoolean(Boolean b){
		if (b == null) {
        	return false;
        } 
        return b;
    }

	public static boolean isBoolean(String b){
		if (b == null || "".equals(b)) {
        	return false;
        } else {
        	if (b.toLowerCase().equals("true")) {
        		return true;
        	} else {
        		return false;
        	}
        }
    }
	
    public static boolean isNotEmpty(Object s) {
        if (null == s || "".equals(s)) {
            return false;
        } else {
            return true;
        }
    }
}
