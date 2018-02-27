package com.challenge.survey.vehicle.collectors;

import com.challenge.survey.vehicle.model.SurveyData;

public interface ISurveyStatisticCollector {

	public void collectStatistics(SurveyData statistics);

	public void printStatistics();
}
