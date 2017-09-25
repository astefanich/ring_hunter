package com.github.astefanich.ringhunter.trees;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.abego.treelayout.TreeLayout;
import org.abego.treelayout.util.DefaultConfiguration;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

import com.github.astefanich.ringhunter.hunters.AbstractRingHunter;
import com.github.astefanich.ringhunter.nodes.Being;
import com.github.astefanich.ringhunter.nodes.MiddleEarthNode;
import com.github.astefanich.ringhunter.nodes.Place;
import com.github.astefanich.ringhunter.nodes.Root;

/**
 * Generates a random tree structure with 0-4 children per node. Nodes are represented
 * by Tolkien's Middle-earth locations or characters. This will create a tree which returns a
 * {@link MiddleEarthNode} for a {@link AbstractRingHunter} to search, and also creates a printable tree
 * with the same structure.
 * 
 * @author AndrewStefanich
 */
public class RandomTree {

	/**
	 * This int defines the maximum # of children any one node can have, and the maximum depth of
	 * each branch (from the tree's root node). <br>
	 * - <br>
	 * The maximum number of unique nodes any depth-based tree would allow is (max ^ max)/max - 1.
	 * <br>
	 * This # should not be greater than 4, since this class' String bank has 85 unique strings.<br>
	 * Filling the tree entirely would only occur if every random number generated == max.
	 * Highly unlikely, but we don't want to stop tree construction prematurely due to lack of
	 * resources.
	 */
	private static final int MAX = 4;

	/** collection of Beings/Places in Middle-earth */
	private List<MiddleEarthNode> nodes;

	/** the tree's overall root node */
	private MiddleEarthNode angmar;

	/** our answer. The path should always end with Frodo */
	private MiddleEarthNode frodo;

	/** object that is adjacent to frodo */
	private MiddleEarthNode adjacentNode;

	/** we want to ensure the tree we built contains Frodo */
	private boolean treeHasRing;

	/** constructs a tree for printing use. Does not affect the hunter algorithm */
	private DefaultTreeForTreeLayout<MiddleEarthNode> treeToVisualize;

	/**
	 * Gets the root node of a randomly arranged tree.
	 * 
	 * @return the root
	 */
	public MiddleEarthNode getTreeTop() {
		reset();
		while (!buildSubTree(angmar, MAX)) {  //keeps building until we get a valid tree
			reset();
		}
		return angmar;
	}

	/**
	 * Re-sets our list, root, and target.
	 */
	private void reset() {
		initializeList((nodes = new ArrayList<>()));
		angmar = new Root("Angmar", "realm of the Ringwraiths");
		frodo = new Being(null, "Frodo", "bearer of The One Ring");
		adjacentNode = nodes.get(new Random().nextInt(nodes.size()));
		frodo.setParent(adjacentNode);  //selects a random parent for the ring bearer
		frodo.setHasRing(true);
		adjacentNode.setAdjacentToRing(true);
		treeToVisualize = new DefaultTreeForTreeLayout<MiddleEarthNode>(angmar);
	}

	/**
	 * Initializes a {@link List} of nodes with names/descriptions from the String bank. Parents
	 * will be set in buildSubTree().
	 * 
	 * @param nodes
	 *            the list to add to
	 */
	private void initializeList(List<MiddleEarthNode> nodes) {
		/** the index of the name parameter, from the String[] */
		final int name_index = 0;
		/** the index of the description parameter, from the String[] */
		final int desc_index = 1;
		/** the index of the type parameter, from the String[] */
		final int type_index = 2;
		for (int i = 0; i < stringz.length; i++) {
			final String[] tokens = stringz[i].split("/");
			if (tokens[type_index].equals("Being")) {
				nodes.add(new Being(null, tokens[name_index], tokens[desc_index]));
			} else if (tokens[type_index].equals("Place")) {
				nodes.add(new Place(null, tokens[name_index], tokens[desc_index]));
			} else {
				System.out.println(String.format("Type could not be identifed for %s. Object not created for this arg",
						stringz[i]));
			}
		} //end of for
	} //end of initializeList

	/**
	 * Recursively creates a random subtree from a given node. Depth should not be greater than the
	 * max variable. (the depth argument ensures capacity and allows for a more even distribution)
	 * 
	 * @param rootNode
	 *            the starting point for branching down
	 * @param depth
	 *            the branch depth of each iteration
	 * @return true if it's a valid tree, otherwise false (false conditions: Angmar doesn't have
	 *         children and/or Frodo isn't in the tree)
	 */
	private boolean buildSubTree(MiddleEarthNode rootNode, int depth) {
		int randomNumChildren = new Random().nextInt(MAX + 1);		//between 0 and the MAX (inclusive)
		//we randomly generate # of children, but Angmar should have atleast one child
		if (rootNode.equals(angmar) && randomNumChildren <= 0) {
			return false;
		}
		//we will create branches until the collection does not meet capacity, or it the algorithm stops naturally (via 0 children, or depth gauge)
		if (randomNumChildren > nodes.size()) {
			return treeHasRing; //we reached the end of our available node objects
		}
		for (int i = 0; i < randomNumChildren; i++) {
			MiddleEarthNode randomChild = nodes.get(new Random().nextInt(nodes.size()));  //gets a random node from the list
			randomChild.setParent(rootNode);
			nodes.remove(randomChild); //remove node to avoid duplicates in the tree
		}
		for (MiddleEarthNode node : rootNode.getChildren()) {
			treeToVisualize.addChild(rootNode, node);
			if (node.isAdjacentToRing()) {
				treeToVisualize.addChild(node, frodo);
				treeHasRing = true;
			} else if (depth > 2) { //this signifies we are 1 level from the base, so we don't want the children to have subtrees
				buildSubTree(node, new Integer(depth - 1));	//new Integer object, don't want the depth value to be shared during the recursion
			}
		}
		return treeHasRing;
	} //end of buildTree()

