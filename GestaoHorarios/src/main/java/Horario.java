import java.time.*;
import java.net.*;
import java.util.*;
import com.google.gson.*;
import java.io.*;


public class Horario {

	
	private List<Aula> horario;

	public Horario(List<Aula> horario) {
		this.horario = horario;
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
		public void saveToCsvLocal(String path) {
			
	}
		
	//ponto 11
	public void saveToCsvRemoto(String url) {
			
	}
	
	//ponto 12
	public void saveToJsonLocal(String path) {
		
	}
	
	//ponto 13
	public void saveToJsonRemoto(String url) {
		
	}
	
}
