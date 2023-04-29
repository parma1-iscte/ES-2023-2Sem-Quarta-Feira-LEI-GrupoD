package Horario;
import java.time.*;

import java.time.format.DateTimeFormatter;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.apache.commons.csv.*;
import org.apache.commons.csv.CSVFormat;
import com.google.gson.*;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Parameter;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.VEvent;

import java.io.*;

/**
 * 
 * @author ES-2023-2Sem-Quarta-Feira-LEI-GrupoD
 * Versão 2.0
 */

public class Horario {

	private List<Aula> horario;

	static final private CSVFormat format = CSVFormat.EXCEL
			.withHeader() // This causes the parser to read the first record and use its values as column names
			.withSkipHeaderRecord(true)
			.withDelimiter(';');

	private static final Gson gson = new GsonBuilder()
			.registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter())
			.registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter())
			.setPrettyPrinting()
			.create();


	public Horario(List<Aula> horario) {
		this.horario = horario;
	}



	public List<Aula> getHorario() {
		return horario;
	}


	/**

    Retorna um objeto Horario a partir de um arquivo JSON localizado em um caminho remoto, usando o usuário e a senha fornecidos para autenticação.
    @param path o caminho remoto para o arquivo JSON
    @param user o nome de usuário a ser usado para autenticação
    @param password a senha a ser usada para autenticação
    @return um objeto Horario que representa o conteúdo do arquivo JSON
    @throws IOException se ocorrer um erro ao ler o arquivo JSON ou ao conectar ao servidor remoto
	 */

	public static Horario getHorarioFromJsonRemote(String path, String user, String password) throws IOException {
		BufferedReader br = getWebContent(path, user, password);
		return new Horario(readFileJsonWithBufferedReader(br));

	}

	/**

    Retorna um objeto Horario a partir de um arquivo JSON local.
    @param file o arquivo JSON a ser lido
    @return um objeto Horario que representa o conteúdo do arquivo JSON
    @throws Exception se ocorrer um erro ao ler o arquivo JSON
	 */

	public static Horario getHorarioFromJsonLocal(File file) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		return new Horario(readFileJsonWithBufferedReader(br));
	}


	/**

    Retorna um objeto BufferedReader que contém o conteúdo da página web localizada em um caminho remoto, usando o usuário e a senha fornecidos para autenticação
	@param path o caminho remoto para a página web
	@param user o nome de usuário a ser usado para autenticação
	@param password a senha a ser usada para autenticação
	@return um objeto BufferedReader contendo o conteúdo da página web
	@throws IOException se ocorrer um erro ao conectar à página web ou ao ler o seu conteúdo
	 */

	public static BufferedReader getWebContent(String path, String user, String password) throws IOException {
		URL url = new URL(path);
		HttpURLConnection con = connectToPage(url, user, password);
		con.setRequestMethod("GET");
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		return br;
	}



	/**
	 * Lê o conteúdo de um arquivo JSON com o auxílio de um BufferedReader e o converte em uma lista de aulas.
	 * @param in um BufferedReader para ler o conteúdo do arquivo JSON
	 * @return uma lista de aulas contidas no arquivo JSON
	 * @throws IOException se ocorrer um erro ao ler o arquivo
	 */
	private static List<Aula> readFileJsonWithBufferedReader(BufferedReader br){
		Aula[] aulas = gson.fromJson(br, Aula[].class);
		return Arrays.asList(aulas);
	}


	public static Horario getHorarioFromCsvRemoto(String path, String user, String password) throws IOException {
		BufferedReader br = getWebContent(path, user, password);
		return new Horario(readFileCsvWithBufferedReader(br));
	}

	public static Horario getHorarioFromCsvLocal(File file) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		return new Horario(readFileCsvWithBufferedReader(br));
	}


	/**

    Este método lê um arquivo CSV com um BufferedReader e cria uma lista de objetos Aula.

    @param in o BufferedReader que aponta para o arquivo CSV
    @return uma lista de objetos Aula criados a partir do arquivo CSV
    @throws IOException se ocorrer um erro de I/O ao ler o arquivo
    @throws IllegalArgumentException se o arquivo CSV estiver mal estruturado
	 */


	public static List<Aula> readFileCsvWithBufferedReader(BufferedReader in) throws IOException {
		List<Aula> lista = new ArrayList<>();
		CSVParser csvParser = new CSVParser(in, format);
		List<CSVRecord> list = csvParser.getRecords();
		if(!Validacao.validarDocumento(csvParser)){
			throw new IllegalArgumentException("Ficheiro mal estruturado");
		}
		for (CSVRecord csvRecord : list) {
			String curso = csvRecord.get("Curso");
			String uc = csvRecord.get("Unidade Curricular");
			String turno = csvRecord.get("Turno");
			String turma = csvRecord.get("Turma");
			Integer inscritos = Integer.parseInt(csvRecord.get("Inscritos no turno"));
			String diaSemana = csvRecord.get("Dia da semana");
			LocalTime horaInicio = LocalTime.parse(csvRecord.get("Hora início da aula"));
			LocalTime horaFim = LocalTime.parse(csvRecord.get("Hora fim da aula"));
			LocalDate dia;
			try {
				 dia = LocalDate.parse(csvRecord.get("Data da aula"),DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			}catch(Exception e) {
				dia = LocalDate.parse(csvRecord.get("Data da aula"));
			}
			String sala = csvRecord.get("Sala atribuída à aula");
			Integer lotacaoSala = Integer.parseInt(csvRecord.get("Lotação da sala"));

			lista.add(new Aula(curso, uc, turno, turma, inscritos, diaSemana, horaInicio, horaFim, dia, sala,
					lotacaoSala));
		}

		return lista;
	}

	/**

    Salva o objeto Horario em um arquivo CSV local usando o arquivo fornecido.
    @param file o arquivo local onde o objeto Horario será salvo em formato CSV
    @throws Exception se ocorrer um erro ao escrever o objeto Horario em formato CSV ou ao salvar o arquivo localmente
	 */

	public void saveToCsvLocal(File file) throws Exception {
		PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8);
		CSVPrinter csvPrinter = new CSVPrinter(writer, format);
		writeHorarioWithCSVPrinter(csvPrinter);
		csvPrinter.close();
		writer.close();
	}

	/**

    Salva o objeto Horario em um arquivo CSV remoto usando o caminho, o nome de usuário e a senha fornecidos para autenticação.
    @param path o caminho remoto para o arquivo CSV
    @param user o nome de usuário a ser usado para autenticação
    @param password a senha a ser usada para autenticação
    @throws Exception se ocorrer um erro ao escrever o objeto Horario em formato CSV ou ao enviá-lo para o servidor remoto
	 */

	public void saveToCsvRemoto(String path, String user, String password) throws Exception{
		URL url = new URL(path);
		HttpURLConnection conexao = connectToPage(url, user, password);
		conexao.setRequestMethod("PUT");
		conexao.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(conexao.getOutputStream(), StandardCharsets.UTF_8);
		CSVPrinter csvPrinter = new CSVPrinter(writer,format);
		writeHorarioWithCSVPrinter(csvPrinter);
		csvPrinter.flush();
		csvPrinter.close();
		int resposta = conexao.getResponseCode();
		System.out.println("Resposta do servidor: " + resposta);
	}

	/**
	 * Escreve os dados do horário em formato CSV utilizando o {@link CSVPrinter}.
	 * @param csvPrinter o {@link CSVPrinter} a ser utilizado para escrever os dados
	 * @throws IOException se ocorrer um erro de entrada/saída ao escrever os dados
	 */
	private void writeHorarioWithCSVPrinter(CSVPrinter csvPrinter) throws IOException {
		csvPrinter.printRecord("Curso",
				"Unidade Curricular", "Turno", "Turma", "Inscritos no turno", "Dia da semana", "Hora início da aula",
				"Hora fim da aula",
				"Data da aula", "Sala atribuída à aula","Lotação da sala");
		for (Aula aula: horario) {
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
					aula.getLotacaoSala());
		}
	}


	public void saveToJsonLocal(File file) throws Exception{
		FileWriter fw = new FileWriter(file,StandardCharsets.UTF_8);
		gson.toJson(horario,fw);
		fw.flush();
		fw.close();
	}

	/**

    Salva o objeto Horario em um arquivo JSON remoto usando o caminho, o nome de usuário e a senha fornecidos para autenticação.
    @param path o caminho remoto para o arquivo JSON
    @param user o nome de usuário a ser usado para autenticação
    @param password a senha a ser usada para autenticação
    @throws Exception se ocorrer um erro ao escrever o objeto Horario em formato JSON ou ao enviá-lo para o servidor remoto
	 */

	public void saveToJsonRemoto(String path, String user, String password) throws Exception {
		URL url = new URL(path);
		HttpURLConnection conexao = connectToPage(url, user, password);
		conexao.setRequestMethod("PUT");
		conexao.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(conexao.getOutputStream(), StandardCharsets.UTF_8);
		gson.toJson(horario,writer);
		writer.flush();
		writer.close();
		int resposta = conexao.getResponseCode();
		System.out.println("Resposta do servidor: " + resposta);
	}

	/**

    Cria uma conexão HttpURLConnection com uma página web usando a URL fornecida e as credenciais de autenticação especificadas.
    @param url a URL da página web
    @param user o nome de usuário a ser usado para autenticação (pode ser nulo se a página não requer autenticação)
    @param password a senha a ser usada para autenticação (pode ser nula se a página não requer autenticação)
    @return uma conexão HttpURLConnection configurada para acessar a página web com as credenciais de autenticação fornecidas
    @throws IOException se ocorrer um erro ao conectar à página web
	 */

	public static HttpURLConnection connectToPage(URL url, String user, String password) throws IOException {
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		if(user != null && password != null) {
			// Adição do cabeçalho de autorização básico com as credenciais de autenticação
			String auth = user + ":" + password;
			byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
			String authHeader = "Basic " + new String(encodedAuth);
			con.setRequestProperty("Authorization", authHeader);
		}
		return con;
	}
	
	public static Horario getHorarioFromFenix(String webCal) throws IOException, ParserException {
        List<Aula> aulas = new ArrayList<>();
        CalendarBuilder builder = new CalendarBuilder();
        String uri = webCal.replace("webcal://", "https://");
        URL url = new URL(uri);
        Calendar calendar = builder.build(url.openStream());

        for(Object obj : calendar.getComponents(Component.VEVENT)){
            VEvent event = (VEvent) obj;
            if(event.getDescription().getValue().split("\n").length == 15) {
                String curso = "Desconhecido";
                String uc = event.getSummary().getValue().split("-")[0];
                String turno = event.getDescription().getValue().split("\n")[3].split(":")[1];
                String turma = "Desconhecida";
                Integer inscritos = 0;
                LocalDate dia = event.getStartDate().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalTime horaInicio = event.getStartDate().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
                LocalTime horaFim = event.getEndDate().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
                String diaSemana = dia.getDayOfWeek().toString();
                String sala = event.getLocation().getValue().split(",")[0];
                Integer lotacaoSala = 0;
                aulas.add(new Aula( curso,  uc, turno, turma, inscritos, diaSemana, horaInicio, horaFim, dia, sala, lotacaoSala));

            }
        }

        return new Horario(aulas);
    }
}