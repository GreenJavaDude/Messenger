package com.greenjavadude.Messenger.Server;

import java.io.IOException;
import java.net.Socket;

public class Connecting implements Runnable{
	private boolean running;
	private Server server;
	
	public Connecting(Server s){
		running = false;
		server = s;
	}
	
	public void run(){
		try{
			while(running){
				try{
					Socket sock = checkForConnection();
					//connection found
					Stuff stuff = new Stuff(sock);
					server.getPeople().push(stuff);
				}catch(Exception e){
					System.out.println("Connection Failed\n");
				}
				
				Thread.sleep(5);
			}
		}catch(Exception e){
			System.out.println("Oops...\n");
		}
	}
	
	public void start(){
		running = true;
		new Thread(this).start();
	}
	
	public void stop(){
		running = false;
	}
	
	public Socket checkForConnection() throws IOException{
		return server.getServerSocket().accept();
	}
}