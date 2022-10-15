package com.myCompany.ks1;

import com.myCompany.Launch;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;
import java.nio.file.Paths;

public class Kata1_1 {
    public static void main(String[] args) throws IOException {
        SparkSession spark = SparkSession.builder().appName("Java-Basics").master("local[*]")
                .getOrCreate();

        // Read File from local filesystem and create an RDD
        String pathToDataFile = Paths.get(Launch.absolut_path_to_directory, "LICENSE").toString();
        JavaRDD<Row> data = spark.read().text(pathToDataFile).javaRDD();

        // display RDD’s content
        data.foreach(l -> System.out.println(l));
        System.out.println("# lines: "+ data.count());

        spark.stop();
    }
}
