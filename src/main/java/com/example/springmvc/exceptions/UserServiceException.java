package com.example.springmvc.exceptions;

public class UserServiceException extends RuntimeException{


	private static final long serialVersionUID = 2477381970831720950L;
	
	public UserServiceException(String message) 
	{
		super(message);
	}

}
