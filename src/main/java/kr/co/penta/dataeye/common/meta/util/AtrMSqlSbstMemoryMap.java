package kr.co.penta.dataeye.common.meta.util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.entities.meta.AtrInfoEntity;
import kr.co.penta.dataeye.common.meta.util.AtrMSqlSbstMemoryMap.CodeEntity;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.spring.mybatis.dao.MetaDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.common.service.impl.MetaReqServiceImpl;

/**
 * �Ӽ��⺻ ���� ������ �޸𸮸� Class
 * <br/><b>History</b><br/>
 * <pre>
 * 2012. 5. 4. �����ۼ�
 * </pre>
 * @author cjsxowls
 * @version 1.0
 */
public class AtrMSqlSbstMemoryMap {
    private static final Logger LOG = LoggerFactory.getLogger(AtrMSqlSbstMemoryMap.class);
    
    private static final AtrMSqlSbstMemoryMap SQL_SBST_MEMORY_MAP = new AtrMSqlSbstMemoryMap();
	private static final Map<String, List<CodeEntity>> QUERY_MAP = new HashMap<String, List<CodeEntity>>();
	
	private AtrMSqlSbstMemoryMap() {
		super();
	}
	/**
	 * �Ӽ��⺻ ���� ������ SqlSbstMemoryMap������<br>
	 * @param String, Object
	 * @return Object
	 */ 
	public Object put(final String key, final List<CodeEntity> value) {
		return QUERY_MAP.put(key, value);
	}
	
	/**
     * �Ӽ��⺻ ���� ������ SqlSbstMemoryMap���� ����<br>
     * @param String, Object
     * @return Object
     */ 
    public void remove(final String key) {
        QUERY_MAP.remove(key);
    }
    
	/**
	 * SqlSbstMemoryMap���� �Ӽ��⺻ ���� ������ ����<br>
	 * @param String
	 * @return Object
	 */ 
	public List<CodeEntity> get(final String key) {
		return QUERY_MAP.get(key);
	}
 
	/**
	 * SqlSbstMemoryMap �ν��Ͻ� ����<br>
	 * @param 
	 * @return SqlSbstMemoryMap
	 */ 
    public static AtrMSqlSbstMemoryMap getInstance() {
       return SQL_SBST_MEMORY_MAP;
    }
    
    public static class CodeEntity extends CamelMap {
        private static final long serialVersionUID = -430972298940942816L;
        
        public String getCode() {
            return CastUtils.toString(get("code"));
        }
        public void setCode(String code) {
            put("CODE", code);
        }
        public String getName() {
            return CastUtils.toString(get("name"));
        }
        public void setName(String name) {
            put("NAME", name);
        }
        public String getDispName() {
            return CastUtils.toString(get("dispName"));
        }
        public void setDispName(String dispName) {
            put("DISP_NAME", dispName);
        }
    }
    
    public List<Map<String, List<CodeEntity>>> getAtrMSqlSbstResultMap(List<AtrInfoEntity> atrInfos, List<Map<String, Object>> atrValInfos, MetaDao metaDao) {
        final List<Map<String, List<CodeEntity>>> codeResultList = new ArrayList<Map<String, List<CodeEntity>>>();
        for(int i=0; i<atrInfos.size(); i++) {
            final AtrInfoEntity atrInfoEntity = atrInfos.get(i);
            
            final String atrRfrnCd = atrInfoEntity.getAtrRfrnCd();
            
            if ("C".equals(atrRfrnCd) || "R".equals(atrRfrnCd) || "M".equals(atrRfrnCd)) {
                if (!"".equals(atrInfoEntity.getSqlSbst())) {
                    final Map<String, List<CodeEntity>> codeMap = new HashMap<String, List<CodeEntity>>();
                    try {
                        final List<CodeEntity> codes = getAtrRfrnCdResult(atrInfoEntity, metaDao);
                        codeMap.put("ATR_CODE_"+atrInfoEntity.getAtrIdSeq(), codes);
                        
                        codeResultList.add(codeMap);
                    } catch(Exception e){
                        LOG.error("codeService.getAtrRfrnCdResult Error.", e);
                    }
                }
            }
        }
        
        return codeResultList;
    }
    
    public List<CodeEntity> getAtrRfrnCdResult(AtrInfoEntity atrInfoEntity, MetaDao metaDao) {
        List<CodeEntity> result = new ArrayList<CodeEntity>();
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", atrInfoEntity.getObjTypeId());
        daoParam.put("sqlSbst", atrInfoEntity.getSqlSbst());
         
        if (DataEyeSettingsHolder.getInstance().getMetaConfig().getAtrSqlsbstInmemoryEnable()) {
            if ("C".equals(atrInfoEntity.getAtrRfrnCd())) {
                result = AtrMSqlSbstMemoryMap.getInstance().get(atrInfoEntity.getAtrId());
                if (result == null) {
                    result = metaDao.getAtrMSqlSbstResult(daoParam);
                    
                    final Pattern pattern = Pattern.compile(".*#\\{.*\\}.*", Pattern.DOTALL);
                    Matcher matcher = null;
                    matcher = pattern.matcher(atrInfoEntity.getSqlSbst());
                    if(!matcher.matches()) {
                        AtrMSqlSbstMemoryMap.getInstance().put(atrInfoEntity.getAtrId(), result);
                    }
                }
            } else if ("R".equals(atrInfoEntity.getAtrRfrnCd())) {
                result = metaDao.getAtrMSqlSbstResult(daoParam);
            }
        } else {
            result = metaDao.getAtrMSqlSbstResult(daoParam);
        }
        
        return result;
    }
}