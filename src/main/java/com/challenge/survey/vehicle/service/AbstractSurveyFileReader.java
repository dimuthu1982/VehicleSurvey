package com.challenge.survey.vehicle.service;

import java.nio.file.Path;
import java.util.function.Consumer;

import com.challenge.survey.vehicle.exceptions.FileReaderSrviceException;
import com.challenge.survey.vehicle.exceptions.UnrecognizedFileContentException;
import com.challenge.survey.vehicle.model.SurveyData;

public abstract class AbstractSurveyFileReader implements ISurveyFileReader {

	private Path path;

	public AbstractSurveyFileReader(Path path) {
		this.path = path;
	}

	public void startReading(Consumer<SurveyData> dataConsumer) throws FileReaderSrviceException {
		SurveyData surveyData;

		try (SurveyFileReader fileReader = new SurveyFileReader(path)) {

			while (fileReader.hasMoreData()) {

				surveyData = getSurveyData(readRawData(fileReader));
				dataConsumer.accept(surveyData);
			}
		} catch (Exception e) {
			throw new FileReaderSrviceException("Error in Survey file", e);
		}
	}

	protected abstract SurveyData getSurveyData(String rawData) throws FileReaderSrviceException;

	protected abstract String readRawData(SurveyFileReader fileReader) throws UnrecognizedFileContentException, FileReaderSrviceException;
}
