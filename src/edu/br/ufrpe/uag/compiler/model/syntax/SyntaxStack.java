package edu.br.ufrpe.uag.compiler.model.syntax;

import java.util.ArrayDeque;
import java.util.Deque;

import edu.br.ufrpe.uag.compiler.model.AntTerminal;

public class SyntaxStack {
	private final Deque<AntTerminal> stack;

	public SyntaxStack() {
		super();
		this.stack = new ArrayDeque<AntTerminal>();
	}
	
	public AntTerminal getLast(){
		return stack.getLast();
	}
	
	public void pop(){
		stack.removeLast();
	}
	
	public boolean add(AntTerminal a){
		return stack.add(a);
	}
	
	public int size(){
		return stack.size();
	}
	
	
	
}
