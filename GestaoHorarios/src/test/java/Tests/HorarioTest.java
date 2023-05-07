package GestaoHorarios.src.test.java.Tests;

import GestaoHorarios.src.main.java.Horario.Aula;
import GestaoHorarios.src.main.java.Horario.Horario;
//import Horario.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;



/**
 * 
 * @author ES-2023-2Sem-Quarta-Feira-LEI-GrupoD
 * Versão 2.0
 */

public class HorarioTest {


	public final String USER = "";
	public final String PASSWORD = ""; 

	//Testes para  Leitura JSON local
	/**
    @throws Exception se ocorrer um erro ao executar o teste
    */
	@Test
	public void testGetHorarioFromJsonLocalValidFile() throws Exception {
		// Arrange
		File file = new File("json_teste.json");

		// Act
		Horario horario = Horario.getHorarioFromJsonLocal(file);

		// Assert
		assertEquals(2, horario.getHorario().size());
	}

	/**
    Testa o método {@link Horario#getHorarioFromJsonLocal(File)} para o caso em que o arquivo
    de origem não existe.
    @throws IOException se ocorrer um erro de I/O durante o teste.
    */

	@Test
	public void testGetHorarioFromJsonLocalNonexistentFile() throws IOException {
		// Arrange
		File f = new File("nonexistent_file.json");
		// Act & Assert
		assertThrows(FileNotFoundException.class, () -> Horario.getHorarioFromCsvLocal(f));
	}

	//Testes para Leitura JSON Remoto
	/**
	Testa o método getHorarioFromJsonRemoto() da classe Horario usando uma URL válida para um arquivo JSON remoto.
	O teste verifica se o objeto Horario é corretamente criado a partir do conteúdo JSON obtido da URL fornecida.
	@throws Exception se ocorrer um erro ao obter o conteúdo JSON da URL ou ao criar o objeto Horario
	 */

	@Test
	public void testGetHorarioFromJsonRemotoValidURL() throws Exception {
		// Arrange
		String url = "https://raw.githubusercontent.com/parma1-iscte/ES-2023-2Sem-Quarta-Feira-LEI-GrupoD/main/json_exemplo.json";
		// Act
		Horario horario = Horario.getHorarioFromJsonRemote(url,null,null);

		// Assert
		assertEquals(2, horario.getHorario().size());
	}

	/**
    Testa o método getHorarioFromJsonRemote() da classe Horario, passando uma URL inexistente como argumento.
    Espera-se que uma exceção IOException seja lançada.
    @throws IOException se ocorrer um erro de I/O ao tentar abrir a URL
    @throws URISyntaxException se ocorrer um erro na sintaxe da URL
    */
	
	@Test
	public void testGetHorarioFromJsonRemotoNonexistentURL() throws IOException, URISyntaxException {
		// Arrange
		String url = "http://example.com/nonexistent_file.json";

		// Act & Assert
		assertThrows(IOException.class, () -> Horario.getHorarioFromJsonRemote(url,null,null));
	}



	//Testes para Leitura CSV Local
	
	/**
	Testa o método getHorarioFromCsvLocal da classe Horario, que obtém um objeto Horario a partir de um arquivo CSV local.
 	@throws Exception se ocorrer algum erro durante a execução do teste.
    */
	
	@Test
	public void testGetHorarioFromCsvLocal() throws Exception {
		// Cria um arquivo temporário para teste
		String path = "csv_teste.csv";

		File file = new File("C:\\Users\\pamen\\ES-2023-2Sem-Quarta-Feira-LEI-GrupoD-9\\GestaoHorarios\\Conjunto de teste\\Correto.csv");
		// Chama o método para obter um objeto Horario
		Horario horario = Horario.getHorarioFromCsvLocal(file);

		// Verifica se o objeto Horario foi criado corretamente
		assertNotNull(horario);

		// Verifica se a lista de Aula no Horario não é nula
		assertNotNull(horario.getHorario());

		// Verifica se a lista de Aula no Horario não está vazia
		assertFalse(horario.getHorario().isEmpty());
	}

