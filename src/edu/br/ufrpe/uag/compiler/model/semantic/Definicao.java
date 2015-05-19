package edu.br.ufrpe.uag.compiler.model.semantic;

public class Definicao {
	private String variavel;
	private Tipo tipo;
	public Definicao(String variavel, Tipo tipo) {
		super();
		this.variavel = variavel;
		this.tipo = tipo;
	}
	public String getVariavel() {
		return variavel;
	}
	public void setVariavel(String variavel) {
		this.variavel = variavel;
	}
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
}
