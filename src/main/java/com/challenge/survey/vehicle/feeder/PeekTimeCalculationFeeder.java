package com.challenge.survey.vehicle.feeder;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.challenge.survey.vehicle.model.CountingStatistics;
import com.challenge.survey.vehicle.service.ISurveyDataRetreiver;
import com.challenge.survey.vehicle.utils.DateUtils;

public class PeekTimeCalculationFeeder implements ISurveyStatisticFeeder {

	private ISurveyDataRetreiver<CountingStatistics> surveyStatisticDataCollection;

	private static final int UP_STREAM = 0;
	private static final int DOWN_STREAM = 1;

	private List<CountingStatistics> peekTimesUpStream;

	private List<CountingStatistics> peekTimesDownStream;

	public PeekTimeCalculationFeeder(ISurveyDataRetreiver<CountingStatistics> surveyStatisticDataCollection) {
		this.surveyStatisticDataCollection = surveyStatisticDataCollection;
	}

	@Override
	public void executeStatisticFeeders() {

		findPeekTimeSlots(UP_STREAM);
		findPeekTimeSlots(DOWN_STREAM);
	}

	@Override
	public void printStatisticFeeders() {

		System.out.println("\n------------------- " + surveyStatisticDataCollection.getStatisticTypes()
				+ " (Upstream Peek Times)-------------------");
		if (peekTimesUpStream != null) {

			System.out.println("Up stream counts : " + peekTimesUpStream.get(0).getUpStreamCount());
			peekTimesUpStream.forEach(stat -> System.out.println(
					String.format("Period: [%s - %s]", stat.getStartTime(), DateUtils.formatTime(stat.getEndTime()))));
		} else {
			System.out.println("No Data Found for up stream");
		}
		System.out.println("------------------- " + surveyStatisticDataCollection.getStatisticTypes()
				+ " (Upstream Peek Times)-------------------\n");

		System.out.println("\n------------------- " + surveyStatisticDataCollection.getStatisticTypes()
				+ " (DownStream Peek Times)-------------------");
		if (peekTimesDownStream != null) {
			System.out.println("Down stream counts : " + peekTimesDownStream.get(0).getDownStreamCount());
			peekTimesDownStream.forEach(stat -> System.out.println(
					String.format("Period: [%s - %s]", stat.getStartTime(), DateUtils.formatTime(stat.getEndTime()))));
		} else {
			System.out.println("No Data Found for up stream");
		}
		System.out.println("------------------- " + surveyStatisticDataCollection.getStatisticTypes()
				+ " (DownStream Peek Times)-------------------\n");
	}

	private void findPeekTimeSlots(int upStream) {
		Comparator<CountingStatistics> comparatorStream;

		if (upStream == UP_STREAM) {

			comparatorStream = Comparator.comparing(CountingStatistics::getUpStreamCount);
			List<CountingStatistics> sortedStatList = getValidDataByStream(comparatorStream);
			int peekTimeCount = sortedStatList.get(0).getUpStreamCount();
			peekTimesUpStream = sortedStatList.stream().filter(stat -> stat.getUpStreamCount() == peekTimeCount)
					.collect(Collectors.toList());

		} else {
			comparatorStream = Comparator.comparing(CountingStatistics::getDownStreamCount);
			List<CountingStatistics> sortedStatList = getValidDataByStream(comparatorStream);
			int peekTimeCount = sortedStatList.get(0).getDownStreamCount();
			peekTimesDownStream = sortedStatList.stream().filter(stat -> stat.getDownStreamCount() == peekTimeCount)
					.collect(Collectors.toList());

		}
	}

	private List<CountingStatistics> getValidDataByStream(Comparator<CountingStatistics> comparatorField) {
		return surveyStatisticDataCollection.getStatistics().stream().filter(CountingStatistics::isStatistic)
				.sorted(comparatorField.reversed()).collect(Collectors.toList());
	}
}
