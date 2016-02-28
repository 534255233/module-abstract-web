package com.zlp.test.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class DemoAop {
	
	@Pointcut("execution(* com.zlp.controller.LoginController.*(..))")  
    private void anyMethod(){}//定义一个切入点
	
	@Before("anyMethod() && args(name)")
	public void doBefore(String name) {
		System.out.println(name);  
        System.out.println("前置通知");
	}
	
	@After("anyMethod()")
	public void doAfter() {
		System.out.println("最终通知");
	}
	
	@AfterReturning("anyMethod()")
	public void doAfterReturning() {
		System.out.println("后置通知");
	}
	
	@AfterThrowing("anyMethod()")
	public void doAfterThrowing() {
		System.out.println("例外通知");  
	}
	
	@Around("anyMethod()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("进入环绕通知");  
        Object object = pjp.proceed();//执行该方法  
        System.out.println("退出方法");  
        return object;  
	}
	
	/**
	 * 前置通知 
	 * 进入环绕通知  
	 * -->我是save方法
	 * 后置通知
	 * 退出方法
	 * 最终通知
	 */

}