/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.nemolovich.apps.homeapp.ajax;

import fr.nemolovich.apps.homeapp.route.pages.CameraPage;
import fr.nemolovich.apps.nemolight.config.route.RouteElement;
import fr.nemolovich.apps.nemolight.route.WebRouteServlet;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.regex.Matcher;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

/**
 *
 * @author Nemolovich
 */
@RouteElement(path = "/ajax", page = "ajax.tpl")
public class AjaxConnection extends WebRouteServlet {

	private static final String BEAN_KEY = "action";
	private static final String VALUE_KEY = "value";

	public AjaxConnection(String routePath, String page, Configuration config)
		throws IOException {
		super(routePath, page, config);
	}

	@Override
	protected void doGet(Request request, Response response, SimpleHash root)
		throws TemplateException, IOException {
		System.out.println("GET!");
	}

	@Override
	protected void doPost(Request request, Response response, SimpleHash root)
		throws TemplateException, IOException {

		String value = request.raw().getParameter("value");
		String bean = request.raw().getParameter("bean");

		Matcher matcher = CameraPage.CAMERA_DIMENSION.matcher(value);

		if (matcher.matches()) {
			JSONObject result = new JSONObject();

			result.put("width", matcher.group("width"));
			result.put("height", matcher.group("height"));

			root.put(BEAN_KEY, bean);
			root.put(VALUE_KEY, result);
		} else {
			root.put(BEAN_KEY, bean);
			root.put(VALUE_KEY, value);
		}

	}

}