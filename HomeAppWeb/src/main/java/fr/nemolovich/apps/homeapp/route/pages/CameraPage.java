package fr.nemolovich.apps.homeapp.route.pages;

import fr.nemolovich.apps.nemolight.route.WebRouteServletAdapter;
import fr.nemolovich.apps.nemolight.route.annotations.PageField;
import fr.nemolovich.apps.nemolight.route.annotations.RouteElement;
import fr.nemolovich.apps.nemolight.route.exceptions.ServerException;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

/**
 *
 * @author Nemolovich
 */
@RouteElement(path = "/camera/:size", page = "camera.html")
public class CameraPage extends WebRouteServletAdapter {

    private static final String DEFAULT_SERVER = "raspberry";
    private static final int DEFAULT_PORT = 5000;
    private static final String DEFAULT_PROTOCOL = "http";
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 800;

    private final String cameraServer;
    private final int cameraPort;
    private final String cameraProtocol;

    @PageField
    private String piCameraWidth;
    @PageField
    private String piCameraHeight;

    private String piCameraProtocol;
    private String piCameraServer;
    private String piCameraPort;

    private static final Pattern CAMERA_DIMENSION = Pattern
        .compile("^(?<width>\\d{2,4})x(?<height>\\d{2,4})$");

    public CameraPage(String path, String context, String templateName,
        Configuration config) throws IOException {
        super(path, context, templateName, config);
        this.cameraServer = DEFAULT_SERVER;
        this.cameraPort = DEFAULT_PORT;
        this.cameraProtocol = DEFAULT_PROTOCOL;
    }

    @Override
    protected void doGet(Request request, Response response,
        SimpleHash root) throws ServerException {
        String size = request.params("size");

        Matcher matcher = CAMERA_DIMENSION.matcher(size);

        String cameraWidth;
        String cameraHeight;
        if (matcher.matches()) {
            cameraWidth = matcher.group("width");
            cameraHeight = matcher.group("height");
        } else {
            cameraWidth = String.valueOf(DEFAULT_WIDTH);
            cameraHeight = String.valueOf(DEFAULT_HEIGHT);
        }
        root.put("pi_camera_width", cameraWidth);
        root.put("pi_camera_height", cameraHeight);
        root.put("pi_camera_protocol", this.cameraProtocol);
        root.put("pi_camera_server", this.cameraServer);
        root.put("pi_camera_port", String.valueOf(this.cameraPort));
    }

    @Override
    public void getAjaxRequest(JSONObject request, SimpleHash root) {
        super.getAjaxRequest(request, root);
    }
}
