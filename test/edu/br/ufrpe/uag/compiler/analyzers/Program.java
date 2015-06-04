package edu.br.ufrpe.uag.compiler.analyzers;

public class Program {
	static int d;

	static int multiplica(int a, int b) {
		if (b > 1) {
			b = b - 1;
			d = a + multiplica(a, b);

		}
		return (d);

	}

}