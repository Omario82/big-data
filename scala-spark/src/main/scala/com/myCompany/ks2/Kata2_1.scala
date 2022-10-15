package com.myCompany.ks2

import com.myCompany.Launch
import org.apache.spark.sql.SparkSession
import java.nio.file.Paths

object Kata2_1 {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder.appName("scala-Basics").master("local[*]")
      .getOrCreate

    // Read File from local filesystem and create an RDD
    val pathToDataFile = Paths.get(Launch.absolut_path_to_directory, "LICENSE").toString
    // Read File from local filesystem and create an RDD
    val data = spark.read.textFile(pathToDataFile).rdd
    // split words against word delimiters
    val elements = data.flatMap(l => l.split("[;\\s]"))
    // map operation to index words
    val paires = elements.map( element => (element, 1))
    //reduce operation to count word
    val counts = paires.reduceByKey((a,b) => a+b)
    // display results
    counts.foreach(println)

    spark.stop()
  }
}
