package org.mvbrock.kripkechecker;

import java.util.Hashtable;
import java.util.Vector;

public class Tokenizer {
	private int tokenIdx;
	private Vector<Token> tokenArray;
	private Hashtable<String, Token> tokenHash;
	private static String tokenKey[] = {
		"(", ")", "and", "or", "not", "implies", "box", "diamond", "top", "bottom", "label", ","
	};
	private static Token tokenValue[] = {
			new Token(TokenId.LPAREN), new Token(TokenId.RPAREN), new Token(TokenId.AND),
			new Token(TokenId.OR), new Token(TokenId.NOT), new Token(TokenId.IMPLIES), new Token(TokenId.BOX),
			new Token(TokenId.DIAMOND), new Token(TokenId.TOP), new Token(TokenId.BOTTOM), new Token(TokenId.LABEL),
			new Token(TokenId.COMMA)
	};

	public Tokenizer(String tokenStream) {
		tokenIdx = 0;
		tokenArray = new Vector<Token>();
		tokenHash = new Hashtable<String, Token>();
		createHashTable();
		createTokenArray(tokenStream);
	}

	private boolean createTokenArray(String tokenStream) {
		int i = 0;
		while (i < tokenStream.length()) {
			char ch = tokenStream.charAt(i);
			switch (ch) {
			case ' ':
				i = handleWhiteSpace(tokenStream, i);
				break;
			case '(':
				tokenArray.add(new Token(TokenId.LPAREN, i));
				i++;
				break;
			case ')':
				tokenArray.add(new Token(TokenId.RPAREN, i));
				i++;
				break;
			case ',':
				tokenArray.add(new Token(TokenId.COMMA, i));
				i++;
				break;
			default:
				if ((ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9')) {
					i = handleIdentifier(tokenStream, i);
				} else {
					System.out.println("Invalid character \'" + ch + "\' in formula.");
					return false;
				}
				break;
			}
		}
		return true;
	}

	private boolean createHashTable() {
		if (tokenKey.length != tokenValue.length) {
			System.out.println("Invalid keys and values for hash table.");
			return false;
		}
		for (int i = 0; i < tokenKey.length; i++) {
			tokenHash.put(tokenKey[i], tokenValue[i]);
		}
		return true;
	}

	public Token getToken() {
		return tokenArray.get(tokenIdx);
	}

	public Token getToken(int idx) {
		if (idx >= 0 && idx < tokenArray.size()) {
			return tokenArray.get(idx);
		} else {
			return null;
		}
	}

	private int handleWhiteSpace(String tokenString, int position) {
		while (tokenString.charAt(position) == ' ' && position < tokenString.length()) {
			position++;
		}
		return position;
	}

	private int handleIdentifier(String tokenString, int position) {
		char ch = tokenString.charAt(position);
		int tokenPosition = position;
		String identifier = "";
		while ((ch >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9')) {
			identifier += ch;
			position++;
			if (position == tokenString.length()) {
				break;
			}
			ch = tokenString.charAt(position);
		}
		for (int i = 0; i < tokenKey.length; i++) {
			if (identifier.equals(tokenKey[i])) {
				// Add the reserved word to the token array
				Token reservedToken = tokenHash.get(identifier);
				tokenArray.add(new Token(reservedToken.getId(), tokenPosition));
				return position;
			}
		}
		// Otherwise we just add the token as a labels
		tokenArray.add(new Token(TokenId.LABEL, tokenPosition, identifier));
		return position;
	}

	public int length() {
		return tokenArray.size();
	}

	public boolean nextToken() {
		if (tokenIdx == tokenArray.size() - 1) {
			return false;
		} else {
			tokenIdx++;
			return true;
		}
	}

	public boolean onLastToken() {
		if (tokenIdx == tokenArray.size() - 1) {
			return true;
		} else {
			Token token = tokenArray.get(tokenIdx);
			System.out.println("Formula ends incorrectly at position " + token.getPosition() + ".");
			return false;
		}
	}

	public boolean previousToken() {
		if (tokenIdx == 0) {
			return false;
		} else {
			tokenIdx--;
		}
		return true;
	}
}
