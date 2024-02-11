/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the bottom of the window.
 */
	private NameSurferDataBase database = new NameSurferDataBase("names-data.txt");
	private NameSurferGraph graph; 
	private JLabel label;
    private JTextField txtField;
    private JButton btnGraph;
    private JButton btnClear;
    private JButton btnPrognose;
    private JButton btnDelete;
	private JPanel panel;
    
	public NameSurfer()
	{
		graph = new NameSurferGraph(); 
		add(graph, BorderLayout.CENTER); 
	}
	
	public void init() {
	    // You fill this in, along with any helper methods //
		
		label= new JLabel("Name");
		txtField = new JTextField(30);
		btnGraph = new JButton("Graph");
		btnClear = new JButton("Clear");
		btnDelete = new JButton("Delete");
		btnPrognose = new JButton("Prognose");
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(APPLICATION_WIDTH, 37));
		btnGraph.setFont(new Font("Arial",Font.BOLD,15));
        btnClear.setFont(new Font("Arial",Font.BOLD,15));
        btnPrognose.setFont(new Font("Arial",Font.BOLD,15));
        btnDelete.setFont(new Font("Arial",Font.BOLD,15));
        label.setFont(new Font("Arial",Font.BOLD,15));
        txtField.setFont(new Font("Arial",Font.BOLD,15));
		
        add(panel, BorderLayout.SOUTH);
		panel.add(label);
		panel.add(txtField);
		panel.add(btnGraph);
		panel.add(btnClear);
		panel.add(btnDelete);
		panel.add(btnPrognose);
		
		
		
		btnGraph.addActionListener(this);
        btnClear.addActionListener(this);
        btnDelete.addActionListener(this);
        btnPrognose.addActionListener(this);
	}

/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnGraph) 
		{
		    String name = txtField.getText().toLowerCase();
		    NameSurferEntry entry = database.findEntry(name);
		    	if (entry != null) 
		    	{
		        graph.addEntry(entry);
		        graph.update();
		    	} 
		    	else 
		    	{
		    	println("Name not found in the database.");
		    	}
		}
		
		else if (e.getSource() == btnClear) 
		{
            println("Clear button clicked!");
            txtField.setText("");
            graph.clear();
            graph.update();
        }
		else if(e.getSource()== btnDelete)
		{
			println("Delete button clicked!");
			String name = txtField.getText().toLowerCase();
		    NameSurferEntry entry = database.findEntry(name);
		    graph.Delete(entry);
			txtField.setText("");
			graph.update();
		}
		else if(e.getSource()==btnPrognose)
		{
			println("Prognose button clicked!");
			graph.prognoseClicked(true);
			graph.update();	
		}
	}
}
