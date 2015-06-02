package edu.br.ufrpe.uag.compiler.exceptions;

public class SemanticException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7779464263412161512L;

	public SemanticException(String message){
		super("SemanticException: "+message);
	}
}
