package com.github.astefanich.ringhunter.nodes;

import java.util.ArrayList;
import java.util.List;

/**
 * This abstract class represents people and places you may encounter as you search for The
 * One Ring. (it is a name substitue for MountainCave, with some slight design improvements, no
 * functional changes in relation to the driver/hunter classes).
 * 
 * @author AndrewStefanich
 * @see Being
 * @see Place
 * @see Root
 */
public abstract class MiddleEarthNode {

	/** the name of our node */
	private String name;

	/** node description */
	private String description;

	/** the parent node */
	private MiddleEarthNode parent;

	/** holds true if this node has the ring */
	private boolean hasRing;

	/** holds true if this node is connected to the node which has the ring */
	private boolean isAdjacentToRing;

	/** each node contains a collection of its child nodes */
	private List<MiddleEarthNode> children = new ArrayList<MiddleEarthNode>();

	/**
	 * Sets the name of this node
	 * 
	 * @param name
	 *            the name
	 */
	void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the name of this node.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the description of this node.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the message associated with this node.
	 * 
	 * @param description
	 *            the description
	 */
	void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Determines if this node has The One Ring.
	 * 
	 * @return true if it has the ring, otherwise false
	 */
	public boolean hasRing() {
		return hasRing;
	}

	/**
	 * Sets the boolean value if this node has the ring.
	 * 
	 * @param hasRing
	 *            true, it is has the ring
	 */
	public void setHasRing(boolean hasRing) {
		this.hasRing = hasRing;
	}

	/**
	 * Determines if this nodes is adjacent to the node which holds the ring.
	 * 
	 * @return true if adjacent, otherwise false
	 */
	public boolean isAdjacentToRing() {
		return isAdjacentToRing;
	}

	/**
	 * Sets the boolean value of this node's adjacency to the ring.
	 * 
	 * @param adjacentToRing
	 *            true, if adjacent to ring.
	 */
	public void setAdjacentToRing(boolean adjacentToRing) {
		this.isAdjacentToRing = adjacentToRing;
	}

	/**
	 * Gets the collection of child nodes.
	 * 
	 * @return the list
	 */
	public List<MiddleEarthNode> getChildren() {
		return children;
	}

	/**
	 * Sets this nodes parent, and adds "this" as a child to the parent.
	 * 
	 * @param theParent
	 *            the parent node
	 */
	public void setParent(MiddleEarthNode theParent) {
		parent = theParent;
		parent.addChild(this);
	}

	/**
	 * Gets the parent of this node.
	 * 
	 * @return the parent node
	 */
	public MiddleEarthNode getParent() {
		return parent;
	}

	/**
	 * Adds a node to this object's collection of child nodes.
	 * 
	 * @param node
	 *            the child to add
	 */
	public void addChild(MiddleEarthNode node) {
		if (node != null) {
			children.add(node);
		}
	}

	/**
	 * Determines if this node has a parent.
	 * 
	 * @return true if it has a parent, otherwise false (parent==null)
	 */
	public boolean hasParent() {
		if (parent == null) {
			return false;
		}
		return true;
	}

	/**
	 * Hash code for MiddleEarthNode objects.
	 * 
	 * @return the hashCode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Determines equality of MiddleEarthNode instances.
	 * 
	 * @param node
	 *            the node to compare
	 * @return true if equal, otherwise false
	 */
	@Override
	public boolean equals(Object node) {
		if (this == node)
			return true;
		if (node == null)
			return false;
		if (getClass() != node.getClass())
			return false;
		MiddleEarthNode other = (MiddleEarthNode) node;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/** String representation of this node
	 * @return the name
	 */
	@Override
	public String toString(){
		return name;
	}

} //end of MiddleEarthNode class
