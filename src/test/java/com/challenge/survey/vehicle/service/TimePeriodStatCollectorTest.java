package com.challenge.survey.vehicle.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.challenge.survey.vehicle.model.CountingStatistics;
import com.challenge.survey.vehicle.model.SurveyData;
import com.challenge.survey.vehicle.settings.SurveySessions;

@RunWith(Parameterized.class)
public class TimePeriodStatCollectorTest {

	private SurveyData statistics;
	private List<TimePeriodStatCollector> collectorList;
	private List<CountingStatistics> outputList;
	
	
	public TimePeriodStatCollectorTest(SurveyData statistics, List<TimePeriodStatCollector> collectorList, List<CountingStatistics> outputList){
		this.statistics = statistics;
		this.collectorList = collectorList;
		this.outputList = outputList;
	}
	
	@Before
    public void setUp() throws Exception {
		collectorList.forEach(collector -> collector.collectStatistics(statistics));
	}

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { 
			{getSurveyData(37355562L,37379698L,SurveyData.DIRECTION_UP),getCollectorList(),
				Arrays.asList(getResultList(LocalTime.of(10, 00),LocalTime.of(10, 59),1,0), getResultList(LocalTime.of(10, 00),LocalTime.of(10, 29),1,0), getResultList(LocalTime.of(10, 20),LocalTime.of(10, 39),1,0), getResultList(LocalTime.of(10, 15),LocalTime.of(10, 29),1,0))} //10:22:35.562 - 10:22:59.698
		});
	}

	private static SurveyData getSurveyData(long firstMark, long secondMark, int direction) {
		return new SurveyData(firstMark,secondMark,direction);
	}
	
	private static List<TimePeriodStatCollector> getCollectorList() {
		List<TimePeriodStatCollector> collectorList = new ArrayList<>();
		collectorList.add(new TimePeriodStatCollector(60));
		collectorList.add(new TimePeriodStatCollector(30));
		collectorList.add(new TimePeriodStatCollector(20));
		collectorList.add(new TimePeriodStatCollector(15));
		collectorList.add(new TimePeriodStatCollector(Arrays.asList(SurveySessions.MORNING_SESSION, SurveySessions.EVENING_SESSION)));
		
		return collectorList;
	}
	
	private static CountingStatistics getResultList(LocalTime startTime, LocalTime endTime, int upCount,int downCount) {
		CountingStatistics stat = new CountingStatistics(startTime,endTime.plus(59, ChronoUnit.SECONDS));
		stat.setUpStreamCount(upCount);
		stat.setDownStreamCount(downCount);
		
		return stat;
	}
	
	@Test
	public void shouldGetValuesFor60Mminute() {
		
		List<CountingStatistics>  filteredStatistics = collectorList.get(0).getStatistics().stream().filter(CountingStatistics::isStatistic).collect(Collectors.<CountingStatistics>toList()); 
		
		assertEquals(1, filteredStatistics.size());
		assertTrue(outputList.get(0).getStartTime().equals(filteredStatistics.get(0).getStartTime()));
		assertTrue(outputList.get(0).getEndTime().equals(filteredStatistics.get(0).getEndTime()));
		assertTrue(outputList.get(0).getUpStreamCount() == filteredStatistics.get(0).getUpStreamCount());
		assertTrue(outputList.get(0).getDownStreamCount() == filteredStatistics.get(0).getDownStreamCount());
	}
	
}
