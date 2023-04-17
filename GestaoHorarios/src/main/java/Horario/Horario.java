package Horario;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import org.apache.commons.csv.*;
import org.apache.commons.csv.CSVFormat;
import com.google.gson.*;

import java.io.*;

/**
 * 
 * @author ES-2023-2Sem-Quarta-Feira-LEI-GrupoD
 * Versão 1.0
 */

public class Horario {


	private List<Aula> horario;

	public Horario(List<Aula> horario) {
		this.horario = horario;
	}

	public List<Aula> getHorario() {
		return horario;
	}


	//ponto 6
	/**
	 * Lê um arquivo JSON local e converte seu conteúdo em uma lista de aulas.
	 * @param file o arquivo JSON a ser lido
	 * @return uma lista de aulas contidas no arquivo JSON
	 * @throws IOException se ocorrer um erro ao ler o arquivo
	 */
	public static Horario getHorarioFromJsonLocal(File file) throws IOException {

		BufferedReader in = new BufferedReader(new FileReader(file));

		return new Horario(readFileJsonWithBufferedReader(in));
	}


	//ponto 7
	/**
	 * Lê um arquivo JSON remoto e converte seu conteúdo em uma lista de aulas.
	 * @param url a URL do arquivo JSON a ser lido
	 * @return uma lista de aulas contidas no arquivo JSON
	 * @throws IOException se ocorrer um erro ao ler o arquivo
	 * @throws URISyntaxException se a URI da URL estiver incorreta
	 */
	public static Horario getHorarioFromJsonRemoto(String url) throws IOException, URISyntaxException {
		BufferedReader in = new BufferedReader(new InputStreamReader(new URI(url).toURL().openStream()));

		return new Horario(readFileJsonWithBufferedReader(in));

	}

	// funcoes communs para pontos 6,7
	/**
	 * Converte um objeto JSON em uma instância de Aula.
	 * @param json o objeto JSON a ser convertido
	 * @return uma instância de Aula contida no objeto JSON
	 */
	private static Aula jsonToAula(JsonObject json) {
		String curso = json.get("Curso").toString();
		String uc = json.get("Unidade Curricular").toString();
		String turno = json.get("Turno").toString(); 
		String turma = json.get("Turma").toString();
		Integer inscritos = Integer.parseInt(json.get("Inscritos no turno").toString());
		String diaSemana = json.get("Dia da semana").toString();
		LocalTime horaInicio = LocalTime.parse(json.get("Hora início da aula").toString());
		LocalTime horaFim = LocalTime.parse(json.get("Hora fim da aula").toString());
		LocalDate dia = LocalDate.parse(json.get("Data da aula").toString());
		String sala = json.get("Sala atribuída à aula").toString();
		Integer lotacaoSala = Integer.parseInt(json.get("Lotação da sala").toString());

		return new Aula(curso,uc,turno,turma,inscritos,diaSemana,horaInicio,horaFim,dia,sala,lotacaoSala);
	}

	/**
	 * Lê o conteúdo de um arquivo JSON com o auxílio de um BufferedReader e o converte em uma lista de aulas.
	 * @param in um BufferedReader para ler o conteúdo do arquivo JSON
	 * @return uma lista de aulas contidas no arquivo JSON
	 * @throws IOException se ocorrer um erro ao ler o arquivo
	 */
	private static List<Aula> readFileJsonWithBufferedReader(BufferedReader in) throws IOException{
		List<Aula> list = new ArrayList<>();
		Gson gson = new Gson();

		JsonObject json;
		while((json = gson.fromJson(in.readLine(), JsonObject.class)) != null) {
			if(Validacao.validarDocumento(json))
				list.add(jsonToAula(json));
		}
		return list;
	}

	// ponto 8
	
