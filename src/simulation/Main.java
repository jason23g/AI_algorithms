package simulation;

import fileManager.File_Manager;
import graph.Graph;
import graph.Vertex;
import simulation.Agent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {

	public static void main(String[] args) {

		/*
		 * JFrame frame = new JFrame(); GraphVisualizer gv = new GraphVisualizer();
		 * 
		 * frame.setSize(700, 700);
		 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 * frame.setTitle("Graph Visualization"); frame.add(gv);
		 * 
		 * 
		 * 
		 * frame.setResizable(false);
		 * 
		 * frame.setLocationRelativeTo(null); frame.setVisible(true);
		 */
		
		File outputFile = new File("output.txt");
		File inputFile = new File("sampleGraph1.txt");
		File_Manager fileManager = new File_Manager();
		Vector<String> input = fileManager.read_from_file(inputFile);
		fileManager.unmarshall(input);

		String source_name = fileManager.getVertex_src();
		String goal_name = fileManager.getVertex_goal();
		Vertex src = fileManager.getDays().get(0).getCity_instance().getVertices().get(source_name);
		Vertex goal = fileManager.getDays().get(0).getCity_instance().getVertices().get(goal_name);

		Agent agent = null; 

		int visitedNodesNumber = 0;
		float predictedCost = 0;
		float realCost = 0;
		String Path = "";

		Graph city = fileManager.getDays().get(0).getCity_instance();
		
		float mean_real_cost = 0f;
		float mean_predicted_cost = 0f;
		float UCS_real_costs = 0f;
		float UCS_predicted_costs = 0f;
		float IDAStar_real_costs = 0f;
		float IDAStar_predicted_costs = 0f;
		long UCS_executionTime = 0;
		long IDAStar_executionTime = 0;
		float LRTAStar_real_costs = 0f;
		float LRTAStar_predicted_costs = 0f;
		long LRTAStar_executionTime = 0;
		long meanExecutionTime;

		int numOfDays = 80;
		
		for (String s : city.getEdges().keySet()) {
		 city.getEdges().get(s).setTrafficWeightPredictionLabel("low");
		 
		}
		 
		 

		agent = new Agent(city, src, goal);
		//chrysa.calculate_edges();
		agent.calculateHeuristics();
		agent.resetState();
		for (int i = 0; i < numOfDays; i++) {
			
			predictedCost = 0;
			agent.resetState();
			fileManager.getDays().get(i).updateEdges();
			agent.setCity(fileManager.getDays().get(i).getCity_instance());// load city instance for that day
			
			
			 //UCS 
			  
			  long startTime = System.nanoTime(); 
			  predictedCost = agent.UCS(src); 
			  long endTime = System.nanoTime();
			  
			  long executionTime = endTime - startTime; 
			  Path = agent.printPath(); 
			  realCost = agent.calculateActualCost(); 
			  visitedNodesNumber = agent.getVisitedNodeNumber();
			  
			  UCS_real_costs += realCost;
			  UCS_predicted_costs += predictedCost;
			  UCS_executionTime += executionTime;
			  
			  fileManager.getDays().get(i).setVisitedNodesNumberUCS(visitedNodesNumber);
			  fileManager.getDays().get(i).setExecutionTimeUCS(executionTime);
			  fileManager.getDays().get(i).setPathUCS(Path);
			  fileManager.getDays().get(i).setPredictedCostUCS(predictedCost);
			  fileManager.getDays().get(i).setExecutionTimeUCS(executionTime);
			  fileManager.getDays().get(i).setRealCostUCS(realCost);
			  
			  System.out.println("Day "+i);
			  System.out.println("UCS");
			  System.out.println("\tVisited Nodes Number :" + visitedNodesNumber);
			  System.out.println("\tExecution Time:"+executionTime);
			  System.out.println("\tPath "+Path);
			  System.out.println("\tPredicted Cost :"+predictedCost);
			  System.out.println("\tReal Cost :"+realCost); 
			 
				 
			  
			 // IDA*
			 
			
			
			agent.resetState();
			startTime = System.nanoTime(); 
			predictedCost = agent.IDA_star();
			endTime = System.nanoTime();
			executionTime = endTime - startTime;
			realCost = agent.calculateActualCost();	
			Path = agent.printPath();
			visitedNodesNumber = agent.getVisitedNodeNumber();
			
			 IDAStar_real_costs += realCost;
			 IDAStar_predicted_costs += predictedCost;
			 IDAStar_executionTime += executionTime;
			
			 fileManager.getDays().get(i).setVisitedNodesNumberIDAStar(visitedNodesNumber);
			 fileManager.getDays().get(i).setExecutionTimeIDAStar(executionTime);
			 fileManager.getDays().get(i).setPathIDAStar(Path);
			 fileManager.getDays().get(i).setPredictedCostIDAStar(predictedCost);
			 fileManager.getDays().get(i).setExecutionTimeIDAStar(executionTime);
			 fileManager.getDays().get(i).setRealCostIDAStar(realCost);
			
			System.out.println("IDA*:");
			System.out.println("\tVisited Nodes Number :"+visitedNodesNumber);
			System.out.println("\tExecution Time:"+executionTime);
			System.out.println("\tPath "+Path);
			System.out.println("\tPredicted Cost :"+predictedCost);
			System.out.println("\tReal Cost :"+realCost); 
			 
			agent.resetState();
			startTime = System.nanoTime(); 
			realCost = agent.LRTA_star();
			endTime = System.nanoTime();
			executionTime = endTime - startTime;
			
			visitedNodesNumber = agent.getVisitedNodeNumber();
			
			LRTAStar_real_costs += realCost;
			LRTAStar_executionTime += executionTime;
			
			System.out.println("LRTA*:");
			System.out.println("\tVisited Nodes Number :"+visitedNodesNumber);
			System.out.println("\tExecution Time:"+executionTime);
			System.out.println("\tReal Cost :"+realCost); 

		}
		
		fileManager.write_from_file(fileManager.marshall(),outputFile);

		mean_predicted_cost = UCS_predicted_costs/numOfDays;
		mean_real_cost = UCS_real_costs/numOfDays;
		meanExecutionTime = UCS_executionTime/numOfDays;
		
		System.out.println("UCS Mean Predicted Cost: "+mean_predicted_cost);
		System.out.println("UCS Mean Real Cost: "+mean_real_cost);
		System.out.println("UCS Mean ExecutionTime: "+meanExecutionTime);
		
		mean_predicted_cost = IDAStar_predicted_costs/numOfDays;
		mean_real_cost = IDAStar_real_costs/numOfDays;
		meanExecutionTime = IDAStar_executionTime/numOfDays;
		
		System.out.println("IDA* Mean Predicted Cost: "+mean_predicted_cost);
		System.out.println("IDA* Mean Real Cost: "+mean_real_cost);
		System.out.println("IDA* Mean ExecutionTime: "+meanExecutionTime);
		

		mean_real_cost = LRTAStar_real_costs/numOfDays;
		meanExecutionTime = LRTAStar_executionTime/numOfDays;
		
		System.out.println("LRTA* Mean Real Cost: "+mean_real_cost);
		System.out.println("LRTA* Mean ExecutionTime: "+meanExecutionTime);
		
		
	}

}