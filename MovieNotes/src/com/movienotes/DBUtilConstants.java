package com.movienotes;

public class DBUtilConstants {

	
	  public static final String TABLE_USERS = "User";
	  public static final String TABLE_MOVIES = "devices";
	  public static final String TABLE_USERFRIENDS = "UserFriends";
	  public static final String MOVIE_NOTES = "MovieNotes";
	  public static final String MOVIE_RATES = "MovieRates";
	  	  
	  // User Base
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_NAME = "name";
	  public static final String COLUMN_FBID = "FBID";
	  public static final String COLUMN_RATING = "RATING";
	  public static final String NUM_WATCHED = "NUMBER_WATCHED";
	  public static final String ACTIVE = "ACTIVE";
	  
	  public static final String COLUMN_IC = "INTERESTS_C";
	  public static final String COLUMN_IR = "INTERESTS_R";
	  public static final String COLUMN_IA = "INTERESTS_A";
	  public static final String COLUMN_ST = "INTERESTS_ST";	  	  	  
	
	  // Link
	  public static final String COLUMN_USERID = "USERID";
	  public static final String COLUMN_FRIENDID = "FRIENDID";	  
	  
	  // Movie Base REG
	  public static final String MOVIE_ID = "movieID";
	  public static final String MOVIE_NAME = "MovieName";
	  public static final String MOVIE_IMDB = " MovieIDImdb";	 
	  
	  // Movie LIsts
	  // Link
	  public static final String MOVIE_LISTTYPE = "LIST_TYPE";
	  public static final String MOVIE_SUGGESTEDID = "SUGGESTEDUSERID";	  // Self or Friend
	  
	  
	  // Movie Rates
	  // Link
	  public static final String MOVIE_RATING = "RATING";
	  public static final String MOVIE_COMMENT = "COMMENTS";	  
	  
	  
}
