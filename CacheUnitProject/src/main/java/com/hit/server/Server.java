package com.hit.server;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.Observer;
import java.util.Scanner;


import com.hit.serverAPI.Controller;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class Server<T> 
{
	
	private ServerSocket socket;
	private Socket s;
    private Controller controller;
    private  static boolean serverUp = true;
	
    
	public Server(int port) {
		
		try {
			this.socket = new ServerSocket(port);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.controller = new Controller();
	}
	
	public void listen()
	{
		killServer();
		while(this.serverUp)
		{
			
			try
			{
				s = socket.accept();
				
				Scanner reader = new Scanner(new InputStreamReader(s.getInputStream())) ;
		        PrintWriter writer = new PrintWriter(new OutputStreamWriter(s.getOutputStream())) ;
				
		        System.out.println("A new client is connected : " + s);
				System.out.println("Assigning new thread for this client");
	                // create a new thread object 
	                Thread t = new ClientHandler<T>(s,reader,writer,controller); 
	                	           
	                t.start(); 
			 } 
            catch (Exception e){ 
                try {
					s.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
                e.printStackTrace(); 
            } }
		}
		public static void killServer(){
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					System.out.println("type 'stop' to kill the server");
					Scanner scn = new Scanner(System.in);
					while(serverUp){
						String s = scn.nextLine();
						if(s.equals("stop"))
						{
							
						System.out.println("server is off");
							serverUp = false;				
						}
					}
					scn.close();
				}
			}).start();
	}

	
		

	
	
	

}
