package com.grad.ids;

import java.sql.*;
import javax.swing.*;

public class SQL {
	
	Connection conn = null;
	
	public static Connection dbConnector(){
		try{
			
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Abhishek\\workspace\\EmpDetails.sqlite");
			return conn;
			
		}catch(Exception e){
			
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}

}
