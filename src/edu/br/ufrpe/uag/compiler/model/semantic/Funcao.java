package edu.br.ufrpe.uag.compiler.model.semantic;

import java.util.ArrayList;
import java.util.List;

public class Funcao extends Definicao{
	private List<Argumento> argumentos;
	
	public Funcao(String variavel, Tipo tipo){
		super(variavel, tipo);
		this.argumentos = new ArrayList<Argumento>();
	}

	public Funcao(String variavel, Tipo tipo, List<Argumento> argumentos) {
		super(variavel, tipo);
		this.argumentos = argumentos;
	}

	public List<Argumento> getArgumentos() {
		return argumentos;
	}

	public void setArgumentos(List<Argumento> argumentos) {
		this.argumentos = argumentos;
	}
	
}
