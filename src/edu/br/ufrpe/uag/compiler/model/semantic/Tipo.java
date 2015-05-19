package edu.br.ufrpe.uag.compiler.model.semantic;

public class Tipo {
	private String tipoNome;

	public Tipo(String tipoNome) {
		super();
		this.tipoNome = tipoNome;
	}

	public String getTipoNome() {
		return tipoNome;
	}

	public void setTipoNome(String tipoNome) {
		this.tipoNome = tipoNome;
	}
}
