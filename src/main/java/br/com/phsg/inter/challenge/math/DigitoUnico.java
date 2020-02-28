/**
 * 
 */
package br.com.phsg.inter.challenge.math;

/**
 * @author pedro.gomes - 2020/02/27
 * 
 */
public class DigitoUnico {

	private static boolean verificaNumero(String numero) {				   
		return numero != null && numero.length() > 0 && numero.length() <= Integer.MAX_VALUE;
	}
	
	private static boolean verificaNumero(int numero) {
		return numero > 0 && numero <= 1000000;
	}
	
	public static int digitoUnico(String numero, int repeticao) {
		if (!verificaNumero(repeticao)) {
			throw new RuntimeException("Número de repetições maior que 1000000 ou igual a 0.");
		}
		
		if (!verificaNumero(numero)) {
			throw new RuntimeException("Número maior que o máximo inteiro ou igual a 0.");
		}
		
		String numeroConcatenado = "";
		for (int i = 0; i < repeticao; ++i) {
			numeroConcatenado = numeroConcatenado.concat(numero);
		};

		return digitoUnico(numeroConcatenado);
	}
	
	private static int digitoUnico(String numero) {
		Integer unico = 0;
		if (numero.length() == 1)
			return Integer.parseInt(numero);
		
		for (int i = 0; i < numero.length(); ++i) {
			unico += Integer.parseInt(String.valueOf(numero.charAt(i)));
		};

		return digitoUnico(unico.toString());
	}
	
//	public static void main(String[] args) {
//		int numero = DigitoUnico.digitoUnico("9875", 4);
//		Assert.isTrue(numero == 8, "Não é igual a 8! É: " + numero);
//		System.out.println("Único = " + numero);
//	}
}
