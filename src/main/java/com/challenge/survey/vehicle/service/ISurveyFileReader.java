package com.challenge.survey.vehicle.service;

import java.util.function.Consumer;

import com.challenge.survey.vehicle.exceptions.FileReaderSrviceException;
import com.challenge.survey.vehicle.model.SurveyData;

public interface ISurveyFileReader {

	public void startReading(Consumer<SurveyData> dataConsumer) throws FileReaderSrviceException;
}
