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
	 * m�todo que ser� utilizado pelo an�lisador sint�tico para obter os tokens
	 * @return o pr�ximo token. Caso n�o exista pr�ximo ser� retornado null.
	 */
	public Token getNextToken(){
		//se a linha selecionada possuir tamanho maior que 0.
		if(sequenceRow.length() > 0){
			removeInitialSpace();
			//la�o que varre os tokens at� encontrar algum que satisfa�a a condi��o interna.
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
			//caso o inicio da linha selecionada n�o possua token correspondente ser� lan�ada uma exce��o.
			throw new LexicalException(row, sequenceRow);
		//caso a linha selecionada n�o possua tamanho maior que 0.
		//indica que chegou ao fim o parse na linha.
		} else {
			//incrementa o numero da linha.
			row++;
			//se o numero de linhas for maior que o array contendo o texto original quebrado em linhas retorne null.
			if(row > sequence.length-1) return null;
			//caso n�o a linha selecionada ser� a proxima linha da sequencia.
			sequenceRow = new String(sequence[row]);
			//recursivamente chama getNextToken() que agora estar� na nova linha.
			return getNextToken();
		}
	}
	/**
	 * m�todo que adiciona um TokenType na lista do analisador l�xico.
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
		//retira os espa�os vazios que sobrem depois do texto retirado
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
