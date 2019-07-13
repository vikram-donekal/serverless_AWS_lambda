package com.aws.lambda.data;

import java.util.List;
import java.util.function.Consumer;

public class ResponseStudentList {
	private List<Student> StudentList;
	private String message;
	

	public ResponseStudentList(List<Student> studentList, String message) {
		super();
		StudentList = studentList;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Student> getStudentList() {
		return StudentList;
	}

	public void setStudentList(List<Student> studentList) {
		StudentList = studentList;
	}

	public ResponseStudentList() {
		
	}

	@Override
	public String toString() {

		StringBuffer FinalString =new StringBuffer();
		StudentList.stream().forEach(new Consumer<Student>() {
			@Override
			public void accept(Student student) {
				FinalString.append("Student [id=" + student.getId() + ", name=" + student.getName()+ ", phnumber=" + student.getPhnumber() + "]");
				FinalString.append("\n");						
			}
		});
		FinalString.append("\n"+message);
		return new String(FinalString);
	}
}
