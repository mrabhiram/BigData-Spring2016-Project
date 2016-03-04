import org.apache.spark.SparkConf
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import spire.std.string


object TwitterStreaming {

  def main(args: Array[String]) {

    val filters = Array("KCA","Poissons","bbcqt","Directioners","gamedev");

    // Set the system properties so that Twitter4j library used by twitter stream
    // can use them to generate OAuth credentials

    System.setProperty("twitter4j.oauth.consumerKey", "HDGHDFSKJDFH2746jshdsouhds")
    System.setProperty("twitter4j.oauth.consumerSecret", "eg654fghHFHFGF654GFGs6se654wshsdguygyui")
    System.setProperty("twitter4j.oauth.accessToken", "365436458-9FHGtr6dfhgwDrFvDhbjsfgsFRhhdjshdskskhw")
    System.setProperty("twitter4j.oauth.accessTokenSecret", "8VGjhjdsywegakjsuw6gwvHGFShwdbsbjshjdhsdssdsd")

    //Create a spark configuration with a custom name and master
    // For more master configuration see  https://spark.apache.org/docs/1.2.0/submitting-applications.html#master-urls
    val sparkConf = new SparkConf().setAppName("STweetsApp").setMaster("local[*]")
    //Create a Streaming COntext with 2 second window
    val ssc = new StreamingContext(sparkConf, Seconds(2))
    //Using the streaming context, open a twitter stream (By the way you can also use filters)
    //Stream generates a series of random tweets
    val stream = TwitterUtils.createStream(ssc, None, filters)
       // stream.print()
    //Map : Retrieving Hash Tags
    val hashTags = stream.flatMap(status => status.getText.split(" ").filter(_.startsWith("#")))

    //Finding the top hash Tags on 30 second window
    val topCounts30 = hashTags.map((_, 1)).reduceByKeyAndWindow(_ + _, Seconds(30))
      .map{case (topic, count) => (count, topic)}
      .transform(_.sortByKey(false))
    //Finding the top hash Tgas on 10 second window
    val topCounts10 = hashTags.map((_, 1)).reduceByKeyAndWindow(_ + _, Seconds(60))
      .map{case (topic, count) => (count, topic)}
      .transform(_.sortByKey(false))

    // Print 30 popular hashtags
//    topCounts30.foreachRDD(rdd => {
//      val topList = rdd.take(10)
//      println("\nPopular topics in last 30 seconds (%s total):".format(rdd.count()))
//      topList.foreach{case (count, tag) => println("%s (%s tweets)".format(tag, count))}
//    })

    topCounts10.foreachRDD(rdd => {
      val topList = rdd.take(10)
      println("\nPopular topics in last 10 seconds (%s total):".format(rdd.count()))
      topList.foreach{case (count, tag) => println("%s (%s tweets)".format(tag, count))}
      var s:String = "tags \n"
      topList.foreach{case(word,count)=>{
        s+=word+" : "+count+"\n"
      }}
      SocketClient.sendCommandToRobot(s)
    })
     ssc.start()

    ssc.awaitTermination()
  }
}
