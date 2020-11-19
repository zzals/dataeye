package kr.co.penta.dataeye.common.util;


import java.util.List;

import kr.co.penta.dataeye.common.entities.BaseAuth;
import kr.co.penta.dataeye.common.entities.PortalMenuEntity;

public class AuthMenuBuilder {
	private List<PortalMenuEntity> menus = null;
	private List<BaseAuth> auths = null;

	enum AUTH_YN{Y, N}
	
	public AuthMenuBuilder(List<PortalMenuEntity> menus, List<BaseAuth> auths) {
		super();
		this.menus = menus;
		this.auths = auths;
	}
	
	public List<PortalMenuEntity> build(boolean isAllAuth) {
		if (isAllAuth) {
			for(PortalMenuEntity menu : menus) {
				menu.setRAuth(AUTH_YN.Y.name());
				menu.setCAuth(AUTH_YN.Y.name());
				menu.setUAuth(AUTH_YN.Y.name());
				menu.setDAuth(AUTH_YN.Y.name());
				menu.setEAuth(AUTH_YN.Y.name());
			}
		} else {
			for(PortalMenuEntity menu : menus) {
				if ("FOLDER".equals(menu.getMenuType())) {
				} else {
					setMenuAuth(menu);
				}
			}
			
			for (int i=menus.size()-1; i>=0; i--) {
				PortalMenuEntity menu = menus.get(i);
				if (!AUTH_YN.Y.name().equals(menu.getRAuth())) {
					menus.remove(menu);
				}
			}
		}
		
		return menus;
	}
	
	private void setMenuAuth(PortalMenuEntity menu) {
		for (BaseAuth auth : auths) {
			if (menu.getMenuId().equals(auth.getMenuId())) {
				setMenuAuth(menu, auth);
				
				if (AUTH_YN.Y.name().equals(auth.getRAuth()) && menu.getUpMenuId() != null && !menu.getUpMenuId().equals("")) {
					setParentFolderRAuth(menu.getUpMenuId());
				}
			}
		}
	}
	
	private void setMenuAuth(PortalMenuEntity menu, BaseAuth auth) {
		if (AUTH_YN.Y.name().equals(auth.getRAuth())) menu.setRAuth(AUTH_YN.Y.name());
		if (AUTH_YN.Y.name().equals(auth.getCAuth())) menu.setCAuth(AUTH_YN.Y.name()); menu.setRAuth(AUTH_YN.Y.name());
		if (AUTH_YN.Y.name().equals(auth.getUAuth())) menu.setUAuth(AUTH_YN.Y.name()); menu.setRAuth(AUTH_YN.Y.name());
		if (AUTH_YN.Y.name().equals(auth.getDAuth())) menu.setDAuth(AUTH_YN.Y.name()); menu.setRAuth(AUTH_YN.Y.name());
		if (AUTH_YN.Y.name().equals(auth.getEAuth())) menu.setEAuth(AUTH_YN.Y.name()); menu.setRAuth(AUTH_YN.Y.name());
	}
	
	private void setParentFolderRAuth(String upMenuId) {
		for(PortalMenuEntity menu : menus) {
			if (upMenuId.equals(menu.getMenuId())) {
				menu.setRAuth(AUTH_YN.Y.name());
				
				if (menu.getUpMenuId() != null && !menu.getUpMenuId().equals("")) {
					setParentFolderRAuth(menu.getUpMenuId());
				}
				
				break;
			}
		}
	}
}