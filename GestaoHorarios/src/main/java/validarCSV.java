import java.util.*;
import java.io.File;
import org.apache.commons.csv.*;
import org.apache.commons.csv.CSVFormat;
/**
 * @author Pedro Mendonça
 * Desenvolver uma função que valida documentos de um ficheiro csv (se têm o formato correto)
 * (3 horas)
 */
public class validarCSV {
    // É singleton

    public static final validarCSV INSTANCE = new validarCSV();
    private static final List<String> colunasDoCSV = List.of("Curso",
            "Unidade Curricular", "Turno", "Turma", "Inscritos no turno", "Dia da semana", "Hora início da aula", "Hora fim da aula",
            "Data da aula", "Sala atribuída à aula", "Lotação da sala");

    private validarCSV() {
    }

    /*
    Validar:
        Se tem as colunas todas
        Se campos corretos

     */
    public static void validar() {


        CSVParser parser = null;
        try {
            parser = CSVParser.parse("./horario_exemplo.csv",CSVFormat.EXCEL);
        } catch (java.lang.Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(parser.getHeaderNames());


    }

    public static void main(String[] args) {

        CSVParser parser = null;
        try {
            parser = CSVParser.parse("./horario_exemplo.csv",CSVFormat.create().setHeader().setSkipHeaderRecord(true));
        } catch (java.lang.Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println(parser.getHeaderNames().toString());
    }
}