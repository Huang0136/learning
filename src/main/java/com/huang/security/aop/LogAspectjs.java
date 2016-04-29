package com.huang.security.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspectjs {
	
	private static final Logger logger = Logger.getLogger(LogAspectjs.class);
	
	@Pointcut(value="execution(public * com.huang.controller.UserController.*(..))")
	public void controllerMethod() {
		
	}
	
	@Before(value="controllerMethod()")
	public void before(){
		System.out.println("before");
	}
	
	@Around(value="controllerMethod()")
	public Object wirtingLog(ProceedingJoinPoint point) {
		logger.info("arround func before doing");
		System.out.println("---");
		
		Object object = null;
		
		try {
			//执行被拦截的方法
			object = point.proceed();
		} catch (Throwable e1) {
			e1.printStackTrace();
		}
		
		logger.info("arround func after doing");
		
		//日志记录
		try {	
			logger.warn("模拟记录日志");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return object;
	}
	
}
