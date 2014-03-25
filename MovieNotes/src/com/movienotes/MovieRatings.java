package com.movienotes;

import java.io.Serializable;

public class MovieRatings implements Serializable {
	private static final long serialVersionUID = 2L;

	private Movie movie;
	
	
	public String getComments() {
		return Comments;
	}
	public void setComments(String comments) {
		Comments = comments;
	}

	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}

	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	private User user;
	private String Comments;
	private int rating;
	
	
	
	

}
