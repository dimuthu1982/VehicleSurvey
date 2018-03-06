package com.challenge.survey.vehicle.builder;

import com.challenge.survey.vehicle.collectors.ISurveyStatisticCollector;
import com.challenge.survey.vehicle.collectors.SpeedDistributionTimePeriodStatCollector;
import com.challenge.survey.vehicle.feeder.ISurveyStatisticFeeder;
import com.challenge.survey.vehicle.feeder.PeekTimeCalculationFeeder;
import com.challenge.survey.vehicle.service.ISurveyDataRetreiver;
import com.challenge.survey.vehicle.service.TimePeriodStatCollector;
import com.challenge.survey.vehicle.settings.SurveySessions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VehicleSurveyStatCollectorBuilder implements IStatCollectorBuilder {

    private List<ISurveyStatisticCollector> statCollectionList = new ArrayList<>();

    private List<ISurveyStatisticFeeder> statCollectionFeederList = new ArrayList<>();

    public VehicleSurveyStatCollectorBuilder() {

        ISurveyStatisticCollector timePeriodMinuits60 = new TimePeriodStatCollector(60);

        statCollectionList.add(timePeriodMinuits60);
        statCollectionList.add(new TimePeriodStatCollector(30));
        statCollectionList.add(new TimePeriodStatCollector(20));
        statCollectionList.add(new TimePeriodStatCollector(15));

        statCollectionList.add(new TimePeriodStatCollector(Arrays.asList(SurveySessions.MORNING_SESSION, SurveySessions.EVENING_SESSION)));

        statCollectionList.add(new SpeedDistributionTimePeriodStatCollector(60));

        statCollectionFeederList.add(new PeekTimeCalculationFeeder((ISurveyDataRetreiver) timePeriodMinuits60));
    }

    @Override
    public List<ISurveyStatisticCollector> getStatCollectors() {
        return statCollectionList;
    }

    @Override
    public List<ISurveyStatisticFeeder> getStatFeeders() {
        return statCollectionFeederList;
    }

    public IStatCollectorBuilder build() {
        return this;
    }

}
