package com.aws.lambda.data;

public class ResponseCount {

	private long count;
	private String message;
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ResponseCount(long count, String message) {
		super();
		this.count = count;
		this.message = message;
	}
	public ResponseCount() {
		super();
	}
	@Override
	public String toString() {
		return "ResponseObject [count=" + count + ", message=" + message + "]";
	}
	
	
}
