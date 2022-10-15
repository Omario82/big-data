package com.myCompany

import com.myCompany.processing.Transformation
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.types.{DataTypes, Metadata, StructField, StructType}

import java.nio.file.Paths;

object Launch{
  val path_to_directory = "."

  // folder in your datalake home path
  val absolut_path_to_directory = "C:\\Tools\\spark\\spark-3.2.1-bin-hadoop3.2"


  val pathToDataFile = Paths.get(absolut_path_to_directory, "LICENSE").toString

  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder
      .appName("scala-Basics")
      .master("local[*]").getOrCreate

    Transformation.wordCount(spark)

    spark.stop()
  }

  private def introduction(spark: SparkSession) = {
    // Read File from local filesystem and create an RDD
    val data = spark.read.textFile(pathToDataFile).rdd

    // display RDD’s content
    data.foreach(println)
    System.out.println("# lines: " + data.count)

    // Create an RDD through Parallelized Collection
    val numbers = Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val numbersDataRDD: RDD[Int] = spark.sparkContext.parallelize(numbers)
    val df = spark.createDataFrame(numbersDataRDD, null);
    import spark.implicits._

    val ds2 = df.as[String]
    
    val df= data.toDF()
    val ds = df.as[String].toDF()
    numbersDataRDD.foreach(println)
  }

  private def writing_code(spark: SparkSession): Unit = {
    val schema = new StructType().add(new StructField("id", DataTypes.LongType, false, Metadata.empty)).add(new StructField("name", DataTypes.StringType, false, Metadata.empty)).add(new StructField("age", DataTypes.IntegerType, true, Metadata.empty))
    val persons = spark.read.schema(schema).json("file.json")
    val personsInfer: DataFrame = spark.read.schema(schema).option("inferSchema", true).json("file.json")
    val jsonFileDF: DataFrame = spark.read.json("path_file_parquet")

  }
}
