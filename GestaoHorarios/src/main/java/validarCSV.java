import java.util.*;
import java.io.File;
import org.apache.commons.csv.*;
import org.apache.commons.csv.CSVFormat;
import java.nio.charset.Charset;


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
            "Data da aula","Lotação da sala", "Sala atribuída à aula");

    private validarCSV() {
    }

    /*
    Validar:
        Se tem as colunas todas(falta ordenar as listas)
        Se campos corretos

     */
    public static boolean validar() {
        //TODO por path ou File instance como argumento (uma versao para ambos prov)
        CSVFormat format =   CSVFormat.Builder.create()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setDelimiter(';')
                .build();
        CSVParser parser = null;
        try {
            File csvData = new File("horario_exemplo.csv");
            parser = CSVParser.parse(csvData, Charset.defaultCharset(),format);
        } catch (java.lang.Exception e) {
            throw new RuntimeException(e);
            //TODO
        }


        if (!parser.getHeaderNames().containsAll(colunasDoCSV))
            //https://docs.oracle.com/javase/8/docs/api/java/util/List.html#containsAll-java.util.Collection-
            /*
             * Se tiver colunas que não são as que era suposto ter
             * (que são as guardadas na lista colunasDoCSV)
             * retorna falso senão continua para
             * o resto das veirficações.
             */
            return false;
        return true;
    }

    public static void main(String[] args) {
        System.out.println(validar());

    }
}