package com.zlp.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zlp.entity.Range;
import com.zlp.entity.vo.ResultMessageVO;
import com.zlp.entity.vo.ResultMessageVO.CodeEnum;
import com.zlp.exception.AddErrorException;
import com.zlp.exception.DeleteErrorException;
import com.zlp.exception.FileNoExistException;
import com.zlp.exception.HtmlNoExistException;
import com.zlp.exception.NoAddPermException;
import com.zlp.exception.NoDeletePermException;
import com.zlp.exception.NoReadPermException;
import com.zlp.exception.NoUpdatePermException;
import com.zlp.exception.SessionNullException;
import com.zlp.exception.UpdateErrorException;

public abstract class BaseController {
	
	protected static final String RANGE_PREFIX = "items=";
	protected static final String CONTENT_RANGE_HEADER = "Content-Range";
	protected static final String ACCEPT_JSON = "Accept=application/json";
	
	protected int start;
	protected int end;
	protected int pageSize;
	protected int currentPage;
	
	protected void setPagination(String range) {
		Range parsedRange = new Range(range.replaceAll(RANGE_PREFIX, ""));
		start = parsedRange.getStart();
		end = parsedRange.getEnd();
		pageSize = parsedRange.getPageSize();
		currentPage = parsedRange.getCurrentPage();
	}
	
	protected String getContentRangeValue(Integer from, Integer to, Long total) {
        StringBuffer value = new StringBuffer("items "+from+"-");
        if (to == 0) {
            value.append("0");
        } else {
            value.append(from + to - 1);
        }
        value.append("/"+total);
        return value.toString();
    }
	/**
	 * 
	 * @param request
	 * @return
	 */
	protected Map<String, Integer> sort(HttpServletRequest request) {
		if(request == null) return null;
//		Enumeration<String> params = request.getParameterNames();
		Enumeration<?> params = request.getParameterNames();
    	while(params != null && params.hasMoreElements()) {
    		String parameter = (String)params.nextElement();
    		if(parameter.startsWith("sort")) {
    			Map<String, Integer> tmp = new HashMap<>();
    			parameter = parameter.replace("sort(", "");
    			String order = parameter.substring(0, 1);
    			String field = parameter.substring(1, parameter.lastIndexOf(")"));
    			if("-".equals(order)) {
    				tmp.put(field, -1);
    			} else {
    				tmp.put(field, 1);
    			}
    			return tmp;
    		} 
    	}
    	return null;
	}
	
	/**权限拦截*/
	@ExceptionHandler(SessionNullException.class)
	protected String handleSessionNullException(SessionNullException ex) {
		return "login.html";
	}
	@ExceptionHandler(HtmlNoExistException.class)
	protected String handleHtmlNoExistException(HtmlNoExistException ex) {
		return "no-html-exist.html";
	}
	@ExceptionHandler(FileNoExistException.class)
	protected String handleFileNoExistException(FileNoExistException ex) {
		return "file-no-exist.html";
	}
	
	/**权限异常拦截*/
	@ExceptionHandler(NoReadPermException.class)
	protected String handleNoReadPermException(NoReadPermException ex) {
		return "no-read-permission.html";
	}
	@ExceptionHandler(NoAddPermException.class)
	@ResponseBody
	protected ResultMessageVO handleNoAddPermException(NoAddPermException ex) {
		return exceptionMessage(ex);
	}
	@ExceptionHandler(NoUpdatePermException.class)
	@ResponseBody
	protected ResultMessageVO handleNoUpdatePermException(NoUpdatePermException ex) {
		return exceptionMessage(ex);
	}
	@ExceptionHandler(NoDeletePermException.class)
	@ResponseBody
	protected ResultMessageVO handleNoDeletePermException(NoDeletePermException ex) {
		return exceptionMessage(ex);
	}
	
	/**CUD异常拦截*/
	@ExceptionHandler(AddErrorException.class)
	@ResponseBody
	protected ResultMessageVO handleAddErrorException(AddErrorException ex) {
		return exceptionMessage(ex);
	}
	@ExceptionHandler(UpdateErrorException.class)
	@ResponseBody
	protected ResultMessageVO handleUpdateErrorException(UpdateErrorException ex) {
		return exceptionMessage(ex);
	}
	@ExceptionHandler(DeleteErrorException.class)
	@ResponseBody
	protected ResultMessageVO handleDeleteErrorException(DeleteErrorException ex) {
		return exceptionMessage(ex);
	}
	
	/**空指针异常拦截*/
	@ExceptionHandler(NullPointerException.class)
	@ResponseBody
	protected ResultMessageVO handleException(NullPointerException ex) {
		return exceptionMessage(ex);
	}
	
	/**不可知异常拦截*/
	@ExceptionHandler(Exception.class)
	@ResponseBody
	protected ResultMessageVO handleException(Exception ex) {
		return exceptionMessage(ex);
	}
	
	private ResultMessageVO exceptionMessage(Exception ex) {
		CodeEnum e = ResultMessageVO.CodeEnum.EXCEPTION;
		return new ResultMessageVO(e.getValue(), e.getDes(), ex.getMessage());
	}
		
}
