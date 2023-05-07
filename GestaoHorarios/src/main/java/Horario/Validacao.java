package Horario;


import java.util.HashSet;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

import org.apache.commons.csv.*;


/**
 * @author ES-2023-2Sem-Quarta-Feira-LEI-GrupoD
 *         Versão 1.0
 */

public class Validacao {

    private Validacao() {
    }

    public static final String CURSO = "Curso";
    public static final String UNIDADE_CURRICULAR = "Unidade Curricular";
    public static final String TURMA = "Turma";
    public static final String HORA_FIM_DA_AULA = "Hora fim da aula";
    public static final String TURNO = "Turno";
    public static final String INSCRITOS_NO_TURNO = "Inscritos no turno";
    public static final String DIA_DA_SEMANA = "Dia da semana";
    public static final String HORA_INICIO_DA_AULA = "Hora início da aula";
    public static final String LOTACAO_DA_SALA = "Lotação da sala";
    public static final String DATA_DA_AULA = "Data da aula";
    public static final String SALA_ATRIBUIDA_A_AULA = "Sala atribuída à aula";
    private static final List<String> colunasDoCSV = List.of(CURSO,
            UNIDADE_CURRICULAR, TURNO, TURMA, INSCRITOS_NO_TURNO, DIA_DA_SEMANA, HORA_INICIO_DA_AULA,
            HORA_FIM_DA_AULA,
            DATA_DA_AULA, LOTACAO_DA_SALA, SALA_ATRIBUIDA_A_AULA);

    /**
     * Verifica se o curso informado foi preenchido.
     * @param curso a ser validado.
	 * @return true se o curso foi preenchido, false caso contrário.
     * 
     */
    public static boolean validarCurso(String curso) {
        	return !curso.isEmpty();
   }
   
    /**
     * Verifica se a UC informado foi preenchido.
     * @param UC a ser validada.
	 * @return true se a UC foi preenchida, false caso contrário.
     * 
     */
   public static boolean validarUC(String uc) {
        return !uc.isEmpty();
   }
   
   /**
    * Verifica se o turno informado foi preenchido.
    * @param turno a ser validado.
	* @return true se o turno foi preenchido, false caso contrário.
    * 
    */
   public static boolean validarTurno(String turno) {

        	return !turno.isEmpty();
   }
   
   /**
    * Verifica se a turma informado foi preenchido.
    * @param turma a ser validada.
	* @return true se a turma foi preenchida, false caso contrário.
    * 
    */
   public static boolean validarTurma(String turma) {
       return !turma.isEmpty();
    }
   
   /**
    * Verifica se o dia da semana foi preenchido.
    * @param dia da semana a ser validada.
	* @return true se a dia da semana foi preenchido, false caso contrário.
    * 
    */
   
   public static boolean validarDiadaSemana(String diaDaSemana) {
        return !diaDaSemana.isEmpty();

    }
   /**
   * Verifica se o número de inscritos informado é válido para criar uma aula.
   * @param inscritosNoTurno número de inscritos para validar.
   * @return true se o número de inscritos for maior do que zero, false caso contrário.
   */
   
    public static boolean validarInscritosNoTurno(int inscritosNoTurno) {
        return inscritosNoTurno >= 0;
    }
    
    /**
     * Verifica se a hora de início da aula informada é válida.
     * @param hora_inicio hora de início da aula para validar.
     * @return true se a hora de início da aula for válida, false caso contrário.
     */
    public static boolean validarHoraInicioAula(LocalTime horaInicio) {
    	return horaInicio != null;
    }
    /**
     * Verifica se a hora de fim da aula informada é válida.
     * @param hora_inicio hora de fima da aula para validar.
     * @return true se a hora de início da aula for válida, false caso contrário.
     */
   public static boolean validarHoraFim(LocalTime horaFim) {
    return horaFim != null;
   }
   
   /** 
    * Verifica se a data da aula informada é válida.
    * @param dataAula data da aula para validar.
	* @return true se a data da aula for válida, false caso contrário.
    *
    */
   public static boolean validarDataAula(LocalDate dataAula) {
	   return !(dataAula == null || (dataAula.getYear()< 0));
    }
   
