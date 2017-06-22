package it.polito.tdp.music;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import it.polito.tdp.music.model.Artist;
import it.polito.tdp.music.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class MusicController {
		private Model model;
		private Map< String,Integer> map;
		
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> boxMese;

    @FXML
    private Button btnArtisti;

    @FXML
    private Button btnNazioni;

    @FXML
    private TextArea txtResult;

    

    @FXML
    void doCercaArtisti(ActionEvent event) {
    	String mese = boxMese.getValue();

    	if(mese==null){
    		txtResult.appendText("Scegliere mese\n");
    		return;
    	}
    	int month = map.get(mese);
    	
    	List<Artist> primi = model.getClassifica(month);
    	for(Artist a: primi){
    		txtResult.appendText(a.toString()+" ascolti: "+a.getAscolti()+"\n");
    	}
    }

    @FXML
    void doMaxDistanza(ActionEvent event) {
    	String mese = boxMese.getValue();

    	if(mese==null){
    		txtResult.appendText("Scegliere mese\n");
    		return;
    	}
    	int month = map.get(mese);
    	
    	List<Artist> primi = model.getClassifica(month);
    	
    	model.creaGrafo(month);
    	int max = model.getMaxDistance(month);
    	
    	txtResult.appendText("Massima distanza tra due nodi adiacenti del grafo creato per il mese "+month+" : "+max+"\n");
    }
    @FXML
    void initialize() {
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'MusicA.fxml'.";
        assert btnArtisti != null : "fx:id=\"btnArtisti\" was not injected: check your FXML file 'MusicA.fxml'.";
        assert btnNazioni != null : "fx:id=\"btnNazioni\" was not injected: check your FXML file 'MusicA.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'MusicA.fxml'.";

    }

	public void setModel(Model model) {
		this.model=model;

		boxMese.getItems().addAll(this.getmap().keySet());
		}

	private Map< String,Integer> getmap() {
		if(map==null){
			map= new TreeMap<>();
			map.put("Gennaio",1);
			map.put( "Febbraio", 2);
			map.put("Marzo",3);
			map.put( "Aprile",4);
			map.put( "Maggio",5);
			map.put( "Giugno",6);
			map.put( "Luglio",7);
			map.put( "Agosto",8);
			map.put( "Settembre",9);
			map.put( "Ottobre",10);
			map.put( "Novembre",11);
			map.put( "Dicembre",12);
			
		}
		return map;
	}
}
