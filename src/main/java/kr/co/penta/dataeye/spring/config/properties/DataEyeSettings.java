package kr.co.penta.dataeye.spring.config.properties;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="dataeye")
public class DataEyeSettings {
    private String appId;
    
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

	@NestedConfigurationProperty()
    private MetaConfig metaConfig = new MetaConfig();

    public MetaConfig getMetaConfig() {
        return this.metaConfig;
    }
    
    public class MetaConfig {
        private boolean atrSqlsbstInmemoryEnable;
        private boolean logicalDeleteEnable;
        private String splitDelimiterChar;
        private String splitDelimiterString;
        private Integer objRelSearchLimit;
        private String qmBrRunSh;
        
        public boolean getAtrSqlsbstInmemoryEnable() {
            return atrSqlsbstInmemoryEnable;
        }

        public void setAtrSqlsbstInmemoryEnable(boolean atrSqlsbstInmemoryEnable) {
            this.atrSqlsbstInmemoryEnable = atrSqlsbstInmemoryEnable;
        }
        
        public boolean getLogicalDeleteEnable() {
            return logicalDeleteEnable;
        }

        public void setLogicalDeleteEnable(boolean logicalDeleteEnable) {
            this.logicalDeleteEnable = logicalDeleteEnable;
        }
        
        public String getSplitDelimiterChar() {
            return splitDelimiterChar;
        }

        public void setSplitDelimiterChar(String splitDelimiterChar) {
            this.splitDelimiterChar = StringEscapeUtils.unescapeJava(splitDelimiterChar);
        }
        
        public String getSplitDelimiterString() {
            return splitDelimiterString;
        }

        public void setSplitDelimiterString(String splitDelimiterString) {
            this.splitDelimiterString = splitDelimiterString;
        }
        
        public Integer getObjRelSearchLimit() {
            return objRelSearchLimit;
        }

        public void setObjRelSearchLimit(Integer objRelSearchLimit) {
            this.objRelSearchLimit = objRelSearchLimit;
        }
        
        public String getQmBrRunSh() {
            return qmBrRunSh;
        }

        public void setQmBrRunSh(String qmBrRunSh) {
            this.qmBrRunSh = qmBrRunSh;
        }
    }
    
    @NestedConfigurationProperty()
    private Config config = new Config();

    public Config getConfig() {
        return this.config;
    }
    
    public class Config {
        private String javaTimestampFormat;
        private String javaDateFormat;
        private String dbTimestampFormat;
        private String dbDateFormat;
        private boolean loginLoggingEnable;
        private boolean logoutLoggingEnable;
        private boolean menuAccessLoggingEnable;
        private boolean objectAccessLoggingEnable;
        private String[] objectAccessLoggingTargetObjects;
        private boolean xssFilterEnable;
        private boolean dummyFlag;
        
        public String getJavaTimestampFormat() {
            return javaTimestampFormat;
        }

        public void setJavaTimestampFormat(String javaTimestampFormat) {
            this.javaTimestampFormat = javaTimestampFormat;
        }
        
        public String getJavaDateFormat() {
            return javaDateFormat;
        }

        public void setJavaDateFormat(String javaDateFormat) {
            this.javaDateFormat = javaDateFormat;
        }
        public String getDbTimestampFormat() {
            return dbTimestampFormat;
        }

        public void setDbTimestampFormat(String dbTimestampFormat) {
            this.dbTimestampFormat = dbTimestampFormat;
        }
        
        public String getDbDateFormat() {
            return dbDateFormat;
        }

        public void setDbDateFormat(String dbDateFormat) {
            this.dbDateFormat = dbDateFormat;
        }
        
        public boolean getLoginLoggingEnable() {
            return loginLoggingEnable;
        }
        
        public void setLogoutLoggingEnable(boolean logoutLoggingEnable) {
            this.logoutLoggingEnable = logoutLoggingEnable;
        }
        
        public boolean getLogoutLoggingEnable() {
            return logoutLoggingEnable;
        }
        
        public void setLoginLoggingEnable(boolean loginLoggingEnable) {
            this.loginLoggingEnable = loginLoggingEnable;
        }
        
        public boolean getMenuAccessLoggingEnable() {
            return menuAccessLoggingEnable;
        }
        
        public void setMenuAccessLoggingEnable(boolean menuAccessLoggingEnable) {
            this.menuAccessLoggingEnable = menuAccessLoggingEnable;
        }
        
        public boolean getObjectAccessLoggingEnable() {
            return objectAccessLoggingEnable;
        }
        
