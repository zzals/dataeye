package kr.co.penta.dataeye.consts;

import java.util.Arrays;
import java.util.List;

public class BR_UPLOAD_CONST {
	public static int START_ROW = 2;
    public static int LIMIT_ROW = 1000;
    
    public enum BR_TYPE {
    	DQI("030101L"),
    	CTQ("030102L"),
    	BR("030103L"),
    	GOAL_BR("030104L"),
    	MUTUAL_BR("030105L"),
    	LIST_BR("030106L"),
    	BR_GRP("030199L");
    	private String value;
		private BR_TYPE(String value) {
            this.value = value;
        }
        public String value() {
            return value;
        }
    }
    
    public enum SHEET_NAME {
    	BR_GRP("검증실행그룹"),
    	BR("검증룰");
    	private String value;
		private SHEET_NAME(String value) {
            this.value = value;
        }
        public String value() {
            return value;
        }
    }
	
	public enum BR_GRP_CD {
    	DQ_MNG_TYPE(2),
    	DQ_BREXEGPU(3);
    	private List<Integer> value;
		private BR_GRP_CD(Integer... value) {
            this.value = Arrays.asList(value);
        }
        public List<Integer> value() {
            return value;
        }
    }
    
	public enum BR_CD {
    	DQI(3),
    	DQ_MNG_TYPE(4),
    	BR_TYPE(6),
    	CONN_INFO(7,13,16,18,21),
    	DQ_COMPTYPE(9,15,20),
    	DQ_VALUNIT(10,12);
    	private List<Integer> value;
		private BR_CD(Integer... value) {
            this.value = Arrays.asList(value);
        }
        public List<Integer> value() {
            return value;
        }
    }
	
	public enum BR_GRP_SHEET_COL {
    	BR_GRP_NM(0, true),
    	BR_GRP_DESC(1, false),
    	DQ_MNG_TYPE(2, true),
    	DQ_BREXEGPU(3, true),
    	MNGR(4, true);
    	private List<Object> value;
		private BR_GRP_SHEET_COL(Object... value) {
            this.value = Arrays.asList(value);
        }
        public List<Object> value() {
            return value;
        }
    }
	
	public enum BR_SHEET_COL {
    	BR_GRP_NM(0, false),
    	BR_NM(1, true),
    	BR_DESC(2, false),
    	DQI(3, true),
    	DQ_MNG_TYPE(4, true),
    	MNGR(5, true),
    	BR_TYPE(6, true),
    	GOAL_CONN_INFO(7, true),
    	GOAL_GOAL_VAL(8, true),
    	GOAL_DQ_COMPTYPE(9, true),
    	GOAL_DQ_VALUNIT(10, true),
    	GOAL_SQL(11, true),
    	MUTUAL_DQ_VALUNIT(12, true),
    	MUTUAL_SRC_CONN_INFO(13, true),
    	MUTUAL_SRC_SQL(14, true),
    	MUTUAL_DQ_COMPTYPE(15, true),
    	MUTUAL_TGT_CONN_INFO(16, true),
    	MUTUAL_TGT_SQL(17, true),
    	LIST_SRC_CONN_INFO(18, true),
    	LIST_SRC_SQL(19, true),
    	LIST_DQ_COMPTYPE(20, true),
    	LIST_TGT_CONN_INFO(21, true),
    	LIST_TGT_SQL(22, true);
    	private List<Object> value;
		private BR_SHEET_COL(Object... value) {
            this.value = Arrays.asList(value);
        }
        public List<Object> value() {
            return value;
        }
    }
}
