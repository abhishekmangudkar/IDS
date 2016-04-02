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

public class AfterLoggedIn extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JScrollPane scrollPane;
	private JComboBox comboBoxName;
	private JList listName;
	private JLabel lblLabel_Clock;
	
	static String username;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AfterLoggedIn frame = new AfterLoggedIn(username);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection sqlcon = null;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private Label label_loggedin;
	private Label label_logged;
	
	private JTextField textField_id;
	private JTextField textField_name;
	private JTextField textField_age;
	private JTextField textField_uname;
	private JTextField textField_pwd;
	private JButton btnNewButton_2;
	private JButton btnNewButton_3;
	private JTextField textFieldSearch;
	private JMenuBar menuBar;
	private JMenu mnNew;
	
	/**Refresh the table**/
	public void refreshTable()
	{
		try{
			String displayquery = "Select * from Employee";
			PreparedStatement result = sqlcon.prepareStatement(displayquery);
			ResultSet display = result.executeQuery();
			
			table.setModel(DbUtils.resultSetToTableModel(display));
			
			result.close();
			display.close();
		}
		catch(Exception x)
		{}
		
	}
	
	/**Combo Box**/
	public void fillComboBox()
	{
		try{
			String comboquery = "Select * from Employee";
			PreparedStatement comboresult = sqlcon.prepareStatement(comboquery);
			ResultSet combodisplay = comboresult.executeQuery();
			
			while (combodisplay.next()) {
				
				comboBoxName.addItem(combodisplay.getString("Name"));
				
			}
			
			comboresult.close();
			combodisplay.close();
		}
		catch(Exception x)
		{}
	}
	
	/**List**/
	public void loadList(){
		
		try{
			String listquery = "Select * from Employee";
			PreparedStatement listresult = sqlcon.prepareStatement(listquery);
			ResultSet listdisplay = listresult.executeQuery();
			
			DefaultListModel dlm = new DefaultListModel();
			
			while (listdisplay.next()) {
				dlm.addElement(listdisplay.getString("Name"));
			}
			
			listName.setModel(dlm);
			
			listresult.close();
			listdisplay.close();
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
						
						lblLabel_Clock.setText(formattedDate);
						
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
	
	/**
	 * Create the frame.
	 */
	public AfterLoggedIn(String user) {
		
		sqlcon = SQL.dbConnector();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(150, 150, 1200, 850);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mnNew = new JMenu("New");
		mnFile.add(mnNew);
		
		JMenuItem mntmMedicalProject = new JMenuItem("Medical Project");
		mnNew.add(mntmMedicalProject);
		
		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				System.exit(JFrame.EXIT_ON_CLOSE);
				
			}
		});
		mnFile.add(mntmClose);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Display");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
					String displayquery = "Select * from Employee";
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
		btnNewButton.setBounds(73, 51, 97, 25);
		contentPane.add(btnNewButton);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(73, 89, 679, 291);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				try{
					int row = table.getSelectedRow();
					String id = (table.getModel().getValueAt(row, 0)).toString();
					
					String comquery = "select * from Employee where ID='"+id+"' ";
					PreparedStatement comresult = sqlcon.prepareStatement(comquery);
					
					ResultSet comset = comresult.executeQuery();
					
					while(comset.next())
					{
						textField_id.setText(comset.getString("ID"));
						textField_name.setText(comset.getString("Name"));
						textField_age.setText(comset.getString("Age"));
						textField_uname.setText(comset.getString("username"));
						textField_pwd.setText(comset.getString("password"));
					}
					
					comset.close();
				}
				catch(Exception ex){
				ex.printStackTrace();}
				
			}
		});
		scrollPane.setViewportView(table);
		
		lblNewLabel = new JLabel("ID");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(841, 89, 121, 30);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblNewLabel_1.setBounds(841, 142, 121, 30);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Age");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblNewLabel_2.setBounds(841, 191, 121, 30);
		contentPane.add(lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel("Username");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblNewLabel_3.setBounds(841, 234, 121, 30);
		contentPane.add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel("Password");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblNewLabel_4.setBounds(841, 277, 121, 30);
		contentPane.add(lblNewLabel_4);
		
		textField_id = new JTextField();
		textField_id.setBounds(988, 86, 149, 33);
		contentPane.add(textField_id);
		textField_id.setColumns(10);
		
		textField_name = new JTextField();
		textField_name.setBounds(988, 142, 149, 30);
		contentPane.add(textField_name);
		textField_name.setColumns(10);
		
		textField_age = new JTextField();
		textField_age.setBounds(988, 191, 149, 30);
		contentPane.add(textField_age);
		textField_age.setColumns(10);
		
		textField_uname = new JTextField();
		textField_uname.setBounds(988, 234, 149, 31);
		contentPane.add(textField_uname);
		textField_uname.setColumns(10);
		
		textField_pwd = new JTextField();
		textField_pwd.setBounds(988, 277, 149, 30);
		contentPane.add(textField_pwd);
		textField_pwd.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("ENTER");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
					
					if(username.contains("Admin"))
					{
						String displayinsertquery = "INSERT INTO Employee (ID,Name,Age,username,password) values (?,?,?,?,?)";
						PreparedStatement insertresult = sqlcon.prepareStatement(displayinsertquery);
						
						insertresult.setString(1, textField_id.getText());
						insertresult.setString(2, textField_name.getText());
						insertresult.setString(3, textField_age.getText());
						insertresult.setString(4, textField_uname.getText());
						insertresult.setString(5, textField_pwd.getText());
						
						insertresult.execute();
	
						JOptionPane.showMessageDialog(null, "Data Saved");
						
						insertresult.close();
					}
					else
					{
						JOptionPane.showMessageDialog(null,label_loggedin.getText()+" not Allowed to Create..!!");
					}
				}
				catch(Exception x)
				{x.printStackTrace();}
				refreshTable();
			}
		});
		btnNewButton_1.setBounds(988, 325, 149, 30);
		contentPane.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("UPDATE");
		btnNewButton_2.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				try{
					
					if(username.contains("Admin"))
					{
						String upquery = "update Employee set ID='"+textField_id.getText()+"', Name='"+textField_name.getText()+"', Age='"+textField_age.getText()+"', username='"+textField_uname.getText()+"', password='"+textField_pwd.getText()+"' where ID='"+textField_id.getText()+"'";
						PreparedStatement updateresult = sqlcon.prepareStatement(upquery);
						updateresult.execute();
						
						JOptionPane.showMessageDialog(null, "Data Updated");
						updateresult.close();
					}
					else
					{
						JOptionPane.showMessageDialog(null,label_loggedin.getText()+" not Allowed to Update..!!");
					}
				}
				catch(Exception ex)
				{ex.printStackTrace();}
				refreshTable();
			}
		});
		btnNewButton_2.setBounds(988, 368, 149, 30);
		contentPane.add(btnNewButton_2);
		
		btnNewButton_3 = new JButton("DELETE");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int action = JOptionPane.showConfirmDialog(null, "Do you want to delete?","Delete",JOptionPane.YES_NO_OPTION);
				
				if(action == 0){
				try{
					
					if(username.contains("Admin"))
					{
						String deletequery = "Delete from Employee where ID='"+textField_id.getText()+"'";
						PreparedStatement deleteresult = sqlcon.prepareStatement(deletequery);
						deleteresult.execute();
						
						JOptionPane.showMessageDialog(null, "Data Deleted");
						
						deleteresult.close();
					}
					else
					{
						JOptionPane.showMessageDialog(null,label_loggedin.getText()+" not Allowed to Delete..!!");
					}
				}
				catch(Exception ex){
				ex.printStackTrace();}
				}
				refreshTable();
			}
		});
		btnNewButton_3.setBounds(988, 411, 149, 30);
		contentPane.add(btnNewButton_3);
		
		comboBoxName = new JComboBox();
		comboBoxName.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				try{
					String comquery = "select * from Employee where Name=?";
					PreparedStatement comresult = sqlcon.prepareStatement(comquery);
					comresult.setString(1, (String)comboBoxName.getSelectedItem());
					
					ResultSet comset = comresult.executeQuery();
					
					while(comset.next())
					{
						textField_id.setText(comset.getString("ID"));
						textField_name.setText(comset.getString("Name"));
						textField_age.setText(comset.getString("Age"));
						textField_uname.setText(comset.getString("username"));
						textField_pwd.setText(comset.getString("password"));
					}
					
					comset.close();
				}
				catch(Exception ex){
				ex.printStackTrace();}
				
			}
		});
		comboBoxName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBoxName.setBounds(193, 50, 115, 25);
		contentPane.add(comboBoxName);
		
		listName = new JList();
		listName.setBounds(76, 417, 133, 217);
		contentPane.add(listName);
		
		JButton btnNewButton_4 = new JButton("Load Value");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DefaultListModel dlm = new DefaultListModel();
				dlm.addElement("Abhishek");
				dlm.addElement("Abhi");
				dlm.addElement("Vaibhav");
				dlm.addElement("Bittu");
				dlm.addElement("Rishi");
				listName.setModel(dlm);
				
			}
		});
		btnNewButton_4.setBounds(73, 663, 97, 25);
		contentPane.add(btnNewButton_4);
		
		textFieldSearch = new JTextField();
		textFieldSearch.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				
				try{
					String searchquery = "select * from Employee where Name=?";
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
		textFieldSearch.setBounds(244, 415, 159, 26);
		contentPane.add(textFieldSearch);
		textFieldSearch.setColumns(10);
		
		lblLabel_Clock = new JLabel("");
		lblLabel_Clock.setFont(new Font("Lucida Sans", Font.BOLD, 15));
		lblLabel_Clock.setBounds(947, 0, 201, 33);
		contentPane.add(lblLabel_Clock);
				
		label_logged = new Label("Logged in as :");
		label_logged.setFont(new Font("Dialog", Font.BOLD, 15));
		label_logged.setBounds(748, 0, 105, 33);
		contentPane.add(label_logged);
		
		username = user;
		
		label_loggedin = new Label(username);
		label_loggedin.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 15));
		label_loggedin.setBounds(851, 0, 131, 33);
		contentPane.add(label_loggedin);
		
		refreshTable();
		fillComboBox();
		loadList();
		clock();
	}
}
