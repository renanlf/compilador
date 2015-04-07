package edu.br.ufrpe.uag.compiler.model;

import java.util.regex.Pattern;

public class TokenType {
	private final Pattern regularExpression;
	private final String typeName;
	private int typeId;
	private final int id;
	/**
	 * Construtor do TokenType
	 * @param id
	 * @param regularExpression
	 * @param typeName
	 */
	public TokenType(int id, Pattern regularExpression, String typeName) {
		super();
		this.id = id;
		this.typeName = typeName;
		this.regularExpression = regularExpression;
		this.typeId = 1;
	}
	
	public int generateId(){
		int result = typeId;
		typeId++;
		return result;
	}

	public String getTypeName() {
		return typeName;
	}

	public Pattern getRegularExpression() {
		return regularExpression;
	}

	public int getId() {
		return id;
	}
	
}
