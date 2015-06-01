package edu.br.ufrpe.uag.compiler.exceptions;

import edu.br.ufrpe.uag.compiler.model.syntax.NonTerminal;

public class NonTerminalEmptyException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NonTerminalEmptyException(NonTerminal n){
		super("NonTerminal "+n.getName()+" não possui produções!");
	}
}
