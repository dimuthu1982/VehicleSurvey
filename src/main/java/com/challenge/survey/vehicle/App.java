package com.challenge.survey.vehicle;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import com.challenge.survey.vehicle.builder.IStatCollectorBuilder;
import com.challenge.survey.vehicle.builder.VehicleSurveyStatCollectorBuilder;
import com.challenge.survey.vehicle.collectors.ISurveyStatisticCollector;
import com.challenge.survey.vehicle.exceptions.FileReaderSrviceException;
import com.challenge.survey.vehicle.service.ISurveyBook;
import com.challenge.survey.vehicle.service.ISurveyFileReader;
import com.challenge.survey.vehicle.service.SurveyBook;
import com.challenge.survey.vehicle.service.VehicleSurveyStatCollectIterator;
import com.challenge.survey.vehicle.service.VehicleSurveyFileReader;

/**
 * Hello world! Future enhancement replace sysout with logger use DI Catch and
 * print proper exceptions
 *
 */
public class App {
	public static void main(String[] args) throws URISyntaxException, FileReaderSrviceException {
		Path path = getFilePath();

		System.out.println("Starting statistic display");

		ISurveyFileReader surveyFileReader = new VehicleSurveyFileReader(path);

		IStatCollectorBuilder statCollectorBuilder = new VehicleSurveyStatCollectorBuilder().build();

		ISurveyStatisticCollector surveyStatCollectorIterator = new VehicleSurveyStatCollectIterator(
				statCollectorBuilder);

		ISurveyBook surveyBook = new SurveyBook(surveyFileReader, surveyStatCollectorIterator);
		surveyBook.startSurvey();

		System.out.println("Ending statistic display");

	}

	private static Path getFilePath() throws URISyntaxException {
		// return Paths.get(App.class.getClassLoader().getResource("Vehicle Survey
		// Coding Challenge sample data.txt").toURI());
		return Paths.get(App.class.getClassLoader().getResource("testSurvay.txt").toURI());
	}

	static void printData(Path path) {
		LocalTime startTime = LocalTime.of(00, 00);

		try (Stream<String> stream = Files.lines(path)) {

			stream.forEach(line -> {
				String replacedLine = line.replaceFirst("A|B", "");
				System.out.println(line + ":= " + startTime.plus(Long.parseLong(replacedLine), ChronoUnit.MILLIS));
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
