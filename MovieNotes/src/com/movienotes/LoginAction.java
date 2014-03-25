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

		
		if (serv.getAttribute("loggedinUserId") != null) {
			System.out.print("testing");
			RequestDispatcher rd = req
					.getRequestDispatcher("jsps/MovieNotesHomePage.jsp");
			resp.setHeader("Cache-Control", "no-cahce");
			resp.setHeader("Expires", "0");
			rd.forward(req, resp);
			
			return;
		}
		

		String id = req.getParameter("fbid");
		String name =  URLDecoder.decode(req.getParameter("fbname"));
		System.out.print("testing"+id);

		if (id != null) {
			DBUtil dbutil = new DBUtil();
			Long statid = dbutil.validateUserLogin(new Long(id),name);

			if (statid != null) {
				serv.setAttribute("loggedinUserName", name);
				serv.setAttribute("loggedinUserId", id);
//				try {
//					jsobj.put("status", true);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
				System.out.print("testing1");
				RequestDispatcher rd = req
						.getRequestDispatcher("jsps/MovieNotesHomePage.jsp");
				resp.setHeader("Cache-Control", "no-cahce");
				resp.setHeader("Expires", "0");
				rd.forward(req, resp);
				return;
			} 
			//pout.print(jsobj.toString());

		}
	}
}
