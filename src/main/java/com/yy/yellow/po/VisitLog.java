package com.yy.yellow.po;

import java.util.Date;

public class VisitLog {
	private int id;
	private Integer userId;
	private String ip;
	private String userAgent;
	private String requestUrl;
	private String params;
	private Date createTime = new Date();
	public int getId() {
		return id;
	}
	public Integer getUserId() {
		return userId;
	}
	public String getIp() {
		return ip;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public String getRequestUrl() {
		return requestUrl;
	}
	public String getParams() {
		return params;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}