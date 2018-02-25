package com.challenge.survey.vehicle.service;

import com.challenge.survey.vehicle.collectors.ISurvayStatisticCollector;
import com.challenge.survey.vehicle.exceptions.FileReaderSrviceException;

public class SurvayBook extends AbstractSurvayBook{

	private ISurvayFileReader survayFileReader;
	private ISurvayStatisticCollector survayStatCollector;
	
	public SurvayBook(ISurvayFileReader survayFileReader, ISurvayStatisticCollector survayStatCollector) {
		this.survayFileReader = survayFileReader;
		this.survayStatCollector = survayStatCollector;
	}
	
	@Override
	public void startCollectingStatistics() throws FileReaderSrviceException {
		survayFileReader.startReading(survayStatCollector::collectStatistics);
	}
	
	@Override
	public ISurvayStatisticCollector getSurvayCollector() {
		return survayStatCollector;
	}
}
