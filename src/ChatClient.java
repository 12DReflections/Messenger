import java.io.*;
import java.net.*;

import javax.swing.*;


public class ChatClient extends JFrame implements Runnable {

	Socket socket;
	JTextArea ta;
	
	Thread thread; 
	
	DataInputStream din;
	DataOutputStream dout;
	
	String LoginName;
	
	ChatClient(String login) throws UnknownHostException, IOException{
		super(login); //Super constructor to name JFrame
		LoginName = login;
		
		ta = new JTextArea(18, 50);
		
		socket = new Socket("localhost", 5218);
		
		din = new DataInputStream(socket.getInputStream()); //take from socket
		dout = new DataOutputStream(socket.getOutputStream());
		
		dout.writeUTF(LoginName);//write login name
		dout.writeUTF(LoginName + " " + "LOGIN");
		
		thread = new Thread(this);
		thread.start();
		setup(); //the JFrame
	}
	
	private void setup(){
		setSize(600,400);
		
		JPanel panel = new JPanel();
		panel.add(new JScrollPane(ta)); //add a scroll pane to the text area
		
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
		ChatClient client = new ChatClient("User 1");
	}
}
