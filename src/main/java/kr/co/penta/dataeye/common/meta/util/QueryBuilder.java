package kr.co.penta.dataeye.common.meta.util;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.co.penta.dataeye.common.entities.meta.AtrInfoEntity;

public final class QueryBuilder {
	private static final QueryBuilder builder = new QueryBuilder();
	
	enum OBJ_M_SEQ {
		OBJ_TYPE_ID(1),
		OBJ_ID(2),
		ADM_OBJ_ID(3),
		OBJ_NM(4),
		OBJ_ABBR_NM(5),
		OBJ_DESC(6),
		PATH_OBJ_TYPE_ID(7),
		PATH_OBJ_ID(8);
		private final int value;
		OBJ_M_SEQ(final int value) {
			this.value = value;
		}
		public int value() {
			return value;
		}
	}
	
	/**
	 * atrib map key ���� <br>
	 * @param 
	 * @return 
	 */ 
	enum atribList_map_key {
		ATR_ID_SEQ, 
		ATR_RFRN_CD,
		SQL_SBST;
	};
	
	private static final String WHEN = "WHEN";
	private static final String THEN = "THEN";
	private static final String ELSE = "ELSE";
	
	/**
	 * extend obj_atrib_sbst case �� ���� ��<br>
	 * @param 
	 * @return String
	 */ 
	public String extendObjAtrValSelectStatment(List<AtrInfoEntity> atrInfos, boolean isCode) {
		final StringBuffer buf = new StringBuffer(256);
		Integer atrIdSeq;
		String atrRfrnCd;
		String sqlSbst;
		
		for (final Iterator<AtrInfoEntity> iterator=atrInfos.iterator(); iterator.hasNext(); ) {
			final AtrInfoEntity entity = iterator.next();
			atrIdSeq = entity.getAtrIdSeq();
			atrRfrnCd = entity.getAtrRfrnCd();
			sqlSbst = entity.getSqlSbst();
			
			if (("M".equals(atrRfrnCd) || "C".equals(atrRfrnCd) && isCode) && (sqlSbst != null || !"".equals(sqlSbst))) {
				if (sqlSbst == null || "".equals(sqlSbst)) {
					buf.append(WHEN).append(" ").append(atrIdSeq).append(" ").append(THEN).append(" T101.OBJ_ATR_VAL\n");
				} else {
					buf.append(WHEN).append(" ").append(atrIdSeq).append(" THEN T_SEQ_").append(atrIdSeq).append(".DISP_NAME\n");
				}
			} else {
				if (atrIdSeq < 101) {
					buf.append(WHEN).append(" ").append(atrIdSeq).append(" ").append(THEN).append(" T101.OBJ_ATR_VAL\n");
				}
			}
		}
		
		if (buf.length() == 0) {
			buf.append("TO_CHAR(T101.OBJ_ATR_VAL)\n");
//			buf.append("CAST(T101.OBJ_ATR_VAL AS NVARCHAR(4000))\n");
		} else {
			buf.insert(0, "CASE T101.ATR_ID_SEQ\n");
			buf.append(ELSE).append(' ').append("T101.OBJ_ATR_VAL");
			buf.append("\nEND\n");
		}
		return buf.toString();
	}
	
	/**
	 * extend obj_atrib_sbst join �� ���� ��<br>
	 * @param 
	 * @return String
	 */ 
	public String extendObjAtrValJoinStatment(List<AtrInfoEntity> atrInfos, boolean isCode) {
		final StringBuffer buf = new StringBuffer(32);
		Integer atrIdSeq;
		String atrRfrnCd;
		String sqlSbst;
		
		for (final Iterator<AtrInfoEntity> iterator=atrInfos.iterator(); iterator.hasNext(); ) {
			final AtrInfoEntity entity = iterator.next();
			atrIdSeq = entity.getAtrIdSeq();
			atrRfrnCd = entity.getAtrRfrnCd();
			sqlSbst = entity.getSqlSbst();
			
			if (("M".equals(atrRfrnCd) || "C".equals(atrRfrnCd) && isCode) && (sqlSbst != null || !"".equals(sqlSbst))) {
				if (sqlSbst.toUpperCase(Locale.KOREAN).lastIndexOf("ORDER") != -1) {
					sqlSbst = sqlSbst.substring(0, sqlSbst.toUpperCase(Locale.KOREAN).lastIndexOf("ORDER"));
				}
				buf.append("LEFT OUTER JOIN (\nSELECT CODE, DISP_NAME\nFROM (\n");
				buf.append(sqlSbst);
				buf.append("\n) T \n) T_SEQ_").append(atrIdSeq);
				buf.append(" ON T_SEQ_").append(atrIdSeq).append(".CODE = T101.OBJ_ATR_VAL\n");
			}
		}
		return buf.toString();
	}
	
