package edu.br.ufrpe.uag.compiler.analyzers;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.br.ufrpe.uag.compiler.model.Token;
import edu.br.ufrpe.uag.compiler.model.TokenType;

public class LexicalAnalyzer {
	private final ArrayList<TokenType> tokenTypes;
	private final String[] sequence;
	private int row;
	private String sequenceRow;

	public LexicalAnalyzer(ArrayList<TokenType> arrayList, String string) {
		super();
		this.tokenTypes = arrayList;
		this.sequence = string.split("\n");
		this.row = 0;
		this.sequenceRow = new String(sequence[row]);
	}
	
	public Token getNextToken(){
		
		if(sequenceRow.length() > 0){
			for(TokenType tokenType : tokenTypes){
				Matcher m = tokenType.getRegularExpression().matcher(sequenceRow);
				if(m.find()){
					String expression = m.group().trim();
					sequenceRow = m.replaceFirst("");
					while(sequenceRow.startsWith(" ")){
						sequenceRow = sequenceRow.replaceFirst(" ", "");
					}
					return new Token(tokenType, expression);
				}
			}
			return null;
		} else {
			row++;
			if(row > sequence.length-1) return null;
			sequenceRow = new String(sequence[row]);
			return getNextToken();
		}
	}
	
	public boolean addTokenType(int id, String regularExpression, String name){
		TokenType tokenType = new TokenType(id, Pattern.compile("^("+regularExpression+")"), name);
		return tokenTypes.add(tokenType);
	}

	public ArrayList<TokenType> getTokenTypes() {
		return tokenTypes;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getSequenceRow() {
		return sequenceRow;
	}

	public void setSequenceRow(String sequenceRow) {
		this.sequenceRow = sequenceRow;
	}
}
