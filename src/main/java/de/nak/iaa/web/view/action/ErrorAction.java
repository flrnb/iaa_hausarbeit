package de.nak.iaa.web.view.action;

import java.util.Arrays;

import com.opensymphony.xwork2.Action;

import de.nak.iaa.web.util.DataHelper;

public class ErrorAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public static final int KEINE_ID_UEBERGEBEN = 1;
	public static final int UNBEKANNTER_FEHLER = 2;
	public static final Integer[] errorCodes = new Integer[] { KEINE_ID_UEBERGEBEN, UNBEKANNTER_FEHLER };

	private String errorMessage;

	public String show() {
		if (getParameters().containsKey("errorCode")) {
			if (Arrays.asList(errorCodes).contains(
					Integer.parseInt(DataHelper.stringArrayToString(getParameters().get("errorCode"))))) {
				setErrorMessage(getMsg("errorActionCode."
						+ DataHelper.stringArrayToString(getParameters().get("errorCode"))));
			}
		} else {
			setErrorMessage(getMsg("errorActionCode." + UNBEKANNTER_FEHLER));
		}

		return Action.SUCCESS;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
