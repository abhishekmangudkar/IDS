package com.grad.ids;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

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

import javax.swing.JScrollBar;

public class Dashboard extends JFrame {
	
	Connection sqlcon = null;
	
	private JFrame frame;
	
	private JLabel lblNewLabel_pid;
	private JLabel lblNewLabel_Name;
	private JLabel lblNewLabel_dob;
	private JLabel lblNewLabel_mobile;
	private JLabel lblNewLabel_blpre;
	private JLabel lblNewLabel_blsug;
	private JLabel lblNewLabel_cholesterol;
	private JLabel lblNewLabel_blgrp;
	private JLabel lblNewLabel_haemo;
	private JLabel lblNewLabel_clock;
	private JLabel lblNewLabel_logged;
	private JLabel lblNewLabel_loggedinas;
	
	static String username;
	
	private JPanel contentPane;
	private JTable table;
	private JTextField textField_pid;
	private JTextField textField_Name;
	private JTextField textField_dob;
	private JTextField textField_mobile;
	private JTextField textField_blpre;
	private JTextField textField_blsug;
	private JTextField textField_cholesterol;
	private JTextField textField_haemo;
	private JTextField textField_blgrp;
	private JButton btnNewButton_Refresh;
	private JScrollPane scrollPane;
	private JTextField textFieldSearch;
	private JLabel lblNewLabel_3;
	private JTextField textFieldAddress;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dashboard frame = new Dashboard(username);
					frame.setTitle("Dashboard");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**Refresh the table**/
	public void refreshTable()
	{
		try{
			String displayquery = "Select Patient_ID,Name,DOB,Mobile_No,Blood_Group,Blood_Pressure,Blood_Sugar,Cholesterol_Level,Haemoglobin_Level,Address from PatientData";
			PreparedStatement result = sqlcon.prepareStatement(displayquery);
			ResultSet display = result.executeQuery();
			
			table.setModel(DbUtils.resultSetToTableModel(display));
			
			result.close();
			display.close();
		}
		catch(Exception x)
		{}
		
	}
	
