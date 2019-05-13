package io.github.interestinglab.waterdrop.input.batch;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.typesafe.config.Config;
import io.github.interestinglab.waterdrop.apis.BaseStaticInput;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.execution.datasources.hbase.HBaseTableCatalog;
import scala.Tuple2;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * TODO:
 *  1. Config should be replaced by waterdrop typesafe config.
 *  2. Tuple2<Object, String> checkConfig() ==> Boolean
 *  3. waterdrop-apis in mvn repo, exclude spark jar.
 *  4. plugin version info
 *  5. waterdrop runner for plugin.
 *  6. assembly zip dir name
 *  7. get schema string from plugin dir[using Waterdrop Common API].
 *  8. [****] added Try deadline.
 * */
public class HBase extends BaseStaticInput {

    private Config config;
    private String catalogJsonString;

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
        if (! this.config.hasPath("catalog_file")) {
            return Tuple2.apply(false, "please specify schema of hbase table by parameter [catalog_file]");
        }

        // TODO: (1) get schema string from plugin dir[using Waterdrop Common API].
        this.catalogJsonString = "";
        try {
            this.catalogJsonString = FileUtils.readFileToString(new File(config.getString("catalog_file")));
        } catch (IOException e) {
            return Tuple2.apply(false, "Cannot get schema of hbase table by parameter [catalog_file], file maybe not exists or cannot be read");
        }

        if (StringUtils.isBlank(this.catalogJsonString)) {
            return Tuple2.apply(false, "Cannot get schema of hbase table by parameter [catalog_file], file content is empty");
        }

        Gson gson = new Gson();
        try {
            JsonObject jsonObject = gson.fromJson(this.catalogJsonString, JsonObject.class);
        } catch (JsonSyntaxException e) {
            return Tuple2.apply(false, "Cannot get schema of hbase table by parameter [catalog_file], file content is not a valid json string");
        }

        return Tuple2.apply(true, "");
    }

    @Override
    public Dataset<Row> getDataset(SparkSession spark) {

        Map<String, String> options = Collections.singletonMap(
                HBaseTableCatalog.tableCatalog(),
                this.catalogJsonString);
        return spark.read().options(options)
                .format("org.apache.spark.sql.execution.datasources.hbase")
                .load();
    }
}
