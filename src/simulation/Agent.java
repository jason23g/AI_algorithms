package simulation;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Vector;

import graph.*;

public class Agent {
	
	private Graph city;
	private Vertex source, goal;
	private PriorityQueue<Vertex> frontier;
	private LinkedList<Vertex> shortestPath;
	private boolean foundPath;
	private float[] distribution;
	private int visitedNodeNumber;
	
	
	
	
	public Agent(Graph G, Vertex src, Vertex goal) {
		this.city = G;
		this.source = src;
		this.goal = goal;
		this.frontier = new PriorityQueue<Vertex>();
		this.shortestPath = new LinkedList<Vertex>();
		this.foundPath = false;
		this.distribution = new float[3];
		distribution[0] = 0.6f;
		distribution[1] = 0.2f;
		distribution[2] = 0.2f;
		this.visitedNodeNumber = 0;
			
	}
	
	
	//============================================== IDA star ====================================================================
	
	
	
	
	public Vertex getSource() {
		return source;
	}


	public int getVisitedNodeNumber() {
		return visitedNodeNumber;
	}


	public void setVisitedNodeNumber(int visitedNodeNumber) {
		this.visitedNodeNumber = visitedNodeNumber;
	}


	public void setCity(Graph city) {
		this.city = city;
	}


	public Vertex getGoal() {
		return goal;
	}
	
	//clears the shortest path and resets the values g(n) for all vertices
	public void resetState() {
		
		Vertex v = null;
		
		this.shortestPath.removeAll(this.shortestPath);
		this.frontier.removeAll(this.frontier);
		this.foundPath = false;
		this.visitedNodeNumber = 0;
		
		for(String v_name: this.city.getVertices().keySet()) {
			
			v  = this.city.getVertices().get(v_name);
			
			v.setG_n(Float.MAX_VALUE);
			v.setPred(null);
		}
	}


	public float IDA_star() {
		
		//LinkedList<Vertex> visited = new LinkedList<Vertex>();
		Hashtable<String,Boolean> visited = new Hashtable<String,Boolean>();
		//Hashtable<String, Float> g_n = new Hashtable<String,Float>();
		float currentThreshold = 0;
		
		//Initialization of the predecessors
		for (String key: city.getVertices().keySet()) {
			
			city.getVertices().get(key).setPred(null); //The predecessors are set to be null
			//g_n.put(key, Float.MAX_VALUE); //set all distances from source to infinity
			visited.put(key, false); //all vertices are initially not visited
			
				
		}
		
		source.setG_n(0f);// distance from source to source is 0, duh
		shortestPath.push(source);
		
		
		while(!foundPath) {
			
			if(currentThreshold == Float.MAX_VALUE) {
				System.out.println("Path not found");
				return Float.MAX_VALUE;
			}
				
			//Initialise the Hashtable of visited vertices before each IDA*_visit call
			for (String key: city.getVertices().keySet()) {
				visited.put(key, false);
			}
			float nextThreshold = Float.MAX_VALUE;
			
			currentThreshold = IDA_star_visit(source, visited, currentThreshold,nextThreshold);
		}
		
		return goal.getF_n();//return the predicted cost to get to the goal
		
			
		}
	
	
	
	public float IDA_star_visit(Vertex u,Hashtable<String,Boolean> visited,float currentThreshold,float nextThreshold) {
		
			this.visitedNodeNumber++;
			visited.put(u.getName(), true);
			
			u.setF_n(u.getG_n() + u.getH_n());// f(n) = g(n) + h(n)
			
			if(u.getF_n() > currentThreshold) {
				
				//Find the minimum next threshold out of all possible thresholds 	
				//that exceed the previous threshold value
				if(u.getF_n() < nextThreshold) 
					nextThreshold = u.getF_n();
				
				return nextThreshold;
			}	
			
			if(u.getName().equals(goal.getName())) {
				//createShortestPath(u);//path to goal found
				foundPath = true;
				return 0;
			}
			
			
			for(Vertex v: u.getNeighbors()) {
			
				if(!inShortestPath(v)) {
					
					//v.setPred(u);
					city.getVertices().get(v.getName()).setG_n(u.getG_n() + cost(u, v));
					shortestPath.push(v);
					nextThreshold = IDA_star_visit(v, visited, currentThreshold, nextThreshold);
					if(foundPath) return 0;
					
					shortestPath.pop();
				}
			
			}
		return nextThreshold;
		
		}
	
