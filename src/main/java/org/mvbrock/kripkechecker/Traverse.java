
package org.mvbrock.kripkechecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class Traverse {

	private static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	TreeNode node;

	public Traverse(TreeNode node) {
		this.node = node;
	}

	public void traverse() throws IOException {
		System.out.print("(");
		if (node.getParent() != null) {
			System.out.print(node.getParent());
		}
		System.out.print(")->[" + node + "] : ");

		Vector<Modal> modalArray = node.getModalArray();
		for (int i = 0; i < modalArray.size(); i++) {
			System.out.print(modalArray.get(i) + ",");
		}
		System.out.println("");

		System.out.print("(");
		if (node.getChildOne() != null) {
			System.out.print(node.getChildOne());
		}
		System.out.print("), (");
		if (node.getChildTwo() != null) {
			System.out.print(node.getChildTwo());
		}
		System.out.println(")");

		System.out.print(":");
		int val = Integer.parseInt(stdin.readLine());
		switch (val) {
		case 8:
			if (node.getParent() != null) {
				node = node.getParent();
			}
			traverse();
			break;
		case 4:
			if (node.getChildOne() != null) {
				node = node.getChildOne();
			}
			traverse();
			break;
		case 6:
			if (node.getChildTwo() != null) {
				node = node.getChildTwo();
			}
			traverse();
			break;
		case 0:
			break;
		}

	}
}
