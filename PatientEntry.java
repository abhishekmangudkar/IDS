package com.grad.ids;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import net.proteanit.sql.DbUtils;

import org.sqlite.SQLite;
import org.sqlite.SQLiteConnection;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import java.awt.Label;

public class PatientEntry extends JFrame {
	
	Connection sqlcon = null;

	private JPanel contentPane;
	private JTextField textFieldFirst;
	private JTextField textFieldDob;
	private JTextField textFieldMobile;
	private JTextField textFieldBloodGr;
	private JTextField textFieldBloodPre;
	private JTextField textFieldBloodSug;
	private JTextField textFieldCholesterol;
	private JTextField textFieldHaemoglobin;
	private JTextField textFieldAddress;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PatientEntry frame = new PatientEntry();
					frame.setTitle("Patient Entry");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PatientEntry() {
		
		sqlcon = SQL.dbConnector();
			
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 292);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFirstLabel = new JLabel("First Name");
		lblFirstLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblFirstLabel.setBounds(56, 44, 86, 16);
		contentPane.add(lblFirstLabel);
		
		textFieldFirst = new JTextField();
		textFieldFirst.setBounds(154, 41, 145, 22);
		contentPane.add(textFieldFirst);
		textFieldFirst.setColumns(10);
		
		JLabel lblLastLabel = new JLabel("Date of Birth");
		lblLastLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblLastLabel.setBounds(42, 78, 100, 16);
		contentPane.add(lblLastLabel);
		
		textFieldDob = new JTextField();
		textFieldDob.setBounds(154, 76, 145, 22);
		contentPane.add(textFieldDob);
		textFieldDob.setColumns(10);
		
		JLabel lblAgeLabel = new JLabel("Mobile Number");
		lblAgeLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblAgeLabel.setBounds(23, 110, 119, 22);
		contentPane.add(lblAgeLabel);
		
		textFieldMobile = new JTextField();
		textFieldMobile.setBounds(154, 111, 145, 22);
		contentPane.add(textFieldMobile);
		textFieldMobile.setColumns(10);
		
		JButton btnEnter = new JButton("ENTER");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
					String displayinsertquery = "INSERT INTO PatientData (Name,DOB,Mobile_No,Patient_ID,Blood_Group,Blood_Pressure,Blood_Sugar,Cholesterol_Level,Haemoglobin_Level,Address) values (?,?,?,?,?,?,?,?,?,?)";
					PreparedStatement insertresult = sqlcon.prepareStatement(displayinsertquery);
					
					insertresult.setString(1, textFieldFirst.getText());
					insertresult.setString(2, textFieldDob.getText());
					insertresult.setString(3, textFieldMobile.getText());
					insertresult.setString(4, textFieldFirst.getText().substring(0, 2).toUpperCase()+""+textFieldDob.getText().substring(7, 10)+""+textFieldMobile.getText().substring(7, 9)+""+textFieldBloodPre.getText().substring(0, 2));
					insertresult.setString(5, textFieldBloodGr.getText());
					insertresult.setString(6, textFieldBloodPre.getText());
					insertresult.setString(7, textFieldBloodSug.getText());
					insertresult.setString(8, textFieldCholesterol.getText());
					insertresult.setString(9, textFieldHaemoglobin.getText());
					insertresult.setString(10, textFieldAddress.getText());
					insertresult.execute();
	
					JOptionPane.showMessageDialog(null, "Data Saved");
					
					insertresult.close();
				}
				catch(Exception x)
				{x.printStackTrace();}
			}
		});
		btnEnter.setBounds(525, 201, 145, 31);
		contentPane.add(btnEnter);
		
		JLabel lblNewLabel = new JLabel("Blood Group");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(48, 148, 94, 16);
		contentPane.add(lblNewLabel);
		
		textFieldBloodGr = new JTextField();
		textFieldBloodGr.setBounds(154, 146, 145, 22);
		contentPane.add(textFieldBloodGr);
		textFieldBloodGr.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Blood Pressure");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(414, 44, 116, 16);
		contentPane.add(lblNewLabel_1);
		
		textFieldBloodPre = new JTextField();
		textFieldBloodPre.setBounds(554, 42, 116, 22);
		contentPane.add(textFieldBloodPre);
		textFieldBloodPre.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("eg. 95-125");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(682, 43, 108, 18);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Blood Sugar");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_3.setBounds(436, 77, 94, 19);
		contentPane.add(lblNewLabel_3);
		
		textFieldBloodSug = new JTextField();
		textFieldBloodSug.setBounds(554, 76, 116, 22);
		contentPane.add(textFieldBloodSug);
		textFieldBloodSug.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("< 100 mg/DL");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_4.setBounds(682, 77, 108, 19);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Cholestrol Level");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_5.setBounds(414, 110, 126, 16);
		contentPane.add(lblNewLabel_5);
		
		textFieldCholesterol = new JTextField();
		textFieldCholesterol.setBounds(554, 108, 116, 22);
		contentPane.add(textFieldCholesterol);
		textFieldCholesterol.setColumns(10);
		
		textFieldHaemoglobin = new JTextField();
		textFieldHaemoglobin.setBounds(554, 146, 116, 22);
		contentPane.add(textFieldHaemoglobin);
		textFieldHaemoglobin.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("Haemoglobin Level");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_6.setBounds(396, 147, 151, 19);
		contentPane.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("< 190 LDL");
		lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_7.setBounds(682, 114, 108, 16);
		contentPane.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("< 13.5 g/Litre");
		lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_8.setBounds(682, 149, 126, 19);
		contentPane.add(lblNewLabel_8);
		
		JLabel lblNewLabel_add = new JLabel("Address");
		lblNewLabel_add.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_add.setBounds(76, 188, 66, 16);
		contentPane.add(lblNewLabel_add);
		
		textFieldAddress = new JTextField();
		textFieldAddress.setBounds(154, 186, 145, 22);
		contentPane.add(textFieldAddress);
		textFieldAddress.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("dd/MM/YYYY");
		lblNewLabel_9.setBounds(311, 79, 77, 16);
		contentPane.add(lblNewLabel_9);
	}
}
