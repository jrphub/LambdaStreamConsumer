package com.lambda.consumer.LambdaStreamConsumer;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Tweet implements Serializable {

    @SerializedName("id_str")
    private String id;

    @SerializedName("text")
    private String text;

    @SerializedName("created_at")
    private String createdat;

    @SerializedName("source")
    private String source;
	
	/*@SerializedName("coordinates")
	private String coordinates;*/

    @SerializedName("lang")
    private String lang;

    private String sentimate;

    //private TweetUser user;

    //private String extendedText;

    public String getCreatedat() {
        return createdat;
    }

    public void setCreatedat(String createdat) {
        this.createdat = createdat;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSentimate() {
        return sentimate;
    }

    public void setSentimate(String sentimate) {
        this.sentimate = sentimate;
    }
}
