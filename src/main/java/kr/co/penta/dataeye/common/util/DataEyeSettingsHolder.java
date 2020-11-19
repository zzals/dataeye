package kr.co.penta.dataeye.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.penta.dataeye.spring.config.properties.DataEyeSettings;
import kr.co.penta.dataeye.spring.config.properties.DataEyeSettings.AttachFile;
import kr.co.penta.dataeye.spring.config.properties.DataEyeSettings.BizStd;
import kr.co.penta.dataeye.spring.config.properties.DataEyeSettings.Config;
import kr.co.penta.dataeye.spring.config.properties.DataEyeSettings.MetaConfig;
import kr.co.penta.dataeye.spring.config.properties.DataEyeSettings.MgrRole;
import kr.co.penta.dataeye.spring.config.properties.DataEyeSettings.Template;

@Service
public class DataEyeSettingsHolder {
    private static final DataEyeSettingsHolder holder = new DataEyeSettingsHolder();

    private static DataEyeSettings dataEyeSettings;

    @Autowired
    public void setDataEyeSettings(DataEyeSettings dataEyeSettings) {
        DataEyeSettingsHolder.dataEyeSettings = dataEyeSettings;
    }

    public DataEyeSettingsHolder() {
        super();
    }

    public String getAppId() {
        return dataEyeSettings.getAppId();
    }
    
    public Config getConfig() {
        return dataEyeSettings.getConfig();
    }

    public MetaConfig getMetaConfig() {
        return dataEyeSettings.getMetaConfig();
    }
    
    public AttachFile getAttachFile() {
        return dataEyeSettings.getAttachFile();
    }
    
    public BizStd getBizStd() {
        return dataEyeSettings.getBizStd();
    }
    
    public MgrRole getMgrRole() {
        return dataEyeSettings.getMgrRole();
    }
    
    public Template getTemplate() {
        return dataEyeSettings.getTemplate();
    }
    
    public static DataEyeSettingsHolder getInstance() {
        return holder;
    }
}