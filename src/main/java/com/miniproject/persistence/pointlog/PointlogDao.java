package com.miniproject.persistence.pointlog;

import java.sql.Date;

import com.miniproject.domain.PointlogDto;

public interface PointlogDao {

	int insertPointlog(PointlogDto pointDto) throws Exception;

	Date selectLastLogin(String userId) throws Exception;
}
