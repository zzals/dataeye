package kr.co.penta.dataeye.spring.config.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="application.security")
public class SecuritySettings {
    private String provider;
    private String userDetailsService;
    private String loginPage;
    private String loginProcessingUrl;
    private String usernameParameter;
    private String passwordParameter;
    private String successHandler;
    private String failureHandler;
    private String loginSuccessUrl;

    private String logoutUrl;
    private String logoutSuccessHandler;
    private String logoutSuccessUrl;
    private String expiredUrl;
    private String ssoEntryPoint;

    private String ajaxRequestHeader;
    private String ajaxRequestValue;

    private String allowOrigin;

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(String userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public String getLoginProcessingUrl() {
        return loginProcessingUrl;
    }

    public void setLoginProcessingUrl(String loginProcessingUrl) {
        this.loginProcessingUrl = loginProcessingUrl;
    }

    public String getUsernameParameter() {
        return usernameParameter;
    }

    public void setUsernameParameter(String usernameParameter) {
        this.usernameParameter = usernameParameter;
    }

    public String getPasswordParameter() {
        return passwordParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        this.passwordParameter = passwordParameter;
    }

    public String getSuccessHandler() {
        return successHandler;
    }

    public void setSuccessHandler(String successHandler) {
        this.successHandler = successHandler;
    }

    public String getFailureHandler() {
        return failureHandler;
    }

    public void setFailureHandler(String failureHandler) {
        this.failureHandler = failureHandler;
    }

    public String getLoginSuccessUrl() {
        return loginSuccessUrl;
    }

    public void setLoginSuccessUrl(String loginSuccessUrl) {
        this.loginSuccessUrl = loginSuccessUrl;
    }

    public String getLogoutUrl() {
        return logoutUrl;
    }

    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    public String getLogoutSuccessHandler() {
        return logoutSuccessHandler;
    }

    public void setLogoutSuccessHandler(String logoutSuccessHandler) {
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    public String getSsoEntryPoint() {
        return ssoEntryPoint;
    }

    public void setSsoEntryPoint(String ssoEntryPoint) {
        this.ssoEntryPoint = ssoEntryPoint;
    }

    public String getLogoutSuccessUrl() {
        return logoutSuccessUrl;
    }

    public void setLogoutSuccessUrl(String logoutSuccessUrl) {
        this.logoutSuccessUrl = logoutSuccessUrl;
    }
    

    public String getExpiredUrl() {
        return expiredUrl;
    }

    public void setExpiredUrl(String expiredUrl) {
        this.expiredUrl = expiredUrl;
    }
    
    public String getAjaxRequestHeader() {
        return ajaxRequestHeader;
    }

    public void setAjaxRequestHeader(String ajaxRequestHeader) {
        this.ajaxRequestHeader = ajaxRequestHeader;
    }

    public String getAjaxRequestValue() {
        return ajaxRequestValue;
    }

    public void setAjaxRequestValue(String ajaxRequestValue) {
        this.ajaxRequestValue = ajaxRequestValue;
    }

    public String getAllowOrigin() {
        return allowOrigin;
    }

    public void setAllowOrigin(String allowOrigin) {
        this.allowOrigin = allowOrigin;
    }
}

