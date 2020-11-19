package kr.co.penta.dataeye.spring.security.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.co.penta.dataeye.common.entities.meta.PenSysUserGrpM;
import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.spring.mybatis.dao.UserDao;

@Service
@Transactional(propagation= Propagation.SUPPORTS, readOnly=true)
public class UserServiceImpl implements UserService {
     
     @Autowired UserDao userDao;

     @Override
     @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
     public User loadUserByUsername(String username) throws UsernameNotFoundException {
          User user = userDao.getUser(username);
          if (null != user) {
               /*List<Map<String, Object>> actionAuth = userDao.getActionAuth(username);
               user.setActionAuth(actionAuth);*/
               user.setAuthorities(getAuthorities(username));
          }

          return user;
     }

     @Transactional(propagation=Propagation.REQUIRED, readOnly=false)
     public Collection<GrantedAuthority> getAuthorities(String username) {
          List<PenSysUserGrpM> userGroups = userDao.getAuthority(username);
          List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
          for (PenSysUserGrpM userGroup : userGroups) {
        	  if (userGroup.getUserGrpId().equals(DataEyeSettingsHolder.getInstance().getMgrRole().getAdminRoleGroup()))
        		  authorities.add(new SimpleGrantedAuthority(DataEyeSettingsHolder.getInstance().getMgrRole().getAdminRole()));
        	  else if (userGroup.getUserGrpId().equals(DataEyeSettingsHolder.getInstance().getMgrRole().getSystemRoleGroup()))
        		  authorities.add(new SimpleGrantedAuthority(DataEyeSettingsHolder.getInstance().getMgrRole().getSystemRole()));
          }
          return authorities;
     }

	@Override
	public void updateAccountAuth(Map<String, Object> user) {
		userDao.updateAccountAuth(user);
	}
}