package graph;
import java.util.Vector;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Vertex extends Rectangle implements MouseListener,Comparable<Vertex>{
	
	//int MAX_CHAR = 50;
	private int v_width, v_height;
	private String name;
	public Color color;
	private Vector<Vertex> neighbors = null;
	//private Vector<Edge> incomingEdges = null;
	private Vector<Edge> exitingEdges = null;
	
	private Vertex pred;
	private float g_n;
	private float h_n;// estimated distance from this vertex to the goal
	private float f_n;// g(n) + h(n)
	
	public Vertex(String name) {
		this.v_width = 20;
		this.v_height = 20;
		
		color = Color.WHITE; 
	
		this.name = name;
		this.neighbors = new Vector<Vertex>();
		//this.incomingEdges = new Vector<Edge>();
		this.exitingEdges = new Vector<Edge>();
		this.g_n = java.lang.Float.MAX_VALUE; //to avoid collision between the primitive and java.awt.geom.Rectangle2D.Float
		this.h_n = java.lang.Float.MAX_VALUE;
		this.f_n = java.lang.Float.MAX_VALUE;
	}
	
	public Vector<Vertex> getNeighbors(){
		return this.neighbors;
	}
	
	public void addNeighbor(Vertex v) {
		this.neighbors.add(v);
	}
	
	public void removeNeighbor(String neighborName) {
		this.neighbors.remove(getNeighborByName(neighborName));
	}
	
	/*public void addIncomingEdge(Edge e) {
		this.incomingEdges.add(e);
	}
	
	public void removeIncomingEdges(String edgeName) {
		this.incomingEdges.remove(getIncomingEdgeByName(edgeName));
	}
	
	public Edge getIncomingEdgeByName(String edgeName) {
		for(Edge e: incomingEdges) {
			if(e.getName().equals(edgeName))
				return e;
		}
		
		return null;//no vertex with that name
	}*/
	
	public void addExitingEdge(Edge e) {
		this.exitingEdges.add(e);
	}
	
	public void removeExitingEdge(String edgeName) {
		this.exitingEdges.remove(getExitingEdge(edgeName));
	}
	
	public Edge getExitingEdge(String edgeName) {
		for(Edge e: exitingEdges) {
			if(e.getName().equals(edgeName))
				return e;
		}
		
		return null;//no vertex with that name
	}
	
	
	
	public Vertex getNeighborByName(String neighborName) {
		for(Vertex v: neighbors) {
			if(v.name.equals(neighborName))
				return v;
		}
		
		return null;//no vertex with that name
	}
	

	public float getG_n() {
		return g_n;
	}

	public void setG_n(float g_n) {
		this.g_n = g_n;
	}

	public float getH_n() {
		return h_n;
	}

	public void setH_n(float h_n) {
		this.h_n = h_n;
	}

	public float getF_n() {
		return f_n;
	}

	public void setF_n(float f_n) {
		this.f_n = f_n;
	}

	public int getV_width() {
		return v_width;
	}

	public void setV_width(int v_width) {
		this.v_width = v_width;
	}

	public int getV_height() {
		return v_height;
	}

	public void setV_height(int v_height) {
		this.v_height = v_height;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNeighbors(Vector<Vertex> neighbors) {
		this.neighbors = neighbors;
	}

	
	/*public Vector<Edge> getIncomingEdges() {
		return incomingEdges;
	}

	public void setIncomingEdges(Vector<Edge> incomingEdges) {
		this.incomingEdges = incomingEdges;
	}
*/
	public Vector<Edge> getExitingEdges() {
		return exitingEdges;
	}

	public void setExitingEdges(Vector<Edge> exitingEdges) {
		this.exitingEdges = exitingEdges;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("MOUSE CLICK");
		this.color = Color.GREEN;
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		System.out.println("MOUSE ENTERED");
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public Vertex getPred() {
		return pred;
	}

	public void setPred(Vertex pred) {
		this.pred = pred;
	}


	public int compareTo(Vertex v) {
		
		if (this.g_n < v.getG_n())
			return 1;
		else if(this.g_n == v.getG_n())
			return 0;
		else
			return -1;
		
	
	}

	
	

}
