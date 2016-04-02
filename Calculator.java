package com.grad.ids;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Calculator {
	
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Calculator window = new Calculator();
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
	public Calculator() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("First");
		lblNewLabel.setBounds(22, 19, 200, 50);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(60, 25, 135, 38);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Second");
		lblNewLabel_1.setBounds(222, 19, 200, 50);
		frame.getContentPane().add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setBounds(271, 25, 135, 32);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int num1, num2, answer;
				
				try{
					num1 = Integer.parseInt((textField).getText());
					num2 = Integer.parseInt((textField_1).getText());
					
					answer = num1 + num2;
					
					textField_2.setText(Integer.toString(answer));
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, "Enter Valid Number");
				}
				
			}
		});
		btnNewButton.setBounds(22, 123, 97, 25);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Subtract");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int num1, num2, answer;
				
				try{
					num1 = Integer.parseInt((textField).getText());
					num2 = Integer.parseInt((textField_1).getText());
					
					answer = num1 - num2;
					
					textField_2.setText(Integer.toString(answer));
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, "Enter Valid Number");
				}
				
			}
		});
		btnNewButton_1.setBounds(121, 123, 97, 25);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Multiply");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int num1, num2, answer;
				
				try{
					num1 = Integer.parseInt((textField).getText());
					num2 = Integer.parseInt((textField_1).getText());
					
					answer = num1 * num2;
					
					textField_2.setText(Integer.toString(answer));
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, "Enter Valid Number");
				}
				
			}
		});
		btnNewButton_2.setBounds(222, 123, 97, 25);
		frame.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Divide");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int num1, num2, answer;
				
				try{
					num1 = Integer.parseInt((textField).getText());
					num2 = Integer.parseInt((textField_1).getText());
					
					answer = num1 / num2;
					
					textField_2.setText(Integer.toString(answer));
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null, "Enter Valid Number");
				}
				
			}
		});
		btnNewButton_3.setBounds(325, 123, 97, 25);
		frame.getContentPane().add(btnNewButton_3);
		
		JLabel lblNewLabel_2 = new JLabel("Answer is");
		lblNewLabel_2.setBounds(98, 179, 200, 50);
		frame.getContentPane().add(lblNewLabel_2);
		
		textField_2 = new JTextField();
		textField_2.setBounds(162, 191, 200, 25);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
	}

}
