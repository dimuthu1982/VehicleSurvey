package com.challenge.survey.vehicle.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.challenge.survey.vehicle.collectors.ISurvayStatisticCollector;
import com.challenge.survey.vehicle.collectors.SpeedDistributionTimePeriodStatCollector;
import com.challenge.survey.vehicle.feeder.ISurvayStatisticFeeder;
import com.challenge.survey.vehicle.feeder.PeekTimeCalculationFeeder;
import com.challenge.survey.vehicle.service.ISurvayDataRetreiver;
import com.challenge.survey.vehicle.service.TimePeriodStatCollector;
import com.challenge.survey.vehicle.settings.VehileServiceConstants;

public class VehicleSurvayStatCollectorBuilder implements IStatCollectorBuilder{

	private List<ISurvayStatisticCollector> statCollectionList = new ArrayList<>();
	
	private List<ISurvayStatisticFeeder> statCollectionFeederList = new ArrayList<>();
	
	public VehicleSurvayStatCollectorBuilder() {
		
		ISurvayStatisticCollector timePeriodMinuits60 = new TimePeriodStatCollector(60);
		
		statCollectionList.add(timePeriodMinuits60);
		statCollectionList.add(new TimePeriodStatCollector(30));
		statCollectionList.add(new TimePeriodStatCollector(20));
		statCollectionList.add(new TimePeriodStatCollector(15));
		statCollectionList.add(new TimePeriodStatCollector(Arrays.asList(VehileServiceConstants.MORNING_SESSION, VehileServiceConstants.EVENING_SESSION)));
		statCollectionList.add(new SpeedDistributionTimePeriodStatCollector(60));
		
		statCollectionFeederList.add(new PeekTimeCalculationFeeder((ISurvayDataRetreiver)timePeriodMinuits60));
	}
	
	@Override
	public List<ISurvayStatisticCollector> getStatCollectors() {
		return statCollectionList;
	}

	@Override
	public List<ISurvayStatisticFeeder> getStatFeeders() {
		return statCollectionFeederList;
	}

	public IStatCollectorBuilder build() {
		return this;
	}

	
}
