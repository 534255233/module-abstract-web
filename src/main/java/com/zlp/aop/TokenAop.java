package com.zlp.aop;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zlp.annotation.Login;
import com.zlp.annotation.LoginValidate;
import com.zlp.annotation.Logout;
import com.zlp.entity.User;
import com.zlp.log.LoggerFactory;
import com.zlp.util.CurrentThreadUtil;
import com.zlp.util.UuidUtil;

/**
 * 
 * @author zhoulongpeng
 * @date   2016-02-17
 */
@Named("tokenAop")
@Aspect
public class TokenAop {
	
	private static Logger log = LoggerFactory.getLogger(TokenAop.class);
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Inject
	private HttpServletRequest request;
	
	@Inject
	private HttpServletResponse response;
	
	private static Map<String, String> users = new HashMap<>();
	
	@AfterReturning("@annotation(login)")
	public void interceptLogin(Login login) {
		log.log(Level.INFO, "--> intercepter of annotation of {0}", login.value());
		Object subject = CurrentThreadUtil.getSubject();
		try {
			String userString = this.serializeSubject(subject);
			log.info(userString);
			final String key = UuidUtil.getUUID32();
			users.put(key, userString);
			response.addHeader(login.value(), key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Around("@annotation(loginvalidate)")
	public Object interceptLoginValidate(ProceedingJoinPoint joinPoint, LoginValidate loginvalidate) throws Throwable{
		final String tokenName = loginvalidate.value();
		String token = request.getHeader(tokenName);
		if(StringUtils.isEmpty(token)) token = request.getParameter(tokenName);
		User user = this.deserializeSubject(users.get(token), User.class);
		log.info(user.toString());
		return joinPoint.proceed();
	}
	
	@Around("@annotation(logout)")
	public Object interceptLogout(ProceedingJoinPoint joinPoint,Logout logout) throws Throwable{
//		final String tokenName = logout.value();
//		final String token = internalValidateLogin(tokenName);
		return joinPoint.proceed();
	}
	
	
	private String serializeSubject(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}
	
	//IOException, JsonParseException, JsonMappingException
	private <T> T deserializeSubject(String json,Class<T> clazz) throws Exception{
		return objectMapper.readValue(json, clazz); 
	}

}
