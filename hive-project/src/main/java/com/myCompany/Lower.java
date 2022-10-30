package com.myCompany;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class Lower extends UDF {
    private Text result = new Text();
    public Text evaluate(Text str) {
        if (str == null) {
            return null;
        }
        result.set(StringUtils.lowerCase(str.toString()));
        return result;
    }
}
