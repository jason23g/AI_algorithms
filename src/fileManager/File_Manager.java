package fileManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import graph.Edge;
import graph.Vertex;
import simulation.Day;

public class File_Manager {
	

private FileReader fileReader;
private FileWriter fileWriter;
private BufferedReader bufferedReader;
private BufferedWriter bufferedWriter;
private String vertex_src;
private String vertex_goal;
private Hashtable<Integer,Day> days;


	
	
public File_Manager() {

	days = new Hashtable<Integer,Day>();
	
}


public Vector<String> read_from_file(File file) {
	
	Vector<String> lines = new Vector<>();
	
	try {
		fileReader = new FileReader(file);
		bufferedReader = new BufferedReader(fileReader);
		String line = bufferedReader.readLine();
		
		
		
		while (line != null) {
			
			lines.add(line);
			line = bufferedReader.readLine();
			
		}
		
		bufferedReader.close();
		
	} catch (FileNotFoundException e) {
		System.out.println("File not found");
	}
	catch (IOException e) {
		
		e.printStackTrace();
	}
	
	
	return lines;
}


public void write_from_file(Vector<String> lines, File file) {
	

	
	try {
		fileWriter = new FileWriter(file);
		bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write(lines.get(0));
		
		
		
		for (int i = 1; i< lines.size();i++){
			
			bufferedWriter.newLine();
			bufferedWriter.write(lines.get(i));
			
			
			
		}
		
		bufferedWriter.close();
		
	}
	catch (FileNotFoundException e) {
		System.out.println("File not found");
	}
	catch (IOException e) {
		
		e.printStackTrace();
	}
	
	
	
}


public Vector<String> marshall() {
	
	Vector<String> lines = new Vector<String>();
	String line ="";
	
	
		for(int i = 0; i< this.days.size();i++) {
		
		line = "Day "+(i)+"\n";
		
		lines.add(line);
		line = "UCS: ";
		lines.add(line);
		line = "\tVisited Nodes number:" + this.days.get(i).getVisitedNodesNumberUCS();
		lines.add(line);
		line = "\tPath:" + this.days.get(i).getPathUCS();
		lines.add(line);
		line = "\tPredicted Cost:" + this.days.get(i).getPredictedCostUCS();
		lines.add(line);
		line = "\tReal Cost:" + this.days.get(i).getRealCostUCS();
		lines.add(line);
		
		line = "\n";
		lines.add(line);
		
		line = "IDA*: " ;
		lines.add(line);
		line = "\tVisited Nodes number:" + this.days.get(i).getVisitedNodesNumberIDAStar();
		lines.add(line);
		line = "\tPath:" + this.days.get(i).getPathIDAStar();
		lines.add(line);
		line = "\tPredicted Cost:" + this.days.get(i).getPredictedCostIDAStar();
		lines.add(line);
		line = "\tReal Cost:" + this.days.get(i).getRealCostIDAStar();
		lines.add(line);
		
		
		
		
		}
	
	
	return lines;
	
}


public void unmarshall(Vector<String> lines) {
	
	String line;
	String origin_vertex_name;
	String goal_vertex_name;
	String road_name = "";
	String vertex_source = "";
	String vertex_dst = "";
	String trafficWeightPredictionLabel = "";
	String trafficWeightLabelActual = "";
	int dayNumber = 0;
	
	Hashtable<String, Edge> edges = new Hashtable<String,Edge>();
	Hashtable<String, Vertex> vertices = new Hashtable<String,Vertex>();
	Day day;
	
	float normal_weight_value = 0; 
	
	
	
	for (int i = 0; i < lines.size()-1; i++) {
		
		line = lines.get(i);
		
		if(line.contains("<Source>")) {
			
			line  = line.replace("<Source>", "");
			origin_vertex_name = line.replace("</Source>", "").trim();
			this.vertex_src = origin_vertex_name;
			Vertex source = new Vertex(this.vertex_src);
			vertices.put(source.getName(), source);
			//System.out.println(origin_vertex_name);
		}
		else if(line.contains("<Destination>")) {
			
			line  = line.replace("<Destination>", "");
			goal_vertex_name = line.replace("</Destination>", "").trim();
			this.vertex_goal = goal_vertex_name;
			Vertex goal = new Vertex(vertex_goal);
			vertices.put(goal.getName(), goal);
			//System.out.println(goal_vertex_name);
			
		}
		else if (line.equals("<Roads>")) {
			i++;
			
			line = lines.get(i);
			
			while(!line.equals("</Roads>")) {
				i++;
				
				Edge road;
				Vertex src = null;
				Vertex goal  = null;
				
				//This the code for the edges 
				road_name 	= line.replaceAll("\\s","").substring(0,line.indexOf(";"));
				//System.out.println(road_name);
				line = (line.replace(road_name+";","")).replaceAll("\\s","");
				vertex_source = line.substring(0,line.indexOf(";"));
				//System.out.println(vertex_src);
				line = (line.replace(vertex_source+";","")).replaceAll("\\s","");
				vertex_dst 	= line.substring(0,line.indexOf(";"));
				//System.out.println(vertex_dst);
				line = (line.replace(vertex_dst+";","")).replaceAll("\\s","");
				
				normal_weight_value	= Float.parseFloat(line);
				//System.out.println(normal_weight_value);
				
				line = lines.get(i);
				
				/*if(vertex_dst.equals(this.vertex_goal) || vertex_source.equals(this.vertex_goal)) {
					
					goal = vertices.get(this.vertex_goal); 
					
					
					
					}
				else */if(vertices.get(vertex_dst)!= null) {
					
					goal = vertices.get(vertex_dst);
				}
				else {
					
					goal = new Vertex(vertex_dst);
					
				}
				
				
				/*if(vertex_dst.equals(this.vertex_src) || vertex_source.equals(this.vertex_src)) {
					
					src = vertices.get(this.vertex_src); 
				
				
				}
				else */if(vertices.get(vertex_source)!= null) {
					
					src = vertices.get(vertex_source);
					
					
				}
				else {
					
					src = new Vertex(vertex_source);
					
					
				}
				
				
				if (src.getNeighborByName(goal.getName()) == null && !(src.getName().equals(goal.getName())))
				src.addNeighbor(goal);
				
				if (goal.getNeighborByName(src.getName()) == null && !(src.getName().equals(goal.getName())))
				goal.addNeighbor(src);
				
				vertices.put(goal.getName(), goal);
				vertices.put(src.getName(), src);
				
				road = new Edge(road_name,src,goal,normal_weight_value);
				src.addExitingEdge(road);
				goal.addExitingEdge(road);
				
				vertices.put(goal.getName(), goal);
				vertices.put(src.getName(), src);
				
				/*src.addIncomingEdge(road);
				goal.addIncomingEdge(road);*/
				edges.put(road.getName(), road);
				
			}
			
			
		//System.out.println("it works fine");
			
			
		}
		
		else if (line.equals("<Predictions>")) {
			
			i++;
			

			line = lines.get(i);
			
			
			while(!line.equals("</Predictions>")) {
				
				
				
			if( line.equals("<Day>")) {
			
				i++;
				//System.out.println("Prediction:"+line);
				line = lines.get(i);
				
				day = new Day();
				
				Hashtable<String, Edge> edges_temp = new Hashtable<String,Edge>();
				edges_temp = (Hashtable<String, Edge>)edges.clone();
			
				
				
			while(!line.equals("</Day>")) {
				i++;
				Edge e_temp = null;
				//System.out.println(line);
				road_name 	= line.replaceAll("\\s","").substring(0,line.indexOf(";"));
				line = (line.replace(road_name+";","")).replaceAll("\\s","");
				trafficWeightPredictionLabel = line;
				try {
					e_temp = (Edge) edges.get(road_name).clone();
					e_temp.setTrafficWeightPredictionLabel(trafficWeightPredictionLabel);
					edges_temp.put(e_temp.getName(), e_temp);
				} catch (CloneNotSupportedException e) {
					
					e.printStackTrace();
				}
				
				//edges.get(road_name).calculateWeightBasedOnTraffic();
				
				line = lines.get(i);
				

				
			}
			
			day.getCity_instance().setEdges(edges_temp);
			day.getCity_instance().setVertices(vertices);
			day.updateEdges();
			
			
			days.put(dayNumber, day);
			
			dayNumber++;
			
			
			
			i++;
			line = lines.get(i);
			//System.out.println("Think mcfly think");
			
			}
			
			//System.out.println(line);
			//System.out.println("Think jason think");
			}
			
			//System.out.println("It works fine");
			
		}
		
		else if (line.equals("<ActualTrafficPerDay>")) {
			
			i++;
			
			line = lines.get(i);
			
			dayNumber = 0;
			while(!line.equals("</ActualTrafficPerDay>")) {
				
			
			if( line.equals("<Day>")) {
			
				i++;
				//System.out.println("Actual:"+line);
				//System.out.println("It works finally");
				line = lines.get(i);
				
			
				day = days.get(dayNumber);
				
			while(!line.equals("</Day>")) {
				i++;
				Edge e;
				
				//System.out.println(line);
				road_name 	= line.replaceAll("\\s","").substring(0,line.indexOf(";"));
				line = (line.replace(road_name+";","")).replaceAll("\\s","");
				trafficWeightLabelActual = line;
				
				day.getCity_instance().getEdges().get(road_name).setTrafficWeightActualLabel(trafficWeightLabelActual);
				//day.getCity_instance().getEdges().get(road_name).calculateActualWeightBasedOnTraffic();
				
				line = lines.get(i);
				
			
			}
			
			day.getCity_instance().setEdges(day.getCity_instance().getEdges());
			day.getCity_instance().setVertices(day.getCity_instance().getVertices());
			day.updateEdges();
			
			
			days.put(dayNumber, day);
			
			dayNumber++;
			
			i++;
			line = lines.get(i);
			
			}
			
			
			}
			
			
			
		}
		
		else{
			
		}
		
	}
	
}


public Hashtable<Integer, Day> getDays() {
	return days;
}


public void setDays(Hashtable<Integer, Day> days) {
	this.days = days;
}


public String getVertex_src() {
	return vertex_src;
}


public String getVertex_goal() {
	return vertex_goal;
}




}
