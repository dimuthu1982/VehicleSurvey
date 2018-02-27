package com.challenge.survey.vehicle.service;

import com.challenge.survey.vehicle.builder.IStatCollectorBuilder;
import com.challenge.survey.vehicle.collectors.ISurveyStatisticCollector;
import com.challenge.survey.vehicle.feeder.ISurveyStatisticFeeder;
import com.challenge.survey.vehicle.model.SurveyData;

public class VehicleSurveyStatCollectIterator implements ISurveyStatisticCollector, ISurveyStatisticFeeder {

	private IStatCollectorBuilder statCollectorBuilder;

	public VehicleSurveyStatCollectIterator(IStatCollectorBuilder statCollectorBuilder) {
		this.statCollectorBuilder = statCollectorBuilder;
	}

	@Override
	public void collectStatistics(SurveyData statistics) {
		statCollectorBuilder.getStatCollectors().forEach(collector -> collector.collectStatistics(statistics));
	}

	@Override
	public void printStatistics() {
		statCollectorBuilder.getStatCollectors().forEach(ISurveyStatisticCollector::printStatistics);
	}

	@Override
	public void executeStatisticFeeders() {
		this.statCollectorBuilder.getStatFeeders().forEach(ISurveyStatisticFeeder::executeStatisticFeeders);
	}

	@Override
	public void printStatisticFeeders() {
		this.statCollectorBuilder.getStatFeeders().forEach(ISurveyStatisticFeeder::printStatisticFeeders);
	}
}
