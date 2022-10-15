package com.myCompany.ks2;

import com.myCompany.Launch;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import java.io.IOException;
import java.nio.file.Paths;

public class Kata2_2 {
    public static void main(String[] args) throws IOException {
        // creating java spark context
        SparkConf sparkConf = new SparkConf().setAppName("Java-Advanced").setMaster("local[*]");
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);

        String pathToDataFile = Paths.get(Launch.path_to_directory, "SalesJan2009.csv").toString();
        JavaRDD<String> lines = jsc.textFile(pathToDataFile);

        lines.take(10).forEach(e-> System.out.println(e));

        JavaRDD<String> linesNoHeader = lines.filter(e -> !e.startsWith("Transaction_date"));

        System.out.println(String.format("initial number of lines %d", lines.count()));

        String csv_sep = ";";
        int price_index = 2;
        JavaRDD<String> cleanedData = linesNoHeader
                .filter(e->e.split(csv_sep)[price_index].matches("^\\d+$"));

        System.out.println(String.format("final number of lines %d", cleanedData.count()));

        jsc.stop();
    }
}
