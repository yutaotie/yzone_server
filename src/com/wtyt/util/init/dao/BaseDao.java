package com.wtyt.util.init.dao;

import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;

public interface BaseDao {

	

	// 添加
	void insert(Object bean,String namespace) throws DataAccessException;

	// 修改
	void update(Object bean,String namespace) throws DataAccessException;

	// 删除
	void delete(Object bean,String namespace) throws DataAccessException;

	// 获取单个结果集
	Object getInfo(Object bean,String namespace) throws DataAccessException;

	// 获取列表结果集
	@SuppressWarnings("unchecked")
	List getList(Object bean,String namespace) throws DataAccessException;

	// 获取总数
	Integer getCount(Object bean,String namespace) throws DataAccessException;

	// 批量添加
	@SuppressWarnings("unchecked")
	void batchInsert(final List list,final String namespace) throws SQLException;

	// 批量修改
	@SuppressWarnings("unchecked")
	void batchUpdate(final List list,final String namespace) throws SQLException;

	// 批量删除
	@SuppressWarnings("unchecked")
	void batchDelete(final List list,final String namespace) throws SQLException;

	//插入返回
	String insertReturn(Object bean,String namespace) throws DataAccessException;

}
