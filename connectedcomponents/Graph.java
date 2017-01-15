package edu.uta.cse6331;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.*;
import org.apache.hadoop.mapreduce.lib.output.*;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

class Vertex implements Writable {

public short tag;
public long group;
public long VID;
public Vector<Long> adjacent=new Vector<Long>();
public int noofnodes;

public Vertex()
{
}

public Vertex(short t,long g,long vid1,int node,Vector<Long> adj)
{
tag=t;
group=g;
VID=vid1;
noofnodes=node;
adjacent=adj;
}

     
public Vertex(short t,long g)
{
tag=t;
group=g;
}

public void write(DataOutput out) throws IOException
{
out.writeShort(tag);
out.writeLong(group);
out.writeLong(VID);
out.writeInt(noofnodes);
for(Long a: adjacent)
{
out.writeLong(a);
}
}

public void readFields( DataInput  in) throws IOException
{
tag=in.readShort();
group=in.readLong();
VID=in.readLong();
noofnodes=in.readInt();
if(tag==0)
{
int i=0;
adjacent = new Vector<Long>();
while(i<noofnodes)
{
adjacent.add(in.readLong());
i=i+1;
}
 }
}

}


public class Graph {

public static class Mapper1 extends Mapper<Object, Text, LongWritable, Vertex> {
    	
    	private long VID;
    	private short tag = 0;
    	private Vector<Long> adjacent = new Vector<Long>();
    	private int size;
    	@Override
            public void map ( Object key, Text value, Context context )
                            throws IOException, InterruptedException {
    		
	    		Scanner s = new Scanner(value.toString()).useDelimiter(",");
	    			size=0;
	    		VID = s.nextLong();
	    		adjacent.clear();
	    		while(s.hasNext()){		
	    			adjacent.add(s.nextLong());
	    			size=size+1;	    			
	    		}
	    		context.write(new LongWritable(VID),new Vertex(tag,VID,VID,size,adjacent));
	    		s.close();
               
            }
    }
	
	 public static class Mapper2 extends Mapper<LongWritable, Vertex, LongWritable, Vertex> {
		 	private short tag = 1;
		 	
		 	@Override
	        public void map ( LongWritable key, Vertex value, Context context )
	                        throws IOException, InterruptedException {
		 		
	        	context.write(new LongWritable(value.VID), value);
	        		        	
	        	for( Long n : value.adjacent){
	        		context.write(new LongWritable(n), new Vertex(tag, value.group));
	        	}
	        	
	        }
	    }
	 
	 public static class Reducer1 extends Reducer<LongWritable, Vertex, LongWritable, Vertex> {
		 private Long m;
		 private short tag = 0;
		 private Vertex vx = null;
	    	@Override
	    	
	    	public void reduce ( LongWritable key, Iterable<Vertex> values, Context context )
	                           throws IOException, InterruptedException {
	    		m=Long.MAX_VALUE;
	    		for(Vertex v:values)
	    		{
	    			if(v.tag == 0)
	    			{
	    				vx= v;
	    			}
	    			m = Math.min(m, v.group);
	    		}
	    		context.write(new LongWritable(m), new Vertex(tag,m,key.get(),vx.adjacent.size(),vx.adjacent));
	    
	    	}
	    	
	    }

	 public static class Mapper3 extends Mapper<LongWritable, Vertex, LongWritable, IntWritable> {
	        @Override
	        public void map ( LongWritable key, Vertex value, Context context )
	                        throws IOException, InterruptedException {
	      
	            context.write(key, new IntWritable(1));	            
	        }
	    } 
	 
	 public static class Reducer2 extends Reducer<LongWritable, IntWritable, LongWritable, Text> {
		 private int m;
	    	@Override
	    	public void reduce ( LongWritable key, Iterable<IntWritable> values, Context context )
	                           throws IOException, InterruptedException {
	    		m=0;
	    		for (IntWritable v: values){
	    			m = m + v.get();
	    		}
	    		context.write(null, new Text(key + "," + m));
	        }
	    	
	    }

public static void main( String[] args ) throws Exception {
	Configuration c = new Configuration();
        Job job1 = Job.getInstance(c);
        job1.setJobName("Job1");
        job1.setJarByClass(Graph.class);
        job1.setNumReduceTasks(0);
        job1.setInputFormatClass(TextInputFormat.class);
	job1.setOutputFormatClass(SequenceFileOutputFormat.class);
	job1.setMapOutputKeyClass(LongWritable.class);
        job1.setMapOutputValueClass(Vertex.class);
        job1.setOutputKeyClass(LongWritable.class);
        job1.setOutputValueClass(Vertex.class);
        job1.setMapperClass(Mapper1.class);
        FileInputFormat.setInputPaths(job1,new Path(args[0]));
        SequenceFileOutputFormat.setOutputPath(job1,new Path(args[1]+"/f0"));
        job1.waitForCompletion(true);
        for(int i=0; i < 5; i++){
        Configuration con = new Configuration();
        Job job2 = Job.getInstance(con);
	job2.setJobName("Job2");
	job2.setJarByClass(Graph.class);
	job2.setInputFormatClass(SequenceFileInputFormat.class);
	job2.setOutputFormatClass(SequenceFileOutputFormat.class);
	job2.setMapOutputKeyClass(LongWritable.class);
	job2.setMapOutputValueClass(Vertex.class);
	job2.setOutputKeyClass(LongWritable.class);
	job2.setOutputValueClass(Vertex.class);
	job2.setMapperClass(Mapper2.class);
	job2.setReducerClass(Reducer1.class);
	SequenceFileInputFormat.setInputPaths(job2,new Path(args[1]+"/f"+i));
	SequenceFileOutputFormat.setOutputPath(job2,new Path(args[1]+"/f"+(i+1)));
	job2.waitForCompletion(true);
        }
        
        Configuration confg = new Configuration();
        Job job3 = Job.getInstance(confg);
        job3.setJobName("Job3");
        job3.setJarByClass(Graph.class);
        job3.setInputFormatClass(SequenceFileInputFormat.class);
        job3.setOutputFormatClass(TextOutputFormat.class);
        job3.setMapOutputKeyClass(LongWritable.class);
        job3.setMapOutputValueClass(IntWritable.class);
        job3.setOutputKeyClass(LongWritable.class);
        job3.setOutputValueClass(Text.class);
        job3.setMapperClass(Mapper3.class);
        job3.setReducerClass(Reducer2.class);
        SequenceFileInputFormat.setInputPaths(job3,new Path(args[1]+"/f5"));
        TextOutputFormat.setOutputPath(job3,new Path(args[2]));
        job3.waitForCompletion(true);

}

}