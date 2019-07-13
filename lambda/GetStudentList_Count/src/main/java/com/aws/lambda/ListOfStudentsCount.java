package com.aws.lambda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.lambda.data.ResponseCount;


public class ListOfStudentsCount implements RequestHandler<Object, ResponseCount>{
	
	LambdaLogger logger =null;

	@Override
	public ResponseCount handleRequest(Object arg0, Context context) {
		logger = context.getLogger();
		logger.log("Request To Get Student List Count Lambda Expression");
		ResponseCount responseDetails = new ResponseCount();
				try {
					Long count=getCount();
					responseDetails.setCount(count);
					responseDetails.setMessage("SuccessFull Query Execution");
				} catch (SQLException sqlException) {
					responseDetails.setCount(0);
					responseDetails.setMessage("Unable to Get Students List "+sqlException);
				}
				logger.log("Output : "+responseDetails.toString());
				return responseDetails;
	}

	private Long getCount() throws SQLException {
		logger.log("Inside Get Students Count");
		Connection connection = getConnection();
		Statement statement = connection.createStatement();
		String query = getquery();
		ResultSet resultSet = statement.executeQuery(query);
		resultSet.next();
	    return (long) resultSet.getInt(1);	
	}

	private String getquery() {
		
		String query = "select count(*) from student";
		logger.log("Query to Get Count of Students "+query);
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
