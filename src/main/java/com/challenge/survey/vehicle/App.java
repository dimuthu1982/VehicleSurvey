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
import com.challenge.survey.vehicle.builder.VehicleSurvayStatCollectorBuilder;
import com.challenge.survey.vehicle.collectors.ISurvayStatisticCollector;
import com.challenge.survey.vehicle.exceptions.FileReaderSrviceException;
import com.challenge.survey.vehicle.service.ISurvayBook;
import com.challenge.survey.vehicle.service.ISurvayFileReader;
import com.challenge.survey.vehicle.service.SurvayBook;
import com.challenge.survey.vehicle.service.VehicleSurvayFileReader;
import com.challenge.survey.vehicle.service.VehicleSurvayStatCollectIterator;


/**
 * Hello world!
 *
 */
public class App 
{
	public static void main( String[] args ) throws URISyntaxException, FileReaderSrviceException{
		Path path = getFilePath();

		System.out.println("Start Reading Survay");
		ISurvayFileReader survayFileReader = new VehicleSurvayFileReader(path);

		IStatCollectorBuilder statCollectorBuilder = new VehicleSurvayStatCollectorBuilder().build();

		ISurvayStatisticCollector survayStatCollectorIterator = new VehicleSurvayStatCollectIterator(statCollectorBuilder);

		ISurvayBook survayBook = new SurvayBook(survayFileReader, survayStatCollectorIterator);
		survayBook.startSurvay();

		System.out.println("End Reading Survay");
		
//		System.out.println("KM/H: " + Math.round((2.5*1000*60*60)/(145*1000)));

		//    	 printData(path); 
	}

	private static Path getFilePath() throws URISyntaxException {
		//    	return Paths.get(App.class.getClassLoader().getResource("Vehicle Survey Coding Challenge sample data.txt").toURI());
		return Paths.get(App.class.getClassLoader().getResource("testSurvay.txt").toURI());
	}

	static void printData(Path path) {
		LocalTime startTime = LocalTime.of(00, 00);

		try (Stream<String> stream = Files.lines(path)) {

			stream.forEach( line -> {
				String replacedLine = line.replaceFirst("A|B", "");
				System.out.println(line + ":= " + startTime.plus(Long.parseLong(replacedLine), ChronoUnit.MILLIS));
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
