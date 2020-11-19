package kr.co.penta.dataeye.common.entities.meta;

import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;

/**
 * Created by Administrator on 2014-09-04.
 */
public class PenSysUserGrpM extends DaoParam {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2885329334161823074L;

	public PenSysUserGrpM() {
        
    }
    
    public String getUserGrpId() {
        return getString("userGrpId");
    }

    public void setUserGrpId(String userGrpId) {
        put("USER_GRP_ID", userGrpId);
    }

    public String getDelYn() {
        return getString("delYn");
    }

    public void setDelYn(String delYn) {
        put("DEL_YN", delYn);
    }

    public String getUserGrpNm() {
        return getString("userGrpNm");
    }

    public void setUserGrpNm(String userGrpNm) {
        put("USER_GRP_NM", userGrpNm);
    }

    public String getUserGrpDesc() {
        return getString("userGrpDesc");
    }

    public void setUserGrpDesc(String userGrpDesc) {
        put("USER_GRP_DESC", userGrpDesc);
    }

    public String getUserGrpType() {
        return getString("userGrpType");
    }

    public void setUserGrpType(String userGrpType) {
        put("USER_GRP_TYPE", userGrpType);
    }

    public String getPrivYn() {
        return getString("privYn");
    }

    public void setPrivYn(String privYn) {
        put("PRIV_YN", privYn);
    }

    public String getUseYn() {
        return getString("useYn");
    }

    public void setUseYn(String useYn) {
        put("USE_YN", useYn);
    }
}
