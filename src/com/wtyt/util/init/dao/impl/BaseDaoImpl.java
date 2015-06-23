package com.wtyt.util.init.dao.impl;

import java.sql.SQLException;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.wtyt.util.init.dao.BaseDao;


public class BaseDaoImpl extends SqlMapClientDaoSupport implements BaseDao {
	private static final long serialVersionUID = 1L;
	

	// 添加
	public void insert(Object bean,String namespace) throws DataAccessException {
		getSqlMapClientTemplate().insert(namespace, bean);
	}

	// 修改
	public void update(Object bean,String namespace) throws DataAccessException {
		getSqlMapClientTemplate().update(namespace, bean);
	}

	// 删除
	public void delete(Object bean,String namespace) throws DataAccessException {
		getSqlMapClientTemplate().delete(namespace, bean);
	}

	// 获取单个结果集
	public Object getInfo(Object bean,String namespace) throws DataAccessException {
		return getSqlMapClientTemplate().queryForObject(namespace, bean);
	}

	// 获取列表结果集
	@SuppressWarnings("unchecked")
	public List getList(Object bean,String namespace) throws DataAccessException {
		return getSqlMapClientTemplate().queryForList(namespace, bean);
	}

	// 获取总数
	public Integer getCount(Object bean,String namespace) throws DataAccessException {
		return (Integer) getSqlMapClientTemplate().queryForObject(namespace, bean);
	}

	// 批量添加
	@SuppressWarnings("unchecked")
	public void batchInsert(final List list, final String namespace) throws SQLException {
		if (list != null) {
			this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
				public Object doInSqlMapClient(SqlMapExecutor executor)
						throws SQLException {
					executor.startBatch();
					for (int i = 0, n = list.size(); i < n; i++) {
						executor.insert(namespace, list.get(i));
					}
					executor.executeBatch();
					return null;
				}
			});
		}
	}

	// 批量修改
	@SuppressWarnings("unchecked")
	public void batchUpdate(final List list,final String namespace) throws SQLException {
		if (list != null) {
			this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
				public Object doInSqlMapClient(SqlMapExecutor executor)
						throws SQLException {
					executor.startBatch();
					for (int i = 0, n = list.size(); i < n; i++) {
						executor.update(namespace, list.get(i));
					}
					executor.executeBatch();
					return null;
				}
			});
		}
	}

	// 批量删除
	@SuppressWarnings("unchecked")
	public void batchDelete(final List list,final String namespace) throws SQLException {
		if (list != null) {
			this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
				public Object doInSqlMapClient(SqlMapExecutor executor)
						throws SQLException {
					executor.startBatch();
					for (int i = 0, n = list.size(); i < n; i++) {
						executor.delete(namespace, list.get(i));
					}
					executor.executeBatch();
					return null;
				}
			});
		}
	}

	/**
	 * 带返回值的插入方法
	 */
	public String insertReturn(Object bean,String namespace) throws DataAccessException {
		return (String)getSqlMapClientTemplate().insert(namespace, bean);
	}



}
