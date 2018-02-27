package com.challenge.survey.vehicle.service;

import java.util.List;

import com.challenge.survey.vehicle.model.TimeStatistics;

public interface ISurveyDataRetreiver<T extends TimeStatistics> {

	public List<T> getStatistics();

	public String getStatisticTypes();
}
