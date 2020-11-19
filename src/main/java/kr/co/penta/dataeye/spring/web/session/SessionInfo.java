package kr.co.penta.dataeye.spring.web.session;

import java.io.Serializable;

public class SessionInfo implements Serializable {
	private static final long serialVersionUID = 8474637359476757048L;
	private String remoteIp =			"";
	private String userId =				"";
	private String userName =			"";
	
	private boolean adminRole = false;	
	private boolean systemRole = false;
	
	public String getRemoteIp() {
		return remoteIp;
	}
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public boolean isAdminRole() {
		return adminRole;
	}
	public void setAdminRole(boolean adminRole) {
		this.adminRole = adminRole;
	}
	public boolean isSystemRole() {
		return systemRole;
	}
	public void setSystemRole(boolean systemRole) {
		this.systemRole = systemRole;
	}
	
	@Override
    public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("SessionInfo [")
	    .append("userId=").append(userId)
	    .append(", userNaem=").append(userName)
        .append("]");
        return super.toString();
    }
}