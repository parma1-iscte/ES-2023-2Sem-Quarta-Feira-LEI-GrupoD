import java.io.IOException;

import com.google.gson.JsonObject;
/**
 * 
 * @author Carlos Henriques
 *  Desenvolver uma função que valida documentos de um ficheiro JSON (se têm o formato correto) 
 *  (3 horas)
 */

public class Validacao{

	public static boolean validarDocumento(JsonObject objeto){	
		 if (objeto.get("Curso").toString().isEmpty()){
		        //throw new RuntimeException("Curso é de preenchimento obrigatório");
			 return false;
		    }
		    
		    if(objeto.get("Unidade Curricular").toString().isEmpty()){
		    	//throw new RuntimeException("A UC é de preenchimento obrigatório");
		    	return false;
		    }

		    if(objeto.get("Turma").toString().isEmpty()){
		    	//throw new RuntimeException("Cada aula tem de ter uma turma associada e pode conter letras/números");
		    	return false;
		    }

		 
		    if(objeto.get("Dia da semana").toString().isEmpty()){
		    	//throw new RuntimeException("O dia da semana é de preenchimento obrigatório e tem de estar no formato (\"SEG,TER,QUA,QUI,SEX,SAB,DOM\") ");
		    	return false;
		    }
		   
		    if(Integer.parseInt(objeto.get("Inscritos no turno").toString()) <= 0) { // podemos escolher o nº mínimo de alunos que é necessário para criar a aula???
		        //throw new RuntimeException("Nº de inscritos inválido");
		    	return false;
		    }
		   
		    String[] hora_inicio_aula = objeto.get("Hora de início da aula").toString().split(":");
		    int hora_inicio = Integer.parseInt(hora_inicio_aula[0]);
		    int minutos_inicio = Integer.parseInt(hora_inicio_aula[1]);
		    int segundos_inicio = Integer.parseInt(hora_inicio_aula[2]);
		    
		    if(objeto.get("Hora de início da aula").toString().isEmpty() || !(hora_inicio>=0 && hora_inicio<=23) || !(minutos_inicio>=0 && minutos_inicio<=59) || !(segundos_inicio>=0 && segundos_inicio<=59) ) {
		            //throw new RuntimeException("A hora de início da aula é de preenchimento obrigatório e tem de estar no formato \"(hh:mm:ss)\", por exemplo, \"(12:59:06)\" ");
		    	return false;
		    }
		    
		    String[] hora_fim_aula = objeto.get("Hora de fim da aula").toString().split(":");
		    int hora_fim = Integer.parseInt(hora_inicio_aula[0]);
		    int minutos_fim = Integer.parseInt(hora_inicio_aula[1]);
		    int segundos_fim = Integer.parseInt(hora_inicio_aula[2]);
		    if(objeto.get("Hora de fim da aula").toString().isEmpty() || !(hora_fim>=0 && hora_fim<=23) || !(minutos_fim>=0 && minutos_fim<=59) || !(segundos_fim>=0 && segundos_fim<=59) ) {
	            //throw new RuntimeException("A hora de início da aula é de preenchimento obrigatório e tem de estar no formato \"(hh:mm:ss)\", por exemplo, \"(12:59:06)\" ");
		    	return false;
		    }
		    
		    String[] data_aula = objeto.get("Data da aula").toString().split("/");
		    int dia_aula = Integer.parseInt(data_aula[0]);
		    int mes_aula = Integer.parseInt(data_aula[1]);
		    int ano_aula = Integer.parseInt(data_aula[2]);
		    if(objeto.get("Data da aula").toString().isEmpty() || !(dia_aula>=1 && dia_aula<=31) || !(mes_aula>=1 && mes_aula<=12) || !(ano_aula>=0 && ano_aula<=2023) ) {
	            //throw new RuntimeException("A hora de início da aula é de preenchimento obrigatório e tem de estar no formato \"(dia/mes/ano)\", por exemplo, \"(12/12/2023)\" ");
		    	return false;
		    }
		    
		    String[]sala_atribuida = objeto.get("Sala atribuída à aula").toString().split(".");
		    String edificio_sala = sala_atribuida[0];
		    int sala = Integer.parseInt(sala_atribuida[1]);
		    if( !edificio_sala.startsWith("C") || !edificio_sala.startsWith("AA") || !edificio_sala.startsWith("D") || !edificio_sala.startsWith("Auditório") || !edificio_sala.startsWith("B") || !(sala>=0 && sala<=12)){
		    	//throw new RuntimeException("Sala com formato incorreto, o formato tem de ser por exemplo \"C5.06\" ");
		    	return false;
		    }
		    
		    if(Integer.parseInt(objeto.get("Lotação da sala").toString()) <0 && Integer.parseInt(objeto.get("Lotação da sala").toString())> 250){
		    	//throw new RuntimeException("lotação inválida");		    	
		    	return false;
		    }
		   return true;
	}
		    
}
	

