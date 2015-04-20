package edu.br.ufrpe.uag.compiler.model.lexical;


public class Token {
	
	private final Terminal terminal;
	private final int id;
	private final String expression;
	
	/**
	 * construtor do Token
	 * @param terminal
	 * @param expression
	 */
	public Token(Terminal terminal,
			String expression) {
		
		this.id = terminal.generateId();
		this.terminal = terminal;
		this.expression = expression;
		
	}

	public Terminal getTerminal() {
		return terminal;
	}

	public int getId() {
		return id;
	}

	public String getExpression() {
		return expression;
	}
	
	@Override
	public String toString(){
		return "<"+terminal.getName()+", "+id+" '"+expression+"'>";
	}
	
	

}
