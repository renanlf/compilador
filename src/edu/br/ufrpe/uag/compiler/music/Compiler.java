package edu.br.ufrpe.uag.compiler.music;

import edu.br.ufrpe.uag.compiler.analyzers.LexicalAnalyzer;
import edu.br.ufrpe.uag.compiler.analyzers.SyntaxAnalyzer;
import edu.br.ufrpe.uag.compiler.exceptions.NonTerminalEmptyException;
import edu.br.ufrpe.uag.compiler.exceptions.SemanticException;
import edu.br.ufrpe.uag.compiler.exceptions.SyntaxException;
import edu.br.ufrpe.uag.compiler.exceptions.TerminalNotFoundException;
import edu.br.ufrpe.uag.compiler.model.lexical.Terminal;
import edu.br.ufrpe.uag.compiler.model.lexical.Token;
import edu.br.ufrpe.uag.compiler.model.semantic.SemanticAction;
import edu.br.ufrpe.uag.compiler.model.syntax.NonLeaf;
import edu.br.ufrpe.uag.compiler.model.syntax.NonTerminal;
import edu.br.ufrpe.uag.compiler.model.syntax.SyntaxNode;

public class Compiler {
	private static LexicalAnalyzer lexicalAnalyzer;
	private static SyntaxAnalyzer syntaxAnalyzer;

