package graph;
import java.util.*;
import java.math.*;
import java.awt.Color;
import java.awt.Shape;


public class Graph {
		
		private Hashtable<String, Vertex> vertices;
		private Hashtable<String, Edge> edges;
		private int numOfVertices;
		private int numOfEdges;
		
		private LinkedList<Vertex> shortestPath = null;
		
		public Graph() {
			edges = new Hashtable<String, Edge>();
			vertices = new Hashtable<String, Vertex>();
			numOfVertices = 39;
			
			/*for(int i=0; i<numOfVertices; i++) {
				Vertex v = new Vertex("A"+i);
				if(i==0) v.color = Color.ORANGE;//source node has a different color
				vertices.put(v.getName(), v);
			}*/
			
			
		}
		
		
		
		public Hashtable<String, Vertex> getVertices() {
			return vertices;
		}



		public void setVertices(Hashtable<String, Vertex> vertices) {
			this.vertices = vertices;
		}



		public Hashtable<String, Edge> getEdges() {
			return edges;
		}



		public void setEdges(Hashtable<String, Edge> edges) {
			this.edges = edges;
		}



		public int getNumOfVertices() {
			return numOfVertices;
		}
		
		public void setNumOfVertices(int numOfVertices) {
			this.numOfVertices = numOfVertices;
		}

		public int getNumOfEdges() {
			return numOfEdges;
		}

		public void setNumOfEdges(int numOfEdges) {
			this.numOfEdges = numOfEdges;
		}

		public LinkedList<Vertex> getShortestPath() {
			return shortestPath;
		}

		public void setShortestPath(LinkedList<Vertex> shortestPath) {
			this.shortestPath = shortestPath;
		}
		
		
		
		
}
