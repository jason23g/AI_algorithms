package simulation;

import java.util.LinkedList;
import java.util.Vector;

import graph.*;

public class Day {
	
		private Graph city_instance;
		private int visitedNodesNumberUCS;
		private float executionTimeUCS;
		private String pathUCS;
		private float predictedCostUCS;
		private float realCostUCS;
		private int visitedNodesNumberIDAStar;
		private float executionTimeIDAStar;
		private String pathIDAStar;
		private float predictedCostIDAStar;
		private float realCostIDAStar;
		//private String trafficWeightPredictionLabel;
		
		
		public Day() {
			
			city_instance = new Graph();
			visitedNodesNumberUCS = 0;
			executionTimeUCS = 0;
			predictedCostUCS = 0;
			realCostUCS = 0;
			//this.trafficWeightPredictionLabel = trafficWeightPredictionLabel;
			
		}

		//Setters and getters 
		

		public Graph getCity_instance() {
			return city_instance;
		}

		public int getVisitedNodesNumberUCS() {
			return visitedNodesNumberUCS;
		}

		public void setVisitedNodesNumberUCS(int visitedNodesNumberUCS) {
			this.visitedNodesNumberUCS = visitedNodesNumberUCS;
		}

		public float getExecutionTimeUCS() {
			return executionTimeUCS;
		}

		public void setExecutionTimeUCS(float executionTimeUCS) {
			this.executionTimeUCS = executionTimeUCS;
		}

		public String getPathUCS() {
			return pathUCS;
		}

		public void setPathUCS(String pathUCS) {
			this.pathUCS = pathUCS;
		}

		public float getPredictedCostUCS() {
			return predictedCostUCS;
		}

		public void setPredictedCostUCS(float predictedCostUCS) {
			this.predictedCostUCS = predictedCostUCS;
		}

		public float getRealCostUCS() {
			return realCostUCS;
		}

		public void setRealCostUCS(float realCostUCS) {
			this.realCostUCS = realCostUCS;
		}

		public int getVisitedNodesNumberIDAStar() {
			return visitedNodesNumberIDAStar;
		}

		public void setVisitedNodesNumberIDAStar(int visitedNodesNumberIDAStar) {
			this.visitedNodesNumberIDAStar = visitedNodesNumberIDAStar;
		}

		public float getExecutionTimeIDAStar() {
			return executionTimeIDAStar;
		}

		public void setExecutionTimeIDAStar(float executionTimeIDAStar) {
			this.executionTimeIDAStar = executionTimeIDAStar;
		}

		public String getPathIDAStar() {
			return pathIDAStar;
		}

		public void setPathIDAStar(String pathIDAStar) {
			this.pathIDAStar = pathIDAStar;
		}

		public float getPredictedCostIDAStar() {
			return predictedCostIDAStar;
		}

		public void setPredictedCostIDAStar(float predictedCostIDAStar) {
			this.predictedCostIDAStar = predictedCostIDAStar;
		}

		public float getRealCostIDAStar() {
			return realCostIDAStar;
		}

		public void setRealCostIDAStar(float realCostIDAStar) {
			this.realCostIDAStar = realCostIDAStar;
		}

		public void setCity_instance(Graph city_instance) {
			this.city_instance = city_instance;
		}
		
		//This function is used for updating the exiting edges of each vertex
		//after taking into consideration the traffic that day
		public void updateEdges() {
			
			for(String name: this.city_instance.getVertices().keySet()) {
				for(Edge e: city_instance.getVertices().get(name).getExitingEdges()) {
					e.setPredicted_weight(city_instance.getEdges().get(e.getName()).getPredicted_weight());
					e.setActual_weight(city_instance.getEdges().get(e.getName()).getActual_weight());
				}
			}
		}

		
		
		
		
		

}
