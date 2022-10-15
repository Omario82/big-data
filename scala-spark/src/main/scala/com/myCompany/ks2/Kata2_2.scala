package com.myCompany.ks2

import com.myCompany.Launch
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

import java.nio.file.Paths

object Kata2_2 {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder.appName("scala-Basics").master("local[*]")
      .getOrCreate

    val pathToDataFile: String = Paths.get(Launch.path_to_directory, "SalesJan2009.csv").toString
    val lines: RDD[String] = spark.read.textFile(pathToDataFile).rdd

    lines.take(10).foreach((e: String) => System.out.println(e))

    val linesNoHeader: RDD[String] = lines.filter((e: String) => !(e.startsWith("Transaction_date")))

    println(s"initial number of lines, ${lines.count}")

    val csv_sep: String = ";"
    val price_index: Int = 2
    val cleanedData: RDD[String] = linesNoHeader.filter((e: String) => e.split(csv_sep)(price_index).matches("^\\d+$"))
    println(s"final number of lines, ${cleanedData.count}")

    spark.stop()
  }
}
