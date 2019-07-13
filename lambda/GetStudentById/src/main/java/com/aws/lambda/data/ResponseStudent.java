package com.aws.lambda.data;

public class ResponseStudent {
	
	private Student student;
	private String message;
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "ResponseStudent [student=" + student.toString() + ", message=" + message + "]";
	}
	public ResponseStudent() {
		super();
		
	}
	

}
