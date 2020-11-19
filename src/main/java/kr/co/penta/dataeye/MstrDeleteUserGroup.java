package kr.co.penta.dataeye;

import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebObjectSource;
import com.microstrategy.web.objects.WebObjectsException;
import com.microstrategy.web.objects.WebObjectsFactory;
import com.microstrategy.web.objects.admin.users.WebUserEntity;
import com.microstrategy.webapi.EnumDSSXMLApplicationType;
import com.microstrategy.webapi.EnumDSSXMLObjectTypes;

public class MstrDeleteUserGroup {

	public static void main(String[] args) throws WebObjectsException {

		try {

			WebObjectsFactory factory = WebObjectsFactory.getInstance();

			WebIServerSession serverSession = factory.getIServerSession();
			serverSession.setServerName("192.168.100.73");
			serverSession.setServerPort(0);
			serverSession.setProjectName("eBay");
			serverSession.setLogin("Administrator");
			serverSession.setPassword("");
			serverSession.setApplicationType(EnumDSSXMLApplicationType.DssXmlApplicationCustomApp);

			WebObjectsFactory woFact = serverSession.getFactory();
			WebObjectSource wos = woFact.getObjectSource();

			// Getting the User Group Object
			WebUserEntity userEntity = (WebUserEntity) wos.getObject("D3AAE1BC40EC71E960217B8E3DFBCFF1", EnumDSSXMLObjectTypes.DssXmlTypeUser);

			// Deleting the User Group Object
			wos.deleteObject(userEntity);
			serverSession.closeSession();
			System.out.println("Done");

		} catch (Exception e) {
			e.printStackTrace();

		}

	}
}
