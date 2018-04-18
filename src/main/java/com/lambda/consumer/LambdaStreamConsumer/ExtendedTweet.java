package com.lambda.consumer.LambdaStreamConsumer;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class ExtendedTweet implements Serializable {
	
	private static final long serialVersionUID = -4700747380480002734L;
	
	@SerializedName("full_text")
	private String fullText;

	public String getFullText() {
		return fullText;
	}

	public void setFullText(String fullText) {
		this.fullText = fullText;
	}

	@Override
	public String toString() {
		return getFullText();
	}
}
