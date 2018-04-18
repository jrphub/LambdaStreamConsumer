package com.lambda.consumer.LambdaStreamConsumer;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class TweetUser implements Serializable {
	
	private static final long serialVersionUID = -1169946739935538169L;
	
	private String name;
	@SerializedName("screen_name")
	private String screenName;
	private String url;
	private String description;
	
	@SerializedName("followers_count")
	private String followersCount;
	
	@SerializedName("friends_count")
	private String friendsCount;
	
	@SerializedName("profile_image_url_https")
	private String profileImageUrlHttps;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFollowersCount() {
		return followersCount;
	}

	public void setFollowerCount(String followersCount) {
		this.followersCount = followersCount;
	}

	public String getFriendsCount() {
		return friendsCount;
	}

	public void setFriendsCount(String friendsCount) {
		this.friendsCount = friendsCount;
	}

	public String getProfileImageUrlHttps() {
		return profileImageUrlHttps;
	}

	public void setProfileImageUrlHttps(String profileImageUrlHttps) {
		this.profileImageUrlHttps = profileImageUrlHttps;
	}

}
