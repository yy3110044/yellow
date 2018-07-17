package com.yy.yellow.util;

import java.util.HashMap;

public class MyMap extends HashMap<String, Object> {
	private static final long serialVersionUID = 1683927307411577815L;

	public MyMap set(String key, Object value) {
		put(key, value);
		return this;
	}
}