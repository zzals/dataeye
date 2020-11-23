package kr.co.penta.dataeye.spring.security.core;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.common.util.TimeUtils;

public class User implements UserDetails {
	private static final long serialVersionUID = -4610715415210352203L;
	private String sabun;
	private String userId;
    private String userPassword;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean accountEnabled;
    private Collection<? extends GrantedAuthority> authorities;

    private Integer loginFailCnt;
    private String passwordLastChgDt;
    private String loginLastDt;
    
    private String code;  
    private String userNm;
    private String telNo;
    private String hpNo;
    private String emailAddr;
    private String exOuterEmail;
    private String delYn;
    private String lgPstnDvCd;
    private String lgPstnDvNm;
    private String lgDutyCd;
    private String lgDutyNm;
    private String dpUserNm;
    private String exDeptCd;
    private String exDeptNm;
    private String exCompNm;
    private String dsObjId;
    private String userGbn;
    
    private boolean isAdmin = false;
    private boolean isSystem = false;
    
    private List<Map<String, Object>> actionAuth;

    public User() {
    }

    public User(String userId, String userPassword) {
        this.userId = userId;
        this.userPassword = userPassword;
    }

    public User(String userId, String userPassword, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return accountEnabled;
    }

    public Integer getLoginFailCnt() {
        return loginFailCnt;
    }

    public void setLoginFailCnt(Integer loginFailCnt) {
        this.loginFailCnt = loginFailCnt;
    }

    public String getPasswordLastChgDt() {
        return passwordLastChgDt;
    }

    public void setPasswordLastChgDt(String passwordLastChgDt) {
        this.passwordLastChgDt = passwordLastChgDt;
    }

    public String getLoginLastDt() {
        return loginLastDt;
    }

    public void setLoginLastDt(String loginLastDt) {
        this.loginLastDt = loginLastDt;
    }

    public String getSabun() {
		return sabun;
	}

	public void setSabun(String sabun) {
		this.sabun = sabun;
	}

	public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getHpNo() {
        return hpNo;
    }

