package com.myCompany.processing

import com.myCompany.Launch.pathToDataFile
import org.apache.spark.sql.SparkSession

object Transformation {
  def wordCount(spark: SparkSession): Unit ={
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
  }
}