	/**
    Testa o método getHorarioFromCsvLocal da classe Horario quando o arquivo CSV não é encontrado.
    Espera-se que uma exceção FileNotFoundException seja lançada.
	@throws IOException se ocorrer algum erro durante a execução do teste.
    */

	public void testGetHorarioFromCsvLocalFileNotFound() throws IOException {
		// Cria um arquivo fictício que não existe
		File f = new File("arquivo_nao_existente.csv");

		// Chama o método, espera-se que uma exceção FileNotFoundException seja lançada
		assertThrows(FileNotFoundException.class, () -> Horario.getHorarioFromCsvLocal(f));
	}



	//Testes para Leitura CSV Remoto
	/**
    Testa o método getHorarioFromCsvRemoto da classe Horario, que obtém um objeto Horario a partir de um arquivo CSV remoto.
    @throws Exception se ocorrer algum erro durante a execução do teste.
    */

	@Test
	public void testGetHorarioFromCsvRemoto() throws Exception {
		// URL de exemplo para o arquivo CSV remoto
		String url = "https://raw.githubusercontent.com/parma1-iscte/ES-2023-2Sem-Quarta-Feira-LEI-GrupoD/main/GestaoHorarios/Conjunto%20de%20teste/Correto.csv";

		// Chama o método para obter um objeto Horario
		Horario horario = Horario.getHorarioFromCsvRemoto(url,null,null);

		// Verifica se o objeto Horario foi criado corretamente
		assertNotNull(horario);

		// Verifica se a lista de Aula no Horario não é nula
		assertNotNull(horario.getHorario());

		// Verifica se a lista de Aula no Horario não está vazia
		assertFalse(horario.getHorario().isEmpty());
	}

	/**
    Testa o método getHorarioFromCsvRemoto da classe Horario quando a URL para o arquivo CSV remoto é inválida.
    Espera-se que uma exceção MalformedURLException seja lançada.
    @throws IOException se ocorrer algum erro durante a execução do teste.
    @throws URISyntaxException se ocorrer algum erro durante a execução do teste.
    */
	
	@Test
	public void testGetHorarioFromCsvRemotoMalformedUrl() throws IOException, URISyntaxException {
		// URL inválida
		String url = "htps:/example.com/horario.csv";

		// Chama o método, espera-se que uma exceção MalformedURLException seja lançada
		assertThrows(MalformedURLException.class, () -> Horario.getHorarioFromCsvRemoto(url,null,null));
	}


	//Testes para Escrita CSV Local
	/**
    Testa o método saveToCsvLocal() da classe Horario, que salva o horário em um arquivo CSV localmente.
    Cria um objeto Horario com algumas aulas, cria um arquivo temporário para salvar o CSV e chama o método para salvar
    o horário no arquivo. Em seguida, verifica se o arquivo foi criado corretamente.
    @throws Exception se ocorrer algum erro durante o teste
    */
	
	@Test
	public void testSaveToCsvLocal() throws Exception {
		// Cria um objeto Horario com algumas aulas
		List<Aula> list = new ArrayList<>();
		Aula a1 = new Aula("Curso1", "UC1", "Turno1", "Turma1", 30, "Segunda-feira", LocalTime.parse("08:00:00"),
				LocalTime.parse("10:00:00"), LocalDate.parse("2023-04-16"), "Sala1", 50);
		Aula a2 = new Aula("Curso2", "UC2", "Turno2", "Turma2", 25, "Terça-feira", LocalTime.parse("14:00:00"),
				LocalTime.parse("16:00:00"), LocalDate.parse("2023-04-17"), "Sala2", 40);
		list.add(a1);list.add(a2);
		Horario horario = new Horario(list);
		// Cria um arquivo temporário para salvar o CSV
		File file = new File("csv_teste.csv");

		// Chama o método para salvar o horário no arquivo CSV
		horario.saveToCsvLocal(file);

		// Verifica se o arquivo foi criado corretamente
		assertTrue(file.exists());
		assertTrue(file.length() > 0);
	}

