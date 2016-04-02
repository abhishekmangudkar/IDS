package com.grad.ids;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Label;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginWindow {

	private JFrame frame;
	private JTextField textField;
	private JPasswordField passwordField;
	
	Connection connect = null;	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow window = new LoginWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginWindow() {
		initialize();
		connect = SQL.dbConnector();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(750, 400, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("Medical Reporting System");
		
		textField = new JTextField();
		textField.setBounds(240, 85, 130, 22);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(238, 157, 132, 22);
		frame.getContentPane().add(passwordField);
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setBounds(240, 65, 63, 16);
		frame.getContentPane().add(lblUserName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(240, 137, 63, 16);
		frame.getContentPane().add(lblPassword);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
					String query = "Select * from users where username=? and password=?";
					PreparedStatement pst = connect.prepareStatement(query);
					
					pst.setString(1, textField.getText());
					pst.setString(2, passwordField.getText());
					
					ResultSet rst = pst.executeQuery();
					
					String user = textField.getText();
					
					int count = 0;
					while(rst.next())
					{
						count = count + 1;
					}
					if(count == 1)
					{
						frame.dispose();
						
						Dashboard dash = new Dashboard(user);
						dash.setVisible(true);
						
						/*AfterLoggedIn afterWindow = new AfterLoggedIn(user);
						afterWindow.setVisible(true);*/						
					}
					else if(count > 1)
					{
						JOptionPane.showMessageDialog(null, "Duplicate Username and password");
					}
					else
					{
						JOptionPane.showMessageDialog(null, "Username and password is not correct");
					}
					rst.close();
					pst.close();
				}
				catch(Exception x)
				{
					JOptionPane.showMessageDialog(null, x);
				}
				finally{
					try{
						
					}
					catch(Exception y)
					{
						
					}
				}
				
			}
		});
		btnNewButton.setBounds(273, 202, 97, 25);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/main_logo.gif")).getImage();
		lblNewLabel.setIcon(new ImageIcon(img));
		lblNewLabel.setBounds(12, 13, 230, 227);
		frame.getContentPane().add(lblNewLabel);
		
		/*JLabel login_label = new JLabel("");
		Image loginimg = new ImageIcon(this.getClass().getResource("/tick.png")).getImage();
		login_label.setIcon(new ImageIcon(loginimg));
		login_label.setBounds(277, 202, 56, 16);
		frame.getContentPane().add(login_label);*/
	}

}
