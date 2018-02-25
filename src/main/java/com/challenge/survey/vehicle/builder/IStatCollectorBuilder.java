package com.challenge.survey.vehicle.builder;

import java.util.List;

import com.challenge.survey.vehicle.collectors.ISurvayStatisticCollector;
import com.challenge.survey.vehicle.feeder.ISurvayStatisticFeeder;

public interface IStatCollectorBuilder {

	public List<ISurvayStatisticCollector> getStatCollectors();
	
	public List<ISurvayStatisticFeeder> getStatFeeders();
}
