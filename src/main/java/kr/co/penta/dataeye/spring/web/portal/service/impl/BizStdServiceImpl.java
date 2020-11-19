package kr.co.penta.dataeye.spring.web.portal.service.impl;

import kr.co.penta.dataeye.common.entities.CamelMap;
import kr.co.penta.dataeye.common.entities.meta.OBJ_TYPE_ID;
import kr.co.penta.dataeye.common.entities.meta.OBJ_TYPE_ID.BIZ_STD;
import kr.co.penta.dataeye.common.entities.meta.PenObjD;
import kr.co.penta.dataeye.common.entities.meta.PenObjM;
import kr.co.penta.dataeye.common.exception.ServiceRuntimeException;
import kr.co.penta.dataeye.common.meta.util.TermChecker;
import kr.co.penta.dataeye.common.meta.util.TermModel;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.consts.BIZ_STD_CONST;
import kr.co.penta.dataeye.consts.BR_UPLOAD_CONST;
import kr.co.penta.dataeye.spring.mybatis.dao.CommonDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.portal.service.BizStdService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.co.penta.dataeye.common.exception.BizException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Created by Administrator on 2016-11-30.
 */
@Service
public class BizStdServiceImpl implements BizStdService {
    @Autowired CommonDao commonDao;
	    
	
	private static enum TERM_GRID {
		CHK,
		KOR_TERM_NM,
		DMN_NM,
		KOR_CB_NM,
		ENG_TERM_NM, 
		TERM_CNT, 
	    TERM_LEN, 
	    CLASS_YN,
		DMN_YN,
		DUPL_YN,
		VRF_RSLT,
		TERM_OBJECT
	}
	
	private static enum DMN_GRID {
	    CHK,
	    DMN_NM,
	    DMN_DATA_TYPE,
	    DMN_GRP_CD, 
	    DUPL_YN,
	    DATA_TYPE_YN,
	    DMN_GRP_YN,
	    VRF_RSLT
	}
	
	private static enum WORD_GRID {
	    CHK,
	    WORD_NM,
	    WORD_ABBR_NM,
	    WORD_ENG_NM,
	    CLASS_YN,
	    PHB_CHAR_YN,
	    DUPL_YN,
	    VRF_RSLT
	}
    
    /**
     * 표준용어 적합성 체크<br>
     * @param term 
     * @return List<Map<String, Object>>
     */
    @Override
    public List<TermModel> termVerify(String objTypeId, String objId, String term) {
        DaoParam daoParam = new DaoParam();
        daoParam.put("objTypeId", objTypeId).put("objId", objId).put("term", term);
        Map<String, Object> map = commonDao.selectOne("portal.biz.std.termUsedCount", daoParam);
        final int cnt = CastUtils.toInteger(map.get("CNT"));
        if (cnt > 0) {
            throw new ServiceRuntimeException("이미 등록된 용어 입니다.");
        }
        
        daoParam.clear();
        daoParam.put("objTypeId", OBJ_TYPE_ID.BIZ_STD.WORD.value());
        List<Map<String, Object>> terms = commonDao.selectList("portal.biz.std.findWordSimpleList", daoParam);
        TermChecker checker = new TermChecker(terms);
        checker.check(term);
        if (checker.isValid()) {
            return checker.getValidTerms();
        } else {
            return checker.getInvalidTerms();
        }
    }

    @Override
    public void jdbcConnectionTest(CamelMap parameter) throws Exception {
        String jdbcDriver = CastUtils.toString(parameter.get("jdbcDriver"));
        String jdbcUrl = CastUtils.toString(parameter.get("jdbcUrl"));
        String userId = CastUtils.toString(parameter.get("userId"));
        String password = CastUtils.toString(parameter.get("password"));
        Connection connection = null;
        try {
            Class.forName(jdbcDriver);
            connection = DriverManager.getConnection(jdbcUrl, userId, password);
        } catch (Exception e) {
            throw e;
        } finally {
            try { connection.close(); } catch (Exception e) {}
        }
    }

    @Override
    public List<Map<String,Object>> getStdTermMenu() {
    	DaoParam daoParam = new DaoParam();
    	return commonDao.selectList("portal_biz.getStdTermMenu",daoParam);
    }
    
