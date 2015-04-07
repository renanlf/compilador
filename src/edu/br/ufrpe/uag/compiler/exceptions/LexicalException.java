package edu.br.ufrpe.uag.compiler.exceptions;

public class LexicalException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final int row;
	private final String sequence;
	
	public LexicalException(int row, String sequenceRow){
		super("Erro na linha "+(row+1)+" com o(s) caracter(es): "+sequenceRow.split(" ")[0]);
		this.row = row;
		this.sequence = sequenceRow;
	}

	public int getRow() {
		return row;
	}

	public String getSequence() {
		return sequence;
	}

}
