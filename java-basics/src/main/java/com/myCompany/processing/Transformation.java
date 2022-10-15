package com.myCompany.processing;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

import static com.myCompany.Launch.pathToDataFile;

public class Transformation {
    public static void wordCount(JavaSparkContext jsc) {
        // Read File from local filesystem and create an RDD
        JavaRDD<String> data = jsc.textFile(pathToDataFile);
        // split words against word delimiters
        JavaRDD<String> elements = data.flatMap(content -> Arrays.asList(content.split("[;\\s]")).iterator());
        // map operation to index words
        JavaPairRDD<String, Integer> paires = elements.mapToPair(element -> new Tuple2(element, 1));
        //reduce operation to count word
        JavaPairRDD<String, Integer> counts = paires.reduceByKey((a,b) -> a+b);
        // display results
        counts.foreach(l -> System.out.println(l));
    }
}
