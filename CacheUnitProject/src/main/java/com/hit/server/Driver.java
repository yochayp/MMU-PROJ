package com.hit.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Driver { // set up server configuration
	
	 public static void main(String[] args)
	 {    
		
		 Server server = new Server(33333); ///Add your implementation   }
		 
		 server.listen();
		
		
	 }
	 

}
