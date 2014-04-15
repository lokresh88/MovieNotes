package com.movienotes;

import java.io.Serializable;

public class Movie implements Serializable {
	
	private static final long serialVersionUID = 2L;
	private String action;
	private int addTo;
	private String cast;
	private String genre;
	private double rating;
	private String url;
	private int year;
	private long id = -1;
	private String name;
	private String imdbid;
	/**
	 * @return the collatedAvg
	 */
	public double getCollatedAvg() {
		return collatedAvg;
	}

	/**
	 * @param collatedAvg the collatedAvg to set
	 */
	public void setCollatedAvg(double collatedAvg) {
		this.collatedAvg = collatedAvg;
	}
	private Long userId;
	private boolean romance=false;
	private boolean comedy=false;
	private boolean action_v=false;
	private boolean performance=false;
	private double collatedAvg = 0;
	
	
	public void setGenre(){
		if(genre!=null){
			if(genre.contains("Action")){
				action_v=true;
			}else if(genre.contains("Romance")){
				romance=true;
			}else if(genre.contains("Comedy")){
				comedy=true;
			}
		}
	}
	
	/**
	 * @return the addTo
	 */
	public int getAddTo() {
		return addTo;
	}
	/**
	 * @param addTo the addTo to set
	 */
	public void setAddTo(int addTo) {
		this.addTo = addTo;
	}
	/**
	 * @return the cast
	 */
	public String getCast() {
		return cast;
	}
	/**
	 * @param cast the cast to set
	 */
	public void setCast(String cast) {
		this.cast = cast;
	}
	/**
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}
	/**
	 * @param genre the genre to set
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}
	/**
	 * @return the rating
	 */
	public double getRating() {
		return rating;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setRating(double rating) {
		this.rating = rating;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * @return the mnrating
	 */
	public double getMnrating() {
		return mnrating;
	}
	/**
	 * @param mnrating the mnrating to set
	 */
	public void setMnrating(double mnrating) {
		this.mnrating = mnrating;
	}
	private double mnrating;
	

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
	public String getImdbid() {
		return imdbid;
	}
	public void setImdbid(String imdbid) {
		this.imdbid = imdbid;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public boolean isRomance() {
		return romance;
	}
	public void setRomance(boolean romance) {
		this.romance = romance;
	}
	public boolean isComedy() {
		return comedy;
	}
	public void setComedy(boolean comedy) {
		this.comedy = comedy;
	}
	public boolean isAction_v() {
		return action_v;
	}
	public void setAction_v(boolean action_v) {
		this.action_v = action_v;
	}
	public boolean isPerformance() {
		return performance;
	}
	public void setPerformance(boolean performance) {
		this.performance = performance;
	}
	
	

}
