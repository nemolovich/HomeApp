/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.nemolovich.apps.homeapp.route.pages;

import fr.nemolovich.apps.homeapp.utils.HomeAppConstants;
import fr.nemolovich.apps.homeapp.video.FileExtensionFilter;
import fr.nemolovich.apps.nemolight.route.WebRouteServlet;
import fr.nemolovich.apps.nemolight.route.annotations.RouteElement;
import fr.nemolovich.apps.nemolight.session.MessageSeverity;
import fr.nemolovich.apps.nemolight.session.SessionUtils;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.IOException;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

/**
 *
 * @author Nemolovich
 */
@RouteElement(path = "/player", page = "player.html")
public class Player extends WebRouteServlet {
    
    public Player(String path, String templateName,
        Configuration config)
        throws IOException {
        super(path, templateName, config);
    }

    @Override
    protected void doGet(Request request, Response response,
        SimpleHash root)
        throws TemplateException, IOException {
        String video = request.raw().getParameter("video");
        String subFolder = request.raw().getParameter("folder");

        String rootPath = HomeAppConstants.APP_VIDEO_ROOT_PATH;
        if (subFolder != null) {
            rootPath = rootPath.concat("/").concat(subFolder);
        }
        File rootFolder = new File(rootPath);

        if (video == null) {
            root.put("files", rootFolder.listFiles(new FileExtensionFilter(
                HomeAppConstants.APP_VIDEO_EXTENSIONS, true)));
        } else {
            root.put("src", String.format("file://%s/%s",
                rootPath, video));
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
