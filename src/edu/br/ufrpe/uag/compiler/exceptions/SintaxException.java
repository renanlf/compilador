package edu.br.ufrpe.uag.compiler.exceptions;

import edu.br.ufrpe.uag.compiler.model.lexical.Terminal;
import edu.br.ufrpe.uag.compiler.model.lexical.Token;

public class SintaxException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SintaxException(Terminal t, int i, Token token){
		super("Expected Terminal "+t.getName()+" in row "+(i+1)+" but have "+token.getExpression());
	}
}
