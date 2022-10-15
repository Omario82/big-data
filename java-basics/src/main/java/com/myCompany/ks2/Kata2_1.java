package com.myCompany.ks2;

import com.myCompany.Launch;
import org.apache.spark.api.java.*;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

public class Kata2_1 {
    public static void main(String[] args) throws IOException {
        SparkSession spark = SparkSession.builder().appName("Java-Basics").master("local[*]")
                .getOrCreate();
        // creating java spark context
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

        // Read File from local filesystem and create an RDD
        String pathToDataFile = Paths.get(Launch.absolut_path_to_directory, "LICENSE").toString();
        JavaRDD<String> data = jsc.textFile(pathToDataFile);
        // split words against word delimiters
        JavaRDD<String> elements = data.flatMap(content -> Arrays.asList(content.split("[;\\s]")).iterator());
        // map operation to index words
        JavaPairRDD<String, Integer> paires = elements.mapToPair(element -> new Tuple2(element, 1));
        //reduce operation to count word
        JavaPairRDD<String, Integer> counts = paires.reduceByKey((a,b) -> a+b);
        // display results
        counts.foreach(l -> System.out.println(l));

        spark.stop();
    }
}
