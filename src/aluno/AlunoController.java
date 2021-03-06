package aluno;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

import persistencia.Persistencia;
import util.aluno.AlunoValidador;
import util.aluno.MensagemAluno;
import util.comparators.EmailComparator;
import util.comparators.MatriculaComparator;
import util.comparators.NomeComparator;
import util.controller.ErroController;
import util.controller.OpcaoController;

/**
 * Controller dos alunos do sistema.
 * 
 * @author Marcus Vinicius
 */
public class AlunoController {

	/**
	 * Conjunto de alunos que o sistema possui.
	 */
	private Map<String, Aluno> alunos;

	/**
	 * O tipo da ordenação de aluno.
	 */
	private Comparator<Aluno> ordem;
	
	private Persistencia persistencia;

	/**
	 * Construtor da classe.
	 */
	public AlunoController() {
		this.alunos = new HashMap<String, Aluno>();
		this.ordem = new NomeComparator<Aluno>();
		persistencia = new Persistencia();
	}

	/**
	 * Cadastra um aluno no sistema.
	 * 
	 * @param nome
	 *            o nome do aluno
	 * @param matricula
	 *            a matricula do aluno
	 * @param codigoCurso
	 *            o código do curso que o aluno frequenta
	 * @param telefone
	 *            o telefone do aluno
	 * @param email
	 *            o email do aluno
	 */
	public void cadastrarAluno(String nome, String matricula, int codigoCurso, String telefone, String email) {
		try {
			if (AlunoValidador.validaAluno(nome, matricula, telefone, email, codigoCurso)
					&& this.validaAlunoExistente(matricula)) {

				Aluno aluno = new Aluno(matricula, nome, telefone, email, codigoCurso);
				this.alunos.put(matricula, aluno);

			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(ErroController.CADASTRO_ALUNO_INVALIDO.toString() + e.getMessage());
		} catch (IllegalStateException e) {
			throw new IllegalArgumentException(ErroController.CADASTRO_ALUNO_INVALIDO.toString() + e.getMessage());
		}

	}

	/**
	 * Obtem a representação textual de um aluno através de sua matrícula
	 * 
	 * @param matricula
	 *            a matrícula do aluno
	 * @return String a representação textual dos atributos do aluno
	 */
	public String recuperaAluno(String matricula) {
		String resultado = "";
		try {
			if (this.validaAluno(matricula)) {
				resultado = this.alunos.get(matricula).toString();
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(ErroController.BUSCA_ALUNO_INVALIDA.toString() + e.getMessage());
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException(ErroController.BUSCA_ALUNO_INVALIDA.toString() + e.getMessage());
		}

		return resultado;

	}

	/**
	 * Retorna um aluno de acordo com sua matricula.
	 * 
	 * @param matricula
	 *            Matricula do aluno
	 * @return um objeto <code>Aluno</code> que representa um aluno
	 */
	public Aluno getAlunoPelaMatricula(String matricula) {
		Aluno aluno = null;
		if (this.validaAluno(matricula)) {
			aluno = this.alunos.get(matricula);
		}
		return aluno;
	}

	/**
	 * Lista os alunos cadastrados no sistema
	 * 
	 * @return String uma representação textual dos alunos cadastrados no sistema.
	 */
	public String listarAlunos() {

		List<Aluno> listaAlunos = new ArrayList<Aluno>(this.alunos.values());
		Collections.sort(listaAlunos, this.ordem);

		StringJoiner joiner = new StringJoiner(", ");
		
		for (Aluno aluno: listaAlunos) {
			joiner.add(aluno.toString());
		}

		return joiner.toString();
	}

	/**
	 * Retorna um determinada informação de um aluno.
	 * 
	 * @param matricula
	 *            a matricula do aluno
	 * @param atributo
	 *            o atributo que se deseja pegar
	 * @return uma <code>string</code> que representa o atributo desejado
	 */
	public String getInfoAluno(String matricula, String atributo) {

		try {
			if (this.validaAluno(matricula)) {

				OpcaoController op = OpcaoController.getEnumByString(atributo);

				switch (op) {
				case NOME:
					return this.alunos.get(matricula).getNome();
				case TELEFONE:
					return this.alunos.get(matricula).getTelefone();
				case EMAIL:
					return this.alunos.get(matricula).getEmail();
				default:
					break;
				}
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(ErroController.GET_INFO_ALUNO_INVALIDA.toString() + e.getMessage());
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException(ErroController.GET_INFO_ALUNO_INVALIDA.toString() + e.getMessage());
		}

		return "";
	}

	/**
	 * Verifica se existe um aluno com a matrícula passada
	 * 
	 * @param matricula
	 *            a matrícula do aluno
	 * @return boolean true, caso a matrícula exista, false, caso contrário.
	 */
	public boolean existeAluno(String matricula) {
		return this.alunos.containsKey(matricula);
	}

	/**
	 * Verifica se a matrícula do aluno não foi cadastrada no sistema.
	 * 
	 * @param matricula
	 *            a matricula do aluno
	 * @return boolean true, caso o aluno não tenha sido cadastrado
	 * @throws IllegalStateException
	 *             caso o aluno já tenha sido cadastrado
	 */
	private boolean validaAlunoExistente(String matricula) {
		if (AlunoValidador.validaMatricula(matricula)) {
			if (this.existeAluno(matricula)) {
				throw new IllegalStateException(MensagemAluno.ALUNO_JA_CADASTRADO.toString());
			}
		}
		return true;

	}

	/**
	 * Verifica se o aluno da matricula passada foi cadastrado no sistema
	 * 
	 * @param matricula
	 *            a matricula do aluno
	 * @return true caso a matricula sido cadastrada
	 * @throws NoSuchElementException
	 *             caso a matrícula não tenha sido cadastrada.
	 */
	public boolean validaAluno(String matricula) {
		if (AlunoValidador.validaMatricula(matricula)) {
			if (!this.existeAluno(matricula)) {
				throw new NoSuchElementException(MensagemAluno.ALUNO_NAO_ENCONTRADO.toString());
			}
		}
		return true;
	}

	/**
	 * Define como será a ordenação da lista de alunos
	 * 
	 * @param ordem
	 *            o atributo que define a ordenação
	 */
	public void configuraOrdem(String ordem) {
    
		OpcaoController op = OpcaoController.getEnumByString(ordem);
		
		switch (op) {
			case MATRICULA:
				this.ordem = new MatriculaComparator<Aluno>();
				break;
			case NOME:
				this.ordem = new NomeComparator<Aluno>();
				break;
			case EMAIL:
				this.ordem = new EmailComparator<Aluno>();
				break;
			default:
				throw new IllegalArgumentException(ErroController.
						CONFIGURA_ORDEM_INVALIDA.toString());
				}
	}
	/**
	 * @see Persistencia#salvaAlunos(String)
	 */
	public void salvaAlunos() throws IOException{
		this.persistencia.salvaAlunos(this.listarAlunos());
	}
	
	/**
	 * @see Persistencia#limparAlunos()
	 */
	public void limpar(){
		this.alunos.clear();
		this.persistencia.limparAlunos();
	}
	
	/**
	 * @see Persistencia#carregaAlunos()
	 */
	public String carregaAlunos() throws IOException{
		return this.persistencia.carregaAlunos();
	}

	
	
}
