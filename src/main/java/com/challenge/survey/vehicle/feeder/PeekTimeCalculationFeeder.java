package com.challenge.survey.vehicle.feeder;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.challenge.survey.vehicle.model.CountingStatistics;
import com.challenge.survey.vehicle.service.ISurveyDataRetreiver;

public class PeekTimeCalculationFeeder implements ISurveyStatisticFeeder {

	private ISurveyDataRetreiver<CountingStatistics> surveyStatisticDataCollection;

	private List<CountingStatistics> peekTimesUpStream;

	private List<CountingStatistics> peekTimesDownStream;

	public PeekTimeCalculationFeeder(ISurveyDataRetreiver<CountingStatistics> surveyStatisticDataCollection) {
		this.surveyStatisticDataCollection = surveyStatisticDataCollection;
	}

	@Override
	public void executeStatisticFeeders() {

		findPeekTimeSlotsUpStream();
		findPeekTimeSlotsDownStream();
	}

	@Override
	public void printStatisticFeeders() {
		System.out.println("\n------------------- " + surveyStatisticDataCollection.getStatisticTypes()+ " (Peek Times)-------------------");
		printUpStreamData();
		printDownStreamData();
	}

	private void printDownStreamData() {
		
		if (peekTimesDownStream != null) {
			long vehiclesCount = peekTimesDownStream.get(0).getDownStreamCount();
			peekTimesDownStream.forEach(stat -> System.out.println(String.format("Time Frame : [%s - %s] : Statistics: [Total Vehicles Travelled Down Stream: %d]", stat.getStartTime(),stat.getEndTime(), vehiclesCount)));
		} else {
			System.out.println("No Data Found for down stream");
		}
	}

	private void printUpStreamData() {
		if (peekTimesUpStream != null || !peekTimesUpStream.isEmpty()) {

			long vehiclesCount = peekTimesUpStream.get(0).getUpStreamCount();
			
			peekTimesUpStream.forEach(stat -> System.out.println(
					String.format("Time Frame : [%s - %s] : Statistics: [Total Vehicles Travelled Up Stream: %d]", stat.getStartTime(), stat.getEndTime(), vehiclesCount)));
		} else {
			System.out.println("No Data Found for up stream");
		}
	}

	private void findPeekTimeSlotsUpStream() {
		Comparator<CountingStatistics> comparatorStream = Comparator.comparing(CountingStatistics::getUpStreamCount);
		List<CountingStatistics> sortedStatList = getValidDataByStream(comparatorStream);
		int peekTimeCount = sortedStatList.get(0).getUpStreamCount();
		peekTimesUpStream = sortedStatList.stream().filter(stat -> stat.getUpStreamCount() == peekTimeCount)
				.collect(Collectors.toList());
	}

	private void findPeekTimeSlotsDownStream() {
		Comparator<CountingStatistics> comparatorStream = Comparator.comparing(CountingStatistics::getDownStreamCount);
		List<CountingStatistics> sortedStatList = getValidDataByStream(comparatorStream);
		int peekTimeCount = sortedStatList.get(0).getDownStreamCount();
		peekTimesDownStream = sortedStatList.stream().filter(stat -> stat.getDownStreamCount() == peekTimeCount)
				.collect(Collectors.toList());
	}

	private List<CountingStatistics> getValidDataByStream(Comparator<CountingStatistics> comparatorField) {
		return surveyStatisticDataCollection.getStatistics().stream().filter(CountingStatistics::isStatistic)
				.sorted(comparatorField.reversed()).collect(Collectors.toList());
	}
}
