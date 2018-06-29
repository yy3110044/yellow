package com.yy.yellow.po;

import java.util.Date;

public class User {
	private int id;
	private String userName;
	private String passWord;
	private String nickName;
	private String email;
	private String lastLoginIp;
	private Date lastLoginTime;
	private String lastLoginType;
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
	public String getNickName() {
		return nickName;
	}
	public String getEmail() {
		return email;
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
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getLastLoginType() {
		return lastLoginType;
	}
	public void setLastLoginType(String lastLoginType) {
		this.lastLoginType = lastLoginType;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}