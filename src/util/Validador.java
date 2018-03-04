package util;

import util.controller.ErroController;

public class Validador {

	/**
	 * Verifica se o texto é vazio ou nulo, caso seja é lançada uma exceção, caso
	 * contrário é retornado true.
	 * 
	 * @param texto
	 *            o texto a ser validado
	 * @param mensagem
	 *            a mensagem de erro, caso o texto seja inválido
	 * @exception IllegalArgumentException
	 *                caso o texto seja nulo ou vazio
	 * @return boolean que deve ser true quando o texto é válido.
	 */
	public static boolean validaTexto(String texto, String mensagem) {
		if (texto == null || texto.trim().isEmpty()) {
			throw new IllegalArgumentException(mensagem);
		}
		return true;
	}

	/**
	 * Verifica se o valor passado é maior ou igual que zero
	 * 
	 * @param valor
	 *            o valor a ser validado
	 * @throws IllegalArgumentException
	 *             caso o valor seja negativo
	 * @return true caso o valor seja maior que zero, false caso contrário
	 */
	public static boolean validaMaiorIgualZero(double valor, String mensagem) {
		if (valor <= 0.0) {
			throw new IllegalArgumentException(mensagem);
		}
		return true;
	}
	
	/**
	 * Verifica se o valor passado é maior que zero
	 * 
	 * @param valor
	 *            o valor a ser validado
	 * @throws IllegalArgumentException
	 *             caso o valor seja negativo
	 * @return true caso o valor seja maior que zero, false caso contrário
	 */
	public static boolean validaMenorQueZero(double valor, String mensagem) {
		if (valor < 0.0) {
			throw new IllegalArgumentException(mensagem);
		}
		return true;
	}

	/**
	 * Verifica se o valor é menor que 0
	 * 
	 * @param valor
	 *            o valor a ser validado
	 * @throws IllegalArgumentException
	 *             caso o valor seja maior ou igual a 6
	 * @return true se o valor é menor que 6, false caso contrário
	 */
	public static boolean validaMenorQue6(double valor, String mensagem) {
		if (valor >= 6.0) {
			throw new IllegalArgumentException(mensagem);
		}
		return true;
	}

	/**
	 * 
	 * @param email
	 * @param mensagem
	 * @return
	 */
	public static boolean validaEmail(String email, String mensagem) {
		if (validaTexto(email, mensagem)) {
			String formaEmail = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
			if (!email.matches(formaEmail)) {
				throw new IllegalArgumentException(mensagem);
			}
		}

		return true;
	}

	/**
	 * 
	 * @param valor
	 * @param mensagem
	 * @return
	 */
	public static boolean validaMaiorOuIgualAZero(double valor, String mensagem) {
		if (valor < 0.0) {
			throw new IllegalArgumentException(mensagem);
		}
		return true;
	}
	
	public static boolean validaMenorQueCinco(double valor, String mensagem) {
		if (valor > 5.0) {
			throw new IllegalArgumentException(mensagem);
		}
		return true;
	}
	
	public static boolean validaObjeto(Object object, String mensagem){
		if(object == null){
			throw new NullPointerException(mensagem);
		}
		return true;
	}

}
