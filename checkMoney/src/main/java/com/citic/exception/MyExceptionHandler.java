package com.citic.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 统一异常处理，有效地针对异步和非异步请求
 */
public class MyExceptionHandler implements HandlerExceptionResolver {

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		Map<String, Object> model = new HashMap<String, Object>();

		if (!(request.getHeader("accept").indexOf("application/json") > -1
				|| (request.getHeader("X-Requested-With") != null
						&& request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
			// 如果不是异步请求
			// Apply HTTP status code for error views, if specified.
			// Only apply it if we're processing a top-level request.
			SystemException systemException = null;
			if (ex instanceof SystemException ) {
				systemException = (SystemException) ex;
			} else if (ex.getCause() instanceof SystemException) {
				systemException = new SystemException(ex.getCause().getMessage());
			} else {
				systemException = new SystemException("未知错误");
			}
			model.put("message", systemException.getMessage());
			return new ModelAndView("error", model);
		} else {// JSON格式返回
			try {
				PrintWriter writer = response.getWriter();
				writer.write(ex.getMessage());
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
