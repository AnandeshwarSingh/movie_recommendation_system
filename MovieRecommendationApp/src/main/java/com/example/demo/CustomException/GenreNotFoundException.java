package com.example.demo.CustomException;

public class GenreNotFoundException extends RuntimeException {

	 private String message;
		public GenreNotFoundException(String message) {
			  super(message);
			this.message=message;
		}
}
