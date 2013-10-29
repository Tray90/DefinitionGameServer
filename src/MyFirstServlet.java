import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import org.json.*;

public class MyFirstServlet extends HttpServlet {
	private JSONObject info_ = new JSONObject();

	public static void main(String[] args) throws Exception {
		// Sets up and starts the server
		Server server = new Server(8080);

		WebAppContext context = new WebAppContext();
		context.setWar("war");
		context.setContextPath("/");
		server.setHandler(context);

		server.start();
		server.join();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		// Populates the server with some default values so it's not empty for
		// demonstration purposes
		if (info_.length() == 0) {
			populateInfo();
		}
		// Returns the json string on an http get
		resp.getWriter().write(info_.toString());
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		try {
			// Parameters contain the login name and the score of the user
			String loginId = req.getParameter("loginId");
			String score = req.getParameter("score");
			// Ignores empty strings
			if (loginId == "") {
				// do Nothing
			}
			// Updates an existing user's score
			else if (info_.has(loginId)) {
				if (Integer.parseInt(score) > 0) {
					info_.remove(loginId);
					JSONObject json = new JSONObject();
					json.put("score", Integer.parseInt(score));
					info_.put(loginId, json);
				}
			// Creates a new user and sets their score to 0 if they did not input one greater
			} else {
				JSONObject json = new JSONObject();
				if (Integer.parseInt(score) > 0) {
					json.put("score", Integer.parseInt(score));
				} else {
					json.put("score", 0);
				}
				info_.put(loginId, json);
			}
		} catch (JSONException e) {

			e.printStackTrace();
		}
		try {
			// Returns a json string with all the info
			resp.getWriter().write(info_.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void populateInfo() {
		try {
			// Hard-coded values for demo
			JSONObject json = new JSONObject();
			json.put("score", 1);
			info_.put("Tray", json);
			JSONObject json1 = new JSONObject();
			json1.put("score", 6);
			info_.put("Ben", json1);
			JSONObject json2 = new JSONObject();
			json2.put("score", 321);
			info_.put("Sam", json2);
			JSONObject json3 = new JSONObject();
			json3.put("score", 54);
			info_.put("Mike", json3);
			JSONObject json4 = new JSONObject();
			json4.put("score", 2);
			info_.put("Barry", json4);
			JSONObject json5 = new JSONObject();
			json5.put("score", 7);
			info_.put("Sarah", json5);
			JSONObject json6 = new JSONObject();
			json6.put("score", 3);
			info_.put("Reggie", json6);
		} catch (JSONException e) {

			e.printStackTrace();
		}
	}
}
