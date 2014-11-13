/**
 * 
 */
package de.quaddy_services.report.format;

import java.text.DecimalFormat;

public class HourTimeFormat implements TimeFormat {
	private String name;
	HourTimeFormat(String aName) {
		// protected
		name = aName;
	}

	private DecimalFormat format = new DecimalFormat("00.00");

	public String format(long aTimeInMillis) {
		double tempCalc = aTimeInMillis;
		tempCalc = tempCalc / (60 * 60 * 1000);
		String tempFormated = format.format(tempCalc);
		return tempFormated;
	}

	private static final int FACTOR = (60 * 60 * 1000) / 100;

	public long roundToDisplay(long aTimeInMillis) {
		int tempRounded = Math.round(aTimeInMillis / FACTOR) * FACTOR;
		return tempRounded;
	}

	public int maxLength() {
		return 5;
	}

	public String getName() {
		return name;
	}

}