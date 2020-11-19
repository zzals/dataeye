package kr.co.penta.dataeye.consts;

import java.util.Arrays;
import java.util.List;

public class DSGN_DB_CONST {
	public static int START_ROW = 1;
    public static int LIMIT_ROW = 1000;
    
    public enum OBJ_TYPE {
    	SUBJ("010101L"),
    	TAB("040102L"),
    	COL("040103L"),
    	APPSYS("050102L"),
    	DBINST("020100L"),
    	DB("020101L"),
    	STD_WORD("010301L");
    	private String value;
		private OBJ_TYPE(String value) {
            this.value = value;
        }
        public String value() {
            return value;
        }
    }
    
    public enum SHEET_NAME {
    	TAB("01. 테이블목록"),
    	COL("02. 테이블정의서");
    	private String value;
		private SHEET_NAME(String value) {
            this.value = value;
        }
        public String value() {
            return value;
        }
    }
		
	public enum SHEET_TAB_COL {
    	SEQ(0, true),
    	DBMS(1, true),
    	APPSYS(2, true),
    	TAB_TYPE_GBN(3, true),
    	TAB_INFO_GBN(4, true),
		CLS_GBN(5, true),
		CLS_SUB_AREA(6, true),
		DB_NM(7, true),
		SCHEMA_NM(8, true),
		TAB_ENTITY_NM(9, true),
		TAB_NM(10, true),
		MNGR(11, false),
		DID_YN(12, true),
		DATA_KEEP_PERIOD(13, false),
		DATA_GEN_PERIOD(14, false),
		DATA_GEN_CNT(15, false),
		DESC(16, false),
		
		APPSYS_VAL(-1, false),
		DB_ID_VAL(-1, false),
		CLS_GBN_VAL(-1, false),
		CLS_SUB_AREA_VAL(-1, false),
		TAB_INFO_GBN_VAL(-1, false),
		TAB_TYPE_GBN_VAL(-1, false),
		TAB_OBJ_ID(-1, false);
    	private List<Object> value;
		private SHEET_TAB_COL(Object... value) {
            this.value = Arrays.asList(value);
        }
        public List<Object> value() {
            return value;
        }
    }
	
	public enum SHEET_COL_COL {
    	SEQ(0, true),
    	TAB_ENTITY_NM(1, true),
    	TAB_NM(2, true),
    	COL_ATR_NM(3, true),
    	COL_NM(4, true),
    	DATA_TYPE(5, true),
    	DATA_LEN(6, true),
    	DATA_SCALE(7, true),
    	NOT_NULL_YN(8, true),
    	PK_ORD(9, true),
    	FK_YN(10, true),
    	DISTRIB_KEY_ORD(11, true),
    	DID_YN(12, true),
    	DESC(13, false),
		
		TAB_OBJ_ID(-1, false),
		COL_ORD(-1, false);
    	private List<Object> value;
		private SHEET_COL_COL(Object... value) {
            this.value = Arrays.asList(value);
        }
        public List<Object> value() {
            return value;
        }
    }
	
	public enum DB_TYPE {
		PDA,
    	ORACLE		
	}
	
	public enum VALID_CHK {
		INVALID_CNT,
		IS_VALID		
	}
}
