package com.challenge.survey.vehicle.model;

import java.time.LocalTime;

public class SpeedCountingStatistics extends CountingStatistics {

	private long upStreamSpeed;

	private long downStreamSpeed;

	public SpeedCountingStatistics(LocalTime startTime, LocalTime endTime) {
		super(startTime, endTime);
	}

	public void addUpStreamSpeed(long speed) {
		upStreamSpeed += speed;
	}

	public void addDownStreamSpeed(long speed) {
		downStreamSpeed = +speed;
	}

	public long calculateUpStreamAverage() {
		return upStreamSpeed / getUpStreamCount();
	}

	public long calculatDownStreamAverage() {
		return downStreamSpeed / getDownStreamCount();
	}

	@Override
	public String toString() {

		String message = "";

		if (upStreamSpeed > 0) {
			message = String.format("Upstream speed: %d KM/H, Total vehicles traveld: %d, Time Period: %s",
					calculateUpStreamAverage(), super.getUpStreamCount(), formatTimeStatistics());
		}

		if (downStreamSpeed > 0) {
			message = message.concat("\n")
					.concat(String.format("Downstream speed: %d, Total vehicles traveld: %d, Time Period: %s",
							calculatDownStreamAverage(), super.getDownStreamCount(), formatTimeStatistics()));
		}

		return message;
	}

}
