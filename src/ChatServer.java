import java.io.*;
import java.net.*;
import java.util.*;


public class ChatServer {
	
	//Allows to to create many clients and many log in names.
	//For each one of our clients that connects
	static Vector ClientSockets;
	static Vector LoginNames;
	
	
	ChatServer() throws IOException{ //throw exception in case server fails
		
		ServerSocket server = new ServerSocket(5217); //need int for socket number
		ClientSockets = new Vector();//Initialise Vectors
		LoginNames = new Vector();
		
		//Endless while loop
		while(true){
			Socket client = server.accept(); //Server accepts client's sockets
			AcceptClient acceptClient = new AcceptClient(client); //Pass client just created
		}
		
	}
	
	class AcceptClient extends Thread{
		Socket ClientSocket;
		DataInputStream din;
		DataOutputStream dout;
		AcceptClient(Socket client) throws IOException{ //A constructor that accepts sockets
			ClientSocket = client; // = client that we passed in
			
			//Initialize streams, DataInput is the Client, Output is Client
			din = new DataInputStream(ClientSocket.getInputStream()); 
			dout = new DataOutputStream(ClientSocket.getOutputStream()); 
			
			String LoginName = din.readUTF(); //read the input stream as LoginName
			
			LoginNames.add(LoginName); //Add to the LoginName Vector
			ClientSockets.add(ClientSockets);//Add to ClientSocket Vector
			start(); //Start the thread
		}
		
	public void run(){
		while(true){
			
		}
	}
	}
}
