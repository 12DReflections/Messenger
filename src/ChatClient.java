import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

import javax.swing.*;


public class ChatClient extends JFrame implements Runnable {

	Socket socket;
	JTextArea ta;
	
	JButton send, logout;
	JTextField tf;
	
	Thread thread; 
	
	DataInputStream din;
	DataOutputStream dout;
	
	String LoginName;
	
	ChatClient(String login) throws UnknownHostException, IOException{
		super(login); //Super constructor to name JFrame
		LoginName = login;
		
		//Instantiate objects
		ta = new JTextArea(18, 50);
		tf = new JTextField(50);
		
		send = new JButton("Send");
		logout = new JButton("Logout");
		
		
		//Sends the Login Name and Data when the send button is pressed
		send.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					dout.writeUTF(LoginName + " " + "DATA " + tf.getText().toString()); //get Login name, Data Type and Text from TF as a string
					tf.setText(""); //Removes the text
				} catch (IOException e) {
					e.printStackTrace();
				}
			}			
		});

		//Sends the Login Name and Data when the send button is pressed
		logout.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					dout.writeUTF(LoginName + " " + "LOGOUT");
					System.exit(1); //Close system when you press logout
				} catch (IOException e) {
					e.printStackTrace();
				}
			}			
		});
		
		socket = new Socket("localhost", 5210);
		
		din = new DataInputStream(socket.getInputStream()); //take from socket
		dout = new DataOutputStream(socket.getOutputStream());
		
		dout.writeUTF(LoginName);//write login name
		dout.writeUTF(LoginName + " " + "LOGIN");
		
		thread = new Thread(this);
		thread.start();
		setup(); //the JFrame
	}
	
	private void setup(){
		//Create panel and add objects
		setSize(600,400);
	
		JPanel panel = new JPanel();
		panel.add(new JScrollPane(ta)); //add a scroll pane to the text area
		panel.add(tf);
		panel.add(send);
		panel.add(logout);
		add(panel); //add panel to JFrame
		
		setVisible(true);
	}
	
	@Override
	public void run() {
		//keep app running
		while(true){
			try {
				ta.append("\n" + din.readUTF());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException{
		ChatClient client = new ChatClient("User 2");
	}
}
