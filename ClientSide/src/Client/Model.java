package Client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



public class Model<T> {
	
	private  Socket s;
	Scanner reader;
	PrintWriter writer;
	
	public Model()
	{
		try 
		{		
			InetAddress	ip = InetAddress.getByName("localhost");
			this.s = new Socket(ip, 33333);
			this.reader = new Scanner(new InputStreamReader(s.getInputStream())) ;
	        this.writer = new PrintWriter(new OutputStreamWriter(s.getOutputStream())) ;
	        
		} catch (  IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public Request<DataModel<T>[]> performLoading() throws FileNotFoundException 
	{		         
        	JFileChooser fileChooser = new JFileChooser("resources\\");
        	DataModel<T>[] datamodels= new DataModel[1];
        	Request<DataModel<T>[]> request2 = new Request<DataModel<T>[]>("No file selected",datamodels);
        	int result = fileChooser.showOpenDialog(null);
        	if (result == JFileChooser.APPROVE_OPTION) 
        	{
        	    File selectedFile = fileChooser.getSelectedFile();
        	    File file = fileChooser.getSelectedFile();
        	    String fullPath = file.getAbsolutePath();
        	    
        	    BufferedReader br = new BufferedReader(new FileReader(fullPath));
        	    Request<DataModel<T>[]> request = new Gson().fromJson(br, Request.class );
        	    
        	    return sendToServer(request);
        	    
			}
        	else return request2;	
	
	}
	
	public Request<DataModel<T>[]> getStatistics() 
	{		   	
    	 DataModel<T>[] datamodels= new DataModel[1];
    	 Request<DataModel<T>[]> request = new Request<DataModel<T>[]>("statistics",datamodels);
        
		return sendToServer(request);
	}
	
	public Request<DataModel<T>[]> sendToServer( Request<DataModel<T>[]> request)
	{
		ClientRequest<T> client =new ClientRequest(s,this.reader,this.writer,request);
		Request<DataModel<T>[]> response = client.sendRequestToServer();
		return  response;
	}

}
