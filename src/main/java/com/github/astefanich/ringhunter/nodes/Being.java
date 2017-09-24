package com.github.astefanich.ringhunter.nodes;

import com.github.astefanich.ringhunter.trees.RandomTree;

/**
 * Class for the living beings of Middle-earth.
 * 
 * @author AndrewStefanich
 * @see MiddleEarthNode
 * @see Place
 * @see RandomTree
 */
public class Being extends MiddleEarthNode {

	/**
	 * Constructs non-root nodes, setting the name and description. <br>
	 * If theParent != null, tt "attaches" the object to its parent (and the parent to its child).
	 * <br>
	 * Creation should be done by a factory/generator.
	 * 
	 * @param theParent
	 *            the parent node
	 * @param name
	 *            the name of this Being
	 * @param description
	 *            the description associate with this Being
	 */
	public Being(MiddleEarthNode theParent, String name, String description) {
		setName(name);
		setDescription(description);
		if (theParent != null) {
			setParent(theParent);
		}
	}

} //end of Being class