package com.greenjavadude.Messenger.Server;

import java.awt.BorderLayout;
import java.io.*;
import java.net.ServerSocket;
import java.util.Stack;

import javax.swing.*;

public class Server extends JFrame implements Runnable{
	private static final long serialVersionUID = 1L;
	
	private boolean running;
	private boolean workingConnection;
	private boolean chicken;
	
	private Connecting connecting;
	private Chatting chatting;
	
	private JTextArea area;
	
	private ServerSocket server;
	
	private Stack<Stuff> people;
	
	public Server(){
		super("Messenger Server");
		
		running = false;
		workingConnection = false;
		chicken = false;
		people = new Stack<Stuff>();
		
		connecting = new Connecting(this);
		chatting = new Chatting(this);
		
		area = new JTextArea();
		
		area.setEditable(false);
		
		add(new JScrollPane(area), BorderLayout.CENTER);
		
		setSize(500, 300);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void run(){
		try{
			server = new ServerSocket(7777, 50);
			connecting.start();
			while(running){
				if(workingConnection){
					if(!chicken){
						chatting.start();
						chicken = true;
					}
				}else{
					chatting.stop();
					chicken = false;
				}
				
				update();
				
				Thread.sleep(5);
			}
		}catch(Exception e){
			System.out.println("Oops.\n");
		}
	}
	
	public void start(){
		running = true;
		new Thread(this).start();
	}
	
	public void stop(){
		running = false;
		connecting.stop();
		chatting.stop();
		System.exit(0);
	}
	
	public void sendMessage(String message, ObjectOutputStream output){
		try {
			output.writeObject(message);
			System.out.println("Sent a message successfully");
		} catch (IOException e) {
			System.out.println("Couldn't send message");
		}
	}
	
	public void showMessage(final String text){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				area.append(text+"\n");
			}
		});
	}
	
	public void update(){
		if(people.isEmpty()){
			workingConnection = false;
		}else{
			workingConnection = true;
		}
	}
	
	public void disconnected(Stuff stuff){
		String guy = stuff.getConnection().getInetAddress().getHostName();
		if(people.contains(stuff)){
			try{
				stuff.getInput().close();
				stuff.getOutput().close();
				stuff.getConnection().close();
			}catch(Exception e){
				System.out.println("Couldn't disconnect properly");
			}
			people.remove(stuff);
		}
		showMessage(guy+" disconnected");
	}
	
	
	public static void main(String[] args){
		Server server = new Server();
		server.start();
	}
	
	public ServerSocket getServerSocket(){
		return server;
	}
	
	public Stack<Stuff> getPeople(){
		return people;
	}
}