	//Calculates the minimum cost needed to go from vertex u to vertex v
	private float cost(Vertex u, Vertex v) {
		
		float cost = Float.MAX_VALUE;// cost to get from u to v
		float minCost = Float.MAX_VALUE;
		
		
		for(Edge e: u.getExitingEdges()) {
			if(e.getVert2().getName().equals(v.getName()) || e.getVert1().getName().equals(v.getName())) {
				
				if(e.getTrafficWeightPredictionLabel() == Traffic.LOW)
					cost = (distribution[0]*0.9f + distribution[1]*1f + distribution[2]*1.25f)*e.getWeight();
				else if(e.getTrafficWeightPredictionLabel() == Traffic.NORMAL)
					cost = (distribution[0]*1f + distribution[1]*0.9f + distribution[2]*1.25f)*e.getWeight();
				else
					cost = (distribution[0]*1.25f + distribution[1]*0.9f + distribution[2]*1f)*e.getWeight();
				
				if(cost < minCost)
					minCost = cost;
			}
		}
		
		return minCost;
		
	}
	
	private float simple_cost(Vertex u, Vertex v) {
		float cost = Float.MAX_VALUE;// cost to get from u to v
		float minCost = Float.MAX_VALUE;
		
		
		for(Edge e: u.getExitingEdges()) {
			if(e.getVert2().getName().equals(v.getName()) || e.getVert1().getName().equals(v.getName())) {
				
				cost = e.getPredicted_weight();
				
				if(cost < minCost)
					minCost = cost;
			}
		}
		
		return minCost;
	}
	
	//Check whether or not vertex v is part of the shortest path
	private boolean inShortestPath(Vertex v) {
		for(Vertex u: shortestPath) {
			if(u.getName().equals(v.getName()))
				return true;
		}
		
		return false;
	}
	
	
	
	
/*	private void createShortestPath(Vertex v) {
		Vertex v_pred;
		this.shortestPath.add(v);// add the goal to the path
		
		//Backtrack through all the nodes in the path until you find the source
		while(!v.getName().equals(this.source.getName())) {
			v_pred = v.getPred();
			this.shortestPath.add(v_pred);
			v = v_pred;
		}
	}*/
	
	public void calculateHeuristics() {
		
		Vertex src = null;
		float distance_from_goal = Float.MAX_VALUE;
		
		for (String key: city.getVertices().keySet()) {
			
			resetState();
			src = city.getVertices().get(key);
			distance_from_goal = 0.75f*UCS(src);
			System.out.println("Distance from goal of node "+src.getName()+" is "+distance_from_goal);
			city.getVertices().get(key).setH_n(distance_from_goal);
			
		}
		
	}
	
	
	//======================================= UNIFORM COST SEARCH ===========================================================
	


	public float UCS(Vertex src) {
		
		Vertex node = null;
		Vertex node_pred = null;
		int node_index;
		float shortestPathCost = Float.MAX_VALUE;
		
		LinkedList<Vertex> frontier = new LinkedList<Vertex>();

		Hashtable<String,Boolean> visited = new Hashtable<String,Boolean>();
		
		
		for (String key: city.getVertices().keySet()) {
			
			//g_n.put(key, Float.MAX_VALUE); //set all distances from source to infinity
			visited.put(key, false); //all vertices are initially not visited
			
				
		}
		
		frontier.add(src);
		src.setG_n(0f);
		
		
		while(!foundPath) {
			
			if( frontier.isEmpty()) {
				 foundPath = false;
				 break;
			}
			
			node_index = getMin(frontier);// chooses the lowest-cost vertex in the frontier
			node = frontier.remove(node_index);
			expand(node);//expand
			this.visitedNodeNumber++;
			
			
									
			
			//checking if node is goal 
			if(node.getName().equals(this.goal.getName())) {
				
				visited.get(node.getName());
				shortestPathCost = node.getG_n();
				
				//finding solution
				
				while(node.getPred() != null) {
					
					this.shortestPath.add(node);
					node_pred = node.getPred();
					node = node_pred;
					
				}
				this.shortestPath.add(node);
				
				foundPath = true;
				
			}
			
			visited.put(node.getName(), true);
			
			for (Vertex v: node.getNeighbors()) {
				boolean inFrontier = false;
				
				//if v not in visited then add it in frontier
				
				if (!visited.get(v.getName())) {
					frontier.add(v);
				}
													
				
				
			}
							
			
		}
		
		
		return shortestPathCost;
		
	}
	
	
	private int getMin(LinkedList<Vertex> frontier) {
		
		float min_value = frontier.get(0).getG_n();
		Vertex v_min_value = frontier.get(0);
		int v_index = 0;
		
		for (int i = 1; i <frontier.size();i++) {
			
			if(min_value > frontier.get(i).getG_n()) {
				v_min_value = frontier.get(i);
				v_index = i;
			}
			
		}
		return v_index;
		
	}
	
	
	
