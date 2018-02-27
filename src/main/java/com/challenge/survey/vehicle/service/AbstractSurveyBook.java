package com.challenge.survey.vehicle.service;

import java.util.Optional;

import com.challenge.survey.vehicle.collectors.ISurveyStatisticCollector;
import com.challenge.survey.vehicle.exceptions.FileReaderSrviceException;
import com.challenge.survey.vehicle.feeder.ISurveyStatisticFeeder;

public abstract class AbstractSurveyBook implements ISurveyBook {

	@Override
	public void startSurvey() throws FileReaderSrviceException{
		startCollectingStatistics();
		collectPostProcessStatistics();
		printStatistics();
	}

	protected abstract void startCollectingStatistics() throws FileReaderSrviceException;
	
	protected abstract ISurveyStatisticCollector getSurveyCollector();
	
	private void collectPostProcessStatistics() {
		Optional.of(getSurveyCollector())
		.filter(ISurveyStatisticFeeder.class::isInstance)
		.map(ISurveyStatisticFeeder.class::cast)
		.ifPresent(ISurveyStatisticFeeder::executeStatisticFeeders);
	}

	
	private void printStatistics() {
		getSurveyCollector().printStatistics();
		
		Optional.of(getSurveyCollector())
		.filter(ISurveyStatisticFeeder.class::isInstance)
		.map(ISurveyStatisticFeeder.class::cast)
		.ifPresent(ISurveyStatisticFeeder::printStatisticFeeders);
	}
}
