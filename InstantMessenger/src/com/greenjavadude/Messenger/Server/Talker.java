package com.greenjavadude.Messenger.Server;

public class Talker implements Runnable{
	private boolean running;
	private Thread thread;
	private String message;
	private Stuff stuff;
	private Server server;
	
	public Talker(Stuff person, Server ser){
		running = false;
		message = "";
		stuff = person;
		server = ser;
	}
	
	public void run(){
		try{
			while(running){
				try{
					System.out.println("Trying to read object");
					message = (String) stuff.getInput().readObject();
					System.out.println("Successfully read object");
					
					if(message.endsWith("END")){
						server.disconnected(this);
					}else{
						for(Talker aTalker:server.getPeople()){
							server.sendMessage(message, aTalker.getStuff().getOutput());
						}
						server.showMessage(message);
					}
					
					
				}catch(Exception e){
					System.out.println("Connection closed.");
				}
				
				Thread.sleep(5);
			}
		}catch(Exception e){
			
		}
	}
	
	public void start(){
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void stop(){
		running = false;
	}
	
	public Stuff getStuff(){
		return stuff;
	}
}