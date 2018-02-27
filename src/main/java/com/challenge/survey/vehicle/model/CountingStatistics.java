package com.challenge.survey.vehicle.model;

import java.time.LocalTime;

public class CountingStatistics extends TimeStatistics {

	private int upStreamCount;

	private int downStreamCount;

	public CountingStatistics(LocalTime startTime, LocalTime endTime) {
		super(startTime, endTime);
	}

	public int getUpStreamCount() {
		return upStreamCount;
	}

	public void setUpStreamCount(int upStreamCount) {
		this.upStreamCount = upStreamCount;
	}
	
	public void setDownStreamCount(int downStreamCount) {
		this.downStreamCount = downStreamCount;
	}

	public int getDownStreamCount() {
		return downStreamCount;
	}

	public void addDownStreamCount(int downStreamCount) {
		this.downStreamCount = downStreamCount;
	}

	public void incrementUpStream() {
		upStreamCount = upStreamCount + 1;
	}

	public void incrementDownStream() {
		downStreamCount = downStreamCount + 1;
	}

	public boolean isStatistic() {
		return upStreamCount > 0 || downStreamCount > 0;
	}

	@Override
	public String toString() {
		return String.format("%s : Statistics: [Upstream Count: %d, Downstream Count: %d]", super.toString(),upStreamCount, downStreamCount);
	}
}
