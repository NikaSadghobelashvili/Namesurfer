/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() 
	{
		addComponentListener(this);
		//	 You fill in the rest //
	}
	
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() 
	{
		entries.clear();
	}
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) 
	{
		 if(!entries.contains(entry))
		 {
			 entries.add(entry);
			 prognoseIsClicked=false;
		 }
	}
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() 
	{
		removeAll();
		
		fontSize = 14*getHeight()/APPLICATION_HEIGHT*getWidth()/APPLICATION_WIDTH;
		graphMarginSize = GRAPH_MARGIN_SIZE*getHeight()/APPLICATION_HEIGHT;
	    availableHeight = getHeight() - 2 * graphMarginSize;
	    
		
		addVerticalLines();
		addHorizontalLines();
		addLabels();
		drawGraphs();
		if(prognoseIsClicked)
		{
			drawPrognose();
		}
		
	}
	public void Delete(NameSurferEntry entry)
	{
		if(entries.contains(entry))
		{
			entries.remove(entry);
		}
	}
	
	public boolean prognoseClicked(boolean clicked)
	{
		prognoseIsClicked=clicked;
		return prognoseIsClicked;
	}
	
	private void drawPrognose() 
	{
	    if (!entries.isEmpty()) 
	    {
	        for (int i = 0; i < entries.size(); i++) 
	        {
	            double sumX = 0;
	            double sumY = 0;
	            double sumXY = 0;
	            double sumXX = 0;
	
	            for (int j = NDECADES - 3; j < NDECADES; j++) {
	                int decade = START_DECADE + j * 10;
	                int rank = entries.get(i).getRank(decade);
	                //Used in the formula below of this for loop
	                sumX += decade;
	                sumY += rank;
	                sumXY += decade * rank;
	                sumXX += decade * decade;
	            }
	//https://www.chegg.com/homework-help/questions-and-answers/formula-slope-least-squares-regression-equation-frac-n-sum-x-y-sum-x-sum-y-n-sum-x-2-left--q122215435
	//formula of the slope for least squares regression
	            double slope = (3 * sumXY - sumX * sumY) / (3 * sumXX - sumX * sumX); 
	//sumY=slope*sumX + n*intercept -> where n is 3    
	            double intercept = (sumY - slope * sumX) / 3;
	
	            int rank1990 = entries.get(i).getRank(2000);
	            int rank2000 = (int) (slope * (2000) + intercept);
	
	            drawLastDecade(rank1990, rank2000, entries.get(i), entries.get(i).getColor());
	        }
	    }
	}
	private void drawLastDecade(int rank1990, int rank2000, NameSurferEntry entry,Color color) {
	    int rank1 = rank1990;
	    int rank2 = rank2000;

	    double x1 = (NDECADES - 1) * (double) getWidth() / NDECADES; // x-coordinate of the lines
	    double x2 = (NDECADES) * (double) getWidth() / NDECADES;

	    double y1 = rank1 == 0 ? getHeight() - graphMarginSize : graphMarginSize + availableHeight * rank1 / MAX_RANK;
	    double y2 = rank2 == 0 ? getHeight() - graphMarginSize : graphMarginSize + availableHeight * rank2 / MAX_RANK;
	    
	    if (y2>getHeight()-graphMarginSize) //ensures y2 to be above the margin
	    {
	    	y2=getHeight()-graphMarginSize;
	    }
	    
	    GLine line = new GLine(x1, y1, x2, y2);
	    line.setColor(color);
	    add(line);

	    boolean ascending = rank2 >= rank1;
        addLabel(entry.getName(), rank2, x2, ascending ? y2 - 5 : y2 + 15,color);
	}
	
	private void drawGraphs()
	{
		if(!entries.isEmpty())
		{
			for(int i=0;i<entries.size();i++)
			{
				drawEntry(entries.get(i));
			}
		}
	}
	
	private void drawEntry(NameSurferEntry entry)
	{
		
		for (int i = 0; i < NDECADES - 1; i++) 
		{
			Color color = getEntryColor(entry);
	        int rank1 = entry.getRank(START_DECADE + i * 10);
	        int rank2 = entry.getRank(START_DECADE + (i + 1) * 10);
	        
	        double x1 = i * (double) getWidth() / NDECADES; // x cordinate of the lines should always be on the vertical lines
	        double x2 = (i + 1) * (double) getWidth() / NDECADES;
	        
	        //if the rank is 0 then put it on the bottom edge, if not, take available height and *its rank/MAX (proportions)
	        double y1 = rank1 == 0 ? getHeight() - graphMarginSize : graphMarginSize + availableHeight * rank1 / MAX_RANK;
	        double y2 = rank2 == 0 ? getHeight() - graphMarginSize : graphMarginSize + availableHeight * rank2 / MAX_RANK;

	        GLine line = new GLine(x1, y1, x2, y2);
	        line.setColor(color);
	        add(line);
	        
	        boolean ascending = rank2 >= rank1; //determine whether the graph is ascending or descending
	        
	        addLabel(entry.getName(), rank1, x1, ascending ? y1 - 5 : y1 + 15,color);
	        
	        if(i==NDECADES-2)
	        {
	        	 addLabel(entry.getName(), rank2, x2, ascending ? y2 - 5 : y2 + 15,color);
	        }
	     }
	}
	
	private void addLabel(String name, int rank, double x, double y, Color color) {
	    if(rank!=0)
	    {
	    GLabel label = new GLabel(name + " " + rank);
	    label.setFont(new Font("Arial", Font.BOLD, fontSize));
	    label.setColor(color);
	    add(label, x, y);
	    }
	    else
	    {
	    	GLabel label = new GLabel(name + " " + "*");
		    label.setFont(new Font("Arial", Font.BOLD, 11));
		    label.setColor(color);
		    add(label, x, y);
	    }
	}
	private void addVerticalLines()
	{
		//adding vertical lines
		int numDecades = NDECADES;
        double width = getWidth();
        double interval = width / numDecades;

        for (int i = 1; i < numDecades; i++) 
        {
            double x = i * interval;
            GLine line = new GLine(x, 0, x, getHeight());
            add(line);
        }
        
	}
	private void addLabels()
	{
		int numDecades = NDECADES;
        double width = getWidth();
        double interval = width / numDecades;
		for (int i = 0; i < numDecades; i++) 
        {
            int decade = START_DECADE + i * 10;
            
            GLabel label = new GLabel(Integer.toString(decade));
            label.setFont(new Font("Arial", Font.BOLD, 10)); 
            
            double labelX = i*interval+6; 
            double labelY = getHeight() - graphMarginSize + label.getAscent() + 3; 
           
            label.setLocation(labelX, labelY);
            add(label);
        }
	}
	private void addHorizontalLines()
	{
		GLine topLine = new GLine(0, graphMarginSize, getWidth(), graphMarginSize); //top line
        add(topLine);
        
        GLine bottomLine = new GLine(0, getHeight() - graphMarginSize, getWidth(), getHeight() - graphMarginSize); //bottom line
        add(bottomLine);
	}
	
	private Color getEntryColor(NameSurferEntry entry)
	{
		if(entry.getColor()==null)
		{
			entry.setColor(getNextColor());
		}
		return entry.getColor();
	}
	
	private Color getNextColor() 
	{
	    Color color = GRAPH_COLORS[colorIndex];
	    colorIndex = (colorIndex + 1) % GRAPH_COLORS.length;
	    return color;
	}
	
	private int fontSize=11;
	private boolean prognoseIsClicked = false;
	private int colorIndex = 0;
	private Color[] GRAPH_COLORS = {Color.BLACK, Color.RED, Color.GREEN, Color.BLUE};
	private ArrayList<NameSurferEntry> entries= new ArrayList<NameSurferEntry>();
	private double graphMarginSize = GRAPH_MARGIN_SIZE;
	private double availableHeight= getHeight()-2*graphMarginSize;
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
}
