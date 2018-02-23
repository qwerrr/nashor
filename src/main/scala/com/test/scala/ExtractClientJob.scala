import java.util.regex.Pattern

import com.alibaba.fastjson.JSON
import org.apache.spark.sql.{SaveMode, SparkSession}


/**
  * @descriptions .
  * @date 17-9-19
  * @author wangliping
  */
object ExtractClientJob {
  val regex = "^.*clientid\\\\+\":\\\\+\"(\\w+)\\\\+.*$"
  val pattern = Pattern.compile(regex)

  def main(args: Array[String]): Unit = {

    val inpath = getParam(args, "-inpath")
    val outpath = getParam(args, "-outpath")
    val key = getParam(args, "-key")
    val (array, k) = parseKey(key)
    val spark = SparkSession.builder().appName("wangliping#" + inpath).getOrCreate()
    spark.sparkContext.hadoopConfiguration.set("fs.s3n.awsAccessKeyId", "AKIAJKDBKLW3G32VTDXA")
    spark.sparkContext.hadoopConfiguration.set("fs.s3n.awsSecretAccessKey", "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")
    spark.sparkContext.hadoopConfiguration.set("fs.s3n.endpoint", "s3-ap-southeast-1.amazonaws.com")
    spark.sparkContext.hadoopConfiguration.set("io.compression.codecs", "io.sensesecure.hadoop.xz.XZCodec")
    val file = spark.read.textFile(inpath)
    import spark.implicits._
    val df = file.rdd.map(line => parseLine(line, array, k)).filter(line => !isBlank(line)).distinct.toDF("cid")
    df.repartition(1).write.mode(SaveMode.Overwrite).parquet(outpath)
  }

  def parseKey(key: String): (Array[String], String) = {
    val arr = key.split(":")
    if (isBlank(arr(0)))
      (null, arr(1))
    else
      (arr(0).split("-"), arr(1))
  }

  def parseLine(line: String, array: Array[String], key: String): String = {
    if (null == line || line.isEmpty) return null
    var ret: String = null
    try {
      val arr = line.split("\t")
      val message = JSON.parseObject(arr(arr.length - 1)).getString("message")
      var json = JSON.parseObject(message)
      if (array != null && array.length > 0) {
        val len = array.length - 1
        for (i <- 0 to len)
          json = json.getJSONObject(array(i))
      }
      ret = json.getString(key)
    } catch {
      case e: Throwable => ret = null
    }
    ret
  }

  def parseLine1(line: String, array: Array[String], key: String): String = {
    if (null == line || line.isEmpty) return null
    var ret: String = null
    try {
      if (line.contains("clientid")) {
        if (pattern.matcher(line).matches()) ret = line.replaceAll(regex, "$1")
        else ret = "111111111111"
      }
    } catch {
      case e: Throwable => ret = null
    }
    ret
  }


  def isBlank(param: String): Boolean = {
    param == null || param.isEmpty
  }

  def getParam(args: Array[String], key: String, defaultValue: String = null): String = {
    if (args == null || isBlank(key)) throw new IllegalArgumentException(" args and key is reqired")
    val len = args.length
    for (i <- 0 until len) {
      if (args(i) == key && i + 1 < len) return args(i + 1)
    }
    defaultValue
  }

}
