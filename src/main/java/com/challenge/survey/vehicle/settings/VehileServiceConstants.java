package com.challenge.survey.vehicle.settings;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class VehileServiceConstants {

	public static List<LocalTime> MORNING_SESSION = Arrays.asList(LocalTime.of(8, 00), LocalTime.of(11, 59));

	public static List<LocalTime> EVENING_SESSION = Arrays.asList(LocalTime.of(12, 00), LocalTime.of(16, 59));
}
