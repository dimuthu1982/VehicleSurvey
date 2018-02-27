package com.challenge.survey.vehicle.builder;

import java.util.List;

import com.challenge.survey.vehicle.collectors.ISurveyStatisticCollector;
import com.challenge.survey.vehicle.feeder.ISurveyStatisticFeeder;

public interface IStatCollectorBuilder {

	public List<ISurveyStatisticCollector> getStatCollectors();

	public List<ISurveyStatisticFeeder> getStatFeeders();
}
