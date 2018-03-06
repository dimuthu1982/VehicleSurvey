package com.challenge.survey.vehicle.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.challenge.survey.vehicle.exceptions.FileReaderSrviceException;

public class SurveyFileReader implements AutoCloseable {

	private final Logger logger = Logger.getLogger(SurveyFileReader.class.getName());
	
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

	public String readLines(int numberOflines) {
		return readLines(numberOflines, DELIMETER);
	}

	public String readLines(int numberOflines, String delimeter) {
		List<String> dataList = IntStream.range(0,numberOflines).boxed().map(i -> readLine()).collect(Collectors.toList());
		return String.join(delimeter, dataList);
	}

	private String readLine(){
		return Optional.of(scanner.nextLine()).filter(Objects::nonNull).orElse(null);
	}

	@Override
	public void close() throws Exception {
		Optional.of(inputStream).ifPresent(br -> {
			try {
				br.close();
			} catch (IOException e) {
				logger.log(Level.WARNING, "Error in closing input stream", e);
			}
		});

		Optional.of(scanner).ifPresent(Scanner::close);
	}
}
