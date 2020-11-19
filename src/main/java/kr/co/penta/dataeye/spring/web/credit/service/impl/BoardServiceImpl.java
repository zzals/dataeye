package kr.co.penta.dataeye.spring.web.credit.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kr.co.penta.dataeye.common.entities.meta.PenAprvM;
import kr.co.penta.dataeye.spring.config.properties.DataEyeSettings;
import kr.co.penta.dataeye.spring.mybatis.dao.CommonDao;
import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.web.aprv.enums.AprvCodeType;
import kr.co.penta.dataeye.spring.web.credit.service.AnaTaskService;
import kr.co.penta.dataeye.spring.web.credit.service.BoardService;
import kr.co.penta.dataeye.spring.web.session.SessionInfo;
import kr.co.penta.dataeye.spring.web.session.SessionInfoSupport;

@Service
public class BoardServiceImpl implements BoardService {
	
	private Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CommonDao commonDao;
    
    @Autowired
    private DataEyeSettings dataEyeSettings;

	@Override
	public Map<String, Object> getBoardList(Map<String, Object> reqParam)  {
		// TODO Auto-generated method stub
		
		DaoParam daoParam = new DaoParam();
        daoParam.put("KIND_CD", reqParam.get("KIND_CD"));                
        Map<String, Object> map = commonDao.selectOne("credit_board.getBoardTotalCnt", daoParam);
        int tot_cnt=Integer.parseInt(map.get("CNT").toString());
        
        int page_size=10;
        int record_size=10;
        int go_page=Integer.parseInt(reqParam.get("GO_PAGE").toString());
                        
        int start_num=record_size*(go_page-1)+1;
        int end_num=record_size*go_page;
        
        daoParam.put("START_NUM", start_num);
        daoParam.put("END_NUM", end_num);
        
        List<Map<String, Object>> list = commonDao.selectList("credit_board.getBoardList", daoParam);
        
        Map<String, Object> retMap=new HashMap<String, Object>();
        
        
        int tot_page=(int) Math.ceil((double)tot_cnt/(double)record_size);
        int start_page=((go_page-1)/page_size)*page_size+1;
        int end_page=start_page+page_size-1;
        
        if(tot_page<end_page) {
        	end_page=tot_page;
        }
        String isFirst, isLast;
        
        int pre_block=(int)Math.floor( ((double)(go_page-1))/(double)page_size);
        int next_block=(int)Math.ceil(((double)go_page)/(double)page_size);
        int tot_block=(int)Math.ceil((double)tot_page/(double)page_size);
        
        isFirst=pre_block==0?"N":"Y";        
        isLast=next_block==tot_block?"N":"Y";
        
        
        Map<String, Object> pageNavi=new HashMap<String, Object>();
        pageNavi.put("KIND_CD", reqParam.get("KIND_CD"));
        pageNavi.put("isFirst", isFirst);
        pageNavi.put("isLast", isLast);
        pageNavi.put("start_page", start_page);
        pageNavi.put("end_page", end_page);
        pageNavi.put("go_page", go_page);
        
        
        if(isFirst.equals("Y")) {         	
        	int pre_no=pre_block*page_size;
        	pageNavi.put("pre_no", pre_no);        	        	
        }
        
        if(isLast.equals("Y")) { 
        	int next_no=((next_block*page_size)+1);
        	pageNavi.put("next_no", next_no);        	        	
        }
        		
        
        
        retMap.put("boardList", list);
        retMap.put("tot_page", tot_page);
        retMap.put("tot_cnt", tot_cnt);
        retMap.put("pageNavi", pageNavi);
        retMap.put("page_size", page_size);
        
        
        
        
		return retMap;
	}

	@Override
	public Map<String, Object> getBoardView(Map<String, Object> reqParam, String gubun)  {
		// TODO Auto-generated method stub
		
		
		DaoParam daoParam = new DaoParam();
        daoParam.put("B_SEQ", reqParam.get("B_SEQ"));
        
        //상세 조회한다.
        Map<String, Object> map = commonDao.selectOne("credit_board.getBoardView", daoParam);
                         
        //파일리스트를 조회한다.
        List<Map<String, Object>> fileList = commonDao.selectList("credit_board.getBoardFileView", daoParam);
        
        
        //조회수를 업데이트 한다.
        if(gubun.equals("view")) {
        	commonDao.insert("credit_board.updateBoardReadCnt", daoParam);
        }
    	
        
        Map<String, Object> retMap=new HashMap<String, Object>();		
        retMap.put("boardView", map);
        retMap.put("fileList", fileList);
        
		return retMap;
	}

