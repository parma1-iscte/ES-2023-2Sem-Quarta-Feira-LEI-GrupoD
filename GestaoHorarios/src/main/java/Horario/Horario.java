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
	Lê um arquivo JSON local e converte seu conteúdo em uma lista de aulas.
    @param file o arquivo JSON a ser lido
    @return uma lista de aulas contidas no arquivo JSON
    @throws IOException se ocorrer um erro ao ler o arquivo
    */
	
	public static Horario getHorarioFromJsonLocal(File file) throws IOException {

		BufferedReader in = new BufferedReader(new FileReader(file));

		return new Horario(readFileJsonWithBufferedReader(in));
	}


	//ponto 7
	
	/**

    Lê um arquivo JSON remoto e converte seu conteúdo em uma lista de aulas.
    @param url a URL do arquivo JSON a ser lido
    @return uma lista de aulas contidas no arquivo JSON
    @throws IOException se ocorrer um erro ao ler o arquivo
    @throws URISyntaxException se a URI da URL estiver incorreta
    */
	
	public static Horario getHorarioFromJsonRemoto(String url) throws IOException, URISyntaxException {
		BufferedReader in = new BufferedReader(new InputStreamReader(new URI(url).toURL().openStream()));

		return new Horario(readFileJsonWithBufferedReader(in));

	}

	/**

    Converte um objeto JSON em uma instância de Aula.

    @param json o objeto JSON a ser convertido

    @return uma instância de Aula contida no objeto JSON
    */
	
	// funcoes communs para pontos 6,7
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

    Lê o conteúdo de um arquivo JSON com o auxílio de um BufferedReader e o converte em uma lista de aulas.

    @param in um BufferedReader para ler o conteúdo do arquivo JSON

    @return uma lista de aulas contidas no arquivo JSON

    @throws IOException se ocorrer um erro ao ler o arquivo
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
	public static Horario getHorarioFromCsvLocal(File file) throws FileNotFoundException, IOException {

		BufferedReader in = new BufferedReader(new FileReader(file));

		return new Horario(readFileCsvWithBufferedReader(in));
	}

	//ponto 9
	public static Horario getHorarioFromCsvRemoto(String url) throws MalformedURLException, IOException, URISyntaxException {
		BufferedReader in = new BufferedReader(new InputStreamReader(new URI(url).toURL().openStream()));

		return new Horario(readFileCsvWithBufferedReader(in));
	}

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
	public void saveToJsonLocal(String csvFilePath, String jsonFilePath) throws IOException {
		Reader reader = new FileReader(csvFilePath);
		CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
		List<Object> data = new ArrayList<>();
		for (CSVRecord csvRecord : csvParser) {
			data.add(csvRecord.toMap());
		}
		Gson gson = new Gson();
		String json = gson.toJson(data);
		FileWriter writer = new FileWriter(jsonFilePath);
		writer.write(json);
		writer.close();



	}

	//ponto 13
	public void saveToJsonRemoto(String csvFilePath, String jsonFilePath,String url) throws IOException, URISyntaxException{
		PrintWriter writer = new PrintWriter(new URI(url).toURL().openConnection().getOutputStream(),
				true, StandardCharsets.UTF_8);
		Reader reader = new FileReader(csvFilePath);
		CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
		List<Object> data = new ArrayList<>();
		for (CSVRecord csvRecord : csvParser) {
			data.add(csvRecord.toMap());
		}
		Gson gson = new Gson();
		String json = gson.toJson(data);
		writer.write(json);
		writer.close();
	}

}
