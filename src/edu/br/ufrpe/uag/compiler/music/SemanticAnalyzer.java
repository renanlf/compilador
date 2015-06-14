package edu.br.ufrpe.uag.compiler.music;

public class SemanticAnalyzer {
	private boolean firstLine;
	
	public SemanticAnalyzer(){
		this.firstLine = true;
	}

	public boolean isFirstLine() {
		return firstLine;
	}

	public void setFirstLine(boolean firstLine) {
		this.firstLine = firstLine;
	}
}
