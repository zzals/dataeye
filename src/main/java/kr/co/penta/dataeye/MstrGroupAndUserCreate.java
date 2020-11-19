package kr.co.penta.dataeye;

import com.microstrategy.utils.serialization.EnumWebPersistableState;
import com.microstrategy.web.objects.WebFolder;
import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebObjectSource;
import com.microstrategy.web.objects.WebObjectsException;
import com.microstrategy.web.objects.WebObjectsFactory;
import com.microstrategy.web.objects.WebSearch;
import com.microstrategy.web.objects.admin.users.WebUser;
import com.microstrategy.web.objects.admin.users.WebUserGroup;
import com.microstrategy.webapi.EnumDSSXMLAuthModes;
import com.microstrategy.webapi.EnumDSSXMLObjectSubTypes;
import com.microstrategy.webapi.EnumDSSXMLObjectTypes;
import com.microstrategy.webapi.EnumDSSXMLPrivilegeTypes;
import com.microstrategy.webapi.EnumDSSXMLSearchDomain;
import com.microstrategy.webapi.EnumDSSXMLSearchFlags;

public class MstrGroupAndUserCreate {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		addMstrUser("admin2","결제");
	}
	
	public static String addMstrUser(String usr_id, String group_nm) {
		
		System.out.println("#usr_id=" + usr_id);
		System.out.println("#group_nm=" + group_nm);
		
		WebObjectsFactory webObjectsFactory = WebObjectsFactory.getInstance();
        WebIServerSession adminSession = webObjectsFactory.getIServerSession();
        WebObjectSource adminOS = null;
        WebUser user = null;
        WebSearch search = null;
        WebSearch groupSearch = null;
        try {
        	adminSession.setServerName("192.168.100.73");
        	adminSession.setServerPort(0);
        	adminSession.setProjectID("DCA153244D89152BE9198DB4AE7055E8");
			//adminSession.setAuthMode(EnumDSSXMLAuthModes.DssXmlAuthLDAP);
        	adminSession.setLogin("yjs9306");
			adminSession.setPassword("Penta1234!@");
        	//adminSession.setPassword("");
        	adminSession.setAuthMode(EnumDSSXMLAuthModes.DssXmlAuthLDAP);
        	//adminSession.reconnect(); // save
	        // save session
	        String sessionId = adminSession.getSessionID();
	        String sessionState = adminSession.saveState(EnumWebPersistableState.MAXIMAL_STATE_INFO);
			
	        
	        System.out.println("Session ID : " + sessionId);
	        System.out.println("Session State : " + sessionState);
	        System.out.println("Authentication : Success");
	        
	        try {
	        	
	        	  WebObjectsFactory adminFactory;

	              try {
	                
	                  //refresh admin Session
	                  adminSession.refresh();	              	    
	                  //get adminFacotry
	                  adminFactory = adminSession.getFactory();
	                  //get WebObjectSource for admin 
		              adminOS = adminFactory.getObjectSource();
	              	    
	              } catch (Exception e) {
	            	  System.out.println("11Authentication : Password Expired" + e.toString());
	              
	              }
	              	
	            
	            search = adminOS.getNewSearchObject();	            
	            

	            //search for login id
	            search.setAbbreviationPattern(usr_id);
	            search.setSearchFlags(search.getSearchFlags() | EnumDSSXMLSearchFlags.DssXmlSearchAbbreviationWildCard);
	            //asynchronized search
	            search.setAsync(false);
	            //search for user
	            search.types().add(new Integer(EnumDSSXMLObjectSubTypes.DssXmlSubTypeUser));
	            //search on repository
	            search.setDomain(EnumDSSXMLSearchDomain.DssXmlSearchDomainRepository);

	            search.submit();
		            
	            WebFolder f = search.getResults();

	            //since login id is unique identity, if found, the first one is the user
	            if (f.size() > 0) {
	                user = (WebUser) f.get(0);
	            }
		            
		        } catch (Exception e) {
		        	 System.out.println("22Authentication : Password Expired" + e.toString());
		        }
		        
		        
		            if (user == null) {
		                //does not exist
		                user = (WebUser) adminOS.getNewObject(EnumDSSXMLObjectTypes.DssXmlTypeUser, EnumDSSXMLObjectSubTypes.DssXmlSubTypeUser);
		                user.setLoginName(usr_id);
		                user.setFullName("이준서");
			            user.getStandardLoginInfo().setPassword("");
			            user.getStandardLoginInfo().setPasswordModifiable(false);
				            
			            //have to enable and set privileges
			            user.setEnabled(true);
				            			            
			            //change or add more privileges if you need. 
			            user.getLocalPrivileges().grant(EnumDSSXMLPrivilegeTypes.DssXmlPrivilegesWebUser);
			            user.getLocalPrivileges().grant(EnumDSSXMLPrivilegeTypes.DssXmlPrivilegesWebAdvancedDrilling);
			            user.getLocalPrivileges().grant(EnumDSSXMLPrivilegeTypes.DssXmlPrivilegesWebViewHistoryList);
			            user.getLocalPrivileges().grant(EnumDSSXMLPrivilegeTypes.DssXmlPrivilegesWebExecuteRWDocument);
			            user.getLocalPrivileges().grant(EnumDSSXMLPrivilegeTypes.DssXmlPrivilegesWebChangeViewMode);
			            user.getLocalPrivileges().grant(EnumDSSXMLPrivilegeTypes.DssXmlPrivilegesWebObjectSearch);
			            user.getLocalPrivileges().grant(EnumDSSXMLPrivilegeTypes.DssXmlPrivilegesWebSort);
			            user.getLocalPrivileges().grant(EnumDSSXMLPrivilegeTypes.DssXmlPrivilegesWebSwitchPageByElements);
			            
			            adminOS.save(user); 
			            
			            groupSearch = adminOS.getNewSearchObject();		            
			            groupSearch.setNamePattern(group_nm);
			            groupSearch.setAsync(false);
			            groupSearch.types().add(EnumDSSXMLObjectSubTypes.DssXmlSubTypeUserGroup);			            
			            groupSearch.setDomain(EnumDSSXMLSearchDomain.DssXmlSearchDomainRepository);

		                WebUserGroup group = (WebUserGroup)performSearch(groupSearch);
		                if(group!=null){
		                    if(user!=null){
		                        group.getMembers().add(user);
		                    }
		                }
		                adminOS.save(group);

			            System.out.println("사용자=" + usr_id + " mstr   등록");
		            } else {
		            	System.out.println("이미 등록된 사용자");
		             //   user.populate();
		            }
		        
		    }
		    catch (WebObjectsException e) {
		        if (e.getErrorCode() == -2147216963) {
		            System.out.println("Authentication : Password Expired");
		        }
		        else {
		            System.out.println("Authentication : Failed");
		        }
		    }
		    finally {
		    	try {
		    		adminSession.closeSession();
		    	}
		    	catch (WebObjectsException e) {
		    	}
		    }
		return "";
	}

	public static Object performSearch(WebSearch search){
        try {
            search.submit();
            WebFolder folder = search.getResults();
            if(folder.size()>0){
                if(folder.size()==1){
                    return folder.get(0);
                } else {
                    System.out.println("Search returns more than 1 object, returning first object");
                    return folder.get(0);
                }
            }
        } catch (WebObjectsException ex) {
            System.out.println("Error performing search: "+ex.getMessage());
        }
        return null;
    }
}