	@Override
	@Transactional
	public void insertBoard(Map<String, Object> param, HttpSession session) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> boardMap=(Map<String, Object>) param.get("BOARD_DATA");
		List<Map<String, Object>> fileList=(List<Map<String, Object>>) param.get("FILE_LIST");
		
		//게시판 키값을 얻는다.
		DaoParam daoParam = new DaoParam();                        
        Map<String, Object> map = commonDao.selectOne("credit_board.getBoardSeq", daoParam);
        int b_seq=Integer.parseInt(map.get("B_SEQ").toString());
        
        //게시판 그룹번호를 얻는다.
      	daoParam = new DaoParam();                        
        map = commonDao.selectOne("credit_board.getBGrpSeq", daoParam);
        int b_grp_seq=Integer.parseInt(map.get("B_GRP_SEQ").toString());
        
        
        
        SessionInfo sInfo=SessionInfoSupport.getSessionInfo(session);
        
        
        //저장        
        daoParam = new DaoParam();
        daoParam.put("B_SEQ", b_seq);
        daoParam.put("B_GRP_SEQ", b_grp_seq);
        daoParam.put("B_LVL", 0);
        daoParam.put("B_SORT", 0);
        daoParam.put("TITLE", boardMap.get("TITLE"));
        daoParam.put("CMNT", boardMap.get("CMNT"));
        daoParam.put("CRETR_ID", sInfo.getUserId());        
        daoParam.put("KIND_CD", boardMap.get("KIND_CD"));
        
        commonDao.insert("credit_board.insertCreditBoard", daoParam);
        
        String real_path=dataEyeSettings.getAttachFile().getUploadReadPath();
		
        //파일 저장
        for(Map<String, Object> fileMap:fileList) {
        	daoParam = new DaoParam();
            daoParam.put("B_SEQ", b_seq);
            daoParam.put("FILE_NM", fileMap.get("FILE_NM"));
            daoParam.put("FILE_PATH", real_path+File.separator);
            daoParam.put("CRETR_ID", sInfo.getUserId());        	
        	commonDao.insert("credit_board.insertCreditBoardFile", daoParam);
        }
        
