package edu.br.ufrpe.uag.compiler.model.semantic;

import java.util.ArrayList;
import java.util.List;

public class Funcao extends Definicao{
	private List<Parametro> parametros;
	
	public Funcao(String variavel, Tipo tipo){
		super(variavel, tipo);
		this.parametros = new ArrayList<Parametro>();
	}

	public Funcao(String variavel, Tipo tipo, List<Parametro> parametros) {
		super(variavel, tipo);
		this.parametros = parametros;
	}

	public List<Parametro> getParametros() {
		return parametros;
	}

	public void setParametros(List<Parametro> parametros) {
		this.parametros = parametros;
	}

	
}
