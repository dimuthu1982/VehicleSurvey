package com.challenge.survey.vehicle.exceptions;

public class UnrecognizedFileContentException extends Exception{
	
	public UnrecognizedFileContentException(String msg, Exception err) {
		super(msg, err);
	}

	public UnrecognizedFileContentException(String string) {
		super(string);
	}
}
