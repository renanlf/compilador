package edu.br.ufrpe.uag.compiler.model.semantic;

public class Parametro extends Definicao{

	public Parametro(String variavel, Tipo tipo) {
		super(variavel, tipo);
	}
	
	public boolean equals(Object o){
		if(o instanceof Parametro || o instanceof Definicao){
			Definicao d = (Definicao)o;
			return this.getVariavel().equals(d.getVariavel());
		} else {
			return false;
		}
	}
}
