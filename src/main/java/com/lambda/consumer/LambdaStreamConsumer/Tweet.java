package com.lambda.consumer.LambdaStreamConsumer;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Tweet implements Serializable{
	
	@SerializedName("text")
	private String text;
	
	@SerializedName("created_at")
	private String createdAt;
	
	@SerializedName("source")
	private String source;
	
	/*@SerializedName("coordinates")
	private String coordinates;*/
	
	@SerializedName("lang")
	private String lang;
	
	//private TweetUser user;
	
	/*@SerializedName("extended_tweet")
	private ExtendedTweet extendedTweet;*/
	
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	/*public String getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}*/
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	/*public TweetUser getUser() {
		return user;
	}
	public void setUser(TweetUser user) {
		this.user = user;
	}*/
	/*public ExtendedTweet getExtendedTweet() {
		return extendedTweet;
	}
	public void setExtendedTweet(ExtendedTweet extendedTweet) {
		this.extendedTweet = extendedTweet;
	}*/
	
}
