# waterdrop-input-hbase
waterdrop input plugin --> hbase input

调整pom.xml 中`hbase.version` 的不同版本，来适应客户的不同hbase版本。不同版本的hbase，rpc协议不兼容，不可混着版本用。

TODO:

1. 这里使用的spark hbase connector不支持limit N的pushdown，还有聚合的pushdown，可尝试替换为：https://github.com/Huawei-Spark/Spark-SQL-on-HBase

2. 支持cluster模式：需要更改waterdrop api，将[Common](https://github.com/InterestingLab/waterdrop/blob/master/waterdrop-core/src/main/scala/io/github/interestinglab/waterdrop/config/Common.scala)中的获取文件的API放到waterdrop api中发布出来，然后在这个插件中使用，才能支持cluster模式中访问需要的插件文件。

3. 允许配置其他参数，需要waterdrop api提供访问sub parameter的支持，[hbase input 可选参数](https://github.com/hortonworks-spark/shc/blob/9f53dd29b7032ffde24507c3c3194559e95c4695/core/src/main/scala/org/apache/spark/sql/execution/datasources/hbase/HBaseRelation.scala#L77)
