package com.challenge.survey.vehicle.settings;

import java.time.LocalTime;

public enum SurveySessions {

	MORNING_SESSION("Morning", LocalTime.of(8, 00), LocalTime.of(11, 59)),
	EVENING_SESSION("Evening", LocalTime.of(12, 00), LocalTime.of(16, 59));

	private final String session;
	private final LocalTime startTime;
	private final LocalTime endTime;

	SurveySessions(String session, LocalTime startTime, LocalTime endTime) {
		this.session = session;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public String getSession() {
		return session;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}
}
