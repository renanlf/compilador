package edu.br.ufrpe.uag.compiler.model.lexical;

import java.util.regex.Pattern;

import edu.br.ufrpe.uag.compiler.model.AntTerminal;
import edu.br.ufrpe.uag.compiler.model.syntax.Production;

public class Terminal implements AntTerminal {
	public static final Terminal BLANK = new Terminal(-1, "", "BLANK");

	private final Pattern regularExpression;
	private final String name;
	private int typeId;
	private final int id;

	/**
	 * Construtor do Terminal
	 * 
	 * @param id
	 * @param regularExpression
	 * @param name
	 */
	public Terminal(int id, Pattern regularExpression, String name) {
		super();
		this.id = id;
		this.name = name;
		this.regularExpression = regularExpression;
		this.typeId = 1;
	}

	public Terminal(int id, String regularExpression, String name) {
		this.id = id;
		this.name = name;
		this.regularExpression = Pattern
				.compile("^(" + regularExpression + ")");
		this.typeId = 1;
	}

	public int generateId() {
		int result = typeId;
		typeId++;
		return result;
	}

	public String getName() {
		return name;
	}

	public Pattern getRegularExpression() {
		return regularExpression;
	}

	public int getId() {
		return id;
	}

	@Override
	public Production and(AntTerminal a) {
		Production p = new Production();
		p.getAntTerminals().add(this);
		p.getAntTerminals().add(a);
		return p;
	}

	public String toString() {
		return "<" + name + ">";
	}

	public boolean equals(Object o) {
		Terminal terminalO = (Terminal) o;
		return this.getName().equals(terminalO.getName());
	}

}
