package edu.br.ufrpe.uag.compiler.analyzers;

import java.util.ArrayList;
import java.util.List;

import edu.br.ufrpe.uag.compiler.model.semantic.Definicao;

public class SemanticAnalyzer {
	
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

	public boolean isExistsExecuta() {
		return existsExecuta;
	}

	public void setExistsExecuta(boolean existsExecuta) {
		this.existsExecuta = existsExecuta;
	}
}
