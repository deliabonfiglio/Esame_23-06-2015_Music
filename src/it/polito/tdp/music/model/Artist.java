package it.polito.tdp.music.model;

public class Artist {
	
	private int id ;
	private String artist ;
	private int ascolti;
	
	public Artist(int id, String artist) {
		super();
		this.id = id;
		this.artist = artist;
		ascolti=0;
	}
	public String toString(){
		return artist;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}

	public void setAscolti(int int1) {
		this.ascolti=int1;
		
	}
	
	public int getAscolti(){
		return ascolti;
	}
	
}