	private boolean wordNmDupChk = DataEyeSettingsHolder.getInstance().getBizStd().getWordNmDuplChkEnable();
	private boolean wordAbbrAutoUpper =  DataEyeSettingsHolder.getInstance().getBizStd().getWordAbbrAutoUpperEnable();
    @Override
    public List<Map<String, Object>> wordVerifyCheck(Map<String, Object> data) {
    	final List<Map<String, Object>> datas = (List<Map<String, Object>>)data.get("data");
    	final Map<String, Object> parameter = new HashMap<String, Object>();
    	
    
    	parameter.put("wordNmDupChk", wordNmDupChk);
    	final List<Map<String, Object>> wordAbbrNms = commonDao.selectList("portal_biz.wordAbbrNm",new DaoParam(parameter));
    	
    	
    	for(final Iterator<Map<String, Object>> iterator=datas.iterator(); iterator.hasNext(); ) {
    	    Map<String, Object> row = iterator.next();
    	    final Map<String, Object> compare = new HashMap<String, Object>();
    	    
    	    row.put(WORD_GRID.WORD_ABBR_NM.name(), CastUtils.toString(row.get(WORD_GRID.WORD_ABBR_NM.name()), true));
            if (wordAbbrAutoUpper) {
                row.put(WORD_GRID.WORD_ABBR_NM.name(), CastUtils.toString(row.get(WORD_GRID.WORD_ABBR_NM.name())).toUpperCase());
        		compare.put(WORD_GRID.WORD_ABBR_NM.name(), row.get(WORD_GRID.WORD_ABBR_NM.name()));
            } else {
        		compare.put(WORD_GRID.WORD_ABBR_NM.name(), row.get(WORD_GRID.WORD_ABBR_NM.name()));
        	}
    	
            row.put(WORD_GRID.WORD_NM.name(), CastUtils.toString(row.get(WORD_GRID.WORD_NM.name()), true));
            if (wordNmDupChk) {
        		compare.put(WORD_GRID.WORD_NM.name(), row.get(WORD_GRID.WORD_NM.name()));
        	}
        	row.put(WORD_GRID.CLASS_YN.name(), CastUtils.toString(row.get(WORD_GRID.CLASS_YN.name()), true));
            if ("".equals(CastUtils.toString(row.get(WORD_GRID.CLASS_YN.name())))) {
                row.put(WORD_GRID.CLASS_YN.name(), "N");
            }
    	    
            boolean isDuplYn = wordAbbrNms.contains(compare);
    		row.put(WORD_GRID.DUPL_YN.name(), isDuplYn?"Y":"N");
    		
    		boolean isPhbChar = isWordPhbCharChexk(CastUtils.toString(row.get(WORD_GRID.WORD_ABBR_NM.name())));
    		row.put(WORD_GRID.PHB_CHAR_YN.name(), isPhbChar?"Y":"N");
    		
            if (
        	    (String)row.get(WORD_GRID.WORD_NM.name()) == null || "".equals((String)row.get(WORD_GRID.WORD_NM.name()))
        	    || (String)row.get(WORD_GRID.WORD_ENG_NM.name()) == null || "".equals((String)row.get(WORD_GRID.WORD_ENG_NM.name()))
        	    || (String)row.get(WORD_GRID.WORD_ABBR_NM.name()) == null || "".equals((String)row.get(WORD_GRID.WORD_ABBR_NM.name()))
        	    || isDuplYn 
        	    || isPhbChar
            ) {
                row.put(WORD_GRID.VRF_RSLT.name(), "N");
            } else {
                row.put(WORD_GRID.VRF_RSLT.name(), "Y");
            }
    	}
    	return datas;
    }
    
    
    @Override
	public Map<String, Object> wordImport(Map<String, Object> data) {
		final List<Map<String, Object>> datas = (List<Map<String, Object>>)data.get("data");
		final Map<String, Object> parameter = new HashMap<String, Object>();
		
		SessionInfo sessionInfo = (SessionInfo)data.get("sessionInfo");
		
		int targetCnt = 0;
        int registCnt = 0;
        int errorCnt = 0;
        for (int i=datas.size()-1; i>=0; i--) {
            Map<String, Object> row = datas.get(i);
            if (row.get(WORD_GRID.CHK.name()) != null && (Boolean)row.get(WORD_GRID.CHK.name())) {
                targetCnt++;
                if (!"Y".equals(row.get(WORD_GRID.VRF_RSLT.name()))) {
                    errorCnt++;
                    continue;
                }
    			try {    				
    				Date now = new Date();    				
    				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    				final String dt = sdf.format(now);
    				
    				final PenObjM penObjM = new PenObjM();
    				penObjM.genObjId();
    				penObjM.putSessionInfo(sessionInfo);
    				
    				penObjM.setObjTypeId("010301L");
    				penObjM.setDelYn("N");
    				penObjM.setCretDt(dt);
    				penObjM.setChgDt(dt);
    				penObjM.setAdmObjId(CastUtils.toString(row.get(WORD_GRID.WORD_ABBR_NM.name())));
    				penObjM.setObjNm(CastUtils.toString(row.get(WORD_GRID.WORD_NM.name())));
    				penObjM.setObjAbbrNm(CastUtils.toString(row.get(WORD_GRID.WORD_ENG_NM.name())));
    				penObjM.setObjDesc("");
    				penObjM.setPathObjTypeId("");
    				penObjM.setPathObjId("");
    				registPenObjM(penObjM);
    				
    				//PEN_OBJ_D 占쏙옙占�
    				final PenObjD penObjD = new PenObjD(penObjM);
    				penObjD.setAtrIdSeq(101);
    				penObjD.setAtrValSeq(101);
    				penObjD.setObjAtrVal(CastUtils.toString(row.get(WORD_GRID.CLASS_YN.name())));
    				registPenObjD(penObjD);
    				
    				datas.remove(i);
                    registCnt++;
                } catch (Exception e) {
                    e.printStackTrace();
                    errorCnt++;
    			}
            }
		}

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("targetCnt", targetCnt);
        result.put("registCnt", registCnt);
        result.put("errorCnt", errorCnt);
        
        Map<String, Object> retrunVal = new HashMap<String, Object>();
        if (datas.size() == 0) {
            final Map<String, Object> tmp = new HashMap<String, Object>();
            tmp.put("CHK", null);
            datas.add(tmp);
        }
        retrunVal.put("data", datas);
        retrunVal.put("result", result);
        
        return retrunVal;
	}
    
