import java.util.*;
import java.net.*;
import java.io.*;
import java.time.*;
import com.google.gson.*;

public class ReadFileJsonRemote {

	private URL url;
	private List<Aula> list = new ArrayList<>();
	private Gson gson = new Gson();
	
	public ReadFileJsonRemote(URL url) {
		this.url = url;
	}
	
	private void readFromRemote() throws IOException {
		BufferedReader in = 
				new BufferedReader(new InputStreamReader(url.openStream()));
		
		JsonObject json;
		while((json = gson.fromJson(in.readLine(), JsonObject.class)) != null) {
			// funcao validacao do json
			list.add(jsonToAula(json));
		}
	}
	
	private Aula jsonToAula(JsonObject json) {
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
	
	public List<Aula> getListJsonObject() throws IOException {
		readFromRemote();
		return list;
	}
	
}
