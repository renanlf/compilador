package edu.br.ufrpe.uag.compiler.analyzers;

import java.util.ArrayList;
import java.util.List;

import edu.br.ufrpe.uag.compiler.model.semantic.Definicao;
import edu.br.ufrpe.uag.compiler.model.semantic.Funcao;
import edu.br.ufrpe.uag.compiler.model.syntax.SyntaxTree;

public class SemanticAnalyzer {
	private SyntaxTree syntaxTree;
	
	private List<Definicao> definicoes;
	private Funcao ultimaFuncao;

	public SemanticAnalyzer(){
		this.definicoes = new ArrayList<Definicao>();
		this.ultimaFuncao = null;
	}

	public List<Definicao> getDefinicoes() {
		return definicoes;
	}

	public void setDefinicoes(List<Definicao> definicoes) {
		this.definicoes = definicoes;
	}

	public Funcao getUltimaFuncao() {
		return ultimaFuncao;
	}

	public void setUltimaFuncao(Funcao ultimaFuncao) {
		this.ultimaFuncao = ultimaFuncao;
	}
}
