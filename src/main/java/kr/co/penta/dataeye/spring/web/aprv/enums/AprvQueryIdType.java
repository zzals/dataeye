package kr.co.penta.dataeye.spring.web.aprv.enums;

public enum AprvQueryIdType {
    /* 네임스페이스__명칭("queryId", "[테이블명] [Dao]") */
    APRV__GET_APRV_M_INFO("aprv.getAprvMInfo", "[PEN_APRV_M, PEN_OBJ_M_T] [aprvDao.selectOne]"),
    APRV__GET_APRV_MD_INFO("aprv.getAprvMdInfo", "[PEN_APRV_M, PEN_OBJ_M_T, PEN_OBJ_D_T, PTL_COM_USER] [aprvDao.selectOne]"),
    APRV__GET_APRV_PRE_MD_INFO("aprv.getAprvPreMdInfo", "[PEN_APRV_M, PEN_OBJ_M_T, PEN_OBJ_D_T, PEN_APRV_D] [aprvDao.selectOne]"),
    APRV__GET_APRV_LINE_INFO("aprv.getAprvLineInfo", "[PEN_OBJ_M, PEN_OBJ_D] [aprvDao.selectList]"),
    APRV__GET_APRV_USER_MAX_FILE_NO("aprv.getAprvUserMaxFileNo", "[PEN_ARPV_F] [aprvDao.selectOne]"),
    APRV__GET_APRV_USER_DTL_INFO("aprv.getAprvUserDtlInfo", "[PTL_COM_USER] [aprvDao.selectOne]"),
    APRV__GET_APRV_USER_GRP_INFO("aprv.getAprvUserGrpInfo", "[PEN_SYS_GRP_USER_R, PTL_COM_USER] [aprvDao.selectList]"),
    APRV__GET_APRV_USER_INFO("aprv.getAprvUserInfo", "[PEN_APRV_D, PTL_COM_USER] [aprvDao.selectList]"),
    APRV__GET_APRV_D_INFO("aprv.getAprvDInfo", "[PEN_APRV_D, PTL_COM_USER] [aprvDao.selectList]"),
    APRV__GET_APRV_DA_INFO("aprv.getAprvDaInfo", "[PEN_APRV_D, PTL_COM_USER] [aprvDao.selectList]"),
    APRV__GET_APRV_PROC_INFO("aprv.getAprvProcInfo", "[PEN_APRV_D, PTL_COM_USER] [aprvDao.selectOne]"),
    APRV__GET_APRV_STS_PROC_INFO("aprv.getAprvStsProcInfo", "[PEN_APRV_D] [aprvDao.selectOne]"),
    APRV__GET_APRV_STS_M_INFO("aprv.getAprvStsMInfo", "[PEN_APRV_M] [aprvDao.selectOne]"),
    APRV__GET_APRV_FILE_INFO("aprv.getAprvFileInfo", "[PEN_APRV_F, PEN_APRV_D] [aprvDao.selectList]"),
    PEN_APRV_M__DELETE_ALL("PEN_APRV_M.deleteAll", "[PEN_APRV_M, PEN_APRV_D] [aprvDao.delete]"),
    PEN_APRV_M__INSERT("PEN_APRV_M.insert", "[PEN_APRV_M] [aprvDao.insert]"),
    PEN_APRV_M__UPDATE("PEN_APRV_M.update", "[PEN_APRV_M] [aprvDao.update]"),
    PEN_APRV_D__INSERT("PEN_APRV_D.insert", "[PEN_APRV_D] [aprvDao.insert]"),
    PEN_APRV_D__UPDATE("PEN_APRV_D.update", "[PEN_APRV_D] [aprvDao.update]"),
    PEN_APRV_D__UPDATE_NEXT("PEN_APRV_D.updateNext", "[PEN_APRV_D] [aprvDao.update]"),
    PEN_APRV_D__UPDATE_ALL("PEN_APRV_D.updateAll", "[PEN_APRV_D] [aprvDao.update]"),
    PEN_APRV_D__UPDATE_NEXT_RTY("PEN_APRV_D.updateNextRty", "[PEN_APRV_D] [aprvDao.update]"),
    PEN_APRV_D__UPDATE_APRV_DA_INFO("PEN_APRV_D.updateAprvDaInfo", "[PEN_APRV_D] [aprvDao.update]"),
    PEN_APRV_F__INSERT("PEN_APRV_F.insert", "[PEN_APRV_F] [aprvDao.insert]"),
    PEN_APRV_F__DELETE("PEN_APRV_F.delete", "[PEN_APRV_F] [aprvDao.delete]"),
    PEN_OBJ_MDR_DELETE("aprv.deleteAprvPenMDR", "[PEN_OBJ_M, PEN_OBJ_D, PEN_OBJ_R] [aprvDao.delete]"),
    PEN_OBJ_M_DELETE("aprv.deleteAprvPenM", "[PEN_OBJ_M] [aprvDao.delete]"),
    PEN_OBJ_D_DELETE("aprv.deleteAprvPenD", "[PEN_OBJ_D] [aprvDao.delete]"),
    PEN_OBJ_R_DELETE("aprv.deleteAprvPenR", "[PEN_OBJ_R] [aprvDao.delete]"),
    
    PEN_APRV_M__DELETE_BY_APRV_ID("PEN_APRV_M.deleteByAprvId", "[PEN_APRV_M] [aprvDao.delete]"),
    PEN_APRV_D__DELETE_BY_APRV_ID("PEN_APRV_D.deleteByAprvId", "[PEN_APRV_D] [aprvDao.delete]"),
    PEN_APRV_F__DELETE_BY_APRV_ID("PEN_APRV_F.deleteByAprvId", "[PEN_APRV_F] [aprvDao.delete]"),
    
    APRV__GET_END("", "");

    private String id;
    private String name;

    AprvQueryIdType(final String id, final String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
