package edu.br.ufrpe.uag.compiler.analyzers;

import org.junit.Test;

import edu.br.ufrpe.uag.compiler.exceptions.LexicalException;
import edu.br.ufrpe.uag.compiler.exceptions.NonTerminalEmptyException;
import edu.br.ufrpe.uag.compiler.exceptions.SyntaxException;
import edu.br.ufrpe.uag.compiler.exceptions.TerminalNotFoundException;
import edu.br.ufrpe.uag.compiler.model.lexical.Terminal;
import edu.br.ufrpe.uag.compiler.model.syntax.NonTerminal;
import edu.br.ufrpe.uag.compiler.model.syntax.SyntaxNode;

public class SyntaxAnalyzerTest {

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
				"inteiro a;\n"
				+ "booleano b;\n"
				+ "executa(inteiro a){\n"
				+ " 	a <- 3;\n"
				+ "		enquanto(a < 10){\n"
				+ "			a <- a + 1;\n"
				+ "		}\n"
				+ "		se(a = 10){\n"
				+ "			a <- a/2;\n"
				+ "		}snao{\n"
				+ "			a <- a * 2 + 5;\n"
				+ "		}\n"
				+ "retorne(a);\n"
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
		
		SyntaxAnalyzer sintaxAnalyzer = new SyntaxAnalyzer(lexicalAnalyzer);
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
		escopo.addProduction(executa.and(abre_parenteses).and(assinatura).and(abre_chaves).and(escopo_funcao).and(fecha_chaves).and(separa_escopo));
		escopo.addProduction(INTEIRO.and(ID).and(declara).and(separa_escopo));
		escopo.addProduction(BOOLEANO.and(ID).and(declara).and(separa_escopo));
		escopo.addProduction(VAZIO.and(ID).and(abre_parenteses).and(assinatura).and(abre_chaves).and(escopo_funcao).and(fecha_chaves).and(separa_escopo));
		
		separa_escopo.addProduction(executa.and(abre_parenteses).and(assinatura).and(abre_chaves).and(escopo_funcao).and(fecha_chaves).and(separa_escopo));
		separa_escopo.addProduction(INTEIRO.and(ID).and(declara).and(separa_escopo));
		separa_escopo.addProduction(BOOLEANO.and(ID).and(declara).and(separa_escopo));
		separa_escopo.addProduction(VAZIO.and(ID).and(abre_parenteses).and(assinatura).and(abre_chaves).and(escopo_funcao).and(fecha_chaves).and(separa_escopo));
		separa_escopo.addProduction(Terminal.BLANK);
		
		declara.addProduction(abre_parenteses.and(assinatura).and(abre_chaves).and(escopo_funcao).and(fecha_chaves));
		declara.addProduction(PONTOVIRGULA);
		
		parametros.addProduction(INTEIRO.and(ID).and(separa_parametros));
		parametros.addProduction(BOOLEANO.and(ID).and(separa_parametros));
		
		separa_parametros.addProduction(VIRGULA.and(parametros));
		separa_parametros.addProduction(Terminal.BLANK);
		
