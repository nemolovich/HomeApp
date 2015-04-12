package fr.nemolovich.apps.homeapp.route.pages;

import fr.nemolovich.apps.nemolight.route.WebRouteServletAdapter;
import fr.nemolovich.apps.nemolight.route.annotations.RouteElement;
import fr.nemolovich.apps.nemolight.route.exceptions.ServerException;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import java.io.IOException;
import spark.Request;
import spark.Response;

/**
 *
 * @author Nemolovich
 */
@RouteElement(path = "/", page = "login.html")
public class HomePage extends WebRouteServletAdapter {

    public HomePage(String routePath, String context, String page,
        Configuration config) throws IOException {
        super(routePath, context, page, config);
    }

    @Override
    protected void doGet(Request request, Response response, SimpleHash root)
        throws ServerException {

        root.put("username", "");
        root.put("login_error", "");
    }

}
