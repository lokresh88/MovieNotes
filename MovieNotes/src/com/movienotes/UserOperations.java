package com.movienotes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.mortbay.util.ajax.JSON;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class UserOperations extends HttpServlet {
	//private static final Logger log = Logger.getLogger(GetTasks.class.getName());

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		System.out.print("testing on oper");
		String action = req.getParameter("action") != null ? req
				.getParameter("action") : null;

		if (action != null && action.equalsIgnoreCase("adduser")) {

			String name = req.getParameter("name");
			Long id = req.getParameter("id")!=null ? Long.parseLong(req.getParameter("id")):null;

			DBUtil dbutil = new DBUtil();
			User userObj = new User();
			userObj.setName(name);
			userObj.setFacebookUserID(id);
			
			
			JSONObject jsobj = new JSONObject();
			if(id!=null){
			if (dbutil.selectUser(id)) {
				try {
					jsobj.put("status", false);
					jsobj.put("errorMessage", "The user mentioned already exist");
				} catch (JSONException e) {
					e.printStackTrace();
				}

			} else {
				userObj = dbutil.createUser(userObj);

				try {
					HttpSession serv=req.getSession();
					jsobj.put("status", true);
					jsobj.put("userID", userObj.getId());
					serv.setAttribute("loggedinUserName", name);
					serv.setAttribute("loggedinUserId", userObj.getFacebookUserID());
									
					try {
						jsobj.put("status", true);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			}else{
				try {
					jsobj.put("status", false);
					jsobj.put("errorMessage", "The FB id is not a valid one.");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			PrintWriter pout = resp.getWriter();
			pout.write(jsobj.toString());
		}
	}
}
