package kr.co.penta.dataeye.customizing.controller;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/customizing", method = RequestMethod.POST)
public class CustomizingController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${dataeye.ldap.url}") private String url;
    @Value("${dataeye.ldap.domain}") private String domain;
    @Value("${dataeye.ldap.searchBase}") private String searchBase;
    
	@RequestMapping(value = "/ldap/user", method = RequestMethod.POST)
	public @ResponseBody String user(HttpServletRequest request) {
			String result = "fail";	   
		   
   		String userId = (String)request.getParameter("de_username");
   		String userPass = (String)request.getParameter("de_pwd");
		   
		Hashtable<String, String> env = new Hashtable<String, String>();
    	env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    	env.put(Context.PROVIDER_URL, url);
    	env.put(Context.SECURITY_AUTHENTICATION, "simple");
    	env.put(Context.SECURITY_PRINCIPAL, domain + "\\" + userId);
    	env.put(Context.SECURITY_CREDENTIALS, userPass);

		try {
			LdapContext ctx = new InitialLdapContext(env, null);
			
			SearchControls sc = new SearchControls();
	    	sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
			//sc.setReturningAttributes(new String[] { "cn", "mail" });
			//sc.setReturningAttributes(new String[] { "cn", "sn", "employeeNumber", "businessCategory", "description", "carLicense", "displayName","homePhone","registeredAddress","userpassword", "isCriticalSystemObject", "logonCount", "objectClass", "distinguishedName", "sAMAccountName" });
	    	sc.setReturningAttributes(new String[] { "cn","logonCount", "objectClass", "distinguishedName", "sAMAccountName" });	//AD의 검색 할 항목
	    	NamingEnumeration<SearchResult> results = ctx.search(searchBase, "sAMAccountName=" + userId, sc);	//검색 실행
	    	
	    	//AD에 정보가 있나 없나만 체크 함.
	    	if (results.hasMore()) {
	    		result = "success";
	    	}
		} catch (NamingException e) {
			result = "fail";
		}
		
    	return result;
	}
	

}
