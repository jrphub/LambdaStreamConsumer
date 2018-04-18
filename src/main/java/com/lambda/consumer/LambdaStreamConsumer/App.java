package com.lambda.consumer.LambdaStreamConsumer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import kafka.serializer.StringDecoder;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import scala.Tuple2;

import com.google.gson.Gson;

/**
 * Lambda Stream Consumer Application
 *
 */
public class App {
	public static void main(String[] args) {
		SparkConf sparkConf = new SparkConf().setMaster("local[2]").setAppName(
				"LambdaStreamingConsumer");

		// Create the context with 2 seconds batch size
		JavaStreamingContext jssc = new JavaStreamingContext(sparkConf,
				new Duration(2000));

		String brokers = "localhost:9092";
		String topics = "lambda-demo";
		

		Set<String> topicsSet = new HashSet<>(Arrays.asList(topics.split(",")));
		Map<String, String> kafkaParams = new HashMap<>();
		kafkaParams.put("metadata.broker.list", brokers);

		// Create direct kafka stream with brokers and topics
		JavaPairInputDStream<String, String> messages = KafkaUtils
				.createDirectStream(jssc, String.class, String.class,
						StringDecoder.class, StringDecoder.class, kafkaParams,
						topicsSet);

		// topic_name, data
		JavaDStream<String> lines = messages
				.map(new Function<Tuple2<String, String>, String>() {
					@Override
					public String call(Tuple2<String, String> tuple2) {
						return tuple2._2();
					}
				});

		lines.print();

		lines.foreachRDD(new VoidFunction<JavaRDD<String>>() {
			
			@Override
			public void call(JavaRDD<String> tweetRDD) throws Exception {
				SparkSession session = SparkSession.builder()
						.config(tweetRDD.context().getConf()).getOrCreate();
				JavaRDD<Tweet> rowRDD = tweetRDD.map(new Function<String, Tweet>() {

					@Override
					public Tweet call(String tweetStr) throws Exception {
						Gson gson = new Gson();
						Tweet tweet = gson.fromJson(tweetStr, Tweet.class);
						return tweet;
					}
				});
				Dataset<Row> wordsDataFrame = session.createDataFrame(rowRDD, Tweet.class);
				//wordsDataFrame.createOrReplaceTempView("twitter");
				wordsDataFrame.write().mode(SaveMode.Append).saveAsTable("twitter");
				session.sql("select * from twitter").show();
				
			}
			
			
		});
		
		jssc.start();
		try {
			jssc.awaitTermination();
		} catch (InterruptedException e) {
			e.printStackTrace();
			jssc.close();
		}

	}
}
