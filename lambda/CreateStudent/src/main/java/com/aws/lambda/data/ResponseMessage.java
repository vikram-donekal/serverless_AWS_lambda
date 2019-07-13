package com.aws.lambda.data;

public class ResponseMessage {
	private String message;


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ResponseMessage() {	}

	@Override
	public String toString() {
		return "Message : "+this.message;
	}
}
