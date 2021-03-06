package edu.br.ufrpe.uag.compiler.music;

import edu.br.ufrpe.uag.compiler.exceptions.SemanticException;


public class Converter {
	
	public static final String[] ALFABETO = {"A","B","C","D","E","F","G",
											 "H","I","J","K","L","M","N",/*"O","P","Q","R","S","T","U","V","W","X","Y","Z,"*/
											 "a","b","c","d","e","f","g",
											 "h","i","j","k","l","m","n",
											 "o","p","q","r","s","t","u",
											 "v","w","x","y","z"};
//	private static final String[] NOTAS = {"A","B","C","D","E","F","G"};
	private static final String[] NOTAS_ACIDENTES = {"A","B","C","D","E","F","G","A#","C#","D#","F#","G#","A$","B$","D$","E$","G$"};
	private static final int[]   NOTAS_NACIDENTES = { 3 , 5 , 0 , 2 , 4 , -1, 1 , -2 ,  7 , -3 ,  6 , -4 , -4 , -2 , 7  , -3 ,  6 };
	
	private static final String[] FIGURAS     =   {"sb", "m","sm", "c", "sc",   "f",   "sf"};
	private static final String[] FIGURAS_TEX =   {"wh", "ha", "qa", "ca", "cca", "ccca", "cccca"};
	private static final String[] FIGURAS_ACORDE= {"zw","zhu","zqu","zcu","zccu","zcccu","zccccu"};
	
	private static final String[] FIGURAS_PAUSA   =   {"psb"  ,"pm","psm", "pc", "psc",   "pf",   "psf"};
	private static final String[] FIGURAS_PAUSA_TEX = {"pause","hp", "qp", "ds",  "qs",   "hs",   "qqs"};
	
	/**
	 * Este método converte a nota na linguagem para a forma do pacote musixtex
	 * @param nota
	 * @param oitavas
	 * @return a nota convertida para o tex
	 * @throws SemanticException
	 */
	public static String converteNota(String nota, String oitavas){
		String notaResultado = "";
		for(int i = 0; i < ALFABETO.length; i++){
			if(nota.equals(ALFABETO[i])){
				if(oitavas.equals("")){
					notaResultado = ALFABETO[i];
				} else {
					if(oitavas.startsWith("+")){
						oitavas = oitavas.replace("+", "");
						int nOitavas = Integer.valueOf(oitavas);
						notaResultado = ALFABETO[i + nOitavas*7];
					} else {
						oitavas = oitavas.replace("-", "");
						int nOitavas = Integer.valueOf(oitavas);
						notaResultado = ALFABETO[i - nOitavas*7];
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
	/**
	 * Este método retorna a quantidade de sustenidos ou bemois do tom da música
	 * @param nota
	 * @param acidentes
	 * @return
	 */
	public static String converterTom(String nota, String acidentes) {
		String tom = nota+acidentes;
		for(int i = 0; i < NOTAS_ACIDENTES.length; i++){
			if(tom.equals(NOTAS_ACIDENTES[i])){
				tom = String.valueOf(NOTAS_NACIDENTES[i]);
				break;
			}
		}
		return tom;
	}
	public static String converteClave(String clave) {
		if(clave.equals("Sol")){
			return "\\setclef1{0000}";
		} else {
			return "\\setclef1{6000}";
		}
	}
	
	/**
	 * Este método converte a figura de som na linguagem para a forma do pacote musixtex
	 * @param fig_som
	 * @return
	 */
	public static String convertePausa(String fig_som) {
		String figuraResultado = "";
		for(int i = 0; i < FIGURAS_PAUSA.length; i++){
			if(fig_som.equals(FIGURAS_PAUSA[i])){
				figuraResultado = FIGURAS_PAUSA_TEX[i];
				break;
			}
		}
		return figuraResultado;
	}
	public static String converteFiguraSomAcorde(String fig_som) {
		String figuraResultado = "";
		for(int i = 0; i < FIGURAS.length; i++){
			if(fig_som.equals(FIGURAS[i])){
				figuraResultado = FIGURAS_ACORDE[i];
				break;
			}
		}
		return figuraResultado;
	}
	
}
