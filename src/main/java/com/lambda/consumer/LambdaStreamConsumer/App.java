package com.lambda.consumer.LambdaStreamConsumer;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.*;
import scala.Tuple2;

import java.util.*;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;
import static com.datastax.spark.connector.japi.CassandraJavaUtil.mapToRow;

/**
 * Lambda Stream Consumer Application
 */
public class App {

    //default values
    private static String brokers = "localhost:9092";
    private static String groupId = "tweets-ml-demo";
    private static String topic = "tweets-ml-raw";

    public static void main(String[] args) {
        if (args.length == 3) {
            brokers = args[0];
            groupId = args[1];
            topic = args[2];
        }
        SparkConf sparkConf = new SparkConf().setMaster("local[2]").setAppName(
                "tweets-ml-raw").setMaster("local[*]");

        // Create the context with 2 seconds batch size
        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf,
                new Duration(2000));

        Set<String> topicsSet = new HashSet<>(Arrays.asList(topic.split(",")));


        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", brokers);
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        kafkaParams.put("group.id", groupId);
        kafkaParams.put("auto.offset.reset", "earliest"); //change to latest
        kafkaParams.put("enable.auto.commit", false);

        // Create direct kafka stream with brokers and topics
        JavaInputDStream<ConsumerRecord<String, String>> stream =
                KafkaUtils.createDirectStream(
                        jssc,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.<String, String>Subscribe(topicsSet, kafkaParams)
                );

        // topic_name, data
        JavaDStream<String> tweets = stream.map(ConsumerRecord::value);

        tweets.print();

        tweets.foreachRDD(new VoidFunction<JavaRDD<String>>() {

            @Override
            public void call(JavaRDD<String> tweetRDD) throws Exception {
                SparkSession session = SparkSession.builder()
                        .config(tweetRDD.context().getConf()).getOrCreate();

                JavaRDD<Tweet> rowRDD = tweetRDD.map(new Function<String, Tweet>() {

                    @Override
                    public Tweet call(String tweetJson) throws Exception {
                        Gson gson = new Gson();
                        Tweet tweet = gson.fromJson(tweetJson, Tweet.class);
                        String sentiment = "";
                        if (tweet.getLang().equals("en")) {
                            sentiment = String.valueOf(SentimentUtils.calculateWeightedSentimentScore(tweet.getText()));
                        }
                        System.out.println("-------Sentiment-----------" + sentiment);
                        tweet.setSentiment(sentiment);
                        return tweet;
                    }
                });
                Dataset<Row> wordsDataFrame = session.createDataFrame(rowRDD, Tweet.class);
                //wordsDataFrame.createOrReplaceTempView("twitter");
                wordsDataFrame.write().mode(SaveMode.Append).saveAsTable("twitter");
                session.sql("select * from twitter").show(); // 20 rows

                //persist data into cassandra
                javaFunctions(rowRDD).writerBuilder(
                        "gis", "tweetlive", mapToRow(Tweet.class)).saveToCassandra();

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