	@Test
	public void testSaveToCsvLocalIOException() throws IOException {
		// Cria um objeto Horario com algumas aulas
		List<Aula> list = new ArrayList<>();
		Aula a1 = new Aula("Curso1", "UC1", "Turno1", "Turma1", 30, "Segunda-feira", LocalTime.parse("08:00:00"),
				LocalTime.parse("10:00:00"), LocalDate.parse("2023-04-16"), "Sala1", 50);
		Aula a2 = new Aula("Curso2", "UC2", "Turno2", "Turma2", 25, "Terça-feira", LocalTime.parse("14:00:00"),
				LocalTime.parse("16:00:00"), LocalDate.parse("2023-04-17"), "Sala2", 40);
		list.add(a1);list.add(a2);
		Horario horario = new Horario(list);

		// Cria um diretório inválido como arquivo para forçar um erro de I/O
		String path = "/path/to/invalid/directory/horario.csv";

		// Chama o método, espera-se que uma exceção IOException seja lançada
		assertThrows(IOException.class, () -> horario.saveToCsvLocal(new File(path)));
	}

	//Testes para Escrita CSV Remoto
	@Test
	public void testSaveToCsvRemoto() throws Exception {
		// Cria um objeto Horario com algumas aulas
		List<Aula> list = new ArrayList<>();
		Aula a1 = new Aula("Curso1", "UC1", "Turno1", "Turma1", 30, "Segunda-feira", LocalTime.parse("08:00:00"),
				LocalTime.parse("10:00:00"), LocalDate.parse("2023-04-16"), "Sala1", 50);
		Aula a2 = new Aula("Curso2", "UC2", "Turno2", "Turma2", 25, "Terça-feira", LocalTime.parse("14:00:00"),
				LocalTime.parse("16:00:00"), LocalDate.parse("2023-04-17"), "Sala2", 40);
		list.add(a1);list.add(a2);
		Horario horario = new Horario(list);

		// Cria uma URL válida para salvar o arquivo CSV remotamente
		String url = "https://raw.githubusercontent.com/parma1-iscte/ES-2023-2Sem-Quarta-Feira-LEI-GrupoD/main/GestaoHorarios/Conjunto%20de%20teste/Correto.csv";

		// Chama o método para salvar o horário no arquivo CSV remoto
		horario.saveToCsvRemoto(url,"","");

		// Verifica se o arquivo foi salvo corretamente remotamente
		assertEquals(horario, Horario.getHorarioFromCsvRemoto(url,USER,PASSWORD));
	}

	
	/**
 	Testa o método saveToCsvRemoto() com uma URL inválida. Espera-se que uma exceção MalformedURLException seja lançada.
    @throws Exception em caso de erro no teste
    */
	
	@Test
	public void testSaveToCsvRemotoException() throws Exception {
		// Cria um objeto Horario com algumas aulas
		List<Aula> list = new ArrayList<>();
		Aula a1 = new Aula("Curso1", "UC1", "Turno1", "Turma1", 30, "Segunda-feira", LocalTime.parse("08:00:00"),
				LocalTime.parse("10:00:00"), LocalDate.parse("2023-04-16"), "Sala1", 50);
		Aula a2 = new Aula("Curso2", "UC2", "Turno2", "Turma2", 25, "Terça-feira", LocalTime.parse("14:00:00"),
				LocalTime.parse("16:00:00"), LocalDate.parse("2023-04-17"), "Sala2", 40);
		list.add(a1);list.add(a2);
		Horario horario = new Horario(list);

		// Cria uma URL inválida para forçar um erro ao salvar o arquivo remotamente
		String url = "hafssdgsgd";

		// Chama o método, espera-se que uma exceção Exception seja lançada
		assertThrows(MalformedURLException.class, () -> horario.saveToCsvRemoto(url,"",""));
	}


	//Testes para Escrita JSON Local
	/**
    Testa o método saveToJsonLocal da classe Horario.
    Cria um objeto Horario com duas Aulas, salva em um arquivo JSON local temporário
    e verifica se o arquivo foi criado e não está vazio.
    @throws Exception se ocorrer um erro durante a execução do teste.
    */
	
