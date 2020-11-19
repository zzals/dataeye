package kr.co.penta.dataeye;

import java.util.HashMap;
import java.util.Map;

import com.microstrategy.utils.serialization.EnumWebPersistableState;
import com.microstrategy.web.objects.WebFolder;
import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebObjectInfo;
import com.microstrategy.web.objects.WebObjectSource;
import com.microstrategy.web.objects.WebObjectsException;
import com.microstrategy.web.objects.WebObjectsFactory;
import com.microstrategy.web.objects.WebReportSource;
import com.microstrategy.web.objects.WebSearch;
import com.microstrategy.webapi.EnumDSSXMLAuthModes;
import com.microstrategy.webapi.EnumDSSXMLObjectTypes;
import com.microstrategy.webapi.EnumDSSXMLSearchDomain;

public class MstrFolder {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {

		//폴더 가져오기		
		WebObjectsFactory webObjectsFactory = WebObjectsFactory.getInstance();
		WebIServerSession webIServerSession = webObjectsFactory.getIServerSession();
		
		try {
			  
			webIServerSession.setServerName("192.168.100.73");
			webIServerSession.setServerPort(0);
			webIServerSession.setProjectID("DCA153244D89152BE9198DB4AE7055E8");
			//webIServerSession.setAuthMode(EnumDSSXMLAuthModes.DssXmlAuthLDAP);
			webIServerSession.setLogin("Administrator");
			//webIServerSession.setPassword("Penta1234!@");
			webIServerSession.setPassword("");
			webIServerSession.setAuthMode(EnumDSSXMLAuthModes.DssXmlAuthStandard);
			webIServerSession.reconnect(); // save
			
			String sessionId = webIServerSession.getSessionID(); 
			String sessionState = webIServerSession.saveState(EnumWebPersistableState.MINIMAL_STATE_INFO);
		   
			WebObjectSource webObjectSource = webIServerSession.getFactory().getObjectSource();
			WebReportSource webReportSource = webIServerSession.getFactory().getReportSource();
			WebSearch webSearch = webObjectSource.getNewSearchObject();
	        
	       
	        /*
	        webSearch.setAsync(false);
	        webSearch.setDisplayName("*");+
	        webSearch.setNamePattern("*" + "aa" + "*");
	        webSearch.setDomain(EnumDSSXMLSearchDomain.DssXmlSearchConfigurationAndAllProjects);
	        */
	      
	        
	        webSearch.setAsync(false);
	        webSearch.setScope(1);
	        //webSearch.setAbbreviationPattern("Administrator");
	        //webSearch.setAsync(false);
	        webSearch.setDisplayName("*");
	        //webSearch.setNamePattern("*" + "test" + "*");
	        //webSearch.types().add(EnumDSSXMLObjectSubTypes.DssXmlSubTypeFolder);
	        webSearch.setDomain(EnumDSSXMLSearchDomain.DssXmlSearchDomainProject);
	        webSearch.submit();
	        
	        
	        System.out.println("##getRoot=" + webObjectSource.getRoot().get(0).getDisplayName());
	        System.out.println("##getRoot=" + webObjectSource.getRoot().get(1).getDisplayName());
	        System.out.println("##getRoot=" + webObjectSource.getRoot().get(2).getDisplayName());
	        System.out.println("##getRoot=" + webObjectSource.getRoot().get(3).getDisplayName());
	        
	        System.out.println("##webSearch=" + webSearch.getName());
	        
	        System.out.println("##webSearch=" + webSearch);
	        
	        WebFolder lSearchResults = webSearch.getResults();
	        
	        System.out.println("##lSearchResults=" + lSearchResults);
	        if (lSearchResults != null && lSearchResults.size() > 0) {
              int lResultsCount = lSearchResults.size();

              System.out.println("EnumDSSXMLObjectTypes.DssXmlTypeDocumentDefinition : "+EnumDSSXMLObjectTypes.DssXmlTypeDocumentDefinition);
              System.out.println("EnumDSSXMLObjectTypes.DssXmlTypeFolder : "+EnumDSSXMLObjectTypes.DssXmlTypeFolder);
              System.out.println("EnumDSSXMLObjectTypes.DssXmlTypeReportDefinition : "+EnumDSSXMLObjectTypes.DssXmlTypeReportDefinition);
              System.out.println("EnumDSSXMLObjectTypes.DssXmlTypeShortcut : "+EnumDSSXMLObjectTypes.DssXmlTypeShortcut);
              
              for (int lIndex = 0; lIndex < lResultsCount; lIndex++) {
              	 WebObjectInfo lObjectInfo = lSearchResults.get(lIndex);
              	 String name = lObjectInfo.getName();
              	 int type = lObjectInfo.getType();
              	 String id = lObjectInfo.getID();
              	 String desc = lObjectInfo.getDescription();
              	 String cType = lObjectInfo.getContainerType();
              	 String abbr = lObjectInfo.getAbbreviation();
              	 String pid = lObjectInfo.getProjectId();
              	 int stype = lObjectInfo.getSubType();
              	 String pname = lObjectInfo.getProjectName();
              	 WebFolder pResult = lObjectInfo.getParent();
              	
              	 String parentId = "";
              	 
          		 if(pResult == null) {
          			 parentId = "";
          		 } else {
          			 parentId = pResult.getID();
          		 }
          		 
          		 
              	//if(type==3||type==8) {
          		//if(type==55) {	//document
          		//if(type==7) {	//document
           		//if(type==10 || type==12) {	//필터 상세
              		System.out.println("---------------------------------------------------------");
              		 System.out.println("name=" + name);
              		 System.out.println("type=" + type);
              		 System.out.println("id=" + id);
              		 System.out.println("desc=" + desc);
              		 System.out.println("cType=" + cType);
              		 System.out.println("abbr=" + abbr);
              		 System.out.println("pid=" + pid);
              		 System.out.println("stype=" + stype);
              		 System.out.println("pname=" + pname);
              		 System.out.println("parentId=" + parentId);
              		System.out.println(lObjectInfo.getAncestors());
              		System.out.println(lObjectInfo.getXML());
              				
              		 Map param = new HashMap();
              		 param.put("id", id);
              		 param.put("name", name);
              		 param.put("type", type);
              		 param.put("desc", "-");
              		 param.put("pid", parentId);
              		 
              		 
              		 //bpaAdminService.saveMstrMeta(param);
              		 //System.out.println("## 저장완료  ##");
              		 System.out.println("---------------------------------------------------------");
              	 //}
              	 
              }
	        }
		 
		   System.out.println("#sessionState=" + sessionState);
		  //request.setAttribute("sessionState", sessionState);
		  //request.setAttribute("sessionId", sessionId); 
		  
		  /*
		  MstrRestUtil mstrRestUtil = new MstrRestUtil("Administrator","");
		  
		  List result = mstrRestUtil.getMyRepportList("B19DEDCC11D4E0EFC000EB9495D0F44F");
				  
		  System.out.println("##result = " + result);
		  
		  */
		  
		  
		  } catch (WebObjectsException e) {
			  if (e.getErrorCode() == -2147216963) {
				  	System.out.println("Authentication : Password Expired"); 
			  } else {
				  System.out.println("Authentication : Failed"); 
			  } 
		 } finally {
			 try {
				 	webIServerSession.closeSession(); 
			} catch (WebObjectsException e) { } 
		 }
		
		//return "admin/main";
	}
}
