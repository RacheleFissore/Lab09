package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;
import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	private List<Country> countryList;
	Map<Integer, Country> countryIdMap;
	private Graph<Country, DefaultEdge> grafo;
	int annoInput = 0;
	
	public List<Country> getCountry() {
		if(countryList == null) { // Non ho ancora fatto la query e quindi la faccio
			// Aggiungo i vertici al mio grafo. I vertici sono oggetti di tipo Country
			BordersDAO dao = new BordersDAO();
			countryList = dao.loadAllCountries(); 
			
			countryIdMap = new HashMap<Integer, Country>(); // Mappa che, dato l'id, mi restituisca l'oggetto country corrispondente
			for(Country country : countryList) {
				countryIdMap.put(country.getCodice(), country);
			}
		}
		
		return countryList;
	}
	
	public void creaGrafo() {
		// Questa procedura deve istanziare il grafo scegliendo tra una delle 16 classi a disposizione, in questo caso devo scegliere la classe
		// che mi permette di ottenere un grafo semplice, non orientato e non pesato
		grafo = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		
		BordersDAO dao = new BordersDAO();
		List<Border> countryDaCollegare = dao.getCountryPairs(annoInput); // Calcola gli archi
		
		for(Border border : countryDaCollegare) { // Aggiungo solo gli archi tra componenti connesse e i vertici del grafo sono tutte le nazioni tra cui esiste
												  // un confine di terra nell'intervallo di tempo specificato
			grafo.addVertex(countryIdMap.get(border.getCodStato1()));
			grafo.addVertex(countryIdMap.get(border.getCodStato2()));
			grafo.addEdge(countryIdMap.get(border.getCodStato1()), countryIdMap.get(border.getCodStato2()));
		}		
	}
	
	public String calcolaRisultato(int anno) {
		annoInput = anno;
		creaGrafo();
		System.out.println("Vertici = " + grafo.vertexSet().size());
		System.out.println("Archi = " + grafo.edgeSet().size());
		
		String string = "";
		
		ConnectivityInspector connectInspect = new ConnectivityInspector<Country, DefaultEdge>(grafo); 
		int num = connectInspect.connectedSets().size(); // Ottengo l'insieme delle componenti connesse e ne prendo il numero
		
		for(Country country : grafo.vertexSet()) {
			List<Country> viciniCountries = Graphs.neighborListOf(grafo, country); // Mi restituisce la lista dei vicini di una determinata country, cioè i 
																				   // vertici connessi ad un arco da un determinato vertice
			string += country.getNomeStato() + " - " + viciniCountries.size() + "\n";
		}
		
		string += "Numero di componenti connesse : " + num;
		return string;
	}
	
	public String statiRaggiungibili(Country partenza) {
		// Devo prima aver creato il grafo
		creaGrafo();
		String string = "";
		if(grafo.containsVertex(partenza)) { // Controllo se nel grafo è presente il vertice che ho selezionato dalla combobox, se non è presente significa
											 // che non ci sono componenti connesse con quel vertice o che non rispetta i parametri richiesti dalla query
			GraphIterator<Country, DefaultEdge> visita = new BreadthFirstIterator<>(grafo, partenza);
			
			while (visita.hasNext()) { // Finchè c'è un successore si continua ad iterare, cioè finchè ci sono dei vertici, cioè degli stati, raggiungibili 
									   // questi vengono presi
				Country country = visita.next();
				string += country.getNomeStato() + "\n";
			}
		}
		else {
			string = "Non sono presenti stati adiacenti a quello selezionato";
		}
		return string;
	}
}
