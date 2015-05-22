package edu.br.ufrpe.uag.compiler.model.semantic;

import edu.br.ufrpe.uag.compiler.model.syntax.NonLeaf;

public interface SemanticAction {
	public void doAction(NonLeaf node, Object object);
	public String writeJava(NonLeaf node);
}
