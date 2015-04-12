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
@RouteElement(path = "/error/:code", page = "error.html")
public class ErrorPage extends WebRouteServletAdapter {

    public ErrorPage(String routePath, String context, String page,
        Configuration config) throws IOException {
        super(routePath, context, page, config);
    }

    @Override
    protected void doGet(Request request, Response response,
        SimpleHash root) throws ServerException {

        String error = request.params("code");
        root.put("code", error);
        String details = request.session().attribute("error_details");
        if (details != null) {
            root.put("error_details", details);
            request.session().removeAttribute("error_details");
        }
        root.put("error", "System has encountered an error.");
    }

}
