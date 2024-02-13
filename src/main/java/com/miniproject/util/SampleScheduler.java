package com.miniproject.util;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.miniproject.domain.PointlogDto;
import com.miniproject.persistence.pointlog.PointlogDao;

@Component
public class SampleScheduler {
	
	@Autowired
//	@Inject
	private PointlogDao pDao;
	
//	@Scheduled(cron = "0 17 10 * * *") // 매일 10 시 17분에 실행
	@Scheduled(cron = "* * * * * *") // 매일 초마다 
//	@Scheduled(cron = "0 0/1 * * * *") // 매일 1분 마다 
	public void sampleShecule() throws Exception {
		
		System.out.println("==============Scheduling=================");
		pDao.insertPointlog(new PointlogDto(-1, null, "test", 1, "peter"));
	}

}
