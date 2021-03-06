package tutor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import aluno.Aluno;
import persistencia.Persistencia;
import util.comparators.EmailComparator;
import util.comparators.MatriculaComparator;
import util.comparators.NomeComparator;
import util.comparators.PontuacaoComparator;
import util.controller.ErroController;
import util.controller.OpcaoController;
import util.tutor.MensagemTutor;
import util.tutor.TutorValidador;

/**
 * Controller dos tutores do sistema.
 * 
 * @author Marcus Vinicius
 */
public class TutorController {

	/**
	 * Conjunto de tutores que o sistema possui. Possui como chave o email do tutor,
	 * e como chave o objeto do tutor.
	 */
	private Map<String, Tutor> tutores;

	private Comparator<Tutor> ordem;
	
	private Persistencia persistencia;

	/**
	 * Construtor da classe.
	 */
	public TutorController() {
		this.tutores = new HashMap<String, Tutor>();
		this.ordem = new NomeComparator<Tutor>();
		this.persistencia = new Persistencia();
	}

	/**
	 * Cadastra um novo tutor no sistema.
	 * 
	 * @param disciplina
	 *            Disciplina que ele dara tutoria
	 * @param proficiencia
	 *            Proficiencia na disciplina que ele dara tutoria
	 * @param aluno
	 *            Aluno que sera um tutor
	 */
	public void cadastraTutor(String disciplina, int proficiencia, Aluno aluno) {
		try {
			if (TutorValidador.validaTutor(disciplina, proficiencia, aluno)) {
				Tutor tutor = new Tutor(disciplina, proficiencia, aluno);
				this.tutores.put(aluno.getEmail(), tutor);
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(ErroController.TORNA_TUTOR_INVALIDO.toString() + e.getMessage());
		}

	}

	/**
	 * Lista os tutores cadastrados no sistema
	 * 
	 * @return String uma representação textual dos tutores cadastrados no sistema.
	 */
	public String listarTutores() {

		List<Tutor> listaTutores = new ArrayList<Tutor>(this.tutores.values());

		Collections.sort(listaTutores, this.ordem);

		StringJoiner joiner = new StringJoiner(", ");

		for (Tutor tutor: listaTutores) {
			joiner.add(tutor.toString());
		}

		return joiner.toString();
	}

	/**
	 * Adiciona uma disciplina e sua proficiencia ao tutor.
	 * 
	 * @param email
	 *            Email do tutor
	 * @param disciplina
	 *            Disciplina que ele dara tutoria
	 * @param proficiencia
	 *            Proficiencia na disciplina que ele dara tutoria
	 */
	public void cadastraDisciplina(String email, String disciplina, int proficiencia) {
		try {
			if (this.validaTutor(email) && TutorValidador.validaDisciplina(disciplina)
					&& TutorValidador.validaProficiencia(proficiencia)) {

				this.tutores.get(email).addDisciplina(disciplina, proficiencia);

			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(ErroController.TORNA_TUTOR_INVALIDO.toString() + e.getMessage());
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException(ErroController.TORNA_TUTOR_INVALIDO.toString() + e.getMessage());
		}
	}

	/**
	 * Cadastra um novo horario de atendimento para um tutor.
	 * 
	 * @param email
	 *            Email do tutor
	 * @param horario
	 *            Horario do atendimento
	 * @param dia
	 *            Dia do atendimento
	 */
	public void cadastrarHorario(String email, String horario, String dia) {
		try {
			if (this.validaTutor(email) && TutorValidador.validaHorarioDeAtendimento(horario, dia)) {

				this.tutores.get(email).cadastrarHorario(horario, dia);

			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(ErroController.CADASTRAR_HORARIO_INVALIDO.toString() + e.getMessage());
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException(ErroController.CADASTRAR_HORARIO_INVALIDO.toString() + e.getMessage());
		}

	}

	/**
	 * Cadastra um novo local de atendimento para um tutor.
	 * 
	 * @param email
	 *            Email to tutor
	 * @param local
	 *            Local do atendimento
	 */
	public void cadastrarLocalDeAtendimento(String email, String local) {
		try {
			if (this.validaTutor(email) && TutorValidador.validaLocalAtendimento(local)) {

				this.tutores.get(email).cadastrarLocal(local);

			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(ErroController.CADASTRAR_LOCAL_INVALIDO.toString() + e.getMessage());
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException(ErroController.CADASTRAR_LOCAL_INVALIDO.toString() + e.getMessage());
		}

	}

	/**
	 * Consulta se um tutor possui um determinado horario de atendimento.
	 * 
	 * @param horario
	 *            Horario de atendimento
	 * @param dia
	 *            Dia de atendimento
	 * @return um <code>boolean</boolean> que informa se o tutor possui ou nao o
	 *         horario de atendimento
	 */
	public boolean consultaHorario(String email, String horario, String dia) {

		boolean resultado = false;

		try {
			
			if(TutorValidador.validaEmail(email)) {
				if (!this.existeTutor(email)) {
					resultado = false;
				} else if (TutorValidador.validaHorarioDeAtendimento(horario, dia)) {

					resultado = this.tutores.get(email).consultaHorario(horario, dia);

				}
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(ErroController.CADASTRAR_HORARIO_INVALIDO.toString() + e.getMessage());
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException(ErroController.CADASTRAR_HORARIO_INVALIDO.toString() + e.getMessage());
		}
		return resultado;

	}

	/**
	 * Consulta se um tutor possui um determinado local de atendimento.
	 * 
	 * @param local
	 *            Local de atendimento
	 * @return um <code>boolean</boolean> que informa se o tutor possui ou nao o
	 *         local de atendimento
	 */
	public boolean consultaLocal(String email, String local) {

		boolean resultado = false;
		try {
			
			if(TutorValidador.validaEmail(email)) {
				if (!this.existeTutor(email)) {
					resultado = false;
				} else if (TutorValidador.validaLocalAtendimento(local)) {

					resultado = this.tutores.get(email).consultaLocal(local);

				}
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(ErroController.CADASTRAR_HORARIO_INVALIDO.toString() + e.getMessage());
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException(ErroController.CADASTRAR_HORARIO_INVALIDO.toString() + e.getMessage());
		}

		return resultado;

	}

	/**
	 * Veririca pelo email se um tutor esta cadastradado.
	 * 
	 * @param email
	 *            Email do aluno
	 * @return um <code>boolean</code> que informa se o tutor esta cadastrado
	 */
	public boolean existeTutor(String email) {

		return this.tutores.containsKey(email);

	}

	/**
	 * Recupera um tutor pelo seu email.
	 * 
	 * @param email
	 *            Email do tutor
	 * @return uma <code>String</code> que representa o tutor
	 */
	public String recuperaTutor(String email) {
		String resultado = "";

		if (this.validaTutor(email)) {
			resultado = this.tutores.get(email).toString();

		}

		return resultado;
	}

	/**
	 * Recupera para uma ajuda um tutor que possui determinada disciplina, horario,
	 * dia e local de atendimento.
	 * 
	 * @param disciplina
	 *            disciplina do tutor
	 * @param horario
	 *            horario do tutor
	 * @param dia
	 *            dia de atendimento do tutor
	 * @param localInteresse
	 *            local de atendimento do tutor
	 */
	public Tutor recuperaTutorParaAjudaPresencial(String disciplina, String horario, String dia,
			String localInteresse) {

		List<Tutor> tutoresParaAjuda = new ArrayList<>();
		tutoresParaAjuda.addAll(this.tutores.values());

		tutoresParaAjuda = tutoresParaAjuda.stream().filter((Tutor t) -> t.disciplinaExiste(disciplina) == true)
				.collect(Collectors.toList());

		tutoresParaAjuda = tutoresParaAjuda.stream().filter((Tutor t) -> t.consultaHorario(horario, dia) == true)
				.collect(Collectors.toList());

		tutoresParaAjuda = tutoresParaAjuda.stream().filter(t -> t.consultaLocal(localInteresse) == true)
				.collect(Collectors.toList());

		PontuacaoComparator pontuacaoComparator = new PontuacaoComparator();
		tutoresParaAjuda.sort(pontuacaoComparator);

		if (tutoresParaAjuda.size() != 0) {
			return tutoresParaAjuda.get(0);
		}

		return null;
	}

	/**
	 * Recupera para uma ajuda um tutor que possui determinada disciplina.
	 * 
	 * @param disciplina
	 *            disciplina do tutor
	 * @return uma objeto tutor
	 */
	public Tutor recuperaTutorParaAjudaOnline(String disciplina) {

		try {
			if (TutorValidador.validaDisciplina(disciplina)) {

				List<Tutor> tutoresParaAjuda = new ArrayList<>();
				tutoresParaAjuda.addAll(this.tutores.values());

				tutoresParaAjuda = tutoresParaAjuda.stream().filter(t -> t.disciplinaExiste(disciplina) == true)
						.collect(Collectors.toList());

				PontuacaoComparator pontuacaoComparator = new PontuacaoComparator();
				tutoresParaAjuda.sort(pontuacaoComparator);

				if (tutoresParaAjuda.size() != 0) {
					return tutoresParaAjuda.get(0);
				}

			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(
					ErroController.PEDIDO_DE_AJUDA_ONLINE_INVALIDA.toString() + e.getMessage());
		}

		return null;
	}

	/**
	 * Valida o email do tutor e verifica se o mesmo existe no sistema
	 * 
	 * @param email o email do tutor
	 */
	private boolean validaTutor(String email) {
		if (TutorValidador.validaEmail(email)) {
			if (!this.existeTutor(email)) {
				throw new NoSuchElementException(ErroController.TUTOR_NAO_CADASTRADO.toString());
			}

		}
		return true;
	}

	/**
	 * Retorna a nota geral da avaliação de determinado tutor
	 * 
	 * @param email
	 *            o email do tutor
	 * @return
	 */
	public String retornaNotaAvaliacao(String email) {
		
		if(validaTutor(email)) {
			return String.format("%.2f", tutores.get(email).getNotaAvaliacao());			
		}
		
		return "";
	}

	/**
	 * Retorna o nível de determinado tutor
	 * 
	 * @param email
	 *            o email do tutor
	 * @return
	 */
	public String retornaNivel(String email) {
		
		if(validaTutor(email)) {
			return tutores.get(email).getNivel();			
		}
		
		return "";
	}

	/**
	 * Avalia a ajuda de um determinado tutor
	 * 
	 * @param email
	 *            o email do tutor a ser avaliado
	 * @param nota
	 *            a nota da avaliação
	 */
	public void avaliaTutor(String email, int nota) {
		if (validaTutor(email) && TutorValidador.validaNotaAvaliacao(nota)) {
			tutores.get(email).alteraNotaAvaliacao(nota);
		}
	}
	
	/**
	 * @see Tutor#receberDoacao(int)
	 * 
	 * @param emailTutor
	 * @param totalCentavos
	 */
	public void doar(String emailTutor, int totalCentavos) {
		
		//A exceção é pega na classe acima
		if(this.validaTutor(emailTutor) && TutorValidador.validaDoacao(totalCentavos)){
			
			this.tutores.get(emailTutor).receberDoacao(totalCentavos);
			
		}
		
	}
	
	/**
	 * Método que avalia qual a taxa que será usada para o calculo das doação para um tutor
	 * 
	 * @param emailTutor
	 * @return
	 */
	public double getTaxaDoacaoTutor(String emailTutor) {
		double taxa = 0;
		
		if(this.validaTutor(emailTutor)){
			taxa = this.tutores.get(emailTutor).calculaTaxaDoacaoTutor();
		}
		
		return taxa;
		
	}
	
	/**
	 * Método que retorna o total do dinheiro de doações arrecadado por um tutor
	 * 
	 * @param emailTutor
	 * @return
	 */
	public int totalDinheiroTutor(String emailTutor) {
		
		int total = 0;
		
		try {
			if(this.validaTutor(emailTutor)){
				total = this.tutores.get(emailTutor).getDoacao();
			}

		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(MensagemTutor.CONSULTA_DINHEIRO_TUTOR.toString() + 
					MensagemTutor.EMAIL_TUTOR_INVALIDO.toString());

		} catch (NoSuchElementException e) {
			throw new NoSuchElementException(MensagemTutor.CONSULTA_DINHEIRO_TUTOR.toString() + 
					MensagemTutor.ERRO_BUSCA_TUTOR.toString());
		}

		return total;
	}

	/**
	 * Define como será a ordenação da lista de tutores
	 * 
	 * @param ordem
	 *            o atributo que define a ordenação
	 */
	public void configuraOrdem(String ordem) {
		
		OpcaoController op = OpcaoController.getEnumByString(ordem);
		
		switch (op) {
		case MATRICULA:
			this.ordem = new MatriculaComparator<Tutor>();
			break;
		case NOME:
			this.ordem = new NomeComparator<Tutor>();
			break;
		case EMAIL:
			this.ordem = new EmailComparator<Tutor>();
			break;
		default:
			throw new IllegalArgumentException(ErroController.
					CONFIGURA_ORDEM_INVALIDA.toString());
		}
	}
	
	/**
	 * @see Persistencia#salvaTutores(String)
	 */
	public void salvaTutores() throws IOException{
		
		this.persistencia.salvaTutores(this.listarTutores());
		
	}
	
	/**
	 * @see Persistencia#limparTutores()
	 */
	public void limpar(){
		this.tutores.clear();
		this.persistencia.limparTutores();
	}

	/**
	 * @see Persistencia#carregaTutores()
	 */
	public String carregaTutores() throws IOException{
		return this.persistencia.carregaTutores();
	}
	
}