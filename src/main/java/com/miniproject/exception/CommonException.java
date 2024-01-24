package com.miniproject.exception;

import java.io.IOException;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // 현재클래스가 공통 예외처리를할 클래스임을 명시
public class CommonException {
	
	@ExceptionHandler(IOException.class) // 구체적으로 해당(특정) 예외에 대하여 처리할 수 있음
	public String ioException(Exception e, Model model ) {
		model.addAttribute("errorMsg", e.getMessage());
		model.addAttribute("errorStack", e.getStackTrace());
		
		return "commonError";
	}
	
	@ExceptionHandler(Exception.class)
	public String exceptionHandling(Exception e, Model model) {
		model.addAttribute("errorMsg", e.getMessage());
		model.addAttribute("errorStack", e.getStackTrace());
		
		return "commonError";
	}
}