        public void setObjectAccessLoggingEnable(boolean objectAccessLoggingEnable) {
            this.objectAccessLoggingEnable = objectAccessLoggingEnable;
        }
        
        public String[] getObjectAccessLoggingTargetObjects() {
            return objectAccessLoggingTargetObjects;
        }
        
        public void setObjectAccessLoggingTargetObjects(String[] objectAccessLoggingTargetObjects) {
            this.objectAccessLoggingTargetObjects = objectAccessLoggingTargetObjects;
        }
        
        public boolean getXssFilterEnable() {
            return xssFilterEnable;
        }
        
        public void setXssFilterEnable(boolean xssFilterEnable) {
            this.xssFilterEnable = xssFilterEnable;
        }
        
        public boolean getDummyFlag() {
            return dummyFlag;
        }
        
        public void setDummyFlag(boolean dummyFlag) {
            this.dummyFlag = dummyFlag;
        }
    }
    
    @NestedConfigurationProperty()
    private Jqgrid jqgrid = new Jqgrid();

    public Jqgrid getJqgrid() {
        return this.jqgrid;
    }
    
    public class Jqgrid {
        private boolean pagingEnable;
        private boolean scroll;
        private Integer rowNum;
        private Integer defaultHeight;
        
        private boolean navEditEnable;
        private boolean navAddEnable;
        private boolean navDelEnable;
        private boolean navSearchEnable;
        private boolean navRefreshEnable;
        private boolean navViewEnable;
        private boolean topPager;
        
        public boolean getPagingEnble() {
            return pagingEnable;
        }

        public void setPagingEnable(boolean pagingEnable) {
            this.pagingEnable = pagingEnable;
        }
        
        public boolean getScroll() {
            return scroll;
        }

        public void setScroll(boolean scroll) {
            this.scroll = scroll;
        }
        
        public Integer getRowNum() {
            return rowNum;
        }

        public void setRowNum(Integer rowNum) {
            this.rowNum = rowNum;
        }
        
        public Integer getDefaultHeight() {
            return defaultHeight;
        }

        public void setDefaultHeight(Integer defaultHeight) {
            this.defaultHeight = defaultHeight;
        }
        
        public boolean getNavEditEnable() {
            return navEditEnable;
        }

        public void setNavEditEnable(boolean navEditEnable) {
            this.navEditEnable = navEditEnable;
        }
        
        public boolean getNavAddEnable() {
            return navAddEnable;
        }

        public void setNavAddEnable(boolean navAddEnable) {
            this.navAddEnable = navAddEnable;
        }
        
        public boolean getNavDelEnable() {
            return navDelEnable;
        }

        public void setNavDelEnable(boolean navDelEnable) {
            this.navDelEnable = navDelEnable;
        }
        
        public boolean getNavSearchEnable() {
            return navSearchEnable;
        }

        public void setNavSearchEnable(boolean navSearchEnable) {
            this.navSearchEnable = navSearchEnable;
        }
        
        public boolean getNavRefreshEnable() {
            return navRefreshEnable;
        }

        public void setNavRefreshEnable(boolean navRefreshEnable) {
            this.navRefreshEnable = navRefreshEnable;
        }
        
        public boolean getNavViewEnable() {
            return navViewEnable;
        }

        public void setNavViewEnable(boolean navViewEnable) {
            this.navViewEnable = navViewEnable;
        }

        public boolean getTopPager() {
            return topPager;
        }

        public void setTopPager(boolean topPager) {
            this.topPager = topPager;
        }
    }
    
    @NestedConfigurationProperty()
    private AttachFile attachFile = new AttachFile();

    public AttachFile getAttachFile() {
        return this.attachFile;
    }
    
    public class AttachFile {
        private String uploadTempPath;
        private String uploadRealPath;
        private String templatePath;
        
        public String getUploadTempPath() {
            return uploadTempPath;
        }

        public void setUploadTempPath(String uploadTempPath) {
            this.uploadTempPath = uploadTempPath;
        }
        
        public String getUploadReadPath() {
        	String userHome = System.getProperty("user.home");
        	userHome = (userHome == null)?"":userHome;        	
        	if(userHome.indexOf("newtake")>0) {
        		return "C:\\work\\upFile\\";
        	} else {
        		return uploadRealPath;	
        	}
            
        }

        public void setUploadReadPath(String uploadRealPath) {
            this.uploadRealPath = uploadRealPath;
        }
        
        public String getTemplatePath() {
            return templatePath;
        }

        public void setTemplatePath(String templatePath) {
            this.templatePath = templatePath;
        }
    }
    
