package kr.co.penta.dataeye.spring.web.portal.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.penta.dataeye.common.entities.JqGridEntity;
import kr.co.penta.dataeye.common.entities.PortalMenuEntity;
import kr.co.penta.dataeye.common.entities.meta.PenSysObjL;
import kr.co.penta.dataeye.common.exception.ServiceRuntimeException;
import kr.co.penta.dataeye.common.util.CastUtils;
import kr.co.penta.dataeye.common.util.DataEyeSettingsHolder;
import kr.co.penta.dataeye.common.util.JSONUtils;
import kr.co.penta.dataeye.spring.config.properties.DataEyeSettings;
import kr.co.penta.dataeye.spring.config.properties.PortalSettings;
import kr.co.penta.dataeye.spring.mybatis.dao.param.JqGridDaoParam;
import kr.co.penta.dataeye.spring.security.core.LoggingService;
import kr.co.penta.dataeye.spring.web.MessageHolder;
import kr.co.penta.dataeye.spring.web.portal.service.PortalService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;

@Controller
@RequestMapping(value="/dataset", method={RequestMethod.POST, RequestMethod.GET})
public class DatasetController {
	
	
	@Autowired private PortalService portalService;
	
    @RequestMapping("/datasetDetail")
    public String datasetDetail(final Model model, HttpServletRequest request) throws IOException {
    	
    	String datasetId = request.getParameter("datasetId");
    	Map<String,String> datasetDetail = portalService.getDatasetDetail(datasetId);    	
    	model.addAttribute("datasetDetail",datasetDetail);
    	
    	
    	//첨부파일 목록
    	Map<String,String> datasetSampleFile = portalService.datasetSampleFile(datasetId);
    	List<Map<String,String>> datasetSampleFileColumnList = new ArrayList<Map<String,String>>();
    	try {
    	if(datasetSampleFile != null) {
	    	//파일을 읽기위해 엑셀파일을 가져온다
    		//로컬에서 싱행시 개발서버에서 업로드된 파일은 로컬에는 없으므로 에러가 남
	    	FileInputStream fis=new FileInputStream(datasetSampleFile.get("FILE_NAME"));
	    	
	    	if(datasetSampleFile.get("EXT").equals("xlsx")) {
	    		XSSFWorkbook workbook=new XSSFWorkbook(fis);
		    	int rowindex=0;
		    	int columnindex=0;
		
		    	XSSFSheet sheet=workbook.getSheetAt(0);
		    	int rows=sheet.getPhysicalNumberOfRows();
		    	
		    	HashMap<String, String> hm = new HashMap<String, String>();
		    	for(rowindex=0;rowindex<1;rowindex++){
		    		XSSFRow row=sheet.getRow(rowindex);
		    		
		    	    if(row !=null){
		    	        int cells=row.getPhysicalNumberOfCells();
		    	        for(columnindex=0;columnindex<=cells;columnindex++){
		    	        	XSSFCell cell=row.getCell(columnindex);
		    	            String value="";
		    	            if(cell==null){
		    	                continue;
		    	            }else{
		    	                switch (cell.getCellType()){
		    	                case XSSFCell.CELL_TYPE_FORMULA:
		    	                    value=cell.getCellFormula();
		    	                    break;
		    	                case XSSFCell.CELL_TYPE_NUMERIC:
		    	                    value=cell.getNumericCellValue()+"";
		    	                    break;
		    	                case XSSFCell.CELL_TYPE_STRING:
		    	                    value=cell.getStringCellValue()+"";
		    	                    break;
		    	                case XSSFCell.CELL_TYPE_BLANK:
		    	                    value=cell.getBooleanCellValue()+"";
		    	                    break;
		    	                case XSSFCell.CELL_TYPE_ERROR:
		    	                    value=cell.getErrorCellValue()+"";
		    	                    break;
		    	                }
		    	            }
		    	            hm.put("COL"+columnindex, value);
		    	            datasetSampleFileColumnList.add(hm);
		    	        }
		    	    }
		    	}
		    	if(workbook!=null)workbook.close();
	    	}else if(datasetSampleFile.get("EXT").equals("xls")) {
	    		HSSFWorkbook workbook=new HSSFWorkbook(fis);
		    	int rowindex=0;
		    	int columnindex=0;
		
		    	HSSFSheet sheet=workbook.getSheetAt(0);
		    	int rows=sheet.getPhysicalNumberOfRows();
		    	
		    	HashMap<String, String> hm = new HashMap<String, String>();
		    	for(rowindex=0;rowindex<1;rowindex++){
		    		HSSFRow row=sheet.getRow(rowindex);
		    		
		    	    if(row !=null){
		    	        int cells=row.getPhysicalNumberOfCells();
		    	        for(columnindex=0;columnindex<=cells;columnindex++){
		    	            HSSFCell cell=row.getCell(columnindex);
		    	            String value="";
		    	            if(cell==null){
		    	                continue;
		    	            }else{
		    	                switch (cell.getCellType()){
		    	                case HSSFCell.CELL_TYPE_FORMULA:
		    	                    value=cell.getCellFormula();
		    	                    break;
		    	                case HSSFCell.CELL_TYPE_NUMERIC:
		    	                    value=cell.getNumericCellValue()+"";
		    	                    break;
		    	                case HSSFCell.CELL_TYPE_STRING:
		    	                    value=cell.getStringCellValue()+"";
		    	                    break;
		    	                case HSSFCell.CELL_TYPE_BLANK:
		    	                    value=cell.getBooleanCellValue()+"";
		    	                    break;
		    	                case HSSFCell.CELL_TYPE_ERROR:
		    	                    value=cell.getErrorCellValue()+"";
		    	                    break;
		    	                }
		    	            }
		    	            hm.put("COL"+columnindex, value);
		    	            datasetSampleFileColumnList.add(hm);
		    	        }
		    	    }
		    	}
		    	if(workbook!=null)workbook.close();
	    	}
	    	
	    	if(fis!=null)fis.close();
	    	model.addAttribute("datasetSampleFileColumnList", datasetSampleFileColumnList);
	    	
	    	if(datasetSampleFileColumnList!=null&&datasetSampleFileColumnList.size()>0) { 
	    		model.addAttribute("datasetSampleDiv", "file");	
	    	} else {
	    		model.addAttribute("datasetSampleDiv", "db");
	    	}
	    	
	    	
    	} else {
    		

        	List<Map<String,String>> datasetSampleColumnList = portalService.getDatasetSampleColumnList(datasetId);    	
        	model.addAttribute("datasetSampleColumnList",datasetSampleColumnList);
        	model.addAttribute("datasetSampleDiv", "db");
    	}
    	
    	} catch(Exception e) { // if it can not read excel file, then read to db 
    		List<Map<String,String>> datasetSampleColumnList = portalService.getDatasetSampleColumnList(datasetId);    	
        	model.addAttribute("datasetSampleColumnList",datasetSampleColumnList);
        	model.addAttribute("datasetSampleDiv", "db");
    	}
    
    	
    	return "portal/dataset/datasetDetail";
    }
    
