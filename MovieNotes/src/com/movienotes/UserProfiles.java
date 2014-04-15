package com.movienotes;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.mongodb.DBConnector;

@SuppressWarnings("serial")
public class UserProfiles extends HttpServlet {
	// private static final Logger log =
	// Logger.getLogger(LoginAction.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		HttpSession serv = req.getSession();
		PrintWriter pout = resp.getWriter();
		JSONObject jsobj = new JSONObject();

		String path = req.getPathInfo();
		path = path.replace("/", "");
		System.out.print(path);

		Long userid = serv.getAttribute("loggedinUserId") != null ? (Long) serv
				.getAttribute("loggedinUserId") : null;
		Long orgUserId = null;
		HashMap<Long,Long> friends = new HashMap<Long,Long>();
		DBUtil dbutil = new DBUtil();

		// session
		if (userid != null) {
			System.out.print(userid);
			orgUserId = dbutil.getUserOrgId(userid);
		} else { // error
			try {
				jsobj.put("status", false);
				pout.print(jsobj.toString());
				resp.setHeader("Cache-Control", "no-cahce");
				resp.setHeader("Expires", "0");
				resp.sendRedirect("/jsps/errorPageRedirect.jsp");
				return;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		System.out.print(orgUserId+" "+path);

		if (path.equalsIgnoreCase("me")) {
			com.movienotes.User userObj = dbutil.getUserById(orgUserId);
			HashMap result =  dbutil.getUserFriends(userObj.getFacebookUserID());
			if(result!=null && result.containsKey("friends"))
				friends = (HashMap<Long,Long>) result.get("friends");
			
			req.setAttribute("user", userObj);
			req.setAttribute("me", true);
			req.setAttribute("friends", friends);
			req.setAttribute("trust", result.get("trust"));
			
			HashMap movieslist = dbutil.getMoviesListing(userid,DBUtilConstants.LIST_TOWATCH);
			req.setAttribute("moviesList", movieslist);	
			System.out.print(movieslist);
		
			HashMap moviesListFav = dbutil.getMoviesListing(userid,DBUtilConstants.LIST_FAV);			
			ArrayList favsMap = (ArrayList)moviesListFav.get("movies");
			req.setAttribute("favs", dbutil.convertMoviesIntoIds(favsMap));			
			
			
			System.out.print("f" + friends);
			serv.setAttribute("loggedinUserName", userObj.getName());
			serv.setAttribute("loggedinUserId", userObj.getFacebookUserID());

			RequestDispatcher rd = req
					.getRequestDispatcher("/jsps/MovieNotesHomePage.jsp");
			resp.setHeader("Cache-Control", "no-cahce");
			resp.setHeader("Expires", "0");
			rd.forward(req, resp);
			return;
		} else {

			Long friendsId = null;
			com.movienotes.User userObj = null;
			orgUserId=null;
			
			if (path != null) {
				try {

					friendsId = Long.parseLong(path);
					if (friendsId != null) {
						orgUserId = dbutil.getUserOrgId(friendsId);
					}System.out.print(userid+" testing");
				} catch (Exception exp) {
					friendsId = null;
				}
			}

			if (orgUserId == null) { // error
				try {
					jsobj.put("status", false);
					pout.print(jsobj.toString());
					resp.setHeader("Cache-Control", "no-cahce");
					resp.setHeader("Expires", "0");
					resp.sendRedirect("/jsps/errorPageRedirect.jsp");
					return;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			userObj = dbutil.getUserById(orgUserId);
			HashMap result = dbutil.getUserFriends(userObj.getFacebookUserID());
			if(result!=null && result.containsKey("friends"))
				friends = (HashMap<Long,Long>) result.get("friends");
			
			int rating = 0;
			if(!friendsId.equals(userid)){
				rating = dbutil.getTrust(userid,userObj.getFacebookUserID());
			}
			
			// fetch movies - 1 list
			HashMap movieslist = dbutil.getMoviesListing(friendsId,DBUtilConstants.LIST_TOWATCH);
			HashMap moviesListFav = dbutil.getMoviesListing(friendsId,DBUtilConstants.LIST_FAV);			
			ArrayList favsMap = (ArrayList)moviesListFav.get("movies");
			req.setAttribute("favs", dbutil.convertMoviesIntoIds(favsMap));			
			req.setAttribute("trust", result.get("trust"));	
			req.setAttribute("moviesList", movieslist);	
			req.setAttribute("rating", rating);	
			req.setAttribute("user", userObj);
			req.setAttribute("me", false);
			req.setAttribute("friends", friends);
			System.out.print("f" + friends);
			RequestDispatcher rd = req
					.getRequestDispatcher("/jsps/MovieNotesHomePage.jsp");
			resp.setHeader("Cache-Control", "no-cahce");
			resp.setHeader("Expires", "0");

			rd.forward(req, resp);
			return;
		}
	}
}
