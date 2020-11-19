package kr.co.penta.dataeye;

import com.microstrategy.web.beans.BeanFactory;
import com.microstrategy.web.beans.UserGroupBean;
import com.microstrategy.web.beans.WebBeanException;
import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebObjectSource;
import com.microstrategy.web.objects.WebObjectsException;
import com.microstrategy.web.objects.WebObjectsFactory;
import com.microstrategy.web.objects.admin.users.WebUserServicesSource;
import com.microstrategy.webapi.EnumDSSXMLApplicationType;

public class MstrCreateUserGroup {

	public static void main(String[] args) throws WebObjectsException {

		try {
			WebObjectsFactory factory = WebObjectsFactory.getInstance();
			WebIServerSession serverSession = factory.getIServerSession();
			serverSession.setServerName("192.168.100.73");
			serverSession.setServerPort(0);
			serverSession.setProjectName("ebay");
			serverSession.setLogin("Administrator");
			serverSession.setPassword("");
			serverSession.setApplicationType(EnumDSSXMLApplicationType.DssXmlApplicationCustomApp);

			WebObjectsFactory woFact = serverSession.getFactory();
			WebObjectSource wos = woFact.getObjectSource();
			WebUserServicesSource wuss = wos.getUserServicesSource();
			UserGroupBean group = null;

			try {
				group = (UserGroupBean) BeanFactory.getInstance().newBean("UserGroupBean");
				group.setSessionInfo(serverSession);
				group.InitAsNew();
				group.getUserEntityObject().setFullName("NEW GROUP");
				group.save();
			} catch (WebBeanException ex) {
				System.out.println("Error creating  group: " + ex.getMessage());
			}

			serverSession.closeSession();
			System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
