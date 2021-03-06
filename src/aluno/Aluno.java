package aluno;

import util.aluno.AlunoValidador;
import util.comparators.Discente;

/**
 * Representacao de um aluno
 * 
 * @author Rayla Medeiros Araújo
 *
 */
public class Aluno implements Discente{

	/**
	 * A matricula do aluno
	 */
	private String matricula;

	/**
	 * O nome do aluno
	 */
	private String nome;

	/**
	 * O telefone do aluno
	 */
	private String telefone;

	/**
	 * O email do aluno
	 */
	private String email;

	/**
	 * A avaliacao do aluno
	 */
	private int notaAvaliacao;

	/**
	 * O codigo do curso que o aluno cursa
	 */
	private int codCurso;

	/**
	 * Constroi um aluno a partir de sua matricula, nome, telefone, email e codigo
	 * do curso.
	 * 
	 * @param matricula
	 *            a matricula do aluno
	 * @param nome
	 *            o nome do aluno
	 * @param telefone
	 *            o telefone do aluno
	 * @param email
	 *            o email do aluno
	 * @param codCurso
	 *            o codigo do curso do aluno
	 */
	public Aluno(String matricula, String nome, String telefone, String email, int codCurso) {
		if (AlunoValidador.validaAluno(nome, matricula, telefone, email, codCurso)) {
			this.matricula = matricula;
			this.nome = nome;
			this.email = email;
			this.telefone = telefone;
			this.codCurso = codCurso;
		}
		this.notaAvaliacao = 5;
	}

	@Override
	public String getMatricula() {
		return matricula;
	}

	@Override
	public String getNome() {
		return nome;
	}

	public String getTelefone() {
		return telefone;
	}

	@Override
	public String getEmail() {
		return email;
	}

	public int getCodCurso() {
		return codCurso;
	}

	public int getNotaAvaliacao() {
		return notaAvaliacao;
	}

	public void setNotaAvaliacao(int notaAvaliacao) {
		this.notaAvaliacao = notaAvaliacao;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String retorno = this.matricula + " - " + this.nome + " - " + this.codCurso + " - ";

		if (!telefone.isEmpty()) {
			retorno += this.telefone + " - ";
		}

		retorno += this.email;

		return retorno;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((matricula == null) ? 0 : matricula.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aluno other = (Aluno) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		} else if (!matricula.equals(other.matricula))
			return false;
		return true;
	}

}
