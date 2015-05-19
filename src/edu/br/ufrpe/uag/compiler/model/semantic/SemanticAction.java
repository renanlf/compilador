package edu.br.ufrpe.uag.compiler.model.semantic;

import edu.br.ufrpe.uag.compiler.model.syntax.SyntaxNode;

public interface SemanticAction {
	public void doAction(SyntaxNode node);
	public String writeJava(SyntaxNode node);
}
