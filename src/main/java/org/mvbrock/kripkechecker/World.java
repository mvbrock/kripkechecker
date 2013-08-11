package org.mvbrock.kripkechecker;

import java.util.Hashtable;
import java.util.Vector;

public class World {

	private Hashtable<String, World> relationHash = new Hashtable<String, World>();
	private Vector<World> relationArray = new Vector<World>();
	private Vector<String> labelArray = new Vector<String>();
	private String name;

	public World(String nName) {
		setName(nName);
	}

	public void addLabel(String label) {
		labelArray.add(label);
	}

	public void addRelation(World world) {
		// If we haven't added the world before, then add it to the array.
		if (relationHash.put(world.toString(), world) == null) {
			relationArray.add(world);
		}
	}

	public String getLabel(int idx) {
		return labelArray.get(idx);
	}

	public int getLabelCount() {
		return labelArray.size();
	}

	public World getRelation(int idx) {
		return relationArray.get(idx);
	}

	public int getRelationCount() {
		return relationArray.size();
	}

	public boolean matchLabel(String label) {
		for (int i = 0; i < labelArray.size(); i++) {
			if (label.equals(labelArray.get(i))) {
				return true;
			}
		}
		return false;
	}

	public void setLabelArray(Vector<String> labelArray) {
		this.labelArray = labelArray;
	}

	public void setName(String nName) {
		name = nName;
	};

	@Override
	public String toString() {
		return name;
	}
}
