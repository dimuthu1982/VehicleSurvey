package com.challenge.survey.vehicle.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.challenge.survey.vehicle.collectors.ISurveyStatisticCollector;
import com.challenge.survey.vehicle.model.SurveyData;
import com.challenge.survey.vehicle.model.TimeStatistics;
import com.challenge.survey.vehicle.utils.DateUtils;

public abstract class AbstractTimePeriodStatCollector<T extends TimeStatistics>
		implements ISurveyStatisticCollector, ISurveyDataRetreiver<T> {

	private List<T> statHolder = new ArrayList<>();

	public AbstractTimePeriodStatCollector(long minuits) {
		timePeriodOrganizer(minuits);
	}

	public AbstractTimePeriodStatCollector(List<List<LocalTime>> sessions) {

		sessions.forEach(seassion -> {
			LocalTime startingSession = seassion.get(0);
			LocalTime endingSession = seassion.get(1);
			statHolder.add(getDataByDateRange(startingSession, endingSession));
		});

	}

	public void timePeriodOrganizer(long timeGapInMinuits) {

		LocalTime dayEnds = DateUtils.getLocalTimeEndDate();

		LocalTime startTime = LocalTime.of(00, 00);
		LocalTime endTime = DateUtils.getLocalTimeUpperBound(startTime, timeGapInMinuits);

		statHolder.add(getDataByDateRange(startTime, endTime));

		do {
			startTime = DateUtils.plusMillisecond(endTime);
			endTime = DateUtils.getLocalTimeUpperBound(startTime, timeGapInMinuits);

			statHolder.add(getDataByDateRange(startTime, endTime));

		} while (endTime.isBefore(dayEnds));

		endTime = DateUtils.minusMillisecond(LocalTime.of(00, 00));

		statHolder.add(getDataByDateRange(startTime, endTime));
	}

	@Override
	public void collectStatistics(SurveyData statistics) {
		LocalTime hitTime = DateUtils.plusMillisecond(LocalTime.of(00, 00), statistics.getSecondMark());

		statHolder.stream()
				.filter(stat -> DateUtils.isLocalDateBetween(stat.getStartTime(), stat.getEndTime(), hitTime))
				.findFirst().ifPresent(updateStatics(statistics));
	}

	@Override
	public List<T> getStatistics() {
		return statHolder;
	}

	protected abstract <T extends TimeStatistics> T getDataByDateRange(LocalTime startTime, LocalTime endTime);

	protected abstract Consumer<T> updateStatics(SurveyData servayData);
}
