package tutor.ajuda;

import tutor.Tutor;

/**
 * Classe que representa uma ajuda online.
 * 
 * @author Marcus Vinicius
 */
public class AjudaOnline extends Ajuda {

	/**
	 * Construtor da classe.
	 * 
	 * @param id
	 *            identificador da ajuda
	 * @param disciplina
	 *            disciplina tema da ajuda
	 * @param tutor
	 *            o tutor que realizará a ajuda
	 */
	public AjudaOnline(Integer id, String disciplina, Tutor tutor) {

		super(id, disciplina, tutor);

	}

	@Override
	public String toString() {
		String result = "Tutor - " + this.tutor.getMatricula() + ", disciplina - " + this.disciplina;

		return result;
	}
}