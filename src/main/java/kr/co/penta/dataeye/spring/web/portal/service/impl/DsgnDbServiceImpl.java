package kr.co.penta.dataeye.spring.web.portal.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.penta.dataeye.common.entities.meta.PenObjD;
import kr.co.penta.dataeye.common.entities.meta.PenObjM;
import kr.co.penta.dataeye.common.exception.ServiceRuntimeException;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.consts.DSGN_DB_CONST;
import kr.co.penta.dataeye.consts.DSGN_DB_CONST.VALID_CHK;
import kr.co.penta.dataeye.spring.mybatis.dao.CommonDao;
import kr.co.penta.dataeye.spring.mybatis.dao.MetaDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.portal.service.DsgnDbService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;

/**
 * Created by Administrator on 2016-11-30.
 */
@Service
public class DsgnDbServiceImpl implements DsgnDbService {
	private final static String VALID_SUFFIX = "_VALID";

	@Autowired CommonDao commonDao;

	@Autowired private MetaDao metaDao;

	@Override
	public Map<String, Object> uploadDsgnTab(File f, String databaseId) {
		FileInputStream in = null;
		Workbook workbook = null;
		try {
			in = new FileInputStream(f);
			workbook = WorkbookFactory.create(in);
			f.deleteOnExit();
		} catch (Exception e) {
			throw new ServiceRuntimeException("not loaded file.");
		} finally {
			try {in.close();} catch (IOException e) {}
		}

		XSSFSheet tabSheet = (XSSFSheet)workbook.getSheet(DSGN_DB_CONST.SHEET_NAME.TAB.value());
		if (tabSheet == null) {
			throw new ServiceRuntimeException("유효한 템플릿이 아닙니다.");
		}

		ErrCnt errCnt = new ErrCnt(0);
		List<Map<String, Object>> dsgnTabList = loadDsgnTab(tabSheet, databaseId, errCnt);
		dsgnTabListCheck(dsgnTabList, databaseId, errCnt);

		Map<String, Object> uploadResult = new HashMap<>();
		uploadResult.put("DSGN_TAB_ERR", errCnt.getValue());

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("DSGN_TAB", dsgnTabList);
		result.put("UPLOAD_RESULT", uploadResult);

		return result;
	}

