package kr.co.penta.dataeye.spring.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="search")
public class SearchSettings {
	private String schema;
	
    private String host;
    
    private Integer port;

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }
    
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
    
    public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	@NestedConfigurationProperty()
    private Config config = new Config();

    public Config getConfig() {
        return this.config;
    }
    
    public class Config {
        private String indexName;
        private String[] dashboardObjTypes;
        private String suggestIndexName;
        private String[] indexesObjTypeIds;
        private String[] indexesObjTypeNms;
        private int allSearchSize;
        private int defaultSearchSize;
        
        public String getIndexName() {
            return indexName;
        }

        public void setIndexName(String indexName) {
            this.indexName = indexName;
        }
        
        public String[] getDashboardObjTypes() {
            return dashboardObjTypes;
        }

        public void setDashboardObjTypes(String[] dashboardObjTypes) {
            this.dashboardObjTypes = dashboardObjTypes;
        }

        public String getSuggestIndexName() {
            return suggestIndexName;
        }

        public void setSuggestIndexName(String suggestIndexName) {
            this.suggestIndexName = suggestIndexName;
        }
        
        public String[] getIndexesObjTypeIds() {
            return indexesObjTypeIds;
        }

        public void setIndexesObjTypeIds(String[] indexesObjTypeIds) {
            this.indexesObjTypeIds = indexesObjTypeIds;
        }
        
        public String[] getIndexesObjTypeNms() {
            return indexesObjTypeNms;
        }

        public void setIndexesObjTypeNms(String[] indexesObjTypeNms) {
            this.indexesObjTypeNms = indexesObjTypeNms;
        }
        
        public int getAllSearchSize() {
            return allSearchSize;
        }

        public void setAllSearchSize(int allSearchSize) {
            this.allSearchSize = allSearchSize;
        }
        
        public int getDefaultSearchSize() {
            return defaultSearchSize;
        }

        public void setDefaultSearchSize(int defaultSearchSize) {
            this.defaultSearchSize = defaultSearchSize;
        }
    }
}