	public void clock()
	{
		Thread clock = new Thread()
		{
			public void run()
			{
				try {
						for(;;)
						{

						SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy hh:mm:ss aa");
						String formattedDate = dateFormat.format(new Date()).toString();
						
						lblNewLabel_clock.setText(formattedDate);
						
						Thread.sleep(900);
						}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		clock.start();
	}
	
	public void EmailSend()
	{
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("secureidsystem", "secureidsystem@123");
                    }
                });

        try {
        	
        	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    		Date date = new Date();
    		
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("secureidsystem@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("abhiqk311@gmail.com"));
            message.setSubject("Intrusion Alert at "+dateFormat.format(date));
            message.setText("Dear Admin,"
                    + "\n\n"+username+" tried to enter restricted area. Kindly take appropriate action to maintain safety & integrity of the System."+"\n\nRegards,\nIT Security Team");
            
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
	}
	
	/**
	 * Create the frame.
	 */
	public Dashboard(String user) {
		
		sqlcon = SQL.dbConnector();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 200, 1125, 550);
		
		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(35, 53, 1029, 250);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setFont(new Font("Trebuchet MS", Font.PLAIN, 15));
		
		//*******Auto Population of data after clicking on any record*************//
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try{
					int row = table.getSelectedRow();
					String id = (table.getModel().getValueAt(row, 0)).toString();
					
					String comquery = "select Patient_ID,Name,DOB,Mobile_No,Blood_Group,Blood_Pressure,Blood_Sugar,Cholesterol_Level,Haemoglobin_Level,Address from PatientData where Patient_ID='"+id+"'";
					PreparedStatement comresult = sqlcon.prepareStatement(comquery);
					
					ResultSet comset = comresult.executeQuery();
					
					while(comset.next())
					{
						textField_pid.setText(comset.getString("Patient_ID"));
						textField_Name.setText(comset.getString("Name"));
						textField_dob.setText(comset.getString("DOB"));
						textField_mobile.setText(comset.getString("Mobile_No"));
						textField_blgrp.setText(comset.getString("Blood_Group"));
						textField_blsug.setText(comset.getString("Blood_Sugar"));
						textField_blpre.setText(comset.getString("Blood_Pressure"));
						textField_cholesterol.setText(comset.getString("Cholesterol_Level"));
						textField_haemo.setText(comset.getString("Haemoglobin_Level"));
						textFieldAddress.setText(comset.getString("Address"));
					}
					
					comset.close();
				}
				catch(Exception ex){
				ex.printStackTrace();}
				
			}
		});
		
		lblNewLabel_pid = new JLabel("Patient ID");
		lblNewLabel_pid.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_pid.setBounds(35, 326, 78, 16);
		contentPane.add(lblNewLabel_pid);
		
		textField_pid = new JTextField();
		textField_pid.setBounds(123, 323, 116, 22);
		contentPane.add(textField_pid);
		textField_pid.setColumns(10);
		
		lblNewLabel_Name = new JLabel("First Name");
		lblNewLabel_Name.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_Name.setBounds(274, 326, 92, 16);
		contentPane.add(lblNewLabel_Name);
		
		textField_Name = new JTextField();
		textField_Name.setBounds(366, 323, 116, 22);
		contentPane.add(textField_Name);
		textField_Name.setColumns(10);
		
		lblNewLabel_dob = new JLabel("Date of Birth");
		lblNewLabel_dob.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_dob.setBounds(494, 326, 95, 16);
		contentPane.add(lblNewLabel_dob);
		
		textField_dob = new JTextField();
		textField_dob.setBounds(601, 324, 92, 22);
		contentPane.add(textField_dob);
		textField_dob.setColumns(10);
		
		lblNewLabel_mobile = new JLabel("Mobile");
		lblNewLabel_mobile.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_mobile.setBounds(705, 326, 56, 19);
		contentPane.add(lblNewLabel_mobile);
		
		textField_mobile = new JTextField();
		textField_mobile.setBounds(760, 323, 103, 22);
		contentPane.add(textField_mobile);
		textField_mobile.setColumns(10);
		
		lblNewLabel_blpre = new JLabel("Blood Pressure");
		lblNewLabel_blpre.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_blpre.setBounds(12, 373, 121, 16);
		contentPane.add(lblNewLabel_blpre);
		
		textField_blpre = new JTextField();
		textField_blpre.setBounds(135, 371, 116, 22);
		contentPane.add(textField_blpre);
		textField_blpre.setColumns(10);
		
		lblNewLabel_blsug = new JLabel("Blood Sugar");
		lblNewLabel_blsug.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_blsug.setBounds(274, 371, 92, 20);
		contentPane.add(lblNewLabel_blsug);
		
		textField_blsug = new JTextField();
		textField_blsug.setBounds(376, 370, 94, 22);
		contentPane.add(textField_blsug);
		textField_blsug.setColumns(10);
		
		lblNewLabel_cholesterol = new JLabel("Cholesterol Level");
		lblNewLabel_cholesterol.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_cholesterol.setBounds(513, 373, 131, 16);
		contentPane.add(lblNewLabel_cholesterol);
		
		textField_cholesterol = new JTextField();
		textField_cholesterol.setBounds(653, 370, 92, 22);
		contentPane.add(textField_cholesterol);
		textField_cholesterol.setColumns(10);
		
		lblNewLabel_haemo = new JLabel("Haemoglobin Level");
		lblNewLabel_haemo.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_haemo.setBounds(785, 371, 146, 20);
		contentPane.add(lblNewLabel_haemo);
		
		textField_haemo = new JTextField();
		textField_haemo.setBounds(933, 370, 85, 22);
		contentPane.add(textField_haemo);
		textField_haemo.setColumns(10);
		
		lblNewLabel_blgrp = new JLabel("Blood Group");
		lblNewLabel_blgrp.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_blgrp.setBounds(875, 326, 91, 16);
		contentPane.add(lblNewLabel_blgrp);
		
		textField_blgrp = new JTextField();
		textField_blgrp.setBounds(978, 324, 70, 22);
		contentPane.add(textField_blgrp);
		textField_blgrp.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("mg/dl");
		lblNewLabel.setBounds(471, 373, 56, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("LDL");
		lblNewLabel_1.setBounds(747, 373, 28, 16);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("g/Litre");
		lblNewLabel_2.setBounds(1020, 373, 44, 16);
		contentPane.add(lblNewLabel_2);
		
		JButton btnNewButton_Update = new JButton("Update");
		btnNewButton_Update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
					
					if(username.contains("Admin"))
					{
						String upquery = "update PatientData set Patient_ID='"+textField_pid.getText()+"', Name='"+textField_Name.getText()+"', DOB='"+textField_dob.getText()+"', Mobile_No='"+textField_mobile.getText()+"', Blood_Group='"+textField_blgrp.getText()+"', Blood_Sugar='"+textField_blsug.getText()+"', Blood_Pressure='"+textField_blpre.getText()+"', Cholesterol_Level='"+textField_cholesterol.getText()+"', Haemoglobin_Level='"+textField_haemo.getText()+"' where Patient_ID='"+textField_pid.getText()+"'";
						PreparedStatement updateresult = sqlcon.prepareStatement(upquery);
						updateresult.execute();
						
						JOptionPane.showMessageDialog(null, "Data Updated");
						updateresult.close();
					}
					else
					{
						JOptionPane.showMessageDialog(null,lblNewLabel_loggedinas.getText()+" not Allowed to Update..!!");
						EmailSend();
					}
				}
				catch(Exception ex)
				{ex.printStackTrace();}
				refreshTable();
			}
				
		});
		btnNewButton_Update.setBounds(471, 435, 97, 25);
		contentPane.add(btnNewButton_Update);
		
		JButton btnNewButton_Delete = new JButton("Delete");
		btnNewButton_Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int action = JOptionPane.showConfirmDialog(null, "Do you want to delete?","Delete",JOptionPane.YES_NO_OPTION);
				
				if(action == 0){
				try{
					
					if(username.contains("Admin"))
					{
						String displayinsertquery = "INSERT INTO Backup_PatientData (Patient_ID,Name,DOB,Mobile_No,Blood_Group,Blood_Pressure,Blood_Sugar,Cholesterol_Level,Haemoglobin_Level,Address) values (?,?,?,?,?,?,?,?,?,?)";
						PreparedStatement insertresult = sqlcon.prepareStatement(displayinsertquery);
						
						insertresult.setString(1, textField_pid.getText());
						insertresult.setString(2, textField_Name.getText());
						insertresult.setString(3, textField_dob.getText());
						insertresult.setString(4, textField_mobile.getText());
						insertresult.setString(5, textField_blgrp.getText());
						insertresult.setString(6, textField_blpre.getText());
						insertresult.setString(7, textField_blsug.getText());
						insertresult.setString(8, textField_cholesterol.getText());
						insertresult.setString(9, textField_haemo.getText());
						insertresult.setString(10, textFieldAddress.getText());
						insertresult.execute();
						
						insertresult.close();
						
						String deletequery = "Delete from PatientData where Patient_ID='"+textField_pid.getText()+"'";
						PreparedStatement deleteresult = sqlcon.prepareStatement(deletequery);
						deleteresult.execute();
						
						JOptionPane.showMessageDialog(null, "Data Deleted");
						
						deleteresult.close();
					}
					else
					{
						JOptionPane.showMessageDialog(null,lblNewLabel_loggedinas.getText()+" not Allowed to Delete..!!");
						EmailSend();
					}
				}
				catch(Exception ex){
				ex.printStackTrace();}
				}
				refreshTable();
			}
				
		});
		btnNewButton_Delete.setBounds(601, 435, 97, 25);
		contentPane.add(btnNewButton_Delete);
		
		lblNewLabel_logged = new JLabel("Logged in as:");
		lblNewLabel_logged.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_logged.setBounds(601, 17, 107, 23);
		contentPane.add(lblNewLabel_logged);
		
		username = user;
		
		lblNewLabel_loggedinas = new JLabel(username);
		lblNewLabel_loggedinas.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 15));
		lblNewLabel_loggedinas.setBounds(706, 12, 131, 33);
		contentPane.add(lblNewLabel_loggedinas);
		
		lblNewLabel_clock = new JLabel("");
		lblNewLabel_clock.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_clock.setBounds(846, 13, 187, 33);
		contentPane.add(lblNewLabel_clock);
		
		btnNewButton_Refresh = new JButton("Refresh");
		btnNewButton_Refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
					String displayquery = "select Patient_ID,Name,DOB,Mobile_No,Blood_Group,Blood_Pressure,Blood_Sugar,Cholesterol_Level,Haemoglobin_Level,Address from PatientData";
					PreparedStatement result = sqlcon.prepareStatement(displayquery);
					ResultSet display = result.executeQuery();
					
					table.setModel(DbUtils.resultSetToTableModel(display));
					
					result.close();
					display.close();
				}
				catch(Exception x)
				{}
				
			}
		});
		btnNewButton_Refresh.setBounds(342, 435, 97, 25);
		contentPane.add(btnNewButton_Refresh);
		
		JButton btnNewButton_logout = new JButton("Generate Report");
		btnNewButton_logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Report report = new Report();
				report.setVisible(true);
			}
		});
		btnNewButton_logout.setBounds(733, 435, 131, 25);
		contentPane.add(btnNewButton_logout);
		
		textFieldSearch = new JTextField();
		textFieldSearch.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				
				try{
					String searchquery = "select Patient_ID,Name,DOB,Mobile_No,Blood_Group,Blood_Pressure,Blood_Sugar,Cholesterol_Level,Haemoglobin_Level,Address from PatientData where Name=?";
					PreparedStatement searchresult = sqlcon.prepareStatement(searchquery);
					searchresult.setString(1, textFieldSearch.getText());
					
					ResultSet searchset = searchresult.executeQuery();
					
					table.setModel(DbUtils.resultSetToTableModel(searchset));
					
					searchset.close();
				}
				catch(Exception ex){
				ex.printStackTrace();}
				
			}
		});
		textFieldSearch.setBounds(135, 18, 116, 22);
		contentPane.add(textFieldSearch);
		textFieldSearch.setColumns(10);
		
		JLabel lblNewLabel_search = new JLabel("Search : ");
		lblNewLabel_search.setBounds(77, 21, 56, 16);
		contentPane.add(lblNewLabel_search);
		
		lblNewLabel_3 = new JLabel("Address");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_3.setBounds(53, 418, 60, 16);
		contentPane.add(lblNewLabel_3);
		
		textFieldAddress = new JTextField();
		textFieldAddress.setBounds(123, 416, 157, 22);
		contentPane.add(textFieldAddress);
		textFieldAddress.setColumns(10);
		
		refreshTable();
		clock();
	}
}
