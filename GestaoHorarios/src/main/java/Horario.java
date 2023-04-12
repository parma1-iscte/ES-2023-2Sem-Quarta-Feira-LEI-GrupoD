import java.time.*;
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

	//ponto 6
	public static Horario getHorarioFromJsonLocal(String path) throws IOException {
		File file = new File(path);

		BufferedReader in = new BufferedReader(new FileReader(file));

		return new Horario(readFileJsonWithBufferedReader(in));
	}





	//ponto 7
	public static Horario getHorarioFromJsonRemoto(String url) throws IOException, URISyntaxException {
		BufferedReader in = 
				new BufferedReader(new InputStreamReader(new URI(url).toURL().openStream()));

		return new Horario(readFileJsonWithBufferedReader(in));

	}


	//ponto 8
	public static Horario getHorarioFromCsvLocal(String path) {
		return null;
	}

	//ponto 9
	public static Horario getHorarioFromCsvRemoto(String url) {
		return null;
	}

	//ponto 10
	public void saveToCsvLocal(String path) throws IOException {

		FileWriter writer = new FileWriter(path);
		CSVFormat csvFormat = CSVFormat.DEFAULT.withDelimiter(';').withHeader("Curso", "Unidade Curricular", "Turno", "Turma",
				"Inscritos no turno", "Dia da semana", "Hora início da aula", "Hora fim da aula", "Data da aula", "Sala atríbuida à aula", "Lotacao da sala");
		CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat);

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


		csvPrinter.close();
		writer.close();
	}


	//ponto 11
	public void saveToCsvRemoto(String url) throws URISyntaxException, IOException {

		try (PrintWriter writer = new PrintWriter(new URI(url).toURL().openConnection().getOutputStream(),
				true, StandardCharsets.UTF_8);
				CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Curso", 
						"Unidade Corricular", "Turno", "Turma","Inscritos no turno", "Dia da semana",
						"Hora início da aula", "Hora fim da aula", "Data da aula", 
						"Sala atribuída à aula", "Lotação da sala"))) 
		{
			for (Aula aula : horario) {
				csvPrinter.printRecord(
						aula.getCurso(), 
						aula.getUc(), 
						aula.getTurno(), 
						aula.getTurma(), 
						aula.getInscritos(), 
						aula.getDiaSemana(), 
						aula.getHoraInicio(),
						aula.getHoraFim(), 
						aula.getDia(), 
						aula.getSala(), 
						aula.getLotacaoSala()
						);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//ponto 12
	public void saveToJsonLocal(String path) {

	}

	//ponto 13
	public void saveToJsonRemoto(String url) {

	}

}
