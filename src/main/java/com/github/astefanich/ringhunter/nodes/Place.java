package com.github.astefanich.ringhunter.nodes;

import com.github.astefanich.ringhunter.trees.RandomTree;

/**
 * Class for the locales of Middle-earth.
 * 
 * @author AndrewStefanich
 * @see MiddleEarthNode
 * @see Being
 * @see RandomTree
 */
public class Place extends MiddleEarthNode {

	/**
	 * Constructs non-root nodes, setting the name and description.<br>
	 * If theParent != null, it "attaches" the object to its parent (and the parent to its child).
	 * <br>
	 * Creation should be done by a factory/generator.
	 * 
	 * @param theParent
	 *            the parent node
	 * @param name
	 *            the name of this Place
	 * @param description
	 *            the description associate with this Place
	 */
	public Place(MiddleEarthNode theParent, String name, String description) {
		setName(name);
		setDescription(description);
		if (theParent != null) {
			setParent(theParent);
		}
	}

}