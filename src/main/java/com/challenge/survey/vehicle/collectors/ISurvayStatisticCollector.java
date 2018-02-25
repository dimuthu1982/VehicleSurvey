package com.challenge.survey.vehicle.collectors;

import com.challenge.survey.vehicle.model.SurvayData;

public interface ISurvayStatisticCollector{

	public void collectStatistics(SurvayData statistics);
	
	public void printStatistics();
}
