package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {
	private static final String url="jdbc:mysql://127.0.0.1:3306/hospital";		
	private static final String username="root";
	private static final String password="********";
		
		
	public static void main(String[] args) {
		System.out.println("hello world");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		Scanner sc=new Scanner(System.in);
			
		try {
			Connection connection =DriverManager.getConnection(url,username,password);
			Patient patient=new Patient(connection ,sc);
			Doctor doctor=new Doctor(connection);
			while(true) {
				System.out.println("HOSPITAL MANAGEMENT SYSTEM");
				System.out.println("1. Add Patient");
				System.out.println("2. View Patients");
				System.out.println("3. View Doctors");
				System.out.println("4. Book Appoinment");
				System.out.println("5. Exit");
				System.out.println("6. View Appoinments");
				System.out.println("Enter Your Choice");
				int ch=sc.nextInt();
				
				switch(ch) {
					case 1:
						patient.addPatient();
						System.out.println();
						break;
					case 2:
						patient.viewPatient();
						System.out.println();
						break;
					case 3:
						doctor.viewDoctor();
						System.out.println();
						break;
					case 4:
						bookAppoinment(patient,doctor,connection,sc);
						System.out.println();
						break;
					case 5:
						System.exit(0);
					case 6:
						viewAppoinments(connection);
						break;
					default:
						System.out.println("Wrong choice");
						System.out.println();
						break;
				}
				
				
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
			
	}
	
	public static void bookAppoinment(Patient patient ,Doctor doctor,Connection connection ,Scanner sc) {
		System.out.println("Enter Patient ID:");
		int patient_id =sc.nextInt();
		System.out.println("Enter Doctor ID:");
		int doctor_id=sc.nextInt();
		System.out.println("Enter Appoinment Date(YYYY-MM-DD): ");
		String date=sc.next();
		
		if(patient.getPatientById(patient_id) && doctor.getDoctorById(doctor_id)) {
			if(checkDoctorAvability(connection ,doctor_id,date)) {
				String query="insert into appoinments(p_id,d_id,appoinment_date) values(?,?,?)";
				try {
					PreparedStatement preparedStatement=connection.prepareStatement(query);
					preparedStatement.setInt(1,patient_id);
					preparedStatement.setInt(2, doctor_id);
					preparedStatement.setString(3, date);
					int rowsAffected=preparedStatement.executeUpdate();
					if(rowsAffected>0) {
						System.out.println("Appoinment Booked successfully");
					}else {
						System.out.println("Failed to Book an Appoinmnet");
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}else {
				System.out.println("Doctor not Available on this date");
			}
		}else {
			System.out.println("Either Patient ID or Doctors ID is invalid");
		}
	}
	
	public static boolean checkDoctorAvability(Connection connection, int d_id,String date) {
		String query="select count(*) from appoinments where d_id=? and appoinment_date=?";
		try {
			PreparedStatement preparedStatement =connection.prepareStatement(query);
			preparedStatement.setInt(1,d_id);
			preparedStatement.setString(2,date);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				int count=resultSet.getInt(1);
				if(count==0) {
					return true;
				}else {
					return false;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	
	
	public static void viewAppoinments(Connection connection) {
		String query="select * from appoinments";
		try {
			PreparedStatement preparedStatement =connection.prepareStatement(query);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()){
				int p_id=resultSet.getInt("p_id");
				int d_id=resultSet.getInt("d_id");
				String date=resultSet.getString("appoinment_date");
				System.out.println(p_id+"	"+d_id+"	"+date);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
}
