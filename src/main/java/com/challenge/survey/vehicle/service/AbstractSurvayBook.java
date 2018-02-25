package com.challenge.survey.vehicle.service;

import java.util.Optional;

import com.challenge.survey.vehicle.collectors.ISurvayStatisticCollector;
import com.challenge.survey.vehicle.exceptions.FileReaderSrviceException;
import com.challenge.survey.vehicle.feeder.ISurvayStatisticFeeder;

public abstract class AbstractSurvayBook implements ISurvayBook {

	@Override
	public void startSurvay() throws FileReaderSrviceException{
		startCollectingStatistics();
		collectPostProcessStatistics();
		printStatistics();
	}

	protected abstract void startCollectingStatistics() throws FileReaderSrviceException;
	
	protected abstract ISurvayStatisticCollector getSurvayCollector();
	
	private void collectPostProcessStatistics() {
		Optional.of(getSurvayCollector())
		.filter(ISurvayStatisticFeeder.class::isInstance)
		.map(ISurvayStatisticFeeder.class::cast)
		.ifPresent(ISurvayStatisticFeeder::executeStatisticFeeders);
	}

	
	private void printStatistics() {
		getSurvayCollector().printStatistics();
		
		Optional.of(getSurvayCollector())
		.filter(ISurvayStatisticFeeder.class::isInstance)
		.map(ISurvayStatisticFeeder.class::cast)
		.ifPresent(ISurvayStatisticFeeder::printStatisticFeeders);
	}
}
