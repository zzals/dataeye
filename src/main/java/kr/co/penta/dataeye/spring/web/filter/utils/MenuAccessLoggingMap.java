package kr.co.penta.dataeye.spring.web.filter.utils;

import java.util.ArrayList;
import java.util.List;

public class MenuAccessLoggingMap {
	private static MenuAccessLoggingMap instance;
	
	
	private List<FilterMenuEntity> menus = new ArrayList<>();
	
	private MenuAccessLoggingMap() {
		menus.add(new FilterMenuEntity("main_0001", "포탈 홈", "portal/main", "", ""));
		menus.add(new FilterMenuEntity("main_0002", "Admin 홈", "admin/main", "", ""));
		menus.add(new FilterMenuEntity("main_0003", "System 홈", "system/main", "", ""));

		menus.add(new FilterMenuEntity("admin_0001", "속성 관리", "admin/view", "admin/atr", ""));
		menus.add(new FilterMenuEntity("admin_0002", "유형 관리", "admin/view", "admin/objType", ""));
		menus.add(new FilterMenuEntity("admin_0003", "객체 관리", "admin/view", "admin/obj", ""));
		menus.add(new FilterMenuEntity("admin_0004", "객체관계 관리", "admin/view", "admin/objRelType", ""));
		menus.add(new FilterMenuEntity("admin_0005", "코드 관리", "admin/view", "admin/code", ""));
		menus.add(new FilterMenuEntity("admin_0006", "UI 관리", "admin/view", "admin/objUI", ""));
		menus.add(new FilterMenuEntity("admin_0007", "UI 매핑", "admin/view", "admin/objUIMapping", ""));
		menus.add(new FilterMenuEntity("admin_0008", "메타 Export", "admin/view", "admin/metaExport", ""));
		menus.add(new FilterMenuEntity("admin_0009", "메타 Import", "admin/view", "admin/metaImport", ""));

		menus.add(new FilterMenuEntity("system_0001", "Application", "admin/view", "system/application", ""));
		menus.add(new FilterMenuEntity("system_0002", "사용자 관리", "admin/view", "system/user", ""));
		menus.add(new FilterMenuEntity("system_0003", "메뉴 관리", "admin/view", "system/menuMgr", ""));
		menus.add(new FilterMenuEntity("system_0004", "ROLE 그룹관리", "admin/view", "system/roleGrpMgr", ""));
		menus.add(new FilterMenuEntity("system_0005", "권한 그룹 관리", "admin/view", "system/authGrpMgr", ""));
		menus.add(new FilterMenuEntity("system_0006", "메뉴 권한 관리", "admin/view", "system/menuAuthMgr", ""));
		menus.add(new FilterMenuEntity("system_0007", "통합 권한 관리", "admin/view", "system/integrationAuthMgr", ""));
		menus.add(new FilterMenuEntity("system_0008", "접속 활용 현황", "admin/view", "system/logUser", ""));
		menus.add(new FilterMenuEntity("system_0009", "메뉴 활용 현황", "admin/view", "system/logMenu", ""));
		menus.add(new FilterMenuEntity("system_0010", "객체 활용 현황", "admin/view", "system/logObj", ""));
	}
	
	public void init(List<FilterMenuEntity> filterMenuEntities) {
		menus.addAll(filterMenuEntities);
	}
	
	public FilterMenuEntity get(String url, String viewId) {
		for(FilterMenuEntity menu : menus) {
			if (url.equals("/"+menu.url) && viewId.equals(menu.viewId)) {
				return menu;
			}
		}
		return null;
	}
	
	public static MenuAccessLoggingMap getInstance() {
		if (instance == null) {
			instance = new MenuAccessLoggingMap();
		}
		
		return instance;
	}
}
