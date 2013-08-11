package org.mvbrock.kripkechecker;

public enum Modal {
	Box, Diamond, Not, Blank;

	static public Modal convertToModal(TokenId tId) {
		if (tId == TokenId.BOX) {
			return Box;
		} else if (tId == TokenId.DIAMOND) {
			return Diamond;
		} else if (tId == TokenId.NOT) {
			return Not;
		} else {
			return Blank;
		}
	}
}
