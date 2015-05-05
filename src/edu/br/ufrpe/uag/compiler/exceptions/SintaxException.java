package edu.br.ufrpe.uag.compiler.exceptions;

import edu.br.ufrpe.uag.compiler.model.lexical.Terminal;
import edu.br.ufrpe.uag.compiler.model.lexical.Token;

public class SintaxException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SintaxException(Terminal t, int i, Token token){
		super("Era experado um token do tipo "+t.getName()+" na linha "+(i+1)+" mas foi encontrado \""+token.getExpression()+"\"");
	}
}
