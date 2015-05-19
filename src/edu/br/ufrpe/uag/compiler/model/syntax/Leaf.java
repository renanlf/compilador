package edu.br.ufrpe.uag.compiler.model.syntax;

import edu.br.ufrpe.uag.compiler.model.lexical.Token;

public class Leaf extends SyntaxNode {
	private Token token;

	public Leaf(NonLeaf node, Token token) {
		super(node);
		this.token = token;
	}

	public Token getToken() {
		return token;
	}

	public void setToken(Token token) {
		this.token = token;
	}
	
	public String toString(){
		String s = this.token.getExpression();
		NonLeaf nodeAnt = getNodeAnt();
		while(nodeAnt != null){
			s = s + " " +nodeAnt.getNonTerminal().getName();
			nodeAnt = nodeAnt.getNodeAnt();
		}			
		return s+"\n";
	}
}
