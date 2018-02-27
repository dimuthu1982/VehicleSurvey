package com.challenge.survey.vehicle.service;

import java.time.LocalTime;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.challenge.survey.vehicle.model.CountingStatistics;
import com.challenge.survey.vehicle.model.SurveyData;


public class TimePeriodStatCollector extends AbstractTimePeriodStatCollector<CountingStatistics>{

	private String periodTypeMessage;
	
	public TimePeriodStatCollector(long minutes) {
		super(minutes);
		periodTypeMessage = String.format("Displaying statistics every %d minutes", minutes);
	}

	public TimePeriodStatCollector(List<List<LocalTime>> sessions) {
		super(sessions);
		String seassions = sessions.stream()
				.map(season -> String.format("[%s - %s]", season.get(0),season.get(1)))
				.collect(Collectors.joining(", "));
		
		periodTypeMessage = "Displaying statistics sessions: " + seassions;
	}

	protected Consumer<CountingStatistics> updateStatics(SurveyData servayData) {
		return stat -> {
			if(servayData.getDirection() == SurveyData.DIRECTION_UP) {
				stat.incrementUpStream();
			}else {
				stat.incrementDownStream();
			}
		};
	}

	@Override
	public void printStatistics() {
		System.out.println("\n------------------- " + periodTypeMessage + " -------------------");
		getStatistics().stream().filter(CountingStatistics::isStatistic).forEach(System.out::println);
		System.out.println("------------------- " + periodTypeMessage + " -------------------\n");
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
