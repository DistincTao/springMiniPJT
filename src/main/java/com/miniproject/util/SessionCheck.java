package com.miniproject.util;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap; // 동기화 된 HashMap

import javax.servlet.annotation.WebListener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionCheck implements HttpSessionListener {
	
	private static Map<String, HttpSession> sessions = new ConcurrentHashMap<>(); // 동기화
	private static HttpSession sess;
	
	// 동기화 하여 동작되도록 설정
	public static synchronized void replaceSessionKey (HttpSession se, String loginUserId) throws Exception {
		// 세션 리스트에 로그인 유저 아이디가 없고, ses 값만 있으면 => 최초 로그인
		if (!sessions.containsKey(loginUserId) && sessions.containsValue(se)) {
			System.out.println(loginUserId + "님의 최초 로그인!");
			sessions.put(loginUserId, se);
			sessions.remove(se.getId());
		} else if (sessions.containsKey(loginUserId)){
			System.out.println(loginUserId + "님의 중복 로그인 감지!!");
			removeSessionKey(loginUserId);
			
			sessions.put(loginUserId, se);
		}
		printSessionsMap();

	}
	
	public static void removeSessionKey(String userId) throws Exception {
		
		if (sessions.containsKey(userId)) {
			sessions.get(userId).removeAttribute("login");
			if (sessions.get(userId).getAttribute("returnUri") != null) {
				sessions.get(userId).removeAttribute("returnUri");
			}	
			
			sessions.get(userId).invalidate();
			sessions.remove(userId);
			
		} 

		
		printSessionsMap();
	}


	@Override
	public synchronized void sessionCreated(HttpSessionEvent se) {
		// 세션이 생성되면 동작되면 알림
		System.out.println("Session이 생성되었습니다!!");
		System.out.println("생성된 Session Id : " + se.getSession().getId());

		//세션이 생성이 되면 Map에 해당 세션을 등록
		sessions.put(se.getSession().getId(), se.getSession());
		printSessionsMap();
		
	}

	@Override
	public synchronized void sessionDestroyed(HttpSessionEvent se) {
		// 세션이 종료되기 직전에 알림
		System.out.println("Session이 소멸되었습니다!!");
		System.out.println("소멸된 Session Id : " + se.getSession().getId());
		// 세션이 종료되면 Map에서 해당 세션 삭제
		if (sessions.containsKey(se.getSession().getId())) {
			se.getSession().invalidate();
			sessions.remove(se.getSession().getId());
			
		}
		
	}

	public static void printSessionsMap() {
		System.out.println("====== 현재 생성된 세션 리스트 =====");
		Set<String> keys = sessions.keySet();
		
		for (String key : keys) {
			System.out.println(key + " : " + sessions.get(key).toString());
		}
		System.out.println("=============================");

	}


}
