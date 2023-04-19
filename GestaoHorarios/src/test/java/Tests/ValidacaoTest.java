package Tests;

import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Test;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.*;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalTime;
import java.io.IOException;
import org.apache.commons.csv.*;

import Horario.*;



/**
 * 
 * @author ES-2023-2Sem-Quarta-Feira-LEI-GrupoD
 *         Versão 1.0
 */

public class ValidacaoTest {
	static JsonObject objeto=new JsonObject();
	@BeforeAll
	public static void setup() {
		
		objeto.addProperty("Curso", "LEI");
	     objeto.addProperty("Unidade Curricular", "POO");
	     objeto.addProperty("Turma", "LEI-P");
	     objeto.addProperty("Dia da semana", "SEG");
	     objeto.addProperty("Inscritos no turno", 50);
	     objeto.addProperty("Hora de início da aula", "09:00:00");
	     objeto.addProperty("Hora de fim da aula", "11:00:00");
	     objeto.addProperty("Data da aula", "18/04/2023");
	     objeto.addProperty("Lotação da sala", 100);
	     objeto.addProperty("Sala atribuída à aula", "Sala 1");	
	}
	
	 /*
	 @Test
	 public void testValidar_Curso_Vazio() {
	     // Cria um objeto JsonObject com o campo "Curso" vazio
	     JsonObject objeto = new JsonObject();
	     objeto.addProperty("Curso", "");

	     // Chama o método a ser testado e verifica se retorna false
	     assertFalse(Validacao.validarDocumento(objeto));
	 }
	 
	 @Test
	    void testValidarDocumento() {
	       

	        Assertions.assertTrue(Validacao.validarDocumento(objeto));
	    }

	    @Test
	      void testCursoObrigatorio() {
	        objeto = new JsonObject();
	        objeto.addProperty("Curso", "");
	        objeto.addProperty("Unidade Curricular", "POO");
	        objeto.addProperty("Turma", "LEI-P");
	        objeto.addProperty("Dia da semana", "SEG");
	        objeto.addProperty("Inscritos no turno", 50);
	        objeto.addProperty("Hora de início da aula", "09:00:00");
	        objeto.addProperty("Hora de fim da aula", "11:00:00");
	        objeto.addProperty("Data da aula", "18/04/2023");
	        objeto.addProperty("Lotação da sala", 100);
	        objeto.addProperty("Sala atribuída à aula", "Sala 1");

	        Assertions.assertFalse(Validacao.validarDocumento(objeto));
	    }
	    */
	
	/**

	Testa a função de validação de curso.
	Verifica se a validação é bem-sucedida para um curso válido e se falha para um curso vazio.
	*/
	
    @Test
    void testValidarCurso() {
        assertTrue(Validacao.validarCurso("Engenharia de Software"));
        assertFalse(Validacao.validarCurso(""));
    }
    
    /**

    Testa a função de validação de unidade curricular.
    Verifica se a validação é bem-sucedida para uma UC válida e se falha para uma UC vazia.
    */
    
    @Test
    void testValidarUC() {
        assertTrue(Validacao.validarUC("Programação Orientada a Objetos"));
        assertFalse(Validacao.validarUC(""));
    }
    
    /**

    Testa a função de validação de turno.
    Verifica se a validação é bem-sucedida para um turno válido e se falha para um turno vazio.
    */

    @Test
    void testValidarTurno() {
        assertTrue(Validacao.validarTurno("Manhã"));
        assertFalse(Validacao.validarTurno(""));
    }

    
    /**

    Testa a função de validação de turma.
    Verifica se a validação é bem-sucedida para uma turma válida e se falha para uma turma vazia.
    */
    
    @Test
    void testValidarTurma() {
        assertTrue(Validacao.validarTurma("A123"));
        assertFalse(Validacao.validarTurma(""));
    }
    
    /**

    Testa a função de validação de dia da semana.
    Verifica se a validação é bem-sucedida para um dia da semana válido e se falha para um dia da semana vazio.
    */
    
    @Test
    void testValidarDiadaSemana() {
        assertTrue(Validacao.validarDiadaSemana("SEG"));
        assertFalse(Validacao.validarDiadaSemana(""));
    }

    /**

    Testa a função de validação de número de inscritos no turno.
    Verifica se a validação é bem-sucedida para um número válido de inscritos e se falha para um número zero de inscritos.
    */
   
    
    @Test
    void testValidarInscritosNoTurno() {
        assertTrue(Validacao.validarInscritosNoTurno(5));
        assertFalse(Validacao.validarInscritosNoTurno(0));
    }

    /**

    Testa a função de validação de hora de início de aula.
    Verifica se a validação é bem-sucedida para uma hora de início válida e se falha para uma hora de início nula.
    */
    
