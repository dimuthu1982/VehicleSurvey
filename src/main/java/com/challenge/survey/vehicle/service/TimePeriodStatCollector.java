package com.challenge.survey.vehicle.service;

import java.time.LocalTime;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.challenge.survey.vehicle.model.CountingStatistics;
import com.challenge.survey.vehicle.model.SurveyData;
import com.challenge.survey.vehicle.settings.SurveySessions;

public class TimePeriodStatCollector extends AbstractTimePeriodStatCollector<CountingStatistics> {

	private String periodTypeMessage;

	public TimePeriodStatCollector(long minutes) {
		super(minutes);
		periodTypeMessage = String.format("Displaying statistics every %d minutes", minutes);
	}

	public TimePeriodStatCollector(List<SurveySessions> sessions) {
		super(sessions);
		
		String seassions = sessions.stream().map(season -> String.format("%s [%s - %s]",season.getSession(),season.getStartTime(),season.getEndTime())).collect(Collectors.joining(", "));
		
		periodTypeMessage = "Displaying statistics in session: " + seassions;
	}

	protected Consumer<CountingStatistics> updateStatics(SurveyData servayData) {
		return stat -> {
			if (servayData.getDirection() == SurveyData.DIRECTION_UP) {
				stat.incrementUpStream();
			} else {
				stat.incrementDownStream();
			}
		};
	}

	@Override
	public void printStatistics() {
		System.out.println("\n------------------- " + periodTypeMessage + " -------------------");
		List<CountingStatistics>  filteredStatistics = getStatistics().stream().filter(CountingStatistics::isStatistic).collect(Collectors.<CountingStatistics>toList()); 
		
		if(filteredStatistics.isEmpty()) {
			System.out.println("No Statistics Found.");
		}else {
			filteredStatistics.forEach(System.out::println);
		}
	}

	@Override
	public String getStatisticTypes() {
		return periodTypeMessage;
	}

	@Override
	protected CountingStatistics getDataByDateRange(LocalTime startTime, LocalTime endTime) {
		return new CountingStatistics(startTime, endTime);
	}
}
