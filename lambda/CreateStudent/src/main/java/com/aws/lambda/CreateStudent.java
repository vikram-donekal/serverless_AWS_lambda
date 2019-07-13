package com.aws.lambda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.lambda.data.ResponseMessage;
import com.aws.lambda.data.Student;

public class CreateStudent implements RequestHandler<Student, ResponseMessage>{

	LambdaLogger logger =null;
	@Override
	public ResponseMessage handleRequest(Student requestStudent, Context context) {	

		logger=context.getLogger();
		logger.log("Request To CreateStudent Lambda Expression");
		logger.log("INPUT : "+requestStudent.toString());

		ResponseMessage responseDetails = new ResponseMessage();
		try {
			if (requestStudent.getId() == null){				
				responseDetails=validateFeilds(requestStudent);
				if(!(responseDetails.getMessage().isEmpty())){
					return responseDetails;	
				}
				if (SaveStudent(requestStudent)){
					responseDetails.setMessage("SuccessFull Saved Student : Name - >"+requestStudent.getName()+" : phnumber ->"+requestStudent.getPhnumber());
				}else{
					responseDetails.setMessage("Failed to  Saved Student : Name - >"+requestStudent.getName()+" : phnumber ->"+requestStudent.getPhnumber());
				}

			}else{
				
				responseDetails=validateFeilds(requestStudent);
				if(!(responseDetails.getMessage().isEmpty())){
					return responseDetails;	
				}
				
				if (UpdateStudent(requestStudent)){
					responseDetails.setMessage("SuccessFull Updated Student : Name - >"+requestStudent.getName()+" : phnumber ->"+requestStudent.getPhnumber());
				}else{
					responseDetails.setMessage("Failed to  Update Student : Name - >"+requestStudent.getName()+" : phnumber ->"+requestStudent.getPhnumber());
				}
			}

			logger.log("Process Completed");

		} catch (SQLException sqlException) {
			logger.log("Exception  : "+sqlException.getMessage());
			responseDetails.setMessage("Unable to Save Student "+sqlException);
		}
		return responseDetails;

	}


	private Boolean SaveStudent(Student requestStudent) throws SQLException {
		logger.log("Inside Save Student Method");
		Connection connection = getConnection();
		PreparedStatement pstmt  = getSavequery(requestStudent,connection);
		int returnCode = pstmt.executeUpdate();
		if (1 == returnCode){
			return true;
		}else{		
			return false;			
		}
	}


	private Boolean UpdateStudent(Student requestStudent)throws SQLException {
		logger.log("Inside Update Student Method");
		Connection connection = getConnection();
		Statement statement = connection.createStatement();		
		if (verifyStudent(requestStudent,statement)){
			logger.log("Student of ID "+requestStudent.getId()+" Not Found to Update");			
			return false;
		}
		String query = getUpdatequery(requestStudent);
		int returnCode = statement.executeUpdate(query);
		if (1 == returnCode){
			return true;
		}else{		
			return false;			
		}

	}

	private Boolean verifyStudent(Student requestStudent, Statement statement) throws SQLException {
		String query = getquery(requestStudent.getId());
		Student queryResult=new Student();
		ResultSet resultSet = statement.executeQuery(query);
		if (resultSet.next()){
		queryResult.setId(resultSet.getLong("id"));
		}
		if (queryResult.getId() == null){
			return true;
		}
		return false;
	}


	private PreparedStatement  getSavequery(Student requestStudent,Connection conn) throws SQLException {
		logger.log("Inside Get Save Query Method");
		String query = "insert into student(id,name,phnumber) values(nextval('student_seq'),?,?)";	
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, requestStudent.getName());
		pstmt.setString(2, requestStudent.getPhnumber());	
		logger.log("Save query :  "+query);
		return pstmt;
	}
	
	private String getquery(Long id) {		
		String query = "select * from student where id ="+id;
		logger.log("Get Student Query : "+query);
		return query;
	}
	
	private String getUpdatequery(Student requestStudent) {
		logger.log("Inside Get Update Query Method");
		String query = "UPDATE student SET name = '"+requestStudent.getName()+"',phnumber='"+requestStudent.getPhnumber()+"' WHERE id= "+requestStudent.getId();                    
		logger.log("Update query :  "+query);
		return query;
	}
	private Connection getConnection() throws SQLException {
		String url = "jdbc:postgresql://vikramfirstdb.cso8b5hewsy1.ap-south-1.rds.amazonaws.com:5432/Test";
		String username = "postgres";
		String password = "postgres";
		Connection conn = DriverManager.getConnection(url, username, password);
		return conn;
	}


	private ResponseMessage validateFeilds(Student requestStudent){
		logger.log("Validation Of Name and Mobile  Number");
		ResponseMessage outputMessage = new ResponseMessage();
		outputMessage.setMessage("");

		if ( requestStudent.getName()==null || requestStudent.getName().isEmpty()){
			outputMessage.setMessage(" Student Name  is null :  Failed to  Saved Student : Name - >"+requestStudent.getName()+" : phnumber ->"+requestStudent.getPhnumber());
			return outputMessage;
		}
		if (requestStudent.getPhnumber()!=null){
			
			if ((requestStudent.getPhnumber().length()!=10 ) ) {
				outputMessage.setMessage(" Student Mobile Number   validation Failed  : Failed to  Saved Student : Name - >"+requestStudent.getName()+" : phnumber ->"+requestStudent.getPhnumber());
				return outputMessage;
			}	
		}
		if (requestStudent.getPhnumber() ==null){
			outputMessage.setMessage(" Student Mobile Number   is null  : Failed to  Saved Student : Name - >"+requestStudent.getName()+" : phnumber ->"+requestStudent.getPhnumber());
		}
		return outputMessage;

	}


}

