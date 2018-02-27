package com.challenge.survey.vehicle.service;

import java.nio.file.Path;
import java.util.regex.Pattern;

import com.challenge.survey.vehicle.exceptions.FileReaderSrviceException;
import com.challenge.survey.vehicle.exceptions.UnrecognizedFileContentException;
import com.challenge.survey.vehicle.model.SurveyData;

public class VehicleSurveyFileReader extends AbstractSurveyFileReader implements ISurveyFileReader {

	private static final Pattern upStreamMatch = Pattern.compile("^A([\\d]+):A([\\d]+)");

	private static final Pattern downStreamMatch = Pattern.compile("^(A([\\d]+):B([\\d]+))[:(A([\\d]+):B([\\d]+))]");

	public VehicleSurveyFileReader(Path path) {
		super(path);
	}

	private long getTme(String data) {
		return Long.parseLong(data.replaceFirst("A", ""));
	}

	private SurveyData getSurveyData(String line1, String line2, int direction) {
		return new SurveyData(getTme(line1), getTme(line2), direction);
	}

	@Override
	protected SurveyData getSurveyData(String rawData) throws FileReaderSrviceException {

		String[] rawDataLines = rawData.split(SurveyFileReader.DELIMETER);

		if (rawDataLines.length == 2) {
			return getSurveyData(rawDataLines[0], rawDataLines[1], SurveyData.DIRECTION_UP);

		} else if (rawDataLines.length == 4) {
			return getSurveyData(rawDataLines[0], rawDataLines[2], SurveyData.DIRECTION_DOWN);
		}
		throw new FileReaderSrviceException("Unable to transform data:" + rawData);
	}

	@Override
	protected String readRawData(SurveyFileReader fileReader)
			throws UnrecognizedFileContentException, FileReaderSrviceException {

		String dataLine1 = fileReader.readLines(2);

		if (upStreamMatch.matcher(dataLine1).find()) {
			return dataLine1;
		}

		String dataLine2 = fileReader.readLines(2);
		String dataLine = dataLine1.concat(SurveyFileReader.DELIMETER).concat(dataLine2);
		if (downStreamMatch.matcher(dataLine).find()) {
			return dataLine;
		}
		throw new UnrecognizedFileContentException(
				String.format("Unrecognized Data Format [%s and %s] ", dataLine1, dataLine2));
	}
}
