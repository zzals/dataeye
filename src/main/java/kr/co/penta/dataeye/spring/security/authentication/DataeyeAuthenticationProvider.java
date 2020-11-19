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
    @Value("${dataeye.ldap.svId}") private String svId;
    @Value("${dataeye.ldap.svPw}") private String svPw;
    
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
        try {
        	//AD에 정보가 있나 없나만 체크 함. 
        	//if(adLogin(username, password)) {
        		user = userService.loadUserByUsername(username);
        	//}
        	//AD에 정보가 있나 없나만 체크 함.

        	if (null == user) {
                throw new BadCredentialsException(ACCOUNT_AUTH_ERR.ACCOUNT_NOTFOUND.name());
            }
            
            if (!user.isAccountNonExpired()) {
            	throw new BadCredentialsException(ACCOUNT_AUTH_ERR.ACCOUNT_EXPIRED.name());
            } else if (!user.isAccountNonLocked()) {
            	throw new BadCredentialsException(ACCOUNT_AUTH_ERR.ACCOUNT_LOCKED.name());
            } else if (!user.isEnabled()) {
            	throw new BadCredentialsException(ACCOUNT_AUTH_ERR.ACCOUNT_DISABLED.name());
            } else if (!user.isCredentialsNonExpired()) {
            	throw new BadCredentialsException(ACCOUNT_AUTH_ERR.CREDENTIAL_EXPIRED.name());
            } /*else {
            	if (passwordEncoder.matches(password, user.getPassword())) {
            		//인증 성공한 경우 비밀번호 사용기간 만료 체크
            		if (user.isPasswordChangeTime(portalSettings.getPasswordChangePeriod())) {
            			user.setAccountEnabled("N");
            		} else {
            			isLoginSuccess = true;
            			user.setLoginFailCnt(0);
            		}
            	} else {
            		//로그인 실패 건수를 1증가한다.
            		user.setLoginFailCnt(user.getLoginFailCnt()+1);
            		
            		//비밀번호 오류 허용 횟수를 체크하여 계정을 잠근다
            		if (user.isPasswordFailCntLimit(portalSettings.getPasswordFailCount())) {
            			user.setAccountNonLocked("N");
                    	throw new BadCredentialsException(ACCOUNT_AUTH_ERR.ACCOUNT_LOCKED.name());            			
            		} else {
            			throw new BadCredentialsException(ACCOUNT_AUTH_ERR.ACCOUNT_NOTFOUND.name());
            		}
            	}
            }*/
        //} catch(BadCredentialsException e) {
        //	LOG.error("", e);
        //    throw new RuntimeException(errSysMsg);
        } catch(Exception e) {
        	LOG.error("", e);
            throw new RuntimeException(errSysMsg);
        } finally {
			if (user != null) {
				Map<String, Object> userMap = new HashMap<>();
				userMap.put("chgrId", user.getUserId());
				userMap.put("accountNonExpired", user.isAccountNonExpired() ? "Y" : "N");
				userMap.put("accountNonLocked", user.isAccountNonLocked() ? "Y" : "N");
				userMap.put("credentialsNonExpired", user.isCredentialsNonExpired() ? "Y" : "N");
				userMap.put("accountEnabled", user.isEnabled() ? "Y" : "N");
				userMap.put("loginFailCnt", user.getLoginFailCnt());
				userMap.put("isLoginSuccess", isLoginSuccess);
				userMap.put("userId", user.getUserId());
				
				userService.updateAccountAuth(userMap);
			}
		}
        
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
        token.setDetails(user);

        return token;
    }
    
    public boolean adLogin(String username, String password) {
    	boolean adLoginSuccess = false;
    	String errSysMsg = messageSource.getMessage("err.message.system", null, LocaleContextHolder.getLocale());
    	
    	try {
	    	//AD 연결
	    	Hashtable<String, String> env = new Hashtable<String, String>();
	    	env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	    	env.put(Context.PROVIDER_URL, url);
	    	env.put(Context.SECURITY_AUTHENTICATION, "simple");
	    	env.put(Context.SECURITY_PRINCIPAL, domain + "\\" + username);
	    	env.put(Context.SECURITY_CREDENTIALS, password);
	
	    	LdapContext ctx = new InitialLdapContext(env, null);
	    	SearchControls sc = new SearchControls();
	    	sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
			//sc.setReturningAttributes(new String[] { "cn", "mail" });
			//sc.setReturningAttributes(new String[] { "cn", "sn", "employeeNumber", "businessCategory", "description", "carLicense", "displayName","homePhone","registeredAddress","userpassword", "isCriticalSystemObject", "logonCount", "objectClass", "distinguishedName", "sAMAccountName" });
	    	sc.setReturningAttributes(new String[] { "cn","logonCount", "objectClass", "distinguishedName", "sAMAccountName" });	//AD의 검색 할 항목
	    	NamingEnumeration<SearchResult> results = ctx.search(searchBase, "sAMAccountName=" + username, sc);	//검색 실행
				
			//AD에 정보가 있나 없나만 체크 함.
	    	if (results.hasMore()) {
	    		adLoginSuccess = true;
	    	}
        
    	} catch (AuthenticationException e) {
			String msg = e.getMessage();

			if (msg.indexOf("data 525") > 0) {
				System.out.println("사용자를 찾을 수 없음.");
				throw new BadCredentialsException(ACCOUNT_AUTH_ERR.ACCOUNT_NOTFOUND.name());
			} else if (msg.indexOf("data 773") > 0) {
				System.out.println("사용자는 암호를 재설정해야합니다.");
			} else if (msg.indexOf("data 52e") > 0) {
				System.out.println("ID와 비밀번호가 일치하지 않습니다.확인 후 다시 시도해 주십시오.");
				throw new BadCredentialsException(ACCOUNT_AUTH_ERR.ACCOUNT_NOTFOUND.name());
			} else if (msg.indexOf("data 533") > 0) {
				System.out.println("입력한 ID는 비활성화 상태 입니다.");
				throw new BadCredentialsException(ACCOUNT_AUTH_ERR.ACCOUNT_DISABLED.name());
			} else if (msg.indexOf("data 532") > 0) {
				System.out.println("암호가 만료되었습니다.");
				throw new BadCredentialsException(ACCOUNT_AUTH_ERR.PASSWORD_EXPIRED.name());
			} else if (msg.indexOf("data 701") > 0) {
				System.out.println("AD에서 계정이 만료됨");
				throw new BadCredentialsException(ACCOUNT_AUTH_ERR.CREDENTIAL_EXPIRED.name());
			}
		} catch (NamingException e) {
			LOG.error("", e);
            throw new RuntimeException(errSysMsg);
		}
    	
    	return adLoginSuccess;
    }
    
    
}
