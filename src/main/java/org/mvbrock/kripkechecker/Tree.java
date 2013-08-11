package org.mvbrock.kripkechecker;

import java.util.Vector;

public class Tree {

	private TreeNode root = new TreeNode();
	private TreeNode cur = new TreeNode();
	private Tokenizer tokenizer;
	private Vector<String> error = new Vector<String>();
	private int parenthesis = 0;

	public Tree(Tokenizer nTokenizer) {
		tokenizer = nTokenizer;
	}

	public TreeNode getRoot() {
		return root;
	}

	public boolean parse() {
		boolean successParse = parseTree(cur);
		boolean successEnd = tokenizer.onLastToken();
		if (successParse == false || successEnd == false) {
			for (int i = 0; i < error.size(); i++) {
				System.out.println(error.get(i));
			}
			System.out.println("One or more errors occured.");
			return false;
		} else {
			root = cur;
			return true;
		}
	}

	private boolean parseTree(TreeNode node) {
		Token t = tokenizer.getToken();
		if (t.getId() == TokenId.TOP || t.getId() == TokenId.BOTTOM || t.getId() == TokenId.LABEL) {
			node.setToken(t);
			node.setLeaf(true);
			return parseTerminal(node);
		} else if (t.getId() == TokenId.NOT || t.getId() == TokenId.BOX || t.getId() == TokenId.DIAMOND) {
			node.addModal(Modal.convertToModal(t.getId()));
			return parseModal(node);
		} else if (t.getId() == TokenId.LPAREN) {
			parenthesis++;
			return parseOperator(node);
		} else {
			error.add("Invalid \'" + t.getId() + "\' token at " + "position " + t.getPosition() + ".");
			return false;
		}
	}

	private boolean parseTerminal(TreeNode node) {
		boolean next = tokenizer.nextToken();
		Token t = tokenizer.getToken();
		if (t.getId() == TokenId.AND || t.getId() == TokenId.OR || t.getId() == TokenId.IMPLIES) {
			tokenizer.previousToken();
			return true;
		} else {
			if (parenthesis <= 0) {
				if (next == false) {
					return true;
				} else {
					error.add("Invalid \'" + t.getId() + "\' token at " + "position " + t.getPosition()
							+ ".  Operator statements " + "must be surrounded by opening and closing parentheses.");
					return false;
				}
			} else {
				if (next == false) {
					error.add(new String("Unexpected end of formula."));
					return false;
				}
				if (t.getId() == TokenId.RPAREN) {
					tokenizer.previousToken();
					return true;
				} else {
					error.add("Invalid \'" + t.getId() + "\' token at " + "position " + t.getPosition() + ".");
					return false;
				}
			}
		}
	}

	private boolean parseModal(TreeNode node) {
		if (tokenizer.nextToken() == false) {
			return false;
		}

		return parseTree(node);
	}

	private boolean parseOperator(TreeNode node) {
		if (tokenizer.nextToken() == false) {
			return false;
		}
		Token t = tokenizer.getToken();

		TreeNode childOne = new TreeNode();
		node.setChildNodeOne(childOne);
		if (parseTree(node.getChildOne()) == false) {
			return false;
		}

		if (tokenizer.nextToken() == false) {
			return false;
		}
		t = tokenizer.getToken();
		if (t.getId() == TokenId.AND || t.getId() == TokenId.OR || t.getId() == TokenId.IMPLIES) {
			node.setToken(t);
		} else {
			error.add("Invalid \'" + t.getId() + "\' token at " + "position " + t.getPosition() + ".");
			return false;
		}

		if (tokenizer.nextToken() == false) {
			return false;
		}

		TreeNode childTwo = new TreeNode();
		node.setChildNodeTwo(childTwo);
		if (parseTree(node.getChildTwo()) == false) {
			return false;
		}

		if (tokenizer.nextToken() == false) {
			return false;
		}

		t = tokenizer.getToken();
		if (t.getId() == TokenId.RPAREN) {
			parenthesis--;
			return true;
		} else {
			error.add("Invalid \'" + t.getId() + "\' token at " + "position " + t.getPosition()
					+ ".  Expected an " + "RPAREN token.");
			return false;
		}
	}
}
