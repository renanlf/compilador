package edu.br.ufrpe.uag.compiler.analyzers;

public class Program {
	static int multiplica(int a, int b) {
		int c;
		while (b > 1) {
			a = a + a;
			b = b - 1;

		}
		c = b + 1;
		return (a);

	}

	public static void main(String args[]) {
		System.out.println(multiplica(3, 2));
	}
}