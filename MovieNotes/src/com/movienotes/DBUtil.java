package com.movienotes;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.TreeMap;
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
import com.google.appengine.api.datastore.Query.CompositeFilter;
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
		if (prop != null)
			return;

		System.out.print(context);
		prop = new Properties();
		InputStream in = context
				.getResourceAsStream("/WEB-INF/oasis.properties");
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

	public Long validateUserLogin(Long id, String name) {
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
		if (cnt == 0) {
			User userObj = new User();
			userObj.setFacebookUserID(id);
			userObj.setName(name);
			userObj = createUser(userObj);
			newid = userObj.getId();
		}
		return newid;
	}

	public Long getUserOrgId(Long fbid) {
		Filter userNameFilter = new FilterPredicate(
				DBUtilConstants.COLUMN_FBID, FilterOperator.EQUAL, fbid);
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
		if (cnt == 0) {
			return null;
		}
		return newid;
	}

	/**
	 * getUserByID Input : userid Output : User Object representing his
	 * information Description : gets the user by id.
	 */

	public User getUserById(Long id) {
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
			newUser.setFacebookUserID((Long) userent
					.getProperty(DBUtilConstants.COLUMN_FBID));
			newUser.setInterest_A(((Long) userent
					.getProperty(DBUtilConstants.COLUMN_IA)).intValue());
			newUser.setInterest_C(((Long) userent
					.getProperty(DBUtilConstants.COLUMN_IC)).intValue());
			newUser.setInterest_R(((Long) userent
					.getProperty(DBUtilConstants.COLUMN_IR)).intValue());
			newUser.setInterest_S(((Long) userent
					.getProperty(DBUtilConstants.COLUMN_ST)).intValue());

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
		user.setProperty(DBUtilConstants.COLUMN_FBID,
				userObj.getFacebookUserID());
		user.setProperty(DBUtilConstants.COLUMN_IA, userObj.getInterest_A());
		user.setProperty(DBUtilConstants.COLUMN_IC, userObj.getInterest_C());
		user.setProperty(DBUtilConstants.COLUMN_IR, userObj.getInterest_R());
		user.setProperty(DBUtilConstants.COLUMN_ST, userObj.getInterest_S());

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
		user.setProperty(DBUtilConstants.COLUMN_FBID,
				userObj.getFacebookUserID());
		user.setProperty(DBUtilConstants.COLUMN_IA, new Integer(3));
		user.setProperty(DBUtilConstants.COLUMN_IC, new Integer(3));
		user.setProperty(DBUtilConstants.COLUMN_IR, new Integer(3));
		user.setProperty(DBUtilConstants.COLUMN_ST, new Integer(3));
		datastore.put(user);
		userObj.setId(user.getKey().getId());
		return userObj;
	}

	public void addFriend(Long userid, Long fid, String fname) {
		if (userid == null || fid == null)
			return;

		Long friendsOrgUserID = getUserOrgId(fid);
		if (friendsOrgUserID == null) {
			User userObj = new User();
			userObj.setName(fname);
			userObj.setFacebookUserID(fid);
			createUser(userObj);
		}

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity user = new Entity(DBUtilConstants.TABLE_USERFRIENDS);
		user.setProperty(DBUtilConstants.COLUMN_USERID, userid);
		user.setProperty(DBUtilConstants.COLUMN_FRIENDID, fid);
		user.setProperty(DBUtilConstants.COLUMN_TRUST, new Long(5));
		datastore.put(user);

		// reverse also
		Entity userRev = new Entity(DBUtilConstants.TABLE_USERFRIENDS);
		userRev.setProperty(DBUtilConstants.COLUMN_USERID, fid);
		userRev.setProperty(DBUtilConstants.COLUMN_FRIENDID, userid);
		userRev.setProperty(DBUtilConstants.COLUMN_TRUST, new Long(5));
		datastore.put(userRev);
	}

	public void editTrust(Long userid, Long fbid, int rating) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter userNameFilter = new FilterPredicate(
				DBUtilConstants.COLUMN_USERID, FilterOperator.EQUAL, fbid);
		Filter pwdFilter = new FilterPredicate(DBUtilConstants.COLUMN_FRIENDID,
				FilterOperator.EQUAL, userid);
		Query q = new Query(DBUtilConstants.TABLE_USERFRIENDS)
				.setFilter(CompositeFilterOperator.and(userNameFilter,
						pwdFilter));
		PreparedQuery pq = datastore.prepare(q);
		for (Entity res : pq.asIterable()) {
			datastore.delete(res.getKey());
		}

		Entity user = new Entity(DBUtilConstants.TABLE_USERFRIENDS);
		user.setProperty(DBUtilConstants.COLUMN_USERID, fbid);
		user.setProperty(DBUtilConstants.COLUMN_FRIENDID, userid);
		user.setProperty(DBUtilConstants.COLUMN_TRUST, new Long(rating));
		datastore.put(user);

	}

	public int getTrust(Long userid, Long fbid) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter userNameFilter = new FilterPredicate(
				DBUtilConstants.COLUMN_USERID, FilterOperator.EQUAL, fbid);
		Filter pwdFilter = new FilterPredicate(DBUtilConstants.COLUMN_FRIENDID,
				FilterOperator.EQUAL, userid);
		Query q = new Query(DBUtilConstants.TABLE_USERFRIENDS)
				.setFilter(CompositeFilterOperator.and(userNameFilter,
						pwdFilter));
		PreparedQuery pq = datastore.prepare(q);
		for (Entity res : pq.asIterable()) {
			return ((Long) res.getProperty(DBUtilConstants.COLUMN_TRUST))
					.intValue();
		}
		return 0;
	}

	public void updatePref(User user, int type, int value) {
		if (value == -1 || type == -1 || type > 4)
			return;

		String[] interests = new String[] { DBUtilConstants.COLUMN_IA,
				DBUtilConstants.COLUMN_IC, DBUtilConstants.COLUMN_IR,
				DBUtilConstants.COLUMN_ST };

		switch (type) {
		case 1:
			user.setInterest_A(value);
			break;
		case 2:
			user.setInterest_C(value);
			break;
		case 3:
			user.setInterest_R(value);
			break;
		case 4:
			user.setInterest_S(value);
			break;
		}
		updateUsers(user);
	}

	public HashMap getUserFriends(Long userId) {
		System.out.println("****************SUCCESS");
		try {

			HashMap result = new HashMap();
			HashMap<Long, Long> flist = new HashMap<Long, Long>();
			int trustrating = 0;

			Filter userNameFilter = new FilterPredicate(
					DBUtilConstants.COLUMN_USERID, FilterOperator.EQUAL, userId);
			Query q = new Query(DBUtilConstants.TABLE_USERFRIENDS)
					.setFilter(userNameFilter);

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			PreparedQuery pq = datastore.prepare(q);
			System.out.print("SUCCESS" + pq.countEntities());
			for (Entity res : pq.asIterable()) {
				flist.put(
						(Long) res.getProperty(DBUtilConstants.COLUMN_FRIENDID),
						(Long) res.getProperty(DBUtilConstants.COLUMN_FRIENDID));
				trustrating += (Long) res
						.getProperty(DBUtilConstants.COLUMN_TRUST);
			}

			result.put("friends", flist);
			if (flist.size() > 0) {
				DecimalFormat df = new DecimalFormat("#.#");
				df.format(trustrating / (double) flist.size());
				result.put("trust",
						df.format(trustrating / (double) flist.size()));
			} else {
				result.put("trust", "5");
			}
			return result;
		} catch (Exception exp) {
			exp.printStackTrace();
			System.out.print("SUCCESS" + exp.toString());
			return null;
		}

	}

	public HashMap getUserFriendsFullInfo(Long userId) {
		System.out.println("****************SUCCESS");
		try {

			HashMap result = new HashMap();
			HashMap<Long, Long> flist = new HashMap<Long, Long>();

			Filter userNameFilter = new FilterPredicate(
					DBUtilConstants.COLUMN_FRIENDID, FilterOperator.EQUAL,
					userId);
			Query q = new Query(DBUtilConstants.TABLE_USERFRIENDS)
					.setFilter(userNameFilter);

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			PreparedQuery pq = datastore.prepare(q);
			System.out.print("SUCCESS" + pq.countEntities());
			for (Entity res : pq.asIterable()) {
				flist.put(
						(Long) res.getProperty(DBUtilConstants.COLUMN_USERID),
						(Long) res.getProperty(DBUtilConstants.COLUMN_TRUST));
			}

			return flist;
		} catch (Exception exp) {
			exp.printStackTrace();
			System.out.print("SUCCESS" + exp.toString());
			return null;
		}

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
		task.setProperty(DBUtilConstants.MOVIE_CAST, item.getCast());
		task.setProperty(DBUtilConstants.MOVIE_IMDBR, item.getRating());
		task.setProperty(DBUtilConstants.MOVIE_GENRE, item.getGenre());
		task.setProperty(DBUtilConstants.MOVIE_URL, item.getUrl());
		task.setProperty(DBUtilConstants.MOVIE_YEAR, item.getYear());
		datastore.put(task);
		long taskid = (task.getKey().getId());
		item.setId(taskid);
		return taskid;
	}

	public long createMovieUserAct(Movie item, int type, Long suggest) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity task = new Entity(DBUtilConstants.MOVIE_NOTES);
		task.setProperty(DBUtilConstants.COLUMN_USERID, item.getUserId());
		task.setProperty(DBUtilConstants.MOVIE_ID, item.getId());
		task.setProperty(DBUtilConstants.MOVIE_LISTTYPE, new Long(type));
		task.setProperty(DBUtilConstants.MOVIE_SUGGESTEDID, suggest);
		datastore.put(task);
		long taskid = (task.getKey().getId());
		return taskid;
	}

	public long createMovieRating(Movie item, String comments, Double ratng) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity task = new Entity(DBUtilConstants.MOVIE_RATES);
		task.setProperty(DBUtilConstants.COLUMN_USERID, item.getUserId());
		task.setProperty(DBUtilConstants.MOVIE_ID, item.getId());
		task.setProperty(DBUtilConstants.MOVIE_RATING, ratng);
		task.setProperty(DBUtilConstants.MOVIE_COMMENT, comments);
		datastore.put(task);
		long taskid = (task.getKey().getId());
		return taskid;
	}

	public void deleteItemUserMap(Movie item, Integer type) {
		deleteItemUserMap(item, type, false);
	}

	public void deleteItemUserMap(Movie item, Integer type,
			Boolean removeFromFavAlso) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter userNameFilter = new FilterPredicate(
				DBUtilConstants.COLUMN_USERID, FilterOperator.EQUAL,
				item.getUserId());
		Filter pwdFilter = new FilterPredicate(DBUtilConstants.MOVIE_ID,
				FilterOperator.EQUAL, item.getId());
		Query q = new Query(DBUtilConstants.MOVIE_NOTES)
				.setFilter(CompositeFilterOperator.and(userNameFilter,
						pwdFilter));

		CompositeFilter filter = CompositeFilterOperator.and(userNameFilter,
				pwdFilter);
		if (type != null) {
			Filter pwdFilter1 = new FilterPredicate(
					DBUtilConstants.MOVIE_LISTTYPE, FilterOperator.EQUAL,
					new Long(type));
			filter = CompositeFilterOperator.and(filter, pwdFilter1);

		}

		if (!removeFromFavAlso) {
			Filter pwdFilter2 = new FilterPredicate(
					DBUtilConstants.MOVIE_LISTTYPE, FilterOperator.NOT_EQUAL,
					DBUtilConstants.LIST_FAV);
			filter = CompositeFilterOperator.and(filter, pwdFilter2);
		}

		q = new Query(DBUtilConstants.MOVIE_NOTES).setFilter(filter);

		PreparedQuery pq = datastore.prepare(q);
		for (Entity res : pq.asIterable()) {
			datastore.delete(res.getKey());
		}
	}

	public void deleteUserMovRating(Movie item) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter userNameFilter = new FilterPredicate(
				DBUtilConstants.COLUMN_USERID, FilterOperator.EQUAL,
				item.getUserId());
		Filter pwdFilter = new FilterPredicate(DBUtilConstants.MOVIE_ID,
				FilterOperator.EQUAL, item.getId());
		Query q = new Query(DBUtilConstants.MOVIE_RATES)
				.setFilter(CompositeFilterOperator.and(userNameFilter,
						pwdFilter));

		PreparedQuery pq = datastore.prepare(q);
		for (Entity res : pq.asIterable()) {
			datastore.delete(res.getKey());
		}
	}

	public Movie updateUserItemTable(Movie item, long id, int type, Long suggest) {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity task = new Entity(DBUtilConstants.MOVIE_NOTES, id);
		task.setProperty(DBUtilConstants.COLUMN_USERID, item.getUserId());
		task.setProperty(DBUtilConstants.MOVIE_ID, item.getId());
		task.setProperty(DBUtilConstants.MOVIE_LISTTYPE, type);
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

	public HashMap<String, Object> getMoviesListing(Long userId, int type) {
		System.out.println("****************SUCCESS");
		try {

			ArrayList<Movie> activeList = new ArrayList<Movie>();
			Filter userNameFilter = new FilterPredicate(
					DBUtilConstants.COLUMN_USERID, FilterOperator.EQUAL, userId);
			Filter typefileter = new FilterPredicate(
					DBUtilConstants.MOVIE_LISTTYPE, FilterOperator.EQUAL,
					new Long(type));
			Query q = new Query(DBUtilConstants.MOVIE_NOTES)
					.setFilter(CompositeFilterOperator.and(userNameFilter,
							typefileter));

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			PreparedQuery pq = datastore.prepare(q);
			System.out.print("SUCCESS" + pq.countEntities());
			ArrayList vals = new ArrayList();
			for (Entity res : pq.asIterable()) {
				com.google.appengine.api.datastore.Key keys = KeyFactory
						.createKey(DBUtilConstants.TABLE_MOVIES, (Long) res
								.getProperty(DBUtilConstants.MOVIE_ID));
				vals.add(keys);
			}

			System.out.print("SUCCESS" + vals);

			if (vals.size() > 0) {
				Filter moviesFilter = new FilterPredicate(
						Entity.KEY_RESERVED_PROPERTY, FilterOperator.IN, vals);
				Query qList = new Query(DBUtilConstants.TABLE_MOVIES)
						.setFilter(moviesFilter);
				PreparedQuery pqlist = datastore.prepare(qList);
				for (Entity res : pqlist.asIterable()) {
					Movie mov = cursorToToDoItem(res);
					activeList.add(mov);
				}
			}

			// form the result and return
			HashMap<String, Object> tasksInTheList = new HashMap<String, Object>();
			tasksInTheList.put("movies", activeList);
			tasksInTheList.put("type", type);
			return tasksInTheList;
		} catch (Exception exp) {
			exp.printStackTrace();
			System.out.print("SUCCESS" + exp.toString());
			return null;
		}

	}

	public HashMap<String, Object> getReccomendations(Long userid) {
		ArrayList<Movie> activeList = new ArrayList<Movie>();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Long userorgid = getUserOrgId(userid);
		User userObj = getUserById(userorgid);

		HashMap<String, Object> tasksInTheList = new HashMap<String, Object>();
		tasksInTheList.put("movies", activeList);
		tasksInTheList.put("type", DBUtilConstants.LIST_REC);

		// friends
		HashMap friends = getUserFriendsFullInfo(userid);
		ArrayList friendsIds = new ArrayList<Long>();
		Collections.addAll(friendsIds, friends.keySet().toArray());
		if (friendsIds.size() == 0) {
			return tasksInTheList;
		}
	
	
		// my friends movies - my movies
		// Movies of friends except those in my list
		Filter moviesFilter = new FilterPredicate(
				DBUtilConstants.COLUMN_USERID, FilterOperator.IN, friendsIds);
		Filter userNameFilter = new FilterPredicate(
				DBUtilConstants.COLUMN_USERID, FilterOperator.NOT_EQUAL, userid);

		Query qList = new Query(DBUtilConstants.MOVIE_NOTES)
				.setFilter(CompositeFilterOperator.and(moviesFilter,
						userNameFilter));

		ArrayList vals = new ArrayList();
		PreparedQuery pqlist = datastore.prepare(qList);
		for (Entity res : pqlist.asIterable()) {
			com.google.appengine.api.datastore.Key keys = KeyFactory.createKey(
					DBUtilConstants.TABLE_MOVIES,
					(Long) res.getProperty(DBUtilConstants.MOVIE_ID));
			vals.add(keys);
		}

		if (vals.size() < 0) {
			return tasksInTheList;
		}

		if (vals.size() > 0) {
			Filter moviesFilterKey = new FilterPredicate(
					Entity.KEY_RESERVED_PROPERTY, FilterOperator.IN, vals);
			Query qListKey = new Query(DBUtilConstants.TABLE_MOVIES)
					.setFilter(moviesFilterKey);
			PreparedQuery pqlistKey = datastore.prepare(qListKey);
			for (Entity res : pqlistKey.asIterable()) {
				Movie mov = cursorToToDoItem(res);
				if (mov.getRating() <= 0)
					mov.setRating(DBUtilConstants.RATING_IMDBDEFAULT);

				mov.setCollatedAvg(mov.getCollatedAvg()
						+ DBUtilConstants.WEIGHT_IMDB * mov.getRating());

				activeList.add(mov);
			}
		}

		ArrayList<Movie> finalsList = new ArrayList<Movie>();
		for (int m = 0; m < activeList.size(); m++) {
			Movie mov = activeList.get(m);
			
			friendsIds.add(userid);
			ArrayList recos = getFriendsReccThisMovie(mov.getId(), userid,friendsIds);
			System.out.println("STEP2 RECC" + recos);

			if(recos.contains(userid)){
				continue;
			}
			
			// Avg Ratings -- from all the friends in the system
			HashMap<Long, MovieRatings> movieRatings = getMovieRatings(userid,
					mov.getId());
			Iterator rItr = movieRatings.keySet().iterator();
			Double avgRating = 0.0;
			while (rItr.hasNext()) {
				Long fbidV = (Long) rItr.next();
				MovieRatings movrating = movieRatings.get(fbidV);
				Double val = movrating.getRating();
				avgRating += val;
			}

			int size = movieRatings.size();
			if (avgRating > 0) {
				avgRating /= size;
				mov.setCollatedAvg(mov.getCollatedAvg() + avgRating
						* DBUtilConstants.WEIGHT_MOVIENOTES);
			} else {
				mov.setCollatedAvg(mov.getCollatedAvg()
						+ DBUtilConstants.WEIGHT_MOVIENOTES
						* DBUtilConstants.RATING_IMDBDEFAULT);
			}


			// trust factor
			Double trust = DBUtilConstants.RATING_IMDBDEFAULT;

			Double avgTrust = 0.0;
		
			for (int r = 0; r < recos.size(); r++) {
				Long user = (Long) recos.get(r);
				if (friends.containsKey(user)) {
					avgTrust += new Double((Long) friends.get(user));
				} else {
					avgTrust += trust;
				}
			}

			// take the average
			if (avgTrust <= 0)
				avgTrust = trust;
			else
				avgTrust /= recos.size();

			mov.setCollatedAvg(mov.getCollatedAvg() + avgTrust
					* DBUtilConstants.WEIGHT_TRUST);
			
			System.out.println("STEP3 RECC" + avgTrust);
			System.out.println("STEP3 RECC" + mov.toString());

			// correlation
			Double similarity = DBUtilConstants.RATING_IMDBDEFAULT;
			Double avgSimilarity = 0.0;
			for (int r = 0; r < recos.size(); r++) {
				Long user = (Long) recos.get(r);
					Long orgid = getUserOrgId(user);
					if (orgid != null) {
						User friend = getUserById(orgid);
						double simil = (userObj.getInterest_A() - friend
								.getInterest_A())
								* (userObj.getInterest_A() - friend
										.getInterest_A())
								+ (userObj.getInterest_C() - friend
										.getInterest_C())
								* (userObj.getInterest_C() - friend
										.getInterest_C())
								+ (userObj.getInterest_R() - friend
										.getInterest_R())
								* (userObj.getInterest_R() - friend
										.getInterest_R())
								+ (userObj.getInterest_S() - friend
										.getInterest_S())
								* (userObj.getInterest_S() - friend
										.getInterest_S());

						similarity = 10 - (simil / 10);
					avgSimilarity+=similarity;
						System.out.println("SIMILLL" + similarity);
					}else{
						avgSimilarity+=similarity;
					}				
			}
			
			// take the average
						if (avgSimilarity <= 0)
							avgSimilarity = similarity;
						else
							avgSimilarity /= recos.size();

						mov.setCollatedAvg(mov.getCollatedAvg() + avgSimilarity
								* DBUtilConstants.WEIGHT_TRUST);
						System.out.println("STEP4 RECC" + mov.toString());
						System.out.println("STEP4 RECC" + avgSimilarity);
						
						finalsList.add(mov);

		}
		// friend - colleration

		// friend - trust

		System.out.println("Recommendations " + finalsList);

		TreeMap<Double, Movie> recosSorted = new TreeMap<Double,Movie>();
		for (int movCnt = 0; movCnt < finalsList.size(); movCnt++) {
			Movie m = finalsList.get(movCnt);
			recosSorted.put(m.getCollatedAvg(),m);
		}
		
		ArrayList activeListRecos = new ArrayList(recosSorted.values());
		Collections.reverse(activeListRecos);

		tasksInTheList = new HashMap<String, Object>();
		tasksInTheList.put("movies", activeListRecos);
		tasksInTheList.put("type", DBUtilConstants.LIST_REC);
		return tasksInTheList;
	}

	private ArrayList getFriendsReccThisMovie(long id, Long userid,ArrayList friends) {
	/*	Filter userNameFilter = new FilterPredicate(
				DBUtilConstants.COLUMN_USERID, FilterOperator.NOT_EQUAL, userid);*/
		Filter typefileter = new FilterPredicate(
				DBUtilConstants.MOVIE_ID, FilterOperator.EQUAL,
				new Long(id));		
		Filter typefileter1 = new FilterPredicate(
				DBUtilConstants.COLUMN_USERID, FilterOperator.IN,
				friends);
				
		Query q = new Query(DBUtilConstants.MOVIE_NOTES)
				.setFilter(CompositeFilterOperator.and(
						typefileter,typefileter1));

		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		PreparedQuery pq = datastore.prepare(q);
		System.out.print("SUCCESS" + pq.countEntities());
		ArrayList vals = new ArrayList();
		for (Entity res : pq.asIterable()) {
			vals.add((Long) res
					.getProperty(DBUtilConstants.COLUMN_USERID));
		}
		return vals;
	}

	public HashMap<Long, MovieRatings> getMovieRatings(Long userId, Long movid) {
		HashMap<Long, MovieRatings> vals = new HashMap();

		try {

			Filter userNameFilter = new FilterPredicate(
					DBUtilConstants.MOVIE_ID, FilterOperator.EQUAL, movid);

			// friends
			HashMap friends = getUserFriendsFullInfo(userId);
			friends.put(userId, userId);
			ArrayList friendsIds = new ArrayList<Long>();
			Collections.addAll(friendsIds, friends.keySet().toArray());
			if (friendsIds.size() == 0) {
				return vals;
			}

			// Movies of friends except those in my list
			Filter moviesFilter = new FilterPredicate(
					DBUtilConstants.COLUMN_USERID, FilterOperator.IN,
					friendsIds);

			Query q = new Query(DBUtilConstants.MOVIE_RATES)
					.setFilter(CompositeFilterOperator.and(userNameFilter,
							moviesFilter));

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			PreparedQuery pq = datastore.prepare(q);
			for (Entity res : pq.asIterable()) {
				MovieRatings rating = new MovieRatings();
				rating.setComments((String) res
						.getProperty(DBUtilConstants.MOVIE_COMMENT));
				rating.setRating((Double) res
						.getProperty(DBUtilConstants.MOVIE_RATING));
				rating.setUser((Long) res
						.getProperty(DBUtilConstants.COLUMN_USERID));
				vals.put((Long) res.getProperty(DBUtilConstants.COLUMN_USERID),
						rating);
			}

			return vals;
		} catch (Exception exp) {
			exp.printStackTrace();
			System.out.print("SUCCESS" + exp.toString());
			return vals;
		}

	}

	public ArrayList<Integer> getMovieInfoForUser(Long userId, Long movid) {

		ArrayList<Integer> movieType = new ArrayList<Integer>();
		try {

			Filter userNameFilter = new FilterPredicate(
					DBUtilConstants.COLUMN_USERID, FilterOperator.EQUAL, userId);
			Filter typefileter = new FilterPredicate(DBUtilConstants.MOVIE_ID,
					FilterOperator.EQUAL, new Long(movid));
			Query q = new Query(DBUtilConstants.MOVIE_NOTES)
					.setFilter(CompositeFilterOperator.and(userNameFilter,
							typefileter));

			DatastoreService datastore = DatastoreServiceFactory
					.getDatastoreService();
			PreparedQuery pq = datastore.prepare(q);
			System.out.print("SUCCESS" + pq.countEntities());
			ArrayList vals = new ArrayList();
			for (Entity res : pq.asIterable()) {
				movieType.add(((Long) res
						.getProperty(DBUtilConstants.MOVIE_LISTTYPE))
						.intValue());
			}

			return movieType;
		} catch (Exception exp) {
			exp.printStackTrace();
			System.out.print("SUCCESS" + exp.toString());
			return movieType;
		}

	}

	public Long getMovieId(String moviename) {
		Filter userNameFilter = new FilterPredicate(DBUtilConstants.MOVIE_NAME,
				FilterOperator.EQUAL, moviename);
		Query q = new Query(DBUtilConstants.TABLE_MOVIES)
				.setFilter(userNameFilter);
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		PreparedQuery pq = datastore.prepare(q);
		Long newid = null;
		for (@SuppressWarnings("unused")
		Entity res : pq.asIterable()) {
			newid = (Long) res.getKey().getId();
			break;
		}
		return newid;
	}

	public Long getMovieImdbId(String imdbidstr) {
		Filter userNameFilter = new FilterPredicate(DBUtilConstants.MOVIE_IMDB,
				FilterOperator.EQUAL, imdbidstr);
		Query q = new Query(DBUtilConstants.TABLE_MOVIES)
				.setFilter(userNameFilter);
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		PreparedQuery pq = datastore.prepare(q);
		Long newid = null;
		for (@SuppressWarnings("unused")
		Entity res : pq.asIterable()) {
			newid = (Long) res.getKey().getId();
			break;
		}
		return newid;
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
		deviceObj.setCast((String) result
				.getProperty(DBUtilConstants.MOVIE_CAST));
		deviceObj.setGenre((String) result
				.getProperty(DBUtilConstants.MOVIE_GENRE));
		deviceObj.setRating((Double) result
				.getProperty(DBUtilConstants.MOVIE_IMDBR));
		deviceObj
				.setUrl((String) result.getProperty(DBUtilConstants.MOVIE_URL));
		deviceObj.setYear(((Long) result
				.getProperty(DBUtilConstants.MOVIE_YEAR)).intValue());
		return deviceObj;
	}

	public ArrayList convertMoviesIntoIds(ArrayList<Movie> movies) {
		ArrayList<Long> movieids = new ArrayList<Long>();
		for (int m = 0; m < movies.size(); m++) {
			movieids.add(movies.get(m).getId());
		}
		return movieids;
	}

}
