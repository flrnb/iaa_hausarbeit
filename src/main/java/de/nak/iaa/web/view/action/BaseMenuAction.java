package de.nak.iaa.web.view.action;

import java.io.File;

import com.opensymphony.xwork2.ActionSupport;

import de.nak.iaa.web.helper.NavigationConfigLoader;

/**
 * This base action will provide data for the header, that will be fetched from
 * the external navigation.xml
 * 
 * To add a new navigationpart, add it to this class and the navigation.xml
 * 
 * @author Christopher Biel
 * 
 */
@SuppressWarnings("serial")
public abstract class BaseMenuAction extends ActionSupport {
	private String mainMenuTitle;
	private String mainMenuLink;

	@Override
	public String execute() throws Exception {
		// TODO find another way to reference the file!
		NavigationConfigLoader loader = new NavigationConfigLoader(new File(
				System.getProperty("user.dir")
						+ "/src/main/webapp/config/navigation.xml"));
		setMainMenuTitle(loader.getMenuItemTitle("mainMenu"));
		return super.execute();
	}

	public String getMainMenuTitle() {
		return mainMenuTitle;
	}

	public void setMainMenuTitle(String mainMenuTitle) {
		this.mainMenuTitle = mainMenuTitle;
	}

	public String getMainMenuLink() {
		return mainMenuLink;
	}

	public void setMainMenuLink(String mainMenuLink) {
		this.mainMenuLink = mainMenuLink;
	}
}
