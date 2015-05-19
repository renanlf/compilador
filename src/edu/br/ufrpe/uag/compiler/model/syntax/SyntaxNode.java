package edu.br.ufrpe.uag.compiler.model.syntax;

public abstract class SyntaxNode {
	private NonLeaf nodeAnt;

	public SyntaxNode(NonLeaf nodeAnt) {
		super();
		this.nodeAnt = nodeAnt;
	}

	public NonLeaf getNodeAnt() {
		return nodeAnt;
	}

	public void setNodeAnt(NonLeaf nodeAnt) {
		this.nodeAnt = nodeAnt;
	}
}
