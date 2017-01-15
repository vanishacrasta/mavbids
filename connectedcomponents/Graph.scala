package edu.uta.cse6331

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import scala.collection._

case class Vertex ( tag: Short, group: Long, VID: Long, adjacent: List[Long] )
      extends Serializable {}


object Graph 
{
  def main(args: Array[ String ]) {
		val conf = new SparkConf().setAppName("Graph")
		val sc = new SparkContext(conf)
		val map1 = sc.textFile(args(0)).map( line => { val a = line.split(",")
		val adjacent1 = new mutable.ListBuffer[Long]()
		for( i <- 1 to a.length - 1){
			adjacent1 += a(i).toLong
		}
		val adj = adjacent1.toList
		(a(0).toLong, adj) })
		var value1 =map1.map( map1 => (map1._1,(0.toShort,map1._1,map1._1,map1._2)) )
		map1.saveAsTextFile("output1")
		value1.saveAsTextFile("output2")
		var m = 0
		while( m < 5 )
	 {
			val union_value = value1.values.map(value1 => (value1._3,value1))
			val union_output = union_value.union(value1.flatMap(value1=>for(m <-value1._2._4)
			yield{
			(m,(1.toShort,value1._2._2,0.toLong,List(0.toLong)))
			})).groupByKey();
			var adjacent2:List[Long]=Nil
			val vertex_vid=union_output.map(union_output => {var minimum=Long.MaxValue
            for( n <- union_output._2)
				{ 
					if(n._1==0)
		       	   adjacent2=n._4
                   	minimum=math.min(minimum,n._2)
                }
			(minimum,(0.toShort,minimum,union_output._1,adjacent2)) 
             }
            )       
			value1 = vertex_vid
			m = m + 1
	}
	val result=value1.map(value1 => (value1._1,1.toLong)).reduceByKey(_+_)
    result.collect().foreach(println)
	result.saveAsTextFile("final Output")
	sc.stop()
	}
    }