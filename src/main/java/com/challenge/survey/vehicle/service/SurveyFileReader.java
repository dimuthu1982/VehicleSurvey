package com.challenge.survey.vehicle.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

import com.challenge.survey.vehicle.exceptions.FileReaderSrviceException;

public class SurveyFileReader implements AutoCloseable {

	private Path filePath;

	private FileInputStream inputStream = null;

	private Scanner scanner = null;

	public static final String DELIMETER = ":";

	public SurveyFileReader(Path filePath) throws FileReaderSrviceException {
		this.filePath = filePath;
		open();
	}

	private SurveyFileReader open() throws FileReaderSrviceException {
		
		File file = getFileByPath();

		try {

			inputStream = new FileInputStream(file);
			scanner = new Scanner(inputStream, "UTF-8");

			return this;
		} catch (FileNotFoundException e) {
			throw new FileReaderSrviceException("Error in reading file : " + filePath.toUri(), e);
		}
	}

	private File getFileByPath() throws FileReaderSrviceException {
		File file = new File(filePath.toUri());
		
		if(file.length() == 0) {
			throw new FileReaderSrviceException("File is empty" + filePath.toUri());
		}
		
		return file;
	}

	public boolean hasMoreData() {
		return scanner.hasNextLine();
	}

	public String readLines(int numberOflines) throws FileReaderSrviceException {
		return readLines(numberOflines, DELIMETER);
	}

	public String readLines(int numberOflines, String delimeter) throws FileReaderSrviceException {
		return String.format("%s%s%S", readLine(), delimeter, readLine());
	}

	private String readLine() throws FileReaderSrviceException {
		return Optional.of(scanner.nextLine()).filter(Objects::nonNull).orElse(null);
	}

	@Override
	public void close() throws Exception {
		Optional.of(inputStream).ifPresent(br -> {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		Optional.of(scanner).ifPresent(fr -> {
			fr.close();
		});
	}
}
