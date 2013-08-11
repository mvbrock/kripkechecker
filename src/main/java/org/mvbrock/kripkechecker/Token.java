package org.mvbrock.kripkechecker;

public class Token {

	private TokenId id;
	private String labelValue;
	private int position;

	public Token(TokenId nId) {
		setId(nId);
		setLabel("");
		setPosition(-1);
	}

	public Token(TokenId nId, int nPosition) {
		setId(nId);
		setLabel("");
		setPosition(nPosition);
	}

	public Token(TokenId nId, int nPosition, String nLabelValue) {
		setId(nId);
		setLabel(nLabelValue);
		setPosition(nPosition);
	}
	
	public TokenId getId() {
		return id;
	};

	public void setId(TokenId nId) {
		id = nId;
	}

	public String getLabel() {
		return labelValue;
	}

	public void setLabel(String nLabelValue) {
		labelValue = nLabelValue;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int nPosition) {
		position = nPosition;
	}	
}
