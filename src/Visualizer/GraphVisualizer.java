package Visualizer;
import graph.*;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GraphVisualizer extends JPanel implements Runnable{
	
	private static final long serialVersionUID = 1L;
	private static int WIDTH = 700, HEIGHT = 700;
	
	private boolean running = false;
	private Thread thread = new Thread();
	
	private Graph graph;
	
	public GraphVisualizer() {
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(null);
		setBackground(Color.BLACK);
		
		graph = new Graph();
		
		this.start();
		
	}
	
	
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				
				g.setColor(Color.WHITE);
				paintVertex(50 + 70*i, 50 + 70*j,
							graph.getVertices().get(i + j*4), (Graphics2D)g);
				
				if(i != 3)
					g.drawLine(70 + 70*i, 60 + 70*j, 120 + 70*i, 60 + 70*j);
				
				if(j != 3)
					g.drawLine(60 + 70*i, 70 + 70*j, 60 + 70*i, 120 + 70*j);
				
			}
		}	
		
	}
	
	
	public synchronized void paintEdge(int xpos1, int ypos1, int xpos2, int ypos2, Edge e, Graphics2D g) {
		g.setColor(Color.WHITE);
	    g.drawLine(xpos1, ypos1, xpos2, ypos2);
	}
	
	public synchronized void paintVertex(int xpos, int ypos, Vertex v, Graphics2D g) {
		g.setColor(v.color);
	    g.drawRect(xpos, ypos, (int)v.getV_width(), (int)v.getV_height());
	    g.fillRect(xpos, ypos, (int)v.getV_width(), (int)v.getV_height());
	    
	    g.setFont(new Font("Comic Sans", Font.BOLD, 11));
	    g.setColor(Color.RED);
	    g.drawString(v.getName(), xpos, ypos+10);
	    
	    g.setColor(Color.WHITE);// refer back to the default color
	    
	    //This block of code is used for
	    //the edge to listen to mouse actions
	    JLabel label = new JLabel();
		label.setBounds(xpos,ypos,(int)v.getV_width(),(int)v.getV_height());
		label.setOpaque(false);
		label.addMouseListener(v);
		
		this.add(label);
		
	    
	}
	
	public void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		long ticks = 0;
		while(running) {
			ticks++;
			if(ticks >= 40000) {
				this.repaint();
				ticks = 0;
			}
			
		}
		
	}



}
