package edu.br.ufrpe.uag.compiler.model.semantic;

public class Parametro {
	private Definicao definicao;

	public Parametro(Definicao definicao) {
		super();
		this.definicao = definicao;
	}

	public Definicao getDefinicao() {
		return definicao;
	}

	public void setDefinicao(Definicao definicao) {
		this.definicao = definicao;
	}
}
