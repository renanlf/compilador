package edu.br.ufrpe.uag.compiler.music;

public class SemanticAnalyzer {
	private boolean firstLine;
	private boolean repeatBar;
	private boolean repeat;
	public SemanticAnalyzer(){
		this.firstLine = true;		
		this.repeatBar = false;
		this.repeat = false;
	}

	public boolean isFirstLine() {
		return firstLine;
	}

	public void setFirstLine(boolean firstLine) {
		this.firstLine = firstLine;
	}

	public boolean isRepeatBar() {
		return this.repeatBar;
	}

	public void setRepeatBar(boolean repeatBar) {
		this.repeatBar = repeatBar;
	}

	public boolean isRepeat() {
		return repeat;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}
}