	public String makeDefaultViewListStatment(final List<Map<String, Object>> atrList, String objTypeId, List<Map<String, Object>> columnModel, Map<String, Object> filter) {
		final StringBuffer buf = new StringBuffer(32);
	    class A {
	    	StringBuilder sb = new StringBuilder();
			String get(List<Map<String, Object>> atrList, int atrIdSeq) {
				for (Map<String, Object> atr : atrList) {
					int atrIdSeq1 = ((BigDecimal)atr.get("ATR_ID_SEQ")).intValue();
					if (atrIdSeq == atrIdSeq1) {
						String atrRfrnCd = (String)atr.get("ATR_RFRN_CD");
						if ("C".equals(atrRfrnCd)) {
							String sqlSbst = (String)atr.get("SQL_SBST");
							
							sb.append("(").append("\n");
							sb.append("		SELECT").append("\n");
							sb.append("			T103.DISP_NAME").append("\n");
							sb.append("		FROM PEN_OBJ_D T102").append("\n");
							sb.append("		INNER JOIN (").append("\n");
							sb.append(sqlSbst).append("\n");
							sb.append("		) T103").append("\n");
							sb.append("		ON (").append("\n");
							sb.append("		        T103.CODE = T102.OBJ_ATR_VAL").append("\n");
							sb.append("		)").append("\n");
							sb.append("		WHERE T102.OBJ_TYPE_ID = T101.OBJ_TYPE_ID").append("\n");
							sb.append("		AND T102.OBJ_ID = T101.OBJ_ID").append("\n");
							sb.append("		AND T102.ATR_ID_SEQ = ").append(atrIdSeq1).append("\n");
							sb.append("		AND T102.ATR_VAL_SEQ=101").append("\n");
							sb.append(")");
						} else {
							sb.append("(").append("\n");
							sb.append("		SELECT").append("\n");
							sb.append("			T102.OBJ_ATR_VAL").append("\n");
							sb.append("		FROM PEN_OBJ_D T102").append("\n");
							sb.append("		WHERE T102.OBJ_TYPE_ID = T101.OBJ_TYPE_ID").append("\n");
							sb.append("		AND T102.OBJ_ID = T101.OBJ_ID").append("\n");
							sb.append("		AND T102.ATR_ID_SEQ = ").append(atrIdSeq1).append("\n");
							sb.append("		AND T102.ATR_VAL_SEQ=101").append("\n");
							sb.append(")").append("\n");
						}
					}
				}
					
				return sb.toString();
			}
		}
				
		buf.append("SELECT").append("\n");
		for (Map<String, Object> column : columnModel) {
			String id = (String)column.get("id");
			try {
				int atrIdSeq = Integer.valueOf(id);
				A a = new A();
				buf.append(a.get(atrList, atrIdSeq)).append(" AS ATR_ID_SEQ_").append(id).append(",").append("\n");
			} catch(Exception e) {
				buf.append("T101.").append(id).append(" AS ").append(id).append(",").append("\n");
				
			}
		}
		buf.append(1).append("\n");
		buf.append("FROM PEN_OBJ_M T101").append("\n");
		buf.append("WHERE T101.OBJ_TYPE_ID = #{objTypeId}").append("\n");
		if (null != filter) {
			final String keyword = (String)filter.get("keyword");
			if (keyword != null && !"".equals(keyword)) {
				buf.append("AND ${searchKey} LIKE '%'||#{keyword}||'%'").append("\n");
			}
			buf.append("ORDER BY T101.OBJ_NM");
		}
		
		return buf.toString();
	}

	public String getDummyQuery(String query) {
		Pattern pattern = Pattern.compile("#\\{\\s?(.*?)\\s?\\}", Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(query);
		query = matcher.replaceAll("0");
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT 1 FROM (").append("\n");
		sb.append(query).append("\n");
		sb.append(") T").append("\n");
		sb.append("WHERE 1=0").append("\n");
		
		return sb.toString();
	}
	
	protected QueryBuilder() {
    	super();
    }
 
	public static QueryBuilder getInstance() {
       return builder;
    }
}