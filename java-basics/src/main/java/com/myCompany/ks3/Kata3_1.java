package com.myCompany.ks3;

import com.myCompany.Launch;
import org.apache.commons.lang.WordUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.UserDefinedFunction;
import org.apache.spark.sql.types.DataTypes;

import java.io.IOException;
import java.nio.file.Paths;

import static org.apache.spark.sql.functions.*;

public class Kata3_1 {
    public static void main(String[] args) throws IOException {
        SparkSession spark = SparkSession.builder().appName("Java-Basics").master("local[*]")
                .getOrCreate();
        // Read Data from CSV file
        String pathToDataFile = Paths.get(Launch.path_to_directory, "SalesJan2009.csv").toString();

        Dataset<Row> sales = spark.read()
                .option("sep", ";")
                .option("header", "true")
                .option("inferSchema", "true")
                .csv(pathToDataFile);
        sales.createOrReplaceTempView("sales");
        // Show the content
        sales.show();
        // Show the schema
        sales.printSchema();

        // register the udf
        UserDefinedFunction upperUDF = udf((String s) -> s.toUpperCase(), DataTypes.StringType);
        spark.sqlContext().udf().register("toCamelCase", upperUDF);
        // Transform the dataframe
        Dataset<Row> namesSql = spark.sql("SELECT toCamelCase(Name) as upper, Name FROM sales");
        // where Name not in (',',' ',';','{','}','(',')','\n','\t')
        namesSql.show();
        // save the dataframe
        String pathToNames = Paths.get(Launch.path_to_directory, "names").toString();
        System.out.println("path: "+pathToNames);
        namesSql.write().format("csv").option("header",true).option("sep",";").mode(SaveMode.Overwrite).save("names");
        //namesSql.write().format("parquet").mode(SaveMode.Overwrite).save(pathToNames);

        spark.stop();
    }
}
