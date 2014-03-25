package com.movienotes;

import java.io.Serializable;

public class MovieNote implements Serializable {
	private static final long serialVersionUID = 2L;

	public int getMovieListType() {
		return movieListType;
	}

	public void setMovieListType(int movieListType) {
		this.movieListType = movieListType;
	}

	private Movie movie;

	/**
	 * @return the movie
	 */
	public Movie getMovie() {
		return movie;
	}

	/**
	 * @param movie
	 *            the movie to set
	 */
	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the suggestedBy
	 */
	public User getSuggestedBy() {
		return suggestedBy;
	}

	/**
	 * @param suggestedBy
	 *            the suggestedBy to set
	 */
	public void setSuggestedBy(User suggestedBy) {
		this.suggestedBy = suggestedBy;
	}

	private User user;
	private int movieListType;
	private User suggestedBy;

	public final int TO_WATCH_LIST = 1;
	public final int MAY_BE_LIST = 2;
	public final int FAV_LIST = 3;
	public final int RECCOMENDED_LIST = 4;
	public final int SUGGESTED = 5;

}
