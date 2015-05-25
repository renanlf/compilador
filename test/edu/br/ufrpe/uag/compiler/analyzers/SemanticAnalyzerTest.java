package edu.br.ufrpe.uag.compiler.analyzers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.br.ufrpe.uag.compiler.exceptions.LexicalException;
import edu.br.ufrpe.uag.compiler.exceptions.NonTerminalEmpty;
import edu.br.ufrpe.uag.compiler.exceptions.SyntaxException;
import edu.br.ufrpe.uag.compiler.exceptions.TerminalNotFoundException;
import edu.br.ufrpe.uag.compiler.model.lexical.Terminal;
import edu.br.ufrpe.uag.compiler.model.semantic.Funcao;
import edu.br.ufrpe.uag.compiler.model.semantic.SemanticAction;
import edu.br.ufrpe.uag.compiler.model.semantic.Tipo;
import edu.br.ufrpe.uag.compiler.model.syntax.Leaf;
import edu.br.ufrpe.uag.compiler.model.syntax.NonLeaf;
import edu.br.ufrpe.uag.compiler.model.syntax.NonTerminal;
import edu.br.ufrpe.uag.compiler.model.syntax.SyntaxNode;

public class SemanticAnalyzerTest {

	@Test
	public void test() {
		//Terminais
		Terminal executa = new Terminal (0, "executa", "EXECUTA");
		Terminal abre_parenteses = new Terminal (1, "\\(", "abre_parenteses");
		Terminal fecha_parenteses = new Terminal (2, "\\)", "fecha_parenteses");
		Terminal abre_chaves = new Terminal (3, "\\{", "abre_chaves");
		Terminal fecha_chaves = new Terminal (4, "\\}", "fecha_chaves");
		Terminal INTEIRO = new Terminal (5, "inteiro", "INTEIRO");
		Terminal BOOLEANO = new Terminal (6, "booleano", "BOOLEANO");
		Terminal VAZIO = new Terminal (7, "vazio", "VAZIO");
		Terminal PONTOVIRGULA = new Terminal (8, "\\;", "PONTOVIRGULA");
		Terminal ATRIBUIDOR = new Terminal (9, "<-", "ATRIBUIDOR");
		Terminal RETORNE = new Terminal (10, "retorne", "RETORNE");
		Terminal ENQUANTO = new Terminal (11, "enquanto", "ENQUANTO");
		Terminal IMPRIMA = new Terminal (12, "imprima", "IMPRIMA");
		Terminal SENAO = new Terminal (13, "snao", "SENAO");
		Terminal SE = new Terminal (14, "se", "SE");
		Terminal VERDADEIRO = new Terminal (15, "V", "VERDADEIRO");
		Terminal FALSO = new Terminal (16, "F", "FALSO");
		Terminal PARE = new Terminal (17, "pare", "PARE");
		Terminal CONTINUE = new Terminal (18, "continue", "CONTINUE");
		Terminal MAIS = new Terminal (19, "\\+", "MAIS");
		Terminal MENOS = new Terminal (20, "\\-", "MENOS");
		Terminal VEZES = new Terminal (21, "\\*", "VEZES");
		Terminal DIVIDIDO = new Terminal (22, "\\/", "DIVIDIDO");
		Terminal MENORQ = new Terminal (23, "\\<", "MENORQ");
		Terminal MENORQIGUAL = new Terminal (24, "<=", "MENORQIGUAL");
		Terminal MAIORQ = new Terminal (25, ">", "MAIORQ");
		Terminal MAIORQIGUAL = new Terminal (26, ">=", "MAIORQIGUAL");
		Terminal DIFERENTE = new Terminal (27, "!=", "DIFERENTE");
		Terminal IGUAL = new Terminal (28, "=", "IGUAL");
		Terminal VIRGULA = new Terminal (29, ",", "VIRGULA");
		Terminal NUMEROS = new Terminal (30, "[0-9]+", "NUMEROS");
		Terminal ID = new Terminal (31, "[a-z][a-zA-Z0-9]*", "ID");
		
		LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(
//				"inteiro a;\n"
//				+ "executa(){\n"
//				+ " 	a <- 3;\n"
//				+ "}\n"
				/*+ */"inteiro soma(inteiro a, inteiro b){\n"
				+ "		inteiro c;\n"
				+ "		c <- a + b;\n"
				+ "		retorne(c);\n"
				+ "}");
		//Adicionando terminais ao analisador léxico
		lexicalAnalyzer.addTerminal(executa);
		lexicalAnalyzer.addTerminal(abre_parenteses);
		lexicalAnalyzer.addTerminal(fecha_parenteses);
		lexicalAnalyzer.addTerminal(abre_chaves);
		lexicalAnalyzer.addTerminal(fecha_chaves);
		lexicalAnalyzer.addTerminal(INTEIRO);
		lexicalAnalyzer.addTerminal(BOOLEANO);
		lexicalAnalyzer.addTerminal(VAZIO);
		lexicalAnalyzer.addTerminal(PONTOVIRGULA);
		lexicalAnalyzer.addTerminal(ATRIBUIDOR);
		lexicalAnalyzer.addTerminal(RETORNE);
		lexicalAnalyzer.addTerminal(ENQUANTO);
		lexicalAnalyzer.addTerminal(IMPRIMA);
		lexicalAnalyzer.addTerminal(SE);
		lexicalAnalyzer.addTerminal(SENAO);
		lexicalAnalyzer.addTerminal(VERDADEIRO);
		lexicalAnalyzer.addTerminal(FALSO);
		lexicalAnalyzer.addTerminal(PARE);
		lexicalAnalyzer.addTerminal(CONTINUE);
		lexicalAnalyzer.addTerminal(MAIS);
		lexicalAnalyzer.addTerminal(MENOS);
		lexicalAnalyzer.addTerminal(VEZES);
		lexicalAnalyzer.addTerminal(DIVIDIDO);
		lexicalAnalyzer.addTerminal(MENORQ);
		lexicalAnalyzer.addTerminal(MENORQIGUAL);
		lexicalAnalyzer.addTerminal(MAIORQ);
		lexicalAnalyzer.addTerminal(MAIORQIGUAL);
		lexicalAnalyzer.addTerminal(DIFERENTE);
		lexicalAnalyzer.addTerminal(IGUAL);
		lexicalAnalyzer.addTerminal(VIRGULA);
		lexicalAnalyzer.addTerminal(NUMEROS);
		lexicalAnalyzer.addTerminal(ID);
		
		SyntaxAnalyzer syntaxAnalyzer = new SyntaxAnalyzer(lexicalAnalyzer);
		SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
		//Variaveis utilizadas na gramática
		NonTerminal escopo = new NonTerminal("escopo");
		NonTerminal separa_escopo = new NonTerminal("separa_escopo");
		NonTerminal declara = new NonTerminal("declara");
		NonTerminal parametros = new NonTerminal("parametros");
		NonTerminal separa_parametros = new NonTerminal("separa_parametros");
		NonTerminal escopo_funcao = new NonTerminal("escopo_funcao");
		NonTerminal separa_escopo_funcao = new NonTerminal("separa_escopo_funcao");
		NonTerminal ID_chamada_NUMERO = new NonTerminal("ID_chamada_NUMERO");
		NonTerminal ID_chamada = new NonTerminal("ID_chamada");
		NonTerminal assinatura = new NonTerminal("assinatura");
		NonTerminal diversas_atribuicoes = new NonTerminal("diversas_atribuicoes");
		NonTerminal comparacao = new NonTerminal("comparacao");
		NonTerminal assinatura_argumentos = new NonTerminal("assinatura_argumentos");
		NonTerminal argumentos = new NonTerminal("argumentos");
		NonTerminal separa_argumentos = new NonTerminal("separa_argumentos");
		NonTerminal escopo_loop = new NonTerminal("escopo_loop");
		NonTerminal separa_escopo_loop = new NonTerminal("separa_escopo_loop");
		NonTerminal escopo_condicional = new NonTerminal("escopo_condicional");
		NonTerminal separa_escopo_condicional = new NonTerminal("separa_escopo_condicional");
		NonTerminal operacao = new NonTerminal("operacao");
		NonTerminal outra_operacao = new NonTerminal("outra_operacao");
		NonTerminal chamada_comparacao_operacao = new NonTerminal("chamada_comparacao_operacao");
		NonTerminal separa = new NonTerminal("separa");
		NonTerminal ID_chamada_NUMERO_BOOL = new NonTerminal("ID_chamada_NUMERO_BOOL");
		NonTerminal comparador = new NonTerminal("comparador");
		NonTerminal operador = new NonTerminal("operador");
		//construção da gramática
		escopo.addProduction(executa.and(abre_parenteses).and(assinatura).and(abre_chaves).and(escopo_funcao).and(fecha_chaves).and(separa_escopo), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				NonLeaf escopo_funcao = (NonLeaf)node.getChildren().get(4);
				NonLeaf separa_escopo = (NonLeaf)node.getChildren().get(6);
				return "public void main(String args[]){"+escopo_funcao.getProduction().getSemanticAction().writeJava(escopo_funcao)+"}"+escopo_funcao.getProduction().getSemanticAction().writeJava(separa_escopo);
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				Tipo tipoExecuta = new Tipo("executa");
				Funcao executa = new Funcao("executa", tipoExecuta);
				NonLeaf assinatura = (NonLeaf)node.getChildren().get(2);
				assinatura.getProduction().getSemanticAction().doAction(assinatura, executa);
			}
		});
		escopo.addProduction(INTEIRO.and(ID).and(declara).and(separa_escopo), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				Leaf id = (Leaf)node.getChildren().get(1);
				NonLeaf declara = (NonLeaf)node.getChildren().get(2);
				NonLeaf separa_escopo = (NonLeaf)node.getChildren().get(3);
				return "int "+id.getToken().getExpression()+declara.getProduction().getSemanticAction().writeJava(declara)
						+separa_escopo.getProduction().getSemanticAction().writeJava(separa_escopo);
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				Tipo tipo = new Tipo("inteiro");
				List<Object> list = new ArrayList<>();
				list.add(tipo);
				list.add(node.getChildren().get(1));
				NonLeaf declara = (NonLeaf)node.getChildren().get(2);
				declara.getProduction().getSemanticAction().doAction(declara, list);
			}
		});
		escopo.addProduction(BOOLEANO.and(ID).and(declara).and(separa_escopo), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				Leaf id = (Leaf)node.getChildren().get(1);
				NonLeaf declara = (NonLeaf)node.getChildren().get(2);
				NonLeaf separa_escopo = (NonLeaf)node.getChildren().get(3);
				return "boolean "+id.getToken().getExpression()+declara.getProduction().getSemanticAction().writeJava(declara)
						+separa_escopo.getProduction().getSemanticAction().writeJava(separa_escopo);
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				Tipo tipo = new Tipo("booleano");
				List<Object> list = new ArrayList<>();
				list.add(tipo);
				list.add(node.getChildren().get(1));
				NonLeaf declara = (NonLeaf)node.getChildren().get(2);
				declara.getProduction().getSemanticAction().doAction(declara, list);
			}
		});
		escopo.addProduction(VAZIO.and(ID).and(abre_parenteses).and(assinatura).and(abre_chaves).and(escopo_funcao).and(fecha_chaves).and(separa_escopo), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				Leaf id = (Leaf)node.getChildren().get(1);
				NonLeaf assinatura = (NonLeaf)node.getChildren().get(3);
				NonLeaf escopo_funcao = (NonLeaf)node.getChildren().get(5);
				NonLeaf separa_escopo = (NonLeaf)node.getChildren().get(7);
				Tipo tipo = new Tipo("vazio");
				Funcao funcao = new Funcao(id.getToken().getExpression(), tipo);
				semanticAnalyzer.getDefinicoes().add(funcao);
				return "void "+id.getToken().getExpression()+"("+assinatura.getProduction().getSemanticAction().writeJava(assinatura)+"{"
						+escopo_funcao.getProduction().getSemanticAction().writeJava(escopo_funcao)+"}"
						+separa_escopo.getProduction().getSemanticAction().writeJava(separa_escopo);
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				Leaf id = (Leaf)node.getChildren().get(1);
				Tipo tipo = new Tipo("vazio");
				Funcao funcao = new Funcao(id.getToken().getExpression(), tipo);
				NonLeaf assinatura = (NonLeaf)node.getChildren().get(2);
				assinatura.getProduction().getSemanticAction().doAction(assinatura, funcao);
			}
		});
		
		separa_escopo.addProduction(executa.and(abre_parenteses).and(assinatura).and(abre_chaves).and(escopo_funcao).and(fecha_chaves).and(separa_escopo), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				NonLeaf escopo_funcao = (NonLeaf)node.getChildren().get(4);
				NonLeaf separa_escopo = (NonLeaf)node.getChildren().get(6);
				return "public void main(String args[]){"+escopo_funcao.getProduction().getSemanticAction().writeJava(escopo_funcao)+"}"+escopo_funcao.getProduction().getSemanticAction().writeJava(separa_escopo);
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				Tipo tipoExecuta = new Tipo("executa");
				Funcao executa = new Funcao("executa", tipoExecuta);
				NonLeaf assinatura = (NonLeaf)node.getChildren().get(2);
				assinatura.getProduction().getSemanticAction().doAction(assinatura, executa);
			}
		});
		separa_escopo.addProduction(INTEIRO.and(ID).and(declara).and(separa_escopo), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				Leaf id = (Leaf)node.getChildren().get(1);
				NonLeaf declara = (NonLeaf)node.getChildren().get(2);
				NonLeaf separa_escopo = (NonLeaf)node.getChildren().get(3);
				return "int "+id.getToken().getExpression()+declara.getProduction().getSemanticAction().writeJava(declara)
						+separa_escopo.getProduction().getSemanticAction().writeJava(separa_escopo);
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				Tipo tipo = new Tipo("inteiro");
				List<Object> list = new ArrayList<>();
				list.add(tipo);
				list.add(node.getChildren().get(1));
				NonLeaf declara = (NonLeaf)node.getChildren().get(2);
				declara.getProduction().getSemanticAction().doAction(declara, list);
			}
		});
		separa_escopo.addProduction(BOOLEANO.and(ID).and(declara).and(separa_escopo), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				Leaf id = (Leaf)node.getChildren().get(1);
				NonLeaf declara = (NonLeaf)node.getChildren().get(2);
				NonLeaf separa_escopo = (NonLeaf)node.getChildren().get(3);
				return "boolean "+id.getToken().getExpression()+declara.getProduction().getSemanticAction().writeJava(declara)
						+separa_escopo.getProduction().getSemanticAction().writeJava(separa_escopo);
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				Tipo tipo = new Tipo("booleano");
				List<Object> list = new ArrayList<>();
				list.add(tipo);
				list.add(node.getChildren().get(1));
				NonLeaf declara = (NonLeaf)node.getChildren().get(2);
				declara.getProduction().getSemanticAction().doAction(declara, list);
			}
		});
		separa_escopo.addProduction(VAZIO.and(ID).and(abre_parenteses).and(assinatura).and(abre_chaves).and(escopo_funcao).and(fecha_chaves).and(separa_escopo), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				Leaf id = (Leaf)node.getChildren().get(1);
				NonLeaf assinatura = (NonLeaf)node.getChildren().get(3);
				NonLeaf escopo_funcao = (NonLeaf)node.getChildren().get(5);
				NonLeaf separa_escopo = (NonLeaf)node.getChildren().get(7);
				Tipo tipo = new Tipo("vazio");
				Funcao funcao = new Funcao(id.getToken().getExpression(), tipo);
				semanticAnalyzer.getDefinicoes().add(funcao);
				return "void "+id.getToken().getExpression()+"("+assinatura.getProduction().getSemanticAction().writeJava(assinatura)+"{"
						+escopo_funcao.getProduction().getSemanticAction().writeJava(escopo_funcao)+"}"
						+separa_escopo.getProduction().getSemanticAction().writeJava(separa_escopo);
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				Leaf id = (Leaf)node.getChildren().get(1);
				Tipo tipo = new Tipo("vazio");
				Funcao funcao = new Funcao(id.getToken().getExpression(), tipo);
				NonLeaf assinatura = (NonLeaf)node.getChildren().get(2);
				assinatura.getProduction().getSemanticAction().doAction(assinatura, funcao);
			}
		});
		
		separa_escopo.addProduction(Terminal.BLANK, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		declara.addProduction(abre_parenteses.and(assinatura).and(abre_chaves).and(escopo_funcao).and(fecha_chaves), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String assinatura = ((NonLeaf)node.getChildren().get(1)).getProduction().getSemanticAction().writeJava((NonLeaf)node.getChildren().get(1));
				String escopo_funcao = ((NonLeaf)node.getChildren().get(3)).getProduction().getSemanticAction().writeJava((NonLeaf)node.getChildren().get(3));
				return "("+assinatura+"{\n"+"\t"+escopo_funcao+"\n}";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		declara.addProduction(PONTOVIRGULA, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return ";";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		parametros.addProduction(INTEIRO.and(ID).and(separa_parametros), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(1);
				String separa_parametros = node.getWriteJava(2);
				return "int "+id+separa_parametros;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		parametros.addProduction(BOOLEANO.and(ID).and(separa_parametros), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(1);
				String separa_parametros = node.getWriteJava(2);
				return "boolean "+id+separa_parametros;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		separa_parametros.addProduction(VIRGULA.and(parametros), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String parametros = node.getWriteJava(1);
				return ", "+parametros;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		separa_parametros.addProduction(Terminal.BLANK, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				// TODO Auto-generated method stub
				return "";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		escopo_funcao.addProduction(INTEIRO.and(ID).and(PONTOVIRGULA).and(separa_escopo_funcao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(1);
				String separa_escopo_funcao = node.getWriteJava(3);
				return "int "+id+";"+"\n\t"+separa_escopo_funcao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		escopo_funcao.addProduction(BOOLEANO.and(ID).and(PONTOVIRGULA).and(separa_escopo_funcao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(1);
				String separa_escopo_funcao = node.getWriteJava(3);
				return "boolean "+id+";"+"\n\t"+separa_escopo_funcao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		escopo_funcao.addProduction(ID.and(ATRIBUIDOR).and(diversas_atribuicoes).and(PONTOVIRGULA).and(separa_escopo_funcao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(0);
				String diversas_atribuicoes = node.getWriteJava(2);
				String separa_escopo_funcao = node.getWriteJava(4);
				return id+" = "+diversas_atribuicoes+";\n\t"+separa_escopo_funcao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		escopo_funcao.addProduction(RETORNE.and(abre_parenteses).and(ID_chamada_NUMERO_BOOL).and(fecha_parenteses).and(PONTOVIRGULA).and(separa_escopo_funcao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO_BOOL = node.getWriteJava(2);
				String separa_escopo_funcao = node.getWriteJava(5);
				return "return("+ID_chamada_NUMERO_BOOL+");\n\t"+separa_escopo_funcao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		escopo_funcao.addProduction(ENQUANTO.and(abre_parenteses).and(comparacao).and(fecha_parenteses).and(abre_chaves).and(escopo_loop).and(fecha_chaves).and(separa_escopo_funcao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String comparacao = node.getWriteJava(2);
				String escopo_loop = node.getWriteJava(5);
				String separa_escopo_funcao = node.getWriteJava(7);
				return "while("+comparacao+"){\n\t"+escopo_loop+"\n\t}"+separa_escopo_funcao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		escopo_funcao.addProduction(IMPRIMA.and(abre_parenteses).and(ID_chamada_NUMERO_BOOL).and(fecha_parenteses).and(PONTOVIRGULA).and(separa_escopo_funcao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO_BOOL = node.getWriteJava(2);
				String separa_escopo_funcao = node.getWriteJava(5);
				return "System.out.println("+ID_chamada_NUMERO_BOOL+");"+"\n\t"+separa_escopo_funcao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		escopo_funcao.addProduction(SE.and(abre_parenteses).and(comparacao).and(fecha_parenteses).and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_funcao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String comparacao = node.getWriteJava(2);
				String escopo_condicional = node.getWriteJava(5);
				String separa_escopo_funcao = node.getWriteJava(7);
				return "if("+comparacao+"){\n\t"+escopo_condicional+"\n\t}"+separa_escopo_funcao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		escopo_funcao.addProduction(SENAO.and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_funcao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String escopo_condicional = node.getWriteJava(2);
				String separa_escopo_funcao = node.getWriteJava(4);
				return "else{\n\t"+escopo_condicional+"\n\t}"+separa_escopo_funcao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		/**
		 * separa_escopo_funcao
		 */
		
		separa_escopo_funcao.addProduction(INTEIRO.and(ID).and(PONTOVIRGULA).and(separa_escopo_funcao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(1);
				String separa_escopo_funcao = node.getWriteJava(3);
				return "int "+id+";"+"\n\t"+separa_escopo_funcao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		separa_escopo_funcao.addProduction(BOOLEANO.and(ID).and(PONTOVIRGULA).and(separa_escopo_funcao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(1);
				String separa_escopo_funcao = node.getWriteJava(3);
				return "boolean "+id+";"+"\n\t"+separa_escopo_funcao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		separa_escopo_funcao.addProduction(ID.and(ATRIBUIDOR).and(diversas_atribuicoes).and(PONTOVIRGULA).and(separa_escopo_funcao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(0);
				String diversas_atribuicoes = node.getWriteJava(2);
				String separa_escopo_funcao = node.getWriteJava(4);
				return id+" = "+diversas_atribuicoes+";\n\t"+separa_escopo_funcao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		separa_escopo_funcao.addProduction(RETORNE.and(abre_parenteses).and(ID_chamada_NUMERO_BOOL).and(fecha_parenteses).and(PONTOVIRGULA).and(separa_escopo_funcao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO_BOOL = node.getWriteJava(2);
				String separa_escopo_funcao = node.getWriteJava(5);
				return "return("+ID_chamada_NUMERO_BOOL+");\n\t"+separa_escopo_funcao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		separa_escopo_funcao.addProduction(ENQUANTO.and(abre_parenteses).and(comparacao).and(fecha_parenteses).and(abre_chaves).and(escopo_loop).and(fecha_chaves).and(separa_escopo_funcao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String comparacao = node.getWriteJava(2);
				String escopo_loop = node.getWriteJava(5);
				String separa_escopo_funcao = node.getWriteJava(7);
				return "while("+comparacao+"){\n\t"+escopo_loop+"\n\t}"+separa_escopo_funcao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		separa_escopo_funcao.addProduction(IMPRIMA.and(abre_parenteses).and(ID_chamada_NUMERO_BOOL).and(fecha_parenteses).and(PONTOVIRGULA).and(separa_escopo_funcao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO_BOOL = node.getWriteJava(2);
				String separa_escopo_funcao = node.getWriteJava(5);
				return "System.out.println("+ID_chamada_NUMERO_BOOL+");"+"\n\t"+separa_escopo_funcao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		separa_escopo_funcao.addProduction(SE.and(abre_parenteses).and(comparacao).and(fecha_parenteses).and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_funcao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String comparacao = node.getWriteJava(2);
				String escopo_condicional = node.getWriteJava(5);
				String separa_escopo_funcao = node.getWriteJava(7);
				return "if("+comparacao+"){\n\t"+escopo_condicional+"\n\t}"+separa_escopo_funcao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		separa_escopo_funcao.addProduction(SENAO.and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_funcao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String escopo_condicional = node.getWriteJava(2);
				String separa_escopo_funcao = node.getWriteJava(4);
				return "else{\n\t"+escopo_condicional+"\n\t}"+separa_escopo_funcao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		separa_escopo_funcao.addProduction(Terminal.BLANK, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		ID_chamada_NUMERO.addProduction(ID.and(ID_chamada), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(0);
				String ID_chamada = node.getWriteJava(1);
				return id+ID_chamada;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		ID_chamada_NUMERO.addProduction(NUMEROS, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String numeros = node.getTokenExpression(0);
				return numeros;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		ID_chamada.addProduction(abre_parenteses.and(assinatura_argumentos), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String assinatura_argumentos = node.getWriteJava(1);
				return "("+assinatura_argumentos;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		ID_chamada.addProduction(Terminal.BLANK, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		assinatura.addProduction(fecha_parenteses, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return ")";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		assinatura.addProduction(INTEIRO.and(ID).and(separa_parametros).and(fecha_parenteses), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(1);
				String separa_parametros = node.getWriteJava(2);
				return "int "+id+separa_parametros+")";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		assinatura.addProduction(BOOLEANO.and(ID).and(separa_parametros).and(fecha_parenteses), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(1);
				String separa_parametros = node.getWriteJava(2);
				return "boolean "+id+separa_parametros+")";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		diversas_atribuicoes.addProduction(ID.and(chamada_comparacao_operacao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(0);
				String chamada_comparacao_operacao = node.getWriteJava(1);
				return id+chamada_comparacao_operacao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		diversas_atribuicoes.addProduction(NUMEROS.and(separa), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String numeros = node.getTokenExpression(0);
				String separa = node.getWriteJava(1);
				return numeros+separa;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		diversas_atribuicoes.addProduction(BOOLEANO, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "boolean";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		comparacao.addProduction(NUMEROS.and(comparador).and(ID_chamada_NUMERO), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String numeros = node.getTokenExpression(0);
				String comparador = node.getWriteJava(1);
				String ID_chamada_NUMERO = node.getWriteJava(2);
				return numeros+comparador+ID_chamada_NUMERO;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		comparacao.addProduction(BOOLEANO, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "boolean";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		comparacao.addProduction(ID.and(ID_chamada).and(comparador).and(ID_chamada_NUMERO), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(0);
				String id_chamada = node.getWriteJava(1);
				String comparador = node.getWriteJava(2);
				String ID_chamada_NUMERO = node.getWriteJava(3);
				return id+id_chamada+comparador+ID_chamada_NUMERO;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		assinatura_argumentos.addProduction(fecha_parenteses, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return ")";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		assinatura_argumentos.addProduction(NUMEROS.and(separa_argumentos).and(fecha_parenteses), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String numeros = node.getTokenExpression(0);
				String separa_argumentos = node.getTokenExpression(1);
				return numeros+separa_argumentos+")";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		assinatura_argumentos.addProduction(BOOLEANO.and(separa_argumentos).and(fecha_parenteses), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
//				String numeros = node.getTokenExpression(0);
				String separa_argumentos = node.getTokenExpression(1);
				return "boolean"+separa_argumentos+")";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		assinatura_argumentos.addProduction(ID.and(ID_chamada).and(separa_argumentos).and(fecha_parenteses), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(0);
				String id_chamada = node.getWriteJava(1);
				String separa_argumentos = node.getWriteJava(2);
				return id+id_chamada+separa_argumentos+")";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		argumentos.addProduction(NUMEROS.and(separa_argumentos), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String numeros = node.getTokenExpression(0);
				String separa_argumentos = node.getWriteJava(1);
				return numeros+separa_argumentos;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		argumentos.addProduction(BOOLEANO.and(separa_argumentos), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String separa_argumentos = node.getWriteJava(1);
				return "boolean"+separa_argumentos;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		argumentos.addProduction(ID.and(ID_chamada).and(separa_argumentos), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(0);
				String id_chamada = node.getWriteJava(1);
				String separa_argumentos = node.getWriteJava(2);
				return id+id_chamada+separa_argumentos;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		separa_argumentos.addProduction(VIRGULA.and(argumentos), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String argumentos = node.getWriteJava(1);
				return ", "+argumentos;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		separa_argumentos.addProduction(Terminal.BLANK, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
escopo_loop.addProduction(INTEIRO.and(ID).and(PONTOVIRGULA).and(separa_escopo_loop), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(1);
				String separa_escopo_loop = node.getWriteJava(3);
				return "int "+id+";"+"\n\t"+separa_escopo_loop;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		escopo_loop.addProduction(BOOLEANO.and(ID).and(PONTOVIRGULA).and(separa_escopo_loop), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(1);
				String separa_escopo_loop = node.getWriteJava(3);
				return "boolean "+id+";"+"\n\t"+separa_escopo_loop;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		escopo_loop.addProduction(ID.and(ATRIBUIDOR).and(diversas_atribuicoes).and(PONTOVIRGULA).and(separa_escopo_loop), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(0);
				String diversas_atribuicoes = node.getWriteJava(2);
				String separa_escopo_loop = node.getWriteJava(4);
				return id+" = "+diversas_atribuicoes+";\n\t"+separa_escopo_loop;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		escopo_loop.addProduction(RETORNE.and(abre_parenteses).and(ID_chamada_NUMERO_BOOL).and(fecha_parenteses).and(PONTOVIRGULA).and(separa_escopo_loop), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO_BOOL = node.getWriteJava(2);
				String separa_escopo_loop = node.getWriteJava(5);
				return "return("+ID_chamada_NUMERO_BOOL+");\n\t"+separa_escopo_loop;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		escopo_loop.addProduction(ENQUANTO.and(abre_parenteses).and(comparacao).and(fecha_parenteses).and(abre_chaves).and(escopo_loop).and(fecha_chaves).and(separa_escopo_loop), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String comparacao = node.getWriteJava(2);
				String escopo_loop = node.getWriteJava(5);
				String separa_escopo_loop = node.getWriteJava(7);
				return "while("+comparacao+"){\n\t"+escopo_loop+"\n\t}"+separa_escopo_loop;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		escopo_loop.addProduction(IMPRIMA.and(abre_parenteses).and(ID_chamada_NUMERO_BOOL).and(fecha_parenteses).and(PONTOVIRGULA).and(separa_escopo_loop), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO_BOOL = node.getWriteJava(2);
				String separa_escopo_loop = node.getWriteJava(5);
				return "System.out.println("+ID_chamada_NUMERO_BOOL+");"+"\n\t"+separa_escopo_loop;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		escopo_loop.addProduction(SE.and(abre_parenteses).and(comparacao).and(fecha_parenteses).and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_loop), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String comparacao = node.getWriteJava(2);
				String escopo_condicional = node.getWriteJava(5);
				String separa_escopo_loop = node.getWriteJava(7);
				return "if("+comparacao+"){\n\t"+escopo_condicional+"\n\t}"+separa_escopo_loop;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		escopo_loop.addProduction(SENAO.and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_loop), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String escopo_condicional = node.getWriteJava(2);
				String separa_escopo_loop = node.getWriteJava(4);
				return "else{\n\t"+escopo_condicional+"\n\t}"+separa_escopo_loop;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		escopo_loop.addProduction(PARE.and(PONTOVIRGULA).and(separa_escopo_loop), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String separa_escopo_loop = node.getWriteJava(2);
				return "break;\n\t"+separa_escopo_loop;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		escopo_loop.addProduction(CONTINUE.and(PONTOVIRGULA).and(separa_escopo_loop), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String separa_escopo_loop = node.getWriteJava(2);
				return "continue;\n\t"+separa_escopo_loop;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		/**
		 * separa_escopo_loop
		 */
		
		separa_escopo_loop.addProduction(INTEIRO.and(ID).and(PONTOVIRGULA).and(separa_escopo_loop), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(1);
				String separa_escopo_loop = node.getWriteJava(3);
				return "int "+id+";"+"\n\t"+separa_escopo_loop;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		separa_escopo_loop.addProduction(BOOLEANO.and(ID).and(PONTOVIRGULA).and(separa_escopo_loop), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(1);
				String separa_escopo_loop = node.getWriteJava(3);
				return "boolean "+id+";"+"\n\t"+separa_escopo_loop;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		separa_escopo_loop.addProduction(ID.and(ATRIBUIDOR).and(diversas_atribuicoes).and(PONTOVIRGULA).and(separa_escopo_loop), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(0);
				String diversas_atribuicoes = node.getWriteJava(2);
				String separa_escopo_loop = node.getWriteJava(4);
				return id+" = "+diversas_atribuicoes+";\n\t"+separa_escopo_loop;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		separa_escopo_loop.addProduction(ENQUANTO.and(abre_parenteses).and(comparacao).and(fecha_parenteses).and(abre_chaves).and(escopo_loop).and(fecha_chaves).and(separa_escopo_loop), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String comparacao = node.getWriteJava(2);
				String escopo_loop = node.getWriteJava(5);
				String separa_escopo_loop = node.getWriteJava(7);
				return "while("+comparacao+"){\n\t"+escopo_loop+"\n\t}"+separa_escopo_loop;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		separa_escopo_loop.addProduction(IMPRIMA.and(abre_parenteses).and(ID_chamada_NUMERO_BOOL).and(fecha_parenteses).and(PONTOVIRGULA).and(separa_escopo_loop), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO_BOOL = node.getWriteJava(2);
				String separa_escopo_loop = node.getWriteJava(5);
				return "System.out.println("+ID_chamada_NUMERO_BOOL+");"+"\n\t"+separa_escopo_loop;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		separa_escopo_loop.addProduction(SE.and(abre_parenteses).and(comparacao).and(fecha_parenteses).and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_loop), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String comparacao = node.getWriteJava(2);
				String escopo_condicional = node.getWriteJava(5);
				String separa_escopo_loop = node.getWriteJava(7);
				return "if("+comparacao+"){\n\t"+escopo_condicional+"\n\t}"+separa_escopo_loop;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		separa_escopo_loop.addProduction(SENAO.and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_loop), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String escopo_condicional = node.getWriteJava(2);
				String separa_escopo_loop = node.getWriteJava(4);
				return "else{\n\t"+escopo_condicional+"\n\t}"+separa_escopo_loop;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		separa_escopo_loop.addProduction(PARE.and(PONTOVIRGULA).and(separa_escopo_loop), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String separa_escopo_loop = node.getWriteJava(2);
				return "break;\n\t"+separa_escopo_loop;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		separa_escopo_loop.addProduction(CONTINUE.and(PONTOVIRGULA).and(separa_escopo_loop), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String separa_escopo_loop = node.getWriteJava(2);
				return "continue;\n\t"+separa_escopo_loop;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		separa_escopo_loop.addProduction(Terminal.BLANK, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});

		
		escopo_condicional.addProduction(INTEIRO.and(ID).and(PONTOVIRGULA).and(separa_escopo_condicional), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(1);
				String separa_escopo_condicional = node.getWriteJava(3);
				return "int "+id+";"+"\n\t"+separa_escopo_condicional;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		escopo_condicional.addProduction(BOOLEANO.and(ID).and(PONTOVIRGULA).and(separa_escopo_condicional), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(1);
				String separa_escopo_condicional = node.getWriteJava(3);
				return "boolean "+id+";"+"\n\t"+separa_escopo_condicional;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		escopo_condicional.addProduction(ID.and(ATRIBUIDOR).and(diversas_atribuicoes).and(PONTOVIRGULA).and(separa_escopo_condicional), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(0);
				String diversas_atribuicoes = node.getWriteJava(2);
				String separa_escopo_condicional = node.getWriteJava(4);
				return id+" = "+diversas_atribuicoes+";\n\t"+separa_escopo_condicional;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		escopo_condicional.addProduction(ENQUANTO.and(abre_parenteses).and(comparacao).and(fecha_parenteses).and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_condicional), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String comparacao = node.getWriteJava(2);
				String escopo_condicional = node.getWriteJava(5);
				String separa_escopo_condicional = node.getWriteJava(7);
				return "while("+comparacao+"){\n\t"+escopo_condicional+"\n\t}"+separa_escopo_condicional;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		escopo_condicional.addProduction(IMPRIMA.and(abre_parenteses).and(ID_chamada_NUMERO_BOOL).and(fecha_parenteses).and(PONTOVIRGULA).and(separa_escopo_condicional), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO_BOOL = node.getWriteJava(2);
				String separa_escopo_condicional = node.getWriteJava(5);
				return "System.out.println("+ID_chamada_NUMERO_BOOL+");"+"\n\t"+separa_escopo_condicional;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		escopo_condicional.addProduction(SE.and(abre_parenteses).and(comparacao).and(fecha_parenteses).and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_condicional), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String comparacao = node.getWriteJava(2);
				String escopo_condicional = node.getWriteJava(5);
				String separa_escopo_condicional = node.getWriteJava(7);
				return "if("+comparacao+"){\n\t"+escopo_condicional+"\n\t}"+separa_escopo_condicional;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		escopo_condicional.addProduction(SENAO.and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_condicional), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String escopo_condicional = node.getWriteJava(2);
				String separa_escopo_condicional = node.getWriteJava(4);
				return "else{\n\t"+escopo_condicional+"\n\t}"+separa_escopo_condicional;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		/**
		 * separa_escopo_condicional
		 */
		
		separa_escopo_condicional.addProduction(INTEIRO.and(ID).and(PONTOVIRGULA).and(separa_escopo_condicional), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(1);
				String separa_escopo_condicional = node.getWriteJava(3);
				return "int "+id+";"+"\n\t"+separa_escopo_condicional;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		separa_escopo_condicional.addProduction(BOOLEANO.and(ID).and(PONTOVIRGULA).and(separa_escopo_condicional), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(1);
				String separa_escopo_condicional = node.getWriteJava(3);
				return "boolean "+id+";"+"\n\t"+separa_escopo_condicional;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		separa_escopo_condicional.addProduction(ID.and(ATRIBUIDOR).and(diversas_atribuicoes).and(PONTOVIRGULA).and(separa_escopo_condicional), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(0);
				String diversas_atribuicoes = node.getWriteJava(2);
				String separa_escopo_condicional = node.getWriteJava(4);
				return id+" = "+diversas_atribuicoes+";\n\t"+separa_escopo_condicional;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		separa_escopo_condicional.addProduction(ENQUANTO.and(abre_parenteses).and(comparacao).and(fecha_parenteses).and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_condicional), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String comparacao = node.getWriteJava(2);
				String escopo_condicional = node.getWriteJava(5);
				String separa_escopo_condicional = node.getWriteJava(7);
				return "while("+comparacao+"){\n\t"+escopo_condicional+"\n\t}"+separa_escopo_condicional;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		separa_escopo_condicional.addProduction(IMPRIMA.and(abre_parenteses).and(ID_chamada_NUMERO_BOOL).and(fecha_parenteses).and(PONTOVIRGULA).and(separa_escopo_condicional), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO_BOOL = node.getWriteJava(2);
				String separa_escopo_condicional = node.getWriteJava(5);
				return "System.out.println("+ID_chamada_NUMERO_BOOL+");"+"\n\t"+separa_escopo_condicional;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		separa_escopo_condicional.addProduction(SE.and(abre_parenteses).and(comparacao).and(fecha_parenteses).and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_condicional), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String comparacao = node.getWriteJava(2);
				String escopo_condicional = node.getWriteJava(5);
				String separa_escopo_condicional = node.getWriteJava(7);
				return "if("+comparacao+"){\n\t"+escopo_condicional+"\n\t}"+separa_escopo_condicional;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		separa_escopo_condicional.addProduction(SENAO.and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_condicional), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String escopo_condicional = node.getWriteJava(2);
				String separa_escopo_condicional = node.getWriteJava(4);
				return "else{\n\t"+escopo_condicional+"\n\t}"+separa_escopo_condicional;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		separa_escopo_condicional.addProduction(Terminal.BLANK, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		operacao.addProduction(ID.and(ID_chamada).and(operador).and(ID_chamada_NUMERO).and(outra_operacao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(0);
				String ID_chamada = node.getWriteJava(1);
				String operador = node.getWriteJava(2);
				String ID_chamada_NUMERO = node.getWriteJava(3);
				String outra_operacao = node.getWriteJava(4);
				return id+ID_chamada+operador+ID_chamada_NUMERO+outra_operacao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		operacao.addProduction(NUMEROS.and(operador).and(ID_chamada_NUMERO).and(outra_operacao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String numeros = node.getTokenExpression(0);
				String operador = node.getWriteJava(1);
				String ID_chamada_NUMERO = node.getWriteJava(2);
				String outra_operacao = node.getWriteJava(3);
				return numeros+operador+ID_chamada_NUMERO+outra_operacao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		outra_operacao.addProduction(MAIS.and(ID_chamada_NUMERO).and(outra_operacao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				String outra_operacao = node.getWriteJava(2);
				return "+"+ID_chamada_NUMERO+outra_operacao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		outra_operacao.addProduction(MENOS.and(ID_chamada_NUMERO).and(outra_operacao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				String outra_operacao = node.getWriteJava(2);
				return "-"+ID_chamada_NUMERO+outra_operacao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});		
		outra_operacao.addProduction(VEZES.and(ID_chamada_NUMERO).and(outra_operacao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				String outra_operacao = node.getWriteJava(2);
				return "*"+ID_chamada_NUMERO+outra_operacao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		outra_operacao.addProduction(DIVIDIDO.and(ID_chamada_NUMERO).and(outra_operacao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				String outra_operacao = node.getWriteJava(2);
				return "/"+ID_chamada_NUMERO+outra_operacao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		outra_operacao.addProduction(Terminal.BLANK, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		chamada_comparacao_operacao.addProduction(abre_parenteses.and(assinatura_argumentos).and(separa), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String assinatura_argumentos = node.getWriteJava(1);
				String separa = node.getWriteJava(2);
				return "("+assinatura_argumentos+separa;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		chamada_comparacao_operacao.addProduction(Terminal.BLANK, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		chamada_comparacao_operacao.addProduction(MAIS.and(ID_chamada_NUMERO).and(outra_operacao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				String outra_operacao = node.getWriteJava(2);
				return "+"+ID_chamada_NUMERO+outra_operacao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		chamada_comparacao_operacao.addProduction(MENOS.and(ID_chamada_NUMERO).and(outra_operacao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				String outra_operacao = node.getWriteJava(2);
				return "-"+ID_chamada_NUMERO+outra_operacao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		chamada_comparacao_operacao.addProduction(VEZES.and(ID_chamada_NUMERO).and(outra_operacao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				String outra_operacao = node.getWriteJava(2);
				return "*"+ID_chamada_NUMERO+outra_operacao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		chamada_comparacao_operacao.addProduction(DIVIDIDO.and(ID_chamada_NUMERO).and(outra_operacao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				String outra_operacao = node.getWriteJava(2);
				return "/"+ID_chamada_NUMERO+outra_operacao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		chamada_comparacao_operacao.addProduction(MENORQ.and(ID_chamada_NUMERO), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				return "<"+ID_chamada_NUMERO;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		chamada_comparacao_operacao.addProduction(MAIORQ.and(ID_chamada_NUMERO), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				return ">"+ID_chamada_NUMERO;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		chamada_comparacao_operacao.addProduction(MENORQIGUAL.and(ID_chamada_NUMERO), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				return "<="+ID_chamada_NUMERO;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		chamada_comparacao_operacao.addProduction(MAIORQIGUAL.and(ID_chamada_NUMERO), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				return ">="+ID_chamada_NUMERO;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		chamada_comparacao_operacao.addProduction(IGUAL.and(ID_chamada_NUMERO), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				return "=="+ID_chamada_NUMERO;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		chamada_comparacao_operacao.addProduction(DIFERENTE.and(ID_chamada_NUMERO), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				return "!="+ID_chamada_NUMERO;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		separa.addProduction(MAIS.and(ID_chamada_NUMERO).and(outra_operacao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				String outra_operacao = node.getWriteJava(2);
				return "+"+ID_chamada_NUMERO+outra_operacao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		separa.addProduction(MENOS.and(ID_chamada_NUMERO).and(outra_operacao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				String outra_operacao = node.getWriteJava(2);
				return "-"+ID_chamada_NUMERO+outra_operacao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		separa.addProduction(VEZES.and(ID_chamada_NUMERO).and(outra_operacao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				String outra_operacao = node.getWriteJava(2);
				return "*"+ID_chamada_NUMERO+outra_operacao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		separa.addProduction(DIVIDIDO.and(ID_chamada_NUMERO).and(outra_operacao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				String outra_operacao = node.getWriteJava(2);
				return "/"+ID_chamada_NUMERO+outra_operacao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		separa.addProduction(MENORQ.and(ID_chamada_NUMERO), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				return "<"+ID_chamada_NUMERO;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		separa.addProduction(MAIORQ.and(ID_chamada_NUMERO), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				return ">"+ID_chamada_NUMERO;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		separa.addProduction(MENORQIGUAL.and(ID_chamada_NUMERO), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				return "<="+ID_chamada_NUMERO;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		separa.addProduction(MAIORQIGUAL.and(ID_chamada_NUMERO), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				return ">="+ID_chamada_NUMERO;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		separa.addProduction(IGUAL.and(ID_chamada_NUMERO), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				return "=="+ID_chamada_NUMERO;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		separa.addProduction(DIFERENTE.and(ID_chamada_NUMERO), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				return "!="+ID_chamada_NUMERO;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		separa.addProduction(Terminal.BLANK, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		ID_chamada_NUMERO_BOOL.addProduction(ID.and(ID_chamada), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(0);
				String ID_chamada = node.getWriteJava(1);
				return id+ID_chamada;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		ID_chamada_NUMERO_BOOL.addProduction(NUMEROS, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String numeros = node.getTokenExpression(0);
				return numeros;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		ID_chamada_NUMERO_BOOL.addProduction(BOOLEANO, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "boolean";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		comparador.addProduction(MENORQ, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "<";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		comparador.addProduction(MAIORQ, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return ">";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		comparador.addProduction(MENORQIGUAL, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "<=";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		comparador.addProduction(MAIORQIGUAL, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return ">=";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		comparador.addProduction(IGUAL, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "==";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		comparador.addProduction(DIFERENTE, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "!=";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		operador.addProduction(MAIS, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "+";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		operador.addProduction(MENOS, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "-";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		operador.addProduction(VEZES, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "*";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		operador.addProduction(DIVIDIDO, new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				return "/";
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		//Adicionando NonTerminal ao analisador sintático
		syntaxAnalyzer.addNonTerminal(escopo);
		syntaxAnalyzer.addNonTerminal(separa_escopo);
		syntaxAnalyzer.addNonTerminal(declara);
		syntaxAnalyzer.addNonTerminal(parametros);
		syntaxAnalyzer.addNonTerminal(separa_parametros);
		syntaxAnalyzer.addNonTerminal(escopo_funcao);
		syntaxAnalyzer.addNonTerminal(separa_escopo_funcao);
		syntaxAnalyzer.addNonTerminal(ID_chamada_NUMERO);
		syntaxAnalyzer.addNonTerminal(ID_chamada);
		syntaxAnalyzer.addNonTerminal(assinatura);
		syntaxAnalyzer.addNonTerminal(diversas_atribuicoes);
		syntaxAnalyzer.addNonTerminal(comparacao);
		syntaxAnalyzer.addNonTerminal(assinatura_argumentos);
		syntaxAnalyzer.addNonTerminal(argumentos);
		syntaxAnalyzer.addNonTerminal(separa_argumentos);
		syntaxAnalyzer.addNonTerminal(escopo_loop);
		syntaxAnalyzer.addNonTerminal(separa_escopo_loop);
		syntaxAnalyzer.addNonTerminal(escopo_condicional);
		syntaxAnalyzer.addNonTerminal(separa_escopo_condicional);
		syntaxAnalyzer.addNonTerminal(operacao);
		syntaxAnalyzer.addNonTerminal(outra_operacao);
		syntaxAnalyzer.addNonTerminal(chamada_comparacao_operacao);
		syntaxAnalyzer.addNonTerminal(separa);
		syntaxAnalyzer.addNonTerminal(ID_chamada_NUMERO_BOOL);
		syntaxAnalyzer.addNonTerminal(comparador);
		syntaxAnalyzer.addNonTerminal(operador);
		
		try {
			SyntaxNode root = syntaxAnalyzer.parse();
			System.out.println(root);
			System.out.println(((NonLeaf)root).getProduction().getSemanticAction().writeJava((NonLeaf)root));
		} catch (SyntaxException e) {
			System.out.println(e.getMessage());
		} catch (TerminalNotFoundException e) {
			System.out.println(e.getOutput());
		} catch (NonTerminalEmpty e) {
			System.out.println(e.getMessage());
		} catch (LexicalException e){
			System.out.println(e.getMessage());
		}
	}

}

