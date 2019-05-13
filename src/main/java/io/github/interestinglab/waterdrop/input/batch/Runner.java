package io.github.interestinglab.waterdrop.input.batch;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.github.interestinglab.waterdrop.apis.BaseStaticInput;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

public class Runner {

    public static void main(String[] args) throws Exception {

        SparkSession sparkSession = SparkSession.builder().getOrCreate();
        BaseStaticInput input = new HBase();
        Config config = ConfigFactory.empty();
        input.setConfig(config);
        Tuple2<Object, String> status = input.checkConfig();
        if (Boolean.valueOf(status._1().toString()) == false) {
            System.out.println(status._2());
            System.exit(-1);
        }

        input.prepare(sparkSession);
        Dataset<Row> dataset = input.getDataset(sparkSession);

        dataset.printSchema();
        dataset.show(false);
    }
}
