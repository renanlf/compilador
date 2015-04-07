package edu.br.ufrpe.uag.compiler.analyzers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import edu.br.ufrpe.uag.compiler.model.Token;
import edu.br.ufrpe.uag.compiler.model.TokenType;

public class LexicalAnalyzerTest {

	@Test
	public void testAddTokenType() {
		LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(new ArrayList<TokenType>(), "");
		boolean ret = lexicalAnalyzer.addTokenType(0, "renan", "nome");
		assertEquals(true, ret);
	}
	
	@Test
	public void testGetNextToken(){
		LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(new ArrayList<TokenType>(), "inteiro a <- 34;\nbooleano% b;\nexecuta(inteiroa, bollean b, inteiro a");
		lexicalAnalyzer.addTokenType(0, "executa", "EXECUTA");
		lexicalAnalyzer.addTokenType(1, "\\(", "abre_parenteses");
		lexicalAnalyzer.addTokenType(2, "\\)", "fecha_parenteses");
		lexicalAnalyzer.addTokenType(3, "\\{", "abre_chaves");
		lexicalAnalyzer.addTokenType(4, "\\}", "fecha_chaves");
		lexicalAnalyzer.addTokenType(5, "inteiro", "INTEIRO");
		lexicalAnalyzer.addTokenType(6, "booleano", "BOOLEANO");
		lexicalAnalyzer.addTokenType(7, "vazio", "VAZIO");
		lexicalAnalyzer.addTokenType(8, "\\;", "PONTOVIRGULA");
		lexicalAnalyzer.addTokenType(9, "<-", "ATRIBUIDOR");
		lexicalAnalyzer.addTokenType(10, "retorne", "RETORNE");
		lexicalAnalyzer.addTokenType(11, "enquanto", "ENQUANTO");
		lexicalAnalyzer.addTokenType(12, "imprima", "IMPRIMA");
		lexicalAnalyzer.addTokenType(13, "se", "SE");
		lexicalAnalyzer.addTokenType(14, "senao", "SENAO");
		lexicalAnalyzer.addTokenType(15, "V", "VERDADEIRO");
		lexicalAnalyzer.addTokenType(16, "F", "FALSO");
		lexicalAnalyzer.addTokenType(17, "pare", "PARE");
		lexicalAnalyzer.addTokenType(18, "continue", "CONTINUE");
		lexicalAnalyzer.addTokenType(19, "\\+", "MAIS");
		lexicalAnalyzer.addTokenType(20, "\\-", "MENOS");
		lexicalAnalyzer.addTokenType(21, "\\*", "VEZES");
		lexicalAnalyzer.addTokenType(22, "\\/", "DIVIDIDO");
		lexicalAnalyzer.addTokenType(23, "\\<", "MENORQ");
		lexicalAnalyzer.addTokenType(24, "<=", "MENORQIGUAL");
		lexicalAnalyzer.addTokenType(25, ">", "MAIORQ");
		lexicalAnalyzer.addTokenType(26, ">=", "MAIORQIGUAL");
		lexicalAnalyzer.addTokenType(27, "!=", "DIFERENTE");
		lexicalAnalyzer.addTokenType(28, "=", "IGUAL");
		lexicalAnalyzer.addTokenType(29, ",", "VIRGULA");
		lexicalAnalyzer.addTokenType(30, "[0-9]+", "NUMEROS");
		lexicalAnalyzer.addTokenType(31, "[a-z][a-zA-Z0-9]*", "ID");
		
		Token token = lexicalAnalyzer.getNextToken();
		String print = "";
		while(token != null){
			print = print + token;
			token = lexicalAnalyzer.getNextToken();
		}
		System.out.println(print);
	}

}
