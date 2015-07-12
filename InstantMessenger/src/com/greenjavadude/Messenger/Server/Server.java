package com.greenjavadude.Messenger.Server;

import java.awt.BorderLayout;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.*;

public class Server extends JFrame implements Runnable{
	private static final long serialVersionUID = 1L;
	
	private boolean running;
	
	private JTextArea area;
	
	public Server(){
		super("Messenger Server");
		
		running = false;
		
		area.setEditable(false);
		
		add(new JScrollPane(area), BorderLayout.CENTER);
		
		setSize(500, 300);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void run(){
		try{
			while(running){
				//stay available, it's a server
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
		System.exit(0);
	}
	
	public static void main(String[] args){
		Server server = new Server();
	}
}












