package com.movienotes;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.jws.soap.SOAPBinding.Use;
import javax.mail.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.apache.catalina.startup.HomesUserDatabase;
import org.json.simple.JSONArray;

import com.google.appengine.api.search.query.ExpressionParser.negation_return;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@SuppressWarnings("serial")
public class LoginAction extends HttpServlet {
	// private static final Logger log =
	// Logger.getLogger(LoginAction.class.getName());

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		HttpSession serv = req.getSession();
		PrintWriter pout = resp.getWriter();
		JSONObject jsobj = new JSONObject();

		Long userid = serv.getAttribute("loggedinUserId") != null ? (Long) serv
				.getAttribute("loggedinUserId") : null;
		Long orgUserId = null;
		String id = req.getParameter("fbid");
		String name = URLDecoder.decode(req.getParameter("fbname"));
		ArrayList<Long> friends = new ArrayList<Long>();
		DBUtil dbutil = new DBUtil();

		// session
		if (userid != null) {
			orgUserId = dbutil.getUserOrgId(userid);
		} // new user or first time
		else if (id != null) {
			orgUserId = dbutil.validateUserLogin(new Long(id), name);
			userid = new Long(id);
		}

		if (orgUserId == null) { // error
			try {
				jsobj.put("status", false);
				pout.print(jsobj.toString());
				return;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			try {
				com.movienotes.User userObj = dbutil.getUserById(orgUserId);
				serv.setAttribute("loggedinUserName", userObj.getName());
				serv.setAttribute("loggedinUserId", userObj.getFacebookUserID());
				jsobj.put("status", true);
				pout.print(jsobj.toString());

			} catch (JSONException e) {
				e.printStackTrace();
			}
			return;
		}

		/*
		 * friends = dbutil.getUserFriends(userObj.getFacebookUserID());
		 * req.setAttribute("user", userObj); req.setAttribute("me", true);
		 * req.setAttribute("friends", friends); System.out.print("f" +
		 * friends); serv.setAttribute("loggedinUserName", userObj.getName());
		 * serv.setAttribute("loggedinUserId", userObj.getFacebookUserID());
		 * RequestDispatcher rd = req
		 * .getRequestDispatcher("jsps/MovieNotesHomePage.jsp");
		 * resp.setHeader("Cache-Control", "no-cahce");
		 * resp.setHeader("Expires", "0");
		 * 
		 * rd.forward(req, resp); return;
		 */

	}
}