	/**

    Cria e retorna um objeto Horario a partir de um arquivo CSV local.
	
	@param file O arquivo CSV local contendo os dados do horário.

    @return um objeto Horario criado a partir do arquivo CSV

    @throws FileNotFoundException se o arquivo no caminho fornecido não for encontrado

    @throws IOException se ocorrer um erro de I/O ao ler o arquivo
    */
	
	
	public static Horario getHorarioFromCsvLocal(File file) throws FileNotFoundException, IOException {

		BufferedReader in = new BufferedReader(new FileReader(file));

		return new Horario(readFileCsvWithBufferedReader(in));
	}

	//ponto 9
	
	/**

    Este método lê um arquivo CSV de uma URL remoto e cria um objeto Horario.

    @param url a URL do arquivo CSV remoto

    @return um objeto Horario criado a partir do arquivo CSV remoto

    @throws MalformedURLException se a URL estiver malformada

    @throws IOException se ocorrer um erro de I/O ao ler o arquivo

    @throws URISyntaxException se a sintaxe da URI estiver incorreta
    */
	
	public static Horario getHorarioFromCsvRemoto(String url) throws MalformedURLException, IOException, URISyntaxException {
		BufferedReader in = new BufferedReader(new InputStreamReader(new URI(url).toURL().openStream()));

		return new Horario(readFileCsvWithBufferedReader(in));
	}

	
	
	// funcao comum  dos pontos 8,9
	
	/**

    Este método lê um arquivo CSV com um BufferedReader e cria uma lista de objetos Aula.

    @param in o BufferedReader que aponta para o arquivo CSV

    @return uma lista de objetos Aula criados a partir do arquivo CSV

    @throws IOException se ocorrer um erro de I/O ao ler o arquivo

    @throws IllegalArgumentException se o arquivo CSV estiver mal estruturado
    */
	
	public static List<Aula> readFileCsvWithBufferedReader(BufferedReader in) throws IOException {
		List<Aula> lista = new ArrayList<>();
		CSVFormat format = CSVFormat.DEFAULT.withDelimiter(';').withHeader("Curso", "Unidade Curricular", "Turno", "Turma",
				"Inscritos no turno", "Dia da semana", "Hora início da aula", "Hora fim da aula", "Data da aula", "Sala atribuída à aula", "Lotação da sala");
		CSVParser csvParser = new CSVParser(in, format);
		Iterator<CSVRecord> iterator = csvParser.iterator();

		if(iterator.hasNext())
			if (!Validacao.validarCsvHeader(iterator.next()))
				throw new IllegalArgumentException("Ficheiro mal estruturado");


		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		while (iterator.hasNext()) {

			CSVRecord csvRecord = iterator.next();
			if(!Validacao.validarCsvLine(csvRecord))
				throw new IllegalArgumentException("Ficheiro mal estruturado");

			String curso = csvRecord.get("Curso");
			String uc = csvRecord.get("Unidade Curricular");
			String turno = csvRecord.get("Turno");
			String turma = csvRecord.get("Turma");
			Integer inscritos = Integer.parseInt(csvRecord.get("Inscritos no turno"));
			String diaSemana = csvRecord.get("Dia da semana");
			LocalTime horaInicio = LocalTime.parse(csvRecord.get("Hora início da aula"), timeFormatter);
			LocalTime horaFim = LocalTime.parse(csvRecord.get("Hora fim da aula"), timeFormatter);
			LocalDate dia = LocalDate.parse(csvRecord.get("Data da aula"), dateFormatter);
			String sala = csvRecord.get("Sala atribuída à aula");
			Integer lotacaoSala = Integer.parseInt(csvRecord.get("Lotação da sala"));

			Aula x = new Aula(curso, uc, turno, turma, inscritos, diaSemana, horaInicio, horaFim, dia, sala,
					lotacaoSala);
			lista.add(x);
		}

		return lista;
	}



