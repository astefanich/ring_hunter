package com.github.astefanich.ringhunter.hunters;

import java.util.NoSuchElementException;

import com.github.astefanich.ringhunter.nodes.Being;
import com.github.astefanich.ringhunter.nodes.MiddleEarthNode;
import com.github.astefanich.ringhunter.nodes.Place;
import com.github.astefanich.ringhunter.nodes.Root;

/**
 * Abstract hunter class. Subclasses should traverse the tree by definin custom searching
 * algorithms, by overriding startAt().
 */
public abstract class RingHunter {

	/**
	 * Gets the name of this instance.
	 * 
	 * @return the name
	 */
	public abstract String getName();

	/**
	 * Traversing algorithm.
	 * 
	 * @param root
	 *            the root node
	 */
	public abstract void startAt(MiddleEarthNode root);

	/**
	 * Used by subclasses to print out where your hunter has been
	 */
	protected String actionLog = "\n";

	/**
	 * This is called by the driver at the end to report where your
	 * hunter has been. Presents this in a JOptionPane
	 * 
	 * @return the actionLog
	 */
	public String report() {
		return actionLog;
	}

	/**
	 * Provides a String format based on the specific class of a MiddleEarthNode instance.
	 * Being objects are prepended with "Meeting", Place objects are prepended with "Entering".
	 * 
	 * @param activeNode
	 *            the node to check
	 * @return the format String
	 */
	String getEncounterFormat(MiddleEarthNode activeNode) {
		String format = null;
		if (activeNode.getClass().equals(Being.class)) {
			format = "Meeting %s (%s)\n";
		} else if (activeNode.getClass().equals(Place.class)) {
			format = "Entering (the) %s (%s)\n";
		} else if (activeNode.getClass().equals(Root.class)) {
			format = "Leaving %s (%s)\n";
		} else {
			throw new NoSuchElementException("Unknown class type");
		}
		return format;
	}

	/**
	 * Gets the String format for printing the path, based on node class type. <br>
	 * Being objects are prepended with "go see" <br>
	 * Place objects are prepended with "visit the" <br>
	 * Root objects are prepended with "Start at" (we should only have 1 root instance)
	 * 
	 * @param activeNode
	 *            the node to check
	 * @return the format String
	 */
	String getPathFormat(MiddleEarthNode activeNode) {
		String format = null;
		if (activeNode.getClass().equals(Being.class)) {
			format = "\nand then go see "; //for use with Being objects
		} else if (activeNode.getClass().equals(Place.class)) {
			format = "\nand then visit (the) "; //for use with Being objects
		} else if (activeNode.getClass().equals(Root.class)) {
			format = "\nStart at ";
		} else {
			throw new NoSuchElementException("Unknown class type");
		}
		return format;
	}

}
