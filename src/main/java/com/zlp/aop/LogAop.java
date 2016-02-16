package com.zlp.aop;

import java.util.logging.Logger;

import javax.inject.Named;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import com.zlp.log.LoggerFactory;

@Named("logAop")
@Aspect
public class LogAop {
	
	private static Logger log = LoggerFactory.getLogger(LogAop.class);
	
//	@Before("execution(* com.zlp.service.LoginService.login(..))")
	@Before("execution(* com.zlp.controller.*.*(..))")
	public void logBefore(JoinPoint joinPoint) {
		log.info("log before aop!");
//		StringBuffer sb = new StringBuffer();
//		for(Object obj : joinPoint.getArgs()) {
//			if(obj == null) continue;
//			sb.append(obj.toString());
//			sb.append(", ");
//		}
//		log.info(joinPoint.getSignature() + "method input parameter values ：" + sb.toString());
	}

	@AfterThrowing(pointcut = "execution(* com.zlp.controller.*.*(..))", throwing = "error")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
		log.info("log after aop from an error exception!");
		StringBuffer sb = new StringBuffer();
		StackTraceElement[] ee = error.getStackTrace();
		for(StackTraceElement e : ee) {
			sb.append(e.toString());
			sb.append("\n");
		}
		log.severe(joinPoint.getSignature() + " throw an Exception : " + error + "\n" + sb.toString());
	}

}
