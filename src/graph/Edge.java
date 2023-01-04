package graph;


public class Edge implements Cloneable{
	
	private boolean directional = false;
	private float predicted_weight;
	private float weight;
	private float actual_weight;
	private int x1, y1;//start of line
	private int x2, y2;//end of line
	private String name = "";
	private Vertex vert1; //if directional, its the source vertex
	private Vertex vert2; //if directional, its the destination vertex
	
	private Traffic trafficWeightPredictionLabel = Traffic.NORMAL;
	private Traffic trafficWeightActualLabel = Traffic.NORMAL;
	
	public Edge(String name, Vertex src, Vertex dst, float normal_weight) {
		this.name = name;
		this.vert1 = src;
		this.vert2 = dst;
		this.weight = normal_weight;
		//this.traffic = traffic;
		
		
		/*if(trafficWeightPredictionLabel == Traffic.HEAVY)
			this.weight = normal_weight*1.25f;
		else if(traffic == Traffic.LOW)
			this.weight = normal_weight*0.9f;
		else
			this.weight = normal_weight;*/		
			
	}

	
	//GETTERS & SETTERS
	//--------------------------------------------------------------------------------------------------------------------
	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vertex getVert1() {
		return vert1;
	}

	public void setVert1(Vertex vert1) {
		this.vert1 = vert1;
	}

	public Vertex getVert2() {
		return vert2;
	}

	public void setVert2(Vertex vert2) {
		this.vert2 = vert2;
	}
	
	
	
	public float getPredicted_weight() {
		return predicted_weight;
	}


	public void setPredicted_weight(float predicted_weight) {
		this.predicted_weight = predicted_weight;
	}


	public Traffic getTrafficWeightPredictionLabel() {
		return trafficWeightPredictionLabel;
	}


	public void setTrafficWeightPredictionLabel(String trafficWeightPredictionLabel) {
		
		if(trafficWeightPredictionLabel.equals("heavy")) {

			this.trafficWeightPredictionLabel = Traffic.HEAVY;
		
		}
		else if(trafficWeightPredictionLabel.equals("low"))
		{
			this.trafficWeightPredictionLabel = Traffic.LOW;
		
		}
		else
		{
			this.trafficWeightPredictionLabel = Traffic.NORMAL;
		}
		
		
		calculateWeightBasedOnTraffic();
		
	}


	public Traffic getTrafficWeightActualLabel() {
		return trafficWeightActualLabel;
	}


public void setTrafficWeightActualLabel(String trafficWeightActualLabel) {
		
		
		if(trafficWeightActualLabel.equals("heavy")) {

			this.trafficWeightActualLabel = Traffic.HEAVY;
		
		}
		else if(trafficWeightActualLabel.equals("low"))
		{
			this.trafficWeightActualLabel = Traffic.LOW;
		
		}
		else {
			this.trafficWeightActualLabel = Traffic.NORMAL;	
		}
		
		calculateActualWeightBasedOnTraffic();
		
	}


public void calculateActualWeightBasedOnTraffic() {
		
		if(this.trafficWeightActualLabel == Traffic.HEAVY) {

			this.actual_weight = this.weight*1.25f;
			
		
		}
		else if(this.trafficWeightActualLabel == Traffic.LOW)
		{
			this.actual_weight = this.weight*0.9f;
			
		
		}
		else
			this.actual_weight = this.weight;		
	}
	
private void calculateWeightBasedOnTraffic() {
	
		
		
		if(this.trafficWeightPredictionLabel == Traffic.HEAVY) {
			
			this.predicted_weight = this.weight*1.25f;
			
		
		}
		else if(this.trafficWeightPredictionLabel == Traffic.LOW)
		{
			this.predicted_weight = this.weight*0.9f;
			
		
		}
		else {
			this.predicted_weight = this.weight;	
		}
		
	
	}


	public float getActual_weight() {
		return actual_weight;
	}


	public void setActual_weight(float actual_weight) {
		this.actual_weight = actual_weight;
	}
	
	public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }

	
	

}
