package com.challenge.survey.vehicle.service;

import java.util.function.Consumer;

import com.challenge.survey.vehicle.exceptions.FileReaderSrviceException;
import com.challenge.survey.vehicle.model.SurvayData;

public interface ISurvayFileReader {

	public void startReading(Consumer<SurvayData> dataConsumer) throws FileReaderSrviceException;
}
