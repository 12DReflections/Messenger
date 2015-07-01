import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.*;


public class Login {
	public static void main(String[] args){
		final JFrame login = new JFrame("Login");
		JPanel panel = new JPanel();
		final JTextField loginName = new JTextField(20);
		JButton enter = new JButton("Login");
		
		panel.add(loginName);
		panel.add(enter);
		login.setSize(300,100);
		login.add(panel);
		login.setVisible(true);
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//When Login is pressed the LoginName is obtained and the frame is closed
		enter.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ChatClient client = new ChatClient(loginName.getText()); //get text from JTextField 'login'
					login.setVisible(false);
					login.dispose(); //Need to dispose the frame so it's not still consuming resources
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} 
			}
			
		});
		
		//Add key listener on text field for enter to read login button
		loginName.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
				try {
					ChatClient client = new ChatClient(loginName.getText()); //get text from JTextField 'login'
					login.setVisible(false);
					login.dispose(); //Need to dispose the frame so it's not still consuming resources
					
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
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
		
		
		
		
		
		
	}
}
