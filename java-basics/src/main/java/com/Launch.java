package com;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;

public class Launch {
    public static void main(String[] args) throws IOException {
        SparkSession spark = SparkSession.builder().appName("Java-Basics").master("local[*]").getOrCreate();
        JavaRDD<Row> lines = spark.read().text("./java-basics/src/main/resources/SalesJan2009.csv").javaRDD();
        System.out.println("wesh "+ lines.count());
        spark.stop();
    }
}
