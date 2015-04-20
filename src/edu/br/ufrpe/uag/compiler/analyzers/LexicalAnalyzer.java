package edu.br.ufrpe.uag.compiler.analyzers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.br.ufrpe.uag.compiler.exceptions.LexicalException;
import edu.br.ufrpe.uag.compiler.model.lexical.Terminal;
import edu.br.ufrpe.uag.compiler.model.lexical.Token;

public class LexicalAnalyzer {
	private final List<Terminal> terminals;
	private final String[] sequence;
	private int row;
	private String sequenceRow;

	/**
	 * Construtor do analisador lexico.
	 * @param arrayList array contendo os Terminals
	 * @param string
	 */
	public LexicalAnalyzer(List<Terminal> terminals, String string) {
		super();
		this.terminals = terminals;
		String[] newSequence = string.split("\n");
		this.sequence = newSequence;
		this.row = 0;
		this.sequenceRow = new String(sequence[row]);
	}
	
	/**
	 * Construtor do analisador lexico.
	 * @param arrayList array contendo os Terminals
	 * @param string
	 */
	public LexicalAnalyzer(String string) {
		super();
		this.terminals = new ArrayList<Terminal>();
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
			for(Terminal tokenType : terminals){
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
	 * m�todo que adiciona um Terminal na lista do analisador l�xico.
	 * @param id
	 * @param regularExpression
	 * @param name
	 * @return
	 */
	public boolean addTerminal(int id, String regularExpression, String name){
		Terminal tokenType = new Terminal(id, Pattern.compile("^("+regularExpression+")"), name);
		return terminals.add(tokenType);
	}
	
	public boolean addTerminal(Terminal t){
		return terminals.add(t);
	}
	
	public void removeInitialSpace(){
		//retira os espa�os vazios que sobrem depois do texto retirado
		while(sequenceRow.startsWith(" ")){
			sequenceRow = sequenceRow.replaceFirst(" ", "");
		}
	}

	public List<Terminal> getTerminals() {
		return terminals;
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
