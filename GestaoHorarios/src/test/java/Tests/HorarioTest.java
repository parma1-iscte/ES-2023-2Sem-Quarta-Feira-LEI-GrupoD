package Tests;

import Horario.*;
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
 * Versão 1.0
 */

public class HorarioTest {

	
	//Testes para  Leitura JSON local
	@Test
	public void testGetHorarioFromJsonLocalValidFile() throws IOException, URISyntaxException {
		// Arrange
		File file = new File("valid_file.json");

		// Act
		Horario horario = Horario.getHorarioFromCsvLocalOrRemote("valid_file.json");

		// Assert
		assertEquals(2, horario.getHorario().size());
	}

	@Test
	public void testGetHorarioFromJsonLocalNonexistentFile() throws IOException {
		// Arrange
		File file = new File("nonexistent_file.json");

		// Act & Assert
		assertThrows(IOException.class, () -> Horario.getHorarioFromCsvLocalOrRemote("nonexistent_file.json"));
	}

	@Test
	public void testGetHorarioFromJsonLocalInvalidFile() throws IOException {
		// Arrange
		File file = new File("invalid_file.json");

		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> Horario.getHorarioFromCsvLocalOrRemote("invalid_file.json"));
	}
	
	//Testes para Leitura JSON Remoto
	@Test
	public void testGetHorarioFromJsonRemotoValidURL() throws IOException, URISyntaxException {
		// Arrange
		String url = "http://example.com/valid_file.json";

		// Act
		Horario horario = Horario.getHorarioFromCsvLocalOrRemote(url);

		// Assert
		assertEquals(2, horario.getHorario().size());
	}

	@Test
	public void testGetHorarioFromJsonRemotoNonexistentURL() throws IOException, URISyntaxException {
		// Arrange
		String url = "http://example.com/nonexistent_file.json";

		// Act & Assert
		assertThrows(IOException.class, () -> Horario.getHorarioFromCsvLocalOrRemote(url));
	}
	
	@Test
	public void testGetHorarioFromJsonRemotoInvalidURL() throws IOException, URISyntaxException {
		// Arrange
		String url = "http://example.com/invalid_file.json";

		// Act & Assert
		assertThrows(IllegalArgumentException.class, () -> Horario.getHorarioFromCsvLocalOrRemote(url));
	}
	
	//Testes para Leitura CSV Local
	@Test
	public void testGetHorarioFromCsvLocal() throws IOException, URISyntaxException {
	    // Cria um arquivo temporário para teste
	    File file = new File("csv_teste.csv");
	    
	    // Chama o método para obter um objeto Horario
	    Horario horario = Horario.getHorarioFromCsvLocalOrRemote("csv_teste.csv");
	    
	    // Verifica se o objeto Horario foi criado corretamente
	    assertNotNull(horario);
	    
	    // Verifica se a lista de Aula no Horario não é nula
	    assertNotNull(horario.getHorario());
	    
	    // Verifica se a lista de Aula no Horario não está vazia
	    assertFalse(horario.getHorario().isEmpty());
	}

	
	public void testGetHorarioFromCsvLocalFileNotFound() throws IOException {
	    // Cria um arquivo fictício que não existe
	    File file = new File("arquivo_nao_existente.csv");
	    
	    
	    // Chama o método, espera-se que uma exceção FileNotFoundException seja lançada
	   assertThrows(FileNotFoundException.class, () -> Horario.getHorarioFromCsvLocalOrRemote("arquivo_nao_existente.csv"));
	}
	
	public void testGetHorarioFromCsvLocalInvalid() throws IOException{
		// Cria um arquivo com estrutura inválida
	    File file = new File("file_csv_invalido.csv");
	    
	    // Chama o método, espera-se que uma exceção FileNotFoundException seja lançada
	   assertThrows(IllegalArgumentException.class, () -> Horario.getHorarioFromCsvLocalOrRemote("file_csv_invalido.csv"));
	}

	
	//Testes para Leitura CSV Remoto
	@Test
	public void testGetHorarioFromCsvRemoto() throws MalformedURLException, IOException, URISyntaxException {
		// URL de exemplo para o arquivo CSV remoto
		String url = "https://github.com/parma1-iscte/ES-2023-2Sem-Quarta-Feira-LEI-GrupoD/raw/Sprint1/horario_exemplo1.csv";

		// Chama o método para obter um objeto Horario
		Horario horario = Horario.getHorarioFromCsvLocalOrRemote(url);

		// Verifica se o objeto Horario foi criado corretamente
		assertNotNull(horario);

		// Verifica se a lista de Aula no Horario não é nula
		assertNotNull(horario.getHorario());

		// Verifica se a lista de Aula no Horario não está vazia
		assertFalse(horario.getHorario().isEmpty());
	}

	@Test
	public void testGetHorarioFromCsvRemotoMalformedUrl() throws IOException, URISyntaxException {
	    // URL inválida
	    String url = "htps:/example.com/horario.csv";
	    
	    // Chama o método, espera-se que uma exceção MalformedURLException seja lançada
	    assertThrows(MalformedURLException.class, () -> Horario.getHorarioFromCsvLocalOrRemote(url));
	}
	
	@Test
	public void testGetHorarioFromCsvRemotoInvalid() throws IOException, URISyntaxException {
	    // URL válida para um arquivo CSV remoto
	    String url = "https://example.com/horario.csv";
	    
	    // Chama o método, espera-se que uma exceção IOException seja lançada
	    assertThrows(IllegalArgumentException.class, () -> Horario.getHorarioFromCsvLocalOrRemote(url));
	}
	
	//Testes para Escrita CSV Local
	@Test
	public void testSaveToCsvLocal() throws IOException, URISyntaxException {
	    // Cria um objeto Horario com algumas aulas
	    List<Aula> list = new ArrayList<>();
	    Aula a1 = new Aula("Curso1", "UC1", "Turno1", "Turma1", 30, "Segunda-feira", LocalTime.parse("08:00:00"),
	            LocalTime.parse("10:00:00"), LocalDate.parse("2023-04-16"), "Sala1", 50);
	    Aula a2 = new Aula("Curso2", "UC2", "Turno2", "Turma2", 25, "Terça-feira", LocalTime.parse("14:00:00"),
	            LocalTime.parse("16:00:00"), LocalDate.parse("2023-04-17"), "Sala2", 40);
	    list.add(a1);list.add(a2);
	    Horario horario = new Horario(list);
	    // Cria um arquivo temporário para salvar o CSV
	    File file = File.createTempFile("horario", ".csv");

	    // Chama o método para salvar o horário no arquivo CSV
	    horario.saveToCsvLocalOrRemote("horario.csv");

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
	    assertThrows(IOException.class, () -> horario.saveToCsvLocalOrRemote(path));
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
	    String url = "https://example.com/horario.csv";

	    // Chama o método para salvar o horário no arquivo CSV remoto
	    horario.saveToCsvLocalOrRemote(url);

	    // Verifica se o arquivo foi salvo corretamente remotamente
	    assertEquals(horario, Horario.getHorarioFromCsvLocalOrRemote(url));
	}

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
	    String url = "https://example.com/invalid/horario.csv";

	    // Chama o método, espera-se que uma exceção Exception seja lançada
	    assertThrows(IOException.class, () -> horario.saveToCsvLocalOrRemote(url));
	}

	
	//Testes para Escrita JSON Local
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
        horario.saveToJsonLocal("json_teste.json");
        
        // Verifica se o arquivo foi criado
        assertTrue(file.exists());
        
        // Verifica se o arquivo não está vazio
        assertTrue(file.length() > 0);
        
        // Verifica o conteúdo do arquivo
        // Aqui você pode implementar verificações específicas do formato JSON,
        // como verificar se os campos e valores estão corretos, usando uma
        // biblioteca de comparação de JSON, como o JSONAssert ou similar.
    }
	
	//Testes para Escrita JSON Remoto
	@Test
    public void testSaveToJsonRemoto() throws Exception {
		List<Aula> list = new ArrayList<>();
	    Aula a1 = new Aula("Curso1", "UC1", "Turno1", "Turma1", 30, "Segunda-feira", LocalTime.parse("08:00:00"),
	            LocalTime.parse("10:00:00"), LocalDate.parse("2023-04-16"), "Sala1", 50);
	    Aula a2 = new Aula("Curso2", "UC2", "Turno2", "Turma2", 25, "Terça-feira", LocalTime.parse("14:00:00"),
	            LocalTime.parse("16:00:00"), LocalDate.parse("2023-04-17"), "Sala2", 40);
	    list.add(a1);list.add(a2);
	    Horario horario = new Horario(list);
	    String url = "https://github.com/parma1-iscte/ES-2023-2Sem-Quarta-Feira-LEI-GrupoD/blob/main/json_exemplo.json";
        // Executa o método de teste
        horario.saveToJsonRemote(url);
        
        // Verifica se o arquivo foi salvo remotamente corretamente
        // Aqui você pode implementar verificações específicas, como verificar
        // se a URL de destino está correta, se o arquivo foi criado no servidor,
        // se o conteúdo do arquivo é válido em formato JSON, etc.
        
        
        // Verifica se o arquivo foi salvo corretamente remotamente
	    assertEquals(horario, Horario.getHorarioFromCsvLocalOrRemote(url));
    }






}