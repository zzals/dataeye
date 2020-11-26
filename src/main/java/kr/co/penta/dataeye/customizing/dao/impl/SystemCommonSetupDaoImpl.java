package kr.co.penta.dataeye.customizing.dao.impl;

import org.springframework.stereotype.Repository;

import kr.co.penta.dataeye.customizing.dao.SystemCommonSetupDao;
import kr.co.penta.dataeye.spring.mybatis.dao.support.DataEyeDaoSupport;

@Repository
public class SystemCommonSetupDaoImpl extends DataEyeDaoSupport implements SystemCommonSetupDao {

	@Override
	public int selectSessionTimeOut() {
		return Integer.parseInt((String) sqlSession.selectOne("systemCommonSetup.selectSessionTimeOut"));
	}

}
