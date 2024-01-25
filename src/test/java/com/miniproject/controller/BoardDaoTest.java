package com.miniproject.controller;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.miniproject.domain.BoardVo;
import com.miniproject.persistence.board.BoardDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class BoardDaoTest {

	@Inject
	private BoardDao dao; // Dao 객체를 주입
	
//	@Test
//	public void selectAllBoarList() {
//		try {
//			List<BoardVo> list = dao.selectAllBoard();
//			
//			for (BoardVo vo : list) {
//				System.out.println(vo.toString());
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
