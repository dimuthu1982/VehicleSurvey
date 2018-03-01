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
			message = String.format("%s : Statistics: [Upstream AVG Speed: %d km/h, Total Vehicles Traveled: %d]",formatTimeStatistics(),
					calculateUpStreamAverage(), super.getUpStreamCount());
		}

		if (downStreamSpeed > 0) {
			if(!message.isEmpty()) {
				message = message.concat("\n");
			}

			message = message.concat(String.format("%s : Statistics: [Downstream AVG Speed: %d km/h, Total Vehicles Traveled: %d]",formatTimeStatistics(),
					calculatDownStreamAverage(), super.getDownStreamCount()));
		}

		return message;
	}
	
	public boolean isSpeedCount() {
		return (upStreamSpeed > 0 || downStreamSpeed > 0);
	}
}