	@Override
	public List<Map<String, Object>> wordUpload(InputStream is) {
		List<Map<String, Object>> list = new ArrayList<>();
		FileInputStream in = null;
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(is);
            XSSFSheet sheet = (XSSFSheet)wb.getSheet(BIZ_STD_CONST.TPL_SHEET_NM.DOMAIN.value());
            
            int rowIdx = BIZ_STD_CONST.DOMAIN_TPL_START_ROW;
            XSSFRow row = sheet.getRow(rowIdx++);
            String domainNm = null;
            if (row != null) {
            	domainNm = row.getCell((int) BIZ_STD_CONST.DOMAIN_SHEET_COL.DOMAIN_NM.value().get(0)).getStringCellValue();
            }
            while (domainNm != null && !"".equals(domainNm)) {
				Map<String, Object> map = new HashMap<>();
				map.put(BIZ_STD_CONST.DOMAIN_SHEET_COL.DOMAIN_GRP_NM.name(), row.getCell((int) BIZ_STD_CONST.DOMAIN_SHEET_COL.DOMAIN_GRP_NM.value().get(0)).getStringCellValue());
				map.put(BIZ_STD_CONST.DOMAIN_SHEET_COL.DOMAIN_NM.name(), domainNm);
				map.put(BIZ_STD_CONST.DOMAIN_SHEET_COL.ORACLE_DATA_TYPE.name(), row.getCell((int) BIZ_STD_CONST.DOMAIN_SHEET_COL.ORACLE_DATA_TYPE.value().get(0)).getStringCellValue());
				map.put(BIZ_STD_CONST.DOMAIN_SHEET_COL.PDA_DATA_TYPE.name(), row.getCell((int) BIZ_STD_CONST.DOMAIN_SHEET_COL.PDA_DATA_TYPE.value().get(0)).getStringCellValue());
				map.put(BIZ_STD_CONST.DOMAIN_SHEET_COL.TIBERO_DATA_TYPE.name(), row.getCell((int) BIZ_STD_CONST.DOMAIN_SHEET_COL.TIBERO_DATA_TYPE.value().get(0)).getStringCellValue());
				map.put(BIZ_STD_CONST.DOMAIN_SHEET_COL.DATA_LENGTH.name(), row.getCell((int) BIZ_STD_CONST.DOMAIN_SHEET_COL.DATA_LENGTH.value().get(0)).getStringCellValue());
				map.put(BIZ_STD_CONST.DOMAIN_SHEET_COL.DECIMAL_POINT.name(), row.getCell((int) BIZ_STD_CONST.DOMAIN_SHEET_COL.DECIMAL_POINT.value().get(0)).getStringCellValue());
				map.put(BIZ_STD_CONST.DOMAIN_SHEET_COL.DOMAIN_DESC.name(), row.getCell((int) BIZ_STD_CONST.DOMAIN_SHEET_COL.DOMAIN_DESC.value().get(0)).getStringCellValue());
            	list.add(map);
            	
            	row = sheet.getRow(rowIdx++);
            	domainNm = row.getCell((int) BIZ_STD_CONST.DOMAIN_SHEET_COL.DOMAIN_NM.value().get(0)).getStringCellValue();
			}
        } catch (Exception e) {
            wb = new HSSFWorkbook();
        } finally {
            try {in.close();} catch (IOException e) {}
        }
        
