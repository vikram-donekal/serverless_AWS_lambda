package com.aws.lambda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.lambda.data.RequestId;
import com.aws.lambda.data.ResponseStudent;
import com.aws.lambda.data.Student;


public class GetStudentById implements RequestHandler<RequestId, ResponseStudent>{
	
	LambdaLogger logger =null;

	@Override
	public ResponseStudent handleRequest(RequestId requestId, Context context) {
		
		logger = context.getLogger();
		logger.log("Request To Get Student By Id Lambda Expression");
		ResponseStudent responseDetails = new ResponseStudent();
		
		if (requestId.getId()==null){
			responseDetails.setMessage("Student Id Validlation Failed ID: "+requestId.getId());
			return responseDetails;
		}
		
				try {					
					responseDetails.setStudent(getStudentById(requestId.getId()));
					if (responseDetails.getStudent().getId()==null){
						responseDetails.setMessage("Student Not Found  ID :"+requestId.getId());
					}else{
						responseDetails.setMessage("SuccessFull Query Execution");
					}
					
					
				} catch (SQLException sqlException) {
					logger.log("Exception  : "+sqlException.getMessage());
					responseDetails.setStudent(null);
					responseDetails.setMessage("Unable to Get Students  "+sqlException);
				}
				return responseDetails;
	}

	private Student getStudentById(Long id ) throws SQLException {
		logger.log("Insdie Get Student By Id Method   ID : "+ id);
		Connection connection = getConnection();
		Statement statement = connection.createStatement();
		String query = getquery(id);
		Student queryResult=new Student();
		ResultSet resultSet = statement.executeQuery(query);
		if (resultSet.next()){
		queryResult.setId(resultSet.getLong("id"));
		queryResult.setName(resultSet.getString("name"));
		queryResult.setPhnumber(resultSet.getString("phnumber"));
		}
		logger.log("Get Student Response : "+queryResult.toString());
	    return queryResult;	
	}

	private String getquery(Long id) {		
		String query = "select * from student where id ="+id;
		logger.log("Get Student Query : "+query);
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
