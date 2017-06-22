package it.polito.tdp.music.db;

import it.polito.tdp.music.model.Artist;
import it.polito.tdp.music.model.ArtistIdMap;
import it.polito.tdp.music.model.City;
import it.polito.tdp.music.model.Country;
import it.polito.tdp.music.model.Listening;
import it.polito.tdp.music.model.Track;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MusicDAO {
	
	public List<Country> getAllCountries() {
		final String sql = "SELECT id, country FROM country" ;
		
		List<Country> countries = new LinkedList<Country>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ) {
				countries.add( new Country(res.getInt("id"), res.getString("country"))) ;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return countries ;
		
	}
	
	public List<City> getAllCities() {
		final String sql = "SELECT id, city FROM city" ;
		
		List<City> cities = new LinkedList<City>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ) {
				cities.add( new City(res.getInt("id"), res.getString("city"))) ;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return cities ;
		
	}

	
	public List<Artist> getAllArtists(int mese, ArtistIdMap map) {
		final String sql = "select a.*, count(l.artistid) as tot "+
							"from artist as a, listening as l "+
							"where a.id=l.artistid and l.month=? "+
							"group by a.id "+
							"order by tot desc " ;
		
		List<Artist> artists = new LinkedList<Artist>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, mese);
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ) {
				Artist a = map.get(res.getInt("id"));
				if(a==null){
					a= new Artist(res.getInt("id"), res.getString("artist"));
					a= map.put(a);
					a.setAscolti(res.getInt("tot"));
				}
				artists.add( a) ;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return artists ;
		
	}

	public List<Track> getAllTracks() {
		final String sql = "SELECT id, track FROM track" ;
		
		List<Track> tracks = new LinkedList<Track>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ) {
				tracks.add( new Track(res.getInt("id"), res.getString("track"))) ;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return tracks ;
		
	}
	
	public List<Listening> getAllListenings() {
		final String sql = "SELECT id, userid, month, weekday, longitude, latitude, countryid, cityid, artistid, trackid FROM listening" ;
		
		List<Listening> listenings = new LinkedList<Listening>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ) {
				listenings.add( new Listening(res.getLong("id"), res.getLong("userid"), res.getInt("month"), res.getInt("weekday"),
						res.getDouble("longitude"), res.getDouble("latitude"), res.getInt("countryid"), res.getInt("cityid"),
						res.getInt("artistid"), res.getInt("trackid"))) ;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return listenings ;
		
	}

	public List<Country> getCountries(Artist a, int mese) {
		final String sql = "SELECT DISTINCT country.* " + 
				"FROM listening,country " + 
				"WHERE listening.artistid=? " + 
				"AND listening.month=? " + 
				"AND listening.countryid=country.id";
		
		
		List<Country> list = new LinkedList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, a.getId());
			st.setInt(2, mese);
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ){
				list.add(new Country(res.getInt("country.id"),res.getString("country.country")));
				
			}
			
			conn.close() ;
		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
		
		return list ;
		
	}

	public int getNumeroArtisti(Country c1, Country c2, int mese) {
		final String sql = "SELECT * " + 
				"FROM listening as l1, listening as l2 " + 
				"WHERE l1.countryid=? " + 
				"AND  l2.countryid=? " + 
				"AND l1.month=? " + 
				"AND l2.month=? " + 
				"AND l1.artistid=l2.artistid " + 
				"GROUP BY l1.artistid ";
		
		
		int num=0;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, c1.getId());
			st.setInt(2, c2.getId());
			st.setInt(3, mese);
			st.setInt(4, mese);
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ){
				num++;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0 ;
		}
		
		return num ;
		
	}
	


}
