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
 * Versão 3.0
 */

public class Horario {

	public static final String UTF_8 = "UTF-8";
	public static final String JSON_EXTENSION_NAME = ".json";
	public static final String CSV_EXTENSION_NAME = ".csv";
	private List<Aula> horario;

	private static final  CSVFormat format = CSVFormat.EXCEL
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

	public static Horario getHorarioFromJsonLocal(File file) throws FileNotFoundException, UnsupportedEncodingException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), UTF_8));
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
		return new BufferedReader(new InputStreamReader(con.getInputStream(), UTF_8));
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

	public static Horario getHorarioFromCsvLocal(File file) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), UTF_8));
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
			String curso = csvRecord.get(Validacao.CURSO);
			String uc = csvRecord.get(Validacao.UNIDADE_CURRICULAR);
			String turno = csvRecord.get(Validacao.TURNO);
			String turma = csvRecord.get(Validacao.TURMA);
			Integer inscritos = Integer.parseInt(csvRecord.get(Validacao.INSCRITOS_NO_TURNO));
			String diaSemana = csvRecord.get(Validacao.DIA_DA_SEMANA);
			LocalTime horaInicio = LocalTime.parse(csvRecord.get(Validacao.HORA_INICIO_DA_AULA));
			LocalTime horaFim = LocalTime.parse(csvRecord.get(Validacao.HORA_FIM_DA_AULA));
			LocalDate dia;
			try {
				dia = LocalDate.parse(csvRecord.get(Validacao.DATA_DA_AULA),DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			}catch(Exception e) {
				dia = LocalDate.parse(csvRecord.get(Validacao.DATA_DA_AULA));
			}
			String sala = csvRecord.get(Validacao.SALA_ATRIBUIDA_A_AULA);
			Integer lotacaoSala = Integer.parseInt(csvRecord.get(Validacao.LOTACAO_DA_SALA));

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

	public void saveToCsvLocal(File file) throws IOException {
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

	public void saveToCsvRemoto(String path, String user, String password) throws IOException {
		URL url = new URL(path);
		HttpURLConnection conexao = connectToPage(url, user, password);
		conexao.setRequestMethod("PUT");
		conexao.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(conexao.getOutputStream(), StandardCharsets.UTF_8);
		CSVPrinter csvPrinter = new CSVPrinter(writer,format);
		writeHorarioWithCSVPrinter(csvPrinter);
		csvPrinter.flush();
		csvPrinter.close();
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


	public void saveToJsonLocal(File file) throws IOException {
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

	public void saveToJsonRemoto(String path, String user, String password) throws IOException {
		URL url = new URL(path);
		HttpURLConnection conexao = connectToPage(url, user, password);
		conexao.setRequestMethod("PUT");
		conexao.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(conexao.getOutputStream(), StandardCharsets.UTF_8);
		gson.toJson(horario,writer);
		writer.flush();
		writer.close();
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
	
	/**
    Analisa uma URL webcal e extrai informações sobre as aulas agendadas usando o formato iCalendar.
    @param webCal a URL webcal a ser analisada
    @return um objeto Horario contendo as informações das aulas extraídas do calendário
    @throws IOException se houver um erro de entrada/saída ao abrir a conexão com a URL
    @throws ParserException se houver um erro ao analisar o calendário iCalendar
    */
	
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
	
	/**
    Retorna uma lista de todas as unidades curriculares (UCs) presentes no horário.
    @return uma lista de Strings contendo o nome de todas as UCs presentes no horário.
    */
	
	public List<String> getUcList(){
        List<String> ucs = new ArrayList<>();
        for(Aula aula : horario)
            if(!ucs.contains(aula.getUc()))
                ucs.add(aula.getUc());
        return ucs;
    }

	/**
    Retorna um objeto Horario contendo todas as Aulas correspondentes às Unidades Curriculares
    especificadas na lista de Strings fornecida.
    @param ucs uma lista de Strings contendo os nomes das Unidades Curriculares desejadas.
    @return um objeto Horario contendo todas as Aulas correspondentes às Unidades Curriculares
    especificadas na lista de Strings fornecida.
    */

	public Horario getHorarioFromUcList(List<String> ucs) {
		List<Aula> aulas = new ArrayList<>();
		for(Aula aula : horario)
			if(ucs.contains(aula.getUc()))
				aulas.add(aula);
		return new Horario(aulas);
	}


	/**
	Retorna o Horario obtido ou lança uma exceção em caso de erro
	@param path uma string contendo o caminho para o arquivo desejado.
	@param user uma string contendo o nome de usuário, caso seja necessário para acessar o arquivo.
	@param password uma string contendo a senha de usuário, caso seja necessário para acessar o arquivo.
	@return um objeto Horario contendo as Aulas presentes no arquivo especificado.
	@throws Exception caso ocorra algum erro ao obter o Horario a partir do arquivo.
	*/

	public static Horario getHorarioFromFile(String path, String user, String password) throws Exception {
		if(path.startsWith("webcal"))
			return getHorarioFromFenix(path);
		else if(path.startsWith("http"))
			return getHorarioFromRemoteFile(path,user,password);
		else
			return getHorarioFromLocalFile(path);
	}

	/**
	 Retorna um objeto Horario a partir de um arquivo local, cujo caminho é especificado pela string "path"
	 @return um objeto Horario contendo as Aulas presentes no arquivo especificado.
	 @throws IllegalArgumentException caso a extensão do arquivo não seja suportada.
	 @throws IOException caso getHorarioFromJsonLocal ou getHorarioFromCsvLocal lançem-na.
	 */
	public static Horario getHorarioFromLocalFile(String path) throws IOException
	 {
		if(path.endsWith(JSON_EXTENSION_NAME))
			return getHorarioFromJsonLocal(new File(path));
		else if(path.endsWith(CSV_EXTENSION_NAME))
			return getHorarioFromCsvLocal(new File(path));
		else
			throw new IllegalArgumentException("Ficheiro não suportado");
	}

	/**

    Retorna um objeto Horario a partir de um arquivo remoto, cujo caminho é especificado pela string "path".
    O método determina o tipo de arquivo a partir da extensão e chama um método específico para obter o Horario
    @param path uma string contendo o caminho para o arquivo desejado.
    @param user uma string contendo o nome de usuário, caso seja necessário para acessar o arquivo.
    @param password uma string contendo a senha de usuário, caso seja necessário para acessar o arquivo.
    @return um objeto Horario contendo as Aulas presentes no arquivo especificado.
    @throws IOException caso ocorra um erro de entrada/saída ao obter o Horario a partir do arquivo.
    @throws IllegalArgumentException caso a extensão do arquivo não seja suportada.
    */

	public static Horario getHorarioFromRemoteFile(String path, String user, String password) throws IOException {
		if(user != null)
			if(user.isBlank() || user.isEmpty()) user = null;
		if(password != null)
			if(password.isBlank() || password.isEmpty()) password = null;
		if(path.endsWith(JSON_EXTENSION_NAME))
			return getHorarioFromJsonRemote(path,user,password);
		else if(path.endsWith(CSV_EXTENSION_NAME))
			return getHorarioFromCsvRemoto(path,user,password);
		else
			throw new IllegalArgumentException("Ficheiro não suportado");
	}

	/**
	 O método determina o tipo de arquivo a partir da extensão e chama um método específico para salvar o Horario.
	 @param path uma string contendo o caminho para o arquivo no qual o Horario deve ser salvo.
	 @param user uma string contendo o nome de usuário para autenticação no caso de um arquivo remoto. Pode ser nulo.
	 @param password uma string contendo a senha para autenticação no caso de um arquivo remoto. Pode ser nulo.
	 @throws IOException caso ocorra algum erro ao salvar o Horario no arquivo ou a extensão do arquivo não seja suportada.
	 */
	public void saveHorarioInFile(String path, String user, String password) throws IOException {
		if(path.startsWith("http"))
			saveHorarioInRemoteFile(path,user,password);
		else
			saveHorarioInLocalFile(path);
	}

	/**
	 O método determina o tipo de arquivo a partir da extensão e chama um método específico para salvar o Horario.
	 @param path uma string contendo o caminho para o arquivo no qual o Horario deve ser salvo.
	 @throws IOException caso ocorra algum erro ao salvar o Horario no arquivo local ou a extensão do arquivo não seja suportada.
	 @throws IllegalArgumentException path não terminar com a substring ".csv" ou ".json"
	 */
	public void saveHorarioInLocalFile(String path) throws IOException {
		if(path.endsWith(JSON_EXTENSION_NAME))
			saveToJsonLocal(new File(path));
		else if(path.endsWith(CSV_EXTENSION_NAME))
			saveToCsvLocal(new File(path));
		else
			throw new IllegalArgumentException();
	}
	/**
	 @param path uma string contendo o caminho para o arquivo no qual o Horario deve ser salvo.
	 @param user uma string contendo o nome de usuário, caso seja necessário para acessar o arquivo remoto.
	 @param password uma string contendo a senha de usuário, caso seja necessário para acessar o arquivo remoto.
	 @throws IOException caso ocorra algum erro ao salvar o Horario no arquivo remoto ou a extensão do arquivo não seja suportada.
	 @throws IllegalArgumentException path não terminar com a substring ".csv" ou ".json"
	 */
	public void saveHorarioInRemoteFile(String path, String user, String password) throws IOException {
		if(user != null)
			if(user.isBlank() || user.isEmpty()) user = null;
		if(password != null)
			if(password.isBlank() || password.isEmpty()) password = null;
		if(path.endsWith(JSON_EXTENSION_NAME))
			saveToJsonRemoto(path,user,password);
		else if(path.endsWith(CSV_EXTENSION_NAME))
			saveToCsvRemoto(path,user,password);
		else
			throw new IllegalArgumentException();
	}
}