    @Test
    void testValidarHoraInicioAula() {
        assertTrue(Validacao.validarHoraInicioAula(LocalTime.of(8, 30, 0)));
        assertFalse(Validacao.validarHoraInicioAula(null));
    }
    
    /**

    Testa a função de validação de hora de fim de aula.
    Verifica se a validação é bem-sucedida para uma hora de fim válida e se falha para uma hora de fim nula.
    */

    @Test
    void testValidarHoraFimAula() {
    	assertTrue(Validacao.validarHoraFim(LocalTime.of(8, 30, 0)));
        assertFalse(Validacao.validarHoraFim(null));
    }

    @Test
	void testValidarDataAula() {
		LocalDate dataAulaValida = LocalDate.of(2023, 4, 19);
		Validacao validarDataAula;
		assertTrue(Validacao.validarDataAula(dataAulaValida));
		
		LocalDate dataAulaInvalida1 = null;
		assertFalse(Validacao.validarDataAula(dataAulaInvalida1));
		
		LocalDate dataAulaInvalida2 = LocalDate.of(2023, 4, 32);
		assertFalse(Validacao.validarDataAula(dataAulaInvalida2));
		
		LocalDate dataAulaInvalida3 = LocalDate.of(2023, 13, 19);
		assertFalse(Validacao.validarDataAula(dataAulaInvalida3));
		
		LocalDate dataAulaInvalida4 = LocalDate.of(-1, 4, 19);
		assertFalse(Validacao.validarDataAula(dataAulaInvalida4));
	}



	 

   private static  final CSVFormat format = CSVFormat.EXCEL
    .withHeader() // This causes the parser to read the first record and use its values as column
                  // names
    .withSkipHeaderRecord(true)
    .withDelimiter(';');
    

    /**
    *Testa se um arquivo CSV fornecido é válido.
    *Este teste usa um arquivo CSV válido como entrada e espera que o método de validação retorne true.
    *@throws RuntimeException se ocorrer uma exceção de E/S durante o processamento do arquivo CSV
    *@return true se o arquivo CSV for válido, false caso contrário
    */
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
    
    /**
*Testa se um arquivo CSV fornecido possui um cabeçalho alterado, ou seja, se alguma coluna do cabeçalho foi renomeada.
*Este teste usa um arquivo CSV com cabeçalho alterado como entrada e espera que o método de validação retorne false.
*@throws RuntimeException se ocorrer uma exceção de E/S durante o processamento do arquivo CSV
*@return true se o arquivo CSV não tiver um cabeçalho alterado, false caso contrário
*/

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

    /**
    *Testa se um arquivo CSV fornecido possui um cabeçalho com colunas a mais do que o esperado.
    *Este teste usa um arquivo CSV com cabeçalho excessivo como entrada e espera que o método de validação retorne false.
    *@throws RuntimeException se ocorrer uma exceção de E/S durante o processamento do arquivo CSV
    *@return true se o arquivo CSV tiver um cabeçalho completo, false caso contrário
    */
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
    /**
*Testa se um arquivo CSV fornecido possui um cabeçalho com colunas a menos do que o esperado.
*Este teste usa um arquivo CSV com cabeçalho incompleto como entrada e espera que o método de validação retorne false.
*@throws RuntimeException se ocorrer uma exceção de E/S durante o processamento do arquivo CSV
*@return true se o arquivo CSV tiver um cabeçalho completo, false caso contrário
*/
    @Test
    public void validarCSVComHeaderComColunasAMenos() {
        CSVParser headerComRowsAmenos = null;
           try {
               File csvData7 = new File("Conjunto de teste/Header com colunas a menos.csv");
               headerComRowsAmenos = CSVParser.parse(csvData7, Charset.defaultCharset(), format);
           } catch (IOException e) {
               throw new RuntimeException(e);
           }
           assertFalse(Validacao.validarDocumento(headerComRowsAmenos));
    }
/**
*Testa se um arquivo CSV fornecido possui registros com campos vazios.
*Este teste usa um arquivo CSV com pelo menos um registro contendo um campo vazio como entrada e espera que o método de validação retorne false.
*@throws RuntimeException se ocorrer uma exceção de E/S durante o processamento do arquivo CSV
*@return true se o arquivo CSV não tiver registros com campos vazios, false caso contrário
*/

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

    /**
*Testa se um arquivo CSV fornecido possui registros com campos numéricos negativos.
*Este teste usa um arquivo CSV com pelo menos um registro contendo um campo numérico negativo como entrada e espera que o método de validação retorne false.
*@throws RuntimeException se ocorrer uma exceção de E/S durante o processamento do arquivo CSV
*@return true se o arquivo CSV não tiver registros com campos numéricos negativos, false caso contrário
*/
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