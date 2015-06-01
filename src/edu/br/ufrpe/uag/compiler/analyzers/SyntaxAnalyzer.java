package edu.br.ufrpe.uag.compiler.analyzers;

import java.util.ArrayList;
import java.util.List;

import edu.br.ufrpe.uag.compiler.exceptions.NonTerminalEmptyException;
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

	public SyntaxAnalyzer(LexicalAnalyzer lexicalAnalyzer) {
		super();
		this.lexicalAnalyzer = lexicalAnalyzer;
		this.nonTerminals = new ArrayList<NonTerminal>();
	}

	public SyntaxNode parse() throws SyntaxException,
			TerminalNotFoundException, NonTerminalEmptyException {

		NonLeaf root = new NonLeaf(null, nonTerminals.get(0));
		NonLeaf current = root;
		Production p;
		Token token = lexicalAnalyzer.getNextToken();
		SyntaxStack stack = new SyntaxStack();
		stack.add(nonTerminals.get(0));
		//enquanto a pilha tiver conteudo
		while (stack.size() > 0) {
			//se ainda estiver consumindo tokens
			if (token != null) {
//				System.out.println(token);
				AntTerminal a = stack.getLast();
				//se o topo da pilha for um terminal
				if (a instanceof Terminal) {
					Terminal t2 = (Terminal) a;
					if (token.equals(t2)) {
						stack.pop();
						while (current.getChildren().size() == current
								.getProduction().getAntTerminals().size()) {
							current = current.getNodeAnt();
						}
						current.add(new Leaf(current, token));
						token = lexicalAnalyzer.getNextToken();
					} else {
						throw new SyntaxException(t2, lexicalAnalyzer.getRow(),
								token);
					}
					//se o topo da pilha for um NonTerminal
				} else {
					NonTerminal n = (NonTerminal) a;
					if (n.getProductions().size() == 0) {
						throw new NonTerminalEmptyException(n);
					}
					p = n.getProduction(token);
					//se não existir uma producao com o token no inicio
					if (p == null) {
						//se o NonTerminal possuir uma produção para vazio(blank)
						if (n.haveBlank()) {
							// token = lexicalAnalyzer.getNextToken();
							p = n.getProduction(Terminal.BLANK);
							while (current.getChildren().size() == current
									.getProduction().getAntTerminals().size()) {
								current = current.getNodeAnt();
							}
							NonLeaf nonTerminalBlank = new NonLeaf(current, n,
									p);

							Leaf blank = new Leaf(nonTerminalBlank, new Token(
									Terminal.BLANK, "empty"));
							nonTerminalBlank.add(blank);

							current.add(nonTerminalBlank);

							stack.pop();
							continue;
							//caso o NonTerminal não possua producao para vazio
						} else {
							throw new TerminalNotFoundException(n,
									lexicalAnalyzer.getRow(), token);
						}
					}
					//tratando quando o current for o root
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
			} else {
				while (current.getChildren().size() == current.getProduction()
						.getAntTerminals().size()) {
					current = current.getNodeAnt();
				}
				NonTerminal lastStack = (NonTerminal) stack.getLast();
				NonLeaf last = new NonLeaf(current, lastStack);
				current.add(last);
				current = last;
				if (lastStack.haveBlank()) {
					p = lastStack.getProduction(Terminal.BLANK);
					current.setProduction(p);
					current.add(new Leaf(current, new Token(Terminal.BLANK,
							"empty")));
					stack.pop();
				} else {
					System.out.println(current.getNonTerminal().getName());
					throw new TerminalNotFoundException(
							current.getNonTerminal(), lexicalAnalyzer.getRow(),
							new Token(Terminal.EOF, "$"));
				}
			}
		}
		if(token == null){
			return root;
		} else {
			throw new SyntaxException(token, lexicalAnalyzer.getRow(), token);
		}
	}

	public boolean addNonTerminal(NonTerminal n) {
		return nonTerminals.add(n);
	}

	public List<NonTerminal> getNonTerminals() {
		return nonTerminals;
	}
}