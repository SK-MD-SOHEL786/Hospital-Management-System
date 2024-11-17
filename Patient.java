package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
	private Connection connection;
	private Scanner sc;
	
	public Patient(Connection connection ,Scanner sc) {
		this.connection =connection;
		this.sc=sc;
	}
	
	public void addPatient() {
		
		System.out.println("Enter Patient Name :");
		String name=sc.next();
		System.out.println("Enter Patient Age :");
		int age=sc.nextInt();
		System.out.println("Enter Patient Gender");
		String gender=sc.next();
		
		try {
			String query ="INSERT INTO patients(Name,Age,Gender) value(?,?,?)";
			PreparedStatement preparedStatement =connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, age);
			preparedStatement.setString(3, gender);
			
			int affectedRows=preparedStatement.executeUpdate();
			
			if(affectedRows>0) {
				System.out.println("Patient Added Succesfully");
			}else {
				System.out.println("Failed to add Patient");
			}	
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void viewPatient() {
		String query="select * from patients";
		
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			ResultSet resultSet=preparedStatement.executeQuery();
			System.out.println("PATIENTS:");
			System.out.println("Patient id  | Name		  | age	 |	gender");
			while(resultSet.next()) {
				int id=resultSet.getInt("id");
				String name=resultSet.getString("name");
				int age=resultSet.getInt("age");
				String gender=resultSet.getString("gender");
				
				System.out.println(id +"	"+name+"	"+age+ "	"+gender);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	
	public boolean getPatientById(int id) {
		String query="select * from patients where id=?";
		
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(query); 
			preparedStatement.setInt(1,id);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				return true;
			}else {
				return false;
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
}
