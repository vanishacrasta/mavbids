package edu.uta.cse6331;
import java.io.IOException;
import java.util.Scanner;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import java.io.*;
import org.apache.hadoop.*;
import java.util.*;
import java.lang.Integer;
import org.apache.hadoop.conf.Configuration;

class ElemM implements Writable {
public short tag;
public double value;
public int index;
ElemM(){}
ElemM(int tag,int j,double v){
index = j;
tag = 0;
value = v;
}

public void write(DataOutput out) throws IOException {
out.writeShort(tag);
out.writeInt(index);
out.writeDouble(value); 
}
@Override
public void readFields(DataInput in) throws IOException {
value=in.readDouble();
index=in.readInt();
}
}

class ElemN implements Writable {
public short tag;
public double value;
public int index;
ElemN(){}
ElemN(int tag,int j,double v){
index = j;
tag = 1;
value = v;
}
public void write(DataOutput out) throws IOException {
out.writeShort(tag);
out.writeInt(index);
out.writeDouble(value); 
}
@Override
public void readFields(DataInput in) throws IOException {
value=in.readDouble();
index=in.readInt();
}
}


class Matrix implements Writable {
    public int tag;
    public int index;
    public double value;

    public Matrix(){}
    
	public Matrix(int t, int i, double v) {
		
	 	tag = t;
    	index = i;
    	value = v;

	}

	public void write ( DataOutput out ) throws IOException {
        out.writeInt(tag);
        out.writeInt(index);
        out.writeDouble(value);
       
    }

    public void readFields ( DataInput in ) throws IOException {
        tag = in.readInt();
        index = in.readInt();
        value = in.readDouble();
    }
}

class Result implements WritableComparable<Result>{
public IntWritable i,k;
Result(){
}
public Result(IntWritable i,IntWritable k){
set(i,k);
}

public IntWritable geti() {
return i;
}

public IntWritable getk() {
return k;
}

public void set(IntWritable i,IntWritable k)
{
this.i=i;
this.k=k;
}

public void readFields(DataInput in) throws IOException {
i.readFields(in);
k.readFields(in);
}

public void write(DataOutput out) throws IOException {
i.write(out);
k.write(out);
}

public String toString() {
return i + " " + k;
}
@Override
public int compareTo(Result index) {
int c = i.compareTo(index.i);
if (c != 0) {
return c;
}
return k.compareTo(index.k);
 }

public boolean equals(Object o)
{
if(o instanceof Result)
{
 Result index = (Result) o;
 return i.equals(index.i) && k.equals(index.k);
  }
   return false;
  }

}

public class Multiply {
Multiply()
{
	}
public static class FirstMapper extends Mapper<Object,Text,IntWritable,Matrix>
{
private int i,j;
private double v;
@Override
public void map(Object key,Text value,Context context) throws  IOException,InterruptedException {
Scanner s = new Scanner(value.toString()).useDelimiter(",");
i=s.nextInt();
j=s.nextInt();
v=s.nextDouble();
System.out.println(i + "," + j +  "," + v);
context.write(new IntWritable(j),new Matrix(0,i,v));
s.close();
}
}

public static class SecondMapper extends Mapper<Object,Text,IntWritable,Matrix>
{
private int i,j;
private double v;
@Override
public void map(Object key,Text value,Context context) throws  IOException,InterruptedException {
Scanner s = new Scanner(value.toString()).useDelimiter(",");
i=s.nextInt();
j=s.nextInt();
v=s.nextDouble();
System.out.println(i + "," + j +  "," + v);
context.write(new IntWritable(i),new Matrix(1,j,v));
s.close();
}
}

public static class FirstReducer extends Reducer<IntWritable,Matrix,Result,DoubleWritable> {
static Vector<Matrix> m1 = new Vector<Matrix>();
static Vector<Matrix> m2 = new Vector<Matrix>();
@Override
public void reduce(IntWritable key,Iterable<Matrix> values,Context context) throws IOException,InterruptedException {
m1.clear();
m2.clear();
for(Matrix mat: values)
{	if(mat.tag==0)
     	{
        m1.add(new Matrix(key.get(),mat.index,mat.value));
}
       	else if(mat.tag==1)
 	{
        m2.add(new Matrix(key.get(),mat.index,mat.value));}
 }
for(Matrix mat1: m1){
for(Matrix mat2: m2) {
System.out.println(mat1.index+" " + mat2.index+" " +mat1.value+" " +mat2.value);
context.write(new Result(new IntWritable(mat1.index),new IntWritable(mat2.index)),new DoubleWritable(mat1.value*mat2.value));
}
}
}
}



public static class MyMapper2 extends Mapper<LongWritable, Text, Text, Text> {
@Override
	public void map(LongWritable ikey, Text ivalue, Context context) throws IOException, InterruptedException {
			
String  [] tvalue1	 = ivalue.toString().split("\\t");
		
  
   String[] strList = tvalue1[0].split(" ");
    
     String key = strList[0] + "," +strList[1];
     String value = tvalue1[1];


     context.write(new Text(key),new Text(value));
}
}
public static class MyReducer2 extends Reducer<Text, Text, Text, Text>
{
@Override 
   public void reduce(Text key, Iterable<Text> ivalue, Context context) throws IOException, InterruptedException {

		String [] key_value = key.toString().split(",");
		String indexi = key_value[0];
		String indexj= key_value[1];
			
		float sum = 0;
		for (Text val : ivalue) {

			String str = val.toString();
			sum += Float.parseFloat(str);
		}
		String result = String.valueOf(sum);
		String multiply_key = indexi+","+indexj;
		String multiply_res = result;
	context.write(new Text(multiply_key),new Text(multiply_res));
}	
}






public static void main (String[] args) throws Exception {
    Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setNumReduceTasks(2);
        job.setJobName("FirstJob");
        job.setNumReduceTasks(2);
        job.setJarByClass(Multiply.class);
     
        job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(Matrix.class);
        
        job.setOutputKeyClass(Result.class);
        job.setOutputValueClass(DoubleWritable.class);
        job.setReducerClass(FirstReducer.class);

        MultipleInputs.addInputPath(job,new Path(args[0]),TextInputFormat.class,FirstMapper.class);
        MultipleInputs.addInputPath(job,new Path(args[1]),TextInputFormat.class,SecondMapper.class);
        TextOutputFormat.setOutputPath(job,new Path(args[2]));
        job.waitForCompletion(true);
        

			Job job2 = Job.getInstance();
 			job2.setJobName("MyJob1");
			job2.setJarByClass(Multiply.class);
			job2.setMapperClass(MyMapper2.class);
			job2.setReducerClass(MyReducer2.class);
			job2.setInputFormatClass(TextInputFormat.class);
	      		job2.setOutputFormatClass(TextOutputFormat.class);
			job2.setOutputKeyClass(Text.class);
			job2.setOutputValueClass(Text.class);
 			FileInputFormat.setInputPaths(job2,new Path(args[2]));
       			FileOutputFormat.setOutputPath(job2,new Path(args[3]));
			System.exit(job2.waitForCompletion(true) ? 0 : 1);


      }

}
