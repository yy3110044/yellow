package com.yy.yellow.po;

import java.util.Date;

public class AdminUser {
	private int id;
	private String userName;
	private String passWord;
	private String lastLoginIp;
	private Date lastLoginTime;
	private Date createTime = new Date();
	public int getId() {
		return id;
	}
	public String getUserName() {
		return userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
}