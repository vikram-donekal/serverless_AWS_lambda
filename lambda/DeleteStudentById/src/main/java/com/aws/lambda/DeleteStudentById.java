package com.aws.lambda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.lambda.data.RequestId;
import com.aws.lambda.data.ResponseMessage;


public class DeleteStudentById implements RequestHandler<RequestId, ResponseMessage>{
	
	LambdaLogger logger=null;
	

	@Override
	public ResponseMessage handleRequest(RequestId requestId, Context context) {
		logger = context.getLogger();
		logger.log("Request to Delete Student  Id :"+requestId);
		ResponseMessage responseDetails = new ResponseMessage();
		if (requestId.getId()==null){
			responseDetails.setMessage("Student Id Validlation Failed ID: "+requestId.getId());
			return responseDetails;
		}
		
		
		try {
			Boolean deletion= deleteStudetById(requestId.getId());
			if (deletion){
				logger.log("Student ID : "+requestId+" Has been Deleted");
				responseDetails.setMessage("SuccessFull Query Execution and Deleted Student id : "+requestId);
			}else{
				logger.log("Student ID : "+requestId+" Not Found");
				responseDetails.setMessage("Student  ID : "+requestId+" Not Found .Cant Delete Student. Operation Failed");
			}

		} catch (SQLException sqlException) {
			logger.log("Exception  : "+sqlException.getMessage());
			responseDetails.setMessage("Unable to Delete Student of Id : "+requestId+"\n"+"Exception : "+sqlException);
		}
		return responseDetails;
	}

	private Boolean deleteStudetById(Long requestId) throws SQLException {
		logger.log("Inside  Delete Student By Id Method "+requestId);
		
		Connection connection = getConnection();
		Statement statement = connection.createStatement();
		String query = getquery(requestId);
		int resultSet = statement.executeUpdate(query);
		if (1== resultSet){
			return true;
		}
		else{
			return false;
		}

	}

	private String getquery(Long id ) {

		String query = "DELETE FROM student WHERE id = "+id;
		logger.log("Delete Student By ID : "+query);
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
