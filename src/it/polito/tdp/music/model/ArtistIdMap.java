package it.polito.tdp.music.model;

import java.util.*;


public class ArtistIdMap {

	private Map<Integer, Artist>map;
	
	
	public ArtistIdMap(){
		this.map= new HashMap<>();
	}
	
	
	public Artist get(Integer id){
		return map.get(id);
	}
	
	public Artist put(Artist value){
		Artist old = map.get(value);
		if(old == null){
			map.put(value.getId(), value);
			return value;
		} else 
			return old;
	}
}
