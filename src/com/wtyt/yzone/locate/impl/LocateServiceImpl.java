package com.wtyt.yzone.locate.impl;

import java.sql.SQLException;


import com.wtyt.util.init.dao.BaseDao;
import com.wtyt.yzone.locate.bean.LocateBean;
import com.wtyt.yzone.locate.service.LocateService;

public class LocateServiceImpl  implements LocateService{
	
	private BaseDao baseDao;

	/**
	 * @return the baseDao
	 */
	public BaseDao getBaseDao() {
		return baseDao;
	}

	/**
	 * @param baseDao the baseDao to set
	 */
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * 插入定位信息
	 */
	public void locateSaveDataInfo(LocateBean locBean)	throws SQLException {
		baseDao.insert(locBean, "locateSaveDataInfo");
	}
	
	
    
}
