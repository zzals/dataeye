package kr.co.penta.dataeye.spring.security.core;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
	User loadUserByUsername(final String username) throws UsernameNotFoundException;
	Collection<GrantedAuthority> getAuthorities(String username);
	void updateAccountAuth(Map<String, Object> user);
} 