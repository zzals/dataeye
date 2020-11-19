package kr.co.penta.dataeye;

import com.microstrategy.utils.serialization.EnumWebPersistableState;
import com.microstrategy.web.app.AbstractExternalSecurity;
import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebObjectsException;
import com.microstrategy.web.objects.WebObjectsFactory;
import com.microstrategy.webapi.EnumDSSXMLApplicationType;
import com.microstrategy.webapi.EnumDSSXMLAuthModes;

public class TrustedAuthentication extends AbstractExternalSecurity {

	private static String INTELLIGENCE_SERVER = "WIN-4FNG27AEFGQ";
    private static String PROJECT_NAME = "ebay";
    
    /*
     * The user ID must be set in the user object. 
     * This can be done by editing the user in MicroStrategy Developer and navigating to Authentication > Metadata > Trusted Authentication Request.
     */
    private static String TRUSTED_USER_ID = "junseo";
    
    /*
     * - The trust token can be found in the WEB-INF/xml/ folder of MicroStrategy Web. 
     * - In the below example, the token file is called "APS-TSIEBLER-VM.token" and contains the "TRUST_TOKEN_VALUE" shown below.
     */
    private static String TRUST_TOKEN_VALUE = "TokenD8A90AAF4EE9941A17337A9A165C2D0F";
    
    public static void main(String[] args) {
        try {
            WebIServerSession mySession = getSessionWithToken(INTELLIGENCE_SERVER, PROJECT_NAME, TRUSTED_USER_ID, TRUST_TOKEN_VALUE);
            
        } catch (WebObjectsException ex) {
            System.out.println("getSessionWithToken - Error creating session - check DSSErrors.log authentication trace for details: " + ex.getMessage());
        }
    }
    
    public static WebIServerSession getSessionWithToken(String intelligenceServerName, String projectName, String trustedUserID, String trustTokenValue) throws WebObjectsException {
        WebIServerSession serverSession = WebObjectsFactory.getInstance().getIServerSession();
        
        serverSession.setApplicationType(EnumDSSXMLApplicationType.DssXmlApplicationCustomApp);
        //serverSession.setAuthMode(EnumDSSXMLAuthModes.DssXmlAuthSimpleSecurityPlugIn);
        serverSession.setAuthMode(EnumDSSXMLAuthModes.DssXmlAuthTrusted);
        serverSession.setTrustToken(trustTokenValue);

        // Intelligence server and project
        serverSession.setServerName(intelligenceServerName);
        serverSession.setProjectName(projectName);
        
        // This is the trusted User ID (NOT the username)
        serverSession.setLogin(trustedUserID);
        serverSession.saveState(EnumWebPersistableState.MAXIMAL_STATE_INFO);

        System.out.println("getSessionWithToken - Session created with ID: " + serverSession.getUserInfo().getID());

        System.out.println(COLLECT_SESSION_NOW);
        System.out.println(USE_CUSTOM_LOGIN_PAGE);
        
        return serverSession;
    }
}
