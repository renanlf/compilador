package edu.br.ufrpe.uag.compiler.model;


public class Token {
	
	private final TokenType tokenType;
	private final int id;
	private final String expression;
	
	public Token(TokenType tokenType,
			String expression) {
		
		this.id = tokenType.generateId();
		this.tokenType = tokenType;
		this.expression = expression;
		
	}

	public TokenType getTokenType() {
		return tokenType;
	}

	public int getId() {
		return id;
	}

	public String getExpression() {
		return expression;
	}
	
	@Override
	public String toString(){
		return "<"+tokenType.getTypeName()+", "+id+" '"+expression+"'>";
	}
	
	

}
