package edu.br.ufrpe.uag.compiler.model.sintax;

import java.util.ArrayDeque;
import java.util.Deque;

import edu.br.ufrpe.uag.compiler.model.AntTerminal;

public class SintaxStack {
	private final Deque<AntTerminal> stack;

	public SintaxStack() {
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
	
	
	
}
