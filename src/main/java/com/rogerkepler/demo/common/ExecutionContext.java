package com.rogerkepler.demo.common;

import java.util.HashMap;
import java.util.Map;

public class ExecutionContext {

	private Map<String, Object> items = new HashMap<String, Object>();
	
	public void addContextItem(String key, Object item) {
		items.put(key,  item);
	}
	
	public Object getContextItem(String key) {
		return items.get(key);
	}
	
	public boolean containsKey(String key) {
		return items.containsKey(key);
	}
}
