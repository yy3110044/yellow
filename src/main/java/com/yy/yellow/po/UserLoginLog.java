package com.yy.yellow.po;

import java.util.Date;

public class UserLoginLog {
	private int id;
	private int userId;
	private String userName;
	private String loginIp;
	private Date loginTime;
	private String loginType;
	private String userAgent;
	private Date createTime = new Date();
	public int getId() {
		return id;
	}
	public int getUserId() {
		return userId;
	}
	public String getUserName() {
		return userName;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}