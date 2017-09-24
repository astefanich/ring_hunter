package com.github.astefanich.ringhunter.trees;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import org.abego.treelayout.TreeForTreeLayout;
import org.abego.treelayout.TreeLayout;

import com.github.astefanich.ringhunter.nodes.MiddleEarthNode;

/**
 * Utility class for printing a tree
 * 
 * @author AndrewStefanich
 */
@SuppressWarnings("serial")
public class TreeVisualizer extends JComponent {

	/** the layout */
	private final TreeLayout<MiddleEarthNode> treeLayout;

	private final static int ARC_SIZE = 10;
	private final static Color BOX_COLOR = Color.orange;
	private final static Color BORDER_COLOR = Color.darkGray;
	private final static Color TEXT_COLOR = Color.black;

	/**
	 * Specifies the tree to be displayed by passing in a {@link TreeLayout} for
	 * that tree.
	 * 
	 * @param treeLayout
	 *            the {@link TreeLayout} to be displayed
	 */
	public TreeVisualizer(TreeLayout<MiddleEarthNode> treeLayout) {
		this.treeLayout = treeLayout;

		Dimension size = treeLayout.getBounds().getBounds().getSize();
		setPreferredSize(size);
	}

	/**
	 * the tree
	 * 
	 * @return the tree instance
	 */
	private TreeForTreeLayout<MiddleEarthNode> getTree() {
		return treeLayout.getTree();
	}

	/**
	 * Gets children
	 * 
	 * @param parent
	 *            the parent node
	 * @return collection of children
	 */
	private Iterable<MiddleEarthNode> getChildren(MiddleEarthNode parent) {
		return getTree().getChildren(parent);
	}

	/**
	 * Gets the numeric bounds of the node, for printing
	 * 
	 * @param node
	 *            the node
	 * @return the rectangle (can be a border)
	 */
	private Rectangle2D.Double getBoundsOfNode(MiddleEarthNode node) {
		return treeLayout.getNodeBounds().get(node);
	}

	/**
	 * paints the branches
	 * 
	 * @param g
	 *            graphics
	 * @param parent
	 *            the parent node
	 */
	private void paintEdges(Graphics g, MiddleEarthNode parent) {
		if (!getTree().isLeaf(parent)) {
			Rectangle2D.Double b1 = getBoundsOfNode(parent);
			double x1 = b1.getCenterX();
			double y1 = b1.getCenterY();
			for (MiddleEarthNode child : getChildren(parent)) {
				Rectangle2D.Double b2 = getBoundsOfNode(child);
				g.drawLine((int) x1, (int) y1, (int) b2.getCenterX(),
						(int) b2.getCenterY());

				paintEdges(g, child);
			}
		}
	}

	/**
	 * paints the boxes, which represent nodes
	 * 
	 * @param g
	 *            graphics
	 * @param node
	 *            the node
	 */
	private void paintBox(Graphics g, MiddleEarthNode node) {
		// draw the box in the background
		if (node.getName().equals("Frodo")) {
			g.setColor(Color.GREEN);
		} else {
			g.setColor(BOX_COLOR);
		}
		Rectangle2D.Double box = getBoundsOfNode(node);
		g.fillRoundRect((int) box.x, (int) box.y, (int) box.width - 1,
				(int) box.height - 1, ARC_SIZE, ARC_SIZE);
		g.setColor(BORDER_COLOR);
		g.drawRoundRect((int) box.x, (int) box.y, (int) box.width - 1,
				(int) box.height - 1, ARC_SIZE, ARC_SIZE);

		// draw the text on top of the box (possibly multiple lines)
		g.setColor(TEXT_COLOR);
		String[] tokens = node.getName().split(" ");
		FontMetrics m = getFontMetrics(getFont());
		int x = (int) box.x + ARC_SIZE / 2;
		int y = (int) box.y + m.getAscent() + m.getLeading() + 1;
		for (int i = 0; i < tokens.length; i++) {
			if (!tokens[i].equals("the") && !tokens[i].equals("of")) {
				g.drawString(tokens[i], x, y);
				y += m.getHeight();
			}
		}
	}

	/**
	 * Paints ths tree
	 * 
	 * @param g
	 *            graphics
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		//paint the structure
		paintEdges(g, getTree().getRoot());
		// paint the nodes' names (with a smaller font)
		Font currentFont = g.getFont();
		Font newFont = currentFont.deriveFont(currentFont.getSize() * .7F);  //names print in a smaller font
		g.setFont(newFont);
		for (MiddleEarthNode nodeName : treeLayout.getNodeBounds().keySet()) {
			paintBox(g, nodeName);
		}
	}
}
