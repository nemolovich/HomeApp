/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.nemolovich.apps.homeapp.route.pages;

import fr.nemolovich.apps.nemolight.config.route.RouteElement;
import fr.nemolovich.apps.nemolight.route.WebRouteServlet;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;
import java.io.IOException;
import spark.Request;
import spark.Response;

/**
 *
 * @author Nemolovich
 */
@RouteElement(path = "/error/:code", page = "error.html")
public class ErrorPage extends WebRouteServlet {

	public ErrorPage(String routePath, String page, Configuration config)
		throws IOException {
		super(routePath, page, config);
	}

	@Override
	protected void doGet(Request request, Response response, SimpleHash root)
		throws TemplateException, IOException {

		String error = request.params("code");
		root.put("code", error);
		String details = request.session().attribute("error_details");
		if (details != null) {
			root.put("error_details", details);
			request.session().removeAttribute("error_details");
		}
		root.put("error", "System has encountered an error.");
	}

	@Override
	protected void doPost(Request request, Response response, SimpleHash root)
		throws TemplateException, IOException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}