package Tests;

import Horario.*;


import  org.junit.jupiter.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


public class HorarioTest {

	private Horario horario;
	private List<Aula> listaAulas;

	@BeforeAll
	public void setUp() {
		// Cria uma lista de aulas para testar
		listaAulas = new ArrayList<>();
		Aula aula1 = new Aula("Curso1", "UC1", "Turno1", "Turma1", 30, "Segunda-feira",
				LocalTime.of(8, 0), LocalTime.of(10, 0), LocalDate.of(2023, 4, 17), "Sala1", 40);
		Aula aula2 = new Aula("Curso2", "UC2", "Turno2", "Turma2", 25, "Terça-feira",
				LocalTime.of(14, 0), LocalTime.of(16, 0), LocalDate.of(2023, 4, 18), "Sala2", 30);
		listaAulas.add(aula1);
		listaAulas.add(aula2);

		// Cria uma instância de Horario com a lista de aulas
		horario = new Horario(listaAulas);
	}

	@Test
	public void testGetHorario() {
		// Verifica se a lista de aulas retornada é a mesma que foi configurada no setUp()
		List<Aula> horarioLista = horario.getHorario();
		assertEquals(listaAulas, horarioLista);
	}
	
	@Test
	public void testGetHorarioFromCsvRemoto() throws MalformedURLException, IOException, URISyntaxException {
	    // URL de exemplo para o arquivo CSV remoto
	    String url = "https://github.com/parma1-iscte/ES-2023-2Sem-Quarta-Feira-LEI-GrupoD/raw/Sprint1/horario_exemplo1.csv";
	    
	    // Chama o método para obter um objeto Horario
	    Horario horario = Horario.getHorarioFromCsvRemoto(url);
	    
	    // Verifica se o objeto Horario foi criado corretamente
	    assertNotNull(horario);
	    
	    // Verifica se a lista de Aula no Horario não é nula
	    assertNotNull(horario.getHorario());
	    
	    // Verifica se a lista de Aula no Horario não está vazia
	    assertFalse(horario.getHorario().isEmpty());
	}

	
	@Test
	public void testReadFileCsvWithBufferedReader() throws IOException {
	    // Cria um StringReader simulando um arquivo CSV em memória
	    StringReader reader = new StringReader("Curso;Unidade Curricular;Turno;Turma;Inscritos no turno;Dia da semana;Hora início da aula;Hora fim da aula;Data da aula;Sala atribuída à aula;Lotação da sala\r\n"
	    		+ "ME;Teoria dos Jogos e dos Contratos;01789TP01;MEA1;30;Sex;13:00:00;14:30:00;02/12/2022;AA2.25;34\r\n"
	    		+ "ME;Teoria dos Jogos e dos Contratos;01789TP01;MEA1;30;Qua;13:00:00;14:30:00;23/11/2022;AA2.25;34\r\n"
	    		+ "ME;Teoria dos Jogos e dos Contratos;01789TP01;MEA1;30;Qua;13:00:00;14:30:00;16/11/2022;AA2.25;34");
	    
	    // Cria um BufferedReader a partir do StringReader
	    BufferedReader in = new BufferedReader(reader);
	    
	    // Chama o método para obter a lista de Aula
	    List<Aula> lista = Horario.readFileCsvWithBufferedReader(in);
	    
	    // Verifica se a lista de Aula foi criada corretamente
	    assertNotNull(lista);
	    
	    // Verifica se a lista de Aula tem o tamanho correto
	    assertEquals(3, lista.size());
	    
	    // Verifica se os atributos de uma Aula na lista foram lidos corretamente
	    Aula aula1 = lista.get(0);
	    assertEquals("ME", aula1.getCurso());
	    assertEquals("Teoria dos Jogos e dos Contratos", aula1.getUc());
	    assertEquals("01789TP01", aula1.getTurno());
	    assertEquals("MEA1", aula1.getTurma());
	    assertEquals(Integer.valueOf(30), aula1.getInscritos());
	    assertEquals("Sex", aula1.getDiaSemana());
	    assertEquals(LocalTime.parse("13:00:00"), aula1.getHoraInicio());
	    assertEquals(LocalTime.parse("14:30:00"), aula1.getHoraFim());
	    assertEquals(LocalDate.parse("2022-12-02"), aula1.getDia());
	    assertEquals("AA2.25", aula1.getSala());
	    assertEquals(Integer.valueOf(34), aula1.getLotacaoSala());
	}


}