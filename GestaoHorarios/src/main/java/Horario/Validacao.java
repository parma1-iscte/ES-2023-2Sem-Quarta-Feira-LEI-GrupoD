package Horario;

import java.io.*;
import java.util.List;

import java.nio.charset.Charset;


import java.time.LocalDate;
import java.time.LocalTime;

import java.io.IOException;
import org.apache.commons.csv.*;

import com.google.gson.JsonObject;

/**
 * 
 * @author ES-2023-2Sem-Quarta-Feira-LEI-GrupoD
 *         Versão 1.0
 */

public class Validacao {

    private static final List<String> colunasDoCSV = List.of("Curso",
            "Unidade Curricular", "Turno", "Turma", "Inscritos no turno", "Dia da semana", "Hora início da aula",
            "Hora fim da aula",
            "Data da aula", "Lotação da sala", "Sala atribuída à aula");

    /**
     * Verifica se o curso informado foi preenchido.
     * @param curso a ser validado.
	 * @return true se o curso foi preenchido, false caso contrário.
     * 
     */
    public static boolean validarCurso(String curso) {
       
        		if(curso.isEmpty()) {
            // throw new RuntimeException("Curso é de preenchimento obrigatório");
        			System.out.println("Curso é de preenchimento obrigatório");
        			return false;  
        		}
        	return true;
   }
   
    /**
     * Verifica se a UC informado foi preenchido.
     * @param UC a ser validada.
	 * @return true se a UC foi preenchida, false caso contrário.
     * 
     */
   public static boolean validarUC(String uc) {
        if(uc.isEmpty()) {
            // throw new RuntimeException("A UC é de preenchimento obrigatório");
        			System.out.println("A UC é de preenchimento obrigatório");
        			return false;
        }
        return true;
   }
   
   /**
    * Verifica se o turno informado foi preenchido.
    * @param turno a ser validado.
	* @return true se o turno foi preenchido, false caso contrário.
    * 
    */
   public static boolean validarTurno(String turno) {
       
        		if(turno.isEmpty()) {
            // throw new RuntimeException("A UC é de preenchimento obrigatório");
        			System.out.println("O turno é de preenchimento obrigatório");
        			return false;
        		}
        	return true;
   }
   
   /**
    * Verifica se a turma informado foi preenchido.
    * @param turma a ser validada.
	* @return true se a turma foi preenchida, false caso contrário.
    * 
    */
   public static boolean validarTurma(String turma) {
        		if(turma.isEmpty()) {
            // throw new RuntimeException("Cada aula tem de ter uma turma associada e pode
            // conter letras/números");
        			System.out.println("Cada aula tem de ter uma turma associada e pode conter letras/números");
        			return false;
        		}
        		return true;
    }
   
   /**
    * Verifica se o dia da semana foi preenchido.
    * @param dia da semana a ser validada.
	* @return true se a dia da semana foi preenchido, false caso contrário.
    * 
    */
   
   public static boolean validarDiadaSemana(String diaDaSemana) {

        		if(diaDaSemana.isEmpty()) {
            // throw new RuntimeException("O dia da semana é de preenchimento obrigatório e
            // tem de estar no formato (\"SEG,TER,QUA,QUI,SEX,SAB,DOM\") ");
        			System.out.println(
                    "O dia da semana é de preenchimento obrigatório e tem de estar no formato (\"SEG,TER,QUA,QUI,SEX,SAB,DOM\") ");
        			return false;
        		}
        		return true;
        		
    }
   /**
   * Verifica se o número de inscritos informado é válido para criar uma aula.
   * @param inscritosNoTurno número de inscritos para validar.
   * @return true se o número de inscritos for maior do que zero, false caso contrário.
   */
   
    public static boolean validarInscritosNoTurno(int inscritosNoTurno) {

        if (inscritosNoTurno <= 0) { // podemos escolher o nº mínimo de
                                                                                  // alunos que é necessário para criar
                                                                                  // a aula???
            // throw new RuntimeException("Nº de inscritos inválido");
            System.out.println("Nº de inscritos inválido");
            return false;
        }
        return true;
    }
    
