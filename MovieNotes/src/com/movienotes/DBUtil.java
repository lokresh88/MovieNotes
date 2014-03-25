package com.movienotes;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import org.apache.catalina.core.ApplicationContext;
import org.w3c.dom.html.HTMLUListElement;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import com.movienotes.User;

public class DBUtil {

	private static final Logger log = Logger.getLogger(DBUtil.class.getName());
	static Properties prop = null;
	private static ServletContext context;

	public static void setServletContext(ServletContext context) {
		DBUtil.context = context;
	}

	public DBUtil() {
		// TODO Auto-generated constructor stub
		// DBUtil.class.getconservletContext.getResourceAsStream("/WEB-INF/config.txt")
		if(prop!=null)return;
		
		System.out.print(context);
		prop = new Properties();
		InputStream in = context.getResourceAsStream(
				"/WEB-INF/oasis.properties");
		try {

			prop.load(in);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// User Managemnent code ..
	public boolean selectUser(Long name) {
		Filter userNameFilter = new FilterPredicate(
				DBUtilConstants.COLUMN_FBID, FilterOperator.EQUAL, name);
		Query q = new Query(DBUtilConstants.TABLE_USERS)
				.setFilter(userNameFilter);
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		PreparedQuery pq = datastore.prepare(q);
		int cnt = 0;
		for (@SuppressWarnings("unused")
		Entity res : pq.asIterable()) {
			cnt++;
		}
		if (cnt != 0)
			return true;
		return false;
	}

	/**
	 * Validate User Input : user details contained in the User object. Output :
	 * Status of the database transaction and login details. Description :
	 * Checks the login information.
	 */

	public Long validateUserLogin(Long id,String name) {
		Filter userNameFilter = new FilterPredicate(
				DBUtilConstants.COLUMN_FBID, FilterOperator.EQUAL, id);	
		Query q = new Query(DBUtilConstants.TABLE_USERS)
				.setFilter(userNameFilter);
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		PreparedQuery pq = datastore.prepare(q);
		int cnt = 0;
		Long newid = null;
		for (Entity res : pq.asIterable()) {
			newid = (Long) res.getKey().getId();
			cnt++;
		}
		if (cnt == 0){
			User userObj = new User();
			userObj.setFacebookUserID(id);
			userObj.setName(name);
			userObj = createUser(userObj );
			newid = userObj.getId();
		}
		return newid;
	}

	/**
	 * getUserByID Input : userid Output : User Object representing his
	 * information Description : gets the user by id.
	 */

	public User getUserById(long id) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Entity userent;
		User newUser = new User();
		

		try {
			userent = datastore.get(KeyFactory.createKey(
					DBUtilConstants.TABLE_USERS, id));
			newUser.setId(userent.getKey().getId());
			newUser.setName((String) userent
					.getProperty(DBUtilConstants.COLUMN_NAME));
	
		} catch (EntityNotFoundException e) {
			log.log(Level.WARNING,
					"There is an exception, if not expected, uncomment line below to debug");
			 e.printStackTrace();
			return null;
		}
		return newUser;
	}

	public boolean updateUsers(User userObj) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity user = new Entity(DBUtilConstants.TABLE_USERS, userObj.getId());
		user.setProperty(DBUtilConstants.COLUMN_NAME, userObj.getName());
		datastore.put(user);
		userObj.setId(user.getKey().getId());
		return true;
	}

	/**
	 * addNewUser Input : user details contained in the User object. Output :
	 * Status of the database transaction and new user details. Description :
	 * Adds a new user , creates default list and returns the new user id.
	 */