	@Test
	public void testSaveToJsonLocal() throws Exception {
		List<Aula> list = new ArrayList<>();
		Aula a1 = new Aula("Curso1", "UC1", "Turno1", "Turma1", 30, "Segunda-feira", LocalTime.parse("08:00:00"),
				LocalTime.parse("10:00:00"), LocalDate.parse("2023-04-16"), "Sala1", 50);
		Aula a2 = new Aula("Curso2", "UC2", "Turno2", "Turma2", 25, "Terça-feira", LocalTime.parse("14:00:00"),
				LocalTime.parse("16:00:00"), LocalDate.parse("2023-04-17"), "Sala2", 40);
		list.add(a1);list.add(a2);
		Horario horario = new Horario(list);
		File file = new File("json_teste.json");
		// Executa o método de teste
		horario.saveToJsonLocal(file);

		// Verifica se o arquivo foi criado
		assertTrue(file.exists());

		// Verifica se o arquivo não está vazio
		assertTrue(file.length() > 0);

		// Verifica o conteúdo do arquivo
		// Aqui você pode implementar verificações específicas do formato JSON,
		// como verificar se os campos e valores estão corretos, usando uma
		// biblioteca de comparação de JSON, como o JSONAssert ou similar.
	}

	/**
	Testa o método getHorarioFromJsonRemoto() da classe Horario usando uma URL válida para um arquivo JSON remoto.
	O teste verifica se o objeto Horario é corretamente criado a partir do conteúdo JSON obtido da URL fornecida.
	@throws Exception se ocorrer um erro ao obter o conteúdo JSON da URL ou ao criar o objeto Horario
    */
	
	@Test
	public void testSaveToJsonLocalInvalido() throws Exception {
		List<Aula> list = new ArrayList<>();
		Aula a1 = new Aula("Curso1", "UC1", "Turno1", "Turma1", 30, "Segunda-feira", LocalTime.parse("08:00:00"),
				LocalTime.parse("10:00:00"), LocalDate.parse("2023-04-16"), "Sala1", 50);
		Aula a2 = new Aula("Curso2", "UC2", "Turno2", "Turma2", 25, "Terça-feira", LocalTime.parse("14:00:00"),
				LocalTime.parse("16:00:00"), LocalDate.parse("2023-04-17"), "Sala2", 40);
		list.add(a1);list.add(a2);
		Horario horario = new Horario(list);
		String path = "/path/to/invalid/directory/horario.json";

		assertThrows(FileNotFoundException.class, () -> horario.saveToJsonLocal(new File(path)));

	}

	//Testes para Escrita JSON Remoto
	/**
    Testa o método saveToJsonRemoto da classe Horario, que salva um objeto Horario em formato JSON em um arquivo remoto.
    @throws Exception se ocorrer algum erro durante a execução do teste.
    */
	
	@Test
	public void testSaveToJsonRemoto() throws Exception {
		List<Aula> list = new ArrayList<>();
		Aula a1 = new Aula("Curso1", "UC1", "Turno1", "Turma1", 30, "Segunda-feira", LocalTime.parse("08:00:00"),
				LocalTime.parse("10:00:00"), LocalDate.parse("2023-04-16"), "Sala1", 50);
		Aula a2 = new Aula("Curso2", "UC2", "Turno2", "Turma2", 25, "Terça-feira", LocalTime.parse("14:00:00"),
				LocalTime.parse("16:00:00"), LocalDate.parse("2023-04-17"), "Sala2", 40);
		list.add(a1);list.add(a2);
		Horario horario = new Horario(list);
		String url = "https://raw.githubusercontent.com/parma1-iscte/ES-2023-2Sem-Quarta-Feira-LEI-GrupoD/main/json_exemplo.json";
		// Executa o método de teste
		horario.saveToJsonRemoto(url,USER,PASSWORD);

		// Verifica se o arquivo foi salvo corretamente remotamente
		assertEquals(horario, Horario.getHorarioFromJsonRemote(url,null,null));
	}
}