    /**
     * Verifica se a hora de início da aula informada é válida.
     * @param hora_inicio hora de início da aula para validar.
     * @return true se a hora de início da aula for válida, false caso contrário.
     */
    public static boolean validarHoraInicioAula(LocalTime hora_inicio) {

        //String[] hora_inicio_aula = objeto.get("Hora de início da aula").toString().split(":");
        //int hora_inicio = Integer.parseInt(hora_inicio_aula[0]);
//        int minutos_inicio = Integer.parseInt(hora_inicio_aula[1]);
//        int segundos_inicio = Integer.parseInt(hora_inicio_aula[2]);

//        if (objeto.get("Hora de início da aula").toString().isEmpty() || !(hora_inicio >= 0 && hora_inicio <= 23)
//                || !(minutos_inicio >= 0 && minutos_inicio <= 59) || !(segundos_inicio >= 0 && segundos_inicio <= 59)) {
    	if(hora_inicio== null || !(hora_inicio.getHour()>= 0 && hora_inicio.getHour() <=23) ||
    			!(hora_inicio.getMinute()>=0 && hora_inicio.getMinute()<=59) || !(hora_inicio.getSecond()>=0 && hora_inicio.getSecond()<=59)	){   
    	
    	// throw new RuntimeException("A hora de início da aula é de preenchimento
            // obrigatório e tem de estar no formato \"(hh:mm:ss)\", por exemplo,
            // \"(12:59:06)\" ");
            System.out.println(
                    "A hora de início da aula é de preenchimento obrigatório e tem de estar no formato \"(hh:mm:ss)\", por exemplo, \"(12:59:06)\" ");
            return false;
        }
    	return true;
    }

//        String[] hora_fim_aula = objeto.get("Hora de fim da aula").toString().split(":");
//        int hora_fim = Integer.parseInt(hora_inicio_aula[0]);
//        int minutos_fim = Integer.parseInt(hora_inicio_aula[1]);
//        int segundos_fim = Integer.parseInt(hora_inicio_aula[2]);
//        if (objeto.get("Hora de fim da aula").toString().isEmpty() || !(hora_fim >= 0 && hora_fim <= 23)
//                || !(minutos_fim >= 0 && minutos_fim <= 59) || !(segundos_fim >= 0 && segundos_fim <= 59)) {
            // throw new RuntimeException("A hora de início da aula é de preenchimento
            // obrigatório e tem de estar no formato \"(hh:mm:ss)\", por exemplo,
            // \"(12:59:06)\" ");
    
    /**
     * Verifica se a hora de fim da aula informada é válida.
     * @param hora_inicio hora de fima da aula para validar.
     * @return true se a hora de início da aula for válida, false caso contrário.
     */
   public static boolean validarHoraFim(LocalTime hora_fim) { 
    if(hora_fim== null || !(hora_fim.getHour()>= 0 && hora_fim.getHour() <=23) ||
			!(hora_fim.getMinute()>=0 && hora_fim.getMinute()<=59) || !(hora_fim.getSecond()>=0 && hora_fim.getSecond()<=59)	){   
	
            System.out.println(
                    "A hora de fim da aula é de preenchimento obrigatório e tem de estar no formato \"(hh:mm:ss)\", por exemplo, \"(12:59:06)\" ");
            return false;
        }
    return true;
   }

//        String[] data_aula = objeto.get("Data da aula").toString().split("/");
//        int dia_aula = Integer.parseInt(data_aula[0]);
//        int mes_aula = Integer.parseInt(data_aula[1]);
//        int ano_aula = Integer.parseInt(data_aula[2]);
//        if (objeto.get("Data da aula").toString().isEmpty() || !(dia_aula >= 1 && dia_aula <= 31)
//                || !(mes_aula >= 1 && mes_aula <= 12) || !(ano_aula >= 0 && ano_aula <= 2023)) {
            // throw new RuntimeException("A hora de início da aula é de preenchimento
            // obrigatório e tem de estar no formato \"(dia/mes/ano)\", por exemplo,
            // \"(12/12/2023)\" ");
   
   
   /** 
    * Verifica se a data da aula informada é válida.
    * @param dataAula data da aula para validar.
	* @return true se a data da aula for válida, false caso contrário.
    *
    */
   public static boolean validarDataAula(LocalDate dataAula) {
	   if(dataAula == null || !(dataAula.getDayOfMonth()>=1 &&dataAula.getDayOfMonth()<=31) || !(dataAula.getMonthValue()>=1 && dataAula.getMonthValue()<=12) || !(dataAula.getYear()>=0)) {
            System.out.println(
                    "A hora de início da aula é de preenchimento obrigatório e tem de estar no formato \"(dia/mes/ano)\", por exemplo, \"(12/12/2023)\" ");
            return false;
	   }
	   return true;
    }
   
