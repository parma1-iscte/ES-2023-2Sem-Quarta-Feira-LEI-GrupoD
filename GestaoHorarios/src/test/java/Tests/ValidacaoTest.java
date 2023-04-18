package Tests;

import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.*;

import java.nio.charset.Charset;

import java.io.IOException;
import org.apache.commons.csv.*;

import Horario.*;

/**
 * 
 * @author ES-2023-2Sem-Quarta-Feira-LEI-GrupoD
 *         Versão 1.0
 */

public class ValidacaoTest {

   private static  final CSVFormat format = CSVFormat.EXCEL
    .withHeader() // This causes the parser to read the first record and use its values as column
                  // names
    .withSkipHeaderRecord(true)
    .withDelimiter(';');
    
    @Test
    public void testValidarCSVCorreto() {
        // Ficheiro valido
        CSVParser correto = null;
        try {
            File csvData = new File("Conjunto de teste/Correto.csv");
            correto = CSVParser.parse(csvData, Charset.defaultCharset(), format);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertTrue(Validacao.validarDocumento(correto));         
    }
    
    @Test
    public void validarCSVComHeaderAlterado() {
        CSVParser headerAlterado = null;
           try {
               File csvData5 = new File("Conjunto de teste/Header com colunas a alteradas.csv");
               headerAlterado = CSVParser.parse(csvData5, Charset.defaultCharset(), format);
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
           assertFalse(Validacao.validarDocumento(headerAlterado));
    }
    @Test
    public void validarCSVComHeaderComLinhasAMais() {
        CSVParser headerComRowsAmais = null;
           try {
               File csvData6 = new File("Conjunto de teste/Header com colunas a mais.csv");
               headerComRowsAmais = CSVParser.parse(csvData6, Charset.defaultCharset(), format);
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
           assertFalse(Validacao.validarDocumento(headerComRowsAmais));
    }

    @Test
    public void validarCSVComHeaderComLinhasAMenos() {
        CSVParser headerComRowsAmenos = null;
           try {
               File csvData7 = new File("Conjunto de teste/Header com colunas a menos.csv");
               headerComRowsAmenos = CSVParser.parse(csvData7, Charset.defaultCharset(), format);
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
           assertFalse(Validacao.validarDocumento(headerComRowsAmenos));
    }
    @Test
    public void validarCSVComHeaderDesordenado() {
        CSVParser headerDesordenado = null;
           try {
               File csvData8 = new File("Conjunto de teste/Header com linhas desordenadas.csv");
               headerDesordenado = CSVParser.parse(csvData8, Charset.defaultCharset(), format);
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
           assertFalse(Validacao.validarDocumento(headerDesordenado));
    }

    @Test
    public void validarCSVComFieldNull() {
        // Record com um campo a null

       CSVParser recordComNull = null;
       try {
           File csvData2 = new File("Conjunto de teste/Record com um campo a null.csv");
           recordComNull = CSVParser.parse(csvData2, Charset.defaultCharset(), format);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
       assertFalse(Validacao.validarDocumento(recordComNull));
    }

    @Test
    public void validarCSVComEmptyString() {
        // Record com um campo com empty String
         CSVParser recordComEmptyString = null;
        try {
            File csvData3 = new File("Conjunto de teste/Record com um campo com empty String.csv");
            recordComEmptyString = CSVParser.parse(csvData3, Charset.defaultCharset(), format);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertFalse(Validacao.validarDocumento(recordComEmptyString));
    }
    @Test
    public void validarCSVComFieldNegativo() {
        // Record com campo numerico negativo
           CSVParser recordComNumNeg = null;
           try {
               File csvData4 = new File("Conjunto de teste/Record com campo numerico negativo.csv");
               recordComNumNeg = CSVParser.parse(csvData4, Charset.defaultCharset(), format);
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
           assertFalse(Validacao.validarDocumento(recordComNumNeg));
    }

}