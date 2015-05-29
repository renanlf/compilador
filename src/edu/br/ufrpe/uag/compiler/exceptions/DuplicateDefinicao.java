package edu.br.ufrpe.uag.compiler.exceptions;

public class DuplicateDefinicao extends SemanticException {

	public DuplicateDefinicao(String message) {
		super("Identificador "+message+" já está sendo utilizado.");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3185914649568201081L;

}
