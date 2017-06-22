package it.polito.tdp.music.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import it.polito.tdp.music.db.MusicDAO;

public class Model {

	private int month;
	private List<Artist>artisti;
	private ArtistIdMap map;
	private SimpleWeightedGraph<Country,DefaultWeightedEdge> graph;
	
	
	public Model(){
		map= new ArtistIdMap();
	}
	
	public List<Country> getCountriesForArtist(Artist a , int mese){

		MusicDAO dao = new MusicDAO();
		return dao.getCountries(a, mese);
	}
	
	public List<Artist> getClassifica(int mese) {
		
			month = mese;
			MusicDAO dao = new MusicDAO();
			List<Artist> reale = dao.getAllArtists(mese, map);
			List<Artist> artisti= new ArrayList<>();
			
			for(int i=0; i<20; i++){
				artisti.add(reale.get(i));
			}
		
		return artisti;
	}

	public void creaGrafo(int mese) {
		graph=new SimpleWeightedGraph<Country,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Set<Country> countries= new HashSet<Country>();

		MusicDAO dao = new MusicDAO();
		
		List<Artist> artistiClassifica = this.getClassifica(mese);
		for(Artist a: artistiClassifica){
			countries.addAll(dao.getCountries(a, mese));
		}
		
		Graphs.addAllVertices(graph, countries);
		
		for(Country c1: graph.vertexSet()){
			for(Country c2: graph.vertexSet()){
				if(!c1.equals(c2)){
					
					int peso = dao.getNumeroArtisti(c1, c2, mese);
						System.out.println(peso+"\n");
					if(peso>0){
						DefaultWeightedEdge e = graph.addEdge(c1, c2);
						graph.setEdgeWeight(e, peso);
					}
				}
			}
		}
		
		System.out.println(graph.toString());
	}

	public int getMaxDistance(int month) {
		int max = Integer.MIN_VALUE;
		
		for(Country ctemp: graph.vertexSet()){
			
			List<Country> vicini = Graphs.neighborListOf(graph, ctemp);
			
			for(Country cvicino: vicini){
				int grado = graph.degreeOf(cvicino);
				
				if(grado > max){
					max = grado;
				}
			}
		}

		return max;
	}

}
