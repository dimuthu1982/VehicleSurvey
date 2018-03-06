package com.challenge.survey.vehicle.utils;

public class SurveyUtils {

	private SurveyUtils() {
		
	}
	
	public static long calculateSpeedKMPH(long speedInMilliseconds) {
		return  Math.round((2.5 * 1000 * 60 * 60) / (speedInMilliseconds * 1000));
	}
}
