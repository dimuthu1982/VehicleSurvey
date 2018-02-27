package com.challenge.survey.vehicle.model;

public class SurveyData {
	public static final int DIRECTION_UP = 0;
	
	public static final int DIRECTION_DOWN = 1;
	
	private long firstMark;
	
	private long secondMark;
	
	private int direction;
	
	public SurveyData(long firstMark, long secondMark, int direction) {
		this.firstMark = firstMark;
		this.secondMark = secondMark;
		this.direction = direction;
	}

	public long getFirstMark() {
		return firstMark;
	}

	public void setFirstMark(long firstMark) {
		this.firstMark = firstMark;
	}

	public long getSecondMark() {
		return secondMark;
	}

	public void setSecondMark(long secondMark) {
		this.secondMark = secondMark;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	@Override
	public String toString() {
		return "SurveyData [firstMark=" + firstMark + ", secondMark=" + secondMark + ", direction=" + direction+ "]";
	}
}