        //파일 이동
        fileMove(fileList);
        
	}
	
	private void fileMove(List<Map<String, Object>> fileList) {
		
		String real_path=dataEyeSettings.getAttachFile().getUploadReadPath();
		
		DaoParam daoParam;
		//파일 저장
        for(Map<String, Object> fileMap:fileList) {
        	String file_path=fileMap.get("FILE_PATH").toString();
        	String file_nm=fileMap.get("FILE_NM").toString();
        	
        	File formfile=new File(file_path+file_nm);
        	File tofile=new File(real_path+File.separator+file_nm);
        	
        	try {
        		
        		if(!tofile.isFile()) {//파일이 존재하지 않으면 이동한다.
        			FileUtils.moveFile(formfile, tofile);
        		}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
        }		
	}
	
	/**
	 * 
	 * @param fileList
	 */
	private void fileDel(int b_seq) {
		
		
	}
	

	@Override
	@Transactional
	public void insertReplyBoard(Map<String, Object> param, HttpSession session) throws Exception {
		// TODO Auto-generated method stub
		
		
		SessionInfo sInfo=SessionInfoSupport.getSessionInfo(session);
		
		Map<String, Object> boardMap=(Map<String, Object>) param.get("BOARD_DATA");
		List<Map<String, Object>> fileList=(List<Map<String, Object>>) param.get("FILE_LIST");
		
		//게시판 키값을 얻는다.
		DaoParam daoParam = new DaoParam();                        
        Map<String, Object> map = commonDao.selectOne("credit_board.getBoardSeq", daoParam);
        int b_seq=Integer.parseInt(map.get("B_SEQ").toString());
        
        //게시판 그룹번호를 얻는다.      	
        int b_grp_seq=Integer.parseInt(boardMap.get("B_GRP_SEQ").toString());        
        int b_sort=Integer.parseInt(boardMap.get("B_SORT").toString());
        int b_lvl=Integer.parseInt(boardMap.get("B_LVL").toString());
        
        
        //sort를 업데이트 한다.        
        daoParam = new DaoParam();       
        daoParam.put("B_GRP_SEQ", b_grp_seq);       
        daoParam.put("B_SORT", b_sort);
        commonDao.update("credit_board.updateBoardSort", daoParam);
        
        
        
        
        //reply 저장        
        daoParam = new DaoParam();
        daoParam.put("B_SEQ", b_seq);
        daoParam.put("B_GRP_SEQ", b_grp_seq);
        daoParam.put("B_LVL", b_lvl+1);
        daoParam.put("B_SORT", b_sort+1);
        daoParam.put("TITLE", boardMap.get("TITLE"));
        daoParam.put("CMNT", boardMap.get("CMNT"));
        daoParam.put("CRETR_ID", sInfo.getUserId());        
        daoParam.put("KIND_CD", boardMap.get("KIND_CD"));        
        commonDao.insert("credit_board.insertCreditBoard", daoParam);
        
        
        
        
        String real_path=dataEyeSettings.getAttachFile().getUploadReadPath();
        
        //파일 저장
        for(Map<String, Object> fileMap:fileList) {
        	daoParam = new DaoParam();
            daoParam.put("B_SEQ", b_seq);
            daoParam.put("FILE_NM", fileMap.get("FILE_NM"));
            daoParam.put("FILE_PATH", real_path+File.separator);
            daoParam.put("CRETR_ID", sInfo.getUserId());        	
        	commonDao.insert("credit_board.insertCreditBoardFile", daoParam);
        }
        
        //파일 이동
        fileMove(fileList);
	}

	@Override
	@Transactional
	public void updateBoard(Map<String, Object> param, HttpSession session) throws Exception {
		// TODO Auto-generated method stub
		
		SessionInfo sInfo=SessionInfoSupport.getSessionInfo(session);
		
		Map<String, Object> boardMap=(Map<String, Object>) param.get("BOARD_DATA");
		List<Map<String, Object>> fileList=(List<Map<String, Object>>) param.get("FILE_LIST");
		                      
       
        int b_seq=Integer.parseInt(boardMap.get("B_SEQ").toString());
        
        
        //수정  
        DaoParam daoParam = new DaoParam();
        daoParam.put("B_SEQ", b_seq);        
        daoParam.put("TITLE", boardMap.get("TITLE"));
        daoParam.put("CMNT", boardMap.get("CMNT"));
        
        commonDao.update("credit_board.updateBoard", daoParam);
        
        
        // 파일 저장
        daoParam = new DaoParam();
        daoParam.put("B_SEQ", b_seq);		
        List<Map<String, Object>> fList = commonDao.selectList("credit_board.getBoardFileView", daoParam);        
        
        String real_path=dataEyeSettings.getAttachFile().getUploadReadPath();
        
        //필요없는 파일을 삭제한다.
        String check_filenm;
        List<Map<String, Object>> checkList=new ArrayList<Map<String, Object>>();
        for(Map<String, Object> map:fList){
        	
        	check_filenm="";
        	String file_path=map.get("FILE_PATH").toString();
        	String file_nm=map.get("FILE_NM").toString();
        	
            for(Map<String, Object> fileMap:fileList) {
            	
            	//db에 있는 파일과 화면에서 넘어온 데이터가 같다면..
            	if(file_nm.equals(fileMap.get("FILE_NM").toString())) {
            		check_filenm=file_nm;            		
            		checkList.add(fileMap);
            	}
            	
            }
            
            if(check_filenm.equals("")) { //db에 있는 파일 삭제
            	File file=new File(real_path+file_nm);            	
            	file.delete();
            }
        }
        
        
        //db에서 파일정보를 삭제한다.
        commonDao.delete("credit_board.deleteBoardFile", daoParam);
        
        //신규파일을 입력한다.
        for(Map<String, Object> fileMap:fileList) {
        	
    		daoParam = new DaoParam();
            daoParam.put("B_SEQ", b_seq);
            daoParam.put("FILE_NM", fileMap.get("FILE_NM"));
            daoParam.put("FILE_PATH", real_path+File.separator);
            daoParam.put("CRETR_ID", sInfo.getUserId());        	
        	commonDao.insert("credit_board.insertCreditBoardFile", daoParam);
        }
        
        //파일 이동
        fileMove(fileList);
        
		
	}
    
    
	

}
