package com.greenjavadude.Messenger.Client;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

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
					stop();
				}else{
					sendMessage(text);
					field.setText("");
				}
			}
		});
		
		addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                stop();
            }
        });
		
		add(new JScrollPane(area), BorderLayout.CENTER);
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
					System.out.println("Trying to read object");
					message = (String) input.readObject();
					System.out.println("Successfully read object");
					showMessage(message);
				}catch(Exception e){
					System.out.println("Couldn't recieve message");
				}
				
				Thread.sleep(5);
			}
		}catch(Exception e){
			stop();
			System.out.println("Oops.");
		}
	}
	
	public void start(){
		running = true;
		new Thread(this).start();
	}
	
	public void stop(){
		running = false;
		try{
			sendMessage("END");
			input.close();
			output.close();
			connection.close();
		}catch(Exception e){
			System.out.println("Couldn't shutdown");
		}
		System.exit(0);
	}
	
	public void sendMessage(String message){
		try {
			if(message.equals("END")){
				output.writeObject(name + ": " + message);
				stop();
			}else{
				output.writeObject(name + ": " + message);
			}
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
	
	public void makeConnection(String ip, int port){
		try{
			connection = new Socket(InetAddress.getByName(ip), port);
			input = new ObjectInputStream(connection.getInputStream());
			output = new ObjectOutputStream(connection.getOutputStream());
			System.out.println("Successful connection made.");
			sendMessage("successfully connected");
		}catch(Exception e){
			System.out.println("Failed to make connection");
		}
	}
	
	public static void main(String[] args){
		String s = JOptionPane.showInputDialog(null, "Enter IPv4 address", "Input example", JOptionPane.QUESTION_MESSAGE);
		String n = JOptionPane.showInputDialog(null, "Enter username", "Input example", JOptionPane.QUESTION_MESSAGE);
		Client client = new Client(s, n);
		client.start();
	}
}













