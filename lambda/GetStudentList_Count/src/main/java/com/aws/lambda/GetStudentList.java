package com.aws.lambda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.lambda.data.ResponseStudentList;
import com.aws.lambda.data.Student;

public class GetStudentList implements RequestHandler<Object, ResponseStudentList>{
	
	LambdaLogger logger =null;

	@Override
	public ResponseStudentList handleRequest(Object arg0, Context context) {
		logger = context.getLogger();
		logger.log("Request To Get Student List Lambda Expression");
		ResponseStudentList responseDetails = new ResponseStudentList();
				try {
					List<Student> queryList=getDetails();
					responseDetails.setStudentList(queryList);
					responseDetails.setMessage("SuccessFull Query Execution");
				} catch (SQLException sqlException) {
					logger.log("Exception  : "+sqlException.getMessage());
					responseDetails.setStudentList(null);
					responseDetails.setMessage("Unable to Get Students List "+sqlException);
				}
				logger.log("Out put of Student List : "+responseDetails.toString());
				return responseDetails;
	}

	private List<Student> getDetails() throws SQLException {
		logger.log("Inside Get Student List Detail");
		Connection connection = getConnection();
		Statement statement = connection.createStatement();
		String query = getquery();
		ResultSet resultSet = statement.executeQuery(query);
		List<Student> studentList=new ArrayList<Student>();
		while(resultSet.next()) {
			Student student = new Student();      
			student.setId(resultSet.getLong("id"));
			student.setName(resultSet.getString("name"));
			student.setPhnumber(resultSet.getString("phnumber"));
			studentList.add(student);
			} 
		return studentList;		
	}

	private String getquery() {
		
		String query = "select * from student";
		logger.log("Query to Get Student List  : "+query);
		return query;
	}

	private Connection getConnection() throws SQLException {
				String url = "jdbc:postgresql://vikramfirstdb.cso8b5hewsy1.ap-south-1.rds.amazonaws.com:5432/Test";
				String username = "postgres";
				String password = "postgres";
				Connection conn = DriverManager.getConnection(url, username, password);
				return conn;
	}

}

