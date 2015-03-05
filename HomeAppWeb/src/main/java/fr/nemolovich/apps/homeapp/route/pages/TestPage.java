package fr.nemolovich.apps.homeapp.route.pages;

import fr.nemolovich.apps.nemolight.route.WebRouteServlet;
import fr.nemolovich.apps.nemolight.route.annotations.PageField;
import fr.nemolovich.apps.nemolight.route.annotations.RouteElement;
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
	}

	@Override
	protected void doPost(Request request, Response response,
		SimpleHash root) throws TemplateException, IOException {

	}
}
