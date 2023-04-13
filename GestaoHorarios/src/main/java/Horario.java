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


    //ponto 6
    public static Horario getHorarioFromJsonLocal(String path) throws IOException {
        File file = new File(path);

        BufferedReader in = new BufferedReader(new FileReader(file));

        return new Horario(readFileJsonWithBufferedReader(in));
    }


    //ponto 7
    public static Horario getHorarioFromJsonRemoto(String url) throws IOException, URISyntaxException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new URI(url).toURL().openStream()));

        return new Horario(readFileJsonWithBufferedReader(in));

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

        return new Aula(curso, uc, turno, turma, inscritos, diaSemana, horaInicio, horaFim, dia, sala, lotacaoSala);
    }

    private static List<Aula> readFileJsonWithBufferedReader(BufferedReader in) throws IOException {
        List<Aula> list = new ArrayList<>();
        Gson gson = new Gson();

        JsonObject json;
        while ((json = gson.fromJson(in.readLine(), JsonObject.class)) != null) {
            if (Validacao.validarDocumento(json))
                list.add(jsonToAula(json));
        }
        return list;
    }

    // ponto 8
    public static Horario getHorarioFromCsvLocal(String path) throws FileNotFoundException, IOException {
        File file = new File(path);

        BufferedReader in = new BufferedReader(new FileReader(file));

        return new Horario(readFileCsvWithBufferedReader(in));
    }

    //ponto 9
    public static Horario getHorarioFromCsvRemoto(String url) throws MalformedURLException, IOException, URISyntaxException {
        BufferedReader in = new BufferedReader(new InputStreamReader(new URI(url).toURL().openStream()));

        return new Horario(readFileCsvWithBufferedReader(in));
    }

    private static List<Aula> readFileCsvWithBufferedReader(BufferedReader in) throws IOException {
        List<Aula> lista = new ArrayList<>();
        CSVFormat format = CSVFormat.EXCEL.withHeader();
        CSVParser csvParser = new CSVParser(in, format);

        for (CSVRecord csvRecord : csvParser) {
            //função que valida csvRecord
            String curso = csvRecord.get("Curso").toString();
            String uc = csvRecord.get("Unidade Curricular").toString();
            String turno = csvRecord.get("Turno").toString();
            String turma = csvRecord.get("Turma").toString();
            Integer inscritos = Integer.parseInt(csvRecord.get("Inscritos no turno").toString());
            String diaSemana = csvRecord.get("Dia da semana").toString();
            LocalTime horaInicio = LocalTime.parse(csvRecord.get("Hora início da aula").toString());
            LocalTime horaFim = LocalTime.parse(csvRecord.get("Hora fim da aula").toString());
            LocalDate dia = LocalDate.parse(csvRecord.get("Data da aula").toString());
            String sala = csvRecord.get("Sala atribuída à aula").toString();
            Integer lotacaoSala = Integer.parseInt(csvRecord.get("Lotação da sala").toString());

            Aula x = new Aula(curso, uc, turno, turma, inscritos, diaSemana, horaInicio, horaFim, dia, sala,
                    lotacaoSala);
            lista.add(x);
        }
        return lista;
    }


    public void saveToCsvLocal(String path) throws IOException {
        FileWriter writer = new FileWriter(path);
        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader("Curso", "Unidade Curricular", "Turno", "Turma",
                "Inscritos no turno", "Dia da semana", "Hora início da aula", "Hora fim da aula", "Data da aula", "Sala atríbuida à aula", "Lotacão da sala");
        CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat);

        for (Aula aula : horario) {
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
    public void saveToCsvRemoto(String url) throws Exception {

        PrintWriter writer = new PrintWriter(new URI(url).toURL().openConnection().getOutputStream(),
                true, StandardCharsets.UTF_8);

        CSVFormat csvFormat = CSVFormat.EXCEL.withHeader(
                "Curso", "Unidade Curricular", "Turno", "Turma", "Inscritos no turno",
                "Dia da semana", "Hora início da aula", "Hora fim da aula", "Data da aula",
                "Sala atríbuida à aula", "Lotação da sala");
        CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat);

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
        csvPrinter.close();
        writer.close();

    }

    //ponto 12
    public void saveToJsonLocal(String path) {

    }

    //ponto 13
    public void saveToJsonRemoto(String url) {

    }

}
