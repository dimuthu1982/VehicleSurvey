package com.challenge.survey.vehicle.builder;

import com.challenge.survey.vehicle.collectors.ISurveyStatisticCollector;
import com.challenge.survey.vehicle.feeder.ISurveyStatisticFeeder;

import java.util.List;

public interface IStatCollectorBuilder {

    public List<ISurveyStatisticCollector> getStatCollectors();

    public List<ISurveyStatisticFeeder> getStatFeeders();
}
