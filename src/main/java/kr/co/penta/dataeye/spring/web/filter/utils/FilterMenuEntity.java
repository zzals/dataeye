package kr.co.penta.dataeye.spring.web.filter.utils;

public class FilterMenuEntity {
	public enum URL_PATTRENS {
		PORTAL_MAIN("/portal/main"),
		ADMIN_MAIN("/admin/main"),
		SYSTEM_MAIN("/system/main"),
		PORTAL_VIEW("/portal/view"),
		ADMIN_VIEW("/admin/view"),
		SYSTEM_VIEW("/system/view");
    	private String value;
		private URL_PATTRENS(String value) {
            this.value = value;
        }
        public String value() {
            return value;
        }
	}
	
	public String menuId;
	public String menuNm;
	public String url;
	public String viewId;
	public String queryStr;
	
	public FilterMenuEntity(String menuId, String menuNm, String url, String viewId, String queryStr) {
		this.menuId = menuId;
		this.menuNm = menuNm;
		this.url = url;
		this.viewId = viewId;
		this.queryStr = queryStr;
	}
}