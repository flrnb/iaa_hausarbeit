package de.nak.iaa.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.xwork.StringUtils;

public class DataHelper {

	/**
	 * Konvertiere String[] zu String
	 * 
	 * @param stringArray
	 * @return
	 */
	public static String stringArrayToString(String[] stringArray) {
		return StringUtils.join(stringArray);
	}

	/**
	 * Prüfe ob das übergebene Datum ein valides Datum ist (dd-mm-yyyy) Source:
	 * http://www.rgagnon.com/javadetails/java-0099.html
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isValidDateStr(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			sdf.setLenient(false);
			sdf.parse(date);
		} catch (ParseException e) {
			return false;
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}
}