	//ponto 10
	/**
	 * Salva o horário em um arquivo CSV localmente.
	 * @param file o arquivo onde o horário será salvo.
	 * @throws IOException se ocorrer um erro de I/O ao escrever no arquivo.
	 */
	public void saveToCsvLocal(File file) throws IOException {

		PrintWriter writer = new PrintWriter(file);
		CSVFormat csvFormat = CSVFormat.DEFAULT.withDelimiter(';').withHeader("Curso", "Unidade Curricular", "Turno", "Turma",
				"Inscritos no turno", "Dia da semana", "Hora início da aula", "Hora fim da aula", "Data da aula", "Sala atríbuida à aula", "Lotacão da sala");
		CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat);

		writeHorarioWithCSVPrinter(csvPrinter);

		csvPrinter.close();
		writer.close();
	}


	//ponto 11
	/**
	 * Salva os dados do horário em um arquivo CSV remoto, a partir da URL especificada.
	 * @param url a URL do arquivo CSV remoto a ser criado
	 * @throws Exception se ocorrer um erro durante a escrita do arquivo CSV remoto
	 */
	public void saveToCsvRemoto(String url) throws Exception {

		PrintWriter writer = new PrintWriter(new URI(url).toURL().openConnection().getOutputStream(),
				true, StandardCharsets.UTF_8);

		CSVFormat csvFormat = CSVFormat.EXCEL.withHeader(
				"Curso", "Unidade Curricular", "Turno", "Turma", "Inscritos no turno", 
				"Dia da semana", "Hora início da aula", "Hora fim da aula", "Data da aula",
				"Sala atríbuida à aula", "Lotação da sala");
		CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat);

		writeHorarioWithCSVPrinter(csvPrinter);

		csvPrinter.close();
		writer.close();

	}

	//funcoes communs para pontos 10,11
	/**
	 * Escreve os dados do horário em formato CSV utilizando o {@link CSVPrinter}.
	 * @param csvPrinter o {@link CSVPrinter} a ser utilizado para escrever os dados
	 * @throws IOException se ocorrer um erro de entrada/saída ao escrever os dados
	 */
	private void writeHorarioWithCSVPrinter(CSVPrinter csvPrinter) throws IOException {
		for (Aula aula: horario) {
			csvPrinter.printRecord(
					aula.getCurso(),
					aula.getUc(),
					aula.getTurno(),
					aula.getTurma(),
					aula.getInscritos(),
					aula.getDiaSemana(),
					aula.getHoraInicio().toString(),
					aula.getHoraFim().toString(),
					aula.getDia().toString(),
					aula.getSala(),
					aula.getLotacaoSala());
		}
	}

	//ponto 12
	public void saveToJsonLocal(File file) throws IOException {
		
		FileWriter writer = new FileWriter(file);
		
		Gson gson = new Gson();
		String json = gson.toJson(this);
		gson.toJson(this,writer);
		writer.close();
	}

	//ponto 13
	public void saveToJsonRemoto(String url) throws IOException, URISyntaxException{

		PrintWriter writer = new PrintWriter(new URI(url).toURL().openConnection().getOutputStream(),
				true, StandardCharsets.UTF_8);
		
		Gson gson = new Gson();
		
		gson.toJson(this,writer);
		writer.close();
		
	}

	public static void main(String[] args) {
		Aula aula = new Aula("Curos","UC","Turno","Turma",20,"Sex",
				LocalTime.of(13, 0, 0),LocalTime.of(14, 30, 0),LocalDate.of(2022, 12, 2),
				"Sala",30);
		
		Horario h = new Horario(List.of(aula));
		try {
			h.saveToJsonLocal(new File("json_teste.json"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		Gson gson = new Gson();
//		String s = "[ {  \"name\": \"john\", \"city\": \"chicago\", \"age\": \"22\" }, { \"name\": \"gary\", \"city\": \"florida\", \"age\": \"35\" }, { \"name\": \"sal\",  \"city\": \"vegas\", \"age\": \"18\"} ]";
//		String json = gson.toJson(s);
//		System.out.println(s);
//		System.out.println(json);
		
	}
	
}

