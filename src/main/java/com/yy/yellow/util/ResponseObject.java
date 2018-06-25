package com.yy.yellow.util;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * json返回结果对象
 * @author yy
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)//去掉null值的属性
public class ResponseObject {
	private int code;
	private String msg;
	private Map<String, Object> result;
	public ResponseObject() {}
	
	public ResponseObject(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public ResponseObject(int code, String msg, Map<String, Object> result) {
		this(code, msg);
		this.result = result;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public Map<String, Object> getResult() {
		return result;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setResult(Map<String, Object> result) {
		this.result = result;
	}
}