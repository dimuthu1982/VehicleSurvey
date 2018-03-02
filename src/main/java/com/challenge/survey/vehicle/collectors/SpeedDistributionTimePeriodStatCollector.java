package com.challenge.survey.vehicle.collectors;

import java.time.LocalTime;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.challenge.survey.vehicle.model.CountingStatistics;
import com.challenge.survey.vehicle.model.SpeedCountingStatistics;
import com.challenge.survey.vehicle.model.SurveyData;
import com.challenge.survey.vehicle.service.TimePeriodStatCollector;
import com.challenge.survey.vehicle.utils.SurveyUtils;

public class SpeedDistributionTimePeriodStatCollector extends TimePeriodStatCollector {

	public SpeedDistributionTimePeriodStatCollector(long minuits) {
		super(minuits);
	}

	@Override
	protected Consumer<CountingStatistics> updateStatics(SurveyData servayData) {
		return super.updateStatics(servayData).andThen(stat -> {

			long travellingSpeed = calculateSpeed(servayData);

			if (servayData.getDirection() == SurveyData.DIRECTION_UP) {
				((SpeedCountingStatistics) stat).addUpStreamSpeed(travellingSpeed);
			} else {
				((SpeedCountingStatistics) stat).addDownStreamSpeed(travellingSpeed);

			}
		});
	}

	private long calculateSpeed(SurveyData servayData) {
		long travellingTime = servayData.getSecondMark() - servayData.getFirstMark();
		return SurveyUtils.calculateSpeedKMPH(travellingTime);
	}

	@Override
	public void printStatistics() {
		System.out.println("\n------------------------ Speed Calculation ------------------------");
		List<CountingStatistics> statiisticList = getStatistics().stream().filter(this::isSpeedCount).collect(Collectors.<CountingStatistics>toList());
		if(statiisticList.isEmpty()) {
			System.out.println("No Statistics Found.");
		}else {
			statiisticList.forEach(System.out::println);
		}
	}

	@Override
	protected SpeedCountingStatistics getDataByDateRange(LocalTime startTime, LocalTime endTime) {
		return new SpeedCountingStatistics(startTime, endTime);
	}
	
	private boolean isSpeedCount(CountingStatistics stat) {
		return ((SpeedCountingStatistics)stat).isSpeedCount();
	}

}
