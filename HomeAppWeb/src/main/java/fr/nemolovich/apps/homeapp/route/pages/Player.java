/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.nemolovich.apps.homeapp.route.pages;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import spark.Request;
import spark.Response;
import fr.nemolovich.apps.homeapp.utils.FilesListComparator;
import fr.nemolovich.apps.homeapp.utils.HomeAppConstants;
import fr.nemolovich.apps.homeapp.video.FileExtensionFilter;
import fr.nemolovich.apps.nemolight.route.WebRouteServlet;
import fr.nemolovich.apps.nemolight.route.annotations.RouteElement;
import fr.nemolovich.apps.nemolight.route.file.utils.DeployConfig;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;

/**
 *
 * @author Nemolovich
 */
@RouteElement(path = "/player", page = "player.html")
public class Player extends WebRouteServlet {

	private static final Logger LOGGER = Logger.getLogger(Player.class);
	private static final String APP_VIDEO_ROOT_PATH;

	static {
		DeployConfig config = null;
		try (InputStream is = Player.class.getResourceAsStream("/META-INF/deploy.xml")) {
			config = DeployConfig.loadConfig(is);
		} catch (IOException | JAXBException e) {
			LOGGER.error("Can not load deloyment configuration file: ", e);
		}

		String path = System.getProperty("os.name").startsWith("Windows") ? "C:/"
				: "/";

		if (config != null) {
			if (config.get(DeployConfig.DEPLOY_FILES_PATH) != null) {
				List<String> paths = config
						.getList(DeployConfig.DEPLOY_FILES_PATH);
				for (String p : paths) {
					if (new File(p).exists()) {
						path = p;
						break;
					}
				}
			}
		}
		APP_VIDEO_ROOT_PATH = path;
	}

	public Player(String path, String templateName, Configuration config)
			throws IOException {
		super(path, templateName, config);
	}

	@Override
	protected void doGet(Request request, Response response, SimpleHash root)
			throws TemplateException, IOException {
		String video = request.raw().getParameter("video");
		String subFolder = request.raw().getParameter("folder");

		String rootPath = APP_VIDEO_ROOT_PATH;
		String rootFolderName = new File(rootPath).getName();
		File rootFolder;

		if (subFolder == null || subFolder.isEmpty()) {

			rootFolder = new File(rootPath);

		} else {

			rootPath = rootPath.concat("/").concat(subFolder);
			rootFolder = new File(rootPath);

			File prevFolder = video == null ? rootFolder.getParentFile()
					: rootFolder;
			String parentFolderName = "";
			while (!prevFolder.getName().equals(rootFolderName)) {
				parentFolderName = prevFolder.getName().concat("/")
						.concat(parentFolderName);
				prevFolder = prevFolder.getParentFile();
			}

			if (parentFolderName != null && !parentFolderName.isEmpty()) {
				root.put("parentFolder", parentFolderName);
			}

		}

		if (video == null) {
			List<File> files = Arrays.asList(rootFolder
					.listFiles(new FileExtensionFilter(
							HomeAppConstants.APP_VIDEO_EXTENSIONS, true)));
			Collections.sort(files, new FilesListComparator());
			root.put("files", files);
		} else {
			root.put("src", String.format("file:///%s/%s", rootPath, video));
		}
	}

	@Override
	protected void doPost(Request request, Response response, SimpleHash root)
			throws TemplateException, IOException {
	}

	@Override
	public void getAjaxRequest(JSONObject request, SimpleHash root) {
		super.getAjaxRequest(request, root);
	}
}
