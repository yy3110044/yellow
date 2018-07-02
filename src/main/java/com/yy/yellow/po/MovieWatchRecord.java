package com.yy.yellow.po;

import java.util.Date;

public class MovieWatchRecord {
	private int id;
	private int userId;
	private String ip;
	private String movieId;
	private Date lastWatchTime;
	private Date createTime = new Date();
	public int getId() {
		return id;
	}
	public int getUserId() {
		return userId;
	}
	public String getIp() {
		return ip;
	}
	public String getMovieId() {
		return movieId;
	}
	public Date getLastWatchTime() {
		return lastWatchTime;
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
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}
	public void setLastWatchTime(Date lastWatchTime) {
		this.lastWatchTime = lastWatchTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}