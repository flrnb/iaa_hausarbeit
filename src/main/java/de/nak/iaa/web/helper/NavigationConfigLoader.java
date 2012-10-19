package de.nak.iaa.web.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.InputSource;

public class NavigationConfigLoader {

	private final InputSource inputSource;

	public NavigationConfigLoader(File xmlInputStream)
			throws FileNotFoundException {
		inputSource = new InputSource(new FileInputStream(xmlInputStream));
	}

	public String getMenuItemTitle(String menuItemId)
			throws XPathExpressionException {
		return this.getMenuItemProperty("/navigation/item[@id='" + menuItemId
				+ "']/title");
	}

	public String getMenuItemLink(String menuItemId)
			throws XPathExpressionException {
		return this.getMenuItemProperty("/navigation/item[@id='" + menuItemId
				+ "']/link");
	}

	protected String getMenuItemProperty(String xPathString)
			throws XPathExpressionException {
		XPathFactory factory = XPathFactory.newInstance();
		XPath xPath = factory.newXPath();

		String result = xPath.evaluate(xPathString, inputSource);

		return result;
	}
}
