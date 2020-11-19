package kr.co.penta.dataeye;

import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebObjectSource;
import com.microstrategy.web.objects.WebObjectsException;
import com.microstrategy.web.objects.WebObjectsFactory;
import com.microstrategy.web.objects.admin.users.WebLDAPLoginInfo;
import com.microstrategy.web.objects.admin.users.WebStandardLoginInfo;
import com.microstrategy.web.objects.admin.users.WebUser;
import com.microstrategy.web.objects.admin.users.WebUserEntity;
import com.microstrategy.web.objects.admin.users.WebUserGroup;
import com.microstrategy.web.objects.admin.users.WebUserList;
import com.microstrategy.webapi.EnumDSSXMLObjectSubTypes;
import com.microstrategy.webapi.EnumDSSXMLObjectTypes;

public class MstrLdapUserCreate {

	public static void main(String[] args) throws WebObjectsException {
		WebObjectsFactory objectFactory = WebObjectsFactory.getInstance();
		// Session parameters
		WebIServerSession serverSession = objectFactory.getIServerSession();
		serverSession.setServerName("192.168.100.73");
		serverSession.setServerPort(0);
		serverSession.setProjectID("DCA153244D89152BE9198DB4AE7055E8");
		serverSession.setLogin("Administrator");
		serverSession.setPassword("");
		

		WebObjectsFactory woFact = serverSession.getFactory();
		WebObjectSource wos = woFact.getObjectSource();

		// Create the user_entity
		WebUserEntity wue = (WebUserEntity) wos.getNewObject(EnumDSSXMLObjectTypes.DssXmlTypeUser, EnumDSSXMLObjectSubTypes.DssXmlSubTypeUser);

		// Get the LDAP login Info
		WebLDAPLoginInfo ldapInfo = wue.getLDAPLoginInfo();

		// Setup the distinguished name required for LDAP Authentication
		ldapInfo.setLDAPDN("CN=이 준서,OU=B팀,OU=BIC팀,DC=bat,DC=penta,DC=co,DC=kr");

		// Set all the other parameters
		WebUser user = (WebUser) wue;
		WebStandardLoginInfo loginInfo = user.getStandardLoginInfo();
		user.setLoginName("junseo");
		user.setFullName("이준서");
		loginInfo.setPassword("Penta1234!@");
		user.setEnabled(true);
		//loginInfo.setStandardAuthAllowed(true);
		wos.save(user);

		// Add the user to LDAP Users group whose ID is 4FF5121511D49BC8C00014894C78604F
		WebUserGroup group = (WebUserGroup) wos.getObject("683B44EF4938F810B2EF388519FEDDE7", EnumDSSXMLObjectTypes.DssXmlTypeUser, EnumDSSXMLObjectSubTypes.DssXmlSubTypeUserGroup);

		WebUserList myList = group.getMembers();
		myList.add(user);
		wos.save(group);

	}

}
