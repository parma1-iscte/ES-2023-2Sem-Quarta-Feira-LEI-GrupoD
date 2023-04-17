package Tests;

import org.junit.jupiter.api.Test;

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
        assertTrue(validarCsvLine(list.get(0)));
        assertTrue(validarCsvLine(list.get(1)));
        assertFalse(validarCsvLine(list.get(2)));
        assertFalse(validarCsvLine(list.get(3)));
        assertFalse(validarCsvLine(list.get(4)));

    }
 

}