    @RequestMapping("/datasetSampleData")
    public String datasetSample(final Model model, HttpServletRequest request) {
    	
    	String datasetId = request.getParameter("datasetId");
    	String cnt = (request.getParameter("cnt")==null)?"0":request.getParameter("cnt");
    	System.out.println("##cnt=" + cnt);
    	Map<String,String> datasetSampleSql = portalService.getDatasetSampleSql(datasetId);
    	List<Map<String,Object>> sampleList = new ArrayList<Map<String,Object>>();
    	    	
    	if(datasetSampleSql != null) {
    		datasetSampleSql.put("cnt", cnt);
    		sampleList = portalService.getSampleList(datasetSampleSql);
    	} 

    	model.addAttribute("sampleList",sampleList);    	
    	return "portal/dataset/datasetSampleData";
    }
    
    @RequestMapping("/datasetSampleFileData")
    public String datasetSampleFile(final Model model, HttpServletRequest request) throws IOException {
    	
    	String datasetId = request.getParameter("datasetId");
    	String cnt = (request.getParameter("cnt")==null)?"0":request.getParameter("cnt");
    	System.out.println("##cnt=" + cnt);
    	
    	//첨부파일 목록
    	Map<String,String> datasetSampleFile = portalService.datasetSampleFile(datasetId);
    	List<Map<String,Object>> sampleList = new ArrayList<Map<String,Object>>();
    	
    	if(datasetSampleFile != null) {
	    	//파일을 읽기위해 엑셀파일을 가져온다
	    	FileInputStream fis=new FileInputStream(datasetSampleFile.get("FILE_NAME"));
	    	
	    	if(datasetSampleFile.get("EXT").equals("xlsx")) {
	    		XSSFWorkbook workbook=new XSSFWorkbook(fis);
		    	int rowindex=0;
		    	int columnindex=0;
		
		    	XSSFSheet sheet=workbook.getSheetAt(0);
		    	int rows=sheet.getPhysicalNumberOfRows();
		    	
		    	for(rowindex=1;rowindex<Integer.parseInt(cnt);rowindex++){
		    		XSSFRow row=sheet.getRow(rowindex);
		    		HashMap<String, Object> hm = new HashMap<String, Object>();
		    	    if(row !=null){
		    	        int cells=row.getPhysicalNumberOfCells();
		    	        for(columnindex=0;columnindex<=cells;columnindex++){
		    	        	XSSFCell cell=row.getCell(columnindex);
		    	            String value="";
		    	            if(cell==null){
		    	                continue;
		    	            }else{
		    	                switch (cell.getCellType()){
		    	                case XSSFCell.CELL_TYPE_FORMULA:
		    	                    value=cell.getCellFormula();
		    	                    break;
		    	                case XSSFCell.CELL_TYPE_NUMERIC:
		    	                    value=cell.getNumericCellValue()+"";
		    	                    break;
		    	                case XSSFCell.CELL_TYPE_STRING:
		    	                    value=cell.getStringCellValue()+"";
		    	                    break;
		    	                case XSSFCell.CELL_TYPE_BLANK:
		    	                    value=cell.getBooleanCellValue()+"";
		    	                    break;
		    	                case XSSFCell.CELL_TYPE_ERROR:
		    	                    value=cell.getErrorCellValue()+"";
		    	                    break;
		    	                }
		    	            }
		    	            hm.put("COL"+columnindex, value);
		    	        }
		    	    }
		    	    sampleList.add(hm);
		    	}
		    	if(workbook!=null)workbook.close();
	    	}else if(datasetSampleFile.get("EXT").equals("xls")) {
	    		HSSFWorkbook workbook=new HSSFWorkbook(fis);
		    	int rowindex=0;
		    	int columnindex=0;
		
		    	HSSFSheet sheet=workbook.getSheetAt(0);
		    	int rows=sheet.getPhysicalNumberOfRows();
		    	
		    	for(rowindex=1;rowindex<Integer.parseInt(cnt);rowindex++){
		    		HSSFRow row=sheet.getRow(rowindex);
		    		HashMap<String, Object> hm = new HashMap<String, Object>();
		    	    if(row !=null){
		    	        int cells=row.getPhysicalNumberOfCells();
		    	        for(columnindex=0;columnindex<=cells;columnindex++){
		    	            HSSFCell cell=row.getCell(columnindex);
		    	            String value="";
		    	            if(cell==null){
		    	                continue;
		    	            }else{
		    	                switch (cell.getCellType()){
		    	                case HSSFCell.CELL_TYPE_FORMULA:
		    	                    value=cell.getCellFormula();
		    	                    break;
		    	                case HSSFCell.CELL_TYPE_NUMERIC:
		    	                    value=cell.getNumericCellValue()+"";
		    	                    break;
		    	                case HSSFCell.CELL_TYPE_STRING:
		    	                    value=cell.getStringCellValue()+"";
		    	                    break;
		    	                case HSSFCell.CELL_TYPE_BLANK:
		    	                    value=cell.getBooleanCellValue()+"";
		    	                    break;
		    	                case HSSFCell.CELL_TYPE_ERROR:
		    	                    value=cell.getErrorCellValue()+"";
		    	                    break;
		    	                }
		    	            }
		    	            hm.put("COL"+columnindex, value);
		    	        }
		    	    }
		    	    sampleList.add(hm);
		    	}
		    	if(workbook!=null)workbook.close();
	    	}
	    	if(fis!=null)fis.close();
    	}
    	
    	model.addAttribute("sampleList",sampleList);
    	
    	return "portal/dataset/datasetSampleFileData";
    }
	
}