package edu.br.ufrpe.uag.compiler.music;

import edu.br.ufrpe.uag.compiler.exceptions.SemanticException;


public class Converter {
	
	private static final String[] ALFABETO = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","x","w","y","z"};
	private static final String[] NOTAS = {"A","B","C","D","E","F","G"};
	
	private static final String[] FIGURAS     = {"sb", "m","sm", "c", "sc",   "f",   "sf"};
	private static final String[] FIGURAS_TEX = {"wh","ha","qa","ca","cca","ccca","cccca"};
	
	/**
	 * Este método converte a nota na linguagem para a forma do pacote musixtex
	 * @param nota
	 * @param oitavas
	 * @return
	 * @throws SemanticException 
	 */
	public static String converteNota(String nota, String oitavas){
		String notaResultado = "";
		for(int i = 0; i < NOTAS.length; i++){
			if(nota.equals(NOTAS[i])){
				if(oitavas.equals("")){
					notaResultado = ALFABETO[i];
				} else {
					int nOitavas = Integer.valueOf(oitavas);
					if(i + nOitavas*8 > ALFABETO.length){
//						throw new SemanticException(row, "A nota desejada é muito alta e não pode ser representada!");
					} else {
						notaResultado = ALFABETO[i + nOitavas*8];
					}
				}
				break;
			}
		}
		return notaResultado;
	}
	/**
	 * Este método converte a figura de som na linguagem para a forma do pacote musixtex
	 * @param fig_som
	 * @return
	 */
	public static String converteFiguraSom(String fig_som) {
		String figuraResultado = "";
		for(int i = 0; i < FIGURAS.length; i++){
			if(fig_som.equals(FIGURAS[i])){
				figuraResultado = FIGURAS_TEX[i];
				break;
			}
		}
		return figuraResultado;
	}
	
}
