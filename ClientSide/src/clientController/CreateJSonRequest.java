package clientController;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectOutputStream;

import Client.ClientRequest;
import Client.DataModel;
import Client.Request;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;




public class CreateJSonRequest {
	 private static final String filepath="resources";
	 
	 public static <T> void main(String args[]) {
		 
		    DataModel<String> d1= new DataModel<>((long)1,"test1");
			DataModel<String> d2= new DataModel<>((long)2,"test2");
			DataModel<String> d3= new DataModel<>((long)3,"test3");
			DataModel<String> d4= new DataModel<>((long)4,"test4");
			DataModel<String> d5= new DataModel<>((long)5,"test5");
			DataModel<String> d6= new DataModel<>((long)6,"test6");
			DataModel<String> d7= new DataModel<>((long)7,"test7");		
			DataModel<String> d8= new DataModel<>((long)8,"test8"); 
			DataModel<String>[]	datamodels = new DataModel[3];
			datamodels[0] = d1;
			datamodels[1] = d8;
			datamodels[2] = d8;
			DataModel<String>[]	datamodels2 = new DataModel[2];
			
			datamodels2[0] = d1;
			datamodels2[1] = d2;
			
			Request<DataModel<T>[]> create = new Request("create",datamodels);
	        Request<DataModel<T>[]> read = new Request("read",datamodels2);
	        Request<DataModel<T>[]> update = new Request("update",datamodels2);
	        Request<DataModel<T>[]> delete = new Request("delete",datamodels2);
	        createJSonRequestFile(create);
	        createJSonRequestFile(read);
	        createJSonRequestFile(update);
	        createJSonRequestFile(delete);
	    }

	public static <T> void createJSonRequestFile(Request<DataModel<T>[]> request)
	{
		String path = "";
		switch(request.getAction())
		{
		case "create":
		path = "resources\\create.json";
		break;
		case "read":
		path = "resources\\read.json";
		break;
		case "update":
		path = "resources\\update.json";
		break;
		case "delete":
		path = "resources\\delete.json";
		break;
		}
		   try {
	            String jsonString = new Gson().toJson(request);
	            FileWriter writer = new FileWriter(path);
	            writer.write(jsonString);
	            writer.close();
	        } catch (IOException e) {
	            System.out.println("exception " + e.getMessage());
	            e.printStackTrace();
	        }
	}
	
}
