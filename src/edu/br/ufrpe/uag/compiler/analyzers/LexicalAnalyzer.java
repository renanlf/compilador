package edu.br.ufrpe.uag.compiler.analyzers;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.br.ufrpe.uag.compiler.exceptions.LexicalException;
import edu.br.ufrpe.uag.compiler.model.Token;
import edu.br.ufrpe.uag.compiler.model.TokenType;

public class LexicalAnalyzer {
	private final ArrayList<TokenType> tokenTypes;
	private final String[] sequence;
	private int row;
	private String sequenceRow;

	/**
	 * Construtor do analisador lexico.
	 * @param arrayList array contendo os TokenTypes
	 * @param string
	 */
	public LexicalAnalyzer(ArrayList<TokenType> tokenTypes, String string) {
		super();
		this.tokenTypes = tokenTypes;
		String[] newSequence = string.split("\n");
		this.sequence = newSequence;
		this.row = 0;
		this.sequenceRow = new String(sequence[row]);
	}
	/**
	 * método que será utilizado pelo análisador sintático para obter os tokens
	 * @return o próximo token. Caso não exista próximo será retornado null.
	 */
	public Token getNextToken(){
		//se a linha selecionada possuir tamanho maior que 0.
		if(sequenceRow.length() > 0){
			removeInitialSpace();
			//laço que varre os tokens até encontrar algum que satisfaça a condição interna.
			for(TokenType tokenType : tokenTypes){
				Matcher m = tokenType.getRegularExpression().matcher(sequenceRow);
				if(m.find()){
					//salva em expression qual foi o texto encontrado.
					String expression = m.group().trim();
					//substitui o texto encontrado por vazio.
					sequenceRow = m.replaceFirst("");
					removeInitialSpace();
					//retorna o token
					return new Token(tokenType, expression);
				}
			}
			//caso o inicio da linha selecionada não possua token correspondente será lançada uma exceção.
			throw new LexicalException(row, sequenceRow);
		//caso a linha selecionada não possua tamanho maior que 0.
		//indica que chegou ao fim o parse na linha.
		} else {
			//incrementa o numero da linha.
			row++;
			//se o numero de linhas for maior que o array contendo o texto original quebrado em linhas retorne null.
			if(row > sequence.length-1) return null;
			//caso não a linha selecionada será a proxima linha da sequencia.
			sequenceRow = new String(sequence[row]);
			//recursivamente chama getNextToken() que agora estará na nova linha.
			return getNextToken();
		}
	}
	/**
	 * método que adiciona um TokenType na lista do analisador léxico.
	 * @param id
	 * @param regularExpression
	 * @param name
	 * @return
	 */
	public boolean addTokenType(int id, String regularExpression, String name){
		TokenType tokenType = new TokenType(id, Pattern.compile("^("+regularExpression+")"), name);
		return tokenTypes.add(tokenType);
	}
	
	public void removeInitialSpace(){
		//retira os espaços vazios que sobrem depois do texto retirado
		while(sequenceRow.startsWith(" ")){
			sequenceRow = sequenceRow.replaceFirst(" ", "");
		}
	}

	public ArrayList<TokenType> getTokenTypes() {
		return tokenTypes;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getSequenceRow() {
		return sequenceRow;
	}

	public void setSequenceRow(String sequenceRow) {
		this.sequenceRow = sequenceRow;
	}
}
