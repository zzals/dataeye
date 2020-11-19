package kr.co.penta.dataeye;

import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebObjectsException;
import com.microstrategy.web.objects.WebObjectsFactory;

public class SessionManagementSample {

	private static WebObjectsFactory factory = null;
	private static WebIServerSession serverSession = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		getSession();

		StringBuilder urlSB = new StringBuilder();
		urlSB.append("http").append("://").append("192.168.100.73:8080"); // Web Server name and port
		urlSB.append("/MicroStrategy/servlet/mstrWeb");
		urlSB.append("?server=").append("WIN-4FNG27AEFGQ"); // I Server name
		urlSB.append("&port=0");
		urlSB.append("&project=").append("eBay"); // Project name
		urlSB.append("&evt=").append(3186);
		urlSB.append("&subscriptionID=").append("E53CBD9842D136E94C707B957D07D852"); // Report ID
		urlSB.append("&src=mstrWeb.").append(3186);
		urlSB.append("&usrSmgr=").append(serverSession.saveState(0));

		System.out.println(urlSB.toString()); // Final URL is printed to console.
		System.out.println();
	}

	/**
	 * Creates a new session that can be reused by other classes
	 * 
	 * @return WebIServerSession
	 */
	public static WebIServerSession getSession() {
		if (serverSession == null) {
			// create factory object
			factory = WebObjectsFactory.getInstance();
			serverSession = factory.getIServerSession();

			// Set up session properties
			serverSession.setServerName("192.168.100.73"); // Should be replaced with the name of an Intelligence Server
			serverSession.setServerPort(0);
			serverSession.setProjectName("eBay"); // Project where the session should be created
			serverSession.setLogin("Administrator"); // User ID
			serverSession.setPassword(""); // Password

			// Initialize the session with the above parameters
			try {
				System.out.println("\nSession created with ID: " + serverSession.getSessionID());
				System.out.println("Session State: "+ serverSession.saveState(0));
			} catch (WebObjectsException ex) {
				handleError(null, "Error creating session:" + ex.getMessage());
			}
		}
		// Return session
		return serverSession;
	}

	/**
	 * Close Intelligence Server Session
	 * 
	 * @param serverSession WebIServerSession
	 */
	public static void closeSession(WebIServerSession serverSession) {
		try {
			serverSession.closeSession();
		} catch (WebObjectsException ex) {
			System.out.println("Error closing session:" + ex.getMessage());
			return;
		}
		System.out.println("Session closed.");
	}

	/**
	 * Print out error message, close session and abort execution
	 * 
	 * @param serverSession WebIServerSession
	 * @param message       String
	 */
	public static void handleError(WebIServerSession serverSession, String message) {
		System.out.println(message);
		if (serverSession != null) {
			closeSession(serverSession);
		}
		System.exit(0);
	}
}
