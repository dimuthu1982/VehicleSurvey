package com.challenge.survey.vehicle.service;

import com.challenge.survey.vehicle.collectors.ISurveyStatisticCollector;
import com.challenge.survey.vehicle.exceptions.FileReaderSrviceException;

public class SurveyBook extends AbstractSurveyBook{

	private ISurveyFileReader surveyFileReader;
	private ISurveyStatisticCollector surveyStatCollector;
	
	public SurveyBook(ISurveyFileReader surveyFileReader, ISurveyStatisticCollector surveyStatCollector) {
		this.surveyFileReader = surveyFileReader;
		this.surveyStatCollector = surveyStatCollector;
	}
	
	@Override
	public void startCollectingStatistics() throws FileReaderSrviceException {
		surveyFileReader.startReading(surveyStatCollector::collectStatistics);
	}
	
	@Override
	public ISurveyStatisticCollector getSurveyCollector() {
		return surveyStatCollector;
	}
}
