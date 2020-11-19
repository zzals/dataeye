package kr.co.penta.dataeye.spring.web.portal.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.penta.dataeye.common.entities.meta.PenObjD;
import kr.co.penta.dataeye.common.entities.meta.PenObjM;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.spring.mybatis.dao.CommonDao;
import kr.co.penta.dataeye.spring.mybatis.dao.MetaDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.portal.service.BdpService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;

/**
 * Created by Administrator on 2016-11-30.
 */
@Service
public class BdpServiceImpl implements BdpService {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());
    
    @Autowired MetaDao metaDao;
    @Autowired CommonDao commonDao;
    
    @Override
    @Transactional
    public void dsetAtrImport(String dsetTypeId, String dsetId, String dsetAtrTypeId, List<Map<String, Object>> data, SessionInfo sessionInfo) {
        Integer atrOrder = 1;
        List<PenObjM> oldPenObjMs = metaDao.getObjMsByPath(dsetAtrTypeId, dsetTypeId, dsetId);
        for(PenObjM penObjM : oldPenObjMs) {
            metaDao.removePenObjD(penObjM.getObjTypeId(), penObjM.getObjId());
            metaDao.removePenObjM(penObjM.getObjTypeId(), penObjM.getObjId());
        }
        
        for(Map<String, Object> map : data) {
            PenObjM penObjM = new PenObjM(map, sessionInfo);
            penObjM.setObjTypeId(dsetAtrTypeId);
            penObjM.setPathObjTypeId(dsetTypeId);
            penObjM.setPathObjId(dsetId);
            
            if ("".equals(penObjM.getObjId())) {
                penObjM.genObjId();
            } else {
                for(PenObjM penObjM2 : oldPenObjMs) {
                    if (penObjM.getObjId().equals(penObjM2.getObjId())) {
                        penObjM.setCretDt(penObjM2.getCretDt());
                        penObjM.setCretrId(penObjM2.getCretrId());
                    }
                }
            }
            metaDao.insertPenObjM(penObjM);
            
            for( String key : map.keySet()){
                if (key.startsWith("ATR_ID_SEQ_")) {
                    String sAtrIdSeq = key.substring("ATR_ID_SEQ_".length());
                    if ("101".equals(sAtrIdSeq)) {
                        PenObjD penObjD = new PenObjD(penObjM);
                        penObjD.setAtrIdSeq(101);
                        penObjD.setAtrValSeq(101);
                        penObjD.setObjAtrVal(atrOrder++);
                        
                        metaDao.insertPenObjD(penObjD);
                    } else {
                        if (NumberUtils.isParsable(sAtrIdSeq)) {
                            Integer atrIdSeq = NumberUtils.toInt(sAtrIdSeq);
                            PenObjD penObjD = new PenObjD(penObjM);
                            penObjD.setAtrIdSeq(atrIdSeq);
                            penObjD.setAtrValSeq(101);
                            penObjD.setObjAtrVal(map.get(key));
                            
                            metaDao.insertPenObjD(penObjD);
                        }
                    }
                }
            }
        }
    }
    
    @Override
    @Transactional
    public void dsgnTabColImport(String dsgnTabTypeId, String dsgnTabId, String dsgnTabColTypeId, List<Map<String, Object>> data, SessionInfo sessionInfo) {
        Integer atrOrder = 1;
        List<PenObjM> oldPenObjMs = metaDao.getObjMsByPath(dsgnTabColTypeId, dsgnTabTypeId, dsgnTabId);
        for(PenObjM penObjM : oldPenObjMs) {
            metaDao.removePenObjD(penObjM.getObjTypeId(), penObjM.getObjId());
            metaDao.removePenObjM(penObjM.getObjTypeId(), penObjM.getObjId());
        }
        
        for(Map<String, Object> map : data) {
            PenObjM penObjM = new PenObjM(map, sessionInfo);
            penObjM.setObjTypeId(dsgnTabColTypeId);
            penObjM.setPathObjTypeId(dsgnTabTypeId);
            penObjM.setPathObjId(dsgnTabId);
            
            if ("".equals(penObjM.getObjId())) {
                penObjM.genObjId();
            } else {
                for(PenObjM penObjM2 : oldPenObjMs) {
                    if (penObjM.getObjId().equals(penObjM2.getObjId())) {
                        penObjM.setCretDt(penObjM2.getCretDt());
                        penObjM.setCretrId(penObjM2.getCretrId());
                    }
                }
            }
            metaDao.insertPenObjM(penObjM);
            
            for( Entry<String, Object> entry : map.entrySet()){
                if (entry.getKey().startsWith("ATR_ID_SEQ_")) {
                    String sAtrIdSeq = entry.getKey().substring("ATR_ID_SEQ_".length());
                    if ("101".equals(sAtrIdSeq)) {
                        PenObjD penObjD = new PenObjD(penObjM);
                        penObjD.setAtrIdSeq(101);
                        penObjD.setAtrValSeq(101);
                        penObjD.setObjAtrVal(atrOrder++);
                        
                        metaDao.insertPenObjD(penObjD);
                    } else {
                        if (NumberUtils.isParsable(sAtrIdSeq)) {
                            Integer atrIdSeq = NumberUtils.toInt(sAtrIdSeq);
                            PenObjD penObjD = new PenObjD(penObjM);
                            penObjD.setAtrIdSeq(atrIdSeq);
                            penObjD.setAtrValSeq(101);
                            penObjD.setObjAtrVal(entry.getValue());
                            
                            metaDao.insertPenObjD(penObjD);
                        }
                    }
                }
            }
        }
    }
    
    @Override
    public List<Map<String, Object>> dsgnTabColStdCheck(String dbmsType, List<Map<String, Object>> data) {
        boolean isFirst = true;
        StringBuilder sb = new StringBuilder();
        for(Iterator<Map<String, Object>> iterator=data.iterator(); iterator.hasNext(); ) {
            Map<String, Object> map = iterator.next();
            String atrNm = CastUtils.toString(map.get("OBJ_NM")).trim();
            if ("".equals(atrNm)) continue;
            
            if (isFirst) {
                isFirst = false;
            } else {
                sb.append("UNION ALL").append("\n");
            }
            sb.append(" SELECT '").append(atrNm).append("' AS ATR_NM FROM DUAL").append("\n");
        }

        DaoParam daoParam = new DaoParam();
        daoParam.put("dbmsType", dbmsType).put("ATR_SET_QUERY", sb.toString());
        
        List<Map<String, Object>> list = commonDao.selectList("bdp_custom.dsgnTabColStdValidCheck", daoParam);
        for(Map<String, Object> row : data) {
            String atrNm = CastUtils.toString(row.get("OBJ_NM")).trim();
            if ("".equals(atrNm)) {
                row.put("OBJ_ABBR_NM", "");
                row.put("ATR_ID_SEQ_102", "");
                row.put("ATR_ID_SEQ_103", "");
                row.put("ATR_ID_SEQ_104", "");
                continue;
            }
            
            boolean isMatch = false;
            for(Map<String, Object> map : list) {
                String stdAtrNm = CastUtils.toString(map.get("atrNm"));
                
                if (atrNm.equals(stdAtrNm)) {
                    String stdColNm = CastUtils.toString(map.get("COL_NM"));
                    String stdDataType = CastUtils.toString(map.get("DATA_TYPE"));
                    Integer stdDataLen = CastUtils.toInteger(map.get("DATA_LEN"));
                    Integer stdDataPointLen = CastUtils.toInteger(map.get("DATA_POINT_LEN"));

                    row.put("OBJ_NM", atrNm);
                    row.put("OBJ_ABBR_NM", stdColNm);
                    row.put("ATR_ID_SEQ_102", stdDataType);
                    row.put("ATR_ID_SEQ_103", stdDataLen);
                    row.put("ATR_ID_SEQ_104", stdDataPointLen);
                    isMatch = true;
                    break;
                }
            }
            if (!isMatch) {
                row.put("OBJ_ABBR_NM", "");
                row.put("ATR_ID_SEQ_102", "");
                row.put("ATR_ID_SEQ_103", "");
                row.put("ATR_ID_SEQ_104", "");
            }
        }
        
        return data;
    }
    
    @Override
    public String dsgnTabNmStdTrans(String entityNm) {
        entityNm = StringUtils.replaceAll(entityNm, " ", "");
        String stdTabNm = entityNm;
        List<Map<String, Object>> list = commonDao.selectList("metacore.getWordLengthDesc", null);
        
        for(int i=0; i<list.size(); i++) {
            Map<String, Object> map = list.get(i);
            String objNm = CastUtils.toString(map.get("objNm"));
            String admObjId = CastUtils.toString(map.get("admObjId"));
            
            int indexOf = StringUtils.indexOf(entityNm, objNm);
            if (indexOf != -1) {
                list.remove(map);
                entityNm = StringUtils.replaceAll(entityNm, objNm, "");
                stdTabNm = StringUtils.replaceAll(stdTabNm, objNm, "_"+admObjId+"_");
                if ("".equals(entityNm)) {
                    break;
                }
                i = -1;
            }
        }

        stdTabNm = StringUtils.replaceAll(stdTabNm, "__", "_");
        if (StringUtils.startsWith(stdTabNm, "_")) {
            stdTabNm = StringUtils.substring(stdTabNm, 1);    
        }
        if (StringUtils.endsWith(stdTabNm, "_")) {
            stdTabNm = StringUtils.substring(stdTabNm, 0, stdTabNm.length()-1);    
        }
        
        return stdTabNm;
    }
}
