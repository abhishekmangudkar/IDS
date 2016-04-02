package com.grad.ids;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Report extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	Connection sqlcon = null;
	
	private JLabel lblNewLabelHead;
	private JLabel lblNewLabel_PID;
	private JLabel lblNewLabelFName;
	private JLabel lblNewLabelLName;
	private JLabel lblNewLabel_Age;
	private JLabel lblNewLabelBldGrp;
	private JLabel lblNewLabel_BlPre;
	private JLabel lblNewLabel_BlSug;
	private JLabel lblNewLabel_BlCho;
	private JLabel lblNewLabel_HaLev;
	
	private JComboBox comboBox;
	
	/**Combo Box**/
	public void fillComboBox()
	{
		try{
			String comboquery = "Select * from PatientData";
			PreparedStatement comboresult = sqlcon.prepareStatement(comboquery);
			ResultSet combodisplay = comboresult.executeQuery();
			
			while (combodisplay.next()) {
				
				comboBox.addItem(combodisplay.getString("Patient_ID"));
				
			}
			
			comboresult.close();
			combodisplay.close();
		}
		catch(Exception x)
		{}
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Report dialog = new Report();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setTitle("Report Card");
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the dialog.
	 */
	public Report() {
		
		sqlcon = SQL.dbConnector();
		
		setBounds(100, 200, 400, 500);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lblNewLabelHead = new JLabel("Medical Report Card");
		lblNewLabelHead.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
		lblNewLabelHead.setBounds(121, 13, 165, 16);
		contentPanel.add(lblNewLabelHead);
		
		lblNewLabel_PID = new JLabel("Patient ID");
		lblNewLabel_PID.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel_PID.setBounds(101, 56, 77, 16);
		contentPanel.add(lblNewLabel_PID);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
					String comquery = "select * from PatientData where Patient_ID=?";
					PreparedStatement comresult = sqlcon.prepareStatement(comquery);
					comresult.setString(1, (String)comboBox.getSelectedItem());
					
					ResultSet comset = comresult.executeQuery();
					
					while(comset.next())
					{
						lblNewLabelFName.setText(comset.getString("Name"));
						lblNewLabelLName.setText(comset.getString("DOB"));
						lblNewLabel_Age.setText(comset.getString("Mobile_No"));
						lblNewLabelBldGrp.setText(comset.getString("Blood_Group"));
						lblNewLabel_BlPre.setText(comset.getString("Blood_Pressure"));
						lblNewLabel_BlSug.setText(comset.getString("Blood_Sugar"));
						lblNewLabel_BlCho.setText(comset.getString("Cholesterol_Level"));
						lblNewLabel_HaLev.setText(comset.getString("Haemoglobin_Level"));
					}
					
					comset.close();
				}
				catch(Exception ex){
				ex.printStackTrace();}
				
			}
				
		});
		comboBox.setBounds(208, 53, 98, 22);
		contentPanel.add(comboBox);
		
		JLabel lblNewLabelName = new JLabel("Name");
		lblNewLabelName.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		lblNewLabelName.setBounds(126, 105, 56, 16);
		contentPanel.add(lblNewLabelName);
		
		lblNewLabelFName = new JLabel("New label");
		lblNewLabelFName.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNewLabelFName.setBounds(208, 105, 98, 22);
		contentPanel.add(lblNewLabelFName);
		
		JLabel lblNewLabelSurname = new JLabel("Date of Birth");
		lblNewLabelSurname.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		lblNewLabelSurname.setBounds(76, 135, 106, 16);
		contentPanel.add(lblNewLabelSurname);
		
		lblNewLabelLName = new JLabel("New label");
		lblNewLabelLName.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNewLabelLName.setBounds(208, 135, 98, 20);
		contentPanel.add(lblNewLabelLName);
		
		JLabel lblNewLabelAge = new JLabel("Mobile Number");
		lblNewLabelAge.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		lblNewLabelAge.setBounds(54, 163, 119, 22);
		contentPanel.add(lblNewLabelAge);
		
		lblNewLabel_Age = new JLabel("New label");
		lblNewLabel_Age.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNewLabel_Age.setBounds(208, 168, 98, 16);
		contentPanel.add(lblNewLabel_Age);
		
		JLabel lblNewLabelBlGr = new JLabel("Blood Group");
		lblNewLabelBlGr.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		lblNewLabelBlGr.setBounds(76, 198, 98, 22);
		contentPanel.add(lblNewLabelBlGr);
		
		lblNewLabelBldGrp = new JLabel("New label");
		lblNewLabelBldGrp.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNewLabelBldGrp.setBounds(208, 203, 98, 16);
		contentPanel.add(lblNewLabelBldGrp);
		
		JLabel lblNewLabelBlPr = new JLabel("Blood Pressure");
		lblNewLabelBlPr.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		lblNewLabelBlPr.setBounds(64, 243, 108, 16);
		contentPanel.add(lblNewLabelBlPr);
		
		lblNewLabel_BlPre = new JLabel("New label");
		lblNewLabel_BlPre.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNewLabel_BlPre.setBounds(208, 243, 98, 16);
		contentPanel.add(lblNewLabel_BlPre);
		
		JLabel lblNewLabel_BlSu = new JLabel("Blood Sugar");
		lblNewLabel_BlSu.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		lblNewLabel_BlSu.setBounds(78, 278, 96, 22);
		contentPanel.add(lblNewLabel_BlSu);
		
		lblNewLabel_BlSug = new JLabel("New label");
		lblNewLabel_BlSug.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNewLabel_BlSug.setBounds(208, 283, 98, 16);
		contentPanel.add(lblNewLabel_BlSug);
		
		JLabel lblNewLabel_ChLe = new JLabel("Cholesterol Level");
		lblNewLabel_ChLe.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		lblNewLabel_ChLe.setBounds(40, 324, 131, 16);
		contentPanel.add(lblNewLabel_ChLe);
		
		lblNewLabel_BlCho = new JLabel("New label");
		lblNewLabel_BlCho.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNewLabel_BlCho.setBounds(208, 324, 98, 16);
		contentPanel.add(lblNewLabel_BlCho);
		
		JLabel lblNewLabel_HaLe = new JLabel("Haemoglobin Level");
		lblNewLabel_HaLe.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		lblNewLabel_HaLe.setBounds(22, 362, 149, 22);
		contentPanel.add(lblNewLabel_HaLe);
		
		lblNewLabel_HaLev = new JLabel("New label");
		lblNewLabel_HaLev.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblNewLabel_HaLev.setBounds(208, 365, 98, 16);
		contentPanel.add(lblNewLabel_HaLev);
		
		JLabel lblNewLabel = new JLabel("mg/dl");
		lblNewLabel.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		lblNewLabel.setBounds(301, 280, 56, 22);
		contentPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("LDL");
		lblNewLabel_1.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(301, 326, 56, 16);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("g/Litre");
		lblNewLabel_2.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(301, 362, 69, 22);
		contentPanel.add(lblNewLabel_2);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
		
		fillComboBox();
	}
}
