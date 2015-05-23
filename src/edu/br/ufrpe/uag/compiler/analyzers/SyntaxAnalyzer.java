package edu.br.ufrpe.uag.compiler.analyzers;

import java.util.ArrayList;
import java.util.List;

import edu.br.ufrpe.uag.compiler.exceptions.NonTerminalEmpty;
import edu.br.ufrpe.uag.compiler.exceptions.SyntaxException;
import edu.br.ufrpe.uag.compiler.exceptions.TerminalNotFoundException;
import edu.br.ufrpe.uag.compiler.model.AntTerminal;
import edu.br.ufrpe.uag.compiler.model.lexical.Terminal;
import edu.br.ufrpe.uag.compiler.model.lexical.Token;
import edu.br.ufrpe.uag.compiler.model.syntax.Leaf;
import edu.br.ufrpe.uag.compiler.model.syntax.NonLeaf;
import edu.br.ufrpe.uag.compiler.model.syntax.NonTerminal;
import edu.br.ufrpe.uag.compiler.model.syntax.Production;
import edu.br.ufrpe.uag.compiler.model.syntax.SyntaxNode;
import edu.br.ufrpe.uag.compiler.model.syntax.SyntaxStack;

public class SyntaxAnalyzer {
	private final List<NonTerminal> nonTerminals;
	private final LexicalAnalyzer lexicalAnalyzer;
	private final List<Token> symbolTable;

	public SyntaxAnalyzer(LexicalAnalyzer lexicalAnalyzer) {
		super();
		this.lexicalAnalyzer = lexicalAnalyzer;
		this.nonTerminals = new ArrayList<NonTerminal>();
		this.symbolTable = new ArrayList<Token>();
	}

	public SyntaxNode parse() throws SyntaxException,
			TerminalNotFoundException, NonTerminalEmpty {
		
		NonLeaf root = new NonLeaf(null, nonTerminals.get(0));
		NonLeaf current = root;
		Token token = lexicalAnalyzer.getNextToken();
		SyntaxStack stack = new SyntaxStack();
		stack.add(nonTerminals.get(0));
		while (token != null) {
			// Terminal t = token.getTerminal();
			AntTerminal a = stack.getLast();
			if (a instanceof Terminal) {
				Terminal t2 = (Terminal) a;
				if (token.equals(t2)) {
					stack.pop();
					while (current.getChildren().size() == current.getProduction().getAntTerminals().size()) {
							current = current.getNodeAnt();
					}
					current.add(new Leaf(current, token));
					symbolTable.add(token);
					token = lexicalAnalyzer.getNextToken();
				} else {
					throw new SyntaxException(t2, lexicalAnalyzer.getRow(),
							token);
				}
			} else {
				NonTerminal n = (NonTerminal) a;
				Production p;
				if (n.getProductions().size() == 0) {
					throw new NonTerminalEmpty(n);
				}
				p = n.getProduction(token);
				if (p == null) {
					if (n.haveBlank()) {
						// token = lexicalAnalyzer.getNextToken();
						stack.pop();
						current.add(new Leaf(current, new Token(Token.BLANK,
								"empty")));
						continue;
					} else {
						throw new TerminalNotFoundException(n,
								lexicalAnalyzer.getRow(), token);
					}
				}
				if (current.getProduction() == null) {
					current.setProduction(p);
				} else {
					while (current.getChildren().size() == current
							.getProduction().getAntTerminals().size()) {
							current = current.getNodeAnt();
					}
					NonLeaf newNonLeaf = new NonLeaf(current, n, p);
					current.add(newNonLeaf);
					current = newNonLeaf;
				}
				stack.pop();
				for (int i = p.getAntTerminals().size() - 1; i >= 0; i--) {
					stack.add(p.getAntTerminals().get(i));
				}

			}
		}
		return root;
	}

	public boolean addNonTerminal(NonTerminal n) {
		return nonTerminals.add(n);
	}

	public List<NonTerminal> getNonTerminals() {
		return nonTerminals;
	}
}
