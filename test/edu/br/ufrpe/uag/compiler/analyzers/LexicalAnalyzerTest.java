package edu.br.ufrpe.uag.compiler.analyzers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.br.ufrpe.uag.compiler.exceptions.LexicalException;
import edu.br.ufrpe.uag.compiler.model.lexical.Terminal;
import edu.br.ufrpe.uag.compiler.model.lexical.Token;

public class LexicalAnalyzerTest {

	@Test
	public void testAddTerminal() {
		LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer("inteiro a;\n"
				+ "booleano b;\n" + "executa(inteiro a, bollean b, inteiro a){"
				+ " 	a <- 3;\n" + "retorne(a+b);\n" + "}");
		boolean ret = lexicalAnalyzer.addTerminal(0, "renan", "nome");
		assertEquals(true, ret);
	}

	@Test
	public void testGetNextToken() {
		// Terminais
		Terminal executa = new Terminal(0, "executa", "EXECUTA");
		Terminal abre_parenteses = new Terminal(1, "\\(", "abre_parenteses");
		Terminal fecha_parenteses = new Terminal(2, "\\)", "fecha_parenteses");
		Terminal abre_chaves = new Terminal(3, "\\{", "abre_chaves");
		Terminal fecha_chaves = new Terminal(4, "\\}", "fecha_chaves");
		Terminal INTEIRO = new Terminal(5, "inteiro", "INTEIRO");
		Terminal BOOLEANO = new Terminal(6, "booleano", "BOOLEANO");
		Terminal VAZIO = new Terminal(7, "vazio", "VAZIO");
		Terminal PONTOVIRGULA = new Terminal(8, "\\;", "PONTOVIRGULA");
		Terminal ATRIBUIDOR = new Terminal(9, "<-", "ATRIBUIDOR");
		Terminal RETORNE = new Terminal(10, "retorne", "RETORNE");
		Terminal ENQUANTO = new Terminal(11, "enquanto", "ENQUANTO");
		Terminal IMPRIMA = new Terminal(12, "imprima", "IMPRIMA");
		Terminal SE = new Terminal(13, "se", "SE");
		Terminal SENAO = new Terminal(14, "senao", "SENAO");
		Terminal VERDADEIRO = new Terminal(15, "V", "VERDADEIRO");
		Terminal FALSO = new Terminal(16, "F", "FALSO");
		Terminal PARE = new Terminal(17, "pare", "PARE");
		Terminal CONTINUE = new Terminal(18, "continue", "CONTINUE");
		Terminal MAIS = new Terminal(19, "\\+", "MAIS");
		Terminal MENOS = new Terminal(20, "\\-", "MENOS");
		Terminal VEZES = new Terminal(21, "\\*", "VEZES");
		Terminal DIVIDIDO = new Terminal(22, "\\/", "DIVIDIDO");
		Terminal MENORQ = new Terminal(23, "\\<", "MENORQ");
		Terminal MENORQIGUAL = new Terminal(24, "<=", "MENORQIGUAL");
		Terminal MAIORQ = new Terminal(25, ">", "MAIORQ");
		Terminal MAIORQIGUAL = new Terminal(26, ">=", "MAIORQIGUAL");
		Terminal DIFERENTE = new Terminal(27, "!=", "DIFERENTE");
		Terminal IGUAL = new Terminal(28, "=", "IGUAL");
		Terminal VIRGULA = new Terminal(29, ",", "VIRGULA");
		Terminal NUMEROS = new Terminal(30, "[0-9]+", "NUMEROS");
		Terminal ID = new Terminal(31, "[a-z][a-zA-Z0-9]*", "ID");

		LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(
				"inteiro a;\n"
				+ "booleano b;\n" 
				+ "executa(inteiro a, bollean b, inteiro a){\n"
				+ " 	a <- 357;\n" 
				+ "retorne(a+b);\n" 
				+ "}");
		// Adicionando terminais ao analisador léxico
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
		try {
			Token token = lexicalAnalyzer.getNextToken();
			String print = "";
			while (token != null) {
				print = print + token;
				token = lexicalAnalyzer.getNextToken();
			}
			System.out.println(print);
		} catch (LexicalException ex) {
			System.out.println(ex.getMessage());
		}
	}

}
