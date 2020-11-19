package kr.co.penta.dataeye.spring.mybatis.dao;

import kr.co.penta.dataeye.spring.mybatis.dao.param.DaoParam;

import java.util.List;

public interface AprvDao {
    <T> T selectOne(DaoParam daoParam);
    <E> List<E> selectList(DaoParam daoParam);
    void insert(DaoParam daoParam);
    void update(DaoParam daoParam);
    void delete(DaoParam daoParam);
}
