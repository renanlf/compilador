package edu.br.ufrpe.uag.compiler.gui;

import java.util.ArrayList;
import java.util.List;

import edu.br.ufrpe.uag.compiler.analyzers.LexicalAnalyzer;
import edu.br.ufrpe.uag.compiler.analyzers.SemanticAnalyzer;
import edu.br.ufrpe.uag.compiler.analyzers.SyntaxAnalyzer;
import edu.br.ufrpe.uag.compiler.exceptions.DuplicateDefinicao;
import edu.br.ufrpe.uag.compiler.exceptions.NonTerminalEmptyException;
import edu.br.ufrpe.uag.compiler.exceptions.SemanticException;
import edu.br.ufrpe.uag.compiler.exceptions.SyntaxException;
import edu.br.ufrpe.uag.compiler.exceptions.TerminalNotFoundException;
import edu.br.ufrpe.uag.compiler.model.lexical.Terminal;
import edu.br.ufrpe.uag.compiler.model.semantic.Definicao;
import edu.br.ufrpe.uag.compiler.model.semantic.Funcao;
import edu.br.ufrpe.uag.compiler.model.semantic.Parametro;
import edu.br.ufrpe.uag.compiler.model.semantic.SemanticAction;
import edu.br.ufrpe.uag.compiler.model.semantic.Tipo;
import edu.br.ufrpe.uag.compiler.model.syntax.Leaf;
import edu.br.ufrpe.uag.compiler.model.syntax.NonLeaf;
import edu.br.ufrpe.uag.compiler.model.syntax.NonTerminal;
import edu.br.ufrpe.uag.compiler.model.syntax.SyntaxNode;

public class Compiler {
	private static LexicalAnalyzer lexicalAnalyzer;
	private static SyntaxAnalyzer syntaxAnalyzer;
	private static SemanticAnalyzer semanticAnalyzer;

