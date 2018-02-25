package com.challenge.survey.vehicle.service;

import com.challenge.survey.vehicle.builder.IStatCollectorBuilder;
import com.challenge.survey.vehicle.collectors.ISurvayStatisticCollector;
import com.challenge.survey.vehicle.feeder.ISurvayStatisticFeeder;
import com.challenge.survey.vehicle.model.SurvayData;

public class VehicleSurvayStatCollectIterator implements ISurvayStatisticCollector, ISurvayStatisticFeeder{

	private IStatCollectorBuilder statCollectorBuilder;
	
	public VehicleSurvayStatCollectIterator(IStatCollectorBuilder statCollectorBuilder) {
		this.statCollectorBuilder = statCollectorBuilder;
	}

	@Override
	public void collectStatistics(SurvayData statistics) {
		statCollectorBuilder.getStatCollectors().forEach(collector -> collector.collectStatistics(statistics));
	}

	@Override
	public void printStatistics() {
		statCollectorBuilder.getStatCollectors().forEach(ISurvayStatisticCollector::printStatistics);
	}

	@Override
	public void executeStatisticFeeders() {
		this.statCollectorBuilder.getStatFeeders().forEach(ISurvayStatisticFeeder::executeStatisticFeeders);
	}

	@Override
	public void printStatisticFeeders() {
		this.statCollectorBuilder.getStatFeeders().forEach(ISurvayStatisticFeeder::printStatisticFeeders);
	}
}
