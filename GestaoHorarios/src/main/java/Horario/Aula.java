package Horario;
import java.time.*;

import com.google.gson.annotations.SerializedName;

/**
 * 
 * @author ES-2023-2Sem-Quarta-Feira-LEI-GrupoD
 * Versão 1.0
 */

public class Aula {
	@SerializedName("Curso")
	private String curso;
	@SerializedName("Unidade Curricular")
	private String uc;
	@SerializedName("Turno")	
	private String turno;
	@SerializedName("Turma")
	private String turma;
	@SerializedName("Inscritos no Turno")
	private Integer inscritos;
	@SerializedName("Dia da semana")
	private String diaSemana;
	@SerializedName("Hora in]icio da aula")
	private LocalTime horaInicio;
	@SerializedName("Hora fim da aula")
	private LocalTime horaFim;
	@SerializedName("Data da aula")
	private LocalDate dia;
	@SerializedName("Sala atribuída à aula")
	private String sala;
	@SerializedName("Lotação da aula")
	private Integer lotacaoSala;

	/**

    Construtor da classe Aula.
    @param curso nome do curso da aula
    @param uc nome da unidade curricular da aula
    @param turno turno da aula
    @param turma turma da aula
    @param inscritos número de alunos inscritos na aula
    @param diaSemana dia da semana em que a aula ocorre
    @param horaInicio horário de início da aula
    @param horaFim horário de término da aula
    @param dia data em que a aula ocorre
    @param sala nome da sala em que a aula ocorre
    @param lotacaoSala lotação máxima da sala em que a aula ocorre
	 */


	public Aula(String curso, String uc, String turno, String turma, Integer inscritos, String diaSemana,
			LocalTime horaInicio, LocalTime horaFim, LocalDate dia, String sala, Integer lotacaoSala) {
		this.curso = curso;
		this.uc = uc;
		this.turno = turno;
		this.turma = turma;
		this.inscritos = inscritos;
		this.diaSemana = diaSemana;
		this.horaInicio = horaInicio;
		this.horaFim = horaFim;
		this.dia = dia;
		this.sala = sala;
		this.lotacaoSala = lotacaoSala;
	}

	public String getCurso() {
		return curso;
	}

	public String getUc() {
		return uc;
	}

	public String getTurno() {
		return turno;
	}

	public String getTurma() {
		return turma;
	}

	public Integer getInscritos() {
		return inscritos;
	}

	public String getDiaSemana() {
		return diaSemana;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public LocalTime getHoraFim() {
		return horaFim;
	}

	public LocalDate getDia() {
		return dia;
	}

	public String getSala() {
		return sala;
	}

	public Integer getLotacaoSala() {
		return lotacaoSala;
	}

	
	
	public boolean isSobrelotada() {
		return inscritos > lotacaoSala;
	}

	/**

    Verifica se duas aulas estão sobrepostas.
    @param aula a aula a ser comparada com a aula atual.
    @return true se as aulas estiverem sobrepostas, false caso contrário.
    */
	
	public boolean isSobreposta(Aula aula) {
		// Verificar se a aula atual começa antes ou no mesmo horário da outra aula
		boolean comecaAntes = horaInicio.isBefore(aula.getHoraInicio()) || horaInicio.equals(aula.getHoraInicio());

		// Verificar se a aula atual termina depois ou no mesmo horário da outra aula
		boolean terminaDepois = horaFim.isAfter(aula.getHoraFim()) || horaFim.equals(aula.getHoraFim());

		// Verificar se a aula atual começa depois do horário de início e antes do horário de término da outra aula
		boolean comecaNoMeio = horaInicio.isAfter(aula.getHoraInicio()) && horaInicio.isBefore(aula.getHoraFim());

		// Verificar se a outra aula começa depois do horário de início e antes do horário de término da aula atual
		boolean outraAulaComecaNoMeio = aula.getHoraInicio().isAfter(horaInicio) && aula.getHoraInicio().isBefore(horaFim);

		// Se a aula atual começa antes ou no mesmo horário e termina depois ou no mesmo horário da outra aula,
		// ou se a aula atual começa exatamente no horário de início da outra aula,
		// ou se a aula atual começa depois do horário de início e antes do horário de término da outra aula,
		// ou se a outra aula começa depois do horário de início e antes do horário de término da aula atual,
		// então as aulas estão sobrepostas
		return comecaAntes && terminaDepois || horaInicio.equals(aula.getHoraInicio()) || comecaNoMeio || outraAulaComecaNoMeio;
	}

	//criar hora - LocalTime h1 = LocalTime.of(horas,minutos,segundos)
	//criar data - LocalDate d1 = LocalDate.of(ano,mes,dia)
}
