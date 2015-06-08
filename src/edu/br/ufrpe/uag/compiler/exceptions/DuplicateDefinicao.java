package edu.br.ufrpe.uag.compiler.exceptions;

public class DuplicateDefinicao extends SemanticException {

	public DuplicateDefinicao(int row, String message) {
		super(row, "Identificador "+message+" já está sendo utilizado.");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3185914649568201081L;

}
