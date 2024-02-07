package com.miniproject.aop;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.miniproject.domain.MemberDto;
import com.miniproject.domain.MemberVo;
import com.miniproject.util.UploadFileProcess;

@Component
@Aspect
public class LoginAdvice {
	
	Logger logger = LoggerFactory.getLogger(LoginAdvice.class);
	
	@Around ("execution(public * com.miniproject.service.member.MemberServiceImple.getLoginUserInfo(..))")
	public Object loginLog (ProceedingJoinPoint pjp) throws Throwable {
//	public void loginLog (ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("----------------------------------------------");
		System.out.println("로그인 시도!!!");
		System.out.println("----------------------------------------------");
		
		
		HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String logRealPath = req.getSession().getServletContext().getRealPath("resources/logs");
		
		System.out.println(logRealPath);
		String realPath = UploadFileProcess.makeCalculatePath(logRealPath);
		System.out.println("realPath : " + realPath);
		
		Calendar cal = Calendar.getInstance();
		String filename = realPath + File.separator + 
						  cal.get(Calendar.YEAR) + 
						  new DecimalFormat("00").format(cal.get(Calendar.MONTH) +1) +
						  new DecimalFormat("00").format(cal.get(Calendar.DATE)) + ".log";
		
		System.out.println("filename : " + filename);
		
		
		Object [] args = pjp.getArgs();
		System.out.println(Arrays.toString(args));
		
		String tryLoginMember = ((MemberDto) pjp.getArgs()[0]).getUserId();
		
		Object result = pjp.proceed();

		System.out.println("로그인 성공 여부 : " + (MemberVo)result);
		
		String resultLogin = ((MemberVo)result != null)? "로그인 성공" : "로그인 실패";
		
		// 파일에 저장
		BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true));
		bw.write(tryLoginMember + "이/가 " + cal.getTime().toString() + "에 접속 시도-- " + resultLogin );
		bw.newLine();
		
		bw.close();
		
		return result;
		
	}
	
}