	private List<Map<String, Object>> loadDsgnTab(XSSFSheet tabSheet, String databaseId, ErrCnt errCnt) {
		int lastRowNum = tabSheet.getLastRowNum();
		List<Map<String, Object>> result = new ArrayList<>();
		for(int i=DSGN_DB_CONST.START_ROW; i<=lastRowNum; i++) {
			XSSFRow row = tabSheet.getRow(i);
			//순번이 "" 이면 종료(마지막 Row로 판단)한다.
			String seq = getCellValue(row.getCell((int)DSGN_DB_CONST.SHEET_TAB_COL.SEQ.value().get(0)));
			if ("".equals(seq)) {
				break;
			}

			Map<String, Object> rowData = new HashMap<>();
			rowData.put(DSGN_DB_CONST.SHEET_TAB_COL.DB_ID_VAL.name(), databaseId);

			int rowInvalidCnt = 0;
			for(int j=0; j<DSGN_DB_CONST.SHEET_TAB_COL.values().length; j++) {
				DSGN_DB_CONST.SHEET_TAB_COL COL = DSGN_DB_CONST.SHEET_TAB_COL.values()[j];

				List<Object> colInfo =  COL.value();
				int colIdx = (int)colInfo.get(0);
				if (colIdx == -1) continue;

				boolean isRequire = (boolean)colInfo.get(1);
				XSSFCell cell = row.getCell(colIdx);
				String value = getCellValue(cell);
				rowData.put(COL.name(), value);

				//필수요구항목인경우 VALID 값을 우선 N로 셋팅한다.
				//dsgnTabListCheck 에서 유효성을 체크해서 Y로 업데이트 됨
				if (isRequire && !COL.equals(DSGN_DB_CONST.SHEET_TAB_COL.SEQ)) {
					rowData.put(COL.name()+VALID_SUFFIX, "N");
					rowInvalidCnt++;
				} else {
					rowData.put(COL.name()+VALID_SUFFIX, "Y");
				}
			}
			
			//필수요구항목의 INVALID 수와 IS_VALID 를 셋팅한다.
			//dsgnTabListCheck 에서 INVALID 수를 줄여줌
			if (rowInvalidCnt == 0) {
				rowData.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "Y");
				rowData.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), 0);
			} else {
				rowData.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "N");
				rowData.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), rowInvalidCnt);
				errCnt.add(1);
			}        	
			result.add(rowData);
		}

		return result;
	}

	@Override
	public Map<String, Object> uploadDsgnCol(File f, String databaseId) {
		FileInputStream in = null;
		Workbook workbook = null;
		try {
			in = new FileInputStream(f);
			workbook = WorkbookFactory.create(in);
			f.deleteOnExit();
		} catch (Exception e) {
			throw new ServiceRuntimeException("not loaded file.");
		} finally {
			try {in.close();} catch (IOException e) {}
		}

		XSSFSheet colSheet = (XSSFSheet)workbook.getSheet(DSGN_DB_CONST.SHEET_NAME.COL.value());
		if (colSheet == null) {
			throw new ServiceRuntimeException("유효한 템플릿이 아닙니다.");
		}

		ErrCnt errCnt = new ErrCnt(0);
		List<Map<String, Object>> dsgnColList = loadDsgnCol(colSheet, databaseId, errCnt);
		dsgnColListCheck(dsgnColList, databaseId, errCnt);

		Map<String, Object> uploadResult = new HashMap<>();
		uploadResult.put("DSGN_COL_ERR", errCnt.getValue());

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("DSGN_COL", dsgnColList);
		result.put("UPLOAD_RESULT", uploadResult);

		return result;
	}

	private List<Map<String, Object>> loadDsgnCol(XSSFSheet colSheet, String databaseId, ErrCnt errCnt) {
		int lastRowNum = colSheet.getLastRowNum();
		List<Map<String, Object>> result = new ArrayList<>();
		for(int i=DSGN_DB_CONST.START_ROW; i<=lastRowNum; i++) {
			XSSFRow row = colSheet.getRow(i);
			//순번이 "" 이면 종료(마지막 Row로 판단)한다.
			String seq = getCellValue(row.getCell((int)DSGN_DB_CONST.SHEET_COL_COL.SEQ.value().get(0)));
			if ("".equals(seq)) {
				break;
			}

			Map<String, Object> rowData = new HashMap<>();        	
			int rowInvalidCnt = 0;
			for(int j=0; j<DSGN_DB_CONST.SHEET_COL_COL.values().length; j++) {
				DSGN_DB_CONST.SHEET_COL_COL COL = DSGN_DB_CONST.SHEET_COL_COL.values()[j];

				List<Object> colInfo =  COL.value();
				int colIdx = (int)colInfo.get(0);
				if (colIdx == -1) continue;

				boolean isRequire = (boolean)colInfo.get(1);
				XSSFCell cell = row.getCell(colIdx);
				String value = getCellValue(cell);
				rowData.put(COL.name(), value);

				//if (isRequire && "".equals(value)) {
				if (isRequire && !COL.equals(DSGN_DB_CONST.SHEET_COL_COL.SEQ)) {
					rowData.put(COL.name()+VALID_SUFFIX, "N");
					rowInvalidCnt++;
				} else {
					rowData.put(COL.name()+VALID_SUFFIX, "Y");
				}
			}

			if (rowInvalidCnt == 0) {
				rowData.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "Y");
				rowData.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), 0);
			} else {
				rowData.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "N");
				rowData.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), rowInvalidCnt);
				errCnt.add(1);
			}        	
			result.add(rowData);
		}

		return result;
	}

	private void dsgnTabListCheck(List<Map<String, Object>> dsgnTabList, String databaseId, ErrCnt errCnt) {
		DaoParam daoParam = new DaoParam();
		daoParam.put("databaseId", databaseId);
		Map<String, Object> dbNmMap = commonDao.selectOne("dsgn.db.getDbNmByDbId", daoParam);
		Map<String, Object> dbTypeMap = commonDao.selectOne("dsgn.db.getDbTypeByDbId", daoParam);
		List<Map<String, Object>> dbSchemaList = commonDao.selectList("dsgn.db.findDbSchemaByDbId", daoParam);
		
		//app system
		daoParam.clear();
		daoParam.put("objTypeId", DSGN_DB_CONST.OBJ_TYPE.APPSYS.value());
		List<Map<String, Object >> appSysList = commonDao.selectList("metapublic.findObjM", daoParam);

		//wordList
		daoParam.clear();
		daoParam.put("objTypeId", DSGN_DB_CONST.OBJ_TYPE.STD_WORD.value());
		List<Map<String, Object >> wordListTmp = commonDao.selectList("metapublic.findObjM", daoParam);
		Map<String, List<String>> wordList = new HashMap<>();
		for(Map<String, Object > map : wordListTmp) {
			String colNm = CastUtils.toString(map.get("ADM_OBJ_ID"));
			String atrNm = CastUtils.toString(map.get("OBJ_NM"));
			if (wordList.containsKey(colNm)) {
				wordList.get(colNm).add(atrNm);
			} else {
				List<String> atrNmList = new ArrayList<String>();
				atrNmList.addAll(Arrays.asList(new String[]{atrNm}));
				wordList.put(colNm, atrNmList);
			};
		}
		
		//테이블목록
		daoParam.clear();
		daoParam.put("objTypeId", DSGN_DB_CONST.OBJ_TYPE.TAB.value());
		daoParam.put("pathObjTypeId", DSGN_DB_CONST.OBJ_TYPE.DB.value());
		daoParam.put("pathOId", databaseId);
		List<Map<String, Object >> tabList = commonDao.selectList("metapublic.findObjM", daoParam);
		
		//주제영역
		daoParam.clear();
		daoParam.put("objTypeId", DSGN_DB_CONST.OBJ_TYPE.SUBJ.value());
		List<Map<String, Object >> subjList = commonDao.selectList("metapublic.findObjM", daoParam);

		//테이블유형구분(테이블, 뷰)
		daoParam.clear();
		daoParam.put("cdGrpId", "DSGN_COMM_INFOTYP");
		List<Map<String, Object >> infoTypList = commonDao.selectList("metapublic.findCd", daoParam);

		//테이블정보구분(Fact, Dimension, History, Summary, Code)
		daoParam.clear();
		daoParam.put("cdGrpId", "DSGN_COMM_CLSTYP");
		List<Map<String, Object >> clsTypList = commonDao.selectList("metapublic.findCd", daoParam);
		
		//테이블기타구분(백업, 임시, 비식별)
		daoParam.clear();
		daoParam.put("cdGrpId", "DSGN_COMM_ETCTYP");
		List<Map<String, Object >> etcTypList = commonDao.selectList("metapublic.findCd", daoParam);

		for(Map<String, Object> map : dsgnTabList) {
			int invalidCnt = CastUtils.toInteger(map.get(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name()));
			
			//DB TYPE
			if (dbTypeMap != null) {
				String dbms = CastUtils.toString(map.get(DSGN_DB_CONST.SHEET_TAB_COL.DBMS.name()));
				if (dbms.equals(CastUtils.toString(dbTypeMap.get("DB_TYPE")))) {
					invalidCnt = invalidCnt -1;
					map.put(DSGN_DB_CONST.SHEET_TAB_COL.DBMS.name()+VALID_SUFFIX, "Y");
					map.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), invalidCnt);
					if (invalidCnt == 0) {
						map.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "Y");
						errCnt.add(-1); 
					}
				}
			}
			
			//DB NM
			if (dbNmMap != null) {
				String dbNm = CastUtils.toString(map.get(DSGN_DB_CONST.SHEET_TAB_COL.DB_NM.name()));
				if (dbNm.equals(CastUtils.toString(dbNmMap.get("DB_NM")))) {
					invalidCnt = invalidCnt -1;
					map.put(DSGN_DB_CONST.SHEET_TAB_COL.DB_NM.name()+VALID_SUFFIX, "Y");
					map.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), invalidCnt);
					if (invalidCnt == 0) {
						map.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "Y");
						errCnt.add(-1); 
					}
				}
			}
			
			//DB Schema
			String schemaNm  = CastUtils.toString(map.get(DSGN_DB_CONST.SHEET_TAB_COL.SCHEMA_NM.name()));
			if (!"".equals(schemaNm)) {
				for(Map<String, Object> map2 : dbSchemaList) {
					String schemaNm2 = CastUtils.toString(map2.get("SCHEMA_NM"));
					if (schemaNm.equals(schemaNm2)) {
						invalidCnt = invalidCnt -1;
						map.put(DSGN_DB_CONST.SHEET_TAB_COL.SCHEMA_NM.name()+VALID_SUFFIX, "Y");
						map.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), invalidCnt);
						if (invalidCnt == 0) {
							map.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "Y");
							errCnt.add(-1);
						}
						break;
					}
				}
			}
			
			//시스템 체크
			String appSysNm = CastUtils.toString(map.get(DSGN_DB_CONST.SHEET_TAB_COL.APPSYS.name()));
			if (!"".equals(appSysNm)) {
				for(Map<String, Object> map2 : appSysList) {
					String appSysNm2 = CastUtils.toString(map2.get("OBJ_NM"));
					if (appSysNm.equals(appSysNm2)) {
						invalidCnt = invalidCnt -1;
						map.put(DSGN_DB_CONST.SHEET_TAB_COL.APPSYS.name()+VALID_SUFFIX, "Y");
						map.put(DSGN_DB_CONST.SHEET_TAB_COL.APPSYS_VAL.name(), CastUtils.toString(map2.get("OBJ_ID")));
						map.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), invalidCnt);
						if (invalidCnt == 0) {
							map.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "Y");
							errCnt.add(-1);
						}
						break;
					}
				}
			}

			boolean isMatch = false;
			boolean isTabNmValid = true;
			String tabChkNm = "";
			String tabChkEntityNm = "";
			String tabChkEntitySuffixNm = "";
			
			//테이블유형구분(테이블, 뷰)
			String tabTypeGbn = CastUtils.toString(map.get(DSGN_DB_CONST.SHEET_TAB_COL.TAB_TYPE_GBN.name()));
			if (!"".equals(tabTypeGbn)) {
				isMatch = false;
				for(Map<String, Object> map2 : infoTypList) {
					String tabTypeGbn2 = CastUtils.toString(map2.get("CD_ID"));
					if (tabTypeGbn.equals(tabTypeGbn2)) {
						isMatch = true;
						invalidCnt = invalidCnt -1;
						map.put(DSGN_DB_CONST.SHEET_TAB_COL.TAB_TYPE_GBN.name()+VALID_SUFFIX, "Y");
						map.put(DSGN_DB_CONST.SHEET_TAB_COL.TAB_TYPE_GBN_VAL.name(), CastUtils.toString(map2.get("CD_ID")));
						map.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), invalidCnt);
						if (invalidCnt == 0) {
							map.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "Y");
							errCnt.add(-1);  
						}
						tabChkNm += tabTypeGbn;
						break;
					}
				}
				if (!isMatch) {
					isTabNmValid = false;	
				}
			} else {
				isTabNmValid = false;
			}

			//테이블정보구분(Fact, Dimesion, History, Summary, Code)
			String tabInfoGbn = CastUtils.toString(map.get(DSGN_DB_CONST.SHEET_TAB_COL.TAB_INFO_GBN.name()));
			if (!"".equals(tabInfoGbn)) {
				isMatch = false;
				for(Map<String, Object> map2 : clsTypList) {
					String tabInfoGbn2 = CastUtils.toString(map2.get("CD_ID"));
					if (tabInfoGbn.equals(tabInfoGbn2)) {
						isMatch = true;
						invalidCnt = invalidCnt -1;
						map.put(DSGN_DB_CONST.SHEET_TAB_COL.TAB_INFO_GBN.name()+VALID_SUFFIX, "Y");
						map.put(DSGN_DB_CONST.SHEET_TAB_COL.TAB_INFO_GBN_VAL.name(), CastUtils.toString(map2.get("CD_ID")));
						map.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), invalidCnt);
						if (invalidCnt == 0) {
							map.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "Y");
							errCnt.add(-1);     
						}
						tabChkNm += tabInfoGbn+"_";
						break;
					}
				}
				if (!isMatch) {
					isTabNmValid = false;	
				}
			} else {
				isTabNmValid = false;
			}

			//업무구분(일반신용, 보험신용, 기술신용, 융합분석)
			String pathSubjId = "";
			String subj1 = CastUtils.toString(map.get(DSGN_DB_CONST.SHEET_TAB_COL.CLS_GBN.name()));
			if (!"".equals(subj1)) {
				isMatch = false;
				for(Map<String, Object> map2 : subjList) {
					String subj1_2 = CastUtils.toString(map2.get("ADM_OBJ_ID"));
					String pathSubjId_2 = CastUtils.toString(map2.get("PATH_OBJ_ID"));
					if (subj1.equals(subj1_2) && pathSubjId.equals(pathSubjId_2)) {
						isMatch = true;
						invalidCnt = invalidCnt -1;
						map.put(DSGN_DB_CONST.SHEET_TAB_COL.CLS_GBN.name()+VALID_SUFFIX, "Y");
						map.put(DSGN_DB_CONST.SHEET_TAB_COL.CLS_GBN_VAL.name(), CastUtils.toString(map2.get("ADM_OBJ_ID")));
						map.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), invalidCnt);
						if (invalidCnt == 0) {
							map.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "Y");
							errCnt.add(-1);
						}
						pathSubjId = CastUtils.toString(map2.get("OBJ_ID"));
						tabChkNm += CastUtils.toString(map2.get("ADM_OBJ_ID"));
						tabChkEntityNm += CastUtils.toString(map2.get("OBJ_NM"))+"_";
						break;
					}
				}
				if (!isMatch) {
					isTabNmValid = false;	
				}
			} else {
				isTabNmValid = false;
			}

			//업무별세부영역(
			String subj2 = CastUtils.toString(map.get(DSGN_DB_CONST.SHEET_TAB_COL.CLS_SUB_AREA.name()));
			if (!"".equals(subj2)) {
				isMatch = false;
				for(Map<String, Object> map2 : subjList) {
					String subj2_2 = CastUtils.toString(map2.get("ADM_OBJ_ID"));
					String pathSubjId_2 = CastUtils.toString(map2.get("PATH_OBJ_ID"));
					if (subj2.equals(subj2_2) && pathSubjId.equals(pathSubjId_2)) {
						isMatch = true;
						invalidCnt = invalidCnt -1;
						map.put(DSGN_DB_CONST.SHEET_TAB_COL.CLS_SUB_AREA.name()+VALID_SUFFIX, "Y");
						map.put(DSGN_DB_CONST.SHEET_TAB_COL.CLS_SUB_AREA_VAL.name(), CastUtils.toString(map2.get("OBJ_ID")));
						map.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), invalidCnt);
						if (invalidCnt == 0) {
							map.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "Y");
							errCnt.add(-1);   
						}
						pathSubjId = CastUtils.toString(map2.get("OBJ_ID"));
						tabChkNm += CastUtils.toString(map2.get("ADM_OBJ_ID"))+"_";
						tabChkEntityNm += CastUtils.toString(map2.get("OBJ_NM"))+"_";
						break;
					}
				}
				if (!isMatch) {
					isTabNmValid = false;	
				}
			} else {
				isTabNmValid = false;
			}

			//테이블 물리명, 논리명
			ArrayList<String> bizEntityNmList = new ArrayList<>();
			bizEntityNmList.add("");
			
			if (isTabNmValid) {
				String exp = ".*[_][B|T|$][0-9]*$";
				String bizNmStr = "";
				String tabNm = CastUtils.toString(map.get(DSGN_DB_CONST.SHEET_TAB_COL.TAB_NM.name()));
				boolean chk = Pattern.matches(exp, tabNm);
				if (chk) {
					bizNmStr = tabNm.substring(tabChkNm.length(), tabNm.lastIndexOf("_"));
					
					String tabSuffixNm = tabNm.substring(tabNm.lastIndexOf("_")+1);
					for(Map<String, Object > etcTypeMap : etcTypList) {
						String suffix = CastUtils.toString(etcTypeMap.get("CD_ID"));
						if (tabSuffixNm.startsWith(suffix)) {
							tabChkEntitySuffixNm = CastUtils.toString("_"+etcTypeMap.get("CD_NM"));
							break;
						}
					}
				} else {
					bizNmStr = tabNm.substring(tabChkNm.length());
				}
				
				String[] bizNms = bizNmStr.split("_");
				for(String bizNm : bizNms) {
					List<String> bizEntityNms = wordList.get(bizNm);
					if (bizEntityNms == null) {
						isTabNmValid = false;
						break;
					}
					
					List<String> cloneList = (List<String>)bizEntityNmList.clone();
					for(int i=1; i<bizEntityNms.size(); i++) {
						bizEntityNmList.addAll(cloneList);
					}
					
					int idx = 0;
					for(String bizEntityNm : bizEntityNms) {
						for(int x=0; x<cloneList.size(); x++) {
							bizEntityNmList.set(idx, bizEntityNmList.get(idx)+bizEntityNm);
							idx++;
						}
					}
				}
				
				if (isTabNmValid) {
					invalidCnt = invalidCnt -1;
					map.put(DSGN_DB_CONST.SHEET_TAB_COL.TAB_NM.name()+VALID_SUFFIX, "Y");
					map.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), invalidCnt);
					if (invalidCnt == 0) {
						map.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "Y");
						errCnt.add(-1); 
					}
					//tab_obj_id 셋팅
					for(Map<String, Object> map2 : tabList) {
						String tempTabNm = CastUtils.toString(map2.get("OBJ_ABBR_NM"));
						if (tabNm.equals(tempTabNm)) {
							map.put("TAB_OBJ_ID", CastUtils.toString(map2.get("OBJ_ID")));
							break;
						}
					}
					
					String tabEntityNm = CastUtils.toString(map.get(DSGN_DB_CONST.SHEET_TAB_COL.TAB_ENTITY_NM.name()));
					for(String entityNm : bizEntityNmList) {
						if (tabEntityNm.equals(tabChkEntityNm+entityNm+tabChkEntitySuffixNm)) {
							invalidCnt = invalidCnt -1;
							map.put(DSGN_DB_CONST.SHEET_TAB_COL.TAB_ENTITY_NM.name()+VALID_SUFFIX, "Y");
							map.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), invalidCnt);
							if (invalidCnt == 0) {
								map.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "Y");
								errCnt.add(-1); 
							}
							break;
						}
					}
				}
			}
			
			//비식별여부
			String didYn = CastUtils.toString(map.get(DSGN_DB_CONST.SHEET_TAB_COL.DID_YN.name()));
			if ("Y".equals(didYn) || "N".equals(didYn) || "".equals(didYn)) {
				invalidCnt = invalidCnt -1;
				map.put(DSGN_DB_CONST.SHEET_TAB_COL.DID_YN.name()+VALID_SUFFIX, "Y");
				map.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), invalidCnt);
				if (invalidCnt == 0) {
					map.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "Y");
					errCnt.add(-1); 
				}
			}
		}
	}

	private void dsgnColListCheck(List<Map<String, Object>> dsgnColList, String databaseId, ErrCnt errCnt) {
		DaoParam daoParam = new DaoParam();
		//term list
		List<Map<String, Object >> termList = commonDao.selectList("dsgn.db.findTermList", daoParam);

		//tableList
		daoParam.clear();
		daoParam.put("objTypeId", DSGN_DB_CONST.OBJ_TYPE.TAB.value());
		daoParam.put("pathObjTypeId", DSGN_DB_CONST.OBJ_TYPE.DB.value());
		daoParam.put("pathObjId", databaseId);
		List<Map<String, Object >> tabList = commonDao.selectList("dsgn.db.findDsgnTab", daoParam);

		int colIdx = 0;
		String preTabName = "";
		for(Map<String, Object> map : dsgnColList) {
			int invalidCnt = CastUtils.toInteger(map.get(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name()));

			//테이블명
			String tabNm = CastUtils.toString(map.get(DSGN_DB_CONST.SHEET_COL_COL.TAB_NM.name()));
			if (preTabName.equals(tabNm)) {
				colIdx++;
			} else {
				preTabName = tabNm;
				colIdx = 1;
			}
			map.put(DSGN_DB_CONST.SHEET_COL_COL.COL_ORD.name(), colIdx);

			String tabEntityNm = CastUtils.toString(map.get(DSGN_DB_CONST.SHEET_COL_COL.TAB_ENTITY_NM.name()));
			String dbType = "";
			if (!"".equals(tabNm)) {
				for(Map<String, Object> map2 : tabList) {
					//테이블물리명 체크
					String tempTabNm = CastUtils.toString(map2.get("OBJ_ABBR_NM"));
					if (tabNm.equals(tempTabNm)) {
						invalidCnt = invalidCnt -1;
						map.put(DSGN_DB_CONST.SHEET_COL_COL.TAB_NM.name()+VALID_SUFFIX, "Y");
						map.put(DSGN_DB_CONST.SHEET_COL_COL.TAB_OBJ_ID.name(), CastUtils.toString(map2.get("OBJ_ID")));
						map.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), invalidCnt);
						if (invalidCnt == 0) {
							map.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "Y");
							errCnt.add(-1);
						}

						//테이블 논리명 체크
						String tempTabEntityNm = CastUtils.toString(map2.get("OBJ_NM"));
						if (tabEntityNm.equals(tempTabEntityNm)) {
							invalidCnt = invalidCnt -1;
							map.put(DSGN_DB_CONST.SHEET_COL_COL.TAB_ENTITY_NM.name()+VALID_SUFFIX, "Y");
							map.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), invalidCnt);
							if (invalidCnt == 0) {
								map.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "Y");
								errCnt.add(-1);
							}
						}

						dbType = CastUtils.toString(map2.get("DB_TYPE"));
						break;
					}
				}
			}

			//컬럼정보
			String colNm = CastUtils.toString(map.get(DSGN_DB_CONST.SHEET_COL_COL.COL_NM.name()));
			String colAtrNm = CastUtils.toString(map.get(DSGN_DB_CONST.SHEET_COL_COL.COL_ATR_NM.name()));
			String dataType = CastUtils.toString(map.get(DSGN_DB_CONST.SHEET_COL_COL.DATA_TYPE.name()));
			String dataLen = CastUtils.toString(map.get(DSGN_DB_CONST.SHEET_COL_COL.DATA_LEN.name()));
			String dataScale = CastUtils.toString(map.get(DSGN_DB_CONST.SHEET_COL_COL.DATA_SCALE.name()));
			if (!"".equals(colNm)) {
				for(Map<String, Object> map2 : termList) {
					//컬럼명 체크
					String tempColNm = CastUtils.toString(map2.get("COL_NM"));
					if (colNm.equals(tempColNm)) {
						invalidCnt = invalidCnt -1;
						map.put(DSGN_DB_CONST.SHEET_COL_COL.COL_NM.name()+VALID_SUFFIX, "Y");
						map.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), invalidCnt);
						if (invalidCnt == 0) {
							map.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "Y");
							errCnt.add(-1);
						}

						//컬럼속성 체크
						String tempColAtrNm = CastUtils.toString(map2.get("COL_ATR_NM"));
						if (colAtrNm.equals(tempColAtrNm)) {
							invalidCnt = invalidCnt -1;
							map.put(DSGN_DB_CONST.SHEET_COL_COL.COL_ATR_NM.name()+VALID_SUFFIX, "Y");
							map.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), invalidCnt);
							if (invalidCnt == 0) {
								map.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "Y");
								errCnt.add(-1);
							}
						}

						//컬럼 데이터타입 체크
						String tempDataType = CastUtils.toString(map2.get(dbType+"_DATA_TYPE"));
						if (dataType.equals(tempDataType)) {
							invalidCnt = invalidCnt -1;
							map.put(DSGN_DB_CONST.SHEET_COL_COL.DATA_TYPE.name()+VALID_SUFFIX, "Y");
							map.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), invalidCnt);
							if (invalidCnt == 0) {
								map.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "Y");
								errCnt.add(-1);
							}
						}

						//컬럼 길이 체크
						String tempDataLen = CastUtils.toString(map2.get("DATA_LEN"));
						if (dataLen.equals(tempDataLen)) {
							invalidCnt = invalidCnt -1;
							map.put(DSGN_DB_CONST.SHEET_COL_COL.DATA_LEN.name()+VALID_SUFFIX, "Y");
							map.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), invalidCnt);
							if (invalidCnt == 0) {
								map.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "Y");
								errCnt.add(-1);
							}
						}

						//컬럼 소수점 체크
						String tempDataScale = CastUtils.toString(map2.get("DATA_SCALE"));
						if (dataScale.equals(tempDataScale)) {
							invalidCnt = invalidCnt -1;
							map.put(DSGN_DB_CONST.SHEET_COL_COL.DATA_SCALE.name()+VALID_SUFFIX, "Y");
							map.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), invalidCnt);
							if (invalidCnt == 0) {
								map.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "Y");
								errCnt.add(-1);
							}
						}

						break;
					}
				}
			}

			//NOT NULL 여부
			String notNullYn = CastUtils.toString(map.get(DSGN_DB_CONST.SHEET_COL_COL.NOT_NULL_YN.name()));
			if ("Y".equals(notNullYn) || "N".equals(notNullYn) || "".equals(notNullYn)) {
				invalidCnt = invalidCnt -1;
				map.put(DSGN_DB_CONST.SHEET_COL_COL.NOT_NULL_YN.name()+VALID_SUFFIX, "Y");
				map.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), invalidCnt);
				if (invalidCnt == 0) {
					map.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "Y");
					errCnt.add(-1);
				}
			}

			//PK 순번
			String pkOrd = CastUtils.toString(map.get(DSGN_DB_CONST.SHEET_COL_COL.PK_ORD.name()));
			if ("".equals(pkOrd) || NumberUtils.isCreatable(pkOrd)) {
				invalidCnt = invalidCnt -1;
				map.put(DSGN_DB_CONST.SHEET_COL_COL.PK_ORD.name()+VALID_SUFFIX, "Y");
				map.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), invalidCnt);
				if (invalidCnt == 0) {
					map.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "Y");
					errCnt.add(-1);
				}
			}

			//FK 여부
			String fkYn = CastUtils.toString(map.get(DSGN_DB_CONST.SHEET_COL_COL.FK_YN.name()));
			if ("Y".equals(fkYn) || "N".equals(fkYn) || "".equals(fkYn)) {
				invalidCnt = invalidCnt -1;
				map.put(DSGN_DB_CONST.SHEET_COL_COL.FK_YN.name()+VALID_SUFFIX, "Y");
				map.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), invalidCnt);
				if (invalidCnt == 0) {
					map.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "Y");
					errCnt.add(-1);
				}
			}

			//분산키 순번
			String distribKeyOrd = CastUtils.toString(map.get(DSGN_DB_CONST.SHEET_COL_COL.DISTRIB_KEY_ORD.name()));
			if ("".equals(distribKeyOrd) || NumberUtils.isCreatable(distribKeyOrd)) {
				invalidCnt = invalidCnt -1;
				map.put(DSGN_DB_CONST.SHEET_COL_COL.DISTRIB_KEY_ORD.name()+VALID_SUFFIX, "Y");
				map.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), invalidCnt);
				if (invalidCnt == 0) {
					map.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "Y");
					errCnt.add(-1);
				}
			}

			//비식별여부
			String didYn = CastUtils.toString(map.get(DSGN_DB_CONST.SHEET_COL_COL.DID_YN.name()));
			if ("Y".equals(didYn) || "N".equals(didYn) || "".equals(didYn)) {
				invalidCnt = invalidCnt -1;
				map.put(DSGN_DB_CONST.SHEET_COL_COL.DID_YN.name()+VALID_SUFFIX, "Y");
				map.put(DSGN_DB_CONST.VALID_CHK.INVALID_CNT.name(), invalidCnt);
				if (invalidCnt == 0) {
					map.put(DSGN_DB_CONST.VALID_CHK.IS_VALID.name(), "Y");
					errCnt.add(-1); 
				}
			}
		}
	}

	@Override
	@Transactional
	public Map<String, Object> importDsgnTab(Map<String, Object> reqParam, SessionInfo sessionInfo) {
		List<Map<String, Object>> dsgnTabList = (List<Map<String, Object>>)reqParam.get("DSGN_TAB_DATA");

		int dsgnTabImportCnt = 0;
		for(Map<String, Object> dsgnTabMap : dsgnTabList) {
			String tabObjId = CastUtils.toString(dsgnTabMap.get(DSGN_DB_CONST.SHEET_TAB_COL.TAB_OBJ_ID.name()));
			
			PenObjM penObjM = new PenObjM(null, sessionInfo);
			penObjM.setObjTypeId(DSGN_DB_CONST.OBJ_TYPE.TAB.value());
			if ("".equals(tabObjId)) {
				penObjM.genObjId();
			} else {
				DaoParam removeDaoParam = new DaoParam();
				removeDaoParam.put("objTypeId", DSGN_DB_CONST.OBJ_TYPE.TAB.value());
				removeDaoParam.put("objId", tabObjId);
				metaDao.removePenObjM(removeDaoParam);
				metaDao.removePenObjD(removeDaoParam);
				penObjM.setObjId(tabObjId);
			}
			String admObjId = "";
			if (!"".equals(CastUtils.toString(dsgnTabMap.get(DSGN_DB_CONST.SHEET_TAB_COL.DB_NM.name())))) {
				admObjId += CastUtils.toString(dsgnTabMap.get(DSGN_DB_CONST.SHEET_TAB_COL.DB_NM.name()))+".";
			}
			if (!"".equals(CastUtils.toString(dsgnTabMap.get(DSGN_DB_CONST.SHEET_TAB_COL.SCHEMA_NM.name())))) {
				admObjId += CastUtils.toString(dsgnTabMap.get(DSGN_DB_CONST.SHEET_TAB_COL.SCHEMA_NM.name()))+".";
			}
			admObjId += CastUtils.toString(dsgnTabMap.get(DSGN_DB_CONST.SHEET_TAB_COL.TAB_NM.name()));

			penObjM.setAdmObjId(admObjId);
			penObjM.setObjNm(CastUtils.toString(dsgnTabMap.get(DSGN_DB_CONST.SHEET_TAB_COL.TAB_ENTITY_NM.name())));
			penObjM.setObjAbbrNm(CastUtils.toString(dsgnTabMap.get(DSGN_DB_CONST.SHEET_TAB_COL.TAB_NM.name())));
			penObjM.setObjDesc(CastUtils.toString(dsgnTabMap.get(DSGN_DB_CONST.SHEET_TAB_COL.DESC.name())));
			penObjM.setPathObjTypeId(DSGN_DB_CONST.OBJ_TYPE.DB.value());
			penObjM.setPathObjId(CastUtils.toString(dsgnTabMap.get(DSGN_DB_CONST.SHEET_TAB_COL.DB_ID_VAL.name())));    		
			metaDao.insertPenObjM(penObjM);

			//업무별세부영역(102)
			PenObjD penObjD = new PenObjD(penObjM);
			penObjD.setAtrValSeq(101);
			penObjD.setAtrIdSeq(102);
			penObjD.setObjAtrVal(CastUtils.toString(dsgnTabMap.get(DSGN_DB_CONST.SHEET_TAB_COL.CLS_SUB_AREA_VAL.name())));
			metaDao.insertPenObjD(penObjD);

			//테이블유형구분: 테이블, 뷰(103)
			penObjD.setAtrIdSeq(103);
			penObjD.setObjAtrVal(CastUtils.toString(dsgnTabMap.get(DSGN_DB_CONST.SHEET_TAB_COL.TAB_TYPE_GBN.name())));
			metaDao.insertPenObjD(penObjD);

			//테이블정보구분: Fact, Dimension, History, Summary, Code, (104)
			penObjD.setAtrIdSeq(104);
			penObjD.setObjAtrVal(CastUtils.toString(dsgnTabMap.get(DSGN_DB_CONST.SHEET_TAB_COL.TAB_INFO_GBN_VAL.name())));
			metaDao.insertPenObjD(penObjD);

			//설계 담당자(105)
			penObjD.setAtrIdSeq(105);
			penObjD.setObjAtrVal(CastUtils.toString(dsgnTabMap.get(DSGN_DB_CONST.SHEET_TAB_COL.MNGR.name())));
			metaDao.insertPenObjD(penObjD);

			//데이터베이스(191)
			penObjD.setAtrIdSeq(191);
			penObjD.setObjAtrVal(CastUtils.toString(dsgnTabMap.get(DSGN_DB_CONST.SHEET_TAB_COL.DB_NM.name())));
			metaDao.insertPenObjD(penObjD);

			//스키마(192)
			penObjD.setAtrIdSeq(192);
			penObjD.setObjAtrVal(CastUtils.toString(dsgnTabMap.get(DSGN_DB_CONST.SHEET_TAB_COL.SCHEMA_NM.name())));
			metaDao.insertPenObjD(penObjD);

			//비식별여부(121)
			penObjD.setAtrIdSeq(121);
			penObjD.setObjAtrVal(CastUtils.toString(dsgnTabMap.get(DSGN_DB_CONST.SHEET_TAB_COL.DID_YN.name())));
			metaDao.insertPenObjD(penObjD);

			//보관기간(122)
			penObjD.setAtrIdSeq(122);
			penObjD.setObjAtrVal(CastUtils.toString(dsgnTabMap.get(DSGN_DB_CONST.SHEET_TAB_COL.DATA_KEEP_PERIOD.name())));
			metaDao.insertPenObjD(penObjD);

			//데이터발생주기(123)
			penObjD.setAtrIdSeq(123);
			penObjD.setObjAtrVal(CastUtils.toString(dsgnTabMap.get(DSGN_DB_CONST.SHEET_TAB_COL.DATA_GEN_PERIOD.name())));
			metaDao.insertPenObjD(penObjD);

			//데이터발생건수(124)
			penObjD.setAtrIdSeq(124);
			penObjD.setObjAtrVal(CastUtils.toString(dsgnTabMap.get(DSGN_DB_CONST.SHEET_TAB_COL.DATA_GEN_CNT.name())));
			metaDao.insertPenObjD(penObjD);

			//DBMS 유형(125)
			penObjD.setAtrIdSeq(125);
			penObjD.setObjAtrVal(CastUtils.toString(dsgnTabMap.get(DSGN_DB_CONST.SHEET_TAB_COL.DBMS.name())));
			metaDao.insertPenObjD(penObjD);

			dsgnTabMap.put("DSGN_TAB_ID", penObjM.getObjId());
			dsgnTabImportCnt++;
		}

		Map<String, Object> result = new HashMap<>();
		result.put("DSGN_TAB_IMPORT_CNT", dsgnTabImportCnt);

		return result;
	}

	@Override
	@Transactional
	public Map<String, Object> importDsgnCol(Map<String, Object> reqParam, SessionInfo sessionInfo) {
		List<Map<String, Object>> dsgnColList = (List<Map<String, Object>>)reqParam.get("DSGN_COL_DATA");

		//설계컬럼 삭제
		String preTabObjId = "";
		for(Map<String, Object> dsgnColMap : dsgnColList) {
			String tabObjId = CastUtils.toString(dsgnColMap.get(DSGN_DB_CONST.SHEET_COL_COL.TAB_OBJ_ID.name()));
			if (!preTabObjId.equals(tabObjId)) {
				DaoParam removeDaoParam = new DaoParam();
				removeDaoParam.put("objTypeId", DSGN_DB_CONST.OBJ_TYPE.COL.value());
				removeDaoParam.put("pathObjTypeId", DSGN_DB_CONST.OBJ_TYPE.TAB.value());
				removeDaoParam.put("pathObjId", tabObjId);
				metaDao.removePenObjM(removeDaoParam);
				metaDao.removePenObjD(removeDaoParam);
				
				preTabObjId = tabObjId;
			}
		}
		
		//설계컬럼 등록
		int dsgnColImportCnt = 0;
		for(Map<String, Object> dsgnColMap : dsgnColList) {
			PenObjM penObjM = new PenObjM(null, sessionInfo);
			penObjM.setObjTypeId(DSGN_DB_CONST.OBJ_TYPE.COL.value());
			penObjM.genObjId();
			penObjM.setObjNm(CastUtils.toString(dsgnColMap.get(DSGN_DB_CONST.SHEET_COL_COL.COL_ATR_NM.name())));
			penObjM.setObjAbbrNm(CastUtils.toString(dsgnColMap.get(DSGN_DB_CONST.SHEET_COL_COL.COL_NM.name())));
			penObjM.setObjDesc(CastUtils.toString(dsgnColMap.get(DSGN_DB_CONST.SHEET_COL_COL.DESC.name())));
			penObjM.setPathObjTypeId(DSGN_DB_CONST.OBJ_TYPE.TAB.value());
			penObjM.setPathObjId(CastUtils.toString(dsgnColMap.get(DSGN_DB_CONST.SHEET_COL_COL.TAB_OBJ_ID.name())));    		
			metaDao.insertPenObjM(penObjM);

			//컬럼순번(101)
			PenObjD penObjD = new PenObjD(penObjM);
			penObjD.setAtrValSeq(101);
			penObjD.setAtrIdSeq(101);
			penObjD.setObjAtrVal(CastUtils.toString(dsgnColMap.get(DSGN_DB_CONST.SHEET_COL_COL.COL_ORD.name())));
			metaDao.insertPenObjD(penObjD);

			//데이터타입(102)
			penObjD.setAtrIdSeq(102);
			penObjD.setObjAtrVal(CastUtils.toString(dsgnColMap.get(DSGN_DB_CONST.SHEET_COL_COL.DATA_TYPE.name())));
			metaDao.insertPenObjD(penObjD);

			//길이(103)
			penObjD.setAtrIdSeq(103);
			penObjD.setObjAtrVal(CastUtils.toString(dsgnColMap.get(DSGN_DB_CONST.SHEET_COL_COL.DATA_LEN.name())));
			metaDao.insertPenObjD(penObjD);

			//소수점(104)
			penObjD.setAtrIdSeq(104);
			penObjD.setObjAtrVal(CastUtils.toString(dsgnColMap.get(DSGN_DB_CONST.SHEET_COL_COL.DATA_SCALE.name())));
			metaDao.insertPenObjD(penObjD);

			//NOT NULL 여부(105)
			penObjD.setAtrIdSeq(105);
			penObjD.setObjAtrVal(CastUtils.toString(dsgnColMap.get(DSGN_DB_CONST.SHEET_COL_COL.NOT_NULL_YN.name())));
			metaDao.insertPenObjD(penObjD);

			//PK순번(107)
			penObjD.setAtrIdSeq(107);
			penObjD.setObjAtrVal(CastUtils.toString(dsgnColMap.get(DSGN_DB_CONST.SHEET_COL_COL.PK_ORD.name())));
			metaDao.insertPenObjD(penObjD);

			//FK여부(108)
			penObjD.setAtrIdSeq(108);
			penObjD.setObjAtrVal(CastUtils.toString(dsgnColMap.get(DSGN_DB_CONST.SHEET_COL_COL.FK_YN.name())));
			metaDao.insertPenObjD(penObjD);

			//분산키순번(111)
			penObjD.setAtrIdSeq(111);
			penObjD.setObjAtrVal(CastUtils.toString(dsgnColMap.get(DSGN_DB_CONST.SHEET_COL_COL.DISTRIB_KEY_ORD.name())));
			metaDao.insertPenObjD(penObjD);

			//비식별화 대상 여부(112)
			penObjD.setAtrIdSeq(112);
			penObjD.setObjAtrVal(CastUtils.toString(dsgnColMap.get(DSGN_DB_CONST.SHEET_COL_COL.DID_YN.name())));
			metaDao.insertPenObjD(penObjD);

			dsgnColMap.put("DSGN_COL_ID", penObjM.getObjId());
			dsgnColImportCnt++;
		}

		Map<String, Object> result = new HashMap<>();
		result.put("DSGN_COL_IMPORT_CNT", dsgnColImportCnt);

		return result;
	}

	private static String getCellValue(final Cell cell) {
		String str = "";
		try {
			if (cell != null) {
				switch (cell.getCellType()) {

				case XSSFCell.CELL_TYPE_NUMERIC:
					str = Integer.toString((int) cell.getNumericCellValue());
					break;
				case Cell.CELL_TYPE_STRING:
					str = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					str = Boolean.toString(cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_FORMULA:
					try {
						str = cell.getStringCellValue();
					} catch (Exception e) {
						str = Integer.toString((int) cell.getNumericCellValue());
					}

					break;
				default:
					str = cell.getStringCellValue();
				}
			}
		} catch (Exception e) {
			return "";
		}
		return str;
	}

	private class ErrCnt {
		int value;

		ErrCnt(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value; 
		}

		public void add(int value) {
			this.value = this.value + value; 
		}
	}

	@Override
	public String genDDL(List<Map<String, Object>> datas) {
		StringBuilder script = new StringBuilder();

		for(Map<String, Object> tabMap : datas) {
			StringBuilder tabScript = new StringBuilder();
			StringBuilder commentScript = new StringBuilder();

			String tabTypeId = CastUtils.toString(tabMap.get("OBJ_TYPE_ID"));
			String tabId = CastUtils.toString(tabMap.get("OBJ_ID"));
			String tabEntityNm = CastUtils.toString(tabMap.get("OBJ_NM"));
			String tabNm = CastUtils.toString(tabMap.get("OBJ_ABBR_NM"));
			String dbType = CastUtils.toString(tabMap.get("DB_TYPE"));

			commentScript.append("COMMENT ON TABLE ").append(tabNm).append(" IS '").append(tabEntityNm).append("';").append("\n");

			DaoParam daoParam = new DaoParam();
			daoParam.put("objTypeId", DSGN_DB_CONST.OBJ_TYPE.COL.value());
			daoParam.put("pathObjTypeId", DSGN_DB_CONST.OBJ_TYPE.TAB.value());
			daoParam.put("pathObjId", tabId);			
			List<Map<String, Object >> colList = commonDao.selectList("dsgn.db.findDsgnCol", daoParam);

			StringBuilder colScript = new StringBuilder();
			Map<Integer, String> pkList = new HashMap();
			Map<Integer, String> distribKeyList = new HashMap();

			for(Map<String, Object> colMap : colList) {
				String colNm = CastUtils.toString(colMap.get("OBJ_ABBR_NM"));
				String colAtrNm = CastUtils.toString(colMap.get("OBJ_NM"));
				String dataType = CastUtils.toString(colMap.get("DATA_TYPE"));
				String dataLen = CastUtils.toString(colMap.get("DATA_LEN"));
				String dataScale = CastUtils.toString(colMap.get("DATA_SCALE"));
				String notNullYn = CastUtils.toString(colMap.get("NOT_NULL_YN"));
				String pkOrd = CastUtils.toString(colMap.get("PK_ORD"));
				String distribKeyOrd = CastUtils.toString(colMap.get("DISTRIB_KEY_ORD"));

				colScript.append("\t").append(StringUtils.rightPad(colNm, 50));
				if ("".equals(dataLen) && "".equals(dataScale)) {
					colScript.append(dataType);
				} else if (!"".equals(dataLen) && "".equals(dataScale)) {
					colScript.append(dataType+"("+dataLen+")");
				} else if (!"".equals(dataLen) && !"".equals(dataScale)) {
					colScript.append(dataType+"("+dataLen+", "+dataScale+")");
				}
				if ("Y".equals(notNullYn)) {
					colScript.append(" NOT NULL");
				}
				colScript.append(",").append("\n");

				commentScript.append("COMMENT ON COLUMN ").append(tabNm).append(".").append(colNm).append(" IS '").append(colAtrNm).append("';").append("\n");
				
				if (!"".equals(pkOrd)) {
					pkList.put(CastUtils.toInteger(pkOrd), colNm);
				}
				if (!"".equals(distribKeyOrd)) {
					distribKeyList.put(CastUtils.toInteger(distribKeyOrd), colNm);
				}
			}
			if (colScript.length() == 0) {
				script.append(StringUtils.rightPad("", 80, "-")).append("\n");
				script.append(StringUtils.rightPad("---- " + tabNm + " COLUMN 정보가 없습니다. ", 78, "-")).append("\n");
				script.append(StringUtils.rightPad("", 80, "-")).append("\n");
				continue;
			} else {
				colScript.setLength(colScript.length()-2);
			}
			
			pkList = sortByValues(pkList);
			distribKeyList = sortByValues(distribKeyList);

			tabScript.append("CREATE TABLE ").append(tabNm).append("\n");
			tabScript.append("(").append("\n");
			tabScript.append(colScript.toString());
			
			if (DSGN_DB_CONST.DB_TYPE.PDA.name().equals(dbType) && distribKeyList.size() != 0) {
				tabScript.append("\n").append(")").append("\n");
				tabScript.append("DISTRIBUTE ON (");
				
				Set<Entry<Integer, String>> set = distribKeyList.entrySet();
				boolean isFirst = true;
				for(Entry<Integer, String> entry : set) {
					if (isFirst) {
						tabScript.append(entry.getValue());
						isFirst = false;
					} else {
						tabScript.append(", ").append(entry.getValue());
					}
				}
				tabScript.append(");").append("\n");
			} else {
				tabScript.append("\n").append(");").append("\n");
			}
			
			script.append(StringUtils.rightPad("", 80, "-")).append("\n");
			script.append(StringUtils.rightPad("---- "+tabNm + " ", 80, "-")).append("\n");
			script.append(StringUtils.rightPad("", 80, "-")).append("\n");
			script.append(tabScript.toString());
			script.append("\n");
			script.append(commentScript.toString());
			script.append("\n");
			script.append("\n");
		}
		
		return script.toString();
	}

	public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
		Comparator<K> valueComparator = 
				new Comparator<K>() {
			public int compare(K k1, K k2) {
				int compare = 
						map.get(k1).compareTo(map.get(k2));
				if (compare == 0) 
					return 1;
				else 
					return compare;
			}
		};

		Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
		sortedByValues.putAll(map);
		return sortedByValues;
	}
}
