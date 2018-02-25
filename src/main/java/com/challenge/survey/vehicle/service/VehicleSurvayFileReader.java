package com.challenge.survey.vehicle.service;

import java.nio.file.Path;
import java.util.regex.Pattern;

import com.challenge.survey.vehicle.exceptions.FileReaderSrviceException;
import com.challenge.survey.vehicle.exceptions.UnrecognizedFileContentException;
import com.challenge.survey.vehicle.model.SurvayData;

public class VehicleSurvayFileReader extends AbstractSurvayFileReader implements ISurvayFileReader {

	private static final Pattern upStreamMatch = Pattern.compile("^A([\\d]+):A([\\d]+)");

	private static final Pattern downStreamMatch = Pattern.compile("^(A([\\d]+):B([\\d]+))[:(A([\\d]+):B([\\d]+))]");

	public VehicleSurvayFileReader(Path path) {
		super(path);
	}

	private long getTme(String data) {
		return Long.parseLong(data.replaceFirst("A", ""));
	}

	private SurvayData getSurvayData(String line1, String line2, int direction) {
		return new SurvayData(getTme(line1), getTme(line2), direction);
	}

	@Override
	protected SurvayData getSurvayData(String rawData) throws FileReaderSrviceException {

		String[] rawDataLines = rawData.split(SurvayFileReader.DELIMETER);

		if(rawDataLines.length == 2) {
			return getSurvayData(rawDataLines[0], rawDataLines[1], SurvayData.DIRECTION_UP);

		}else if(rawDataLines.length == 4) {
			return getSurvayData(rawDataLines[0], rawDataLines[2], SurvayData.DIRECTION_DOWN);
		}
		throw new FileReaderSrviceException("Unable to transform data:" + rawData);
	}

	@Override
	protected String readRawData(SurvayFileReader fileReader) throws UnrecognizedFileContentException, FileReaderSrviceException {
		
		String dataLine1 = fileReader.readLines(2);
		
		if(upStreamMatch.matcher(dataLine1).find()) {
			return dataLine1;
		}

		String dataLine2 = fileReader.readLines(2);
		String dataLine = dataLine1.concat(SurvayFileReader.DELIMETER).concat(dataLine2);
		if(downStreamMatch.matcher(dataLine).find()) {
			return dataLine;
		}
		throw new UnrecognizedFileContentException(String.format("Unrecognized Data Format [%s and %s] ", dataLine1, dataLine2));
	}
}
