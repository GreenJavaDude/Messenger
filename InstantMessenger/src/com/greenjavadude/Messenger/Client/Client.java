package com.greenjavadude.Messenger.Client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

public class Client extends JFrame implements Runnable{
	private static final long serialVersionUID = 1L;
	
	private String ipAddress;
	private String name;
	
	private JTextArea area;
	private JTextField field;
	
	private Socket connection;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
	private boolean running;
	
	public Client(String s, String n){
		super("Messenger Client");
		
		running = false;
		ipAddress = s;
		name = n;
		
		area = new JTextArea();
		area.setEditable(false);
		field = new JTextField();
		field.setEditable(true);
		field.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String text = field.getText();
				if(text.equals("exit")){
					shutdown();
					stop();
				}else{
					sendMessage(text, output);
					showMessage(text);
					field.setText("");
				}
			}
		});
		
		add(area, BorderLayout.CENTER);
		add(field, BorderLayout.NORTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(500, 300);
		setVisible(true);
	}
	
	public void run(){
		try{
			makeConnection(ipAddress, 7777);
			String message;
			while(running){
				try{
					message = (String) input.readObject();
					showMessage(message);
				}catch(Exception e){
					System.out.println("Couldn't recieve message");
				}
				
				Thread.sleep(5);
			}
		}catch(EOFException e){
			
		}catch(Exception e){
			shutdown();
			System.out.println("Oops.");
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
	
	public void sendMessage(String message, ObjectOutputStream output){
		try {
			output.writeObject(name + ": " + message);
		} catch (Exception e) {
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
	
	public void makeConnection(String ip, int port) throws UnknownHostException, IOException{
		connection = new Socket(InetAddress.getByName(ip), port);
		input = new ObjectInputStream(connection.getInputStream());
		output = new ObjectOutputStream(connection.getOutputStream());
	}
	
	public void shutdown(){
		try{
			input.close();
			output.close();
			connection.close();
		}catch(Exception e){
			System.out.println("Couldn't shutdown");
		}
	}
	
	public static void main(String[] args){
		String s = JOptionPane.showInputDialog(null, "Enter ip address", "Input example", JOptionPane.QUESTION_MESSAGE);
		String n = JOptionPane.showInputDialog(null, "Enter username", "Input example", JOptionPane.QUESTION_MESSAGE);
		Client client = new Client(s, n);
		client.start();
	}
}













