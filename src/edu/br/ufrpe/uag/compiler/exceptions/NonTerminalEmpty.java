package edu.br.ufrpe.uag.compiler.exceptions;

import edu.br.ufrpe.uag.compiler.model.sintax.NonTerminal;

public class NonTerminalEmpty extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NonTerminalEmpty(NonTerminal n){
		super("NonTerminal "+n.getName()+" don't have any producer!");
	}
}