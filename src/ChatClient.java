/**
 * This application was built and annotated by Julian Wise
 * Built following an Eduonix Course "Projects in Java"
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
		
		addWindowListener(new WindowAdapter(){ //Special action to allow window to close and user to be removed from the vector
			public void windowClosing(WindowEvent e){
				try {
					dout.writeUTF(LoginName + " " + "LOGOUT");
					System.exit(1);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		//Instantiate objects
		ta = new JTextArea(18, 50);
		tf = new JTextField(50);
		
		tf.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					try {
						if(tf.getText().length()>0)//Check if text length is longer than 0
							dout.writeUTF(LoginName + " " + "DATA " + tf.getText().toString()); //get Login name, Data Type and Text from TF as a string
						tf.setText(""); //Removes the text
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				
			}
			
		});

		
		send = new JButton("Send");
		logout = new JButton("Logout");
		
		
		//Sends the Login Name and Data when the send button is pressed
		send.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(tf.getText().length()>0)//Check if text length is longer than 0
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
		
		socket = new Socket("localhost", 5267);
		
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
	
	
}
