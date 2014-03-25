package com.movienotes;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
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

public class MovieOperations extends HttpServlet {
	private static final Logger log = Logger.getLogger(GetDevices.class
			.getName());

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		HttpSession serv = req.getSession();
		JSONObject jsobj = new JSONObject();
		PrintWriter pout = resp.getWriter();

		String username = (String) serv.getAttribute("loggedinUserName");
		Long userid = (Long) serv.getAttribute("loggedinUserId");

		DBUtil dbutil = new DBUtil();

		String action = req.getParameter("action") != null ? req
				.getParameter("action") : null;
		System.out.print("test");

		if (action != null
				&& (action.equalsIgnoreCase("addMovieNote") || action
						.equalsIgnoreCase("editDevice"))) {

			String devicename = URLDecoder.decode(req
					.getParameter("devicename"));
			Long newedittaskid = null;
			boolean editmode = false;

			if (action.equalsIgnoreCase("editDevice")) {
				editmode = true;
				newedittaskid = req.getParameter("id") != null ? Long
						.parseLong(req.getParameter("id")) : null;
				if (newedittaskid != null) {
					// ins = dbutil.getItemByItemId(newedittaskid);
					// taskchecked = ins.isChecked();
				}
			}

			Movie deviceObj = new Movie();
			deviceObj.setName(devicename);
			deviceObj.setUserId(userid);

//			if (action.equalsIgnoreCase("editDevice")) {
//				/*
//				 * if(newedittaskid!=null){ ins =
//				 * dbutil.getItemByItemId(newedittaskid);
//				 * taskObj.setId(newedittaskid);
//				 * taskObj.setChecked(ins.isChecked()); if(ins.getUserId()!=null
//				 * && ins.getUserId().equals(userid)){
//				 * dbutil.updateItem(taskObj); } try { jsobj.put("status",
//				 * true); jsobj.put("taskId", newedittaskid); } catch
//				 * (JSONException e) { e.printStackTrace(); } } PrintWriter pout
//				 * = resp.getWriter(); pout.write(jsobj.toString());
//				 */
//			} else {
//
//				String nickname = URLDecoder.decode(req
//						.getParameter("nick_name"));
//				deviceObj.setNickname(nickname);
//				Long prior = req.getParameter("priority") != null ? Long
//						.parseLong(req.getParameter("priority")) : 0;
//				deviceObj.setPriority(prior);
//				String micAddr = URLDecoder.decode(req
//						.getParameter("micro_address"));
//				deviceObj.setMicAddr(micAddr);
//				Boolean status = req.getParameter("status") != null ? Boolean
//						.parseBoolean(req.getParameter("status")) : false;
//				deviceObj.setStatus(status);
//
//				Boolean active = req.getParameter("active") != null ? Boolean
//						.parseBoolean(req.getParameter("active")) : false;
//				deviceObj.setActive(active);
//
//				Long newdeviceid = dbutil.createMovie(deviceObj);
//				deviceObj.setId(newdeviceid);
//				System.out.print("CREADED" + userid);
//
//				dbutil.createStatEntry(deviceObj);
//				try {
//					jsobj.put("status", true);
//					jsobj.put("deviceId", newdeviceid);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//				pout.write(jsobj.toString());
//			}
//		} else if (action != null && action.equalsIgnoreCase("updateDevice")) {
//			// called by the micro controller alone ..
//
//			Movie deviceObj = new Movie();
//			String micAddr = URLDecoder.decode(req
//					.getParameter("micro_address"));
//			deviceObj.setMicAddr(micAddr);
//			Boolean status = req.getParameter("status") != null ? Boolean
//					.parseBoolean(req.getParameter("status")) : false;
//			deviceObj.setStatus(status);
//			String devicename = URLDecoder.decode(req
//					.getParameter("devicename"));
//			deviceObj.setName(devicename);
//
//			Long id = dbutil.getDeviceByMICANDNAME(micAddr, devicename);
//
//			if (id == null) {
//				deviceObj.setNickname(devicename);
//				deviceObj.setActive(false);
//				deviceObj.setStatus(status);
//				Long newdeviceid = dbutil.createMovie(deviceObj);
//				System.out.print("CREADED" + userid);
//				deviceObj.setId(newdeviceid);
//				dbutil.createStatEntry(deviceObj);
//				try {
//					jsobj.put("status", true);
//					jsobj.put("deviceId", newdeviceid);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//				pout.write(jsobj.toString());
//			} else {
//				Movie udated = dbutil.getItemByItemId(id);
//				udated.setStatus(status);
//				dbutil.updateItem(udated);
//				dbutil.createStatEntry(udated);
//				try {
//					jsobj.put("status", true);
//					jsobj.put("deviceId", id);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//				pout.write(jsobj.toString());
//			}
//
//		} else if (action != null
//				&& action.equalsIgnoreCase("deviceMonitorState")) {
//
//			Long id = req.getParameter("id") != null ? Long.parseLong(req
//					.getParameter("id")) : null;
//			Boolean state = req.getParameter("state") != null ? Boolean
//					.parseBoolean(req.getParameter("state")) : null;
//
//			if (id != null) {
//				if (!state) {
//					Movie dev = new Movie();
//					dev.setUserId(userid);
//					dev.setId(id);
//					dbutil.deleteItemUserMap(dev);
//				} else {
//					String xstate = req.getParameter("xstate") != null ? (req
//							.getParameter("xstate")) : null;
//					String ystate = req.getParameter("ystate") != null ? (req
//							.getParameter("ystate")) : null;
//
//					Movie dev = new Movie();
//					dev.setUserId(userid);
//					dev.setId(id);
//					dev.setXpos(xstate);
//					dev.setYpos(ystate);
//					dbutil.createMovieUserAct(dev);
//				}
//				try {
//					jsobj.put("status", true);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//				pout.write(jsobj.toString());
//			}
//
//		} else if (action != null && action.equalsIgnoreCase("devicePositions")) {
//			Long id = req.getParameter("id") != null ? Long.parseLong(req
//					.getParameter("id")) : null;
//			String xstate = req.getParameter("xstate") != null ? (req
//					.getParameter("xstate")) : null;
//			String ystate = req.getParameter("ystate") != null ? (req
//					.getParameter("ystate")) : null;
//
//			if (id != null) {
//				Movie dev = new Movie();
//				dev.setUserId(userid);
//				dev.setId(id);
//				dev.setXpos(xstate);
//				dev.setYpos(ystate);
//				dbutil.deleteItemUserMap(dev);
//				dbutil.createMovieUserAct(dev);
//				try {
//					jsobj.put("status", true);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//				pout.write(jsobj.toString());
//			}
//		} else if (action != null && action.equalsIgnoreCase("deviceState")) {
//
//			Long id = req.getParameter("id") != null ? Long.parseLong(req
//					.getParameter("id")) : null;
//			Boolean state = req.getParameter("state") != null ? Boolean
//					.parseBoolean(req.getParameter("state")) : null;
//			if (id != null) {
//				Movie dev = dbutil.getItemByItemId(id);
//				if (dev != null && dev.getName() != null) {
//
//					
//
//					// call the Api
//					com.movienotes.User user = dbutil.getUserById(userid);
//				//	String micAddress = user.getMicAddress();// (String)
//					try {
//						jsobj.put("status", true);
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//					
//				
//				}
//				pout.write(jsobj.toString());
//			}

		}

	}
}
