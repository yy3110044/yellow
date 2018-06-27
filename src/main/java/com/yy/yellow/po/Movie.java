package com.yy.yellow.po;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Movie {
	private String id;
	private String title;
	private String tags;
	private String externalLink;
	private String internalLink;
	还要添加一个文件本地磁盘url的参数
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createTime = new Date();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getExternalLink() {
		return externalLink;
	}
	public void setExternalLink(String externalLink) {
		this.externalLink = externalLink;
	}
	public String getInternalLink() {
		return internalLink;
	}
	public void setInternalLink(String internalLink) {
		this.internalLink = internalLink;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}