package com.hit.server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hit.dm.DataModel;
import com.hit.serverAPI.Controller;

public class ClientHandler<T> extends Thread implements Observer
{
		private Socket s;
		Scanner reader;
		PrintWriter writer;
		
		private Controller controller;
		private Request<DataModel<T>[]> request;
		private Request<DataModel<T>[]> response;
		
		// ArrayList to keep all the observers
	    private ArrayList<Observer> clients;
	    
	    // members to get update from observable
		private int capacity;
		private int available;
		private int numOfGetActions;
		private int numOfPutAndRemove;
		
		public ClientHandler(Socket s,Scanner reader, PrintWriter writer,Controller<T> controller)
		{
			
			this.s = s;
			this.reader =reader;
			this.writer = writer;
	
			this.controller=controller;
			
			this.controller.getCache().register(this);
			this.capacity = controller.getCache().getCapacity();
			this.available = controller.getCache().getAvailable();
			this.numOfGetActions = controller.getCache().getNumOfGetActions();
			this.numOfPutAndRemove = controller.getCache().getNumOfPutAndRemove();
		}
		
		public void run()
		{
			while(s.isConnected()) 
			{
			String json;;
			Type ref = new TypeToken<Request<DataModel<T>[]>>(){}.getType();
			String	 req = reader.nextLine();
			request = new Gson().fromJson(req, ref);
			response = new Request<DataModel<T>[]>();
			ArrayList<Long[]> notFoundIds ;
			Long[] ids ;
						
			switch(request.getAction())
			{
		
			case "create":
			{
				this.controller.create(request.getBody());				 
				this.response.setAction("created");
				json = new Gson().toJson(this.response);
				writer.write(json);
				writer.write("\n");
				writer.flush();
			       			      
			     break;
			}
			case "read":
			{
				ids = new Long[request.getBody().length];
				for(int i=0;i<request.getBody().length;i++)
					ids[i]=request.getBody()[i].getDataModelId();
				response.setBody(controller.read(ids));
				
				if 
				(request.getBody().length==(response.getBody().length))
					this.response.setAction("succeeded");
				else
					this.response.setAction("Failed");
			    json = new Gson().toJson(this.response);
			    writer.write(json);
				writer.write("\n");
				writer.flush();
				System.out.println(response.getBody());
			       			       
		       break;  
			}
			case "update":
			{
				response = new Request<DataModel<T>[]>();

				response.setAction("Succeeded");
				response.setBody(this.controller.update(request.getBody()));
			
				for(DataModel<T> dataModel: response.getBody())
					if (dataModel==null)
						response.setAction("Failed");
				json = new Gson().toJson(response);
				writer.write(json);
				writer.write("\n");
				writer.flush();
			       
			       
			break;
			}
			case "delete":				
			{  
				response = new Request<DataModel<T>[]>();
				
				ids = new Long[request.getBody().length];
				for(int i=0;i<request.getBody().length;i++)
					ids[i]=request.getBody()[i].getDataModelId();
				
				response.setAction("Succeeded");
				response.setBody(this.controller.delete(ids));
				
				for(int i=0;i<ids.length;i++)
				if (response.getBody()[i].getDataModelId()==null)
					response.setAction("Failed");
				
				json = new Gson().toJson(response);
				writer.write(json);
				writer.write("\n");
				writer.flush();
			       
			       
		     break;
		     }   
			case "statistics":
				
				DataModel<T>[] datamodels = new  DataModel[4];
				
				datamodels[0] = new DataModel((long)this.capacity,"");
				datamodels[1]= new DataModel((long)this.available,"");
				datamodels[2]= new DataModel((long)this.numOfGetActions,"");
				datamodels[3]= new DataModel((long)this.numOfPutAndRemove,"");
				
				
				this.response = new Request("statistic",datamodels);
				json = new Gson().toJson(response);
				writer.write(json);
				writer.write("\n");
				writer.flush();
				
			}
						
			
		} 
			try {
				reader.close();
				writer.close();
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	

		@Override
		public void update(int capacity,int available,int numOfGetActions,int numOfPutAndRemove)
		{

			this.capacity = capacity;
			this.available = available;
			this.numOfGetActions =numOfGetActions;
			this.numOfPutAndRemove = numOfPutAndRemove;
			
		}
	}
