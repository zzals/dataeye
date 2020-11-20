package kr.co.penta.dataeye.spring.web;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomizingPropertySource {

	@Value("${spring.datasource.customizing-ds.url}")
	private String url;
	
	@Value("${spring.datasource.customizing-ds.username}")
	private String username;
	
	@Value("${spring.datasource.customizing-ds.password}")
	private String password;
	
	@Value("${spring.datasource.customizing-ds.driver-class-name}")
	private String driverClassName;
	
	@Value("${dataeye.config.algorithm}")
    private String algorithm;
    
    @Value("${dataeye.config.key}")
    private String key;
	
	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setProvider(new BouncyCastleProvider());
		encryptor.setAlgorithm(algorithm);
		encryptor.setPassword(key);
		
		return encryptor.decrypt(password);
	}

	public String getDriverClassName() {
		return driverClassName;
	}

}
