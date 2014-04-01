package com.image.indicator.entity;

/**
 */
public class News {
	// ID
	private int id;
	private String simpleTitle;
	private String fullTitle;
	private String newsUrl;
	private String newsContent;
	private int viewCount;
	private int commentCount;
	private boolean isReaded;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getSimpleTitle() {
		return simpleTitle;
	}
	
	public void setSimpleTitle(String simpleTitle) {
		this.simpleTitle = simpleTitle;
	}
	
	public String getFullTitle() {
		return fullTitle;
	}
	
	public void setFullTitle(String fullTitle) {
		this.fullTitle = fullTitle;
	}
	
	public String getNewsUrl() {
		return newsUrl;
	}
	
	public void setNewsUrl(String newsUrl) {
		this.newsUrl = newsUrl;
	}
	
	public String getNewsContent() {
		return newsContent;
	}
	
	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}
	
	public int getViewCount() {
		return viewCount;
	}
	
	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	
	public int getCommentCount() {
		return commentCount;
	}
	
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	
	public boolean isReaded() {
		return isReaded;
	}
	
	public void setReaded(boolean isReaded) {
		this.isReaded = isReaded;
	}
}
