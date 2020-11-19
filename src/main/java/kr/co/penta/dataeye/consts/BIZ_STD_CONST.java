package kr.co.penta.dataeye.consts;

import java.util.Arrays;
import java.util.List;

public class BIZ_STD_CONST {
	public static int WORD_TPL_START_ROW = 1;
	public static int TERM_TPL_START_ROW = 1;
	public static int DOMAIN_TPL_START_ROW = 1;
    public static int LIMIT_ROW = 10000;
    
    public enum TPL_SHEET_NM {
    	WORD("단어"),
    	TERM("용어"),
    	DOMAIN("도메인");
    	private String value;
		private TPL_SHEET_NM(String value) {
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
	
	public enum DOMAIN_SHEET_COL {
    	DOMAIN_GRP_NM(0, true),
    	DOMAIN_NM(1, true),
    	ORACLE_DATA_TYPE(2, true),
    	PDA_DATA_TYPE(3, true),
    	TIBERO_DATA_TYPE(4, true),
    	DATA_LENGTH(5, true),
    	DECIMAL_POINT(6, false),
    	DOMAIN_DESC(7, false);
    	private List<Object> value;
		private DOMAIN_SHEET_COL(Object... value) {
            this.value = Arrays.asList(value);
        }
        public List<Object> value() {
            return value;
        }
    }
	
	public enum TERM_SHEET_COL {
    	TERM_NM(0, true),
    	DOMAIN_NM(1, false),
    	KOR_SPLIT(2, true),
    	ENG_SPLIT(3, true);
    	private List<Object> value;
		private TERM_SHEET_COL(Object... value) {
            this.value = Arrays.asList(value);
        }
        public List<Object> value() {
            return value;
        }
    }
}
