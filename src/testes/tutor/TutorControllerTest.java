package testes.tutor;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import aluno.Aluno;
import aluno.AlunoController;
import testes.TestUtil;
import tutor.TutorController;

public class TutorControllerTest {

	private TutorController tutorController;
	private AlunoController alunoController;
	
	@Before
	public void setUp() throws Exception {
		tutorController = new TutorController();
		alunoController = new AlunoController();
		
		alunoController.cadastrarAluno("Gauds Lindo", "11715963", 2, "99984-1347", "gaudslindo99@gmail.com");		
		Aluno gauds = alunoController.getAlunoPelaMatricula("11715963");
		tutorController.cadastraTutor("P2", 5, gauds);
	}

	/*---------------------- TESTES CADASTRAR TUTOR ----------------------*/
	
	@Test
	public void testCadastrarTutorValido() {
		alunoController.cadastrarAluno("Tiagu Terror", "658372", 2, "99984-1347", "tiaguus123@gmail.com");		
		Aluno aluno = alunoController.getAlunoPelaMatricula("658372");
		
		tutorController.cadastraTutor("Discreta", 5, aluno);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastrarTutorDisciplinaVazia() {
		Aluno aluno = alunoController.getAlunoPelaMatricula("11715963");
		
		tutorController.cadastraTutor("", 3, aluno);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastrarTutorDisciplinaComEspacos() {
		Aluno aluno = alunoController.getAlunoPelaMatricula("11715963");
		
		tutorController.cadastraTutor("   ", 3, aluno);	
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastrarTutorProficienciaMenoQueZero() {
		Aluno aluno = alunoController.getAlunoPelaMatricula("11715963");
		
		tutorController.cadastraTutor("POO", -10, aluno);	
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastrarTutorProficienciaIgualAZero() {
		Aluno aluno = alunoController.getAlunoPelaMatricula("11715963");
		
		tutorController.cadastraTutor("POO", 0, aluno);	
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastrarTutorProficienciaMaiorQueCinco() {
		Aluno aluno = alunoController.getAlunoPelaMatricula("11715963");
		
		tutorController.cadastraTutor("POO", 6, aluno);	
	}
	
	@Test(expected=NullPointerException.class)
	public void testCadastrarTutorAlunoNulo() {
		Aluno aluno = alunoController.getAlunoPelaMatricula("11715963");
		
		tutorController.cadastraTutor("POO", 3, null);
	}
	
	/*---------------------- TESTES CADASTRAR TUTOR ----------------------*/
	
	@Test
	public void testListarTutores() {
		
		alunoController.cadastrarAluno("Livia Topper", "11715945", 2, "99974-1357", "liviap2@gmail.com");
		alunoController.cadastrarAluno("Jorge Imortal", "11715987", 2, "99955-5017", "jorgesplab@gmail.com");
		alunoController.cadastrarAluno("Joseana GG", "11715947", 2, "99974-1477", "joseanaic@gmail.com");
		
		Aluno gauds = alunoController.getAlunoPelaMatricula("11715963");
		Aluno livia = alunoController.getAlunoPelaMatricula("11715945");
		Aluno jorge = alunoController.getAlunoPelaMatricula("11715987");
		Aluno joseana = alunoController.getAlunoPelaMatricula("11715947");
		
		tutorController.cadastraTutor("P2", 5, gauds);
		tutorController.cadastraTutor("P2", 4, livia);
		tutorController.cadastraTutor("P1", 3, jorge);
		tutorController.cadastraTutor("IC", 5, joseana);



		String resultadoEsperado = "11715963 - Gauds Lindo - 2 - 99984-1347 - gaudslindo99@gmail.com, " 
				+ "11715987 - Jorge Imortal - 2 - 99955-5017 - jorgesplab@gmail.com, "
				+ "11715947 - Joseana GG - 2 - 99974-1477 - joseanaic@gmail.com, "
				+ "11715945 - Livia Topper - 2 - 99974-1357 - liviap2@gmail.com";
		assertEquals(resultadoEsperado, tutorController.listarTutores());
	}
	
	/*---------------------- TESTES CADASTRAR DISCIPLINA ----------------------*/
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastraDisciplinaEmailVazio() {
		
		alunoController.cadastrarAluno("Gauds Lindo", "11715963", 2, "99984-1347", "gaudslindo99@gmail.com");		
		Aluno gauds = alunoController.getAlunoPelaMatricula("11715963");
		tutorController.cadastraTutor("P2", 5, gauds);

		tutorController.cadastraDisciplina("", "POO", 3);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastraDisciplinaEmailComEspacosEmBranco() {
		
		tutorController.cadastraDisciplina("  ", "POO", 3);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastraDisciplinaEmailNulo() {
		
		tutorController.cadastraDisciplina(null, "POO", 3);
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testCadastraDisciplinaEmailInexistente() {
		
		tutorController.cadastraDisciplina("niconiconi@gmail.com", "POO", 3);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastraDisciplinaNomeVazio() {
		
		tutorController.cadastraDisciplina("gaudslindo99@gmail.com", "", 3);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastraDisciplinaNomeComEspacosEmBranco() {
		
		tutorController.cadastraDisciplina("gaudslindo99@gmail.com", "   ", 3);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastraDisciplinaNomeNulo() {
		
		tutorController.cadastraDisciplina("gaudslindo99@gmail.com", null, 3);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastraDisciplinaProficienciaMenorQueZero() {
		
		tutorController.cadastraDisciplina("gaudslindo99@gmail.com", "POO", -10);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastraDisciplinaProficienciaMaiorQueCinco() {
		
		tutorController.cadastraDisciplina("gaudslindo99@gmail.com", "POO", 6);
	}
	
	@Test
	public void testCadastraDisciplinaValida() {
		
		tutorController.cadastraDisciplina("gaudslindo99@gmail.com", "POO", 2);
	}
	
	/*---------------------- TESTES CADASTRAR HORARIO ----------------------*/
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastrarHorarioEmailVazio() {

		tutorController.cadastrarHorario("", "13:00", "Segunda");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastrarHorarioEmailComEspacosEmBranco() {
		
		tutorController.cadastrarHorario("   ", "13:00", "Segunda");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastrarHorarioEmailNulo() {
		
		tutorController.cadastrarHorario(null, "13:00", "Segunda");
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testCadastrarHorarioEmailInexistente() {
		
		tutorController.cadastrarHorario("niconiconi@gmail.com", "POO", "Segunda");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastrarHorarioHoraVazia() {
		
		tutorController.cadastrarHorario("gaudslindo99@gmail.com", "", "Segunda");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastrarHorarioHoraComEspacosEmBranco() {
		
		tutorController.cadastrarHorario("gaudslindo99@gmail.com", "   ", "Segunda");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastrarHorarioHoraNula() {
		
		tutorController.cadastrarHorario("gaudslindo99@gmail.com", null, "Segunda");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastrarHorarioDiaVazio() {
		
		tutorController.cadastrarHorario("gaudslindo99@gmail.com", "13:00", "");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastrarHorarioDiaComEspacosEmBranco() {
		
		tutorController.cadastrarHorario("gaudslindo99@gmail.com", "14:00", "   ");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastrarHorarioDiaNulo() {
		
		tutorController.cadastrarHorario("gaudslindo99@gmail.com", "15:00", null);
	}
	
	@Test
	public void testCadastrarHorarioValido() {
		
		tutorController.cadastrarHorario("gaudslindo99@gmail.com", "15:00", "Quarta");
	}
	
	/*---------------------- TESTES CADASTRAR LOCAL DE ATENDIMENTO ----------------------*/
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastrarLocalDeAtendimentoEmailVazio() {

		tutorController.cadastrarLocalDeAtendimento("", "LCC2");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastrarLocalDeAtendimentoEmailComEspacosEmBranco() {
		
		tutorController.cadastrarLocalDeAtendimento("     ", "LCC2");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastrarLocalDeAtendimentoEmailNulo() {
		
		tutorController.cadastrarLocalDeAtendimento(null, "LCC2");
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testCadastrarLocalDeAtendimentoEmailInexistente() {
		
		tutorController.cadastrarLocalDeAtendimento("gaugaugauds@bol.com", "LCC2");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastrarLocalDeAtendimentoLocalVazio() {
		
		tutorController.cadastrarLocalDeAtendimento("gaudslindo99@gmail.com", "");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastrarLocalDeAtendimentoLocalComEspacosEmBranco() {
		
		tutorController.cadastrarLocalDeAtendimento("gaudslindo99@gmail.com", "    ");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCadastrarLocalDeAtendimentoLocalNulo() {
		
		tutorController.cadastrarLocalDeAtendimento("gaudslindo99@gmail.com", null);
	}
	
	@Test
	public void testCadastrarLocalDeAtendimentoValido() {
		
		tutorController.cadastrarLocalDeAtendimento("gaudslindo99@gmail.com", "LCC2");
	}
	
/*---------------------- TESTES CONSULTA HORARIO ----------------------*/
	
	@Test(expected=IllegalArgumentException.class)
	public void testConsultaHorarioEmailVazio() {

		tutorController.consultaHorario("", "13:00", "Segunda");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConsultaHorarioEmailComEspacosEmBranco() {
		
		tutorController.consultaHorario("   ", "13:00", "Segunda");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConsultaHorarioEmailNulo() {
		
		tutorController.consultaHorario(null, "13:00", "Segunda");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConsultaHorarioHoraVazia() {
		
		tutorController.consultaHorario("gaudslindo99@gmail.com", "", "Segunda");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConsultaHorarioHoraComEspacosEmBranco() {
		
		tutorController.consultaHorario("gaudslindo99@gmail.com", "   ", "Segunda");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConsultaHorarioHoraNula() {
		
		tutorController.consultaHorario("gaudslindo99@gmail.com", null, "Segunda");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConsultaHorarioDiaVazio() {
		
		tutorController.consultaHorario("gaudslindo99@gmail.com", "13:00", "");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConsultaHorarioDiaNulo() {
		
		tutorController.consultaHorario("gaudslindo99@gmail.com", "15:00", null);
	}
	
	@Test
	public void testConsultaHorarioValido() {
		
		tutorController.consultaHorario("gaudslindo99@gmail.com", "15:00", "Quarta");
	}
	
	/*---------------------- TESTES CONSULTA LOCAL DE ATENDIMENTO ----------------------*/
	
	@Test(expected=IllegalArgumentException.class)
	public void testConsultaLocalDeAtendimentoEmailVazio() {

		tutorController.consultaLocal("", "LCC2");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConsultaLocalDeAtendimentoEmailComEspacosEmBranco() {
		
		tutorController.consultaLocal("     ", "LCC2");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConsultaLocalDeAtendimentoEmailNulo() {
		
		tutorController.consultaLocal(null, "LCC2");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConsultaLocalDeAtendimentoLocalVazio() {
		
		tutorController.consultaLocal("gaudslindo99@gmail.com", "");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConsultaLocalDeAtendimentoLocalComEspacosEmBranco() {
		
		tutorController.consultaLocal("gaudslindo99@gmail.com", "    ");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConsultaLocalDeAtendimentoLocalNulo() {
		
		tutorController.consultaLocal("gaudslindo99@gmail.com", null);
	}
	
	@Test
	public void testConsultaLocalDeAtendimentoValido() {
		
		tutorController.consultaLocal("gaudslindo99@gmail.com", "LCC2");
	}
	
	/*---------------------- TESTES RECUPERA TUTOR ----------------------*/
	
	@Test(expected=IllegalArgumentException.class)
	public void testRecuperaTutorEmailVazio() {

		tutorController.recuperaTutor("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRecuperaTutorEmailComEspacosEmBranco() {
		
		tutorController.recuperaTutor("     ");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRecuperaTutorEmailNulo() {
		
		tutorController.recuperaTutor(null);
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testRecuperaTutorEmailInexistente() {
		
		tutorController.recuperaTutor("lerigou@frozen.com");
	}
	
	@Test
	public void testRecuperaTutorValido() {
		
		String actual = tutorController.recuperaTutor("gaudslindo99@gmail.com");
		String expected = "11715963 - Gauds Lindo - 2 - 99984-1347 - gaudslindo99@gmail.com";
		
		assertEquals("Esperando resposta igual", expected, actual);
	}
	
	/*---------------------- TESTES RETORNA NOTA AVALIACAO ----------------------*/
	
	@Test(expected=IllegalArgumentException.class)
	public void testRetornaNotaAvaliacaoEmailVazio() {

		tutorController.retornaNotaAvaliacao("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRetornaNotaAvaliacaoEmailComEspacosEmBranco() {
		
		tutorController.retornaNotaAvaliacao("     ");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRetornaNotaAvaliacaoEmailNulo() {
		
		tutorController.retornaNotaAvaliacao(null);
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testRetornaNotaAvaliacaoEmailInexistente() {
		
		tutorController.retornaNotaAvaliacao("lerigou@frozen.com");
	}
	
	@Test
	public void testRetornaNotaAvaliacaoValido() {
		
		String actual = tutorController.retornaNotaAvaliacao("gaudslindo99@gmail.com");
		String expected = "4,00";
		
		assertEquals("Esperando resposta igual", expected, actual);
	}
	
	/*---------------------- TESTES RETORNA NIVEL ----------------------*/
	
	@Test(expected=IllegalArgumentException.class)
	public void testRetornaNivelEmailVazio() {

		tutorController.retornaNivel("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRetornaNivelEmailComEspacosEmBranco() {
		
		tutorController.retornaNivel("      ");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRetornaNivelEmailNulo() {
		
		tutorController.retornaNivel(null);
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testRetornaNivelEmailInexistente() {
		
		tutorController.retornaNivel("lerigou@frozen.com");
	}
	
	@Test
	public void testRetornaNivelTutorValido() {
		
		String actual = tutorController.retornaNivel("gaudslindo99@gmail.com");
		String expected = "Tutor";
		
		assertEquals("Esperando resposta igual", expected, actual);
	}
	
	@Test
	public void testRetornaNivelTopValido() {
		
		for(int i = 0; i < 5; i ++) {	
			tutorController.avaliaTutor("gaudslindo99@gmail.com", 5);
		}

		String actual = tutorController.retornaNivel("gaudslindo99@gmail.com");
		String expected = "TOP";
		
		assertEquals("Esperando resposta igual", expected, actual);
	}
	
	@Test
	public void testRetornaNivelAprendizValido() {
		
		for(int i = 0; i < 5; i ++) {	
			tutorController.avaliaTutor("gaudslindo99@gmail.com", 1);
		}

		String actual = tutorController.retornaNivel("gaudslindo99@gmail.com");
		String expected = "Aprendiz";
		
		assertEquals("Esperando resposta igual", expected, actual);
	}
	
	/*---------------------- TESTES AVALIA TUTOR ----------------------*/
	
	@Test(expected=IllegalArgumentException.class)
	public void testAvaliaTutorEmailVazio() {

		tutorController.avaliaTutor("", 2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAvaliaTutorEmailComEspacosEmBranco() {
		
		tutorController.avaliaTutor("      ", 3);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAvaliaTutorEmailNulo() {
		
		tutorController.avaliaTutor(null, 2);
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testAvaliaTutorEmailInexistente() {
		
		tutorController.avaliaTutor("lerigou@frozen.com", 2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAvaliaTutorNotaMenorQueZero() {

		tutorController.avaliaTutor("gaudslindo99@gmail.com", -1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAvaliaTutorNotaMaiorQueCinco() {
		
		tutorController.avaliaTutor("gaudslindo99@gmail.com", 6);
	}
	
	@Test
	public void testAvaliaTutorValido() {
		
		tutorController.avaliaTutor("gaudslindo99@gmail.com", 3);
	}
	
	/*---------------------- TESTES DOAR ----------------------*/
	
	@Test(expected=IllegalArgumentException.class)
	public void testDoarEmailVazio() {

		tutorController.doar("", 100);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDoarEmailComEspacosEmBranco() {
		
		tutorController.doar("      ", 100);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDoarEmailNulo() {
		
		tutorController.doar(null, 100);
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testDoarEmailInexistente() {
		
		tutorController.doar("lerigou@frozen.br", 100);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDoarValorMenorQueZero() {

		tutorController.doar("gaudslindo99@gmail.com", -1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDoarValorIgualAZero() {
		
		tutorController.doar("gaudslindo99@gmail.com", 0);
	}
	
	@Test
	public void testDoarValido() {
		
		tutorController.doar("gaudslindo99@gmail.com", 1000);
	}
	
	/*---------------------- TESTES TOTAL DINHEIRO ----------------------*/
	
	@Test(expected=IllegalArgumentException.class)
	public void testTotalDinheiroTutorEmailVazio() {

		tutorController.totalDinheiroTutor("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testTotalDinheiroTutorEmailComEspacosEmBranco() {
		
		tutorController.totalDinheiroTutor("      ");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testTotalDinheiroTutorEmailNulo() {
		
		tutorController.totalDinheiroTutor(null);
	}
	
	@Test(expected=NoSuchElementException.class)
	public void testTotalDinheiroTutorEmailInexistente() {
		
		tutorController.totalDinheiroTutor("lerigou@frozen.br");
	}
	
	@Test
	public void testTotalDinheiroTutorValido() {
		
		tutorController.doar("gaudslindo99@gmail.com", 10000);
		
		int actual = tutorController.totalDinheiroTutor("gaudslindo99@gmail.com");
		int expected = 10000;
		
		assertEquals(expected, actual);
		
	}
	
}
