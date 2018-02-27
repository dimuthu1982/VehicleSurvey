package com.challenge.survey.vehicle.feeder;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.challenge.survey.vehicle.model.CountingStatistics;
import com.challenge.survey.vehicle.service.ISurveyDataRetreiver;
import com.challenge.survey.vehicle.utils.DateUtils;

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

		printUpStreamData();
		printDownStreamData();
	}

	//TODO: To be formatted
	private void printDownStreamData() {
		System.out.println("\n------------------- " + surveyStatisticDataCollection.getStatisticTypes()+ " (DownStream Peek Times)-------------------");
		
		if (peekTimesDownStream != null) {
			System.out.println("Down stream counts : " + peekTimesDownStream.get(0).getDownStreamCount());
			peekTimesDownStream.forEach(stat -> System.out.println(String.format("Period: [%s - %s]", stat.getStartTime(), DateUtils.formatTime(stat.getEndTime()))));
		} else {
			System.out.println("No Data Found for up stream");
		}
	}

	private void printUpStreamData() {
		System.out.println("\n------------------- " + surveyStatisticDataCollection.getStatisticTypes()+ " (Upstream Peek Times)-------------------");
		if (peekTimesUpStream != null) {

			System.out.println("Up stream counts : " + peekTimesUpStream.get(0).getUpStreamCount());
			peekTimesUpStream.forEach(stat -> System.out.println(
					String.format("Period: [%s - %s]", stat.getStartTime(), DateUtils.formatTime(stat.getEndTime()))));
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
