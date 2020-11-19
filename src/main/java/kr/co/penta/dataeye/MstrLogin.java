package kr.co.penta.dataeye;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.microstrategy.utils.serialization.EnumWebPersistableState;
import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebObjectsException;
import com.microstrategy.web.objects.WebObjectsFactory;
import com.microstrategy.webapi.EnumDSSXMLAuthModes;

public class MstrLogin {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		
		
		//서버 로그인				
		WebObjectsFactory webObjectsFactory = WebObjectsFactory.getInstance();
		WebIServerSession webIServerSession = webObjectsFactory.getIServerSession();
		try {
			webIServerSession.setServerName("192.168.100.73");
			//webIServerSession.setServerName("192.168.100.73");
			webIServerSession.setServerPort(0);
			//webIServerSession.setProjectName("ebay");
			webIServerSession.setProjectID("DCA153244D89152BE9198DB4AE7055E8");
			
			//webIServerSession.setServerPort(8080);
			webIServerSession.setAuthMode(EnumDSSXMLAuthModes.DssXmlAuthStandard);
			webIServerSession.setLogin("administrator");
			webIServerSession.setPassword("");
//			webIServerSession.setAuthMode(EnumDSSXMLAuthModes.DssXmlAuthLDAP);
//			webIServerSession.setLogin("yjs9306");
//			webIServerSession.setPassword("Penta1234!@");
			//webIServerSession.setClientID("192.168.40.64");
			webIServerSession.reconnect();
			
			Locale locale = Locale.KOREA;
			webIServerSession.setLocale(locale);
			
			// save session
			String sessionId = webIServerSession.getSessionID(); 
			//String sessionId = webIServerSession.getUserInfo().getID();
			String sessionState = webIServerSession.saveState(EnumWebPersistableState.MAXIMAL_STATE_INFO);
			String sessionNm = webIServerSession.getTrustToken();
			System.out.println("Session ID : " + sessionId);
			System.out.println("Session userid : " + webIServerSession.getUserInfo().getID());
			System.out.println("Session NM : " + sessionNm);
			System.out.println("Session State : " + sessionState);

			System.out.println("Authentication : Success");
		} catch (WebObjectsException e) {
			if (e.getErrorCode() == -2147216963) {
				System.out.println("Authentication : Password Expired");
			} else {
				System.out.println("Authentication : Failed");
			}
		} finally {
			try {
				webIServerSession.closeSession();
			} catch (WebObjectsException e) {
			}
		}
		
		System.out.println(isBefore("20200720111000"));
	}
	
	private static String getRandomString(int length) {
	    
	    char charPool[] = {
	            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
	            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
	            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	    
	    StringBuffer strBuf = new StringBuffer();

	    for (int i = 0; i < length; i++) {
	        strBuf.append(charPool[(int) Math.floor((Math.random() * charPool.length))]);
	    }
	    
	    System.out.println(strBuf.toString());
	    return strBuf.toString();
	}
	
	private static boolean isBefore(String timestemp) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		String today = format.format(now);
		
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date start_time = dataFormat.parse(timestemp);
		Date end_time = dataFormat.parse(today);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(start_time);
		cal.add(Calendar.SECOND, 10);
		end_time = cal.getTime();
		
		System.out.println(start_time + " " + now +" "+ end_time);
		System.out.println(start_time.before(now));
		System.out.println(end_time.after(now));
		
		if(start_time.before(now) && end_time.after(now)) {
			return true;
		}
		
		return false;
	}
}
