package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {
	private Connection connection;
	
	public Doctor(Connection connection) {
		this.connection =connection;
		
	}
	

	
	public void viewDoctor() {
		String query="select * from doctors";
		
		try {
			PreparedStatement preparedStatement=connection.prepareStatement(query);
			ResultSet resultSet=preparedStatement.executeQuery();
			System.out.println("DOCTORS :");
			System.out.println("Doctor id  | Name		  | Specialization");
			while(resultSet.next()) {
				
				String name=resultSet.getString("name");
				int id=resultSet.getInt("id");
				String specialization=resultSet.getString("specialization");
				
				System.out.println(id +"	"+name+"	"+specialization);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	
	public boolean getDoctorById(int id) {
		String query="select * from doctors where id=?";
		
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
