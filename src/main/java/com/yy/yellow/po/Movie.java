package com.yy.yellow.po;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Movie {
	private String id;
	private String title;
	private String tags;
	private String imgUrl;
	private String externalLink;
	private String internalLink;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime = new Date();
	public String getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getTags() {
		return tags;
	}
	public String getExternalLink() {
		return externalLink;
	}
	public String getInternalLink() {
		return internalLink;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public void setExternalLink(String externalLink) {
		this.externalLink = externalLink;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public void setInternalLink(String internalLink) {
		this.internalLink = internalLink;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}