	private Vertex getMax(LinkedList<Vertex> frontier) {
		
		float max_value = Float.MIN_VALUE;
		Vertex v_max_value = null;
		
		for (int i = 0; i <frontier.size();i++) {
			
			if(max_value < frontier.get(i).getG_n()) {
				v_max_value = frontier.get(i);
			}
			
		}
		return v_max_value;
		
	}
		
	//Option 0 is for predicted weight
   //Option 1 is for actual weight
	private void expand (Vertex v) {
		
		
		
		for (Vertex u: v.getNeighbors()) {
			
			if(u.getG_n() > v.getG_n() + cost(v,u)) {
				u.setG_n(v.getG_n() + cost(v,u));
				u.setPred(v);
			}

		}
		
		
	}
	
	public String printPath(){
		
		String path = "";
		Vertex src = null;
		Vertex dst = null;
		String arrow = "->";
		float cost = 0;
		float min_cost = Float.MAX_VALUE;
		
		for(int i = shortestPath.size() -1 ;i > 0;i--) {
			
			src = shortestPath.get(i);
			
			cost = 0;
			min_cost = Float.MAX_VALUE;
			for(Edge e: src.getExitingEdges()) {
				
				dst = shortestPath.get(i - 1);
				 
				cost = simple_cost(src,dst);
				if((e.getVert2().getName().equals(dst.getName()) || e.getVert1().getName().equals(dst.getName())) && cost == e.getPredicted_weight()) {
					
					if(min_cost > cost ) {
						if(path.equals(""))
							path = path+""  +e.getName();
						else
							path = path+"" +arrow +e.getName();
					min_cost= cost;
					}
				}
					
			}
			
			
		}
		
		
		
		
		return path;
	}
	
	public float calculateActualCost() {
		
		float actualCost = 0;

		Vertex src = null;
		Vertex dst = null;
		float cost = 0;
		float min_cost = Float.MAX_VALUE;
		
		for(int i = shortestPath.size() -1 ;i > 0;i--) {
			
			src = shortestPath.get(i);
			
			cost = 0;
			min_cost = Float.MAX_VALUE;
			for(Edge e: src.getExitingEdges()) {
				
				dst = shortestPath.get(i - 1);
				 
				cost = e.getActual_weight();
				if((e.getVert2().getName().equals(dst.getName()) || e.getVert1().getName().equals(dst.getName())) ) {
					
					if(min_cost > cost) {
					min_cost= cost;
					}
				}
					
			}
			
			actualCost += min_cost;
		}
		
		
		
		return actualCost;
	}
	
	
	//================================ LRTA* ==============================================================
	
	//Vertex v is for state s
		//Vertex u is state s'
		public float LRTA_star () {
			float cost = 0;
			this.foundPath = false;
			Vertex v = null;
			float totalCost = 0f;
			Hashtable<String, Float > H = new Hashtable<String, Float>();
			Hashtable<Result, Vertex > result = new Hashtable<Result, Vertex>();
			
			
			Result result_s_a = new Result(source, null);
			result.put(result_s_a, source);
			
			while(!foundPath) {
				
				v = result.get(result_s_a);
				
				if(v.getName().equals(goal.getName())) {
					foundPath = true;
					return totalCost;
				}
						
				
				if(H.get(v.getName()) == null) {
					H.put(v.getName(), v.getH_n());
				}
				
				if(!v.getName().equals(source.getName())) {
					result.put(result_s_a, v);
					cost = H.get(result_s_a.getState().getName()) + result_s_a.getAction().getActual_weight();
					H.put(v.getName(), cost);
					totalCost += result_s_a.getAction().getActual_weight();
				}
				
				result_s_a = LRTAStarCost(v, H);
				
				if(result_s_a.getAction().getVert1().getName().equals(v.getName()))
					result.put(result_s_a, result_s_a.getAction().getVert2());
				else
					result.put(result_s_a, result_s_a.getAction().getVert1());
			}
			
			return -1f; //PATH NOT FOUND
		}
			
		private Result LRTAStarCost(Vertex current_state,Hashtable<String, Float> H) {
			
			this.visitedNodeNumber++;
			float cost = Float.MAX_VALUE;// cost to get from u to v
			float minCost = Float.MAX_VALUE;
			Result next_state = new Result(current_state, null);
			
			for(Edge e: current_state.getExitingEdges()) {
				if(e.getVert1().getName().equals(current_state.getName())) {
					if(H.get(e.getVert2().getName()) == null)
						cost = current_state.getH_n();
					else
						cost = e.getActual_weight() + H.get(e.getVert2().getName());
				}

				else {
					if(H.get(e.getVert1().getName()) == null)
						cost = current_state.getH_n();
					else
						cost = e.getActual_weight() + H.get(e.getVert1().getName());	
				}
					
				if(cost < minCost) {
					minCost = cost;
					next_state.setAction(e);
				}
					
			}
			return next_state;
		}
			
		
		
		
		
	
}



