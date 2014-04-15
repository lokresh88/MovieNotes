package com.movienotes;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.mortbay.util.ajax.JSON;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.gson.Gson;
import com.mongodb.DBConnector;

public class MovieOperations extends HttpServlet {
	private static final Logger log = Logger.getLogger(GetDevices.class
			.getName());

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		HttpSession serv = req.getSession();
		JSONObject jsobj = new JSONObject();
		PrintWriter pout = resp.getWriter();
		Long userid = (Long) serv.getAttribute("loggedinUserId");
		DBUtil dbutil = new DBUtil();
		String params = req.getParameter("params") != null ? req
				.getParameter("params") : null;
		Gson gson = new Gson();
		if (params != null) {
			Movie mov = gson.fromJson(params, Movie.class);

			if (mov.getAction().equalsIgnoreCase("addMovie")) {

				Long movieid = dbutil.getMovieId(mov.getName());
				System.out.print(movieid + " -->" + mov.getName());
				movieid = (movieid == null) ? dbutil.getMovieImdbId(mov
						.getImdbid()) : movieid;
				mov.setUserId(userid);

				System.out.print(movieid + " -->");
				// validate movie name & imdb id
				if (movieid == null)
					movieid = dbutil.createMovie(mov);

				mov.setId(movieid);

				dbutil.createMovieUserAct(mov, mov.getAddTo(), null);
				try {
					jsobj.put("status", true);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				pout.write(jsobj.toString());
				return;
			}
		}

		String action = req.getParameter("action") != null ? req
				.getParameter("action") : null;

		if (action != null && action.equalsIgnoreCase("markFav")) {
			Long movid = req.getParameter("movid") != null ? Long.parseLong(req
					.getParameter("movid")) : null;
			Boolean set = req.getParameter("set") != null ? Boolean
					.parseBoolean(req.getParameter("set")) : null;
			if (movid != null) {
				try {

					Movie mov = dbutil.getItemByItemId(movid);
					mov.setUserId(userid);
					dbutil.deleteItemUserMap(mov, DBUtilConstants.LIST_FAV);

					if (set)
						dbutil.createMovieUserAct(mov,
								DBUtilConstants.LIST_FAV, null);
					jsobj.put("status", true);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				pout.write(jsobj.toString());
				return;
			}

			// error
			try {
				jsobj.put("status", false);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			pout.write(jsobj.toString());
			return;
		} else if (action != null && action.equalsIgnoreCase("setOptions")) {
			Long movid = req.getParameter("movid") != null ? Long.parseLong(req
					.getParameter("movid")) : null;
			Integer set = req.getParameter("set") != null ? Integer
					.parseInt(req.getParameter("set")) : null;
			if (movid != null) {
				try {
					Movie mov = dbutil.getItemByItemId(movid);
					mov.setUserId(userid);
					dbutil.deleteItemUserMap(mov, null);

					switch (set) {
					case 1:
						dbutil.createMovieUserAct(mov,
								DBUtilConstants.LIST_TOWATCH, null);
						break;
					case 2:
						dbutil.createMovieUserAct(mov,
								DBUtilConstants.LIST_WATCHED, null);
						break;
					case 3:
						dbutil.createMovieUserAct(mov,
								DBUtilConstants.LIST_MAYBE, null);
						break;
					}
					jsobj.put("status", true);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				pout.write(jsobj.toString());
				return;
			}

			// error
			try {
				jsobj.put("status", false);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			pout.write(jsobj.toString());
			return;
		} else if (action != null && action.equalsIgnoreCase("Listing")) {

			Long fbid = req.getParameter("fbid") != null ? Long.parseLong(req
					.getParameter("fbid")) : null;
			Boolean me = true;
			if (fbid != null && !userid.equals(fbid)) {
				me = false;
				userid = fbid;
			}

			System.out.print(userid + " " + me);

			Integer set = req.getParameter("set") != null ? Integer
					.parseInt(req.getParameter("set")) : null;
			if (set != null) {
				HashMap movieslist = new HashMap();

				switch (set) {
				case 1:
					movieslist = dbutil.getMoviesListing(userid,
							DBUtilConstants.LIST_TOWATCH);
					break;
				case 2:
					movieslist = dbutil.getMoviesListing(userid,
							DBUtilConstants.LIST_MAYBE);
					break;
				case 3:
					movieslist = dbutil.getMoviesListing(userid,
							DBUtilConstants.LIST_WATCHED);
					break;
				case 4:
					movieslist = dbutil.getMoviesListing(userid,
							DBUtilConstants.LIST_FAV);
					break;
				case 5:
					movieslist = dbutil.getReccomendations(userid);
					break;
				}

				HashMap moviesListFav = dbutil.getMoviesListing(userid,
						DBUtilConstants.LIST_FAV);
				ArrayList favsMap = (ArrayList) moviesListFav.get("movies");
				req.setAttribute("favs", dbutil.convertMoviesIntoIds(favsMap));
				req.setAttribute("moviesList", movieslist);
				req.setAttribute("me", me);
				req.setAttribute("fbid", userid);

				RequestDispatcher rd = req
						.getRequestDispatcher("/jsps/movieslist.jsp");
				resp.setHeader("Cache-Control", "no-cahce");
				resp.setHeader("Expires", "0");
				rd.forward(req, resp);
				return;

			}

			// error
			try {
				jsobj.put("status", false);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			pout.write(jsobj.toString());
			return;
		} else if (action != null && action.equalsIgnoreCase("movieInfo")) {

			Long fbid = req.getParameter("fbid") != null ? Long.parseLong(req
					.getParameter("fbid")) : null;
			Movie movratings = new Movie();
			movratings.setUserId(userid);
			
			Boolean me = true;
			if (fbid != null && !userid.equals(fbid)) {
				me = false;
				userid = fbid;
			}
			Long movid = req.getParameter("movid") != null ? Long.parseLong(req
					.getParameter("movid")) : null;

			if (movid != null) {

				Movie mov = dbutil.getItemByItemId(movid);
				req.setAttribute("mov", mov);
				req.setAttribute("me", me);
				req.setAttribute("fbid", userid);

				// get movie types

				List movieTypes = dbutil.getMovieInfoForUser(userid, movid);
				req.setAttribute("favMap",
						movieTypes.contains(DBUtilConstants.LIST_FAV));
				req.setAttribute("type", movieTypes);

				
				String comments = "";
				Double rating =0.0;
				
				HashMap<Long,MovieRatings> movieRatings = dbutil.getMovieRatings(movratings.getUserId(), movid);
				if(movieRatings.containsKey(userid)){
					MovieRatings movr = movieRatings.get(userid);
					comments = movr.getComments();
					rating = movr.getRating();
					movieRatings.remove(userid);
				}
				
				Iterator rItr = movieRatings.keySet().iterator();
				Double avgRating = rating;
				while (rItr.hasNext()) {
					Long fbidV = (Long)rItr.next();
					MovieRatings movrating = movieRatings.get(fbidV);
					Double val = movrating.getRating();
					avgRating+=val;
				}
				
				int size = movieRatings.size()+1;
				if(avgRating>0)
					avgRating/=size;
				
				DecimalFormat df = new DecimalFormat("#.#");
				String avgRatingStr = df.format(avgRating);
				
				req.setAttribute("comments", comments);
				req.setAttribute("rating", rating);
				req.setAttribute("avgRating", avgRatingStr);
				req.setAttribute("movieRatings", movieRatings);			
				
				
				RequestDispatcher rd = req
						.getRequestDispatcher("/jsps/movieinfo.jsp");
				resp.setHeader("Cache-Control", "no-cahce");
				resp.setHeader("Expires", "0");
				rd.forward(req, resp);
				return;
			}

		} else if (action != null && action.equalsIgnoreCase("movieRating")) {

			Long movid = req.getParameter("movid") != null ? Long.parseLong(req
					.getParameter("movid")) : null;
			String cmts = req.getParameter("cmts") != null ? req
					.getParameter("cmts") : null;
			Double val = req.getParameter("val") != null ? Double
					.parseDouble(req.getParameter("val")) : 0;

			if (movid != null) {

				Movie mov = dbutil.getItemByItemId(movid);
				mov.setUserId(userid);

				// get movie types

				dbutil.deleteUserMovRating(mov);
				dbutil.createMovieRating(mov, cmts, val);
			}

			// error
			try {
				jsobj.put("status", false);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			pout.write(jsobj.toString());
			return;

		}else if (action != null && action.equalsIgnoreCase("getMovieRatings")) {

			Long movid = req.getParameter("movid") != null ? Long.parseLong(req
					.getParameter("movid")) : null;
		
			if (movid != null) {

				Movie mov = dbutil.getItemByItemId(movid);
				mov.setUserId(userid);

				// get movie types

				String comments = "";
				Double rating =0.0;
				HashMap<Long,MovieRatings> movieRatings = dbutil.getMovieRatings(userid, movid);
				if(movieRatings.containsKey(userid)){
					MovieRatings movr = movieRatings.get(userid);
					comments = movr.getComments();
					rating = movr.getRating();
					movieRatings.remove(userid);
				}
				req.setAttribute("comments", comments);
				req.setAttribute("rating", rating);
				req.setAttribute("movieRatings", movieRatings);
				
				System.out.print(movieRatings.toString());
				
				RequestDispatcher rd = req
						.getRequestDispatcher("/jsps/movierating.jsp");
				resp.setHeader("Cache-Control", "no-cahce");
				resp.setHeader("Expires", "0");
				rd.forward(req, resp);
				return;
			}

			// error
			try {
				jsobj.put("status", false);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			pout.write(jsobj.toString());
			return;

		}

	}

}
