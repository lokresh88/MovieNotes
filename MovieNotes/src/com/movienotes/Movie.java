package com.movienotes;

import java.io.Serializable;

public class Movie implements Serializable {
	private static final long serialVersionUID = 2L;

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
	private long id = -1;
	private String name;
	private String imdbid;
	private Long userId;
	

}
