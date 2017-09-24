package com.github.astefanich.ringhunter.driver;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.github.astefanich.ringhunter.hunters.RingHunter;
import com.github.astefanich.ringhunter.hunters.RingHunter_DepthFirst;
import com.github.astefanich.ringhunter.nodes.MiddleEarthNode;
import com.github.astefanich.ringhunter.trees.RandomTree;

/**
 * Main driver class for this application.
 * 
 * @author AndrewStefanich
 */
public class RingHunterDriver extends Applet implements ActionListener {

	/**
	 * Creates a single instance of {@link RingHunterDriver}, which displays in a window.
	 * 
	 * @param args
	 *            cmd line args
	 */
	public static void main(String[] args) {

		final RingHunterDriver hunterFrame = new RingHunterDriver();
	}

	/** introduction string, in HTML format */
	private static final String INTRODUCTION = getIntroStrings();

	/**
	 * Gets strings for our introduction panel
	 * 
	 * @return the string, in HTML format
	 */
	private static String getIntroStrings() {
		StringBuilder sb = new StringBuilder();
		sb.append(stringToHTML(16, 40, "You are the Witch-king of Angmar\n"));
		sb.append(stringToHTML(16, 28, "You must search Middle-earth for The One Ring\n"));
		sb.append(stringToHTML(16, 15, "Along the way you will explore many locations and meet many people\n\n"));
		sb.append(stringToHTML(16, 50, "Find The Ring!!!\n\n\n"));
		sb.append(stringToHTML(12, 20,
				"**** This is not an interactive game, but rather visually demonstrates a searching algorithm ***\n"));
		sb.append(stringToHTML(10, 60, "If a graphic is exceptionally large, it may not display correctly\n"));
		sb.append(stringToHTML(10,60, "Keep clicking reset; a new tree gets generated each time!"));
		

		return sb.toString();
	}

	/**
	 * Converts a raw string into HTML format, edits font size, and performs indentation
	 * 
	 * @param fontSize
	 *            custom font size
	 * @param indentation
	 *            for a given line
	 * @param String
	 *            the string to edit
	 */
	private static String stringToHTML(int fontSize, int numIndents, String originalStr) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html><span style='font-size:");
		sb.append(fontSize);
		sb.append("px'>");
		for (int i = 0; i < numIndents; i++) {
			sb.append("&nbsp;");
		}
		sb.append(originalStr.replaceAll("\n", "<br>"));
		return sb.toString();
	}

	//SWING 
	JFrame window = new JFrame("Ring Hunter");
	JPanel buttonPanel = new JPanel();  //panel to hold the button
	JPanel treePanel = new JPanel();	//panel to hold the tree
	JPanel reportPanel = new JPanel();  //panel to hold the report/introduction

	JButton button = new JButton("Start");  //initiate button
	JLabel reportLabel = new JLabel();   //our component that holds the report string
	JPanel imagePanel = new JPanel();	//component that holds the tree graphic

	/**
	 * Creating an instance of this class will present an introduction pane<br>
	 * Each click of the button will reset the tree, and refresh the window.
	 */
	public RingHunterDriver() {

		button.addActionListener(this);

		imagePanel.add(new JLabel(new ImageIcon(RingHunterDriver.class.getResource("/witch_king.png"))));
		imagePanel.setBackground(Color.BLACK);

		//PANEL FOR THE BUTTON
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
		buttonPanel.setBackground(Color.BLACK);
		buttonPanel.add(button);

		//PANEL WHICH HOLDS THE REPORT COMPONENT (it initially holds our intro string, but it is then reset)
		reportPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
		reportPanel.setBackground(Color.BLACK);
		reportLabel.setText(INTRODUCTION);
		reportLabel.setForeground(Color.WHITE);  //the text color
		reportPanel.add(reportLabel);

		//PANEL WHICH HOLDS THE TREE COMPONENT
		treePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
		treePanel.setBackground(Color.BLACK);

		window.add(imagePanel, BorderLayout.SOUTH);
		window.add(buttonPanel, BorderLayout.NORTH);
		window.add(reportPanel, BorderLayout.WEST);
		window.add(treePanel, BorderLayout.CENTER);

		window.setSize(200, 200);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		reportPanel.setVisible(true);
		window.pack();
		window.setVisible(true);
	}

	/**
	 * Closes the active window <br>
	 * Creates a new {@code RingHunter}<br>
	 * Creates a new {@code RandomTree}<br>
	 * Hunter searches through the random tree, for the target.<br>
	 * New tree graphic is painted <br>
	 * Hunter report is refreshed <br>
	 * Window is reopened <br>
	 * 
	 * @param e
	 *            user clicks the button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		window.setVisible(false);
		treePanel.removeAll();  //empties the container holding the map for each iteration
		button.setText("Reset");  //if we reached this point, our user has clicked the button atleast once

		//get a new tree/report for each user click
		RingHunter myHunter = new RingHunter_DepthFirst();
		RandomTree myTree = new RandomTree();
		MiddleEarthNode treeRoot = myTree.getTreeTop();
		myHunter.startAt(treeRoot);

		String reportString = String.format("Hunter name: %s\n\nHunting report:%s", myHunter.getName(),
				myHunter.report());
		reportLabel.setText(stringToHTML(9, 0, reportString));
		reportPanel.setBackground(Color.DARK_GRAY);

		treePanel.add(myTree.getTreeGraphic());
		treePanel.setBackground(Color.LIGHT_GRAY);
		window.pack();
		window.setVisible(true);

	}
}
