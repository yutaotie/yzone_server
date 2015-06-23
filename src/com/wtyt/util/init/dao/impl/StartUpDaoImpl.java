package com.wtyt.util.init.dao.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;



import com.wtyt.util.init.dao.StartUpDao;
import com.wtyt.util.publicclass.SystemParameterBean;
 

public class StartUpDaoImpl extends SqlMapClientDaoSupport implements
		StartUpDao {

	@SuppressWarnings("unchecked")
	public List<SystemParameterBean> getSysParameters()
			throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("getSysParameters");
	}

	

	 

}
