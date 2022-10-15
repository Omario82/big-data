package com.myCompany;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import com.myCompany.processing.Transformation;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Launch  {
    public static String path_to_directory =".";

    // folder in your datalake home path
    public static String absolut_path_to_directory = "C:\\Tools\\spark\\spark-3.2.1-bin-hadoop3.2";

    public static String pathToDataFile = Paths.get(absolut_path_to_directory, "LICENSE").toString();

    public static void main(String[] args) throws IOException {
        SparkSession spark = SparkSession.builder()
                .appName("Java-Basics")
                .master("local[*]")
                .getOrCreate();
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

        Transformation.wordCount(jsc);

        spark.stop();
    }

    private static void introduction(SparkSession spark) {
        // Read File from local filesystem and create an RDD
        JavaRDD<Row> data = spark.read()
                .text(pathToDataFile)
                .javaRDD();

        // display RDD’s content
        data.foreach(l -> System.out.println(l));
        System.out.println("# lines: "+ data.count());

        // creating java spark context
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());

        // Create an RDD through Parallelized Collection
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        JavaRDD<Integer>  numbersDataRDD = jsc.parallelize(numbers);

        numbersDataRDD.foreach(l -> System.out.println(l));
    }

    private static void writing_code(SparkSession spark) {
        StructType schema = new StructType()
                .add(new StructField("id", DataTypes.LongType, false, Metadata.empty()))
                .add(new StructField("name", DataTypes.StringType, false, Metadata.empty()))
                .add(new StructField("age", DataTypes.IntegerType, true, Metadata.empty()));
        Dataset<Row> persons = spark.read().schema(schema).json("file.json");

        Dataset<Row> personsInfer = spark.read().schema(schema).option("inferSchema", true)
                .json("file.json");

    }
}
