package fr.nemolovich.apps.homeapp.utils;

import fr.nemolovich.apps.nemolight.route.file.utils.DeployConfig;

public class HomeAppProperties {

	private static final HomeAppProperties INSTANCE;

	private static final String VIDEO_PATHS = DeployConfig.DEPLOY_FILES_PATH;

	static {
		INSTANCE = new HomeAppProperties();
	}

	public static HomeAppProperties getInstance() {
		return INSTANCE;
	}

	private HomeAppProperties() {

	}

}
