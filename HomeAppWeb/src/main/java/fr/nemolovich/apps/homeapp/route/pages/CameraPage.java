package fr.nemolovich.apps.homeapp.route.pages;

import fr.nemolovich.apps.nemolight.route.WebRouteServlet;
import fr.nemolovich.apps.nemolight.route.annotations.PageField;
import fr.nemolovich.apps.nemolight.route.annotations.RouteElement;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateException;
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
public class CameraPage extends WebRouteServlet {

	private static final String DEFAULT_SERVER = "localhost";
	private static final int DEFAULT_PORT = 5000;
	private static final String DEFAULT_PROTOCOL = "rtsp";

	private final String cameraServer;
	private final int cameraPort;
	private final String cameraProtocol;

	@PageField
	private String piCameraWidth;
	@PageField
	private String piCameraHeight;
	@PageField
	private String piCameraProtocol;
	@PageField
	private String piCameraServer;
	@PageField
	private String piCameraPort;

	private static final Pattern CAMERA_DIMENSION = Pattern
		.compile("^(?<width>\\d{2,4})x(?<height>\\d{2,4})$");

	public CameraPage(String path, String templateName,
		Configuration config)
		throws IOException {
		super(path, templateName, config);
		this.cameraServer = DEFAULT_SERVER;
		this.cameraPort = DEFAULT_PORT;
		this.cameraProtocol = DEFAULT_PROTOCOL;
	}

	@Override
	protected void doGet(Request request, Response response,
		SimpleHash root)
		throws TemplateException, IOException {
		String size = request.params("size");

		Matcher matcher = CAMERA_DIMENSION.matcher(size);

		String cameraWidth;
		String cameraHeight;
		if (matcher.matches()) {
			cameraWidth = matcher.group("width");
			cameraHeight = matcher.group("height");
		} else {
			cameraWidth = "420";
			cameraHeight = "280";
		}
		root.put("pi_camera_width", cameraWidth);
		root.put("pi_camera_height", cameraHeight);
		root.put("pi_camera_protocol", this.cameraProtocol);
		root.put("pi_camera_server", this.cameraServer);
		root.put("pi_camera_port", String.valueOf(this.cameraPort));
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
