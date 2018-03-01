package com.challenge.survey.vehicle.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtils {

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	public static String formatTime(LocalTime localTime) {
		return localTime.format(formatter);
	}

	public static LocalTime getLocalTimeEndDate() {
		return LocalTime.of(23, 59);
	}

	public static LocalTime getLocalTimeUpperBound(LocalTime lowrBound, long minuits) {
		return lowrBound.plus(minuits, ChronoUnit.MINUTES).minus(1, ChronoUnit.MILLIS);
	}

	public static LocalTime plusMillisecond(LocalTime time) {
		return time.plus(1, ChronoUnit.MILLIS);
	}

	public static LocalTime minusMillisecond(LocalTime time) {
		return time.minus(1, ChronoUnit.MILLIS);
	}

	public static LocalTime plusMillisecond(LocalTime time, long milliseconds) {
		return time.plus(milliseconds, ChronoUnit.MILLIS);
	}

	public static boolean isLocalDateBetween(LocalTime localDate1, LocalTime localDate2, LocalTime pivotDate) {
		return localDate1.isBefore(pivotDate) && localDate2.isAfter(pivotDate);
	}
	
	/*public static LocalTime truncatedToSeconds(LocalTime time) {
		System.out.println("truncatedToSeconds 1: " + time);
		System.out.println("truncatedToSeconds 2: " + time.truncatedTo(ChronoUnit.SECONDS));
		return time.truncatedTo(ChronoUnit.SECONDS);
	}*/
	
	
}
