package Tests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.*;
import java.util.List;

import java.nio.charset.Charset;
import java.util.Arrays;

import java.io.IOException;
import org.apache.commons.csv.*;

import com.google.gson.JsonObject;
import Horario.*;

/**
 * 
 * @author ES-2023-2Sem-Quarta-Feira-LEI-GrupoD
 *
 */

public class ValidacaoTest {
    @Test
    public void testValidarCsvLine(){

        CSVFormat format =  CSVFormat.EXCEL
                .withHeader()  //This causes the parser to read the first record and use its values as column names
                .withSkipHeaderRecord(true)
                .withDelimiter(';');
        CSVParser parser = null;
        try {
            File csvData = new File("HeaderSet.csv");
            parser = CSVParser.parse(csvData,Charset.defaultCharset(),format);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<CSVRecord> list = parser.getRecords();
        assertTrue( Validacao.validarCsvLine(list.get(0)));
        assertTrue( Validacao.validarCsvLine(list.get(1)));
        assertFalse( Validacao.validarCsvLine(list.get(2)));
        assertFalse( Validacao.validarCsvLine(list.get(3)));
        assertFalse( Validacao.validarCsvLine(list.get(4)));

    }
 

}