	public User createUser(User userObj) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity user = new Entity(DBUtilConstants.TABLE_USERS);
		user.setProperty(DBUtilConstants.COLUMN_NAME, userObj.getName());
		user.setProperty(DBUtilConstants.COLUMN_FBID, userObj.getFacebookUserID());
			datastore.put(user);
		userObj.setId(user.getKey().getId());
		return userObj;
	}

	/**
	 * deleteUser Input : user details contained in the User object. Output :
	 * Status of the database transaction. Description : delete the user
	 * provided.
	 */

	public void deleteUser(User user) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		datastore.delete(KeyFactory.createKey(DBUtilConstants.TABLE_USERS,
				user.getId()));
	}

	public Movie getItemByItemId(long id) {

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Entity taskent;
		Movie item = new Movie();
		// form the query
		try {
			taskent = datastore.get(KeyFactory.createKey(
					DBUtilConstants.TABLE_MOVIES, id));
			item = cursorToToDoItem(taskent);

		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
		return item;
	}

	/**
	 * createItem Input : item details contained in the ToDOItem object. Output
	 * : Status of the database transaction and new item details. Description :
	 * Adds a new item and returns the new item id.
	 */

	public long createMovie(Movie item) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity task = new Entity(DBUtilConstants.TABLE_MOVIES);
		task.setProperty(DBUtilConstants.MOVIE_NAME, item.getName());
		task.setProperty(DBUtilConstants.MOVIE_IMDB, item.getImdbid());
		datastore.put(task);
		long taskid = (task.getKey().getId());
		return taskid;
	}
	
	public long createMovieUserAct(Movie item,int type,Long suggest) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity task = new Entity(DBUtilConstants.MOVIE_NOTES);
		task.setProperty(DBUtilConstants.COLUMN_USERID, item.getUserId());		
		task.setProperty(DBUtilConstants.MOVIE_ID, item.getId());
		task.setProperty(DBUtilConstants.MOVIE_LISTTYPE, type );
		task.setProperty(DBUtilConstants.MOVIE_SUGGESTEDID, suggest);
		datastore.put(task);
		long taskid = (task.getKey().getId());
		return taskid;
	}
	
	public void deleteItemUserMap(Movie item) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();		
		Filter userNameFilter = new FilterPredicate(
				DBUtilConstants.COLUMN_USERID,
				FilterOperator.EQUAL, item.getUserId());
		Filter pwdFilter = new FilterPredicate(DBUtilConstants.MOVIE_ID,
				FilterOperator.EQUAL,item.getId());
		Query q = new Query(DBUtilConstants.MOVIE_NOTES)
				.setFilter(CompositeFilterOperator.and(userNameFilter,
						pwdFilter));
		PreparedQuery pq = datastore.prepare(q);
		for (Entity res : pq.asIterable()) {
			datastore.delete(res.getKey());			
		}		
	}
	
	public Movie updateUserItemTable(Movie item,long id,int type,Long suggest) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity task = new Entity(DBUtilConstants.MOVIE_NOTES, id);		
		task.setProperty(DBUtilConstants.COLUMN_USERID, item.getUserId());		
		task.setProperty(DBUtilConstants.MOVIE_ID, item.getId());
		task.setProperty(DBUtilConstants.MOVIE_LISTTYPE, type );
		task.setProperty(DBUtilConstants.MOVIE_SUGGESTEDID, suggest);			
		datastore.put(task);
		return item;
	}
	



	public String getProperties(String key) {
		return prop.get(key) != null ? (String) prop.get(key) : null;
	}


	/**
	 * getListOfItemsForUser Input : user details contained in the GtodoUser
	 * object. Output : Status of the database transaction and list of tasks
	 * details. Description : get the items for the user.
	 */

	public HashMap<String, ArrayList<Movie>> getMoviesListing(Long userId) {
		System.out.println("****************SUCCESS");
		try {

			ArrayList<Movie> inactiveList = new ArrayList<Movie>();
			ArrayList<Movie> activeList = new ArrayList<Movie>();
			Filter userNameFilter = new FilterPredicate(
					DBUtilConstants.COLUMN_USERID, FilterOperator.EQUAL, userId);
			Query q = new Query(DBUtilConstants.MOVIE_NOTES)
					.setFilter(userNameFilter);
		
			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			PreparedQuery pq = datastore.prepare(q);
			System.out.print("SUCCESS" + pq.countEntities());
			for (Entity res : pq.asIterable()) {
				Movie item = cursorToToDoItem(res);					
					activeList.add(item);
			}
			// form the result and return
			HashMap<String, ArrayList<Movie>> tasksInTheList = new HashMap<String, ArrayList<Movie>>();
			tasksInTheList.put("ActiveList", activeList);
		
			return tasksInTheList;
		} catch (Exception exp) {
			exp.printStackTrace();
			System.out.print("SUCCESS" + exp.toString());
			return null;
		}

	}
	

	/**
	 * formToDoItem Input : database result. Output : todoItem object.
	 * Description : converts the database result into the todoitem object.
	 */

	private Movie cursorToToDoItem(Entity result) {
		Movie deviceObj = new Movie();
		deviceObj.setId(result.getKey().getId());
		deviceObj.setName((String) result
				.getProperty(DBUtilConstants.MOVIE_NAME));
		deviceObj.setImdbid((String) result
				.getProperty(DBUtilConstants.MOVIE_IMDB));
		return deviceObj;
	}

	
}
