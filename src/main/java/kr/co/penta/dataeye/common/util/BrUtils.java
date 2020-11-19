package kr.co.penta.dataeye.common.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class BrUtils {
	private static final BrUtils BR_UTILS = new BrUtils();
	
	public List<String> getSqlParams(String query) {
		List<String> list = new ArrayList<>();
		Pattern pattern = Pattern.compile("#\\{\\s?(.*?)\\s?\\}", Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(query);
		
		while(matcher.find()) {
			String param = matcher.group(1);
			if (!list.contains(param)) {
				list.add(param);
			}
		}
		return list;
	}
	
	protected BrUtils() {
    	super();
    }
 
	public static BrUtils getInstance() {
       return BR_UTILS;
    }
}