    public void setHpNo(String hpNo) {
        this.hpNo = hpNo;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    public String getExDeptCd() {
        return exDeptCd;
    }

    public void setExDeptCd(String exDeptCd) {
        this.exDeptCd = exDeptCd;
    }

    public void setAccountEnabled(String accountEnabled) {
        if ("Y".equals(accountEnabled))
            this.accountEnabled = true;
        else
            this.accountEnabled = false;
    }

    public void setAccountNonExpired(String accountNonExpired) {
        if ("Y".equals(accountNonExpired))
            this.accountNonExpired = true;
        else
            this.accountNonExpired = false;
    }

    public void setAccountNonLocked(String accountNonLocked) {
        if ("Y".equals(accountNonLocked))
            this.accountNonLocked = true;
        else
            this.accountNonLocked = false;
    }

    public void setCredentialsNonExpired(String credentialsNonExpired) {
        if ("Y".equals(credentialsNonExpired))
            this.credentialsNonExpired = true;
        else
            this.credentialsNonExpired = false;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
    	for(GrantedAuthority authority : authorities) {
    		String auth = authority.getAuthority();
    		if (auth.equals(DataEyeSettingsHolder.getInstance().getMgrRole().getAdminRole())) {
    			this.isAdmin = true;
    		} else if (auth.equals(DataEyeSettingsHolder.getInstance().getMgrRole().getSystemRole())) {
    			this.isSystem = true;
    		}
    	}
        this.authorities = authorities;
    }

    public String getDsObjId() {
        return dsObjId;
    }

    public void setDsObjId(String dsObjId) {
        this.dsObjId = dsObjId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getExOuterEmail() {
        return exOuterEmail;
    }

    public void setExOuterEmail(String exOuterEmail) {
        this.exOuterEmail = exOuterEmail;
    }

    public String getLgPstnDvCd() {
        return lgPstnDvCd;
    }

    public void setLgPstnDvCd(String lgPstnDvCd) {
        this.lgPstnDvCd = lgPstnDvCd;
    }

    public String getLgPstnDvNm() {
        return lgPstnDvNm;
    }

    public void setLgPstnDvNm(String lgPstnDvNm) {
        this.lgPstnDvNm = lgPstnDvNm;
    }

    public String getLgDutyCd() {
        return lgDutyCd;
    }

    public void setLgDutyCd(String lgDutyCd) {
        this.lgDutyCd = lgDutyCd;
    }

    public String getLgDutyNm() {
        return lgDutyNm;
    }

    public void setLgDutyNm(String lgDutyNm) {
        this.lgDutyNm = lgDutyNm;
    }

    public String getDpUserNm() {
        return dpUserNm;
    }

    public void setDpUserNm(String dpUserNm) {
        this.dpUserNm = dpUserNm;
    }

    public String getExDeptNm() {
        return exDeptNm;
    }

    public void setExDeptNm(String exDeptNm) {
        this.exDeptNm = exDeptNm;
    }

    public String getExCompNm() {
        return exCompNm;
    }

    public void setExCompNm(String exCompNm) {
        this.exCompNm = exCompNm;
    }

    public String getUserGbn() {
        return userGbn;
    }

    public void setUserGbn(String userGbn) {
        this.userGbn = userGbn;
    }

    public List<Map<String, Object>> getActionAuth() {
        return actionAuth;
    }

    public void setActionAuth(List<Map<String, Object>> actionAuth) {
        this.actionAuth = actionAuth;
    }
    
    public boolean isAdmin() {
    	return isAdmin;
    }
    
    public void setIsAdmin(boolean isAdmin) {
    	this.isAdmin = isAdmin;
    }    
    
    public boolean isSystem() {
    	return isSystem;
    }
    
    public void setIsSystem(boolean isSystem) {
    	this.isSystem = isSystem;
    }

    public boolean isPasswordFailCntLimit(Integer defaultFailCnt) {
    	if (defaultFailCnt == -1) {
    		return false;
    	}
    	
    	if (defaultFailCnt <= this.loginFailCnt) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public boolean isPasswordChangeTime(Integer changeDays) {
    	if (changeDays == -1) {
    		return false;
    	}
    	
    	if (this.passwordLastChgDt == null || "".equals(this.passwordLastChgDt)) {
    		return true;
    	} else {
    		Date date = TimeUtils.getInstance().stringToDate(this.passwordLastChgDt, "yyyyMMddHHmmss");
    		Calendar curCal = Calendar.getInstance();
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(date);
    		cal.add(Calendar.DATE, changeDays);
    		
    		if (curCal.getTimeInMillis() > cal.getTimeInMillis()) {
    			return true;
    		} else {
    			return false;
    		}
    	}
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            return userId.equals(((User) obj).getUsername());
        }
        return false;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "userId='" + userId + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", accountEnabled=" + accountEnabled +
                ", authorities=" + authorities +
                ", code='" + code + '\'' +
                ", userNm='" + userNm + '\'' +
                ", telNo='" + telNo + '\'' +
                ", hpNo='" + hpNo + '\'' +
                ", emailAddr='" + emailAddr + '\'' +
                ", exOuterEmail='" + exOuterEmail + '\'' +
                ", delYn='" + delYn + '\'' +
                ", lgPstnDvCd='" + lgPstnDvCd + '\'' +
                ", lgPstnDvNm='" + lgPstnDvNm + '\'' +
                ", lgDutyCd='" + lgDutyCd + '\'' +
                ", lgDutyNm='" + lgDutyNm + '\'' +
                ", dpUserNm='" + dpUserNm + '\'' +
                ", exDeptCd='" + exDeptCd + '\'' +
                ", exDeptNm='" + exDeptNm + '\'' +
                ", exCompNm='" + exCompNm + '\'' +
                ", dsObjId='" + dsObjId + '\'' +
                ", userGbn='" + userGbn + '\'' +
                '}';
    }
}