	/**
	 * Gets the randomly generated tree as a graphic
	 * 
	 * @return the tree component
	 */
	public Component getTreeGraphic() {
		TreeLayout<MiddleEarthNode> layout = new TreeLayout<MiddleEarthNode>(treeToVisualize,
				new NodeSizer(), new DefaultConfiguration<MiddleEarthNode>(70, 15));
		TreeVisualizer treeComponent = new TreeVisualizer(layout);
		return treeComponent;
	}

	/** Bank of names, descriptions, and object types for use in the tree. */
	private static final String[] stringz = {
			"Alatar/the blue wizard/Being",
			"Aragorn/son of Arathorn/Being",
			"Arathorn/chieftain of the Dunedain/Being",
			"Arwen/daughter of Elrond/Being",
			"Balrog/a fiery beast of Moria/Being",
			"Bard/the slayer of Smaug/Being",
			"Bilbo/a very famous hobbit/Being",
			"Boromir/son of Denethor/Being",
			"Deagol/best friend of Smeagol/Being",
			"Denethor/steward of Gondor/Being",
			"Elrond/lord of Rivendell/Being",
			"Eomer/son of Theoden/Being",
			"Eowyn/daughter of Eomund/Being",
			"Faramir/younger brother of Boromir/Being",
			"Gandalf/the grey wizard/Being",
			"Gimli/son of Gloin/Being",
			"Gollum/a hobbit corrupted by the Ring/Being",
			"Gorbag/an Orc captain/Being",
			"Hamfast/father of Samwise/Being",
			"Isildur/high King of Gondor/Being",
			"Legolas/elven Prince of Mirkwood/Being",
			"Merry/Frodo's cousin/Being",
			"Morgoth/the first dark lord/Being",
			"Nazgul/your fellow ringwraiths/Being",
			"Peregrin/Frodo's foolish friend/Being",
			"Radagast/the brown wizard/Being",
			"Samwise/Frodo's best friend/Being",
			"Saruman/the white wizard/Being",
			"Shagrat/an Orc captain/Being",
			"Shelob/a great spider/Being",
			"Smaegol/former ring bearer/Being",
			"Smaug/the last great dragon/Being",
			"Theoden/king of Rohan/Being",
			"Thorin/fighter of Smaug/Being",
			"Thranduil/father of Legolas/Being",
			"Treebeard/an Ent of fangorn forest/Being",
			"Ugluk/leader of the Uruk-hai/Being",
			"Anduin/a river in Wilderland/Place",
			"Ash Mountains/a dark mountain range/Place",
			"Barad-dur/the dark tower of Mordor/Place",
			"Belfalas/a bay in the Great Sea/Place",
			"Belegaer/a great sea west of Middle-earth/Place",
			"Black Gate/the entrance into Mordor/Place",
			"Brandywine River/a river near the Shire/Place",
			"Khazad-dum/a bridge within Moria/Place",
			"Buckland/a small Hobbit colony/Place",
			"Cape Forochel/a cold cape in the northern waste/Place",
			"Caradhras/a peak in the misty mountains/Place",
			"Celduin/a river in Rhovanion/Place",
			"Cirith Ungol/a mountain pass/Place",
			"Dagorlad/a vast plain in Mordor/Place",
			"Dale/former city of men/Place",
			"Dead Marshes/a harsh swampland/Place",
			"Dol Guldur/Sauron's old stronghold/Place",
			"Emyn Muil/a maze of rocks near Rhovanion/Place",
			"Eriador/a realm between mountains/Place",
			"Fangorn Forest/a great forest/Place",
			"Forodwaith/an area north of Eriador/Place",
			"Glittering Caves/a cave system behind Helms Deep/Place",
			"Gondor/a prominent kingdom of Men/Place",
			"Helms Deep/stronghold of Rohan/Place",
			"Henneth Annun/a hidden refuge for rangers/Place",
			"Isengard/an old fortress of Gondor/Place",
			"Lonely Mountain/a stronghold of dwarves/Place",
			"Long Lake/a lake near the Lonely Mountain/Place",
			"Lothlorien/an Elven realm/Place",
			"Minas Morgul/an old city of Gondor/Place",
			"Minas Tirith/the capital of Gondor/Place",
			"Mirkwood/a great forest near Gondor/Place",
			"Misty Mountains/an extensive mountain range/Place",
			"Mordor/a barren wasteland/Place",
			"Moria/an underground Dwarven city/Place",
			"Mount Gundabad/an Orc stronghold/Place",
			"Osgiliath/former capital of Gondor/Place",
			"Rhovanion/a vast region in the north/Place",
			"Rhun/a far eastern realm/Place",
			"Rivendell/a glorious Elven realm/Place",
			"Rohan/a great kingdom of men/Place",
			"Sea Rhun/a large inland sea/Place",
			"Old Forest/woodland near Buckland/Place",
			"Shire/home of the hobbits/Place",
			"Udun/a valley in northwestern Mordor/Place",
			"Woodland Realm/now known as Mirkwood/Place",
	}; //end of stringz

} //end of RandomizedTreeGenerator class
