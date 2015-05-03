package edu.br.ufrpe.uag.compiler.analyzers;

import java.util.ArrayList;
import java.util.List;

import edu.br.ufrpe.uag.compiler.exceptions.NonTerminalEmpty;
import edu.br.ufrpe.uag.compiler.exceptions.SintaxException;
import edu.br.ufrpe.uag.compiler.exceptions.TerminalNotFoundException;
import edu.br.ufrpe.uag.compiler.model.AntTerminal;
import edu.br.ufrpe.uag.compiler.model.lexical.Terminal;
import edu.br.ufrpe.uag.compiler.model.lexical.Token;
import edu.br.ufrpe.uag.compiler.model.sintax.NonTerminal;
import edu.br.ufrpe.uag.compiler.model.sintax.Production;
import edu.br.ufrpe.uag.compiler.model.sintax.SintaxStack;

public class SintaxAnalyzer {
	private final List<NonTerminal> nonTerminals;
	private final LexicalAnalyzer lexicalAnalyzer;

	public SintaxAnalyzer(LexicalAnalyzer lexicalAnalyzer) {
		super();
		this.lexicalAnalyzer = lexicalAnalyzer;
		this.nonTerminals = new ArrayList<NonTerminal>();
	}

	public void parse() throws SintaxException, TerminalNotFoundException, NonTerminalEmpty {
		Token token = lexicalAnalyzer.getNextToken();
		SintaxStack stack = new SintaxStack();
		stack.add(nonTerminals.get(0));
		while (token != null) {
			Terminal t = token.getTerminal();
			AntTerminal a = stack.getLast();
			if (a instanceof Terminal) {
				Terminal t2 = (Terminal) a;
				if (t.equals(t2)) {
					stack.pop();
					token = lexicalAnalyzer.getNextToken();
				} else {
					throw new SintaxException(t2, lexicalAnalyzer.getRow(), token);
				}
			} else {
				NonTerminal n = (NonTerminal) a;
				Production p;
				if(n.getProductions().size() == 0){
					throw new NonTerminalEmpty(n);
				}
				p = n.getProduction(t);
				
				if (p == null) {
					if (n.haveBlank()) {
//						token = lexicalAnalyzer.getNextToken();
						stack.pop();
						continue;
					} else {
						throw new TerminalNotFoundException(n,
								lexicalAnalyzer.getRow());
					}
				}
				stack.pop();
				for (int i = p.getAntTerminals().size() - 1; i >= 0; i--) {
					stack.add(p.getAntTerminals().get(i));
				}

			}
		}
	}

	public boolean addNonTerminal(NonTerminal n) {
		return nonTerminals.add(n);
	}

	public List<NonTerminal> getNonTerminals() {
		return nonTerminals;
	}
}
