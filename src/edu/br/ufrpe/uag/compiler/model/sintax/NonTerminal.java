package edu.br.ufrpe.uag.compiler.model.sintax;

import java.util.ArrayList;
import java.util.List;

import edu.br.ufrpe.uag.compiler.model.AntTerminal;
import edu.br.ufrpe.uag.compiler.model.lexical.Terminal;

public class NonTerminal implements AntTerminal{
	private final String name;
	private final List<Production> productions;

	public NonTerminal(String name, List<Production> productions) {
		super();
		this.name = name;
		this.productions = productions;
	}
	
	public NonTerminal(String name){
		this.name = name;
		this.productions = new ArrayList<Production>();
	}

	public List<Production> getProductions() {
		return productions;
	}

	@Override
	public Production and(AntTerminal a) {
		Production p = new Production();
		p.getAntTerminals().add(this);
		p.getAntTerminals().add(a);
		return p;
	}

	public void addProduction(Production p) {
		productions.add(p);		
	}
	
	public void addProduction(Terminal t) {
		Production p = new Production();
		p.getAntTerminals().add(t);
		productions.add(p);
	}
	
	public String toString(){
		String s = "<"+name+"> ::= ";
		for(int i = 0; i < productions.size(); i++){
			Production p = productions.get(i);
			if(i > 0){
				s = s + "\n   |" + p;
			} else{
				s = s + p;
			}
		}
		return s;
	}

	public String getName() {
		return name;
	}


}
