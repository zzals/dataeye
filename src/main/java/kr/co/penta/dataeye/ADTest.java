package kr.co.penta.dataeye;

import java.util.Calendar;
import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class ADTest {
	
	public static void main(String[] args) throws Exception {
		
		try {
			
			String ntUserId = "pst1552";
			String ntPasswd = "2wsx3edc@#";
//			String ntUserId = "dataeye";
//			String ntPasswd = "WQcKAH\"^AZB)?2*?";
			String url = "ldap://192.168.71.52";
			String domain = "ebaykorea";
			String searchBase = "DC=ebaykorea,DC=corp";

			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, url);
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_PRINCIPAL, domain + "\\" + ntUserId);
			env.put(Context.SECURITY_CREDENTIALS, ntPasswd);

			LdapContext ctx = new InitialLdapContext(env, null);
			SearchControls sc = new SearchControls();
			sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
			//sc.setReturningAttributes(new String[] { "cn", "mail" });
			sc.setReturningAttributes(new String[] { "cn", "sn", "employeeNumber", "businessCategory", "description", "carLicense", "displayName","homePhone","registeredAddress","userpassword", "isCriticalSystemObject", "logonCount", "objectClass", "distinguishedName", "sAMAccountName" });
			NamingEnumeration<SearchResult> results = ctx.search(searchBase, "sAMAccountName=" + ntUserId, sc);	//사용자 정보
			//NamingEnumeration<SearchResult> results = ctx.search(searchBase, "(objectclass=user)", sc);	//사용자 리스트
			
			while (results.hasMoreElements()) {
				SearchResult sr = (SearchResult) results.next();
				Attributes attrs = sr.getAttributes();
				String commonName = attrs.get("distinguishedName").get(0).toString();
				System.out.println("Attributes: " + attrs);
				System.out.println("commonName: " + commonName);
			}
			
		} catch (AuthenticationException e) {
			String msg = e.getMessage();
System.out.println(msg);
			if (msg.indexOf("data 525") > 0) {
				System.out.println("사용자를 찾을 수 없음.");
			} else if (msg.indexOf("data 773") > 0) {
				System.out.println("사용자는 암호를 재설정해야합니다.");
			} else if (msg.indexOf("data 52e") > 0) {
				System.out.println("ID와 비밀번호가 일치하지 않습니다.확인 후 다시 시도해 주십시오.");
			} else if (msg.indexOf("data 533") > 0) {
				System.out.println("입력한 ID는 비활성화 상태 입니다.");
			} else if (msg.indexOf("data 532") > 0) {
				System.out.println("암호가 만료되었습니다.");
			} else if (msg.indexOf("data 701") > 0) {
				System.out.println("AD에서 계정이 만료됨");
			} else {
				System.out.println("정상!");
			}
		}

		
	}
	
}
