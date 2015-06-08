package edu.br.ufrpe.uag.compiler.model.lexical;


public class Token extends Terminal {
	
//	private final Terminal terminal;
	private final int id;
	private final String expression;
	private final int row;

	/**
	 * construtor do Token
	 * @param terminal
	 * @param expression
	 */
	public Token(Terminal terminal, String expression, int row) {
		super(terminal.getId(), terminal.getRegularExpression(), terminal.getName());
		this.id = this.generateId();
//		this.terminal = terminal;
		this.expression = expression;
		this.row = row;
		
	}

//	public Terminal getTerminal() {
//		return terminal;
//	}

	public int getId() {
		return id;
	}

	public String getExpression() {
		return expression;
	}
	
	@Override
	public String toString(){
		return "<"+this.getName()+", "+id+" '"+expression+"'>";
	}
	
	public boolean equals(Object o){
		if (o instanceof Terminal){
			Terminal terminalThis = (Terminal) this;
			return o.equals(terminalThis);
		} else {
			Token tokenO = (Token) o;
			return this.getExpression().equals(tokenO.getExpression());
		}
	}

	public int getRow() {
		return row;
	}
	
	

}
