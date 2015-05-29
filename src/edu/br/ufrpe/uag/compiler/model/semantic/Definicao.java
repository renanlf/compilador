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

	public boolean equals(Object o) {
		if (o instanceof Definicao) {
			Definicao d = (Definicao) o;
			return this.variavel.equals(d.variavel);
		} else {
			String variavel = (String)o;
			return this.variavel.equals(variavel);
		}
	}
}
