package com.aws.lambda.data;

public class Student {

	private Long id ;
	private String name;
	private String phnumber;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhnumber() {
		return phnumber;
	}
	public void setPhnumber(String phnumber) {
		this.phnumber = phnumber;
	}
	public Student(Long id, String name, String phnumber) {
		
		this.id = id;
		this.name = name;
		this.phnumber = phnumber;
	}
	public Student() {
		
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", phnumber=" + phnumber + "]";
	}
	
	
}