   /**
    * Verifica se a sala atribuída informada tem o formato correto.
    * @param salaAtribuida sala atribuída a ser validada.
    * @return true se a sala atribuída tiver o formato correto, false caso contrário.
    */
   public static boolean validarSalaAtribuida(String salaAtribuida) {
//        String[] sala_atribuida = salaAtribuida.split(".");
//        String edificio_sala = sala_atribuida[0];
//        int sala = Integer.parseInt(sala_atribuida[1]);
//        if (!edificio_sala.startsWith("C") || !edificio_sala.startsWith("AA") || !edificio_sala.startsWith("D")
//                || !edificio_sala.startsWith("Auditório") || !edificio_sala.startsWith("B")
//                || !(sala >= 0 && sala <= 12)) {
	   if(salaAtribuida ==null) {
            // throw new RuntimeException("Sala com formato incorreto, o formato tem de ser
            // por exemplo \"C5.06\" ");
            System.out.println("Sala com formato incorreto, o formato tem de ser por exemplo \"C5.06\" ");
            return false;
        }
        return true;
   }
  
   /**

   Verifica se a lotação informada é válida.
   @param lotacao lotação a ser validada.
   @return true se a lotação for maior ou igual a zero e menor ou igual a 250, false caso contrário.
   @throws RuntimeException se a lotação for inválida (não é lançado nesta implementação).
   */
   
   
   public static boolean validarLotacao(int lotacao) {
   

        if (lotacao < 0 && lotacao > 250) {
            // throw new RuntimeException("lotação inválida");
            System.out.println("lotação inválida");
            return false;
        }
        return true;
   	}

    public static boolean validarDocumento(CSVParser parser) {
        if (!(parser.getHeaderNames().containsAll(colunasDoCSV) && colunasDoCSV.containsAll(parser.getHeaderNames())))
            /**
             * O interessante deste if é que garante antes de continuar a execução que o
             * ficheiro csv que importamos
             * têm exatamente as colunas que é suposto ter (que são as guardadas na lista
             * colunasDoCSV);
             * sem colunas a mais, a menos ou diferentes independente da ordem das colunas
             * no ficheiro.
             * Retorna falso e termina a execução da função caso isso não seja garantido.
             */
            return false;

        List<CSVRecord> list = parser.getRecords();
        for (CSVRecord record : list) {
            
            if (!areAllFieldsNotEmptyStrings(record))
                return false;
            if (!areAllFieldsNonNull(record))
                return false;


            
            boolean areTheIntsValid = Integer.parseInt(record.get("Inscritos no turno")) >= 0
                    && Integer.parseInt(record.get("Lotação da sala")) >= 0;

            if (!areTheIntsValid)
                return false;
        }
        return true;
    }

    private static boolean areAllFieldsNotEmptyStrings(CSVRecord record) {
        return !record.get("Curso").isEmpty() &&
                !record.get("Unidade Curricular").isEmpty() &&
                !record.get("Turno").isEmpty() && !record.get("Turma").isEmpty() &&
                !record.get("Inscritos no turno").isEmpty()
                && !record.get("Dia da semana").isEmpty() && !record.get("Hora início da aula").isEmpty()
                && !record.get("Hora fim da aula").isEmpty() && !record.get("Data da aula").isEmpty() &&
                !record.get("Lotação da sala").isEmpty() && !record.get("Sala atribuída à aula").isEmpty();
    }

    private static boolean areAllFieldsNonNull(CSVRecord record) {
        return record.get("Curso") != null && record.get("Unidade Curricular") != null &&
                record.get("Turno") != null && record.get("Turma") != null && record.get("Inscritos no turno") != null
                && record.get("Dia da semana") != null && record.get("Hora início da aula") != null
                && record.get("Hora fim da aula") != null && record.get("Data da aula") != null &&
                record.get("Lotação da sala") != null && record.get("Sala atribuída à aula") != null;
    }
}
