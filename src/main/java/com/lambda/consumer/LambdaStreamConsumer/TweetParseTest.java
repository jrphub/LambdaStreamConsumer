package com.lambda.consumer.LambdaStreamConsumer;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lambda.consumer.beans.Tweet;
import org.datanucleus.util.StringUtils;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class TweetParseTest {

	public static void main(String[] args) throws FileNotFoundException {
		//String tweetStr = "{\"created_at\":\"Sat Apr 07 17:00:06 +0000 2018\",\"id\":982664323918192600,\"id_str\":\"982664323918192640\",\"text\":\"ADVENTURES OF REGINA SEASON 1, EPISODE 1 - NEW MOVIE | 2018 LATEST NIGER... https://t.co/CBMm85WGM6 via @YouTube #Traffic #Trending #BBNajia\",\"truncated\":false,\"in_reply_to_status_id\":null,\"in_reply_to_status_id_str\":null,\"in_reply_to_user_id\":null,\"in_reply_to_user_id_str\":null,\"in_reply_to_screen_name\":null,\"user\":{\"id\":563904163,\"id_str\":\"563904163\",\"name\":\"Kleanson\",\"screen_name\":\"cleanson203\",\"location\":null,\"url\":\"http://www.mynollygist.com\",\"description\":\"a web Master, blogger , youtuber and fun like person, film Maker\",\"translator_type\":\"none\",\"protected\":false,\"verified\":false,\"followers_count\":133,\"friends_count\":161,\"listed_count\":0,\"favourites_count\":146,\"statuses_count\":285,\"created_at\":\"Thu Apr 26 17:38:13 +0000 2012\",\"utc_offset\":null,\"time_zone\":null,\"geo_enabled\":false,\"lang\":\"en\",\"contributors_enabled\":false,\"is_translator\":false,\"profile_background_color\":\"8B542B\",\"profile_background_tile\":false,\"profile_link_color\":\"9D582E\",\"profile_sidebar_border_color\":\"D9B17E\",\"profile_sidebar_fill_color\":\"EADEAA\",\"profile_text_color\":\"333333\",\"profile_use_background_image\":true,\"default_profile\":false,\"default_profile_image\":false,\"following\":null,\"follow_request_sent\":null,\"notifications\":null},\"geo\":null,\"coordinates\":null,\"place\":null,\"contributors\":null,\"is_quote_status\":false,\"quote_count\":0,\"reply_count\":0,\"retweet_count\":0,\"favorite_count\":0,\"entities\":{\"hashtags\":[{\"text\":\"Traffic\",\"indices\":[113,121]},{\"text\":\"Trending\",\"indices\":[122,131]},{\"text\":\"BBNajia\",\"indices\":[132,140]}],\"urls\":[{\"url\":\"https://t.co/CBMm85WGM6\",\"expanded_url\":\"https://youtu.be/m2ymhXointE\",\"display_url\":\"youtu.be/m2ymhXointE\",\"indices\":[76,99]}],\"user_mentions\":[{\"screen_name\":\"YouTube\",\"name\":\"YouTube\",\"id\":10228272,\"id_str\":\"10228272\",\"indices\":[104,112]}],\"symbols\":[]},\"favorited\":false,\"retweeted\":false,\"possibly_sensitive\":false,\"filter_level\":\"low\",\"lang\":\"en\",\"timestamp_ms\":\"1523120406499\"}";
		
		Gson gson = new Gson();
		JsonReader reader = new JsonReader(new FileReader("/home/jrp/workspace_1/LambdaStreamConsumer/src/main/resources/tweet.json"));
		//Tweet tweet = gson.fromJson(tweetStr, Tweet.class);
		//System.out.println(tweet.getText());
		Tweet tweet = gson.fromJson(reader, Tweet.class);
		tweet.setFulltext(tweet.getRetweeted_status().getExtended_tweet().getFull_text());
		String[] locationArr = tweet.getUser().getLocation().split(",");
		if (!StringUtils.isEmpty(locationArr[0])) {
			tweet.setLocation(locationArr[0]);
		}

		if (!StringUtils.isEmpty(locationArr[1])) {
			tweet.setCountry(locationArr[1].trim());
		}
		System.out.println(tweet.toString());

	}
}
