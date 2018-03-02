package com.challenge.survey.vehicle;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.challenge.survey.vehicle.builder.IStatCollectorBuilder;
import com.challenge.survey.vehicle.builder.VehicleSurveyStatCollectorBuilder;
import com.challenge.survey.vehicle.collectors.ISurveyStatisticCollector;
import com.challenge.survey.vehicle.exceptions.FileReaderSrviceException;
import com.challenge.survey.vehicle.service.ISurveyBook;
import com.challenge.survey.vehicle.service.ISurveyFileReader;
import com.challenge.survey.vehicle.service.SurveyBook;
import com.challenge.survey.vehicle.service.VehicleSurveyFileReader;
import com.challenge.survey.vehicle.service.VehicleSurveyStatCollectIterator;

public class SurveyApplication {
	public static void main(String[] args) throws URISyntaxException, FileReaderSrviceException {
		Path path = getFilePath();

		System.out.println("Starting statistic display");

		ISurveyFileReader surveyFileReader = new VehicleSurveyFileReader(path);

		IStatCollectorBuilder statCollectorBuilder = new VehicleSurveyStatCollectorBuilder().build();

		ISurveyStatisticCollector surveyStatCollectorIterator = new VehicleSurveyStatCollectIterator(statCollectorBuilder);

		ISurveyBook surveyBook = new SurveyBook(surveyFileReader, surveyStatCollectorIterator);
		surveyBook.startSurvey();

		System.out.println("\nEnding statistic display");

	}

	private static Path getFilePath() throws URISyntaxException {
		 return Paths.get(SurveyApplication.class.getClassLoader().getResource("Vehicle Survey Coding Challenge sample data.txt").toURI());
	}
}
