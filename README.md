# waterdrop-input-hbase
waterdrop input plugin --> hbase input

调整pom.xml 中`hbase.version` 的不同版本，来适应客户的不同hbase版本。不同版本的hbase，rpc协议不兼容，不可混着版本用。

TODO:

1. 这里使用的spark hbase connector不支持limit N的pushdown，还有聚合的pushdown，可尝试替换为：https://github.com/Huawei-Spark/Spark-SQL-on-HBase

2. 支持cluster模式：需要更改waterdrop api，将Common中的获取文件的API放到waterdrop api中发布出来，然后在这个插件中使用，才能支持cluster模式中访问需要的插件文件。
