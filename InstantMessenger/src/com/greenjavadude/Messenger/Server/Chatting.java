package com.greenjavadude.Messenger.Server;


public class Chatting implements Runnable{
	private boolean running;
	private Server server;
	
	public Chatting(Server s){
		server = s;
		running = false;
	}
	
	public void run(){
		try{
			String message = "";
			while(running){
				
				for(Stuff aThing:server.getPeople()){
					if(aThing.getInput().available() > 0){
						message = (String) aThing.getInput().readObject();
						
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
				
				Thread.sleep(5);
			}
		}catch(Exception e){
			
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