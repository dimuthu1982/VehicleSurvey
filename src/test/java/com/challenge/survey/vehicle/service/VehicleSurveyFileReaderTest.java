package com.challenge.survey.vehicle.service;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.challenge.survey.vehicle.model.SurveyData;


public class VehicleSurveyFileReaderTest {

	private VehicleSurveyFileReader surveyFileReader;
	
	@Rule
	public TemporaryFolder testSurveyFileFolder = new TemporaryFolder(); 
	
	private File surveyFile;
	
	@Before
	public void setUp() throws Exception {
		surveyFile = testSurveyFileFolder.newFile("vehicle-survey-file.txt");
	}

	@Test
	public void shouldGetUpStreamDataWhenUpStreamDataPassesFrom_19_41_19_288_to_19_41_19_418() throws Exception {
//		19:41:19.288 - 19:41:19.418
		writeSurveyDataToFile("A70879288","A70879418");
		
		surveyFileReader = new VehicleSurveyFileReader(surveyFile.toPath());
		
		Consumer<SurveyData> dataConsumer = getSurveyDataConsumer(70879288,70879418,SurveyData.DIRECTION_UP);
		
		surveyFileReader.startReading(dataConsumer);
	}
	
	@Test
	public void shouldGetDownStreamDataWhenDownStreamDataPassesFrom_20_04_27_850_to_20_04_28_023() throws Exception {
//		20:04:27.850, 20:04:27.854, 20:04:28.023, 20:04:28.026
		writeSurveyDataToFile("A72267850","B72267854","A72268023","B72268026");
		
		surveyFileReader = new VehicleSurveyFileReader(surveyFile.toPath());
		
		Consumer<SurveyData> dataConsumer = getSurveyDataConsumer(72267850,72268023,SurveyData.DIRECTION_DOWN);
		
		surveyFileReader.startReading(dataConsumer);
	}

	@Test
	public void shouldGetUpStreamDataWhenUpStreamDataPassesFrom_20_37_41_170_to_20_37_41_300() throws Exception {
//		20:37:41.170 - 20:37:41.300
		writeSurveyDataToFile("A74261170","A74261300");
		
		surveyFileReader = new VehicleSurveyFileReader(surveyFile.toPath());
		
		Consumer<SurveyData> dataConsumer = getSurveyDataConsumer(74261170,74261300,SurveyData.DIRECTION_UP);
		
		surveyFileReader.startReading(dataConsumer);
	}
	
	@Test
	public void shouldGetDownStreamDataWhenDownStreamDataPassesFrom_21_09_12_427_to_21_09_12_589() throws Exception {
//		21:09:12.427, 21:09:12.430, 21:09:12.589, 21:09:12.592
		writeSurveyDataToFile("A76152427","B76152430","A76152589","B76152592");
		
		surveyFileReader = new VehicleSurveyFileReader(surveyFile.toPath());
		
		Consumer<SurveyData> dataConsumer = getSurveyDataConsumer(76152427,76152589,SurveyData.DIRECTION_DOWN);
		
		surveyFileReader.startReading(dataConsumer);
	}
	
	@Test
	public void shouldGetUpStreamDataWhenUpStreamDataPassesFrom_21_59_35_750_to_21_59_35_893() throws Exception {
//		21:59:35.750 - 21:59:35.893
		writeSurveyDataToFile("A79175750","A79175893");
		
		surveyFileReader = new VehicleSurveyFileReader(surveyFile.toPath());
		
		Consumer<SurveyData> dataConsumer = getSurveyDataConsumer(79175750,79175893,SurveyData.DIRECTION_UP);
		
		surveyFileReader.startReading(dataConsumer);
	}
	
	@Test
	public void shouldGetDownStreamDataWhenDownStreamDataPassesFrom_22_19_49_999_to_22_19_50_122() throws Exception {
//		22:19:49.999, 22:19:50.001, 22:19:50.122, 22:19:50.125
		writeSurveyDataToFile("A80389999","B80390001","A80390122","B80390125");
		
		surveyFileReader = new VehicleSurveyFileReader(surveyFile.toPath());
		
		Consumer<SurveyData> dataConsumer = getSurveyDataConsumer(80389999,80390122,SurveyData.DIRECTION_DOWN);
		
		surveyFileReader.startReading(dataConsumer);
	}
	
	private void writeSurveyDataToFile(String...times) throws IOException {
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(surveyFile.toURI())))
		{
			for(String str : times) {
				writer.write(str);
				writer.write("\n");
			}
		}
	}

	private Consumer<SurveyData> getSurveyDataConsumer(long firstMark, long secondMark, int direction) {
		Consumer<SurveyData> dataConsumer = surveyData -> {
			assertEquals(firstMark, surveyData.getFirstMark());
			assertEquals(secondMark, surveyData.getSecondMark());
			assertEquals(direction, surveyData.getDirection());
		};
		return dataConsumer;
	}
}
