package edu.br.ufrpe.uag.compiler.analyzers;
public class Program{
static int c;
static int multiplica(int a, int b){
		int r;
	r = 0;
	if(b>0){
	b = b-1;
	r = a+multiplica(a, b);
	
	}return(r);
	
}
static void t(int a){
c = c+a;
	
}
public static void main(String args[]){
System.out.println(c);
	t(3);
	System.out.println(c);
	System.out.println(multiplica(c, c));
	
}
}