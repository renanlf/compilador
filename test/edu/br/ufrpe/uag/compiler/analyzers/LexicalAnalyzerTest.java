package edu.br.ufrpe.uag.compiler.analyzers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import edu.br.ufrpe.uag.compiler.model.lexical.Terminal;
import edu.br.ufrpe.uag.compiler.model.lexical.Token;

public class LexicalAnalyzerTest {

	@Test
	public void testAddTerminal() {
		LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(new ArrayList<Terminal>(), "");
		boolean ret = lexicalAnalyzer.addTerminal(0, "renan", "nome");
		assertEquals(true, ret);
	}
	
	@Test
	public void testGetNextToken(){
		LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(
				"inteiro a;\n"
				+ "booleano b;\n"
				+ "executa(inteiro a, bollean b, inteiro a){"
				+ "a <- 3;\n"
				+ "retorne(a+b);\n"
				+ "}");
		
		lexicalAnalyzer.addTerminal(0, "executa", "EXECUTA");
		lexicalAnalyzer.addTerminal(1, "\\(", "abre_parenteses");
		lexicalAnalyzer.addTerminal(2, "\\)", "fecha_parenteses");
		lexicalAnalyzer.addTerminal(3, "\\{", "abre_chaves");
		lexicalAnalyzer.addTerminal(4, "\\}", "fecha_chaves");
		lexicalAnalyzer.addTerminal(5, "inteiro", "INTEIRO");
		lexicalAnalyzer.addTerminal(6, "booleano", "BOOLEANO");
		lexicalAnalyzer.addTerminal(7, "vazio", "VAZIO");
		lexicalAnalyzer.addTerminal(8, "\\;", "PONTOVIRGULA");
		lexicalAnalyzer.addTerminal(9, "<-", "ATRIBUIDOR");
		lexicalAnalyzer.addTerminal(10, "retorne", "RETORNE");
		lexicalAnalyzer.addTerminal(11, "enquanto", "ENQUANTO");
		lexicalAnalyzer.addTerminal(12, "imprima", "IMPRIMA");
		lexicalAnalyzer.addTerminal(13, "se", "SE");
		lexicalAnalyzer.addTerminal(14, "senao", "SENAO");
		lexicalAnalyzer.addTerminal(15, "V", "VERDADEIRO");
		lexicalAnalyzer.addTerminal(16, "F", "FALSO");
		lexicalAnalyzer.addTerminal(17, "pare", "PARE");
		lexicalAnalyzer.addTerminal(18, "continue", "CONTINUE");
		lexicalAnalyzer.addTerminal(19, "\\+", "MAIS");
		lexicalAnalyzer.addTerminal(20, "\\-", "MENOS");
		lexicalAnalyzer.addTerminal(21, "\\*", "VEZES");
		lexicalAnalyzer.addTerminal(22, "\\/", "DIVIDIDO");
		lexicalAnalyzer.addTerminal(23, "\\<", "MENORQ");
		lexicalAnalyzer.addTerminal(24, "<=", "MENORQIGUAL");
		lexicalAnalyzer.addTerminal(25, ">", "MAIORQ");
		lexicalAnalyzer.addTerminal(26, ">=", "MAIORQIGUAL");
		lexicalAnalyzer.addTerminal(27, "!=", "DIFERENTE");
		lexicalAnalyzer.addTerminal(28, "=", "IGUAL");
		lexicalAnalyzer.addTerminal(29, ",", "VIRGULA");
		lexicalAnalyzer.addTerminal(30, "[0-9]+", "NUMEROS");
		lexicalAnalyzer.addTerminal(31, "[a-z][a-zA-Z0-9]*", "ID");
		
		Token token = lexicalAnalyzer.getNextToken();
		String print = "";
		while(token != null){
			print = print + token;
			token = lexicalAnalyzer.getNextToken();
		}
		System.out.println(print);
	}

}
