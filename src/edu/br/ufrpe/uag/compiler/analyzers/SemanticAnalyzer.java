package edu.br.ufrpe.uag.compiler.analyzers;

import java.util.ArrayList;
import java.util.List;

import edu.br.ufrpe.uag.compiler.model.semantic.Definicao;
import edu.br.ufrpe.uag.compiler.model.syntax.SyntaxTree;

public class SemanticAnalyzer {
	private SyntaxTree syntaxTree;
	
	private List<Definicao> definicoes;
	private boolean existsExecuta;

	public SemanticAnalyzer(){
		this.definicoes = new ArrayList<Definicao>();
		this.existsExecuta = false;
	}

	public List<Definicao> getDefinicoes() {
		return definicoes;
	}

	public void setDefinicoes(List<Definicao> definicoes) {
		this.definicoes = definicoes;
	}

	public SyntaxTree getSyntaxTree() {
		return syntaxTree;
	}

	public void setSyntaxTree(SyntaxTree syntaxTree) {
		this.syntaxTree = syntaxTree;
	}

	public boolean isExistsExecuta() {
		return existsExecuta;
	}

	public void setExistsExecuta(boolean existsExecuta) {
		this.existsExecuta = existsExecuta;
	}
}
