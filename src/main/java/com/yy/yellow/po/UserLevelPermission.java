package com.yy.yellow.po;

import java.util.Date;

public class UserLevelPermission {
	private int id;
	private int level;
	private int watchMovieCount;
	private Date createTime = new Date();
	public int getId() {
		return id;
	}
	public int getLevel() {
		return level;
	}
	public int getWatchMovieCount() {
		return watchMovieCount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public void setWatchMovieCount(int watchMovieCount) {
		this.watchMovieCount = watchMovieCount;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}