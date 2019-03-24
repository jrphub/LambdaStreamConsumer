package com.lambda.consumer.beans;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class ExtendedTweet implements Serializable {
	
	private static final long serialVersionUID = -4700747380480002734L;
	
	@SerializedName("full_text")
	private String full_text;

	public String getFull_text() {
		return full_text;
	}

	public void setFull_text(String full_text) {
		this.full_text = full_text;
	}

	@Override
	public String toString() {
		return getFull_text();
	}
}
