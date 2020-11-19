package com.mocomsys.microstrategy.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUtil.class);
	private static final Properties prop;
	
	
	static {
		final InputStream stream = ConfigUtil.class.getResourceAsStream("/mstr.properties");
		prop = new Properties();
		
		try {
			prop.load(stream);
		} catch (IOException e) {
			LOGGER.error("!!! error", e);
		}
	}
	
	public static String getServerName() {
		return prop.getProperty("mstr.server.name");
	}

	public static String getPort() {
		return prop.getProperty("mstr.server.port");
	}

	public static String getDefaultProject() {
		return prop.getProperty("mstr.default.project.name");
	}
	
	public static String getTrustToken() {
		return prop.getProperty("mstr.trust.token");
	}
	
	public static String getEsmFailUrl() {
		return prop.getProperty("mstr.esm.fail.url");
	}
	
	public static String getSsoKeyName() {
		return prop.getProperty("mstr.sso.key.name");
	}
}
