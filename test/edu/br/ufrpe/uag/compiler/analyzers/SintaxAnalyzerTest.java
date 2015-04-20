package edu.br.ufrpe.uag.compiler.analyzers;

import org.junit.Test;

import edu.br.ufrpe.uag.compiler.model.lexical.Terminal;
import edu.br.ufrpe.uag.compiler.model.sintax.NonTerminal;

public class SintaxAnalyzerTest {

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
		Terminal SE = new Terminal (13, "se", "SE");
		Terminal SENAO = new Terminal (14, "senao", "SENAO");
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
				+ "executa(inteiro a, bollean b, inteiro a){"
				+ "a <- 3;\n"
				+ "retorne(a+b);\n"
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
		System.out.println(escopo);
		System.out.println(separa_escopo);
	}

}
