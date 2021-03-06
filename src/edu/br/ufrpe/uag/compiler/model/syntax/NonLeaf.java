package edu.br.ufrpe.uag.compiler.model.syntax;

import java.util.ArrayList;
import java.util.List;

import edu.br.ufrpe.uag.compiler.exceptions.SemanticException;

public class NonLeaf extends SyntaxNode{
	private Production production;
	private List<SyntaxNode> children;
	private NonTerminal nonTerminal;

	public NonLeaf(NonLeaf node, NonTerminal nonTerminal, Production production) {
		super(node);
		this.nonTerminal = nonTerminal;
		this.production = production;
		this.children = new ArrayList<SyntaxNode>();
	}
	
	public NonLeaf(NonLeaf node, NonTerminal nonTerminal) {
		super(node);
		this.nonTerminal = nonTerminal;
		this.children = new ArrayList<SyntaxNode>();
	}
	
	public String getWriteJava(int position){
		NonLeaf child = (NonLeaf)this.getChildren().get(position);
		return child.getProduction().getSemanticAction().writeJava(child);
	}
	
	public int getTokenRow(){
		Leaf child = (Leaf)this.getChildren().get(0);
		return child.getToken().getRow();
	}
	
	public String getTokenExpression(int position){
		return ((Leaf)this.getChildren().get(position)).getToken().getExpression();
	}
	
	public void doChildAction(int childPosition, Object object) throws SemanticException{
		NonLeaf nonLeaf = (NonLeaf)this.getChildren().get(childPosition);
		nonLeaf.getProduction().getSemanticAction().doAction(nonLeaf, object);
	}
	
	public boolean add(SyntaxNode node){
		return children.add(node);
	}

	public Production getProduction() {
		return production;
	}

	public void setProduction(Production production) {
		this.production = production;
	}

	public List<SyntaxNode> getChildren() {
		return children;
	}

	public void setChildren(List<SyntaxNode> children) {
		this.children = children;
	}
	
	public String toString(){
		String s = "";
		for(SyntaxNode node : children){
			s = s + node;
		}
		return s;
	}

	public NonTerminal getNonTerminal() {
		return nonTerminal;
	}

	public void setNonTerminal(NonTerminal nonTerminal) {
		this.nonTerminal = nonTerminal;
	}
}
