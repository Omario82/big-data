package com.myCompany.ks1

import com.myCompany.Launch
import org.apache.spark.sql.SparkSession

import java.nio.file.Paths

object Kata1_1 {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder.appName("scala-Basics").master("local[*]")
      .getOrCreate

    // Read File from local filesystem and create an RDD
    val pathToDataFile = Paths.get(Launch.absolut_path_to_directory, "LICENSE").toString
    val data = spark.read.textFile(pathToDataFile).rdd

    // display RDD’s content
    data.foreach(println)
    System.out.println("# lines: " + data.count)

    spark.stop()
  }
}
