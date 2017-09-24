package com.github.astefanich.ringhunter.nodes;

/**
 * Unique class for specifically identifying the root node of our tree.
 * 
 * @author AndrewStefanich
 */
public class Root extends MiddleEarthNode {

	/**
	 * The constructor should only be used for instantiating the root. Instantiation of all other
	 * child nodes should be done via the nested classes, and must give a type argument (Being or
	 * Place). Creation should be done by the factory.
	 * 
	 * @param name
	 *            the name of this place or person
	 * @param description
	 *            the description associate with this name or person
	 */
	public Root(String name, String description) {
		setName(name);
		setDescription(description);
	}

}
