package com.zlp.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class AbstractService {
	
//	@Deprecated
//	protected Map<String, String> queryParams(String field, String operator, String val) {
//		Map<String, String> query = new HashMap<>();
//		query.put("field", field);
//		query.put("operator", operator);
//		query.put("val", val);
//		return query;
//	}
	
	protected Map<String, Object> queryField(String field, String operator, Object val) {
		Map<String, Object> query = new HashMap<>();
		query.put("field", field);
		query.put("operator", operator);
		query.put("val", val);
		return query;
	}
	
	protected Map<String, Object> filter(String key, Object val) {
		Map<String, Object> query = new HashMap<>();
		query.put(key, val);
		return query;
	}
	
	protected Object[] setToArray(Set<?> set) {
		Object[] objs = new Object[set.size()];
		int i = 0;
		Iterator<?> iter = set.iterator();
		while(iter.hasNext()) {
			objs[i++] = iter.next();
		}
		return objs;
	}

}
