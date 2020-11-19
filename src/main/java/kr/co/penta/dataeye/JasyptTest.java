package kr.co.penta.dataeye;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class JasyptTest {

	public static void main(String[] args) throws Exception {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		
		encryptor.setProvider(new BouncyCastleProvider());
		encryptor.setAlgorithm("PBEWITHSHA256AND256BITAES-CBC-BC");
		encryptor.setPassword("ebaykorea");
		
		String encrypteText = encryptor.encrypt("O1_dataeye_meta_bidev");
		String plainText = encryptor.decrypt(encrypteText);
		
		System.out.println("encrypteText : " + encrypteText);
		System.out.println("plainText : " + plainText);
	}
}
