package kr.co.penta.dataeye.spring.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="application.portal")
public class PortalSettings {
    private boolean enabledIntegrationMenu;
    private String httpHeaderAjaxRequestHeader;
    private String httpHeaderAjaxRequestValue;
    private String ui;

    private String defaultLocale;
    private String defaultErrorPagePath;
    private String loginView;
    private String loginErrorView;
    private String logoutView;
    private String expiredView;
    private String portalMainView;
    private String adminMainView;
    private String systemMainView;
    private String passwordInitValue;
    private Integer passwordFailCount;
    private Integer passwordChangePeriod;
    
    public boolean isEnabledIntegrationMenu() {
        return enabledIntegrationMenu;
    }

    public void setEnabledIntegrationMenu(boolean enabledIntegrationMenu) {
        this.enabledIntegrationMenu = enabledIntegrationMenu;
    }

    public String getHttpHeaderAjaxRequestHeader() {
        return httpHeaderAjaxRequestHeader;
    }

    public void setHttpHeaderAjaxRequestHeader(String httpHeaderAjaxRequestHeader) {
        this.httpHeaderAjaxRequestHeader = httpHeaderAjaxRequestHeader;
    }

    public String getHttpHeaderAjaxRequestValue() {
        return httpHeaderAjaxRequestValue;
    }

    public void setHttpHeaderAjaxRequestValue(String httpHeaderAjaxRequestValue) {
        this.httpHeaderAjaxRequestValue = httpHeaderAjaxRequestValue;
    }

    public String getUi() {
        return ui;
    }

    public void setUi(String ui) {
        this.ui = ui;
    }

    public String getDefaultLocale() {
        return defaultLocale;
    }

    public void setDefaultLocale(String defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    public String getDefaultErrorPagePath() {
        return defaultErrorPagePath;
    }

    public void setDefaultErrorPagePath(String defaultErrorPagePath) {
        this.defaultErrorPagePath = defaultErrorPagePath;
    }

    public String getLoginView() {
        return loginView;
    }

    public void setLoginView(String loginView) {
        this.loginView = loginView;
    }

    public String getLoginErrorView() {
        return loginErrorView;
    }

    public void setLoginErrorView(String loginErrorView) {
        this.loginErrorView = loginErrorView;
    }

    public String getLogoutView() {
        return logoutView;
    }

    public void setLogoutView(String logoutView) {
        this.logoutView = logoutView;
    }

    public String getExpiredView() {
        return expiredView;
    }

    public void setExpiredView(String expiredView) {
        this.expiredView = expiredView;
    }

    public String getPortalMainView() {
        return portalMainView;
    }

    public void setPortalMainView(String portalMainView) {
        this.portalMainView = portalMainView;
    }

    public String getAdminMainView() {
        return adminMainView;
    }

    public void setAdminMainView(String adminMainView) {
        this.adminMainView = adminMainView;
    }

    public String getSystemMainView() {
        return systemMainView;
    }

    public void setSystemMainView(String systemMainView) {
        this.systemMainView = systemMainView;
    }

    public String getPasswordInitValue() {
        return passwordInitValue;
    }

    public void setPasswordInitValue(String passwordInitValue) {
        this.passwordInitValue = passwordInitValue;
    }

    public Integer getPasswordFailCount() {
        return passwordFailCount;
    }

    public void setPasswordFailCount(Integer passwordFailCount) {
        this.passwordFailCount = passwordFailCount;
    }

    public Integer getPasswordChangePeriod() {
        return passwordChangePeriod;
    }

    public void setPasswordChangePeriod(Integer passwordChangePeriod) {
        this.passwordChangePeriod = passwordChangePeriod;
    }
}
