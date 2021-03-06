package edu.br.ufrpe.uag.compiler.model.semantic;

import java.util.ArrayList;
import java.util.List;

public class Funcao extends Definicao{
	private List<Parametro> parametros;
	private int position = 0;
	private boolean haveRetorno = false;
	
	public Funcao(String variavel, Tipo tipo){
		super(variavel, tipo);
		this.parametros = new ArrayList<Parametro>();
	}

	public Funcao(String variavel, Tipo tipo, List<Parametro> parametros) {
		super(variavel, tipo);
		this.parametros = parametros;
	}
	
	public Parametro getParametro(){
		if(position < parametros.size()){
			return parametros.get(position);
		} else {
			return null;
		}
	}
	
	public void updatePosition(){
		if(position == parametros.size()){
			position = -1;
		} else {
			position += 1;
		}
	}

	public List<Parametro> getParametros() {
		return parametros;
	}

	public void setParametros(List<Parametro> parametros) {
		this.parametros = parametros;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public boolean isHaveRetorno() {
		return haveRetorno;
	}

	public void setHaveRetorno(boolean haveRetorno) {
		this.haveRetorno = haveRetorno;
	}


	
}
