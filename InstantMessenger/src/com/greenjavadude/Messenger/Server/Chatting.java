package com.greenjavadude.Messenger.Server;


public class Chatting implements Runnable{
	private boolean running;
	private Server server;
	
	public Chatting(Server s){
		server = s;
		running = false;
	}
	
	public void run(){
		try{//if noone is connected can't loop throught people
			String message = "";
			while(running){
				
				for(Stuff aThing:server.getPeople()){
					try{
						Object obj = aThing.getInput().readObject();
						if(!(obj.equals(null))){
							message = (String) obj;
							if(message.endsWith("END")){
								server.disconnected(aThing);
							}else{
								for(Stuff aStuff:server.getPeople()){
									server.sendMessage(message, aStuff.getOutput());
								}
								server.showMessage(message);
							}
						}
					}catch(Exception e){
						System.out.println("Couldn't read string/object");
					}
				}
				
				
				
				
				//this is not really working   |
				//							   |
				//							   |
				//							   V
				/*
				for(Stuff aThing:server.getPeople()){
					message = (String) aThing.getInput().readObject();
					if(!(message.equals(null))){
						System.out.println("read object");
						if(message.endsWith("END")){
							server.disconnected(aThing);
						}else{
							for(Stuff aStuff:server.getPeople()){
								server.sendMessage(message, aStuff.getOutput());
							}
							server.showMessage(message);
						}
					}
				}
				*/
			}
			
			Thread.sleep(5);
		}catch(Exception e){
			System.out.println("Whoops...");
		}
	}
	
	public void start(){
		running = true;
		new Thread(this).start();
	}
	
	public void stop(){
		running = false;
	}
}