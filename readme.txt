1. jquery.layout patch
	- sC.selector = $N.selector.split(".slice")[0]; 주석처리함.
	- jQuery 3 dropped the 'selector' property on objects. 
	  Just comment out that line of code in your copy of Layout. 
	  That property was copied to state.container only to provide extra information; 
	  it has no effect on functionally.
	  
2. br 데이터 초기화
	TRUNCATE TABLE PEN_OBJ_BR_SQL_L;
	TRUNCATE TABLE PEN_OBJ_BR_RSLT_MNG_D;
	TRUNCATE TABLE PEN_OBJ_BR_PARM_L;
	TRUNCATE TABLE PEN_OBJ_BR_LIST_VAL_L;
	TRUNCATE TABLE PEN_OBJ_BR_L;
	TRUNCATE TABLE PEN_OBJ_BR_EXE_GRP_L;
	TRUNCATE TABLE PEN_OBJ_BR_ERR_L;
	DELETE FROM PEN_OBJ_M WHERE OBJ_TYPE_ID IN ('030103L', '030104L', '030105L', '030106L', '030107L', '030199L');
	DELETE FROM PEN_OBJ_D WHERE OBJ_TYPE_ID IN ('030103L', '030104L', '030105L', '030106L', '030107L', '030199L');
	DELETE FROM PEN_OBJ_R WHERE OBJ_TYPE_ID IN ('030103L', '030104L', '030105L', '030106L', '030107L', '030199L') OR REL_OBJ_TYPE_ID IN ('030103L', '030104L', '030105L', '030106L', '030107L', '030199L');
	
	
	