package com.github.astefanich.ringhunter.trees;

import org.abego.treelayout.NodeExtentProvider;

import com.github.astefanich.ringhunter.nodes.MiddleEarthNode;

/**
 * Utility class which provides size dimensions for printing nodes
 * 
 * @author AndrewStefanich
 */
class NodeSizer implements NodeExtentProvider<MiddleEarthNode> {

	/**
	 * Sets the height for nodes
	 * 
	 * @param node
	 *            the node
	 * @return the width
	 */
	@Override
	public double getWidth(MiddleEarthNode node) {
		return 60;
	}

	/**
	 * Gets the height for nodes. Nodes with more than one word will print in a larger box.
	 * 
	 * @param node
	 *            the node
	 * @return the width
	 */
	@Override
	public double getHeight(MiddleEarthNode node) {
		if (node.toString().indexOf(" ") != -1) {
			return 40;
		}
		return 25;
	}

}
