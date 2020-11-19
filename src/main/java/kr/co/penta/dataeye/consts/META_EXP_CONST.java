package kr.co.penta.dataeye.consts;

import java.util.Arrays;
import java.util.List;

public class META_EXP_CONST {
	public enum KEY {
		COMM_CD("PEN_CD_GRP_M", "PEN_CD_M"),
		OBJ_TYPE("PEN_OBJ_TYPE_M", "PEN_OBJ_TYPE_ATR_D"),
		REL_TYPE("PEN_REL_TYPE_M"),
		ATR("PEN_ATR_M"),
		OBJ("PEN_OBJ_M", "PEN_OBJ_D", "PEN_OBJ_R");
    	private List<String> value;
		private KEY(String... value) {
            this.value = Arrays.asList(value);
        }
        public List<String> value() {
            return value;
        }
    }
	
	public enum TAB_NM {
		PEN_CD_GRP_M,
		PEN_CD_M,
		PEN_OBJ_TYPE_M,
		PEN_OBJ_TYPE_ATR_D,
		PEN_REL_TYPE_M,
		PEN_ATR_M,
		PEN_OBJ_M,
		PEN_OBJ_D,
		PEN_OBJ_R;
    }
	
	public enum RUN_STATUS {
		INIT,
		RUNNING,
		SUCCESS,
		ERROR;
    }
}