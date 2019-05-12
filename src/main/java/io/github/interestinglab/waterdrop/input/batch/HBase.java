package io.github.interestinglab.waterdrop.input.batch;

import com.typesafe.config.Config;
import io.github.interestinglab.waterdrop.apis.BaseStaticInput;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

/**
 * TODO:
 *  1. Config should be replaced by waterdrop typesafe config.
 *  2. Tuple2<Object, String> checkConfig() ==> Boolean
 * */
public class HBase extends BaseStaticInput {

    private Config config;

    @Override
    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public Config getConfig() {
        return this.config;
    }

    @Override
    public Tuple2<Object, String> checkConfig() {
        return null;
    }

    @Override
    public Dataset<Row> getDataset(SparkSession spark) {
        // TODO:
        return spark.emptyDataFrame();
    }
}
