package edu.br.ufrpe.uag.compiler.music;

import edu.br.ufrpe.uag.compiler.analyzers.LexicalAnalyzer;
import edu.br.ufrpe.uag.compiler.analyzers.SemanticAnalyzer;
import edu.br.ufrpe.uag.compiler.analyzers.SyntaxAnalyzer;
import edu.br.ufrpe.uag.compiler.exceptions.NonTerminalEmptyException;
import edu.br.ufrpe.uag.compiler.exceptions.SyntaxException;
import edu.br.ufrpe.uag.compiler.exceptions.TerminalNotFoundException;
import edu.br.ufrpe.uag.compiler.model.lexical.Terminal;
import edu.br.ufrpe.uag.compiler.model.lexical.Token;
import edu.br.ufrpe.uag.compiler.model.syntax.NonTerminal;
import edu.br.ufrpe.uag.compiler.model.syntax.SyntaxNode;

public class Compiler {
	private static LexicalAnalyzer lexicalAnalyzer;
	private static SyntaxAnalyzer syntaxAnalyzer;
	private static SemanticAnalyzer semanticAnalyzer;

	public static String compile(String source) throws SyntaxException, TerminalNotFoundException, NonTerminalEmptyException {
		//INICIALIZANDO TERMINAIS
		Terminal NOTA = new Terminal(0, "[CDEFGAB]", "NOTA");
		Terminal CLAVE = new Terminal(1, "Sol|Fa", "CLAVE");
		Terminal COMPASSO = new Terminal(2, "[234]/4", "COMPASSO");
		Terminal FIG_SOM = new Terminal(3, "[s]*[bmcf]", "FIG_SOM");
		Terminal ACIDENTE = new Terminal(4, "[#b]", "ACIDENTE");
		Terminal ESPACO = new Terminal(5, " ", "ESPACO");
		Terminal TAB = new Terminal(6, "\t", "TAB");
		Terminal QUEBRA = new Terminal(7, "\n", "QUEBRA");
		Terminal PARTITURA = new Terminal(8, "partitura", "PARTITURA");
		Terminal ABRE_PAR = new Terminal(9, "\\(", "ABRE_PAR");
		Terminal FECHA_PAR = new Terminal(10, "\\)", "FECHA_PAR");
		Terminal VIRGULA = new Terminal(11, ",", "VIRGULA");
		Terminal ACORDE = new Terminal(12, "acorde", "ACORDE");
		Terminal REPETICAO = new Terminal(13, "repeticao", "REPETICAO");
		Terminal ASTERISCO = new Terminal(14, "\\*", "ASTERISCO");
		
		//INICIALIZANDO ANALISADOR LEXICO
		lexicalAnalyzer = new LexicalAnalyzer(source);
		
		//ADICIONANDO TERMINAIS AO ANALISADOR LÈXICO
		lexicalAnalyzer.addTerminal(NOTA);
		lexicalAnalyzer.addTerminal(CLAVE);
		lexicalAnalyzer.addTerminal(COMPASSO);
		lexicalAnalyzer.addTerminal(FIG_SOM);
		lexicalAnalyzer.addTerminal(ACIDENTE);
		lexicalAnalyzer.addTerminal(ESPACO);
		lexicalAnalyzer.addTerminal(TAB);
		lexicalAnalyzer.addTerminal(QUEBRA);
		lexicalAnalyzer.addTerminal(PARTITURA);
		lexicalAnalyzer.addTerminal(ABRE_PAR);
		lexicalAnalyzer.addTerminal(FECHA_PAR);
		lexicalAnalyzer.addTerminal(VIRGULA);
		lexicalAnalyzer.addTerminal(ACORDE);
		lexicalAnalyzer.addTerminal(REPETICAO);
		lexicalAnalyzer.addTerminal(ASTERISCO);
		
		//INICIALIZANDO NON TERMINAIS
		NonTerminal escopo = new NonTerminal("escopo");
		NonTerminal escopo_partitura = new NonTerminal("escopo_partitura");
		NonTerminal acorde_nota = new NonTerminal("acorde_nota");
		NonTerminal notas_acorde = new NonTerminal("notas_acorde");
		NonTerminal notas = new NonTerminal("notas");
		
		/**
		 * GRAMÀTICA LL(0)
		 */
		
		// <escopo> ::= <NOTA><ACIDENTE><ESPACO><PARTITURA><ABRE_PAR><CLAVE><VIRGULA><COMPASSO><FECHA_PAR><escopo_partitura>
		escopo.addProduction(NOTA.and(ACIDENTE).and(ESPACO).and(PARTITURA).and(ABRE_PAR).and(CLAVE).and(VIRGULA).and(COMPASSO).and(FECHA_PAR).and(escopo_partitura));
		
		// <escopo_partitura> ::= <QUEBRA><TAB><acorde_nota><escopo_partitura>
		escopo_partitura.addProduction(QUEBRA.and(TAB).and(acorde_nota).and(escopo_partitura));
		escopo_partitura.addProduction(Token.BLANK);
		
		// <acorde_nota> ::= <FIG_SOM><NOTA><notas>
		acorde_nota.addProduction(FIG_SOM.and(NOTA).and(notas));
		// <acorde_nota> ::= <ACORDE><notas_acorde>
		acorde_nota.addProduction(ACORDE.and(notas_acorde));
		acorde_nota.addProduction(REPETICAO);
		acorde_nota.addProduction(ASTERISCO);
		
		// <notas_acorde> ::= <ESPACO><FIG_SOM><NOTA><notas_acorde>
		notas_acorde.addProduction(ESPACO.and(FIG_SOM).and(NOTA).and(notas_acorde));
		notas_acorde.addProduction(Token.BLANK);
		
		// <notas> ::= <ESPACO><FIG_SOM><NOTA><notas>
		notas.addProduction(ESPACO.and(FIG_SOM).and(NOTA).and(notas));
		notas.addProduction(Token.BLANK);
		
		/**
		 * INSTANCIANDO ANALISADOR SINTÁTICO
		 */
		
		syntaxAnalyzer = new SyntaxAnalyzer(lexicalAnalyzer);
		
		//ADICIONANDO NON TERMINAIS AO ANALISADOR SINTÁTICO
		syntaxAnalyzer.addNonTerminal(escopo);
		syntaxAnalyzer.addNonTerminal(escopo_partitura);
		syntaxAnalyzer.addNonTerminal(acorde_nota);
		syntaxAnalyzer.addNonTerminal(notas_acorde);
		syntaxAnalyzer.addNonTerminal(notas);
		
		//PEGANDO A ARVORE SINTATICA A PARTIR DA RAIZ
		
		SyntaxNode root = syntaxAnalyzer.parse();
		
		return root.toString();
		
		
	}
	
	public static void main(String[] args){
		String s = "C# partitura(Sol,3/4)\n"
				+ "	smC sbD sbC\n"
				+ "	sbG smG\n"
				+ "	acorde smG sbF	sbG";
		try {
			System.out.println(compile(s));
		} catch (SyntaxException | TerminalNotFoundException
				| NonTerminalEmptyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
