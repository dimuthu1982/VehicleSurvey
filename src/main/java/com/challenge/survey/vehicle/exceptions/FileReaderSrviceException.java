package com.challenge.survey.vehicle.exceptions;

public class FileReaderSrviceException extends Exception {

	public FileReaderSrviceException(String msg, Exception err) {
		super(msg, err);
	}

	public FileReaderSrviceException(String string) {
		super(string);
	}

}
