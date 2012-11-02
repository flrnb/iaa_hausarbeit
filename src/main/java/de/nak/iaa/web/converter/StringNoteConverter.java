package de.nak.iaa.web.converter;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import de.nak.iaa.server.fachwert.Note;
import de.nak.iaa.web.util.DataHelper;

public class StringNoteConverter extends StrutsTypeConverter {

	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		return Note.getNote(DataHelper.stringArrayToString(values));
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {
		return o.toString();
	}
}
