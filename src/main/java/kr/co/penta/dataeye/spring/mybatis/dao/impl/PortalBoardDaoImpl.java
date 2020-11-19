package kr.co.penta.dataeye.spring.mybatis.dao.impl;


import java.util.Map;

import org.springframework.stereotype.Repository;


import kr.co.penta.dataeye.spring.mybatis.dao.PortalBoardDao;

import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;
import kr.co.penta.dataeye.spring.mybatis.dao.support.DataEyeDaoSupport;


@Repository
public class PortalBoardDaoImpl extends DataEyeDaoSupport implements PortalBoardDao {

	
	
	@Override
	public int getBoardNo() {
		DaoParam daoParam = new DaoParam();		
		return sqlSession.selectOne("portal.board.getBoardNo", daoParam);
	}
	
	@Override
	public int getBoardFileNo() {
		DaoParam daoParam = new DaoParam();		
		return sqlSession.selectOne("portal.board.getBoardFileNo", daoParam);
	}
	
	@Override
	public int getBoardReplyNo() {
		DaoParam daoParam = new DaoParam();		
		return sqlSession.selectOne("portal.board.getBoardReplyNo", daoParam);
	}
	
	@Override
	public void insertPortalBoard(Map<String, Object> paraMap) {			
		sqlSession.insert("portal.board.insertPortalBoard", paraMap);
	}
	
	@Override
	public void insertPortalBoardReply(Map<String, Object> paraMap) {			
		sqlSession.insert("portal.board.insertPortalBoardReply", paraMap);
	}
	
	@Override
	public void insertPortalBoardFile(Map<String, Object> paraMap) {		
		sqlSession.insert("portal.board.insertPortalBoardFile", paraMap);
	}
	
	@Override
	public void updatePortalBoard(Map<String, Object> paraMap) {			
		sqlSession.insert("portal.board.updatePortalBoard", paraMap);
	}
	
	@Override
	public void deletePortalBoard(Map<String, Object> paraMap) {		
		sqlSession.insert("portal.board.deletePortalBoard", paraMap);
	}
	
	@Override
	public void deletePortalBoardReply(Map<String, Object> paraMap) {		
		sqlSession.insert("portal.board.deletePortalBoardReply", paraMap);
	}
	
	@Override
	public void deletePortalBoardFile(Map<String, Object> paraMap) {		
		sqlSession.insert("portal.board.deletePortalBoardFile", paraMap);
	}
	
	@Override
	public void updateBoardReadCnt(Map<String, Object> paraMap) {		
		sqlSession.insert("portal.board.updateBoardReadCnt", paraMap);
	}
	
	@Override
	public void updatePortalBoardFile(Map<String, Object> paraMap) {		
		sqlSession.insert("portal.board.updatePortalBoardFile", paraMap);
	}
		
	@Override
	public Map<String,String> getPortalBoardFileInfo(String file_id) {
		DaoParam daoParam = new DaoParam();		
		daoParam.put("file_id",file_id);
		return sqlSession.selectOne("portal.board.getPortalBoardFileInfo",daoParam );
	}

	@Override
	public Map<String,String> getPortalBoardFileInfo2(String brd_id) {
		DaoParam daoParam = new DaoParam();		
		daoParam.put("brd_id",brd_id);
		return sqlSession.selectOne("portal.board.getPortalBoardFileInfo2",daoParam );
	}
}
