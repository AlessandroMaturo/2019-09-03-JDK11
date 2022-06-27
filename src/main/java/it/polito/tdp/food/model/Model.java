package it.polito.tdp.food.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private FoodDao dao;
	private Graph<String, DefaultWeightedEdge> grafo;
	
	//RICORSIONE (CAMMINO DI PESO MAX)
	private List<String> best;
	private int totPeso;
	
	
	public Model() {
		dao = new FoodDao();
	}
	
	public void creaGrafo(int num) {
		
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, dao.getVertex(num));
		
		Set<String> verticiPossibili=grafo.vertexSet(); //così evito di ricalcolarlo ogni volta
		
		for(Edge ei: dao.getArchiEsistenti()) {
			if(verticiPossibili.contains(ei.getP1()) && verticiPossibili.contains(ei.getP2()) && !ei.getP1().equals(ei.getP2())) {
				if(!grafo.containsEdge(ei.getP1(), ei.getP2())) {
						Graphs.addEdge(this.grafo, ei.getP1(), ei.getP2(), ei.getPeso());	
				}
			}
		}
		
	}
	
	public int numVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int numArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public Set<String> getVertici(){
		return this.grafo.vertexSet();
	}

	public List<Edge> getCorrelate(String porzione) {
		
		List<Edge> result = new LinkedList<>();
		
		List<String> vicini = Graphs.neighborListOf(this.grafo, porzione) ;
		for(String v: vicini) {
			double peso = this.grafo.getEdgeWeight(this.grafo.getEdge(porzione, v)) ;
			result.add(new Edge(porzione, v, (int) peso)) ;
		}
		return result ;
		
	}

	
	
	//RICORSIONE
	public List<String> init(int passi, String partenza) {
		
		this.best= new LinkedList<>();
		List<String> parziale = new LinkedList<>();
		this.totPeso=0;
		int parzialePeso=0;
		
		best.add(partenza);
		parziale.add(partenza);
		
		ricorsione(parziale, parzialePeso, passi);
				
		return best;
	}

	private void ricorsione(List<String> parziale, int parzialePeso, int passi) {
		
		if(parziale.size()==passi) {
			if(parzialePeso>totPeso) {
				best= new LinkedList<>(parziale);
				totPeso=parzialePeso;
			}
		}
		
		
		
		for(String si: Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(si)) {
				int aggiunta=(int) this.grafo.getEdgeWeight(this.grafo.getEdge(si, parziale.get(parziale.size()-1)));
					
				parzialePeso+=aggiunta;
				parziale.add(si);
					
				ricorsione(parziale, parzialePeso);
					
				parziale.remove(parziale.size()-1);
				parzialePeso=parzialePeso-aggiunta;
			}
		}
		

		
		
	}
	
}
