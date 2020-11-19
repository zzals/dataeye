package com.mocomsys.microstrategy.custom.esm;

import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microstrategy.utils.serialization.EnumWebPersistableState;
import com.microstrategy.web.app.AbstractExternalSecurity;
import com.microstrategy.web.app.EnumWebParameters;
import com.microstrategy.web.beans.RequestKeys;
import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebObjectsException;
import com.microstrategy.web.objects.WebObjectsFactory;
import com.microstrategy.web.platform.ContainerServices;
import com.microstrategy.webapi.EnumDSSXMLAuthModes;
import com.mocomsys.microstrategy.util.ConfigUtil;

public class SSOESM extends AbstractExternalSecurity {
	private static final Logger LOGGER = LoggerFactory.getLogger(SSOESM.class);
	private static final String SESSION_STATE = "SESSION_STATE";
	private static String customLoginURL;
	
	@Override
    public int handlesAuthenticationRequest(final RequestKeys reqKeys, final ContainerServices cntSvcs, final int reason) { 
		LOGGER.debug("=> _uid:[{}]", cntSvcs.getSessionAttribute("_uid"));
		
    	//return (StringUtils.isNotEmpty((String)cntSvcs.getSessionAttribute("_uid")) ? COLLECT_SESSION_NOW : USE_CUSTOM_LOGIN_PAGE);
		//(StringUtils.isNotEmpty((String)cntSvcs.getSessionAttribute("_uid")) ? COLLECT_SESSION_NOW : USE_MSTR_DEFAULT_LOGIN);
		
		boolean success = false;
		boolean timeIsBefore = false;
		
		if(reqKeys.getValue(ConfigUtil.getSsoKeyName()) != null && !reqKeys.getValue(ConfigUtil.getSsoKeyName()).equals("")) {
			String securityKey = URLDecoder.decode(reqKeys.getValue(ConfigUtil.getSsoKeyName()));
			String[] authString = securityKey.split("\\|");
			if(!authString[1].equals("")) {
				try {
					timeIsBefore = isBefore(authString[1]);
				} catch (ParseException e) {
					LOGGER.error("handlesAuthenticationRequest ParseException - " + e);
				}
			}
				
			if (((String) cntSvcs.getSessionAttribute("_uid")).equals(authString[0]) && timeIsBefore) {
				success = true;
			}
		}
		
		if (success) {
            //session created successfully
            return COLLECT_SESSION_NOW;
        } else {
            //cannot create session
            return USE_MSTR_DEFAULT_LOGIN;
        }
		
    }
	
	@Override
    public String getCustomLoginURL(String originalURL, String desiredServer, int desiredPort, String desiredProject) {
		return ConfigUtil.getEsmFailUrl();
    }
	
	@Override
    public WebIServerSession getWebIServerSession(final RequestKeys reqKeys, final ContainerServices cntSvcs) {
		WebIServerSession userSession = null;
        try {
            String server = reqKeys.getValue(EnumWebParameters.WebParameterLoginServerName);
            String port = reqKeys.getValue(EnumWebParameters.WebParameterLoginPort);
            String project = reqKeys.getValue(EnumWebParameters.WebParameterLoginProjectName);
            
            userSession = WebObjectsFactory.getInstance().getIServerSession();

            server = StringUtils.defaultString(server, ConfigUtil.getServerName());
            LOGGER.debug("=> server:[{}]", server);
            userSession.setServerName(server);
            port = StringUtils.defaultString(port, ConfigUtil.getPort());
            LOGGER.debug("=> port:[{}]", port);
            userSession.setServerPort(Integer.parseInt(port));
            project = StringUtils.defaultString(project, ConfigUtil.getDefaultProject());
            LOGGER.debug("=> project:[{}]", project);
            userSession.setProjectName(project);
            
        	String uid = (String)cntSvcs.getSessionAttribute("_uid");
        	
            userSession.setLogin(uid);
            /*
            userSession.setPassword(null);
            userSession.setAuthMode(EnumDSSXMLAuthModes.DssXmlAuthStandard);
            */
            
            userSession.setAuthMode(EnumDSSXMLAuthModes.DssXmlAuthTrusted);
            String token = ConfigUtil.getTrustToken();
            LOGGER.debug("=> token:[{}]", token);
            userSession.setTrustToken(token);
            
            
            userSession.setClientID(cntSvcs.getRemoteAddress());
            
            userSession.getSessionID();
            
            final String sessionState = userSession.saveState(EnumWebPersistableState.MAXIMAL_STATE_INFO);
            cntSvcs.setSessionAttribute(SESSION_STATE, sessionState);
        } catch (WebObjectsException e) {
            cntSvcs.setSessionAttribute(SESSION_STATE, null);
            LOGGER.error("!!! error", e);
        }
        
        return userSession;
    }
	
	private static boolean isBefore(String timestemp) throws ParseException {
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		
		SimpleDateFormat dataFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date start_time = dataFormat.parse(timestemp);
		Date end_time = dataFormat.parse(timestemp);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(start_time);
		cal.add(Calendar.SECOND, 10);
		end_time = cal.getTime();
		
		if(start_time.before(now) && end_time.after(now)) {
			return true;
		}
		
		return false;
	}
    
}
