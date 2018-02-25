package com.challenge.survey.vehicle.service;

import java.time.LocalTime;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.challenge.survey.vehicle.model.CountingStatistics;
import com.challenge.survey.vehicle.model.SurvayData;


public class TimePeriodStatCollector extends AbstractTimePeriodStatCollector<CountingStatistics>{

	private String periodTypeMessage;
	
	public TimePeriodStatCollector(long minuits) {
		super(minuits);
		periodTypeMessage = String.format("Displaying statistics every %d minuits", minuits);
	}

	public TimePeriodStatCollector(List<List<LocalTime>> sessions) {
		super(sessions);
		String seassions = sessions.stream()
				.map(season -> String.format("[%s - %s]", season.get(0),season.get(1)))
				.collect(Collectors.joining(", "));
		
		periodTypeMessage = "Displaying statistics sessions: " + seassions;
	}

	protected Consumer<CountingStatistics> updateStatics(SurvayData servayData) {
		return stat -> {
			if(servayData.getDirection() == SurvayData.DIRECTION_UP) {
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