		escopo_funcao.addProduction(INTEIRO.and(ID).and(PONTOVIRGULA).and(separa_escopo_funcao));
		escopo_funcao.addProduction(BOOLEANO.and(ID).and(PONTOVIRGULA).and(separa_escopo_funcao));
		escopo_funcao.addProduction(ID.and(ATRIBUIDOR).and(diversas_atribuicoes).and(PONTOVIRGULA).and(separa_escopo_funcao));
		escopo_funcao.addProduction(RETORNE.and(abre_parenteses).and(ID_chamada_NUMERO_BOOL).and(fecha_parenteses).and(PONTOVIRGULA).and(separa_escopo_funcao));
		escopo_funcao.addProduction(ENQUANTO.and(abre_parenteses).and(comparacao).and(fecha_parenteses).and(abre_chaves).and(escopo_loop).and(fecha_chaves).and(separa_escopo_funcao));
		escopo_funcao.addProduction(IMPRIMA.and(abre_parenteses).and(ID_chamada_NUMERO_BOOL).and(fecha_parenteses).and(PONTOVIRGULA).and(separa_escopo_funcao));
		escopo_funcao.addProduction(SE.and(abre_parenteses).and(comparacao).and(fecha_parenteses).and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_funcao));
		escopo_funcao.addProduction(SENAO.and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_funcao));
		
		separa_escopo_funcao.addProduction(INTEIRO.and(ID).and(PONTOVIRGULA).and(separa_escopo_funcao));
		separa_escopo_funcao.addProduction(BOOLEANO.and(ID).and(PONTOVIRGULA).and(separa_escopo_funcao));
		separa_escopo_funcao.addProduction(ID.and(ATRIBUIDOR).and(diversas_atribuicoes).and(PONTOVIRGULA).and(separa_escopo_funcao));
		separa_escopo_funcao.addProduction(RETORNE.and(abre_parenteses).and(ID_chamada_NUMERO_BOOL).and(fecha_parenteses).and(PONTOVIRGULA).and(separa_escopo_funcao));
		separa_escopo_funcao.addProduction(ENQUANTO.and(abre_parenteses).and(comparacao).and(fecha_parenteses).and(abre_chaves).and(escopo_loop).and(fecha_chaves).and(separa_escopo_funcao));
		separa_escopo_funcao.addProduction(IMPRIMA.and(abre_parenteses).and(ID_chamada_NUMERO_BOOL).and(fecha_parenteses).and(PONTOVIRGULA).and(separa_escopo_funcao));
		separa_escopo_funcao.addProduction(SE.and(abre_parenteses).and(comparacao).and(fecha_parenteses).and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_funcao));
		separa_escopo_funcao.addProduction(SENAO.and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_funcao));
		separa_escopo_funcao.addProduction(Terminal.BLANK);
		
		ID_chamada_NUMERO.addProduction(ID.and(ID_chamada));
		ID_chamada_NUMERO.addProduction(NUMEROS);
		
		ID_chamada.addProduction(abre_parenteses.and(assinatura_argumentos));
		ID_chamada.addProduction(Terminal.BLANK);
		
		assinatura.addProduction(fecha_parenteses);
		assinatura.addProduction(INTEIRO.and(ID).and(separa_parametros).and(fecha_parenteses));
		assinatura.addProduction(BOOLEANO.and(ID).and(separa_parametros).and(fecha_parenteses));
		
		diversas_atribuicoes.addProduction(ID.and(chamada_comparacao_operacao));
		diversas_atribuicoes.addProduction(NUMEROS.and(separa));
		diversas_atribuicoes.addProduction(BOOLEANO);
		
		comparacao.addProduction(NUMEROS.and(comparador).and(ID_chamada_NUMERO));
		comparacao.addProduction(BOOLEANO);
		comparacao.addProduction(ID.and(ID_chamada).and(comparador).and(ID_chamada_NUMERO));
		
		assinatura_argumentos.addProduction(fecha_parenteses);
		assinatura_argumentos.addProduction(NUMEROS.and(separa_argumentos).and(fecha_parenteses));
		assinatura_argumentos.addProduction(BOOLEANO.and(separa_argumentos).and(fecha_parenteses));
		assinatura_argumentos.addProduction(ID.and(ID_chamada).and(separa_argumentos).and(fecha_parenteses));
		
		argumentos.addProduction(NUMEROS.and(separa_argumentos));
		argumentos.addProduction(BOOLEANO.and(separa_argumentos));
		argumentos.addProduction(ID.and(ID_chamada).and(separa_argumentos));
		
		separa_argumentos.addProduction(VIRGULA.and(argumentos));
		separa_argumentos.addProduction(Terminal.BLANK);
		
		escopo_loop.addProduction(INTEIRO.and(ID).and(PONTOVIRGULA).and(separa_escopo_loop));
		escopo_loop.addProduction(BOOLEANO.and(ID).and(PONTOVIRGULA).and(separa_escopo_loop));
		escopo_loop.addProduction(ID.and(ATRIBUIDOR).and(diversas_atribuicoes).and(PONTOVIRGULA).and(separa_escopo_loop));
		escopo_loop.addProduction(ENQUANTO.and(abre_parenteses).and(comparacao).and(fecha_parenteses).and(abre_chaves).and(escopo_loop).and(fecha_chaves).and(separa_escopo_loop));
		escopo_loop.addProduction(IMPRIMA.and(abre_parenteses).and(ID_chamada_NUMERO_BOOL).and(fecha_parenteses).and(PONTOVIRGULA).and(separa_escopo_loop));
		escopo_loop.addProduction(SE.and(abre_parenteses).and(comparacao).and(fecha_parenteses).and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_loop));
		escopo_loop.addProduction(SENAO.and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_loop));
		escopo_loop.addProduction(PARE.and(PONTOVIRGULA).and(separa_escopo_loop));
		escopo_loop.addProduction(CONTINUE.and(PONTOVIRGULA).and(separa_escopo_loop));
		
		separa_escopo_loop.addProduction(INTEIRO.and(ID).and(PONTOVIRGULA).and(separa_escopo_loop));
		separa_escopo_loop.addProduction(BOOLEANO.and(ID).and(PONTOVIRGULA).and(separa_escopo_loop));
		separa_escopo_loop.addProduction(ID.and(ATRIBUIDOR).and(diversas_atribuicoes).and(PONTOVIRGULA).and(separa_escopo_loop));
		separa_escopo_loop.addProduction(ENQUANTO.and(abre_parenteses).and(comparacao).and(fecha_parenteses).and(abre_chaves).and(escopo_loop).and(fecha_chaves).and(separa_escopo_loop));
		separa_escopo_loop.addProduction(IMPRIMA.and(abre_parenteses).and(ID_chamada_NUMERO_BOOL).and(fecha_parenteses).and(PONTOVIRGULA).and(separa_escopo_loop));
		separa_escopo_loop.addProduction(SE.and(abre_parenteses).and(comparacao).and(fecha_parenteses).and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_loop));
		separa_escopo_loop.addProduction(SENAO.and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_loop));
		separa_escopo_loop.addProduction(PARE.and(PONTOVIRGULA).and(separa_escopo_loop));
		separa_escopo_loop.addProduction(CONTINUE.and(PONTOVIRGULA).and(separa_escopo_loop));
		separa_escopo_loop.addProduction(Terminal.BLANK);
		
		escopo_condicional.addProduction(INTEIRO.and(ID).and(PONTOVIRGULA).and(separa_escopo_condicional));
		escopo_condicional.addProduction(BOOLEANO.and(ID).and(PONTOVIRGULA).and(separa_escopo_condicional));
		escopo_condicional.addProduction(ID.and(ATRIBUIDOR).and(diversas_atribuicoes).and(PONTOVIRGULA).and(separa_escopo_condicional));
		escopo_condicional.addProduction(ENQUANTO.and(abre_parenteses).and(comparacao).and(fecha_parenteses).and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_condicional));
		escopo_condicional.addProduction(IMPRIMA.and(abre_parenteses).and(ID_chamada_NUMERO_BOOL).and(fecha_parenteses).and(PONTOVIRGULA).and(separa_escopo_condicional));
		escopo_condicional.addProduction(SE.and(abre_parenteses).and(comparacao).and(fecha_parenteses).and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_condicional));
		escopo_condicional.addProduction(SENAO.and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_condicional));
		
		separa_escopo_condicional.addProduction(INTEIRO.and(ID).and(PONTOVIRGULA).and(separa_escopo_condicional));
		separa_escopo_condicional.addProduction(BOOLEANO.and(ID).and(PONTOVIRGULA).and(separa_escopo_condicional));
		separa_escopo_condicional.addProduction(ID.and(ATRIBUIDOR).and(diversas_atribuicoes).and(PONTOVIRGULA).and(separa_escopo_condicional));
		separa_escopo_condicional.addProduction(ENQUANTO.and(abre_parenteses).and(comparacao).and(fecha_parenteses).and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_condicional));
		separa_escopo_condicional.addProduction(IMPRIMA.and(abre_parenteses).and(ID_chamada_NUMERO_BOOL).and(fecha_parenteses).and(PONTOVIRGULA).and(separa_escopo_condicional));
		separa_escopo_condicional.addProduction(SE.and(abre_parenteses).and(comparacao).and(fecha_parenteses).and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_condicional));
		separa_escopo_condicional.addProduction(SENAO.and(abre_chaves).and(escopo_condicional).and(fecha_chaves).and(separa_escopo_condicional));
		separa_escopo_condicional.addProduction(Terminal.BLANK);
		
		operacao.addProduction(ID.and(ID_chamada).and(operador).and(ID_chamada_NUMERO).and(outra_operacao));
		operacao.addProduction(NUMEROS.and(operador).and(ID_chamada_NUMERO).and(outra_operacao));
		
		outra_operacao.addProduction(MAIS.and(ID_chamada_NUMERO).and(outra_operacao));
		outra_operacao.addProduction(MENOS.and(ID_chamada_NUMERO).and(outra_operacao));		
		outra_operacao.addProduction(VEZES.and(ID_chamada_NUMERO).and(outra_operacao));
		outra_operacao.addProduction(DIVIDIDO.and(ID_chamada_NUMERO).and(outra_operacao));
		outra_operacao.addProduction(Terminal.BLANK);
		
		chamada_comparacao_operacao.addProduction(abre_parenteses.and(assinatura_argumentos).and(separa));
		chamada_comparacao_operacao.addProduction(Terminal.BLANK);
		chamada_comparacao_operacao.addProduction(MAIS.and(ID_chamada_NUMERO).and(outra_operacao));
		chamada_comparacao_operacao.addProduction(MENOS.and(ID_chamada_NUMERO).and(outra_operacao));
		chamada_comparacao_operacao.addProduction(VEZES.and(ID_chamada_NUMERO).and(outra_operacao));
		chamada_comparacao_operacao.addProduction(DIVIDIDO.and(ID_chamada_NUMERO).and(outra_operacao));
		chamada_comparacao_operacao.addProduction(MENORQ.and(ID_chamada_NUMERO));
		chamada_comparacao_operacao.addProduction(MAIORQ.and(ID_chamada_NUMERO));
		chamada_comparacao_operacao.addProduction(MENORQIGUAL.and(ID_chamada_NUMERO));
		chamada_comparacao_operacao.addProduction(MAIORQIGUAL.and(ID_chamada_NUMERO));
		chamada_comparacao_operacao.addProduction(IGUAL.and(ID_chamada_NUMERO));
		chamada_comparacao_operacao.addProduction(DIFERENTE.and(ID_chamada_NUMERO));
		
		separa.addProduction(MAIS.and(ID_chamada_NUMERO).and(outra_operacao));
		separa.addProduction(MENOS.and(ID_chamada_NUMERO).and(outra_operacao));
		separa.addProduction(VEZES.and(ID_chamada_NUMERO).and(outra_operacao));
		separa.addProduction(DIVIDIDO.and(ID_chamada_NUMERO).and(outra_operacao));
		separa.addProduction(MENORQ.and(ID_chamada_NUMERO));
		separa.addProduction(MAIORQ.and(ID_chamada_NUMERO));
		separa.addProduction(MENORQIGUAL.and(ID_chamada_NUMERO));
		separa.addProduction(MAIORQIGUAL.and(ID_chamada_NUMERO));
		separa.addProduction(IGUAL.and(ID_chamada_NUMERO));
		separa.addProduction(DIFERENTE.and(ID_chamada_NUMERO));
		separa.addProduction(Terminal.BLANK);
		
		ID_chamada_NUMERO_BOOL.addProduction(ID.and(ID_chamada));
		ID_chamada_NUMERO_BOOL.addProduction(NUMEROS);
		ID_chamada_NUMERO_BOOL.addProduction(BOOLEANO);
		
		comparador.addProduction(MENORQ);
		comparador.addProduction(MAIORQ);
		comparador.addProduction(MENORQIGUAL);
		comparador.addProduction(MAIORQIGUAL);
		comparador.addProduction(IGUAL);
		comparador.addProduction(DIFERENTE);
		
		operador.addProduction(MAIS);
		operador.addProduction(MENOS);
		operador.addProduction(VEZES);
		operador.addProduction(DIVIDIDO);
		
		
		//Adicionando NonTerminal ao analisador sintático
		sintaxAnalyzer.addNonTerminal(escopo);
		sintaxAnalyzer.addNonTerminal(separa_escopo);
		sintaxAnalyzer.addNonTerminal(declara);
		sintaxAnalyzer.addNonTerminal(parametros);
		sintaxAnalyzer.addNonTerminal(separa_parametros);
		sintaxAnalyzer.addNonTerminal(escopo_funcao);
		sintaxAnalyzer.addNonTerminal(separa_escopo_funcao);
		sintaxAnalyzer.addNonTerminal(ID_chamada_NUMERO);
		sintaxAnalyzer.addNonTerminal(ID_chamada);
		sintaxAnalyzer.addNonTerminal(assinatura);
		sintaxAnalyzer.addNonTerminal(diversas_atribuicoes);
		sintaxAnalyzer.addNonTerminal(comparacao);
		sintaxAnalyzer.addNonTerminal(assinatura_argumentos);
		sintaxAnalyzer.addNonTerminal(argumentos);
		sintaxAnalyzer.addNonTerminal(separa_argumentos);
		sintaxAnalyzer.addNonTerminal(escopo_loop);
		sintaxAnalyzer.addNonTerminal(separa_escopo_loop);
		sintaxAnalyzer.addNonTerminal(escopo_condicional);
		sintaxAnalyzer.addNonTerminal(separa_escopo_condicional);
		sintaxAnalyzer.addNonTerminal(operacao);
		sintaxAnalyzer.addNonTerminal(outra_operacao);
		sintaxAnalyzer.addNonTerminal(chamada_comparacao_operacao);
		sintaxAnalyzer.addNonTerminal(separa);
		sintaxAnalyzer.addNonTerminal(ID_chamada_NUMERO_BOOL);
		sintaxAnalyzer.addNonTerminal(comparador);
		sintaxAnalyzer.addNonTerminal(operador);
		
		try {
			SyntaxNode root = sintaxAnalyzer.parse();
			System.out.println(root);
		} catch (SyntaxException e) {
			System.out.println(e.getMessage());
		} catch (TerminalNotFoundException e) {
			System.out.println(e.getOutput());
		} catch (NonTerminalEmptyException e) {
			System.out.println(e.getMessage());
		} catch (LexicalException e){
			System.out.println(e.getMessage());
		}
	}

}
