package com.wtyt.yzone.locate.service;

import java.sql.SQLException;


import com.wtyt.yzone.locate.bean.LocateBean;

public interface LocateService {

	void locateSaveDataInfo(LocateBean locBean) throws SQLException;

}
