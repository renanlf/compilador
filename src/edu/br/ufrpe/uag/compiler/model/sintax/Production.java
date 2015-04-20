package edu.br.ufrpe.uag.compiler.model.sintax;

import java.util.ArrayList;
import java.util.List;

import edu.br.ufrpe.uag.compiler.model.AntTerminal;

public class Production {
	private final List<AntTerminal> antTerminals;

	public Production(){
		this.antTerminals = new ArrayList<AntTerminal>();
	}
	
	public Production(List<AntTerminal> antTerminals) {
		super();
		this.antTerminals = antTerminals;
	}

	public List<AntTerminal> getAntTerminals() {
		return antTerminals;
	}
	
	public Production and(Production p){
		List<AntTerminal> antTerminalP = p.getAntTerminals();
		for(AntTerminal ant : antTerminalP){
			antTerminals.add(ant);
		}
		return this;
	}
	
	public Production and(AntTerminal t){
		Production p = new Production(antTerminals);
		p.getAntTerminals().add(t);
		return p;
	}
	
	public String toString(){
		String s = "";
		for(AntTerminal a : antTerminals){
			s = s + "<"+a.getName()+">";
		}
		return s;
	}
}
