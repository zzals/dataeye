package kr.co.penta.dataeye.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.penta.dataeye.spring.config.properties.SearchSettings;
import kr.co.penta.dataeye.spring.config.properties.SearchSettings.Config;

@Service
public class SearchSettingsHolder {
    private static final SearchSettingsHolder holder = new SearchSettingsHolder();

    private static SearchSettings searchSettings;

    @Autowired
    public void setSearchSettings(SearchSettings searchSettings) {
        SearchSettingsHolder.searchSettings = searchSettings;
    }

    public SearchSettings getSearchSettings() {
        return searchSettings;
    }
    
    public SearchSettingsHolder() {
        super();
    }
    
    public static SearchSettingsHolder getInstance() {
        return holder;
    }
}