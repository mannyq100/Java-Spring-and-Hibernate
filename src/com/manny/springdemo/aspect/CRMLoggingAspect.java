package com.manny.springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class CRMLoggingAspect {
	
	//setup logger
	private Logger myLogger = Logger.getLogger(getClass().getName());
	
	//setup pointcut declarations
	@Pointcut("execution(* com.manny.springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	//do the same for service and dao packages
	@Pointcut("execution(* com.manny.springdemo.dao.*.*(..))")
	private void forDaoPackage() {}
	
	@Pointcut("execution(* com.manny.springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("forControllerPackage() || forDaoPackage() || forServicePackage()")
	public void forAppFlow() {}
		
	
	//add @Before advise
	@Before("forAppFlow()")
	public void before(JoinPoint theJoinPoint) {
	
		// display the method we are calling
	String theMethod =	theJoinPoint.getSignature().toShortString();
	myLogger.info("===>> in @Before: calling method: "+ theMethod);
	
	// get the arguments
	Object[] args = theJoinPoint.getArgs();
	
	// loop through and display args
	for(Object tempArg:args) {
		myLogger.info("====>> argument: "+tempArg);
	}
	}
	
	//add @AfterReturning advise
	@AfterReturning(
			pointcut="forAppFlow()",
			returning="theResult")
	public void afterReturning(JoinPoint theJoinPoint, Object theResult) {
		
		// display the method we are calling
	String theMethod =	theJoinPoint.getSignature().toShortString();
	myLogger.info("===>> in @Before: calling method: "+ theMethod);
	
	//display the data returned
	myLogger.info("result: "+theResult);
	
		
	}
	

}
