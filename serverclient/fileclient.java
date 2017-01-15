//client program - vanisha crasta,1001242903
import java.io.*;
import java.net.*;

public class fileclient {

	public static void main(String args[])
	{
			try
			{
				long start=System.currentTimeMillis();
			System.out.println("Connecting to "+"localhost" + " with port number "+ "7207");
			Socket client=new Socket("localhost",7207);
			System.out.println("Connection successful");
			String Filename;
		    Filename=args[0];
		    DataOutputStream dos=new DataOutputStream(client.getOutputStream());
		    BufferedReader is = new BufferedReader(new InputStreamReader(client.getInputStream()));
	     	dos.writeBytes("GET/"+" "+Filename+" "+"/HTTP/1.1"+"\r\n");
	      	System.out.println("Server Response :");
	        try
	        {
	        	byte[] buffer=new byte[1024];
				String line;
				while((line=is.readLine())!=null)
				{
				System.out.println(line);
				}
				long end=System.currentTimeMillis();
				long rtt=end-start;
				System.out.println("Round trip Time : "+rtt+"ms");
				System.out.println("Host name of the server : localhost "+client.getRemoteSocketAddress()+"\r\n");//referred from http://docs.oracle.com
				System.out.println("Timeout : "+client.getSoTimeout()+"\r\n");//referred from http://docs.oracle.com
				System.out.println("SocketType : SOCK_STREAM\r\n");
				System.out.println("Protocol : TCP/IP");
	        }
	        catch(Exception e)
	        {
	        	System.out.println("exception caught");
	        }
			}
			catch(UnknownHostException e)
			{
				System.out.println("Cannot establish connection...Host could not be identified");
			}
			catch(IOException e)
			{
				System.out.println("Cannot establish connection");
			}
			
}
}
