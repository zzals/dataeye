package kr.co.penta.dataeye.common.entities;

public class PortalMenuEntity extends BaseAuth {
    private String menuAdmId;
    private Integer menuLvl;
    private String menuPath;
    private String menuNm;
    private String iconClass;
    private String pgmId;
    private String upMenuId;
    private String menuType;
    private String menuGrpCd;
    public String getMenuGrpCd() {
		return menuGrpCd;
	}
	public void setMenuGrpCd(String menuGrpCd) {
		this.menuGrpCd = menuGrpCd;
	}
	private Integer sortNo;
    
    public PortalMenuEntity() {
    	super();
    }
    public String getMenuAdmId() {
        return menuAdmId;
    }
    public void setMenuAdmId(String menuAdmId) {
        this.menuAdmId = menuAdmId;
    }
    public Integer getMenuLvl() {
        return menuLvl;
    }
    public void setMenuLvl(Integer menuLvl) {
        this.menuLvl = menuLvl;
    }
    public String getMenuPath() {
        return menuPath;
    }
    public void setMenuPath(String menuPath) {
        this.menuPath = menuPath;
    }
    public String getMenuNm() {
        return menuNm;
    }
    public void setMenuNm(String menuNm) {
        this.menuNm = menuNm;
    }
    public String getIconClass() {
        return iconClass;
    }
    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }
    public String getPgmId() {
        return pgmId;
    }
    public void setPgmId(String pgmId) {
        this.pgmId = pgmId;
    }
    public String getUpMenuId() {
        return upMenuId;
    }
    public void setUpMenuId(String upMenuId) {
        this.upMenuId = upMenuId;
    }
    public String getMenuType() {
        return menuType;
    }
    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }
    public Integer getSortNo() {
        return sortNo;
    }
    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }
}
