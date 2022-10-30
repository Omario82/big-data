drop table IF EXISTS zipcodes_avro;

CREATE TABLE zipcodes_avro ( RecordNumber int, Country string,
City string, Zipcode int, State string)
COMMENT 'zipcodes details'     STORED AS AVRO
TBLPROPERTIES ( 'avro.schema.literal'='${hivevar:schema}');

INSERT OVERWRITE TABLE zipcodes_avro SELECT RecordNumber, Country, City, Zipcode, State from zipcodes_textfile;
