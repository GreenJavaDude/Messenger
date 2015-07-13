package com.greenjavadude.Messenger.Server;


@Deprecated
public class Chatting implements Runnable{
	
	public Chatting(Server s){
	}
	
	public void run(){
		try{
			
			
			
			
			
			
			
			
			Thread.sleep(5);
			/*
			String message = "";
			Stuff thingy = null;
			boolean beef = false;
			while(running){
				try{
					for(Stuff aThing:server.getPeople()){
						try{
							aThing.getInput().
							
							
							
							
							
							if(aThing.getInput().available() > 0){
								System.out.println("Input is available");
								message = (String) aThing.getInput().readObject();
								beef = true;
								thingy = aThing;
								break;
							}else{
								System.out.println("Input is not available");
							}
						}catch(IOException e){
							
						}
					}
				}catch(Exception e){
					//noone connected
					if(server.getWorkingConnection()){
						System.out.println("Error");
					}
				}
				System.out.println("Checking for beef");
				if(beef){
					System.out.println("Beef is true");
					if(message.endsWith("END")){
						server.disconnected(thingy);
					}else{
						System.out.println("Rebouncing the message to all the listeners");
						for(Stuff aStuff:server.getPeople()){
							server.sendMessage(message, aStuff.getOutput());
						}
						server.showMessage(message);
					}
					thingy = null;
					beef = false;
					message = "";
				}
				*/
				
				//these aren't really working  |
				//							   |
				//							   |
				//							   V
				/*
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
				
				*/
				
				
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
			
			
		}catch(Exception e){
			System.out.println("Whoops...");
			//stop();
			//start();
		}
	}
	
	public void start(){
		new Thread(this).start();
	}
	
	public void stop(){
	}
}