   /**
    * Verifica se a sala atribuída informada tem o formato correto.
    * @param salaAtribuida sala atribuída a ser validada.
    * @return true se a sala atribuída tiver o formato correto, false caso contrário.
    */
   public static boolean validarSalaAtribuida(String salaAtribuida) {
        return salaAtribuida != null;
   }
  
   /**

   Verifica se a lotação informada é válida.
   @param lotacao lotação a ser validada.
   @return true se a lotação for maior ou igual a zero e menor ou igual a 250, false caso contrário.
   */
   
   
   public static boolean validarLotacao(int lotacao) {
        return lotacao >= 0 && lotacao <= 250;
   	}

    /**
    *Método responsável por validar um arquivo CSV contendo informações de documentos, verificando se possui todas as colunas requeridas e não mais, se todos os campos são não nulos e não vazios, e se os campos de valores inteiros são maiores ou iguais a zero.
    *@param parser O objeto CSVParser contendo as informações do arquivo CSV a ser validado.
    *@return true se o arquivo CSV é válido, false caso contrário.
    */

    public static boolean validarDocumento(CSVParser parser) {
        if (!(new HashSet<>(parser.getHeaderNames()).equals(new HashSet<>(colunasDoCSV))  ))
            return false;


        List<CSVRecord> list = parser.getRecords();
        for (CSVRecord csvRecord : list) {
            
            if (!areAllFieldsNotEmptyStrings(csvRecord))
                return false;
            if (!areAllFieldsNonNull(csvRecord))
                return false;


            
            boolean areTheIntsValid = Integer.parseInt(csvRecord.get(INSCRITOS_NO_TURNO)) >= 0
                    && Integer.parseInt(csvRecord.get(LOTACAO_DA_SALA)) >= 0;

            if (!areTheIntsValid)
                return false;
        }
        return true;
    }

    /**
    *Verifica se todos os campos do registro CSV passado como parâmetro não são vazios.
    *@param csvRecord O registro CSV a ser verificado.
    *@return true se todos os campos não forem vazios, false caso contrário.
    */

    private static boolean areAllFieldsNotEmptyStrings(CSVRecord csvRecord) {

        return !csvRecord.get(CURSO).isEmpty() &&
                !csvRecord.get(UNIDADE_CURRICULAR).isEmpty() &&
                !csvRecord.get(TURNO).isEmpty() && !csvRecord.get(TURMA).isEmpty() &&
                !csvRecord.get(INSCRITOS_NO_TURNO).isEmpty()
                && !csvRecord.get(DIA_DA_SEMANA).isEmpty() && !csvRecord.get(HORA_INICIO_DA_AULA).isEmpty()
                && !csvRecord.get(HORA_FIM_DA_AULA).isEmpty() && !csvRecord.get(DATA_DA_AULA).isEmpty() &&
                !csvRecord.get(LOTACAO_DA_SALA).isEmpty() && !csvRecord.get(SALA_ATRIBUIDA_A_AULA).isEmpty();
    }

     /**
    /**
    *Verifica se todos os campos do registro CSV passado como parâmetro não são nulos.
    *@param csvRecord O registro CSV a ser verificado.
    *@return true se todos os campos não forem nulos, false caso contrário.
    */
    private static boolean areAllFieldsNonNull(CSVRecord csvRecord) {
        return csvRecord.get(CURSO) != null && csvRecord.get(UNIDADE_CURRICULAR) != null &&
                csvRecord.get(TURNO) != null && csvRecord.get(TURMA) != null && csvRecord.get(INSCRITOS_NO_TURNO) != null
                && csvRecord.get(DIA_DA_SEMANA) != null && csvRecord.get(HORA_INICIO_DA_AULA) != null
                && csvRecord.get(HORA_FIM_DA_AULA) != null && csvRecord.get(DATA_DA_AULA) != null &&
                csvRecord.get(LOTACAO_DA_SALA) != null && csvRecord.get(SALA_ATRIBUIDA_A_AULA) != null;
    }
}
