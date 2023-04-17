package Tests;

import Horario.*;
import  org.junit.jupiter.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


/**
 * 
 * @author ES-2023-2Sem-Quarta-Feira-LEI-GrupoD
 * Versão 1.0
 */

public class HorarioTest {


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



	@Test
	public void testGetHorarioFromJsonLocalValidFile() throws IOException {
		// Arrange
		File file = new File("valid_file.json");

		// Act
		Horario horario = Horario.getHorarioFromJsonLocal(file);

		// Assert
		assertEquals(2, horario.getHorario().size());
	}

	@Test
	public void testGetHorarioFromJsonLocalNonexistentFile() throws IOException {
		// Arrange
		File file = new File("nonexistent_file.json");

		// Act & Assert
		assertThrows(IOException.class, () -> Horario.getHorarioFromJsonLocal(file));
	}

	@Test
	public void testGetHorarioFromJsonLocalInvalidFile() throws IOException {
		// Arrange
		File file = new File("invalid_file.json");

		// Act & Assert
		assertThrows(JsonSyntaxException.class, () -> Horario.getHorarioFromJsonLocal(file));
	}

	@Test
	public void testGetHorarioFromJsonRemotoValidURL() throws IOException, URISyntaxException {
		// Arrange
		String url = "http://example.com/valid_file.json";

		// Act
		Horario horario = Horario.getHorarioFromJsonRemoto(url);

		// Assert
		assertEquals(2, horario.getHorario().size());
	}

	@Test
	public void testGetHorarioFromJsonRemotoNonexistentURL() throws IOException, URISyntaxException {
		// Arrange
		String url = "http://example.com/nonexistent_file.json";

		// Act & Assert
		assertThrows(IOException.class, () -> Horario.getHorarioFromJsonRemoto(url));
	}

	@Test
	public void testGetHorarioFromJsonRemotoInvalidURL() throws IOException, URISyntaxException {
		// Arrange
		String url = "http://example.com/invalid_file.json";

		// Act & Assert
		assertThrows(IOException.class, () -> Horario.getHorarioFromJsonRemoto(url));
	}



	@Test
	public void testGetHorarioFromJsonLocal() throws IOException {
		// Criar um arquivo temporário com conteúdo JSON válido
		File tempFile = File.createTempFile("test", ".json");
		PrintWriter writer = new PrintWriter(tempFile);
		writer.println("[{ \"Curso\": \"Curso A\", \"Unidade Curricular\": \"UC A\", \"Turno\": \"T1\", \"Turma\": \"TA\", \"Inscritos no turno\": 20, \"Dia da semana\": \"SEG\", \"Hora início da aula\": \"08:00\", \"Hora fim da aula\": \"10:00\", \"Data da aula\": \"2023-04-17\", \"Sala atribuída à aula\": \"Sala 1\", \"Lotação da sala\": 30 }, { \"Curso\": \"Curso B\", \"Unidade Curricular\": \"UC B\", \"Turno\": \"T2\", \"Turma\": \"TB\", \"Inscritos no turno\": 15, \"Dia da semana\": \"TER\", \"Hora início da aula\": \"13:00\", \"Hora fim da aula\": \"15:00\", \"Data da aula\": \"2023-04-18\", \"Sala atribuída à aula\": \"Sala 2\", \"Lotação da sala\": 25 }]");
		writer.close();

		// Chamar a função getHorarioFromJsonLocal
		Horario horario = null;
		try {
			horario = Horario.getHorarioFromJsonLocal(tempFile);
		} catch (IOException e) {
			fail("Erro ao ler arquivo JSON local.");
		}

		// Verificar se o objeto Horario retornado pela função está correto
		assertNotNull(horario);
		List<Aula> aulas = horario.getHorario();
		assertEquals(2, aulas.size());
		assertEquals("Curso A", aulas.get(0).getCurso());
		assertEquals("UC A", aulas.get(0).getUc());
		assertEquals("T1", aulas.get(0).getTurno());
		assertEquals("TA", aulas.get(0).getTurma());
		assertEquals(20, aulas.get(0).getInscritos());
		assertEquals("SEG", aulas.get(0).getDiaSemana());
		assertEquals(LocalTime.of(8, 0), aulas.get(0).getHoraInicio());
		assertEquals(LocalTime.of(10, 0), aulas.get(0).getHoraFim());
		assertEquals(LocalDate.of(2023, 4, 17), aulas.get(0).getDia());
		assertEquals("Sala 1", aulas.get(0).getSala());
		assertEquals(30, aulas.get(0).getLotacaoSala());


	}

	@Test
	public void testSaveHorarioToJsonLocal() {
		Aula aula = new Aula("Curos","UC","Turno","Turma",20,"Sex",
				LocalTime.of(13, 0, 0),LocalTime.of(14, 30, 0),LocalDate.of(2022, 12, 2),
				"Sala",30);

		Horario h = new Horario(List.of(aula));
		try {
			h.saveToJsonLocal(new File("json_teste.json"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSaveHorarioToJsonRemote() throws IOException, URISyntaxException {
		Aula aula = new Aula("Curos","UC","Turno","Turma",20,"Sex",
				LocalTime.of(13, 0, 0),LocalTime.of(14, 30, 0),LocalDate.of(2022, 12, 2),
				"Sala",30);

		Horario h = new Horario(List.of(aula));
		h.saveToJsonRemoto("https://github.com/parma1-iscte/ES-2023-2Sem-Quarta-Feira-LEI-GrupoD/blob/Sprint1/json_exemplo.json");

	}


}