package Client;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;




public class ClientRequest<T> {

	private  Socket s;
	Scanner reader;
	PrintWriter writer;
	
	private  Request<DataModel<T>[]> request,response;
	
	public ClientRequest(Socket s,Scanner reader, PrintWriter writer,Request<DataModel<T>[]> request)
	{
		this.s=s;
		this.reader =reader;
		this.writer =writer;
		this.request = request;
	}
	
	public Request<DataModel<T>[]> sendRequestToServer()
	{
		 Type ref = new TypeToken<Request<DataModel<T>[]>>(){}.getType();
		String json = new Gson().toJson(this.request);
		
		 
		 writer.write(json);
		 writer.write("\n");
		 writer.flush();
		    
		    this.response = new Gson().fromJson(this.reader.nextLine(), ref);  

		return this.response;
		
	}
}