	public static String compile(String source) throws SyntaxException, TerminalNotFoundException, NonTerminalEmptyException {		
		
		//INICIALIZANDO TERMINAIS
		Terminal NOTA = new Terminal(0, "[CDEFGAB]", "NOTA");
		Terminal CLAVE = new Terminal(1, "Sol|Ha", "CLAVE");
		Terminal COMPASSO = new Terminal(2, "[234]/4", "COMPASSO");
		Terminal FIG_SOM = new Terminal(3, "[s]*[bmcf]", "FIG_SOM");
		Terminal ACIDENTE = new Terminal(4, "[#$]", "ACIDENTE");
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
		Terminal OITAVA = new Terminal(15, "[\\+|\\-][1-4]", "OITAVA");
		Terminal PERQUADRO = new Terminal(16, "=", "PERQUADRO");
		
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
		lexicalAnalyzer.addTerminal(OITAVA);
		lexicalAnalyzer.addTerminal(PERQUADRO);
		
		SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
		
		//INICIALIZANDO NON TERMINAIS
		NonTerminal escopo = new NonTerminal("escopo");
		NonTerminal escopo_partitura = new NonTerminal("escopo_partitura");
		NonTerminal acorde_nota = new NonTerminal("acorde_nota");
		NonTerminal notas_acorde = new NonTerminal("notas_acorde");
		NonTerminal notas = new NonTerminal("notas");
		NonTerminal acidentes = new NonTerminal("acidentes");
		NonTerminal oitavas = new NonTerminal("oitavas");
		NonTerminal acidentes_nota = new NonTerminal("acidentes_nota");
		
		/**
		 * GRAMÀTICA LL(0)
		 */
		
		// <escopo> ::= <NOTA><acidentes><ESPACO><PARTITURA><ABRE_PAR><CLAVE><VIRGULA><COMPASSO><FECHA_PAR><escopo_partitura>
		escopo.addProduction(NOTA.and(acidentes).and(ESPACO).and(PARTITURA).and(ABRE_PAR).and(CLAVE).and(VIRGULA).and(COMPASSO).and(FECHA_PAR).and(escopo_partitura), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String nota = node.getTokenExpression(0);
				String acidentes = node.getWriteJava(1);
				
				String tom = Converter.converterTom(nota, acidentes);
				tom = "\\generalsignature{"+tom+"}\n";
				
				String clave = node.getTokenExpression(5);
				clave = Converter.converteClave(clave);
				
				String compasso = node.getTokenExpression(7);
				//Removendo a barra
				compasso = compasso.replace("/", "");
				compasso = "\\generalmeter{\\meterfrac"+compasso+"}\n";
				String escopo_partitura = node.getWriteJava(9);
				return tom+compasso+clave+"\\startextract\n"+escopo_partitura;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) throws SemanticException {
				
			}
		});
		
		// <escopo_partitura> ::= <QUEBRA><TAB><acorde_nota><escopo_partitura>
		escopo_partitura.addProduction(QUEBRA.and(TAB).and(acorde_nota).and(escopo_partitura), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String acorde_nota = node.getWriteJava(2);
				String escopo_partitura = node.getWriteJava(3);

				if(semanticAnalyzer.isFirstLine()){
					semanticAnalyzer.setFirstLine(false);
					return acorde_nota+escopo_partitura;
				} else {
					return acorde_nota+"\n\\bar\n"+escopo_partitura;
				}
				
				
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) throws SemanticException {
				// TODO Auto-generated method stub
				
			}
		});
		// <escopo_partitura> ::= î
		escopo_partitura.addProduction(Token.BLANK, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "\n\\endextract\n";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) throws SemanticException {
				// TODO Auto-generated method stub
				
			}
		});
		
		// <acorde_nota> ::= <FIG_SOM><NOTA><acidentes_notas><oitavas><notas>
		acorde_nota.addProduction(FIG_SOM.and(NOTA).and(acidentes_nota).and(oitavas).and(notas), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String fig_som = node.getTokenExpression(0);
				String nota = node.getTokenExpression(1).toLowerCase();
				
				String acidentes_nota = node.getWriteJava(2);
				String oitavas = node.getWriteJava(3);
				String notas = node.getWriteJava(4);
				
				String fig_som_convertida = "\\Notes \\"+Converter.converteFiguraSom(fig_som);
				String nota_convertida = Converter.converteNota(nota, oitavas);
				
				return fig_som_convertida+"{"+acidentes_nota+nota_convertida+"} \\en"+notas;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) throws SemanticException {
				// TODO Auto-generated method stub
				
			}
		});
		// <acorde_nota> ::= <ACORDE><notas_acorde>
		acorde_nota.addProduction(ACORDE.and(notas_acorde));
		// <acorde_nota> ::= <REPETICAO>
		acorde_nota.addProduction(REPETICAO);
		// <acorde_nota> ::= <ASTERISCO>
		acorde_nota.addProduction(ASTERISCO);
		
		// <notas_acorde> ::= <ESPACO><FIG_SOM><NOTA><acidente_notas><oitavas><notas_acorde>
		notas_acorde.addProduction(ESPACO.and(FIG_SOM).and(NOTA).and(acidentes_nota).and(oitavas).and(notas_acorde), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String fig_som = node.getTokenExpression(1);
				String nota = node.getTokenExpression(2).toLowerCase();
				
				String acidentes_nota = node.getWriteJava(3);
				String oitavas = node.getWriteJava(4);
				String notas_acorde = node.getWriteJava(5);
				
				String fig_som_convertida = "\\Notes \\"+Converter.converteFiguraSom(fig_som);
				String nota_convertida = Converter.converteNota(nota, oitavas);
				
				return "\n\t"+fig_som_convertida+"{"+acidentes_nota+nota_convertida+"} \\en"+notas_acorde;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) throws SemanticException {
				// TODO Auto-generated method stub
				
			}
		});
		// <notas_acorde> ::= î
		notas_acorde.addProduction(Token.BLANK, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) throws SemanticException {
				// TODO Auto-generated method stub
				
			}
		});
		
		// <notas> ::= <ESPACO><FIG_SOM><NOTA><acidente_notas><oitavas><notas>
		notas.addProduction(ESPACO.and(FIG_SOM).and(NOTA).and(acidentes_nota).and(oitavas).and(notas), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String fig_som = node.getTokenExpression(1);
				String nota = node.getTokenExpression(2).toLowerCase();
				
				String acidentes_nota = node.getWriteJava(3);
				String oitavas = node.getWriteJava(4);
				String notas = node.getWriteJava(5);
				
				String fig_som_convertida = "\\Notes \\"+Converter.converteFiguraSom(fig_som);
				String nota_convertida = Converter.converteNota(nota, oitavas);
				
				return "\n\t"+fig_som_convertida+"{"+acidentes_nota+nota_convertida+"} \\en"+notas;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) throws SemanticException {
				// TODO Auto-generated method stub
				
			}
		});
		// <notas> ::= î
		notas.addProduction(Token.BLANK, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) throws SemanticException {
				// TODO Auto-generated method stub
				
			}
		});
		
		// <acidentes> ::= <ACIDENTE>
		acidentes.addProduction(ACIDENTE, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String acidente = node.getTokenExpression(0);
				return acidente;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) throws SemanticException {
				// TODO Auto-generated method stub
				
			}
		});
		// <acidentes> ::= î
		acidentes.addProduction(Token.BLANK, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) throws SemanticException {
				// TODO Auto-generated method stub
				
			}
		});
		
		// <oitavas> ::= <OITAVA>
		oitavas.addProduction(OITAVA, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String oitava = node.getTokenExpression(0);
				return oitava;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) throws SemanticException {
				// TODO Auto-generated method stub
				
			}
		});
		// <oitavas> ::= î
		oitavas.addProduction(Token.BLANK, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) throws SemanticException {
				// TODO Auto-generated method stub
				
			}
		});
		
		acidentes_nota.addProduction(ACIDENTE, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String acidente = node.getTokenExpression(0);
				if(acidente.equals("#")){
					return("^");
				} else {
					return("_");
				}
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) throws SemanticException {
				// TODO Auto-generated method stub
				
			}
		});
		
		acidentes_nota.addProduction(PERQUADRO, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "=";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) throws SemanticException {
				// TODO Auto-generated method stub
				
			}
		});
		
		acidentes_nota.addProduction(Token.BLANK, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) throws SemanticException {
				// TODO Auto-generated method stub
				
			}
		});
		
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
		syntaxAnalyzer.addNonTerminal(acidentes);
		syntaxAnalyzer.addNonTerminal(oitavas);
		syntaxAnalyzer.addNonTerminal(acidentes_nota);
		
		//PEGANDO A ARVORE SINTATICA A PARTIR DA RAIZ
		
		SyntaxNode root = syntaxAnalyzer.parse();
		String header = "\\documentclass{article}\n"+
						"\\usepackage{musixtex}\n"+
						"\\begin{document}\n"+
						"\\begin{music}\n"+
//						"\\parindent10mm\n"+
//						"\\instrumentnumber{1}\n"+
						"";
		
		String footer = "\\end{music}\n"
					  + "\\end{document}";
		return header+((NonLeaf)root).getProduction().getSemanticAction().writeJava((NonLeaf)root)+footer;
		
		
	}
	
	public static void main(String[] args){
		String s = "C partitura(Sol,3/4)\n"
				+ "	scC+1 cD fC\n"
				+ "	sfA+2 fG\n"
				+ "	cG cA+1";
//				+ "	acorde smG sbF+2 sbG";
		try {
			System.out.println(compile(s));
		} catch (SyntaxException | TerminalNotFoundException
				| NonTerminalEmptyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
