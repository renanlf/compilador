package edu.br.ufrpe.uag.compiler.exceptions;

import edu.br.ufrpe.uag.compiler.model.lexical.Terminal;
import edu.br.ufrpe.uag.compiler.model.lexical.Token;

public class SyntaxException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SyntaxException(Terminal t, int i, Token token){
		super("SyntaxException: Era esperado um token do tipo "+t.getName()+" na linha "+(i+1)+" mas foi encontrado \""+token.getExpression()+"\"");
	}
}
