package com.movienotes;

import java.util.ArrayList;

public class User {
	private long id;
	private String name;
	private Long facebookUserID;

	// preferences
	private int interest_C;
	private int interest_R;
	private int interest_A;
	private int interest_S;

	private ArrayList<User> friends;
	private ArrayList<Movie> toWatchList;
	private ArrayList<Movie> allTimeFav;
	private ArrayList<Movie> mayBeList;
	private ArrayList<Movie> movieNotesRecos;
	private ArrayList<Movie> suggestedMovies;
	
	//stats
	private int numbWatched;
	public int getNumbWatched() {
		return numbWatched;
	}

	public void setNumbWatched(int numbWatched) {
		this.numbWatched = numbWatched;
	}

	public int getInterests() {
		return interests;
	}

	public void setInterests(int interests) {
		this.interests = interests;
	}

	private int interests;
	

	public int getInterest_C() {
		return interest_C;
	}

	public void setInterest_C(int interest_C) {
		this.interest_C = interest_C;
	}

	public int getInterest_R() {
		return interest_R;
	}

	public void setInterest_R(int interest_R) {
		this.interest_R = interest_R;
	}

	public int getInterest_A() {
		return interest_A;
	}

	public void setInterest_A(int interest_A) {
		this.interest_A = interest_A;
	}

	public int getInterest_S() {
		return interest_S;
	}

	public void setInterest_S(int interest_S) {
		this.interest_S = interest_S;
	}

	public ArrayList<User> getFriends() {
		return friends;
	}

	public void setFriends(ArrayList<User> friends) {
		this.friends = friends;
	}

	public ArrayList<Movie> getToWatchList() {
		return toWatchList;
	}

	public void setToWatchList(ArrayList<Movie> toWatchList) {
		this.toWatchList = toWatchList;
	}

	public ArrayList<Movie> getAllTimeFav() {
		return allTimeFav;
	}

	public void setAllTimeFav(ArrayList<Movie> allTimeFav) {
		this.allTimeFav = allTimeFav;
	}

	public ArrayList<Movie> getMayBeList() {
		return mayBeList;
	}

	public void setMayBeList(ArrayList<Movie> mayBeList) {
		this.mayBeList = mayBeList;
	}

	public ArrayList<Movie> getMovieNotesRecos() {
		return movieNotesRecos;
	}

	public void setMovieNotesRecos(ArrayList<Movie> movieNotesRecos) {
		this.movieNotesRecos = movieNotesRecos;
	}

	public ArrayList<Movie> getSuggestedMovies() {
		return suggestedMovies;
	}

	public void setSuggestedMovies(ArrayList<Movie> suggestedMovies) {
		this.suggestedMovies = suggestedMovies;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public Long getFacebookUserID() {
		return facebookUserID;
	}

	public void setFacebookUserID(Long facebookUserID) {
		this.facebookUserID = facebookUserID;
	}
}
