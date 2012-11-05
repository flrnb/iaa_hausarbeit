package de.nak.iaa.web.util;

import org.apache.commons.lang.xwork.StringUtils;

/**
 * Statische Klasse zum Auslagern von häufig genutzen Operationen auf den Daten
 * 
 * @author Christopher Biel <christopher.biel89@gmail.com>
 */
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
}