    @NestedConfigurationProperty()
    private BizStd bizStd = new BizStd();

    public BizStd getBizStd() {
        return this.bizStd;
    }
    
    public class BizStd {
        private boolean wordNmDuplChkEnable;
        private boolean wordAbbrAutoUpperEnable;
        private boolean termAbbrLengthChkEnable;
        private Integer termAbbrMaxLength;
        
        public boolean getWordNmDuplChkEnable() {
            return wordNmDuplChkEnable;
        }

        public void setWordNmDuplChkEnable(boolean wordNmDuplChkEnable) {
            this.wordNmDuplChkEnable = wordNmDuplChkEnable;
        }
        public boolean getWordAbbrAutoUpperEnable() {
            return wordAbbrAutoUpperEnable;
        }

        public void setWordAbbrAutoUpperEnable(boolean wordAbbrAutoUpperEnable) {
            this.wordAbbrAutoUpperEnable = wordAbbrAutoUpperEnable;
        }
        public boolean getTermAbbrLengthChkEnable() {
            return termAbbrLengthChkEnable;
        }

        public void setTermAbbrLengthChkEnable(boolean termAbbrLengthChkEnable) {
            this.termAbbrLengthChkEnable = termAbbrLengthChkEnable;
        }
        public Integer getTermAbbrMaxLength() {
            return termAbbrMaxLength;
        }

        public void setTermAbbrMaxLength(Integer termAbbrMaxLength) {
            this.termAbbrMaxLength = termAbbrMaxLength;
        }
    }
    
    @NestedConfigurationProperty()
    private MgrRole mgrRole = new MgrRole();

    public MgrRole getMgrRole() {
        return this.mgrRole;
    }
    
    public class MgrRole {
        private String adminRole;
        private String adminRoleGroup;
        private String systemRole;
        private String systemRoleGroup;
        
        public String getAdminRole() {
            return adminRole;
        }

        public void setAdminRole(String adminRole) {
            this.adminRole = adminRole;
        }
        
        public String getAdminRoleGroup() {
            return adminRoleGroup;
        }

        public void setAdminRoleGroup(String adminRoleGroup) {
            this.adminRoleGroup = adminRoleGroup;
        }
        
        public String getSystemRole() {
            return systemRole;
        }

        public void setSystemRole(String systemRole) {
            this.systemRole = systemRole;
        }
        
        public String getSystemRoleGroup() {
            return systemRoleGroup;
        }

        public void setSystemRoleGroup(String systemRoleGroup) {
            this.systemRoleGroup = systemRoleGroup;
        }
    }
        
    @NestedConfigurationProperty()
    private Template template = new Template();

    public Template getTemplate() {
        return this.template;
    }
    
    public class Template {
        private String qmBrUploadTemplate;
        private String stdWordUploadTemplate;
        private String stdTermUploadTemplate;
        private String stdDomainUploadTemplate;
        private String dsgnTabUploadTemplate;
        private String dsgnColUploadTemplate;
        
        public String getQmBrUploadTemplate() {
            return qmBrUploadTemplate;
        }

        public void setQmBrUploadTemplate(String qmBrUploadTemplate) {
            this.qmBrUploadTemplate = qmBrUploadTemplate;
        }
        
        public String getStdWordUploadTemplate() {
            return stdWordUploadTemplate;
        }

        public void setStdWordUploadTemplate(String stdWordUploadTemplate) {
            this.stdWordUploadTemplate = stdWordUploadTemplate;
        }
        
        public String getStdTermUploadTemplate() {
            return stdTermUploadTemplate;
        }

        public void setStdTermUploadTemplate(String stdTermUploadTemplate) {
            this.stdTermUploadTemplate = stdTermUploadTemplate;
        }

        public String getStdDomainUploadTemplate() {
            return stdDomainUploadTemplate;
        }

        public void setStdDomainUploadTemplate(String stdDomainUploadTemplate) {
            this.stdDomainUploadTemplate = stdDomainUploadTemplate;
        }

        public String getDsgnTabUploadTemplate() {
            return dsgnTabUploadTemplate;
        }

        public void setDsgnTabUploadTemplate(String dsgnTabUploadTemplate) {
            this.dsgnTabUploadTemplate = dsgnTabUploadTemplate;
        }

        public String getDsgnColUploadTemplate() {
            return dsgnColUploadTemplate;
        }

        public void setDsgnColUploadTemplate(String dsgnColUploadTemplate) {
            this.dsgnColUploadTemplate = dsgnColUploadTemplate;
        }
    }
}
