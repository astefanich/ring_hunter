package com.github.astefanich.ringhunter.hunters;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.github.astefanich.ringhunter.nodes.MiddleEarthNode;
import com.github.astefanich.ringhunter.trees.RandomTree;

/**
 * Utilizes a depth-first searching algorithm for finding The One Ring. Implementation class of
 * {@link AbstractRingHunter}.
 * 
 * @author AndrewStefanich
 * @see RandomTree
 * @see MiddleEarthNode
 */
public class DepthFirstHunter extends AbstractRingHunter {

	/** name of our hunter */
	private final String name = "Witch-king of Angmar";

	/**
	 * Gets the name of our hunter
	 * 
	 * @return the name
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Depth first tree-traversal algorithm. Determines proper path for finding The One Ring.
	 * 
	 * @param root
	 *          the starting node
	 */
	@Override
	public void startAt(MiddleEarthNode root) {
		if (root == null) {
			throw new IllegalStateException("root has not been initialized");
		}

		final Stack<MiddleEarthNode> path = new Stack<>();  //stack for tracking hunter movements
		final List<MiddleEarthNode> visitedNodes = new ArrayList<>(); //collection for storing visited nodes
		final StringBuilder sb = new StringBuilder();

		path.push(root);

		sb.append(String.format(getEncounterFormat(root), root.getName(), root.getDescription()));

		MiddleEarthNode activeNode = root;

		while (!activeNode.hasRing()) {
			activeNode = path.pop();
			List<MiddleEarthNode> children = activeNode.getChildren();
			for (int i = 0; i < children.size(); i++) {
				if (!visitedNodes.contains(children.get(i))) {  //don't want to revisit nodes
					activeNode = children.get(i);

					final String format = getEncounterFormat(activeNode);  //see RingHunter class for getEncounterFormat()
					sb.append(String.format(format, activeNode.getName(), activeNode.getDescription()));

					visitedNodes.add(activeNode);
					if (activeNode.hasRing()) {
						path.push(activeNode.getParent()); //re-add this parent node to the stack
						path.push(activeNode);
						sb.append("WE FOUND THE ONE RING. MUHAHA!\n");
						break;
					} else if (activeNode.isAdjacentToRing()) {
						path.push(activeNode.getParent()); //re-add this parent node to the stack
						path.push(activeNode);
						sb.append("The Ring is near; I can feel it\n");
						break;
					} else if (activeNode.getChildren().size() > 0) {
						path.push(activeNode.getParent()); //re-add this parent node to the stack
						path.push(activeNode);
						break; //this node has children. we want to break and enter a new for loop with this node as the root
					} else if (i == children.size()) { //if we visited all child nodes, we want to go back to the parent
						path.pop();
					} //end of if
				} //end of if (!visited)
			} //end of for
		} //end of while

		//arrange for printing the stack
//		final String pathIntroStr = new String("...the path is..."); //used for stringBuilder indexing
		final String pathIntroStr = "...the path is..."; //used for stringBuilder indexing
		sb.append(pathIntroStr);
		final int startPathIndex = (sb.indexOf(pathIntroStr)) + pathIntroStr.length();

		//iterate through our stack, prepending the proper path
		while (!path.isEmpty()) {
			activeNode = path.pop();
			sb.insert(startPathIndex, activeNode.getName());
			sb.insert(startPathIndex, getPathFormat(activeNode));   //see RingHunter class for getPathFormat()
		}

		actionLog = actionLog.concat(sb.toString());

	} //end of startAt()

} //end of class
