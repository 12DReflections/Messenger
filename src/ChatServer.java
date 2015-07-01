import java.io.*;
import java.net.*;
import java.util.*;


public class ChatServer {
	
	//Allows to create many clients and many login names.
	//for each one of our clients that connects
	static Vector ClientSockets;
	static Vector LoginNames;
	
	
	ChatServer() throws IOException{ //in case server fails
		ServerSocket server = new ServerSocket(5267); //need int for socket number
		ClientSockets = new Vector();//Initialise Vectors
		LoginNames = new Vector();
		
		//Endless while loop
		while(true){
			Socket client = server.accept(); //Server accepts a client socket 
			AcceptClient acceptClient = new AcceptClient(client); //Client is passed in as accepted
		}	
	}
	
	public static void main(String[] args) throws IOException{
		ChatServer server = new ChatServer();
	}
	
	class AcceptClient extends Thread{
		Socket ClientSocket;
		DataInputStream din;
		DataOutputStream dout;
		
		//A constructor that accepts sockets
		AcceptClient(Socket client) throws IOException{ 
			ClientSocket = client; // = client that we passed in
			
			//Initialize streams, DataInput is the Client, Output is Client
			din = new DataInputStream(ClientSocket.getInputStream()); 
			dout = new DataOutputStream(ClientSocket.getOutputStream()); 
			
			String LoginName = din.readUTF(); //read the input stream as LoginName
			
			LoginNames.add(LoginName); //Add to the LoginName Vector
			ClientSockets.add(ClientSocket);//Add to ClientSocket Vector
			start(); //Start the thread
		}
		
		public void run(){
			while(true){	
				try {
					String msgFromClient = din.readUTF(); //Client message = data input
					StringTokenizer st = new StringTokenizer(msgFromClient); // makes easy to see who is sending
					String LoginName = st.nextToken(); //first token
					String MsgType = st.nextToken();
					int lo = -1;
						
					//Msg String
					String msg = "";
					while(st.hasMoreTokens()){
						msg = msg + " " + st.nextToken();
					}
					
					
					if(MsgType.equals("LOGIN")){
						for(int i = 0; i < LoginNames.size(); i++){
							Socket pSocket = (Socket) ClientSockets.elementAt(i); //for each client get socket, cast socket
							DataOutputStream pOut = new DataOutputStream(pSocket.getOutputStream()); //Output stream from Socket
							pOut.writeUTF(LoginName + " has logged in"); //Write to output stream
						}				
					}
					
					else if(MsgType.equals("LOGOUT")){
						for(int i = 0; i < LoginNames.size(); i++){
							if(LoginName.equals(LoginNames.elementAt(i)))
								lo = i; //lo = logged out
							Socket pSocket = (Socket) ClientSockets.elementAt(i); //for each client get socket, cast socket
							DataOutputStream pOut = new DataOutputStream(pSocket.getOutputStream()); //Output stream from Socket
							pOut.writeUTF(LoginName + " has logged out"); //Write to output stream
						}		
						if(lo >= 0){ //Removes the client from both vectors when logging out
							LoginNames.removeElementAt(lo);
							ClientSockets.removeElementAt(lo);
						}
					}
					else{
						for(int i = 0; i < LoginNames.size(); i++){
							Socket pSocket = (Socket) ClientSockets.elementAt(i); //for each client get socket, cast socket
							DataOutputStream pOut = new DataOutputStream(pSocket.getOutputStream()); //Output stream from Socket
							pOut.writeUTF(LoginName + ": " + msg); //Write to output stream
							}				
					}
					if(MsgType.equals("LOGOUT"))
						break;
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
/*
*Sockets
*TCP: TCP stands for Transmission Control Protocol, 
*which allows for reliable communication between two applications. 
*TCP is typically used over the Internet Protocol, 
*which is referred to as TCP/IP.
*
*The following steps occur when establishing a TCP connection 
*between two computers using sockets:

The server instantiates a ServerSocket object, 
denoting which port number communication is to occur on.

The server invokes the accept() method of the ServerSocket class. 
This method waits until a client connects to the server on the given port.

After the server is waiting, a client instantiates a Socket object, 
specifying the server name and port number to connect to.

The constructor of the Socket class attempts to connect the client 
to the specified server and port number. If communication is established, 
the client now has a Socket object capable of communicating with the server.

On the server side, the accept() method returns a reference to a new socket 
on the server that is connected to the client's socket.

After the connections are established, communication can occur using I/O streams. 
Each socket has both an OutputStream and an InputStream. 
The client's OutputStream is connected to the server's InputStream, 
and the client's InputStream is connected to the server's OutputStream.
-http://www.tutorialspoint.com/java/java_networking.htm

*/