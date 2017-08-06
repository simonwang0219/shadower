package com.unionpay.shadower.server.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class ExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		// 解析异常类型，如果该异常为系统定义的异常，直接取出异常信息，在错误页面展示
		String message = null;
		if (ex instanceof Exception) {
			message = ex.getMessage();
		} else {
			// 如果异常不是系统自定义的异常，构造一个自定义的异常类型（信息为“未知错误”）
			message = "未知错误";
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", message);
		modelAndView.setViewName("error");
		return modelAndView;
	}

}
