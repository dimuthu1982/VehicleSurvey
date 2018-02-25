package com.challenge.survey.vehicle.service;

import java.nio.file.Path;
import java.util.function.Consumer;

import com.challenge.survey.vehicle.exceptions.FileReaderSrviceException;
import com.challenge.survey.vehicle.exceptions.UnrecognizedFileContentException;
import com.challenge.survey.vehicle.model.SurvayData;

public abstract class AbstractSurvayFileReader implements ISurvayFileReader{

	private Path path;
	
	public AbstractSurvayFileReader(Path path) {
		this.path = path;
	}

	public void startReading(Consumer<SurvayData> dataConsumer) throws FileReaderSrviceException {
		String rawData = null;
		
		SurvayData survayData;
		
		try(SurvayFileReader fileReader = new SurvayFileReader(path)){
			while(fileReader.hasMoreData()) {
				rawData = readRawData(fileReader);
				survayData = getSurvayData(rawData);

				dataConsumer.accept(survayData);
			}
		} catch (Exception e) {
			throw new FileReaderSrviceException("Error in Survay file",e);
		}
	}

	protected abstract SurvayData getSurvayData(String rawData) throws FileReaderSrviceException;

	protected abstract String readRawData(SurvayFileReader fileReader) throws UnrecognizedFileContentException, FileReaderSrviceException;
}
