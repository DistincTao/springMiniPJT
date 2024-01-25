package com.miniproject.persistence.readcountprocess;

import com.miniproject.domain.ReadcountprocessDto;
import com.miniproject.domain.ReadcountprocessVo;

public interface readcountDao {

	ReadcountprocessVo selectReadcountprocess (ReadcountprocessDto dto) throws Exception;

	int getHourDiffReadTime (ReadcountprocessDto dto) throws Exception;

	int updateReadCountProcess(ReadcountprocessDto dto) throws Exception;

	int insertReadCountProcess(ReadcountprocessDto dto) throws Exception;	
}
