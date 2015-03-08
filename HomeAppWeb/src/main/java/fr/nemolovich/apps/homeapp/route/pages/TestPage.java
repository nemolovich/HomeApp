package fr.nemolovich.apps.homeapp.route.pages;

import fr.nemolovich.apps.nemolight.route.WebRouteServlet;
import fr.nemolovich.apps.nemolight.route.annotations.PageField;
import fr.nemolovich.apps.nemolight.route.annotations.RouteElement;
import fr.nemolovich.apps.nemolight.security.User;
import fr.nemolovich.apps.nemolight.session.Message;
import fr.nemolovich.apps.nemolight.session.MessageSeverity;
import fr.nemolovich.apps.nemolight.session.Session;
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
@RouteElement(path = "/test", page = "test.html")
public class TestPage extends WebRouteServlet {

	@PageField
	private String field1;
	@PageField
	private String field2;

	public TestPage(String path, String templateName,
		Configuration config) throws IOException {
		super(path, templateName, config);
	}

	@Override
	protected void doGet(Request request, Response response,
		SimpleHash root) throws TemplateException, IOException {
		Session session = new Session();
		session.setUser(new User("User1", "password01"));
		session.addProperty("test1", "This is the test #1");
		session.submitMessage(new Message("Info 1",
			"This is an info message", MessageSeverity.INFO));
		session.submitMessage(new Message("Warning 1",
			"This is a warning message", MessageSeverity.WARNING));
		session.submitMessage(new Message("Error 1",
			"This is an error message", MessageSeverity.ERROR));
		root.put("Session", session);
	}

	@Override
	protected void doPost(Request request, Response response,
		SimpleHash root) throws TemplateException, IOException {

	}
}
