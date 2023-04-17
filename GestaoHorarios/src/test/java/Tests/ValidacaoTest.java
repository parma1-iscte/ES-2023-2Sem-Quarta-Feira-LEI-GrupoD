package Tests;

import org.apache.commons.csv.CSVFormat;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
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
 * Versão 1.0
 */

public class ValidacaoTest {

    List<CSVRecord> headers;
    List<CSVRecord> lines;

    @BeforeAll
    public void setUp() {
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
       headers = parser.getRecords();
    }

    @BeforeAll
    public void setUp2(){
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
        lines = parser.getRecords();
    }
    @Test
    public void testValidarCsvLine(){
        assertTrue(Validacao.validarCsvLine(headers.get(0)));  //Record valido
        assertFalse(Validacao.validarCsvLine(headers.get(2))); //Record com um campo a null
        assertFalse(Validacao.validarCsvLine(headers.get(3))); // Record com um campo com empty String
        assertFalse(Validacao.validarCsvLine(headers.get(4))); // Record com campo numerico negativo
    }

    @Test
    public void testValidarCsvHeader(){
        assertTrue(Validacao.validarCsvLine(headers.get(0)));  //Header correto
        assertTrue(Validacao.validarCsvLine(headers.get(1)));  //Header com linhas desodenadas
        assertFalse(Validacao.validarCsvLine(headers.get(2))); // Header com colunas a mais
        assertFalse(Validacao.validarCsvLine(headers.get(3))); // Header com colunas a menos
        assertFalse(Validacao.validarCsvLine(headers.get(4))); // Header com colunas a alteradas

    }
}