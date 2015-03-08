/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.nemolovich.apps.homeapp.route.pages;

import fr.nemolovich.apps.nemolight.route.WebRouteServlet;
import fr.nemolovich.apps.nemolight.route.annotations.RouteElement;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;
import java.io.IOException;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

/**
 *
 * @author Nemolovich
 */
@RouteElement(path = "/player/:video", page = "player.html")
public class Player extends WebRouteServlet {

	private static final String DEFAULT_PATH = "F:/Users/All/Mes Videos";
	private static final String DEFAULT_PROTOCOL = "file";
	private static final String DEFAULT_EXTENSION = "avi";

	public Player(String path, String templateName,
		Configuration config)
		throws IOException {
		super(path, templateName, config);
	}

	@Override
	protected void doGet(Request request, Response response,
		SimpleHash root)
		throws TemplateException, IOException {
		String video = request.params("video");
		String ext = request.raw().getParameter("type");
		ext = ext == null ? DEFAULT_EXTENSION : ext;
		String protocol = request.raw().getParameter("protocol");
		protocol = protocol == null ? DEFAULT_PROTOCOL : protocol;
		String path = request.raw().getParameter("path");
		path = path == null ? DEFAULT_PATH : path;
		if (video == null) {
			root.put("error", "Unknown video");
			root.put("src", "");
		} else {
			root.put("error", "");
			root.put("src", String.format("%s://%s/%s.%s",
				protocol, protocol.equals("file") ? "/"
					: "".concat(path), video, ext));
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