	public static String compile(String source) throws SyntaxException, TerminalNotFoundException, NonTerminalEmptyException, SemanticException {
		// Terminais
		Terminal executa = new Terminal(0, "executa", "EXECUTA");
		Terminal abre_parenteses = new Terminal(1, "\\(", "abre_parenteses");
		Terminal fecha_parenteses = new Terminal(2, "\\)", "fecha_parenteses");
		Terminal abre_chaves = new Terminal(3, "\\{", "abre_chaves");
		Terminal fecha_chaves = new Terminal(4, "\\}", "fecha_chaves");
		Terminal INTEIRO = new Terminal(5, "inteiro", "INTEIRO");
		Terminal BOOLEANO = new Terminal(6, "booleano", "BOOLEANO");
		Terminal VAZIO = new Terminal(7, "vazio", "VAZIO");
		Terminal PONTOVIRGULA = new Terminal(8, "\\;", "PONTOVIRGULA");
		Terminal ATRIBUIDOR = new Terminal(9, "<-", "ATRIBUIDOR");
		Terminal RETORNE = new Terminal(10, "retorne", "RETORNE");
		Terminal ENQUANTO = new Terminal(11, "enquanto", "ENQUANTO");
		Terminal IMPRIMA = new Terminal(12, "imprima", "IMPRIMA");
		Terminal SENAO = new Terminal(13, "snao", "SENAO");
		Terminal SE = new Terminal(14, "se", "SE");
		Terminal BOOLEANOS = new Terminal(15, "V|F", "BOOLEANOS");
		Terminal CHAMA = new Terminal(16, "chama", "CHAMA");
		Terminal PARE = new Terminal(17, "pare", "PARE");
		Terminal CONTINUE = new Terminal(18, "continue", "CONTINUE");
		Terminal MAIS = new Terminal(19, "\\+", "MAIS");
		Terminal MENOS = new Terminal(20, "\\-", "MENOS");
		Terminal VEZES = new Terminal(21, "\\*", "VEZES");
		Terminal DIVIDIDO = new Terminal(22, "\\/", "DIVIDIDO");
		Terminal MENORQ = new Terminal(23, "\\<", "MENORQ");
		Terminal MENORQIGUAL = new Terminal(24, "<=", "MENORQIGUAL");
		Terminal MAIORQ = new Terminal(25, ">", "MAIORQ");
		Terminal MAIORQIGUAL = new Terminal(26, ">=", "MAIORQIGUAL");
		Terminal DIFERENTE = new Terminal(27, "!=", "DIFERENTE");
		Terminal IGUAL = new Terminal(28, "=", "IGUAL");
		Terminal VIRGULA = new Terminal(29, ",", "VIRGULA");
		Terminal NUMEROS = new Terminal(30, "[0-9]+", "NUMEROS");
		Terminal ID = new Terminal(31, "[a-z][a-zA-Z0-9]*", "ID");

		lexicalAnalyzer = new LexicalAnalyzer(source);

		// Adicionando terminais ao analisador léxico
		lexicalAnalyzer.addTerminal(executa);
		lexicalAnalyzer.addTerminal(abre_parenteses);
		lexicalAnalyzer.addTerminal(fecha_parenteses);
		lexicalAnalyzer.addTerminal(abre_chaves);
		lexicalAnalyzer.addTerminal(fecha_chaves);
		lexicalAnalyzer.addTerminal(INTEIRO);
		lexicalAnalyzer.addTerminal(BOOLEANO);
		lexicalAnalyzer.addTerminal(VAZIO);
		lexicalAnalyzer.addTerminal(PONTOVIRGULA);
		lexicalAnalyzer.addTerminal(ATRIBUIDOR);
		lexicalAnalyzer.addTerminal(RETORNE);
		lexicalAnalyzer.addTerminal(ENQUANTO);
		lexicalAnalyzer.addTerminal(IMPRIMA);
		lexicalAnalyzer.addTerminal(SE);
		lexicalAnalyzer.addTerminal(SENAO);
		lexicalAnalyzer.addTerminal(BOOLEANOS);
		lexicalAnalyzer.addTerminal(CHAMA);
		lexicalAnalyzer.addTerminal(PARE);
		lexicalAnalyzer.addTerminal(CONTINUE);
		lexicalAnalyzer.addTerminal(MAIS);
		lexicalAnalyzer.addTerminal(MENOS);
		lexicalAnalyzer.addTerminal(VEZES);
		lexicalAnalyzer.addTerminal(DIVIDIDO);
		lexicalAnalyzer.addTerminal(MENORQ);
		lexicalAnalyzer.addTerminal(MENORQIGUAL);
		lexicalAnalyzer.addTerminal(MAIORQ);
		lexicalAnalyzer.addTerminal(MAIORQIGUAL);
		lexicalAnalyzer.addTerminal(DIFERENTE);
		lexicalAnalyzer.addTerminal(IGUAL);
		lexicalAnalyzer.addTerminal(VIRGULA);
		lexicalAnalyzer.addTerminal(NUMEROS);
		lexicalAnalyzer.addTerminal(ID);

		syntaxAnalyzer = new SyntaxAnalyzer(lexicalAnalyzer);
		semanticAnalyzer = new SemanticAnalyzer();

		// Variaveis utilizadas na gramática
		NonTerminal escopo = new NonTerminal("escopo");
		NonTerminal separa_escopo = new NonTerminal("separa_escopo");
		NonTerminal declara = new NonTerminal("declara");
		NonTerminal parametros = new NonTerminal("parametros");
		NonTerminal separa_parametros = new NonTerminal("separa_parametros");
		NonTerminal escopo_funcao = new NonTerminal("escopo_funcao");
		NonTerminal separa_escopo_funcao = new NonTerminal(
				"separa_escopo_funcao");
		NonTerminal ID_chamada_NUMERO = new NonTerminal("ID_chamada_NUMERO");
		NonTerminal ID_chamada = new NonTerminal("ID_chamada");
		NonTerminal assinatura = new NonTerminal("assinatura");
		NonTerminal diversas_atribuicoes = new NonTerminal(
				"diversas_atribuicoes");
		NonTerminal comparacao = new NonTerminal("comparacao");
		NonTerminal assinatura_argumentos = new NonTerminal(
				"assinatura_argumentos");
		NonTerminal argumentos = new NonTerminal("argumentos");
		NonTerminal separa_argumentos = new NonTerminal("separa_argumentos");
		NonTerminal escopo_loop = new NonTerminal("escopo_loop");
		NonTerminal separa_escopo_loop = new NonTerminal("separa_escopo_loop");
		NonTerminal escopo_condicional = new NonTerminal("escopo_condicional");
		NonTerminal separa_escopo_condicional = new NonTerminal(
				"separa_escopo_condicional");
		NonTerminal operacao = new NonTerminal("operacao");
		NonTerminal outra_operacao = new NonTerminal("outra_operacao");
		NonTerminal chamada_comparacao_operacao = new NonTerminal(
				"chamada_comparacao_operacao");
		NonTerminal separa = new NonTerminal("separa");
		NonTerminal ID_chamada_NUMERO_BOOL = new NonTerminal(
				"ID_chamada_NUMERO_BOOL");
		NonTerminal comparador = new NonTerminal("comparador");
		NonTerminal operador = new NonTerminal("operador");
		// construção da gramática
		escopo.addProduction(
				executa.and(abre_parenteses).and(fecha_parenteses)
						.and(abre_chaves).and(escopo_funcao).and(fecha_chaves)
						.and(separa_escopo), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String escopo_funcao = node.getWriteJava(4);
						String separa_escopo = node.getWriteJava(6);
						return "public static void main(String args[]){\n"
								+ escopo_funcao + "\n}\n" + separa_escopo;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						Funcao executa = new Funcao("executa", null);
						if (semanticAnalyzer.isExistsExecuta()) {
							throw new SemanticException(
									"método executa duplicado!");
						} else {
							semanticAnalyzer.setExistsExecuta(true);
						}
						node.doChildAction(4, executa);
						node.doChildAction(6, object);

					}
				});
		escopo.addProduction(INTEIRO.and(ID).and(declara).and(separa_escopo),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(1);
						String declara = node.getWriteJava(2);
						String separa_escopo = node.getWriteJava(3);
						return "static int " + id + declara + separa_escopo;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						Tipo tipo = new Tipo("inteiro");
						String id = node.getTokenExpression(1);
						Definicao definicao = new Definicao(id, tipo);
						if (semanticAnalyzer.getDefinicoes()
								.contains(definicao)) {
							throw new DuplicateDefinicao(id);
						} else {
							node.doChildAction(2, definicao);
							node.doChildAction(3, object);
						}

					}
				});
		escopo.addProduction(BOOLEANO.and(ID).and(declara).and(separa_escopo),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						Leaf id = (Leaf) node.getChildren().get(1);
						NonLeaf declara = (NonLeaf) node.getChildren().get(2);
						NonLeaf separa_escopo = (NonLeaf) node.getChildren()
								.get(3);
						return "static boolean "
								+ id.getToken().getExpression()
								+ declara.getProduction().getSemanticAction()
										.writeJava(declara)
								+ separa_escopo.getProduction()
										.getSemanticAction()
										.writeJava(separa_escopo);
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						Tipo tipo = new Tipo("booleano");
						String id = node.getTokenExpression(1);
						Definicao definicao = new Definicao(id, tipo);
						if (semanticAnalyzer.getDefinicoes()
								.contains(definicao)) {
							throw new DuplicateDefinicao(id);
						} else {
//							semanticAnalyzer.getDefinicoes().add(definicao);
							node.doChildAction(2, definicao);
							node.doChildAction(3, object);
						}

					}
				});
		escopo.addProduction(
				VAZIO.and(ID).and(abre_parenteses).and(assinatura)
						.and(abre_chaves).and(escopo_funcao).and(fecha_chaves)
						.and(separa_escopo), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						Leaf id = (Leaf) node.getChildren().get(1);
						NonLeaf assinatura = (NonLeaf) node.getChildren()
								.get(3);
						NonLeaf escopo_funcao = (NonLeaf) node.getChildren()
								.get(5);
						NonLeaf separa_escopo = (NonLeaf) node.getChildren()
								.get(7);
						Tipo tipo = new Tipo("vazio");
						Funcao funcao = new Funcao(id.getToken()
								.getExpression(), tipo);
						semanticAnalyzer.getDefinicoes().add(funcao);
						return "static void "
								+ id.getToken().getExpression()
								+ "("
								+ assinatura.getProduction()
										.getSemanticAction()
										.writeJava(assinatura)
								+ "{\n"
								+ escopo_funcao.getProduction()
										.getSemanticAction()
										.writeJava(escopo_funcao)
								+ "\n}\n"
								+ separa_escopo.getProduction()
										.getSemanticAction()
										.writeJava(separa_escopo);
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						Tipo tipo = new Tipo("vazio");
						String id = node.getTokenExpression(1);
						Funcao definicao = new Funcao(id, tipo);
						if (semanticAnalyzer.getDefinicoes()
								.contains(definicao)) {
							throw new DuplicateDefinicao(id);
						} else {
							semanticAnalyzer.getDefinicoes().add(definicao);
							node.doChildAction(3, definicao);
							node.doChildAction(5, definicao);
							node.doChildAction(7, object);
						}

					}
				});

		/**
		 * separa_escopo
		 */

		separa_escopo.addProduction(
				executa.and(abre_parenteses).and(fecha_parenteses)
						.and(abre_chaves).and(escopo_funcao).and(fecha_chaves)
						.and(separa_escopo), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String escopo_funcao = node.getWriteJava(4);
						String separa_escopo = node.getWriteJava(6);
						return "public static void main(String args[]){\n"
								+ escopo_funcao + "\n}" + separa_escopo;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						Funcao executa = new Funcao("executa", null);
						if (semanticAnalyzer.isExistsExecuta()) {
							throw new SemanticException(
									"método executa duplicado!");
						} else {
							semanticAnalyzer.setExistsExecuta(true);
						}
						node.doChildAction(4, executa);
						node.doChildAction(6, object);

					}
				});
		separa_escopo.addProduction(
				INTEIRO.and(ID).and(declara).and(separa_escopo),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(1);
						String declara = node.getWriteJava(2);
						String separa_escopo = node.getWriteJava(3);
						return "static int " + id + declara + separa_escopo;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						Tipo tipo = new Tipo("inteiro");
						String id = node.getTokenExpression(1);
						Definicao definicao = new Definicao(id, tipo);
						if (semanticAnalyzer.getDefinicoes()
								.contains(definicao)) {
							throw new DuplicateDefinicao(id);
						} else {
//							semanticAnalyzer.getDefinicoes().add(definicao);
							node.doChildAction(2, definicao);
							node.doChildAction(3, object);
						}

					}
				});
		separa_escopo.addProduction(
				BOOLEANO.and(ID).and(declara).and(separa_escopo),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						Leaf id = (Leaf) node.getChildren().get(1);
						NonLeaf declara = (NonLeaf) node.getChildren().get(2);
						NonLeaf separa_escopo = (NonLeaf) node.getChildren()
								.get(3);
						return "static boolean "
								+ id.getToken().getExpression()
								+ declara.getProduction().getSemanticAction()
										.writeJava(declara)
								+ separa_escopo.getProduction()
										.getSemanticAction()
										.writeJava(separa_escopo);
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						Tipo tipo = new Tipo("booleano");
						String id = node.getTokenExpression(1);
						Definicao definicao = new Definicao(id, tipo);
						if (semanticAnalyzer.getDefinicoes()
								.contains(definicao)) {
							throw new DuplicateDefinicao(id);
						} else {
							semanticAnalyzer.getDefinicoes().add(definicao);
							node.doChildAction(2, definicao);
							node.doChildAction(3, object);
						}

					}
				});
		separa_escopo.addProduction(
				VAZIO.and(ID).and(abre_parenteses).and(assinatura)
						.and(abre_chaves).and(escopo_funcao).and(fecha_chaves)
						.and(separa_escopo), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						Leaf id = (Leaf) node.getChildren().get(1);
						NonLeaf assinatura = (NonLeaf) node.getChildren()
								.get(3);
						NonLeaf escopo_funcao = (NonLeaf) node.getChildren()
								.get(5);
						NonLeaf separa_escopo = (NonLeaf) node.getChildren()
								.get(7);
						Tipo tipo = new Tipo("vazio");
						Funcao funcao = new Funcao(id.getToken()
								.getExpression(), tipo);
						semanticAnalyzer.getDefinicoes().add(funcao);
						return "static void "
								+ id.getToken().getExpression()
								+ "("
								+ assinatura.getProduction()
										.getSemanticAction()
										.writeJava(assinatura)
								+ "{\n"
								+ escopo_funcao.getProduction()
										.getSemanticAction()
										.writeJava(escopo_funcao)
								+ "\n}\n"
								+ separa_escopo.getProduction()
										.getSemanticAction()
										.writeJava(separa_escopo);
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						Tipo tipo = new Tipo("vazio");
						String id = node.getTokenExpression(1);
						Funcao definicao = new Funcao(id, tipo);
						if (semanticAnalyzer.getDefinicoes()
								.contains(definicao)) {
							throw new DuplicateDefinicao(id);
						} else {
							semanticAnalyzer.getDefinicoes().add(definicao);
							node.doChildAction(3, definicao);
							node.doChildAction(5, definicao);
							node.doChildAction(7, object);
						}

					}
				});

		separa_escopo.addProduction(Terminal.BLANK, new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				return "";
			}

			@Override
			public void doAction(NonLeaf node, Object object) {

			}
		});

		declara.addProduction(abre_parenteses.and(assinatura).and(abre_chaves)
				.and(escopo_funcao).and(fecha_chaves), new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				String assinatura = ((NonLeaf) node.getChildren().get(1))
						.getProduction().getSemanticAction()
						.writeJava((NonLeaf) node.getChildren().get(1));
				String escopo_funcao = ((NonLeaf) node.getChildren().get(3))
						.getProduction().getSemanticAction()
						.writeJava((NonLeaf) node.getChildren().get(3));
				return "(" + assinatura + "{\n" + "\t" + escopo_funcao
						+ "\n}\n";
			}

			@Override
			public void doAction(NonLeaf node, Object object)
					throws SemanticException {
				Definicao definicao = (Definicao) object;
				Funcao funcao = new Funcao(definicao.getVariavel(), definicao
						.getTipo());
				semanticAnalyzer.getDefinicoes().add(funcao);
				node.doChildAction(1, funcao);
				node.doChildAction(3, funcao);
				if(!funcao.isHaveRetorno()){
					throw new SemanticException("Função "+funcao.getVariavel()+" sem retorno");
				}
			}
		});
		declara.addProduction(PONTOVIRGULA, new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				return ";\n";
			}

			@Override
			public void doAction(NonLeaf node, Object object) {
				Definicao definicao = (Definicao) object;
				semanticAnalyzer.getDefinicoes().add(definicao);
			}
		});

		parametros.addProduction(INTEIRO.and(ID).and(separa_parametros),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(1);
						String separa_parametros = node.getWriteJava(2);
						return "int " + id + separa_parametros;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						// faz o cast do objeto passado para funcao
						Funcao funcao = (Funcao) object;
						// cria o parametro e o adiciona na lista de parametros
						// da funcao
						Parametro parametro = new Parametro(node
								.getTokenExpression(1), new Tipo("inteiro"));
						funcao.getParametros().add(parametro);
						node.doChildAction(2, funcao);
					}
				});
		parametros.addProduction(BOOLEANO.and(ID).and(separa_parametros),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(1);
						String separa_parametros = node.getWriteJava(2);
						return "boolean " + id + separa_parametros;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						// faz o cast do objeto passado para funcao
						Funcao funcao = (Funcao) object;
						// cria o parametro e o adiciona na lista de parametros
						// da funcao
						Parametro parametro = new Parametro(node
								.getTokenExpression(1), new Tipo("booleano"));
						funcao.getParametros().add(parametro);
						node.doChildAction(2, funcao);
					}
				});

		separa_parametros.addProduction(VIRGULA.and(parametros),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String parametros = node.getWriteJava(1);
						return ", " + parametros;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(1, object);
					}
				});
		separa_parametros.addProduction(Terminal.BLANK, new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {

				return "";
			}

			@Override
			public void doAction(NonLeaf node, Object object) {

			}
		});

		escopo_funcao.addProduction(
				INTEIRO.and(ID).and(PONTOVIRGULA).and(separa_escopo_funcao),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(1);
						String separa_escopo_funcao = node.getWriteJava(3);
						return "\tint " + id + ";" + "\n\t"
								+ separa_escopo_funcao;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						Funcao funcao = (Funcao) object;
						String id = node.getTokenExpression(1);
						Definicao definicao = new Definicao(id, new Tipo(
								"inteiro"));
						if (semanticAnalyzer.getDefinicoes()
								.contains(definicao)
								|| funcao.getParametros().contains(definicao)) {
							throw new DuplicateDefinicao(id);
						} else {
							semanticAnalyzer.getDefinicoes().add(definicao);
							node.doChildAction(3, object);
						}
					}
				});

		escopo_funcao.addProduction(
				BOOLEANO.and(ID).and(PONTOVIRGULA).and(separa_escopo_funcao),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(1);
						String separa_escopo_funcao = node.getWriteJava(3);
						return "boolean " + id + ";" + "\n\t"
								+ separa_escopo_funcao;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						Funcao funcao = (Funcao) object;
						String id = node.getTokenExpression(1);
						Definicao definicao = new Definicao(id, new Tipo(
								"booleano"));
						if (semanticAnalyzer.getDefinicoes()
								.contains(definicao)
								|| funcao.getParametros().contains(definicao)) {
							throw new DuplicateDefinicao(id);
						} else {
							semanticAnalyzer.getDefinicoes().add(definicao);
							node.doChildAction(3, object);
						}
					}
				});

		escopo_funcao.addProduction(
				ID.and(ATRIBUIDOR).and(diversas_atribuicoes).and(PONTOVIRGULA)
						.and(separa_escopo_funcao), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(0);
						String diversas_atribuicoes = node.getWriteJava(2);
						String separa_escopo_funcao = node.getWriteJava(4);
						return id + " = " + diversas_atribuicoes + ";\n\t"
								+ separa_escopo_funcao;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						Funcao funcao = (Funcao) object;
						List<Object> l = new ArrayList<>();
						l.add(funcao);
						String id = node.getTokenExpression(0);
						Definicao definicaoTest = new Definicao(id,
								new Tipo(""));
						if (semanticAnalyzer.getDefinicoes().contains(
								definicaoTest)) {
							Definicao definicao = semanticAnalyzer
									.getDefinicoes().get(
											semanticAnalyzer.getDefinicoes()
													.indexOf(definicaoTest));
							l.add(definicao);
//							if (definicao.getTipo().getTipoNome()
//									.equals("inteiro")) {
								node.doChildAction(2, l);
								node.doChildAction(4, object);
//							} else {
//								throw new SemanticException("Tipo de " + id
//										+ " deveria ser inteiro");
//							}
						} else {
							if (funcao.getParametros().contains(definicaoTest)) {
								Definicao definicao = funcao.getParametros()
										.get(funcao.getParametros().indexOf(
												definicaoTest));
								l.add(definicao);
//								if (definicao.getTipo().getTipoNome()
//										.equals("inteiro")) {
									node.doChildAction(2, l);
									node.doChildAction(4, object);
//								} else {
//									throw new SemanticException("Tipo de " + id
//											+ " deveria ser inteiro");
//								}
							} else {
								throw new SemanticException("Identificador "
										+ id + " não declarado");
							}
						}
					}
				});

		escopo_funcao.addProduction(
				RETORNE.and(abre_parenteses).and(ID_chamada_NUMERO_BOOL)
						.and(fecha_parenteses).and(PONTOVIRGULA)
						.and(separa_escopo_funcao), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO_BOOL = node.getWriteJava(2);
						String separa_escopo_funcao = node.getWriteJava(5);
						return "return(" + ID_chamada_NUMERO_BOOL + ");\n\t"
								+ separa_escopo_funcao;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						Funcao funcao = (Funcao) object;
						funcao.setHaveRetorno(true);
						node.doChildAction(2, object);
						node.doChildAction(5, object);
					}
				});

		escopo_funcao.addProduction(
				ENQUANTO.and(abre_parenteses).and(comparacao)
						.and(fecha_parenteses).and(abre_chaves)
						.and(escopo_loop).and(fecha_chaves)
						.and(separa_escopo_funcao), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String comparacao = node.getWriteJava(2);
						String escopo_loop = node.getWriteJava(5);
						String separa_escopo_funcao = node.getWriteJava(7);
						return "while(" + comparacao + "){\n\t" + escopo_loop
								+ "\n\t}" + separa_escopo_funcao;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
						node.doChildAction(5, object);
						node.doChildAction(7, object);
					}
				});

		escopo_funcao.addProduction(
				IMPRIMA.and(abre_parenteses).and(ID_chamada_NUMERO_BOOL)
						.and(fecha_parenteses).and(PONTOVIRGULA)
						.and(separa_escopo_funcao), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO_BOOL = node.getWriteJava(2);
						String separa_escopo_funcao = node.getWriteJava(5);
						return "System.out.println(" + ID_chamada_NUMERO_BOOL
								+ ");" + "\n\t" + separa_escopo_funcao;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
						node.doChildAction(5, object);
					}
				});

		escopo_funcao.addProduction(SE.and(abre_parenteses).and(comparacao)
				.and(fecha_parenteses).and(abre_chaves).and(escopo_condicional)
				.and(fecha_chaves).and(separa_escopo_funcao),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String comparacao = node.getWriteJava(2);
						String escopo_condicional = node.getWriteJava(5);
						String separa_escopo_funcao = node.getWriteJava(7);
						return "if(" + comparacao + "){\n\t"
								+ escopo_condicional + "\n\t}"
								+ separa_escopo_funcao;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
						node.doChildAction(5, object);
						node.doChildAction(7, object);
					}
				});

		escopo_funcao.addProduction(
				SENAO.and(abre_chaves).and(escopo_condicional)
						.and(fecha_chaves).and(separa_escopo_funcao),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String escopo_condicional = node.getWriteJava(2);
						String separa_escopo_funcao = node.getWriteJava(4);
						return "else{\n\t" + escopo_condicional + "\n\t}"
								+ separa_escopo_funcao;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
						node.doChildAction(4, object);
					}
				});
		
		escopo_funcao.addProduction(CHAMA.and(abre_parenteses).and(ID).and(abre_parenteses).and(assinatura_argumentos).and(fecha_parenteses).and(PONTOVIRGULA).and(separa_escopo_funcao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(2);
				String assinatura_argumentos = node.getWriteJava(4);
				String separa_escopo_funcao = node.getWriteJava(7);
				return id+"("+assinatura_argumentos+";\n\t"+separa_escopo_funcao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) throws SemanticException {				
				String id = node.getTokenExpression(2);
				Definicao definicaoTest = new Definicao(id, new Tipo(""));
				if(semanticAnalyzer.getDefinicoes().contains(definicaoTest)){
					Definicao definicao = semanticAnalyzer.getDefinicoes().get(semanticAnalyzer.getDefinicoes().indexOf(definicaoTest));
					if(definicao instanceof Funcao){
						List<Object> l = new ArrayList<Object>();
						l.add(object);
						l.add(definicao);
						node.doChildAction(4, l);
						Funcao funcao = (Funcao)object;
						if(funcao.getPosition() < funcao.getParametros().size() || funcao.getPosition() > funcao.getParametros().size()){
							throw new SemanticException("Quantidade de argumentos esperada era "+funcao.getParametros().size());
						} else {
							funcao.setPosition(0);
						}
						node.doChildAction(7, object);
					} else {
						throw new SemanticException(id+" deveria ser uma função ou procedimento!");
					}
				} else {
					throw new SemanticException("Identificador "+id+" não declarado");
				}
				
			}
		});

		/**
		 * separa_escopo_funcao
		 */

		separa_escopo_funcao.addProduction(INTEIRO.and(ID).and(PONTOVIRGULA)
				.and(separa_escopo_funcao), new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(1);
				String separa_escopo_funcao = node.getWriteJava(3);
				return "\tint " + id + ";" + "\n\t" + separa_escopo_funcao;
			}

			@Override
			public void doAction(NonLeaf node, Object object)
					throws SemanticException {
				Funcao funcao = (Funcao) object;
				String id = node.getTokenExpression(1);
				Definicao definicao = new Definicao(id, new Tipo("inteiro"));
				if (semanticAnalyzer.getDefinicoes().contains(definicao)
						|| funcao.getParametros().contains(definicao)) {
					throw new DuplicateDefinicao(id);
				} else {
					semanticAnalyzer.getDefinicoes().add(definicao);
					node.doChildAction(3, object);
				}
			}
		});

		separa_escopo_funcao.addProduction(BOOLEANO.and(ID).and(PONTOVIRGULA)
				.and(separa_escopo_funcao), new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(1);
				String separa_escopo_funcao = node.getWriteJava(3);
				return "boolean " + id + ";" + "\n\t" + separa_escopo_funcao;
			}

			@Override
			public void doAction(NonLeaf node, Object object)
					throws SemanticException {
				Funcao funcao = (Funcao) object;
				String id = node.getTokenExpression(1);
				Definicao definicao = new Definicao(id, new Tipo("booleano"));
				if (semanticAnalyzer.getDefinicoes().contains(definicao)
						|| funcao.getParametros().contains(definicao)) {
					throw new DuplicateDefinicao(id);
				} else {
					semanticAnalyzer.getDefinicoes().add(definicao);
					node.doChildAction(3, object);
				}
			}
		});

		separa_escopo_funcao.addProduction(
				ID.and(ATRIBUIDOR).and(diversas_atribuicoes).and(PONTOVIRGULA)
						.and(separa_escopo_funcao), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(0);
						String diversas_atribuicoes = node.getWriteJava(2);
						String separa_escopo_funcao = node.getWriteJava(4);
						return id + " = " + diversas_atribuicoes + ";\n\t"
								+ separa_escopo_funcao;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						Funcao funcao = (Funcao) object;
						List<Object> l = new ArrayList<>();
						l.add(funcao);
						String id = node.getTokenExpression(0);
						Definicao definicaoTest = new Definicao(id,
								new Tipo(""));
						if (semanticAnalyzer.getDefinicoes().contains(
								definicaoTest)) {
							Definicao definicao = semanticAnalyzer
									.getDefinicoes().get(
											semanticAnalyzer.getDefinicoes()
													.indexOf(definicaoTest));
							l.add(definicao);
//							if (definicao.getTipo().getTipoNome()
//									.equals("inteiro")) {
								node.doChildAction(2, l);
								node.doChildAction(4, object);
//							} else {
//								throw new SemanticException("Tipo de " + id
//										+ " deveria ser inteiro");
//							}
						} else {
							if (funcao.getParametros().contains(definicaoTest)) {
								Definicao definicao = funcao.getParametros()
										.get(funcao.getParametros().indexOf(
												definicaoTest));
								l.add(definicao);
//								if (definicao.getTipo().getTipoNome()
//										.equals("inteiro")) {
									node.doChildAction(2, l);
									node.doChildAction(4, object);
//								} else {
//									throw new SemanticException("Tipo de " + id
//											+ " deveria ser inteiro");
//								}
							} else {
								throw new SemanticException("Identificador "
										+ id + " não declarado");
							}
						}
					}
				});

		separa_escopo_funcao.addProduction(
				RETORNE.and(abre_parenteses).and(ID_chamada_NUMERO_BOOL)
						.and(fecha_parenteses).and(PONTOVIRGULA)
						.and(separa_escopo_funcao), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO_BOOL = node.getWriteJava(2);
						String separa_escopo_funcao = node.getWriteJava(5);
						return "return(" + ID_chamada_NUMERO_BOOL + ");\n\t"
								+ separa_escopo_funcao;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						Funcao funcao = (Funcao) object;
						funcao.setHaveRetorno(true);
						node.doChildAction(2, object);
						node.doChildAction(5, object);
					}
				});

		separa_escopo_funcao.addProduction(
				ENQUANTO.and(abre_parenteses).and(comparacao)
						.and(fecha_parenteses).and(abre_chaves)
						.and(escopo_loop).and(fecha_chaves)
						.and(separa_escopo_funcao), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String comparacao = node.getWriteJava(2);
						String escopo_loop = node.getWriteJava(5);
						String separa_escopo_funcao = node.getWriteJava(7);
						return "while(" + comparacao + "){\n\t" + escopo_loop
								+ "\n\t}" + separa_escopo_funcao;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
						node.doChildAction(5, object);
						node.doChildAction(7, object);
					}
				});

		separa_escopo_funcao.addProduction(
				IMPRIMA.and(abre_parenteses).and(ID_chamada_NUMERO_BOOL)
						.and(fecha_parenteses).and(PONTOVIRGULA)
						.and(separa_escopo_funcao), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO_BOOL = node.getWriteJava(2);
						String separa_escopo_funcao = node.getWriteJava(5);
						return "System.out.println(" + ID_chamada_NUMERO_BOOL
								+ ");" + "\n\t" + separa_escopo_funcao;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
						node.doChildAction(5, object);
					}
				});

		separa_escopo_funcao.addProduction(
				SE.and(abre_parenteses).and(comparacao).and(fecha_parenteses)
						.and(abre_chaves).and(escopo_condicional)
						.and(fecha_chaves).and(separa_escopo_funcao),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String comparacao = node.getWriteJava(2);
						String escopo_condicional = node.getWriteJava(5);
						String separa_escopo_funcao = node.getWriteJava(7);
						return "if(" + comparacao + "){\n\t"
								+ escopo_condicional + "\n\t}"
								+ separa_escopo_funcao;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
						node.doChildAction(5, object);
						node.doChildAction(7, object);
					}
				});

		separa_escopo_funcao.addProduction(
				SENAO.and(abre_chaves).and(escopo_condicional)
						.and(fecha_chaves).and(separa_escopo_funcao),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String escopo_condicional = node.getWriteJava(2);
						String separa_escopo_funcao = node.getWriteJava(4);
						return "else{\n\t" + escopo_condicional + "\n\t}"
								+ separa_escopo_funcao;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
						node.doChildAction(4, object);
					}
				});
		
		separa_escopo_funcao.addProduction(CHAMA.and(abre_parenteses).and(ID).and(abre_parenteses).and(assinatura_argumentos).and(fecha_parenteses).and(PONTOVIRGULA).and(separa_escopo_funcao), new SemanticAction() {
			
			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(2);
				String assinatura_argumentos = node.getWriteJava(4);
				String separa_escopo_funcao = node.getWriteJava(7);
				return id+"("+assinatura_argumentos+";\n\t"+separa_escopo_funcao;
			}
			
			@Override
			public void doAction(NonLeaf node, Object object) throws SemanticException {				
				String id = node.getTokenExpression(2);
				Definicao definicaoTest = new Definicao(id, new Tipo(""));
				if(semanticAnalyzer.getDefinicoes().contains(definicaoTest)){
					Definicao definicao = semanticAnalyzer.getDefinicoes().get(semanticAnalyzer.getDefinicoes().indexOf(definicaoTest));
					if(definicao instanceof Funcao){
						List<Object> l = new ArrayList<Object>();
						l.add(object);
						l.add(definicao);
						node.doChildAction(4, l);
						Funcao funcao = (Funcao)object;
						if(funcao.getPosition() < funcao.getParametros().size() || funcao.getPosition() > funcao.getParametros().size()){
							throw new SemanticException("Quantidade de argumentos esperada era "+funcao.getParametros().size());
						} else {
							funcao.setPosition(0);
						}
						node.doChildAction(7, object);
					} else {
						throw new SemanticException(id+" deveria ser uma função ou procedimento!");
					}
				} else {
					throw new SemanticException("Identificador "+id+" não declarado");
				}
				
			}
		});

		separa_escopo_funcao.addProduction(Terminal.BLANK,
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						return "";
					}

					@Override
					public void doAction(NonLeaf node, Object object) {

					}
				});

		ID_chamada_NUMERO.addProduction(ID.and(ID_chamada),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(0);
						String ID_chamada = node.getWriteJava(1);
						return id + ID_chamada;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						Funcao funcao = (Funcao) object;
						String id = node.getTokenExpression(0);
						Definicao definicaoTest = new Definicao(id,
								new Tipo(""));
						if (semanticAnalyzer.getDefinicoes().contains(definicaoTest)) {
							Definicao definicao = semanticAnalyzer.getDefinicoes().get(semanticAnalyzer.getDefinicoes().indexOf(definicaoTest));
							if (definicao.getTipo().getTipoNome().equals("inteiro")) {
								List<Object> l = new ArrayList<>();
								l.add(funcao);
								l.add(definicao);
								node.doChildAction(1, l);
							} else {
								throw new SemanticException("Tipo de " + id
										+ " não é inteiro");
							}

						} else {
							if (funcao.getParametros().contains(definicaoTest)) {
								Definicao definicao = funcao.getParametros()
										.get(funcao.getParametros().indexOf(
												definicaoTest));
								if (definicao.getTipo().getTipoNome()
										.equals("inteiro")) {
									List<Object> l = new ArrayList<>();
									l.add(funcao);
									l.add(definicao);
									node.doChildAction(1, l);
								} else {
									throw new SemanticException("Tipo de " + id
											+ " não é inteiro");
								}
							} else {
								throw new SemanticException("Identificador "
										+ id + " não declarado");
							}
						}
					}
				});
		ID_chamada_NUMERO.addProduction(NUMEROS, new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				String numeros = node.getTokenExpression(0);
				return numeros;
			}

			@Override
			public void doAction(NonLeaf node, Object object) {

			}
		});

		ID_chamada.addProduction(abre_parenteses.and(assinatura_argumentos),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String assinatura_argumentos = node.getWriteJava(1);
						return "(" + assinatura_argumentos;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						List<Object> l = (List<Object>)object;
						if(l.get(1) instanceof Funcao){
							Funcao funcao = (Funcao)l.get(1);
							List<Object> l2 = new ArrayList<>();
							node.doChildAction(1, object);
							if(funcao.getPosition() < funcao.getParametros().size() || funcao.getPosition() > funcao.getParametros().size()){
								throw new SemanticException("Quantidade de argumentos esperada era "+funcao.getParametros().size());
							} else {
								funcao.setPosition(0);
							}
						} else {
							throw new SemanticException("Era esperada uma função!");
						}
					}
				});

		ID_chamada.addProduction(Terminal.BLANK, new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				return "";
			}

			@Override
			public void doAction(NonLeaf node, Object object) {

			}
		});

		assinatura.addProduction(fecha_parenteses, new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				return ")";
			}

			@Override
			public void doAction(NonLeaf node, Object object) {

			}
		});

		assinatura.addProduction(
				INTEIRO.and(ID).and(separa_parametros).and(fecha_parenteses),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(1);
						String separa_parametros = node.getWriteJava(2);
						return "int " + id + separa_parametros + ")";
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						String id = node.getTokenExpression(1);
						Funcao funcao = (Funcao) object;
						Parametro parametro = new Parametro(id, new Tipo(
								"inteiro"));
						funcao.getParametros().add(parametro);
						node.doChildAction(2, object);
					}
				});

		assinatura.addProduction(
				BOOLEANO.and(ID).and(separa_parametros).and(fecha_parenteses),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(1);
						String separa_parametros = node.getWriteJava(2);
						return "boolean " + id + separa_parametros + ")";
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						String id = node.getTokenExpression(1);
						Funcao funcao = (Funcao) object;
						Parametro parametro = new Parametro(id, new Tipo(
								"booleano"));
						funcao.getParametros().add(parametro);
						node.doChildAction(2, object);
					}
				});

		diversas_atribuicoes.addProduction(ID.and(chamada_comparacao_operacao),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(0);
						String chamada_comparacao_operacao = node
								.getWriteJava(1);
						return id + chamada_comparacao_operacao;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						List<Object> l = (List<Object>)object;
						Funcao funcao = (Funcao) l.get(0);
						Definicao definicaoEsquerda = (Definicao) l.get(1);
						String id = node.getTokenExpression(0);
						Definicao definicaoTest = new Definicao(id,
								new Tipo(""));
						if (semanticAnalyzer.getDefinicoes().contains(definicaoTest)) {
							Definicao definicao = semanticAnalyzer.getDefinicoes().get(semanticAnalyzer.getDefinicoes().indexOf(definicaoTest));
							if (definicao.getTipo().getTipoNome().equals(definicaoEsquerda.getTipo().getTipoNome())) {
								List<Object> l2 = new ArrayList<>();
								l2.add(l.get(0));
								l2.add(definicao);
								node.doChildAction(1, l2);
							} else {
								throw new SemanticException("Tipo de " + id	+ " deveria ser "+definicaoEsquerda.getTipo().getTipoNome());
							}
						} else {
							if (funcao.getParametros().contains(definicaoTest)) {
								Definicao definicao = funcao.getParametros()
										.get(funcao.getParametros().indexOf(
												definicaoTest));
								if (definicao.getTipo().getTipoNome().equals(definicaoEsquerda.getTipo().getTipoNome())) {
									List<Object> l2 = new ArrayList<>();
									l2.add(l.get(0));
									l2.add(definicao);
									node.doChildAction(1, l2);
								} else {
									throw new SemanticException("Tipo de " + id	+ " deveria ser "+definicaoEsquerda.getTipo().getTipoNome());
								}
							} else {
								throw new SemanticException("Identificador "
										+ id + " não declarado");
							}
						}
					}
				});

		diversas_atribuicoes.addProduction(NUMEROS.and(separa),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String numeros = node.getTokenExpression(0);
						String separa = node.getWriteJava(1);
						return numeros + separa;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						List<Object> l = (List<Object>)object;
						Funcao funcao = (Funcao) l.get(0);
						Definicao definicaoEsquerda = (Definicao)l.get(1);
						if(definicaoEsquerda.getTipo().getTipoNome().equals("inteiro")){
							node.doChildAction(1, funcao);
						} else {
							throw new SemanticException("O identificador "+ definicaoEsquerda.getVariavel()+ " não é do tipo inteiro");
						}
					}
				});

		diversas_atribuicoes.addProduction(BOOLEANOS, new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				String booleanos = node.getTokenExpression(0);
				if(booleanos.equals("V")){
					return "true";
				} else {
					return "false";
				}
			}

			@Override
			public void doAction(NonLeaf node, Object object) throws SemanticException {
				List<Object> l = (List<Object>)object;
				Definicao definicaoEsquerda = (Definicao)l.get(1);
				if(!(definicaoEsquerda.getTipo().getTipoNome().equals("booleano"))){
					throw new SemanticException("O identificador "+ definicaoEsquerda.getVariavel()+ " não é do tipo booleano");
				}
			}
		});

		comparacao.addProduction(
				NUMEROS.and(comparador).and(ID_chamada_NUMERO),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String numeros = node.getTokenExpression(0);
						String comparador = node.getWriteJava(1);
						String ID_chamada_NUMERO = node.getWriteJava(2);
						return numeros + comparador + ID_chamada_NUMERO;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
					}
				});

		comparacao.addProduction(BOOLEANOS, new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				String booleanos = node.getTokenExpression(0);
				if(booleanos.equals("V")){
					return "true";
				} else {
					return "false";
				}
			}

			@Override
			public void doAction(NonLeaf node, Object object) {

			}
		});

		comparacao.addProduction(
				ID.and(ID_chamada).and(comparador).and(ID_chamada_NUMERO),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(0);
						String id_chamada = node.getWriteJava(1);
						String comparador = node.getWriteJava(2);
						String ID_chamada_NUMERO = node.getWriteJava(3);
						return id + id_chamada + comparador + ID_chamada_NUMERO;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						Funcao funcao = (Funcao) object;
						String id = node.getTokenExpression(0);
						Definicao definicaoTest = new Definicao(id,
								new Tipo(""));
						if (semanticAnalyzer.getDefinicoes().contains(
								definicaoTest)) {
							Definicao definicao = semanticAnalyzer
									.getDefinicoes().get(
											semanticAnalyzer.getDefinicoes()
													.indexOf(definicaoTest));
							if (definicao.getTipo().getTipoNome()
									.equals("inteiro")) {
								List<Object> l = new ArrayList<>();
								l.add(funcao);
								l.add(definicao);
								node.doChildAction(1, l);
								node.doChildAction(3, object);
							} else {
								throw new SemanticException("Tipo de " + id
										+ " não é inteiro");
							}
						} else {
							if (funcao.getParametros().contains(definicaoTest)) {
								Definicao definicao = funcao.getParametros()
										.get(funcao.getParametros().indexOf(
												definicaoTest));
								if (definicao.getTipo().getTipoNome()
										.equals("inteiro")) {
									List<Object> l = new ArrayList<>();
									l.add(funcao);
									l.add(definicao);
									node.doChildAction(1, l);
									node.doChildAction(3, object);
								} else {
									throw new SemanticException("Tipo de " + id
											+ " não é inteiro");
								}
							} else {
								throw new SemanticException("Identificador "
										+ id + " não declarado");
							}
						}
					}
				});

		assinatura_argumentos.addProduction(fecha_parenteses,
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						return ")";
					}

					@Override
					public void doAction(NonLeaf node, Object object) throws SemanticException {
//						Funcao funcao = (Funcao)object;
//						if(funcao.getPosition() < funcao.getParametros().size() || funcao.getPosition() > funcao.getParametros().size()){
//							throw new SemanticException("Quantidade de argumentos esperada era "+funcao.getParametros().size());
//						} else {
//							funcao.setPosition(0);
//						}
					}
				});

		assinatura_argumentos.addProduction(NUMEROS.and(separa_argumentos).and(fecha_parenteses),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String numeros = node.getTokenExpression(0);
						String separa_argumentos = node.getWriteJava(1);
						return numeros + separa_argumentos + ")";
					}

					@Override
					public void doAction(NonLeaf node, Object object)throws SemanticException {
						List<Object> l = (List<Object>)object;
						Funcao funcao = (Funcao)l.get(1);
						Parametro parametro = funcao.getParametro();
						if (funcao.getPosition() < funcao.getParametros().size()) {
							if (parametro.getTipo().getTipoNome().equals("inteiro")) {
								funcao.updatePosition();
								node.doChildAction(1, l);
								// após verificar os parametros para esta
								// chamada da função coloque no início as
								// posições do parâmetro
//								if(funcao.getPosition() < funcao.getParametros().size() || funcao.getPosition() > funcao.getParametros().size()){
//									throw new SemanticException("Quantidade de argumentos esperada era "+funcao.getParametros().size());
//								} else {
//									funcao.setPosition(0);
//								}
							} else {
								throw new SemanticException("Era experado um argumento do tipo "+ parametro.getTipo().getTipoNome());
							}
						} else {
							throw new SemanticException("A quantidade de argumentos necessária era "+ funcao.getParametros().size());
						}
					}
				});
		assinatura_argumentos.addProduction(BOOLEANOS.and(separa_argumentos)
				.and(fecha_parenteses), new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				String booleanos = node.getTokenExpression(0);
				String separa_argumentos = node.getTokenExpression(1);
				if(booleanos.equals("V")){
					return "true" + separa_argumentos + ")";
				} else {
					return "false" + separa_argumentos + ")";
				}

			}

			@Override
			public void doAction(NonLeaf node, Object object)
					throws SemanticException {
				List<Object> l = (List<Object>)object;
				Funcao funcao = (Funcao)l.get(1);
				Parametro parametro = funcao.getParametro();
				if (funcao.getPosition() < funcao.getParametros().size()) {
					if (parametro.getTipo().getTipoNome().equals("booleano")) {
						funcao.updatePosition();
						node.doChildAction(1, l);
//						if(funcao.getPosition() < funcao.getParametros().size() || funcao.getPosition() > funcao.getParametros().size()){
//							throw new SemanticException("Quantidade de argumentos esperada era "+funcao.getParametros().size());
//						} else {
//							funcao.setPosition(0);
//						}
					} else {
						throw new SemanticException(
								"Era experado um argumento do tipo "
										+ parametro.getTipo().getTipoNome());
					}
				} else {
					throw new SemanticException(
							"A quantidade de argumentos necessária era "
									+ funcao.getParametros().size());
				}
			}
		});
		/**
		 * alterada gramática!!
		 * ID.and(ID_chamada).and(separa_argumentos).and(fecha_parenteses)
		 */
		assinatura_argumentos.addProduction(
				ID.and(separa_argumentos).and(fecha_parenteses),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(0);
						String separa_argumentos = node.getWriteJava(1);
						return id + separa_argumentos + ")";
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						String id = node.getTokenExpression(0);
						Definicao definicaoTest = new Definicao(id,
								new Tipo(""));
						List<Object> l = (List<Object>)object;
						Funcao funcaoEscopo = (Funcao)l.get(0);
						Funcao funcao = (Funcao)l.get(1);
						if (semanticAnalyzer.getDefinicoes().contains(definicaoTest)) {
							Definicao definicao = semanticAnalyzer.getDefinicoes().get(semanticAnalyzer.getDefinicoes().indexOf(definicaoTest));
							Parametro parametro = funcao.getParametro();
							if (funcao.getPosition() < funcao.getParametros().size()) {
								if (parametro.getTipo().getTipoNome().equals(definicao.getTipo().getTipoNome())) {
									funcao.updatePosition();
									node.doChildAction(1, l);
//									if(funcao.getPosition() < funcao.getParametros().size() || funcao.getPosition() > funcao.getParametros().size()){
//										throw new SemanticException("Quantidade de argumentos esperada era "+funcao.getParametros().size());
//									} else {
//										funcao.setPosition(0);
//									}
								} else {
									throw new SemanticException("Era experado um argumento do tipo "+ parametro.getTipo()
															.getTipoNome());
								}
							} else {
								throw new SemanticException(
										"A quantidade de argumentos necessária era "
												+ funcao.getParametros().size());
							}
						} else {
							if (funcaoEscopo.getParametros().contains(definicaoTest)) {
								Definicao definicao = funcaoEscopo.getParametros().get(funcaoEscopo.getParametros().indexOf(definicaoTest));
								Parametro parametro = funcao.getParametro();
								if (funcao.getPosition() < funcao.getParametros().size()) {
									if (parametro.getTipo().getTipoNome().equals(definicao.getTipo().getTipoNome())) {
										funcao.updatePosition();
										node.doChildAction(1, l);
//										if(funcao.getPosition() < funcao.getParametros().size() || funcao.getPosition() > funcao.getParametros().size()){
//											throw new SemanticException("Quantidade de argumentos esperada era "+funcao.getParametros().size());
//										} else {
//											funcao.setPosition(0);
//										}
									} else {
										throw new SemanticException(
												"Era experado um argumento do tipo "
														+ parametro.getTipo()
																.getTipoNome());
									}
								} else {
									throw new SemanticException(
											"A quantidade de argumentos necessária era "
													+ funcao.getParametros().size());
								}
							} else {
								throw new SemanticException("Identificador "
										+ id + " não declarado");
							}
						}
					}
				});

		argumentos.addProduction(NUMEROS.and(separa_argumentos),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String numeros = node.getTokenExpression(0);
						String separa_argumentos = node.getWriteJava(1);
						return numeros + separa_argumentos;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						List<Object> l = (List<Object>)object;
						Funcao funcao = (Funcao)l.get(1);
						if (funcao.getPosition() < funcao.getParametros()
								.size()) {

							Parametro parametro = funcao.getParametro();
							if (parametro.getTipo().getTipoNome()
									.equals("inteiro")) {
								funcao.updatePosition();
								node.doChildAction(1, l);
							} else {
								throw new SemanticException(
										"Era experado um argumento do tipo "
												+ parametro.getTipo()
														.getTipoNome());
							}
						} else {
							throw new SemanticException(
									"A quantidade de argumentos necessária era "
											+ funcao.getParametros().size());
						}
					}
				});

		argumentos.addProduction(BOOLEANOS.and(separa_argumentos),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String booleanos = node.getTokenExpression(0);
						String separa_argumentos = node.getWriteJava(1);
						if(booleanos.equals("V")){
							return "true" + separa_argumentos;
						} else {
							return "false"+ separa_argumentos;
						}
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						List<Object> l = (List<Object>)object;
						Funcao funcao = (Funcao)l.get(1);
						Parametro parametro = funcao.getParametro();
						if (funcao.getPosition() < funcao.getParametros().size()) {
							if (parametro.getTipo().getTipoNome().equals("booleano")) {
								funcao.updatePosition();
								node.doChildAction(1, l);
							} else {
								throw new SemanticException("Era experado um argumento do tipo "
												+ parametro.getTipo()
														.getTipoNome());
							}
						} else {
							throw new SemanticException(
									"A quantidade de argumentos necessária era "
											+ funcao.getParametros().size());
						}
					}
				});
		/**
		 * alterada gramática ID.and(ID_chamada).and(separa_argumentos)
		 */
		argumentos.addProduction(ID.and(separa_argumentos),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(0);
						String separa_argumentos = node.getWriteJava(1);
						return id + separa_argumentos;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						String id = node.getTokenExpression(0);
						Definicao definicaoTest = new Definicao(id,	new Tipo(""));

						List<Object> l = (List<Object>)object;
						Funcao funcaoEscopo = (Funcao)l.get(0);
						Funcao funcao = (Funcao)l.get(1);
						
						if (semanticAnalyzer.getDefinicoes().contains(definicaoTest)) {
							Definicao definicao = semanticAnalyzer.getDefinicoes().get(semanticAnalyzer.getDefinicoes().indexOf(definicaoTest));
							Parametro parametro = funcao.getParametro();
							if (funcao.getPosition() < funcao.getParametros().size()) {
								if (parametro.getTipo().getTipoNome().equals(definicao.getTipo().getTipoNome())) {
									funcao.updatePosition();
									node.doChildAction(1, l);
								} else {
									throw new SemanticException(
											"Era experado um argumento do tipo "
													+ parametro.getTipo()
															.getTipoNome());
								}
							} else {
								throw new SemanticException(
										"A quantidade de argumentos necessária era "
												+ funcao.getParametros().size());
							}
						} else {
							if (funcaoEscopo.getParametros().contains(definicaoTest)) {
								Definicao definicao = funcaoEscopo.getParametros().get(funcaoEscopo.getParametros().indexOf(definicaoTest));
								Parametro parametro = funcao.getParametro();
								if (funcao.getPosition() < funcao.getParametros().size()) {
									if (parametro.getTipo().getTipoNome().equals(definicao.getTipo().getTipoNome())) {
										funcao.updatePosition();
										node.doChildAction(1, l);
//										if(funcao.getPosition() < funcao.getParametros().size() || funcao.getPosition() > funcao.getParametros().size()){
//											throw new SemanticException("Quantidade de argumentos esperada era "+funcao.getParametros().size());
//										} else {
//											funcao.setPosition(0);
//										}
									} else {
										throw new SemanticException(
												"Era experado um argumento do tipo "
														+ parametro.getTipo()
																.getTipoNome());
									}
								} else {
									throw new SemanticException(
											"A quantidade de argumentos necessária era "
													+ funcao.getParametros().size());
								}
							} else {
								throw new SemanticException("Identificador "
										+ id + " não declarado");
							}
							
						}
					}
				});

		separa_argumentos.addProduction(VIRGULA.and(argumentos),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String argumentos = node.getWriteJava(1);
						return ", " + argumentos;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(1, object);
					}
				});
		separa_argumentos.addProduction(Terminal.BLANK, new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				return "";
			}

			@Override
			public void doAction(NonLeaf node, Object object) throws SemanticException {
//				Funcao funcao = (Funcao)object;
//				if(funcao.getPosition() < funcao.getParametros().size() || funcao.getPosition() > funcao.getParametros().size()){
//					throw new SemanticException("Quantidade de argumentos esperada era "+funcao.getParametros().size());
//				} else {
//					funcao.setPosition(0);
//				}
			}
		});

		escopo_loop.addProduction(
				INTEIRO.and(ID).and(PONTOVIRGULA).and(separa_escopo_loop),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(1);
						String separa_escopo_loop = node.getWriteJava(3);
						return "\tint " + id + ";" + "\n\t"
								+ separa_escopo_loop;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						Funcao funcao = (Funcao) object;
						String id = node.getTokenExpression(1);
						Definicao definicao = new Definicao(id, new Tipo(
								"inteiro"));
						if (semanticAnalyzer.getDefinicoes()
								.contains(definicao)
								|| funcao.getParametros().contains(definicao)) {
							throw new DuplicateDefinicao(id);
						} else {
							semanticAnalyzer.getDefinicoes().add(definicao);
							node.doChildAction(3, object);
						}
					}
				});

		escopo_loop.addProduction(
				BOOLEANO.and(ID).and(PONTOVIRGULA).and(separa_escopo_loop),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(1);
						String separa_escopo_loop = node.getWriteJava(3);
						return "boolean " + id + ";" + "\n\t"
								+ separa_escopo_loop;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						String id = node.getTokenExpression(0);
						Definicao definicao = new Definicao(id, new Tipo(
								"booleano"));
						if (semanticAnalyzer.getDefinicoes()
								.contains(definicao)) {
							throw new DuplicateDefinicao(id);
						} else {
							semanticAnalyzer.getDefinicoes().add(definicao);
							node.doChildAction(3, object);
						}
					}
				});

		escopo_loop.addProduction(ID.and(ATRIBUIDOR).and(diversas_atribuicoes)
				.and(PONTOVIRGULA).and(separa_escopo_loop),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(0);
						String diversas_atribuicoes = node.getWriteJava(2);
						String separa_escopo_loop = node.getWriteJava(4);
						return id + " = " + diversas_atribuicoes + ";\n\t"
								+ separa_escopo_loop;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						Funcao funcao = (Funcao) object;
						List<Object> l = new ArrayList<>();
						l.add(funcao);
						String id = node.getTokenExpression(0);
						Definicao definicaoTest = new Definicao(id,
								new Tipo(""));
						if (semanticAnalyzer.getDefinicoes().contains(
								definicaoTest)) {
							Definicao definicao = semanticAnalyzer
									.getDefinicoes().get(
											semanticAnalyzer.getDefinicoes()
													.indexOf(definicaoTest));
							l.add(definicao);
//							if (definicao.getTipo().getTipoNome()
//									.equals("inteiro")) {
								node.doChildAction(2, l);
								node.doChildAction(4, object);
//							} else {
//								throw new SemanticException("Tipo de " + id
//										+ " deveria ser inteiro");
//							}
						} else {
							if (funcao.getParametros().contains(definicaoTest)) {
								Definicao definicao = funcao.getParametros()
										.get(funcao.getParametros().indexOf(
												definicaoTest));
								l.add(definicao);
//								if (definicao.getTipo().getTipoNome()
//										.equals("inteiro")) {
									node.doChildAction(2, object);
									node.doChildAction(4, object);
//								} else {
//									throw new SemanticException("Tipo de " + id
//											+ " deveria ser inteiro");
//								}
							} else {
								throw new SemanticException("Identificador "
										+ id + " não declarado");
							}
						}
					}
				});

		escopo_loop.addProduction(
				ENQUANTO.and(abre_parenteses).and(comparacao)
						.and(fecha_parenteses).and(abre_chaves)
						.and(escopo_loop).and(fecha_chaves)
						.and(separa_escopo_loop), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String comparacao = node.getWriteJava(2);
						String escopo_loop = node.getWriteJava(5);
						String separa_escopo_loop = node.getWriteJava(7);
						return "while(" + comparacao + "){\n\t" + escopo_loop
								+ "\n\t}" + separa_escopo_loop;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
						node.doChildAction(5, object);
						node.doChildAction(7, object);
					}
				});

		escopo_loop.addProduction(
				IMPRIMA.and(abre_parenteses).and(ID_chamada_NUMERO_BOOL)
						.and(fecha_parenteses).and(PONTOVIRGULA)
						.and(separa_escopo_loop), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO_BOOL = node.getWriteJava(2);
						String separa_escopo_loop = node.getWriteJava(5);
						return "System.out.println(" + ID_chamada_NUMERO_BOOL
								+ ");" + "\n\t" + separa_escopo_loop;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
						node.doChildAction(5, object);
					}
				});

		escopo_loop.addProduction(
				SE.and(abre_parenteses).and(comparacao).and(fecha_parenteses)
						.and(abre_chaves).and(escopo_condicional)
						.and(fecha_chaves).and(separa_escopo_loop),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String comparacao = node.getWriteJava(2);
						String escopo_condicional = node.getWriteJava(5);
						String separa_escopo_loop = node.getWriteJava(7);
						return "if(" + comparacao + "){\n\t"
								+ escopo_condicional + "\n\t}"
								+ separa_escopo_loop;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
						node.doChildAction(5, object);
						node.doChildAction(7, object);
					}
				});

		escopo_loop.addProduction(
				SENAO.and(abre_chaves).and(escopo_condicional)
						.and(fecha_chaves).and(separa_escopo_loop),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String escopo_condicional = node.getWriteJava(2);
						String separa_escopo_loop = node.getWriteJava(4);
						return "else{\n\t" + escopo_condicional + "\n\t}"
								+ separa_escopo_loop;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
						node.doChildAction(4, object);
					}
				});

		escopo_loop.addProduction(PARE.and(PONTOVIRGULA)
				.and(separa_escopo_loop), new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				String separa_escopo_loop = node.getWriteJava(2);
				return "break;\n\t" + separa_escopo_loop;
			}

			@Override
			public void doAction(NonLeaf node, Object object)
					throws SemanticException {
				node.doChildAction(2, object);
			}
		});

		escopo_loop.addProduction(
				CONTINUE.and(PONTOVIRGULA).and(separa_escopo_loop),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String separa_escopo_loop = node.getWriteJava(2);
						return "continue;\n\t" + separa_escopo_loop;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
					}
				});

		/**
		 * separa_escopo_loop
		 */

		separa_escopo_loop.addProduction(
				INTEIRO.and(ID).and(PONTOVIRGULA).and(separa_escopo_loop),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(1);
						String separa_escopo_loop = node.getWriteJava(3);
						return "\tint " + id + ";" + "\n\t"
								+ separa_escopo_loop;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						Funcao funcao = (Funcao) object;
						String id = node.getTokenExpression(1);
						Definicao definicao = new Definicao(id, new Tipo(
								"inteiro"));
						if (semanticAnalyzer.getDefinicoes()
								.contains(definicao)
								|| funcao.getParametros().contains(definicao)) {
							throw new DuplicateDefinicao(id);
						} else {
							semanticAnalyzer.getDefinicoes().add(definicao);
							node.doChildAction(3, object);
						}
					}
				});

		separa_escopo_loop.addProduction(BOOLEANO.and(ID).and(PONTOVIRGULA)
				.and(separa_escopo_loop), new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(1);
				String separa_escopo_loop = node.getWriteJava(3);
				return "boolean " + id + ";" + "\n\t" + separa_escopo_loop;
			}

			@Override
			public void doAction(NonLeaf node, Object object)
					throws SemanticException {
				String id = node.getTokenExpression(0);
				Definicao definicao = new Definicao(id, new Tipo("booleano"));
				if (semanticAnalyzer.getDefinicoes().contains(definicao)) {
					throw new DuplicateDefinicao(id);
				} else {
					semanticAnalyzer.getDefinicoes().add(definicao);
					node.doChildAction(3, object);
				}
			}
		});

		separa_escopo_loop.addProduction(
				ID.and(ATRIBUIDOR).and(diversas_atribuicoes).and(PONTOVIRGULA)
						.and(separa_escopo_loop), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(0);
						String diversas_atribuicoes = node.getWriteJava(2);
						String separa_escopo_loop = node.getWriteJava(4);
						return id + " = " + diversas_atribuicoes + ";\n\t"
								+ separa_escopo_loop;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						Funcao funcao = (Funcao) object;
						List<Object> l = new ArrayList<Object>();
						l.add(funcao);
						String id = node.getTokenExpression(0);
						Definicao definicaoTest = new Definicao(id,
								new Tipo(""));
						if (semanticAnalyzer.getDefinicoes().contains(
								definicaoTest)) {
							Definicao definicao = semanticAnalyzer
									.getDefinicoes().get(
											semanticAnalyzer.getDefinicoes()
													.indexOf(definicaoTest));
							l.add(definicao);
//							if (definicao.getTipo().getTipoNome()
//									.equals("inteiro")) {
								node.doChildAction(2, l);
								node.doChildAction(4, object);
//							} else {
//								throw new SemanticException("Tipo de " + id
//										+ " deveria ser inteiro");
//							}
						} else {
							if (funcao.getParametros().contains(definicaoTest)) {
								Definicao definicao = funcao.getParametros()
										.get(funcao.getParametros().indexOf(
												definicaoTest));
								l.add(definicao);
//								if (definicao.getTipo().getTipoNome()
//										.equals("inteiro")) {
									node.doChildAction(2, l);
									node.doChildAction(4, object);
//								} else {
//									throw new SemanticException("Tipo de " + id
//											+ " deveria ser inteiro");
//								}
							} else {
								throw new SemanticException("Identificador "
										+ id + " não declarado");
							}
						}
					}
				});

		separa_escopo_loop.addProduction(
				ENQUANTO.and(abre_parenteses).and(comparacao)
						.and(fecha_parenteses).and(abre_chaves)
						.and(escopo_loop).and(fecha_chaves)
						.and(separa_escopo_loop), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String comparacao = node.getWriteJava(2);
						String escopo_loop = node.getWriteJava(5);
						String separa_escopo_loop = node.getWriteJava(7);
						return "while(" + comparacao + "){\n\t" + escopo_loop
								+ "\n\t}" + separa_escopo_loop;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
						node.doChildAction(5, object);
						node.doChildAction(7, object);
					}
				});

		separa_escopo_loop.addProduction(
				IMPRIMA.and(abre_parenteses).and(ID_chamada_NUMERO_BOOL)
						.and(fecha_parenteses).and(PONTOVIRGULA)
						.and(separa_escopo_loop), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO_BOOL = node.getWriteJava(2);
						String separa_escopo_loop = node.getWriteJava(5);
						return "System.out.println(" + ID_chamada_NUMERO_BOOL
								+ ");" + "\n\t" + separa_escopo_loop;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
						node.doChildAction(5, object);
					}
				});

		separa_escopo_loop.addProduction(
				SE.and(abre_parenteses).and(comparacao).and(fecha_parenteses)
						.and(abre_chaves).and(escopo_condicional)
						.and(fecha_chaves).and(separa_escopo_loop),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String comparacao = node.getWriteJava(2);
						String escopo_condicional = node.getWriteJava(5);
						String separa_escopo_loop = node.getWriteJava(7);
						return "if(" + comparacao + "){\n\t"
								+ escopo_condicional + "\n\t}"
								+ separa_escopo_loop;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
						node.doChildAction(5, object);
						node.doChildAction(7, object);
					}
				});

		separa_escopo_loop.addProduction(
				SENAO.and(abre_chaves).and(escopo_condicional)
						.and(fecha_chaves).and(separa_escopo_loop),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String escopo_condicional = node.getWriteJava(2);
						String separa_escopo_loop = node.getWriteJava(4);
						return "else{\n\t" + escopo_condicional + "\n\t}"
								+ separa_escopo_loop;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
						node.doChildAction(4, object);
					}
				});

		separa_escopo_loop.addProduction(
				PARE.and(PONTOVIRGULA).and(separa_escopo_loop),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String separa_escopo_loop = node.getWriteJava(2);
						return "break;\n\t" + separa_escopo_loop;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
					}
				});

		separa_escopo_loop.addProduction(
				CONTINUE.and(PONTOVIRGULA).and(separa_escopo_loop),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String separa_escopo_loop = node.getWriteJava(2);
						return "continue;\n\t" + separa_escopo_loop;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
					}
				});

		separa_escopo_loop.addProduction(Terminal.BLANK, new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				return "";
			}

			@Override
			public void doAction(NonLeaf node, Object object) {

			}
		});

		/**
		 * escopo_condicional
		 */

		escopo_condicional.addProduction(
				INTEIRO.and(ID).and(PONTOVIRGULA)
						.and(separa_escopo_condicional), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(1);
						String separa_escopo_condicional = node.getWriteJava(3);
						return "\tint " + id + ";" + "\n\t"
								+ separa_escopo_condicional;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						Funcao funcao = (Funcao) object;
						String id = node.getTokenExpression(1);
						Definicao definicao = new Definicao(id, new Tipo(
								"inteiro"));
						if (semanticAnalyzer.getDefinicoes()
								.contains(definicao)
								|| funcao.getParametros().contains(definicao)) {
							throw new DuplicateDefinicao(id);
						} else {
							semanticAnalyzer.getDefinicoes().add(definicao);
							node.doChildAction(3, object);
						}
					}
				});

		escopo_condicional.addProduction(BOOLEANO.and(ID).and(PONTOVIRGULA)
				.and(separa_escopo_condicional), new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				String id = node.getTokenExpression(1);
				String separa_escopo_condicional = node.getWriteJava(3);
				return "boolean " + id + ";" + "\n\t"
						+ separa_escopo_condicional;
			}

			@Override
			public void doAction(NonLeaf node, Object object)
					throws SemanticException {
				String id = node.getTokenExpression(0);
				Definicao definicao = new Definicao(id, new Tipo("booleano"));
				if (semanticAnalyzer.getDefinicoes().contains(definicao)) {
					throw new DuplicateDefinicao(id);
				} else {
					semanticAnalyzer.getDefinicoes().add(definicao);
					node.doChildAction(3, object);
				}
			}
		});

		escopo_condicional.addProduction(
				ID.and(ATRIBUIDOR).and(diversas_atribuicoes).and(PONTOVIRGULA)
						.and(separa_escopo_condicional), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(0);
						String diversas_atribuicoes = node.getWriteJava(2);
						String separa_escopo_condicional = node.getWriteJava(4);
						return id + " = " + diversas_atribuicoes + ";\n\t"
								+ separa_escopo_condicional;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						Funcao funcao = (Funcao) object;
						List<Object> l = new ArrayList<Object>();
						l.add(funcao);
						String id = node.getTokenExpression(0);
						Definicao definicaoTest = new Definicao(id,
								new Tipo(""));
						if (semanticAnalyzer.getDefinicoes().contains(
								definicaoTest)) {
							Definicao definicao = semanticAnalyzer
									.getDefinicoes().get(
											semanticAnalyzer.getDefinicoes()
													.indexOf(definicaoTest));
							l.add(definicao);
//							if (definicao.getTipo().getTipoNome()
//									.equals("inteiro")) {
								node.doChildAction(2, l);
								node.doChildAction(4, object);
//							} else {
//								throw new SemanticException("Tipo de " + id
//										+ " deveria ser inteiro");
//							}
						} else {
							if (funcao.getParametros().contains(definicaoTest)) {
								Definicao definicao = funcao.getParametros()
										.get(funcao.getParametros().indexOf(
												definicaoTest));
								l.add(definicao);
//								if (definicao.getTipo().getTipoNome()
//										.equals("inteiro")) {
									node.doChildAction(2, l);
									node.doChildAction(4, object);
//								} else {
//									throw new SemanticException("Tipo de " + id
//											+ " deveria ser inteiro");
//								}
							} else {
								throw new SemanticException("Identificador "
										+ id + " não declarado");
							}
						}
					}
				});

		escopo_condicional.addProduction(
				ENQUANTO.and(abre_parenteses).and(comparacao)
						.and(fecha_parenteses).and(abre_chaves)
						.and(escopo_loop).and(fecha_chaves)
						.and(separa_escopo_condicional), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String comparacao = node.getWriteJava(2);
						String escopo_loop = node.getWriteJava(5);
						String separa_escopo_condicional = node.getWriteJava(7);
						return "while(" + comparacao + "){\n\t" + escopo_loop
								+ "\n\t}" + separa_escopo_condicional;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
						node.doChildAction(5, object);
						node.doChildAction(7, object);
					}
				});

		escopo_condicional.addProduction(
				IMPRIMA.and(abre_parenteses).and(ID_chamada_NUMERO_BOOL)
						.and(fecha_parenteses).and(PONTOVIRGULA)
						.and(separa_escopo_condicional), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO_BOOL = node.getWriteJava(2);
						String separa_escopo_condicional = node.getWriteJava(5);
						return "System.out.println(" + ID_chamada_NUMERO_BOOL
								+ ");" + "\n\t" + separa_escopo_condicional;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
						node.doChildAction(5, object);
					}
				});

		escopo_condicional.addProduction(
				SE.and(abre_parenteses).and(comparacao).and(fecha_parenteses)
						.and(abre_chaves).and(escopo_condicional)
						.and(fecha_chaves).and(separa_escopo_condicional),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String comparacao = node.getWriteJava(2);
						String escopo_condicional = node.getWriteJava(5);
						String separa_escopo_condicional = node.getWriteJava(7);
						return "if(" + comparacao + "){\n\t"
								+ escopo_condicional + "\n\t}"
								+ separa_escopo_condicional;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
						node.doChildAction(5, object);
						node.doChildAction(7, object);
					}
				});

		escopo_condicional.addProduction(
				SENAO.and(abre_chaves).and(escopo_condicional)
						.and(fecha_chaves).and(separa_escopo_condicional),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String escopo_condicional = node.getWriteJava(2);
						String separa_escopo_condicional = node.getWriteJava(4);
						return "else{\n\t" + escopo_condicional + "\n\t}"
								+ separa_escopo_condicional;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
						node.doChildAction(4, object);
					}
				});

		escopo_condicional.addProduction(
				PARE.and(PONTOVIRGULA).and(separa_escopo_condicional),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String separa_escopo_condicional = node.getWriteJava(2);
						return "break;\n\t" + separa_escopo_condicional;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
					}
				});

		escopo_condicional.addProduction(
				CONTINUE.and(PONTOVIRGULA).and(separa_escopo_condicional),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String separa_escopo_condicional = node.getWriteJava(2);
						return "continue;\n\t" + separa_escopo_condicional;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
					}
				});

		/**
		 * separa_escopo_condicional
		 */

		separa_escopo_condicional.addProduction(
				INTEIRO.and(ID).and(PONTOVIRGULA)
						.and(separa_escopo_condicional), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(1);
						String separa_escopo_condicional = node.getWriteJava(3);
						return "\tint " + id + ";" + "\n\t"
								+ separa_escopo_condicional;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						Funcao funcao = (Funcao) object;
						String id = node.getTokenExpression(1);
						Definicao definicao = new Definicao(id, new Tipo(
								"inteiro"));
						if (semanticAnalyzer.getDefinicoes()
								.contains(definicao)
								|| funcao.getParametros().contains(definicao)) {
							throw new DuplicateDefinicao(id);
						} else {
							semanticAnalyzer.getDefinicoes().add(definicao);
							node.doChildAction(3, object);
						}
					}
				});

		separa_escopo_condicional.addProduction(
				BOOLEANO.and(ID).and(PONTOVIRGULA)
						.and(separa_escopo_condicional), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(1);
						String separa_escopo_condicional = node.getWriteJava(3);
						return "boolean " + id + ";" + "\n\t"
								+ separa_escopo_condicional;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						String id = node.getTokenExpression(0);
						Definicao definicao = new Definicao(id, new Tipo(
								"booleano"));
						if (semanticAnalyzer.getDefinicoes()
								.contains(definicao)) {
							throw new DuplicateDefinicao(id);
						} else {
							semanticAnalyzer.getDefinicoes().add(definicao);
							node.doChildAction(3, object);
						}
					}
				});

		separa_escopo_condicional.addProduction(
				ID.and(ATRIBUIDOR).and(diversas_atribuicoes).and(PONTOVIRGULA)
						.and(separa_escopo_condicional), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(0);
						String diversas_atribuicoes = node.getWriteJava(2);
						String separa_escopo_condicional = node.getWriteJava(4);
						return id + " = " + diversas_atribuicoes + ";\n\t"
								+ separa_escopo_condicional;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						Funcao funcao = (Funcao) object;
						List<Object> l = new ArrayList<Object>();
						l.add(funcao);
						String id = node.getTokenExpression(0);
						Definicao definicaoTest = new Definicao(id,
								new Tipo(""));
						if (semanticAnalyzer.getDefinicoes().contains(
								definicaoTest)) {
							Definicao definicao = semanticAnalyzer
									.getDefinicoes().get(
											semanticAnalyzer.getDefinicoes()
													.indexOf(definicaoTest));
							l.add(definicao);
//							if (definicao.getTipo().getTipoNome()
//									.equals("inteiro")) {
								node.doChildAction(2, l);
								node.doChildAction(4, object);
//							} else {
//								throw new SemanticException("Tipo de " + id
//										+ " deveria ser inteiro");
//							}
						} else {
							if (funcao.getParametros().contains(definicaoTest)) {
								Definicao definicao = funcao.getParametros()
										.get(funcao.getParametros().indexOf(
												definicaoTest));
								l.add(definicao);
//								if (definicao.getTipo().getTipoNome()
//										.equals("inteiro")) {
									node.doChildAction(2, l);
									node.doChildAction(4, object);
//								} else {
//									throw new SemanticException("Tipo de " + id
//											+ " deveria ser inteiro");
//								}
							} else {
								throw new SemanticException("Identificador "
										+ id + " não declarado");
							}
						}
					}
				});

		separa_escopo_condicional.addProduction(
				ENQUANTO.and(abre_parenteses).and(comparacao)
						.and(fecha_parenteses).and(abre_chaves)
						.and(escopo_loop).and(fecha_chaves)
						.and(separa_escopo_condicional), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String comparacao = node.getWriteJava(2);
						String escopo_loop = node.getWriteJava(5);
						String separa_escopo_condicional = node.getWriteJava(7);
						return "while(" + comparacao + "){\n\t" + escopo_loop
								+ "\n\t}" + separa_escopo_condicional;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
						node.doChildAction(5, object);
						node.doChildAction(7, object);
					}
				});

		separa_escopo_condicional.addProduction(
				IMPRIMA.and(abre_parenteses).and(ID_chamada_NUMERO_BOOL)
						.and(fecha_parenteses).and(PONTOVIRGULA)
						.and(separa_escopo_condicional), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO_BOOL = node.getWriteJava(2);
						String separa_escopo_condicional = node.getWriteJava(5);
						return "System.out.println(" + ID_chamada_NUMERO_BOOL
								+ ");" + "\n\t" + separa_escopo_condicional;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
						node.doChildAction(5, object);
					}
				});

		separa_escopo_condicional.addProduction(
				SE.and(abre_parenteses).and(comparacao).and(fecha_parenteses)
						.and(abre_chaves).and(escopo_condicional)
						.and(fecha_chaves).and(separa_escopo_condicional),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String comparacao = node.getWriteJava(2);
						String escopo_condicional = node.getWriteJava(5);
						String separa_escopo_condicional = node.getWriteJava(7);
						return "if(" + comparacao + "){\n\t"
								+ escopo_condicional + "\n\t}"
								+ separa_escopo_condicional;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
						node.doChildAction(5, object);
						node.doChildAction(7, object);
					}
				});

		separa_escopo_condicional.addProduction(
				SENAO.and(abre_chaves).and(escopo_condicional)
						.and(fecha_chaves).and(separa_escopo_condicional),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String escopo_condicional = node.getWriteJava(2);
						String separa_escopo_condicional = node.getWriteJava(4);
						return "else{\n\t" + escopo_condicional + "\n\t}"
								+ separa_escopo_condicional;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
						node.doChildAction(4, object);
					}
				});

		separa_escopo_condicional.addProduction(
				PARE.and(PONTOVIRGULA).and(separa_escopo_condicional),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String separa_escopo_condicional = node.getWriteJava(2);
						return "break;\n\t" + separa_escopo_condicional;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
					}
				});

		separa_escopo_condicional.addProduction(
				CONTINUE.and(PONTOVIRGULA).and(separa_escopo_condicional),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String separa_escopo_condicional = node.getWriteJava(2);
						return "continue;\n\t" + separa_escopo_condicional;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(2, object);
					}
				});

		separa_escopo_condicional.addProduction(Terminal.BLANK,
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						return "";
					}

					@Override
					public void doAction(NonLeaf node, Object object) {

					}
				});

		operacao.addProduction(
				ID.and(ID_chamada).and(operador).and(ID_chamada_NUMERO)
						.and(outra_operacao), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(0);
						String ID_chamada = node.getWriteJava(1);
						String operador = node.getWriteJava(2);
						String ID_chamada_NUMERO = node.getWriteJava(3);
						String outra_operacao = node.getWriteJava(4);
						return id + ID_chamada + operador + ID_chamada_NUMERO
								+ outra_operacao;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						Funcao funcao = (Funcao) object;
						String id = node.getTokenExpression(0);
						Definicao definicaoTest = new Definicao(id,
								new Tipo(""));
						if (semanticAnalyzer.getDefinicoes().contains(
								definicaoTest)) {
							Definicao definicao = semanticAnalyzer
									.getDefinicoes().get(
											semanticAnalyzer.getDefinicoes()
													.indexOf(definicaoTest));
							if (definicao.getTipo().getTipoNome()
									.equals("inteiro")) {
								List<Object> l = new ArrayList<>();
								l.add(funcao);
								l.add(definicao);
								node.doChildAction(1, l);
								node.doChildAction(2, object);
								node.doChildAction(4, object);
							} else {
								throw new SemanticException("Tipo de " + id
										+ " deveria ser inteiro");
							}
						} else {
							if (funcao.getParametros().contains(definicaoTest)) {
								Definicao definicao = funcao.getParametros()
										.get(funcao.getParametros().indexOf(
												definicaoTest));
								if (definicao.getTipo().getTipoNome()
										.equals("inteiro")) {
									List<Object> l = new ArrayList<>();
									l.add(funcao);
									l.add(definicao);
									node.doChildAction(1, l);
									node.doChildAction(2, definicao);
									node.doChildAction(4, object);
								} else {
									throw new SemanticException("Tipo de " + id
											+ " deveria ser inteiro");
								}
							} else {
								throw new SemanticException("Identificador "
										+ id + " não declarado");
							}
						}
					}
				});

		operacao.addProduction(NUMEROS.and(operador).and(ID_chamada_NUMERO)
				.and(outra_operacao), new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				String numeros = node.getTokenExpression(0);
				String operador = node.getWriteJava(1);
				String ID_chamada_NUMERO = node.getWriteJava(2);
				String outra_operacao = node.getWriteJava(3);
				return numeros + operador + ID_chamada_NUMERO + outra_operacao;
			}

			@Override
			public void doAction(NonLeaf node, Object object)
					throws SemanticException {
				node.doChildAction(2, object);
				node.doChildAction(3, object);
			}
		});

		outra_operacao.addProduction(
				MAIS.and(ID_chamada_NUMERO).and(outra_operacao),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO = node.getWriteJava(1);
						String outra_operacao = node.getWriteJava(2);
						return "+" + ID_chamada_NUMERO + outra_operacao;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(1, object);
						node.doChildAction(2, object);
					}
				});
		outra_operacao.addProduction(
				MENOS.and(ID_chamada_NUMERO).and(outra_operacao),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO = node.getWriteJava(1);
						String outra_operacao = node.getWriteJava(2);
						return "-" + ID_chamada_NUMERO + outra_operacao;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(1, object);
						node.doChildAction(2, object);
					}
				});
		outra_operacao.addProduction(
				VEZES.and(ID_chamada_NUMERO).and(outra_operacao),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO = node.getWriteJava(1);
						String outra_operacao = node.getWriteJava(2);
						return "*" + ID_chamada_NUMERO + outra_operacao;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(1, object);
						node.doChildAction(2, object);
					}
				});
		outra_operacao.addProduction(
				DIVIDIDO.and(ID_chamada_NUMERO).and(outra_operacao),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO = node.getWriteJava(1);
						String outra_operacao = node.getWriteJava(2);
						return "/" + ID_chamada_NUMERO + outra_operacao;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(1, object);
						node.doChildAction(2, object);
					}
				});
		outra_operacao.addProduction(Terminal.BLANK, new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				return "";
			}

			@Override
			public void doAction(NonLeaf node, Object object) {

			}
		});

		chamada_comparacao_operacao.addProduction(
				abre_parenteses.and(assinatura_argumentos).and(separa),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String assinatura_argumentos = node.getWriteJava(1);
						String separa = node.getWriteJava(2);
						return "(" + assinatura_argumentos + separa;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						List<Object> l = (List<Object>)object;
						Definicao definicaoTest = (Definicao) l.get(1);
						if (semanticAnalyzer.getDefinicoes().contains(
								definicaoTest)) {
							Definicao definicao = semanticAnalyzer
									.getDefinicoes().get(
											semanticAnalyzer.getDefinicoes()
													.indexOf(definicaoTest));
							Funcao funcao = (Funcao) definicao;
							
							List<Object> l2 = new ArrayList<>();
							
							l2.add(l.get(0));
							l2.add(funcao);

							node.doChildAction(1, l2);
							
							if(funcao.getPosition() < funcao.getParametros().size() || funcao.getPosition() > funcao.getParametros().size()){
								throw new SemanticException("Quantidade de argumentos esperada era "+funcao.getParametros().size());
							} else {
								funcao.setPosition(0);
							}
							
							node.doChildAction(2, l.get(0));
						}
					}
				});

		chamada_comparacao_operacao.addProduction(Terminal.BLANK,
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						return "";
					}

					@Override
					public void doAction(NonLeaf node, Object object) {

					}
				});

		chamada_comparacao_operacao.addProduction(MAIS.and(ID_chamada_NUMERO)
				.and(outra_operacao), new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				String outra_operacao = node.getWriteJava(2);
				return "+" + ID_chamada_NUMERO + outra_operacao;
			}

			@Override
			public void doAction(NonLeaf node, Object object)
					throws SemanticException {
				Funcao funcao = (Funcao)(((List<Object>) object).get(0));
				node.doChildAction(1, funcao);
				node.doChildAction(2, funcao);
			}
		});
		chamada_comparacao_operacao.addProduction(MENOS.and(ID_chamada_NUMERO)
				.and(outra_operacao), new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				String outra_operacao = node.getWriteJava(2);
				return "-" + ID_chamada_NUMERO + outra_operacao;
			}

			@Override
			public void doAction(NonLeaf node, Object object)
					throws SemanticException {
				Funcao funcao = (Funcao)(((List<Object>) object).get(0));
				node.doChildAction(1, funcao);
				node.doChildAction(2, funcao);
			}
		});
		chamada_comparacao_operacao.addProduction(VEZES.and(ID_chamada_NUMERO)
				.and(outra_operacao), new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				String outra_operacao = node.getWriteJava(2);
				return "*" + ID_chamada_NUMERO + outra_operacao;
			}

			@Override
			public void doAction(NonLeaf node, Object object)
					throws SemanticException {
				Funcao funcao = (Funcao)(((List<Object>) object).get(0));
				node.doChildAction(1, funcao);
				node.doChildAction(2, funcao);
			}
		});
		chamada_comparacao_operacao.addProduction(
				DIVIDIDO.and(ID_chamada_NUMERO).and(outra_operacao),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO = node.getWriteJava(1);
						String outra_operacao = node.getWriteJava(2);
						return "/" + ID_chamada_NUMERO + outra_operacao;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(1, object);
						node.doChildAction(2, object);
					}
				});
		chamada_comparacao_operacao.addProduction(
				MENORQ.and(ID_chamada_NUMERO), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO = node.getWriteJava(1);
						return "<" + ID_chamada_NUMERO;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(1, object);
					}
				});
		chamada_comparacao_operacao.addProduction(
				MAIORQ.and(ID_chamada_NUMERO), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO = node.getWriteJava(1);
						return ">" + ID_chamada_NUMERO;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(1, object);
					}
				});
		chamada_comparacao_operacao.addProduction(
				MENORQIGUAL.and(ID_chamada_NUMERO), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO = node.getWriteJava(1);
						return "<=" + ID_chamada_NUMERO;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(1, object);
					}
				});
		chamada_comparacao_operacao.addProduction(
				MAIORQIGUAL.and(ID_chamada_NUMERO), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO = node.getWriteJava(1);
						return ">=" + ID_chamada_NUMERO;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(1, object);
					}
				});
		chamada_comparacao_operacao.addProduction(IGUAL.and(ID_chamada_NUMERO),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO = node.getWriteJava(1);
						return "==" + ID_chamada_NUMERO;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(1, object);
					}
				});
		chamada_comparacao_operacao.addProduction(
				DIFERENTE.and(ID_chamada_NUMERO), new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO = node.getWriteJava(1);
						return "!=" + ID_chamada_NUMERO;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(1, object);
					}
				});

		separa.addProduction(MAIS.and(ID_chamada_NUMERO).and(outra_operacao),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO = node.getWriteJava(1);
						String outra_operacao = node.getWriteJava(2);
						return "+" + ID_chamada_NUMERO + outra_operacao;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(1, object);
						node.doChildAction(2, object);
					}
				});
		separa.addProduction(MENOS.and(ID_chamada_NUMERO).and(outra_operacao),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO = node.getWriteJava(1);
						String outra_operacao = node.getWriteJava(2);
						return "-" + ID_chamada_NUMERO + outra_operacao;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(1, object);
						node.doChildAction(2, object);
					}
				});
		separa.addProduction(VEZES.and(ID_chamada_NUMERO).and(outra_operacao),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO = node.getWriteJava(1);
						String outra_operacao = node.getWriteJava(2);
						return "*" + ID_chamada_NUMERO + outra_operacao;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(1, object);
						node.doChildAction(2, object);
					}
				});
		separa.addProduction(DIVIDIDO.and(ID_chamada_NUMERO)
				.and(outra_operacao), new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				String ID_chamada_NUMERO = node.getWriteJava(1);
				String outra_operacao = node.getWriteJava(2);
				return "/" + ID_chamada_NUMERO + outra_operacao;
			}

			@Override
			public void doAction(NonLeaf node, Object object)
					throws SemanticException {
				node.doChildAction(1, object);
				node.doChildAction(2, object);
			}
		});
		separa.addProduction(MENORQ.and(ID_chamada_NUMERO),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO = node.getWriteJava(1);
						return "<" + ID_chamada_NUMERO;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(1, object);
					}
				});
		separa.addProduction(MAIORQ.and(ID_chamada_NUMERO),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO = node.getWriteJava(1);
						return ">" + ID_chamada_NUMERO;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(1, object);
					}
				});
		separa.addProduction(MENORQIGUAL.and(ID_chamada_NUMERO),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO = node.getWriteJava(1);
						return "<=" + ID_chamada_NUMERO;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(1, object);
					}
				});
		separa.addProduction(MAIORQIGUAL.and(ID_chamada_NUMERO),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO = node.getWriteJava(1);
						return ">=" + ID_chamada_NUMERO;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(1, object);
					}
				});
		separa.addProduction(IGUAL.and(ID_chamada_NUMERO),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO = node.getWriteJava(1);
						return "==" + ID_chamada_NUMERO;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(1, object);
					}
				});
		separa.addProduction(DIFERENTE.and(ID_chamada_NUMERO),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String ID_chamada_NUMERO = node.getWriteJava(1);
						return "!=" + ID_chamada_NUMERO;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						node.doChildAction(1, object);
					}
				});
		separa.addProduction(Terminal.BLANK, new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				return "";
			}

			@Override
			public void doAction(NonLeaf node, Object object) {

			}
		});

		ID_chamada_NUMERO_BOOL.addProduction(ID.and(ID_chamada),
				new SemanticAction() {

					@Override
					public String writeJava(NonLeaf node) {
						String id = node.getTokenExpression(0);
						String ID_chamada = node.getWriteJava(1);
						return id + ID_chamada;
					}

					@Override
					public void doAction(NonLeaf node, Object object)
							throws SemanticException {
						Funcao funcao = (Funcao) object;
						String id = node.getTokenExpression(0);
						Definicao definicaoTest = new Definicao(id,
								new Tipo(""));
						if (semanticAnalyzer.getDefinicoes().contains(
								definicaoTest)) {
							Definicao definicao = semanticAnalyzer
									.getDefinicoes().get(
											semanticAnalyzer.getDefinicoes()
													.indexOf(definicaoTest));
//							if (definicao.getTipo().getTipoNome()
//									.equals("inteiro")) {
							List<Object> l = new ArrayList<>();
							l.add(funcao);
							l.add(definicao);
							node.doChildAction(1, l);
//							} else {
//								throw new SemanticException("Tipo de " + id
//										+ " deveria ser inteiro");
//							}
						} else {
							if (funcao.getParametros().contains(definicaoTest)) {
								Definicao definicao = funcao.getParametros()
										.get(funcao.getParametros().indexOf(
												definicaoTest));
								if (definicao.getTipo().getTipoNome()
										.equals("inteiro")) {
									List<Object> l = new ArrayList<>();
									l.add(funcao);
									l.add(definicao);
									node.doChildAction(1, l);
								} else {
									throw new SemanticException("Tipo de " + id
											+ " deveria ser inteiro");
								}
							} else {
								throw new SemanticException("Identificador "
										+ id + " não declarado");
							}
						}
					}
				});

		ID_chamada_NUMERO_BOOL.addProduction(NUMEROS, new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				String numeros = node.getTokenExpression(0);
				return numeros;
			}

			@Override
			public void doAction(NonLeaf node, Object object) {

			}
		});
		ID_chamada_NUMERO_BOOL.addProduction(BOOLEANOS, new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				String booleanos = node.getTokenExpression(0);
				if(booleanos.equals("V")){
					return "true";
				} else {
					return "false";
				}
			}

			@Override
			public void doAction(NonLeaf node, Object object) {

			}
		});

		comparador.addProduction(MENORQ, new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				return "<";
			}

			@Override
			public void doAction(NonLeaf node, Object object) {

			}
		});
		comparador.addProduction(MAIORQ, new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				return ">";
			}

			@Override
			public void doAction(NonLeaf node, Object object) {

			}
		});
		comparador.addProduction(MENORQIGUAL, new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				return "<=";
			}

			@Override
			public void doAction(NonLeaf node, Object object) {

			}
		});
		comparador.addProduction(MAIORQIGUAL, new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				return ">=";
			}

			@Override
			public void doAction(NonLeaf node, Object object) {

			}
		});
		comparador.addProduction(IGUAL, new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				return "==";
			}

			@Override
			public void doAction(NonLeaf node, Object object) {

			}
		});
		comparador.addProduction(DIFERENTE, new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				return "!=";
			}

			@Override
			public void doAction(NonLeaf node, Object object) {

			}
		});

		operador.addProduction(MAIS, new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				return "+";
			}

			@Override
			public void doAction(NonLeaf node, Object object) {

			}
		});
		operador.addProduction(MENOS, new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				return "-";
			}

			@Override
			public void doAction(NonLeaf node, Object object) {

			}
		});
		operador.addProduction(VEZES, new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				return "*";
			}

			@Override
			public void doAction(NonLeaf node, Object object) {

			}
		});
		operador.addProduction(DIVIDIDO, new SemanticAction() {

			@Override
			public String writeJava(NonLeaf node) {
				return "/";
			}

			@Override
			public void doAction(NonLeaf node, Object object) {

			}
		});

		// Adicionando NonTerminal ao analisador sintático
		syntaxAnalyzer.addNonTerminal(escopo);
		syntaxAnalyzer.addNonTerminal(separa_escopo);
		syntaxAnalyzer.addNonTerminal(declara);
		syntaxAnalyzer.addNonTerminal(parametros);
		syntaxAnalyzer.addNonTerminal(separa_parametros);
		syntaxAnalyzer.addNonTerminal(escopo_funcao);
		syntaxAnalyzer.addNonTerminal(separa_escopo_funcao);
		syntaxAnalyzer.addNonTerminal(ID_chamada_NUMERO);
		syntaxAnalyzer.addNonTerminal(ID_chamada);
		syntaxAnalyzer.addNonTerminal(assinatura);
		syntaxAnalyzer.addNonTerminal(diversas_atribuicoes);
		syntaxAnalyzer.addNonTerminal(comparacao);
		syntaxAnalyzer.addNonTerminal(assinatura_argumentos);
		syntaxAnalyzer.addNonTerminal(argumentos);
		syntaxAnalyzer.addNonTerminal(separa_argumentos);
		syntaxAnalyzer.addNonTerminal(escopo_loop);
		syntaxAnalyzer.addNonTerminal(separa_escopo_loop);
		syntaxAnalyzer.addNonTerminal(escopo_condicional);
		syntaxAnalyzer.addNonTerminal(separa_escopo_condicional);
		syntaxAnalyzer.addNonTerminal(operacao);
		syntaxAnalyzer.addNonTerminal(outra_operacao);
		syntaxAnalyzer.addNonTerminal(chamada_comparacao_operacao);
		syntaxAnalyzer.addNonTerminal(separa);
		syntaxAnalyzer.addNonTerminal(ID_chamada_NUMERO_BOOL);
		syntaxAnalyzer.addNonTerminal(comparador);
		syntaxAnalyzer.addNonTerminal(operador);
		
			SyntaxNode root = syntaxAnalyzer.parse();
//			System.out.println(root);
			SemanticAction startAction = ((NonLeaf)root).getProduction().getSemanticAction();
			startAction.doAction((NonLeaf)root, null);
			String out = "public class Program{\n";
			out = out + startAction.writeJava((NonLeaf)root)+"\n}";
			return out;

	}

}