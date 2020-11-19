package kr.co.penta.dataeye;

import java.util.Enumeration;

import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebObjectInfo;
import com.microstrategy.web.objects.WebObjectSource;
import com.microstrategy.web.objects.WebObjectsException;
import com.microstrategy.web.objects.admin.users.WebUser;
import com.microstrategy.web.objects.admin.users.WebUserGroup;
import com.microstrategy.web.objects.admin.users.WebUserList;
import com.microstrategy.webapi.EnumDSSXMLObjectSubTypes;
import com.microstrategy.webapi.EnumDSSXMLObjectTypes;

public class ListMembers {
	
	//그룹에 속한 사용자 리스트
	
	public static void main(String[] args) {
	    // TODO Auto-generated method stub
	    // Connectivity for the intelligence server
	    String intelligenceServerName = "192.168.100.73";
	    String projectName = "ebay";
	    String microstrategyUsername = "Administrator";
	    String microstrategyPassword = "";
	    
	    // Create our I-Server Session
	    WebIServerSession session = SessionManager.getSessionWithDetails(intelligenceServerName, projectName, microstrategyUsername, microstrategyPassword);
	    
	    // 
	    String groupId = "C82C6B1011D2894CC0009D9F29718E4F";
	    listMembersInGroup(session, groupId);
	  }

	// This method retrieves a group from the metadata, using the object Id, and
	// lists the first-class children.
	// These may be users or groups that can be differentiated using the sub type.
	// Note: as this method uses populate() on the group reference, this can be slow
	// in bigger environments. See KB30899 for an alternative.
	public static void listMembersInGroup(WebIServerSession serverSession, String groupId) {
	    WebObjectSource wos = serverSession.getFactory().getObjectSource();
	    try {
	      // retrieve group from metadata
	      WebUserGroup group = (WebUserGroup)wos.getObject(groupId, EnumDSSXMLObjectTypes.DssXmlTypeUser, EnumDSSXMLObjectSubTypes.DssXmlSubTypeUserGroup);
	      
	      // without populate this group is empty. Large groups may take a long time to populate, see KB30899
	      group.populate();
	      
	      WebUserList memberList = group.getMembers();
	      System.out.println("children: " + memberList.size());
	      
	      Enumeration<WebUser> members = memberList.elements();
	      
	      while(members.hasMoreElements()) {
	        WebObjectInfo child = members.nextElement();
	        
	        switch (child.getSubType()) {
	          case EnumDSSXMLObjectSubTypes.DssXmlSubTypeUserGroup:
	            WebUserGroup childGroup = (WebUserGroup)child;
	            System.out.println("-- Child group: " + childGroup.getDisplayName() + " " + childGroup.getID());
	            break;
	            
	          case EnumDSSXMLObjectSubTypes.DssXmlSubTypeUser:
	            WebUser childUser = (WebUser)child;
	            System.out.println("-- Child user: " + childUser.getAbbreviation() + " " + childUser.getFullName() + " " + childUser.getID() + " " + childUser.getXML());
	            break;
	            
	          default:
	            System.out.println("-- Unhandled object subtype: " + child.getSubType() + " " + child.getID());
	            break;
	        }
	      }
	      
	      System.out.println("reached end.");
	      
	    } catch (WebObjectsException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    } catch (IllegalArgumentException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    }
	  }
}

