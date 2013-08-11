package org.mvbrock.kripkechecker;

import java.util.Vector;

public class TreeNode {
	private boolean evaluated;
	private boolean isLeaf;
	private Token token;
	private Vector<Modal> modalArray = new Vector<Modal>();
	private TreeNode childOne;
	private TreeNode childTwo;
	private TreeNode parent;

	public TreeNode() {
	}

	public TreeNode(Token nToken) {
		token = nToken;
	}

	public TreeNode(Token nToken, boolean nIsLeaf) {
		token = nToken;
		isLeaf = nIsLeaf;
	}

	public void addModal(Modal modal) {
		modalArray.add(modal);
	}

	public Vector<Modal> getModalArray() {
		return modalArray;
	}

	public int getModalArrayCount() {
		return modalArray.size();
	}

	public TreeNode getChildOne() {
		return childOne;
	}

	public TreeNode getChildTwo() {
		return childTwo;
	}

	public boolean getEvaluated() {
		return evaluated;
	}

	public boolean getIsLeaf() {
		return isLeaf;
	}

	public TreeNode getParent() {
		return parent;
	}

	public Token getToken() {
		return token;
	}	

	public void setChildNodeOne(TreeNode nChildOne) {
		childOne = nChildOne;
		childOne.setParent(this);
	}

	public void setChildNodeTwo(TreeNode nChildTwo) {
		childTwo = nChildTwo;
		childTwo.setParent(this);
	}

	public void setLeaf(boolean nIsLeaf) {
		isLeaf = nIsLeaf;
	}

	public void setParent(TreeNode nParent) {
		parent = nParent;
	}

	public void setToken(Token nToken) {
		token = nToken;
	}

	@Override
	public String toString() {
		String output = "";
		if (this.getToken() != null) {
			output += this.getToken().getId().toString();
			if (this.getToken().getId() == TokenId.LABEL) {
				output += ":(" + this.getToken().getLabel() + ")";
			}
		} else {
			output += "null";
		}
		return output;
	}
}
