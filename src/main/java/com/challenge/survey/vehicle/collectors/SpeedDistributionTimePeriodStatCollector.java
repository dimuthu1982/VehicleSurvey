package com.challenge.survey.vehicle.collectors;

import java.time.LocalTime;
import java.util.function.Consumer;

import com.challenge.survey.vehicle.model.CountingStatistics;
import com.challenge.survey.vehicle.model.SpeedCountingStatistics;
import com.challenge.survey.vehicle.model.SurveyData;
import com.challenge.survey.vehicle.service.TimePeriodStatCollector;

public class SpeedDistributionTimePeriodStatCollector extends TimePeriodStatCollector{

	public SpeedDistributionTimePeriodStatCollector(long minuits) {
		super(minuits);
	}

	@Override
	protected Consumer<CountingStatistics> updateStatics(SurveyData servayData){
		return super.updateStatics(servayData).andThen(stat -> {
			
			long travellingSpeed = calculateSpeed(servayData);
			
			if(servayData.getDirection() == SurveyData.DIRECTION_UP) {
				((SpeedCountingStatistics)stat).addUpStreamSpeed(travellingSpeed);
			}else {
				((SpeedCountingStatistics)stat).addDownStreamSpeed(travellingSpeed);
				
			}
		});
	}
	
	
	private long calculateSpeed(SurveyData servayData) {
		long travellingTime = servayData.getSecondMark() - servayData.getFirstMark();
		return Math.round((2.5*1000*60*60)/(travellingTime*1000));
	}

	@Override
	public void printStatistics() {
		System.out.println("\n------------------- Speed Calculation -------------------");
		getStatistics().stream().filter(CountingStatistics::isStatistic).forEach(System.out::println);
		System.out.println("------------------- Speed Calculation -------------------\n");
	}
	

	@Override
	protected CountingStatistics getDataByDateRange(LocalTime startTime, LocalTime endTime) {
		return new SpeedCountingStatistics(startTime, endTime);
	}


}
