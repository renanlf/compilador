package edu.br.ufrpe.uag.compiler.exceptions;

import edu.br.ufrpe.uag.compiler.model.lexical.Terminal;
import edu.br.ufrpe.uag.compiler.model.lexical.Token;
import edu.br.ufrpe.uag.compiler.model.sintax.NonTerminal;
import edu.br.ufrpe.uag.compiler.model.sintax.Production;

public class TerminalNotFoundException extends Exception {

	/**
	 * 
	 */
	private final String output;
	private static final long serialVersionUID = 1L;
	
	public TerminalNotFoundException(NonTerminal n, int i, Token token){
		String o = "Era esperado:";
		for(Production p : n.getProductions()){
			Terminal first = (Terminal)p.getAntTerminals().get(0);
			o = o+" "+first.getName();
		}
		this.output = o+" na linha "+(i+1)+" mas foi encontrado \""+token.getExpression()+"\"";
	}

	public String getOutput() {
		return output;
	}

}
