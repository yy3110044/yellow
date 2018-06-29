package com.yy.yellow.po;

import java.util.Date;

public class AdminUserLoginLog {
	private int id;
	private int adminUserId;
	private String adminUserName;
	private String loginIp;
	private Date loginTime;
	private String userAgent;
	private Date createTime = new Date();
	public int getId() {
		return id;
	}
	public int getAdminUserId() {
		return adminUserId;
	}
	public String getAdminUserName() {
		return adminUserName;
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
	public void setAdminUserId(int adminUserId) {
		this.adminUserId = adminUserId;
	}
	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}