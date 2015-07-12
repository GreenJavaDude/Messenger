package com.greenjavadude.Messenger.Server;

import java.io.*;
import java.net.*;

public class Stuff implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private final Socket connection;
	private final ObjectInputStream input;
	private final ObjectOutputStream output;
	
	public Stuff(Socket socket) throws IOException{
		connection = socket;
		input = new ObjectInputStream(connection.getInputStream());
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
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