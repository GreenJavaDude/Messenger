package com.greenjavadude.Messenger.Server;

import java.io.*;
import java.net.*;

public class Stuff implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Socket connection;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
	public Stuff(Socket socket) throws IOException{
		connection = socket;
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
	}
	
	public Socket getConnection(){
		return connection;
	}
	
	public ObjectInputStream getInput(){
		return input;
	}
	
	public ObjectOutputStream getOutput(){
		return output;
	}
}