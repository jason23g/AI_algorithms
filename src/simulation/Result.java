package simulation;

import graph.Edge;
import graph.Vertex;

public class Result {
	
	//public Vertex result;
	private Vertex s;
	private Edge a;
	
	
	public Result(Vertex s, Edge a) {
		
		
		this.s = s ;
		this.a = a;
		
		
	}


	public Vertex getState() {
		return s;
	}

	public void setState(Vertex s) {
		this.s = s;
	}

	public Edge getAction() {
		return a;
	}

	public void setAction(Edge a) {
		this.a = a;
	}

	
	
	

}
