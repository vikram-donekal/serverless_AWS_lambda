package com.aws.lambda.data;

public class RequestId {
	
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RequestId() {
		super();
	}

	@Override
	public String toString() {
		return "RequestId [id=" + id + "]";
	}
	

}
