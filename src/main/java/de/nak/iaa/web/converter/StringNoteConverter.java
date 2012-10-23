package de.nak.iaa.web.converter;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import de.nak.iaa.server.fachwert.Note;
import de.nak.iaa.web.util.DataHelper;

public class StringNoteConverter extends StrutsTypeConverter {

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		return Note.getNote(DataHelper.stringArrayToString(values));
	}

	@Override
	public String convertToString(Map context, Object o) {
		return o.toString();
	}

	// @Override
	// public Object convertValue(Object value, Class toType) {
	// if (toType.equals(String.class))
	// return ((Note) value).toString();
	// else
	// return Note.get((String) value);
	// }
}
