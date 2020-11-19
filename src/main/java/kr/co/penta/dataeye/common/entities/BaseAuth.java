package kr.co.penta.dataeye.common.entities;

public class BaseAuth {
	private String appId;
    private String menuId;
    private String cAuth;
    private String rAuth;
    private String uAuth;
    private String dAuth;
    private String eAuth;
    private String privOperGbn;
    
    public BaseAuth() {
    	this.cAuth = "N";
    	this.rAuth = "N";
    	this.uAuth = "N";
    	this.dAuth = "N";
    	this.eAuth = "N";
    }
    
    public void setAllAuth() {
    	this.cAuth = "Y";
    	this.rAuth = "Y";
    	this.uAuth = "Y";
    	this.dAuth = "Y";
    	this.eAuth = "Y";
    }
    
    public void setAuth(BaseAuth baseAuth) {
    	this.appId = baseAuth.appId;
    	this.menuId = baseAuth.menuId;
    	this.cAuth = baseAuth.cAuth;
    	this.rAuth = baseAuth.rAuth;
    	this.uAuth = baseAuth.uAuth;
    	this.dAuth = baseAuth.dAuth;
    	this.eAuth = baseAuth.eAuth;
    }
    
    public String getAppId() {
        return appId;
    }
    public void setAppId(String appId) {
        this.appId = appId;
    }
    public String getMenuId() {
        return menuId;
    }
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }
    
    public String getCAuth() {
        return cAuth;
    }
    public void setCAuth(String cAuth) {
        this.cAuth = cAuth;
    }
    public String getRAuth() {
        return rAuth;
    }
    public void setRAuth(String rAuth) {
        this.rAuth = rAuth;
    }
    public String getUAuth() {
        return uAuth;
    }
    public void setUAuth(String uAuth) {
        this.uAuth = uAuth;
    }
    public String getDAuth() {
        return dAuth;
    }
    public void setDAuth(String dAuth) {
        this.dAuth = dAuth;
    }
    public String getEAuth() {
        return eAuth;
    }
    public void setEAuth(String eAuth) {
        this.eAuth = eAuth;
    }
    public String getPrivOperGbn() {
        return privOperGbn;
    }
    public void setPrivOperGbn(String privOperGbn) {
    	String[] auths = privOperGbn.split(":");
    	for(int i=0; i<auths.length; i++) {
    		switch (i) {
			case 0:
				setCAuth(auths[i]);
				break;

			case 1:
				setRAuth(auths[i]);
				break;

			case 2:
				setUAuth(auths[i]);
				break;
			
			case 3:
				setDAuth(auths[i]);
				break;
			
			case 4:
				setEAuth(auths[i]);
				break;

			default:
				break;
			}
    	}
    	
        this.privOperGbn = privOperGbn;
    }

    public void setMode(String mode) {
    	if (mode == null || "".equals(mode)) {
    		setRAuth("Y");
    	} else if ("R".equals(mode) || "RO".equals(mode)) {
    		setRAuth("Y");
    	} else if ("C".equals(mode)) {
    		setRAuth("Y");
    		setCAuth("Y");
    	} else if ("U".equals(mode)) {
    		setRAuth("Y");
    		setUAuth("Y");
    	} else if ("D".equals(mode)) {
    		setRAuth("Y");
    		setDAuth("Y");
    	}
    }
}
