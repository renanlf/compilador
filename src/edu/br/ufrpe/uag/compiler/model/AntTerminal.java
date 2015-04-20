package edu.br.ufrpe.uag.compiler.model;

import edu.br.ufrpe.uag.compiler.model.sintax.Production;
/**
 * Interface implementada por Terminais e Não Terminais.
 * @author renan
 *
 */
public interface AntTerminal {
	public Production and(AntTerminal a);
	public String getName();
}
