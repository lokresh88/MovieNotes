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

	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}

	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Long getUser() {
		return userId;
	}
	public void setUser(Long user) {
		this.userId = user;
	}

	private Long userId;
	private String Comments;
	private double rating;
	
	
	
	

}
