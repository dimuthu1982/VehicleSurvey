package com.challenge.survey.vehicle.model;

import java.time.LocalTime;

public class TimeStatistics {

	private LocalTime startTime;

	private LocalTime endTime;

	public TimeStatistics(LocalTime startTime, LocalTime endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return formatTimeStatistics();
	}

	protected final String formatTimeStatistics() {
		return String.format("Time Frame [%s - %s] ", startTime, endTime);
	}
}
