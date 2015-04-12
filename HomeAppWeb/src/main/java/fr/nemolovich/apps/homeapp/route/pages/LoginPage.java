package fr.nemolovich.apps.homeapp.route.pages;

import fr.nemolovich.apps.nemolight.route.WebRouteServletAdapter;
import fr.nemolovich.apps.nemolight.route.annotations.RouteElement;
import fr.nemolovich.apps.nemolight.route.exceptions.ServerException;
import fr.nemolovich.apps.nemolight.security.CommonUtils;
import fr.nemolovich.apps.nemolight.security.SecurityConfiguration;
import fr.nemolovich.apps.nemolight.security.SecurityStatus;
import fr.nemolovich.apps.nemolight.security.User;
import fr.nemolovich.apps.nemolight.session.MessageSeverity;
import fr.nemolovich.apps.nemolight.session.Session;
import fr.nemolovich.apps.nemolight.session.SessionUtils;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import java.io.IOException;
import spark.Request;
import spark.Response;

/**
 *
 * @author Nemolovich
 */
@RouteElement(path = "/login", page = "login.html", login = true)
public class LoginPage extends WebRouteServletAdapter {

    public LoginPage(String routePath, String context, String page,
        Configuration config) throws IOException {
        super(routePath, context, page, config);
    }

    @Override
    protected void doGet(Request request, Response response,
        SimpleHash root) throws ServerException {
        root.put("username", "");
        root.put("login_error", "");
    }

    @Override
    protected void doPost(Request request, Response response,
        SimpleHash root) throws ServerException {

        String name = request.queryParams("name");
        String password = request.queryParams("password");

        root.put("username", name);

        SecurityStatus status
            = SecurityConfiguration.submitAuthentication(name,
                CommonUtils.getEncryptedPassword(password));

        spark.Session session = request.session(true);
        Session userSession = SessionUtils.getSession(session);

        if (status.equals(SecurityStatus.AUTH_SUCCESS)) {
            User user = SecurityConfiguration
                .getInstance().getUser(name);
            userSession.setUser(user);
            String expectedPage = userSession.getExpectedPage();
            SessionUtils.submitMessage(userSession, "Login succeed",
                "You are now connected.", MessageSeverity.INFO);
            if (expectedPage != null) {
                this.redirect(request, response);
//				userSession.redirect(this, response);
            }
        } else {
            SessionUtils.submitMessage(userSession, "Login error",
                "Login error. Please check your login/password.",
                MessageSeverity.ERROR);
            root.put("login_error",
                "Login error. Please check your login/password.");
        }
    }

}
