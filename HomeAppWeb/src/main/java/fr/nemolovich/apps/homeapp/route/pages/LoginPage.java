/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.nemolovich.apps.homeapp.route.pages;

import fr.nemolovich.apps.nemolight.constants.NemoLightConstants;
import fr.nemolovich.apps.nemolight.route.WebRouteServlet;
import fr.nemolovich.apps.nemolight.route.annotations.RouteElement;
import fr.nemolovich.apps.nemolight.security.CommonUtils;
import fr.nemolovich.apps.nemolight.security.GlobalSecurity;
import fr.nemolovich.apps.nemolight.security.SecurityConfiguration;
import fr.nemolovich.apps.nemolight.security.SecurityStatus;
import fr.nemolovich.apps.nemolight.security.User;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;
import java.io.IOException;
import spark.Request;
import spark.Response;
import spark.Session;

/**
 *
 * @author Nemolovich
 */
@RouteElement(path = "/login", page = "login.html", login = true)
public class LoginPage extends WebRouteServlet {

	public LoginPage(String routePath, String page, Configuration config)
		throws IOException {
		super(routePath, page, config);
	}

	@Override
	protected void doGet(Request request, Response response, SimpleHash root)
		throws TemplateException, IOException {
		root.put("username", "");
		root.put("login_error", "");
	}

	@Override
	protected void doPost(Request request, Response response, SimpleHash root)
		throws TemplateException, IOException {

		String name = request.queryParams("name");
		String password = request.queryParams("password");

		root.put("username", name);

		SecurityStatus status
			= GlobalSecurity.submitAuthentication(name,
				CommonUtils.getEncryptedPassword(password));
		if (status.equals(SecurityStatus.AUTH_SUCCESS)) {
			User user = SecurityConfiguration
				.getInstance().getUser(name);
			Session session = request.session(true);
			session.attribute(NemoLightConstants.USER_ATTR,
				user);
			String expectedPage = request.session().attribute(
				NemoLightConstants.EXPECTED_PAGE_ATTR);
			if (expectedPage != null) {
				session.removeAttribute(
					NemoLightConstants.EXPECTED_PAGE_ATTR);
				response.redirect(expectedPage);
			}
		} else {
			root.put("login_error",
				"Login error. Please check your login/password.");
		}
	}

}
