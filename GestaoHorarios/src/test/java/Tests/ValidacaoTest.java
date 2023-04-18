package Tests;

import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.*;
import java.util.List;

import java.nio.charset.Charset;

import java.io.IOException;
import org.apache.commons.csv.*;

import Horario.*;

/**
 * 
 * @author ES-2023-2Sem-Quarta-Feira-LEI-GrupoD
 * Versão 1.0
 */

public  class ValidacaoTest {
  
    @Test
    public  void testValidarCsvLine(){
        CSVFormat format =  CSVFormat.EXCEL
                .withHeader()  //This causes the parser to read the first record and use its values as column names
                .withSkipHeaderRecord(true)
                .withDelimiter(';');
        CSVParser parser = null;
        try {
            File csvData = new File("linesSets.csv");
            parser = CSVParser.parse(csvData,Charset.defaultCharset(),format);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<CSVRecord> lines = parser.getRecords();
        assertTrue(Validacao.validarCsvLine(lines.get(0)));  //Record valido
        assertFalse(Validacao.validarCsvLine(lines.get(1))); //Record com um campo a null
        assertFalse(Validacao.validarCsvLine(lines.get(2))); // Record com um campo com empty String
        assertFalse(Validacao.validarCsvLine(lines.get(3))); // Record com campo numerico negativo
    }

    @Test
    public   void testValidarCsvHeader(){

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
List<CSVRecord> headers = parser.getRecords();
        assertTrue(Validacao.validarCsvLine(headers.get(0)));  //Header correto
        assertTrue(Validacao.validarCsvLine(headers.get(1)));  //Header com linhas desodenadas
        assertFalse(Validacao.validarCsvLine(headers.get(2))); // Header com colunas a mais
        assertFalse(Validacao.validarCsvLine(headers.get(3))); // Header com colunas a menos
        assertFalse(Validacao.validarCsvLine(headers.get(4))); // Header com colunas a alteradas
    }
}