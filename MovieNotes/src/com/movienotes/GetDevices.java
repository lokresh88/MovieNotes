package com.movienotes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.json.simple.JSONObject;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class GetDevices extends HttpServlet {
	// private static final Logger log =
	// Logger.getLogger(GetTasks.class.getName());

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		HttpSession serv = req.getSession();
		PrintWriter pout = resp.getWriter();

		Long userid = (Long) serv.getAttribute("loggedinUserId");
		Gson jsonbjG = new Gson();
		com.google.appengine.labs.repackaged.org.json.JSONObject jsonbj = new com.google.appengine.labs.repackaged.org.json.JSONObject();
		DBUtil dbutil = new DBUtil();

		Boolean isMobile = req.getParameter("isMobile") != null ? (Boolean
				.parseBoolean(req.getParameter("isMobile"))) : true;

		if (userid == null) {
			try {
				jsonbj.put("status", false);
				jsonbj.put("errorMessage", "username-password not valid :( ");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pout.print(jsonbj.toString());
			return;
		}

		com.movienotes.User user = dbutil.getUserById(userid);
		ArrayList<Movie> devices = new ArrayList<Movie>();
		System.out.println("****************SUCCESS" + userid);
		HashMap<String, ArrayList<Movie>> devicesListings = new HashMap<String, ArrayList<Movie>>();
		try {
	//		devicesListings = dbutil.getDevicesListing(userid, micAddress);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (devicesListings.size() > 0) {
			ArrayList<Movie> activeList = (ArrayList<Movie>) devicesListings
					.get("ActiveList");
			ArrayList<Movie> inactiveList = (ArrayList<Movie>) devicesListings
					.get("InActiveList");
			req.setAttribute("ActiveList", activeList);
			req.setAttribute("InActiveList", inactiveList);
			req.setAttribute("username", user.getName());
			System.out.print(inactiveList);
			System.out.print(activeList);

			try {
				jsonbj.put("ActiveList", jsonbjG.toJson(activeList));
				jsonbj.put("InActiveList", jsonbjG.toJson(inactiveList));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (isMobile) {
			pout.write(jsonbj.toString());
			return;
		}
		// req.setAttribute("devices", jsonbj);
		RequestDispatcher rd = req
				.getRequestDispatcher("jsps/devicesTasks.jsp");
		resp.setHeader("Cache-Control", "no-cahce");
		resp.setHeader("Expires", "0");
		rd.forward(req, resp);
		return;
	}
}