		return list;
	}
    

	private boolean termAbbrLengthChk =  DataEyeSettingsHolder.getInstance().getBizStd().getTermAbbrLengthChkEnable();
    private Integer termAbbrMaxLength =  DataEyeSettingsHolder.getInstance().getBizStd().getTermAbbrMaxLength();
    @Override
	public Map<String, Object> termVerifyCheck(Map<String, Object> data) {
		final List<Map<String, Object>> datas = (List<Map<String, Object>>)data.get("data");
		final Map<String, Object> parameter = new HashMap<String, Object>();
		final Map<String, Object> returns = new HashMap<String, Object>();
		
		
		List<Map<String, Object>>  terms =  commonDao.selectList("portal.biz.std.findWordSimpleList",new DaoParam(parameter));		
		
		final List<Map<String, Object>> dmnList =  commonDao.selectList("portal.biz.std.findDmnNm",new DaoParam(parameter));		
		
		final List<Map<String, Object>> termNms = commonDao.selectList("portal.biz.std.findTermNms",new DaoParam(parameter));		
		
		Map<String, Object> chkTermMap = new HashMap<String,Object>();
		int rowNum = 0;
		for(final Iterator<Map<String, Object>> iterator=datas.iterator(); iterator.hasNext(); ) {
		    Map<String, Object> row = iterator.next();
			TermChecker checker = new TermChecker(terms);
			List<TermModel> temModels = null;
			
			final String korTermNm = CastUtils.toString(row.get(TERM_GRID.KOR_TERM_NM.name()), true);
			final String dmnNm = CastUtils.toString(row.get(TERM_GRID.DMN_NM.name()), true);
			
			row.put(TERM_GRID.KOR_TERM_NM.name(), korTermNm);
			row.put(TERM_GRID.DMN_NM.name(), dmnNm);
            
			//TERM Check
			if (!"".equals(korTermNm)) {
				checker.check((String)row.get(TERM_GRID.KOR_TERM_NM.name()));
				if (checker.isValid()) {
					temModels = checker.getValidTerms();
					row.put(TERM_GRID.ENG_TERM_NM.name(), temModels.get(0).ENG_TERM);
					row.put(TERM_GRID.KOR_CB_NM.name(), temModels.get(0).KOR_TERM);
					row.put(TERM_GRID.TERM_CNT.name(), temModels.size());
					row.put(TERM_GRID.CLASS_YN.name(), "".equals(temModels.get(0).CLASS_WORD_ID)?"N":"Y");
				} else {
					temModels = checker.getInvalidTerms();
					if (temModels.isEmpty()) {
						row.put(TERM_GRID.ENG_TERM_NM.name(), "");
						row.put(TERM_GRID.KOR_CB_NM.name(), "");
						row.put(TERM_GRID.TERM_CNT.name(), 0);
						row.put(TERM_GRID.CLASS_YN.name(), "N");
					} else {
						row.put(TERM_GRID.ENG_TERM_NM.name(), temModels.get(0).ENG_TERM+"_???");
						row.put(TERM_GRID.KOR_CB_NM.name(), temModels.get(0).KOR_TERM+"_???");
						row.put(TERM_GRID.TERM_CNT.name(), 0);
						row.put(TERM_GRID.CLASS_YN.name(), "".equals(temModels.get(0).CLASS_WORD_ID)?"N":"Y");
					}
				}
				chkTermMap.put("row_"+String.valueOf(rowNum++), temModels);
			} else {
				row.put(TERM_GRID.ENG_TERM_NM.name(), "");
				row.put(TERM_GRID.KOR_CB_NM.name(), "");
				row.put(TERM_GRID.TERM_CNT.name(), 0);
				row.put(TERM_GRID.CLASS_YN.name(), "N");
			}
			
			//DOMAIN Check
			boolean isDmnYn = dmnList.contains(dmnNm);
			if (isDmnYn) {
				row.put(TERM_GRID.DMN_YN.name(), "Y");
			} else {
				row.put(TERM_GRID.DMN_YN.name(), "N");
			}
			
			//DUPL Check
			boolean isDuplYn = termNms.contains(korTermNm);
			row.put(TERM_GRID.DUPL_YN.name(), isDuplYn?"Y":"N");
			
			//MAX LEN CHECK
			Integer len = CastUtils.toString(row.get(TERM_GRID.ENG_TERM_NM.name())).length();
            if (termAbbrLengthChk) {
                row.put(TERM_GRID.TERM_LEN.name(), len);
            }
            
			if (
			        (Integer)row.get(TERM_GRID.TERM_CNT.name()) != 0 
			        && "Y".equals((String)row.get(TERM_GRID.CLASS_YN.name()))
			        && "Y".equals((String)row.get(TERM_GRID.DMN_YN.name()))
			        && "N".equals((String)row.get(TERM_GRID.DUPL_YN.name()))
			        && len <= termAbbrMaxLength) {
				row.put(TERM_GRID.VRF_RSLT.name(), "Y");
			} else {
				row.put(TERM_GRID.VRF_RSLT.name(), "N");
			}
		}
		returns.put("grid", datas);
		returns.put("select", chkTermMap);
		
		return returns;
	}
	
	@Override
	public Map<String, Object> termImport(Map<String, Object> data) {
		final List<Map<String, Object>> datas = (List<Map<String, Object>>)data.get("data");
		final Map<String, Object> parameter = new HashMap<String, Object>();
	
		
		SessionInfo sessionInfo = (SessionInfo)data.get("sessionInfo");    

		
		List<Map<String, Object>>  dmns = commonDao.selectList("portal_biz.dmn",new DaoParam(parameter));
		
		int targetCnt = 0;
		int registCnt = 0;
		int errorCnt = 0;
		for (int i=datas.size()-1; i>=0; i--) {
		    Map<String, Object> row = datas.get(i);
		    if (row.get(TERM_GRID.CHK.name()) != null && (Boolean)row.get(TERM_GRID.CHK.name())) {
		        targetCnt++;
    			if (!"Y".equals(row.get(TERM_GRID.VRF_RSLT.name()))) {
    			    errorCnt++;
    			    continue;
    			}
    			try {
    				//PEN_OBJ_M 占쏙옙占�    				
    				Date now = new Date();    				
    				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    				final String dt = sdf.format(now);
    				
    				//{DOMAIN=, WORD_ID1=010301L_000000164, WORD_ID2=010301L_000000169, WORD_ID3=010301L_000001453, WORD_ID4=, WORD_ID5=, WORD_ID6=, WORD_ID7=, WORD_ID8=, WORD_ID9=, 
    				//CLASS_WORD_ID=010301L_000000848, KOR_WORD1=2G, KOR_WORD2=3G, KOR_WORD3=占쏙옙占쏙옙, KOR_WORD4=, KOR_WORD5=, KOR_WORD6=, KOR_WORD7=, KOR_WORD8=, KOR_WORD9=, CLASS_KOR_WORD=占쌘듸옙, 
    				//ENG_WORD1=2G, ENG_WORD2=3G, ENG_WORD3=DIV, ENG_WORD4=, ENG_WORD5=, ENG_WORD6=, ENG_WORD7=, ENG_WORD8=, ENG_WORD9=, CLASS_ENG_WORD=CD, KOR_TERM=2G_3G_占쏙옙占쏙옙_占쌘듸옙, ENG_TERM=2G_3G_DIV_CD, STEP=4, VALID=true}
    				Map<String, Object> termMap = (Map<String, Object>)row.get(TERM_GRID.TERM_OBJECT.name());
    				final PenObjM penObjM = new PenObjM();
    				penObjM.genObjId();
    				penObjM.putSessionInfo(sessionInfo);
    				
    				penObjM.setObjTypeId("010302L");
    				penObjM.setDelYn("N");
    				penObjM.setCretDt(dt);
    				penObjM.setChgDt(dt);
    				penObjM.setAdmObjId(CastUtils.toString(termMap.get("ENG_TERM")));
    				penObjM.setObjNm(CastUtils.toString(termMap.get("KOR_TERM")).replaceAll("_", ""));
    				penObjM.setObjAbbrNm("");
    				penObjM.setObjDesc("");
    				penObjM.setPathObjTypeId("010301L");
    				penObjM.setPathObjId(CastUtils.toString(termMap.get("CLASS_WORD_ID")));
    				registPenObjM(penObjM);
    				
    				final PenObjD penObjD = new PenObjD(penObjM);
    				final String dmnNm = (String)row.get(TERM_GRID.DMN_NM.name());
    				String dmnId = null;
    				for(Iterator<Map<String, Object>> iterator=dmns.iterator(); iterator.hasNext(); ) {
    					final Map<String, Object> dmn = iterator.next();
    					if (dmnNm.equals((String)dmn.get("OBJ_NM"))) {
    						dmnId = (String)dmn.get("OBJ_ID");
    						break;
    					}
    				}
    				if (dmnId == null || "".equals(dmnId)) {
    					throw new Exception("not found domain info.");
    				}
    				//DOMAIN 
    				penObjD.setAtrIdSeq(101);
    				penObjD.setAtrValSeq(101);
    				penObjD.setObjAtrVal(dmnId);
    				registPenObjD(penObjD);
    				
    				//WORD1,WORD2, WORD3, WORD4, WORD5, WORD6, WORD7, WORD8, WORD9
    				for(int j=1; j<10; j++) {
    					final String key = "WORD_ID"+j;
    					if ("".equals(termMap.get(key))) {
    						break;
    					} else {
    						penObjD.setAtrIdSeq(101+j);
    						penObjD.setAtrValSeq(101);
    						penObjD.setObjAtrVal(CastUtils.toString(termMap.get(key)));
    						registPenObjD(penObjD);
    					}
    				}
    				datas.remove(i);
    				registCnt++;
    			} catch (Exception e) {
    				e.printStackTrace();
    				errorCnt++;
    			}
		    }
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("targetCnt", targetCnt);
		result.put("registCnt", registCnt);
		result.put("errorCnt", errorCnt);
		
		Map<String, Object> retrunVal = new HashMap<String, Object>();
		if (datas.size() == 0) {
            final Map<String, Object> tmp = new HashMap<String, Object>();
            tmp.put("CHK", null);
            datas.add(tmp);
        }
        retrunVal.put("data", datas);
        retrunVal.put("result", result);
        
		return retrunVal;
	}
    
	@Override
	public List<Map<String, Object>> termUpload(InputStream is) {
		List<Map<String, Object>> list = new ArrayList<>();
		FileInputStream in = null;
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(is);
            XSSFSheet sheet = (XSSFSheet)wb.getSheet(BIZ_STD_CONST.TPL_SHEET_NM.TERM.value());
            
            int rowIdx = BIZ_STD_CONST.TERM_TPL_START_ROW;
            XSSFRow row = sheet.getRow(rowIdx++);
            String termNm = null;
            if (row != null) {
            	termNm = row.getCell((int) BIZ_STD_CONST.TERM_SHEET_COL.TERM_NM.value().get(0)).getStringCellValue();
            }
            while (termNm != null && !"".equals(termNm)) {
				Map<String, Object> map = new HashMap<>();
				map.put(BIZ_STD_CONST.TERM_SHEET_COL.TERM_NM.name(), termNm);
				map.put(BIZ_STD_CONST.TERM_SHEET_COL.DOMAIN_NM.name(), row.getCell((int) BIZ_STD_CONST.TERM_SHEET_COL.DOMAIN_NM.value().get(0)).getStringCellValue());
				map.put(BIZ_STD_CONST.TERM_SHEET_COL.KOR_SPLIT.name(), row.getCell((int) BIZ_STD_CONST.TERM_SHEET_COL.KOR_SPLIT.value().get(0)).getStringCellValue());
				map.put(BIZ_STD_CONST.TERM_SHEET_COL.ENG_SPLIT.name(), row.getCell((int) BIZ_STD_CONST.TERM_SHEET_COL.ENG_SPLIT.value().get(0)).getStringCellValue());
            	list.add(map);
            	
            	row = sheet.getRow(rowIdx++);
            	termNm = row.getCell((int) BIZ_STD_CONST.TERM_SHEET_COL.TERM_NM.value().get(0)).getStringCellValue();
			}
        } catch (Exception e) {
            wb = new HSSFWorkbook();
        } finally {
            try {in.close();} catch (IOException e) {}
        }
        
		return list;
	}
	
    @Override
	public List<Map<String, Object>> dmnVerifyCheck(Map<String, Object> data) {
		final List<Map<String, Object>> datas = (List<Map<String, Object>>)data.get("data");
		final Map<String, Object> parameter = new HashMap<String, Object>();
		
		final List<Map<String, Object>> dmnGrpList = commonDao.selectList("portal.biz.std.dmnGrpNm",new DaoParam(parameter));
		
		final List<Map<String, Object>> dataTypeList = commonDao.selectList("portal.biz.std.dmnDataTypeForOracle",new DaoParam(parameter));
		
		final List<Map<String, Object>> dmnList = commonDao.selectList("portal.biz.std.dmnNm",new DaoParam(parameter));
		
		for(final Iterator<Map<String, Object>> iterator=datas.iterator(); iterator.hasNext(); ) {
		    Map<String, Object> row = iterator.next();
			boolean isDuplYn = dmnList.contains(row.get(DMN_GRID.DMN_NM.name()));
			row.put(DMN_GRID.DUPL_YN.name(), isDuplYn?"Y":"N");
			boolean isDataTypeYn = isDmnDataTypeValid(dataTypeList, (String)row.get(DMN_GRID.DMN_DATA_TYPE.name()));
			row.put(DMN_GRID.DATA_TYPE_YN.name(), isDataTypeYn?"Y":"N");
			boolean isValidGrpPass = dmnGrpList.contains(row.get(DMN_GRID.DMN_GRP_CD.name()));
			row.put(DMN_GRID.DMN_GRP_YN.name(), isValidGrpPass?"Y":"N");
			
			if (!isDuplYn && isDataTypeYn && isValidGrpPass) {
				row.put(DMN_GRID.VRF_RSLT.name(), "Y");
			} else {
				row.put(DMN_GRID.VRF_RSLT.name(), "N");
			}
		}
		return datas;
	}
	
	@Override
	public Map<String, Object> dmnImport(Map<String, Object> data) {
		final List<Map<String, Object>> datas = (List<Map<String, Object>>)data.get("data");
		final Map<String, Object> parameter = new HashMap<String, Object>();
		
		SessionInfo sessionInfo = (SessionInfo)data.get("sessionInfo");    
		
		final List<Map<String, Object>> dmnGrpList = commonDao.selectList("portal_biz.dmnGrp",new DaoParam(parameter));
		
		int targetCnt = 0;
        int registCnt = 0;
        int errorCnt = 0;
        for (int i=datas.size()-1; i>=0; i--) {
            Map<String, Object> row = datas.get(i);
            if (row.get(DMN_GRID.CHK.name()) != null && (Boolean)row.get(DMN_GRID.CHK.name())) {
                targetCnt++;
                if (!"Y".equals(row.get(DMN_GRID.VRF_RSLT.name()))) {
                    errorCnt++;
                    continue;
                }
    			try {
    				Date now = new Date();    				
    				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    				final String dt = sdf.format(now);
    				
    				final PenObjM penObjM = new PenObjM();
    				penObjM.genObjId();
    				penObjM.putSessionInfo(sessionInfo);
    				
    				penObjM.setObjTypeId("010303L");
    				penObjM.setDelYn("N");
    				penObjM.setCretDt(dt);
    				penObjM.setChgDt(dt);
    				penObjM.setAdmObjId("");
    				penObjM.setObjNm(CastUtils.toString(row.get(DMN_GRID.DMN_NM.name())));
    				penObjM.setObjAbbrNm(CastUtils.toString(row.get(DMN_GRID.DMN_DATA_TYPE.name())));
    				penObjM.setObjDesc("");
    				penObjM.setPathObjTypeId("010304L");
    				penObjM.setPathObjId(CastUtils.toString(getValueByMap(dmnGrpList, "OBJ_NM", (String)row.get(DMN_GRID.DMN_GRP_CD.name()), "OBJ_ID")));
    				registPenObjM(penObjM);
    				
                    final PenObjD penObjD = new PenObjD(penObjM);
                    penObjD.setAtrIdSeq(101);
                    penObjD.setAtrValSeq(101);
                    penObjD.setObjAtrVal(getDmnDataTypePrecision((String)row.get(DMN_GRID.DMN_DATA_TYPE.name())));
                    registPenObjD(penObjD);
                    
                    penObjD.setAtrIdSeq(102);
                    penObjD.setAtrValSeq(101);
                    penObjD.setObjAtrVal(getDmnDataTypeScale((String)row.get(DMN_GRID.DMN_DATA_TYPE.name())));
                    registPenObjD(penObjD);
                    
    				datas.remove(i);
    				registCnt++;
    			} catch (Exception e) {
    			    e.printStackTrace();
                    errorCnt++;
    			}
            }
		}

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("targetCnt", targetCnt);
        result.put("registCnt", registCnt);
        result.put("errorCnt", errorCnt);
        
        Map<String, Object> retrunVal = new HashMap<String, Object>();
        if (datas.size() == 0) {
            final Map<String, Object> tmp = new HashMap<String, Object>();
            tmp.put("CHK", null);
            datas.add(tmp);
        }
        retrunVal.put("data", datas);
        retrunVal.put("result", result);
        
        return retrunVal;
	}
    
	@Override
	public List<Map<String, Object>> dmnUpload(InputStream is) {
		List<Map<String, Object>> list = new ArrayList<>();
		FileInputStream in = null;
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(is);
            XSSFSheet sheet = (XSSFSheet)wb.getSheet(BIZ_STD_CONST.TPL_SHEET_NM.DOMAIN.value());
            
            int rowIdx = BIZ_STD_CONST.DOMAIN_TPL_START_ROW;
            XSSFRow row = sheet.getRow(rowIdx++);
            String domainNm = null;
            if (row != null) {
            	domainNm = row.getCell((int) BIZ_STD_CONST.DOMAIN_SHEET_COL.DOMAIN_NM.value().get(0)).getStringCellValue();
            }
            while (domainNm != null && !"".equals(domainNm)) {
				Map<String, Object> map = new HashMap<>();
				map.put(BIZ_STD_CONST.DOMAIN_SHEET_COL.DOMAIN_GRP_NM.name(), row.getCell((int) BIZ_STD_CONST.DOMAIN_SHEET_COL.DOMAIN_GRP_NM.value().get(0)).getStringCellValue());
				map.put(BIZ_STD_CONST.DOMAIN_SHEET_COL.DOMAIN_NM.name(), domainNm);
				map.put(BIZ_STD_CONST.DOMAIN_SHEET_COL.ORACLE_DATA_TYPE.name(), row.getCell((int) BIZ_STD_CONST.DOMAIN_SHEET_COL.ORACLE_DATA_TYPE.value().get(0)).getStringCellValue());
				map.put(BIZ_STD_CONST.DOMAIN_SHEET_COL.PDA_DATA_TYPE.name(), row.getCell((int) BIZ_STD_CONST.DOMAIN_SHEET_COL.PDA_DATA_TYPE.value().get(0)).getStringCellValue());
				map.put(BIZ_STD_CONST.DOMAIN_SHEET_COL.TIBERO_DATA_TYPE.name(), row.getCell((int) BIZ_STD_CONST.DOMAIN_SHEET_COL.TIBERO_DATA_TYPE.value().get(0)).getStringCellValue());
				map.put(BIZ_STD_CONST.DOMAIN_SHEET_COL.DATA_LENGTH.name(), row.getCell((int) BIZ_STD_CONST.DOMAIN_SHEET_COL.DATA_LENGTH.value().get(0)).getStringCellValue());
				map.put(BIZ_STD_CONST.DOMAIN_SHEET_COL.DECIMAL_POINT.name(), row.getCell((int) BIZ_STD_CONST.DOMAIN_SHEET_COL.DECIMAL_POINT.value().get(0)).getStringCellValue());
				map.put(BIZ_STD_CONST.DOMAIN_SHEET_COL.DOMAIN_DESC.name(), row.getCell((int) BIZ_STD_CONST.DOMAIN_SHEET_COL.DOMAIN_DESC.value().get(0)).getStringCellValue());
            	list.add(map);
            	
            	row = sheet.getRow(rowIdx++);
            	domainNm = row.getCell((int) BIZ_STD_CONST.DOMAIN_SHEET_COL.DOMAIN_NM.value().get(0)).getStringCellValue();
			}
        } catch (Exception e) {
            wb = new HSSFWorkbook();
        } finally {
            try {in.close();} catch (IOException e) {}
        }
        
		return list;
	}
	
	private String getDmnDataTypeScale(String s) {
        if (s == null) return null;
        String scale = null;
        Pattern pattern = Pattern.compile("([\\w]+)\\s*(\\(\\s*([0-9]+\\s*([,]\\s*[0-9]+)?\\s*)\\)\\s*)?");
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches()) {
            for(int i=0; i<matcher.groupCount(); i++) {
                if (i==3) {
                    if (null != matcher.group(i)) {
                        scale = matcher.group(i).trim();
                    } else {
                        scale = "";
                    }
                }
            }
        }
        
        return scale;
    }
    	
	private Object getValueByMap(List<Map<String, Object>> list, String searchKey, String searchValue, String findKey) {
		for(final Iterator<Map<String, Object>> iterator=list.iterator(); iterator.hasNext(); ) {
			Map<String, Object> map = iterator.next();
			if ( searchValue.equals((String)map.get(searchKey))) {
				return map.get(findKey);
			}
		}
		throw new BizException("ref key not found.");
	} 
	
	
	private String getDmnDataTypePrecision(String s) {
        if (s == null) return null;
        String precision = null;
        Pattern pattern = Pattern.compile("([\\w]+)\\s*(\\(\\s*([0-9]+\\s*([,]\\s*[0-9]+)?\\s*)\\)\\s*)?");
        Matcher matcher = pattern.matcher(s);
        if (matcher.matches()) {
            for(int i=0; i<matcher.groupCount(); i++) {
                if (i==1) {
                    precision = matcher.group(i).trim();
                }
            }
        }
        
        return precision;
    }
    
    
	private boolean isDmnDataTypeValid(List<Map<String, Object>> dataTypeList, String s) {
	    if (s == null) return false;
	    Pattern pattern = Pattern.compile("([\\w]+)\\s*(\\(\\s*([0-9]+\\s*([,]\\s*[0-9]+)?\\s*)\\)\\s*)?");
        Matcher matcher = pattern.matcher(s);
        String dataType = "";
        if (matcher.matches()) {
            for(int i=0; i<matcher.groupCount(); i++) {
                if (i==1) {
                    dataType = matcher.group(i);
                    System.out.println(matcher.group(i));
                } else if (i==3) {
                    System.out.println(matcher.group(i));
                }
            }
            if (dataTypeList.contains(dataType)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
	}
	
	private void registPenObjM(Map<String, Object> map) {
		map.put("timestampFormat", "YYYY-MM-DD HH24:MI:SS");
		commonDao.execute("PEN_OBJ_M.insert", map);
	}
	
	private void registPenObjD(Map<String, Object> map) {
		map.put("timestampFormat", "YYYY-MM-DD HH24:MI:SS");
		commonDao.execute("PEN_OBJ_D.insert", map);
	}

	private boolean isWordPhbCharChexk(String s) {
	    Pattern p = Pattern.compile("^[a-zA-Z0-9]+$");
	    Matcher m = p.matcher(s);
	    if (m.find()){
	        return false;
	    }else{
	        return true;
	    }
    }
}
