package com.lambda.consumer.beans;

import java.io.Serializable;

import com.datastax.driver.mapping.annotations.Transient;
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
	
    @SerializedName("lang")
    private String lang;

    private int sentiment;

    @Transient
    private TweetUser user;

    @Transient
    private RetweetedStatus retweeted_status;

    private String fulltext;
    private String location;
    private String country;


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

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSentiment() {
        return sentiment;
    }

    public void setSentiment(int sentiment) {
        this.sentiment = sentiment;
    }

    public TweetUser getUser() {
        return user;
    }

    public void setUser(TweetUser user) {
        this.user = user;
    }

    public RetweetedStatus getRetweeted_status() {
        return retweeted_status;
    }

    public void setRetweeted_status(RetweetedStatus retweeted_status) {
        this.retweeted_status = retweeted_status;
    }

    public String getFulltext() {
        return fulltext;
    }

    public void setFulltext(String fulltext) {
        this.fulltext = fulltext;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String toString() {
        return "From : " + getLocation() + ", " + getCountry() + "\nTweet : " + getFulltext();
    }
}
