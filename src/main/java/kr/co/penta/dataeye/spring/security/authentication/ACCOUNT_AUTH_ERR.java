package kr.co.penta.dataeye.spring.security.authentication;

public enum ACCOUNT_AUTH_ERR {
	ACCOUNT_NOTFOUND("err.login.account.notfound"),
	ACCOUNT_EXPIRED("err.login.account.expired"),
	ACCOUNT_LOCKED("err.login.account.locked"),
	ACCOUNT_DISABLED("err.login.account.disabled"),
	CREDENTIAL_EXPIRED("err.login.credential.expired"),
	PASSWORD_EXPIRED("err.login.password.expired");
	
	public final String value;
	private ACCOUNT_AUTH_ERR(final String value) {
		this.value = value;
	}
}
