package kr.co.penta.dataeye.spring.security.authentication;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import kr.co.penta.dataeye.spring.config.properties.PortalSettings;
import kr.co.penta.dataeye.spring.security.core.User;
import kr.co.penta.dataeye.spring.security.core.UserService;

@Component
public class DataeyeAuthenticationProvider implements AuthenticationProvider {
    static final Logger LOG = LoggerFactory.getLogger(DataeyeAuthenticationProvider.class);

    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PortalSettings portalSettings;
    
    @Value("${dataeye.ldap.url}") private String url;
    @Value("${dataeye.ldap.domain}") private String domain;
    @Value("${dataeye.ldap.searchBase}") private String searchBase;
    
    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String)authentication.getCredentials();
        
        if (authentication.isAuthenticated()) {
            return authentication;
        }
        
        String errSysMsg = messageSource.getMessage("err.message.system", null, LocaleContextHolder.getLocale());
        User user = null;
        boolean isLoginSuccess = false;
        int pUser = 0;
        try {
        	
        	user = userService.loadUserByUsername(domain +"\\"+ username);

        	if (null == user) {
                throw new BadCredentialsException(ACCOUNT_AUTH_ERR.ACCOUNT_NOTFOUND.name());
            } else {
            	isLoginSuccess = true;
            	pUser = userService.pUserCnt(user.getSabun());
            }
            
        } catch(Exception e) {
        	LOG.error("", e);
            throw new RuntimeException(errSysMsg);
        } finally {
			if (user != null) {
				
				Map<String, Object> userMap = new HashMap<>();
				userMap.put("chgrId", user.getSabun());
				//userMap.put("accountNonExpired", user.isAccountNonExpired() ? "Y" : "N");
				//userMap.put("accountNonLocked", user.isAccountNonLocked() ? "Y" : "N");
				//userMap.put("credentialsNonExpired", user.isCredentialsNonExpired() ? "Y" : "N");
				//userMap.put("accountEnabled", user.isEnabled() ? "Y" : "N");
				//userMap.put("loginFailCnt", user.getLoginFailCnt());
				userMap.put("isLoginSuccess", isLoginSuccess);
				userMap.put("userId", user.getSabun());
				
				if(pUser == 0) {
					userMap.put("cretrId", user.getSabun());
					
					userService.insertAccountAuth(userMap);
				} else {
					userService.updateAccountAuth(userMap);
				}
				
			}
		}
        
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
        token.setDetails(user);

        return token;
    }
    
}
