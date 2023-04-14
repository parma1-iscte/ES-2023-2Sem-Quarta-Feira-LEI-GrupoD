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


        if (!parser.getHeaderNames().containsAll(colunasDoCSV))//TODO corrigir
            //https://docs.oracle.com/javase/8/docs/api/java/util/List.html#containsAll-java.util.Collection-
            /*
             * Se tiver colunas que não são as que era suposto ter
             * (que são as guardadas na lista colunasDoCSV)
             * retorna falso senão continua para
             * o resto das veirficações.
             */
            return false;
/*
        List.of("Curso",
                "Unidade Curricular", "Turno", "Turma", "Inscritos no turno", "Dia da semana", "Hora início da aula", "Hora fim da aula",
                "Data da aula","Lotação da sala", "Sala atribuída à aula");*/
        List<CSVRecord> list = parser.getRecords();
        for(CSVRecord record : list){
           boolean isRecordValid =  !record.get("Curso").isEmpty() && !record.get("Unidade Curricular").isEmpty() &&
                    !record.get("Turno").isEmpty()  &&  !record.get("Turma").isEmpty()  &&
                    !record.get("Inscritos no turno").isEmpty() && ((int) record.get("Inscritos no turno")) >= 0 //TODO corrigir erro conhecido;
                    &&  !record.get("Dia da semana").isEmpty() && !record.get("Hora início da aula").isEmpty() &&
                    &&  !record.get("Hora fim da aula").isEmpty() && !record.get("Data da aula").isEmpty() &&
                    &&  !record.get("Lotação da sala").isEmpty() && !record.get("Sala atribuída à aula").isEmpty() ;
        }
        return true;
    }

    public static void main(String[] args) {
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

       List<CSVRecord> list = parser.getRecords();
       System.out.print( list.get(0));
    }
}