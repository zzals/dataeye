package kr.co.penta.dataeye.spring.mybatis.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import kr.co.penta.dataeye.spring.mybatis.dao.MstrDao;
import kr.co.penta.dataeye.spring.mybatis.dao.support.MstrDaoSupport;

@Repository
public class MstrDaoImpl extends MstrDaoSupport implements MstrDao {

	@Override
	public List<Map<String, Object>> reportList(Map<String, Object> paraMap) {
		return sqlSession.selectList("mstr.latest.reportList", paraMap);
	}

}
