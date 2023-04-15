package Tests;

import static org.junit.Assert.*;
import org.junit.Test;
import java.time.LocalTime;
import java.time.LocalDate;
import Horario.*;

public class AulaTest {

    @Test
    public void testIsSobrelotada() {
        Aula aula1 = new Aula("Curso1", "UC1", "Manhã", "Turma1", 30, "Segunda-feira",
            LocalTime.of(8, 0), LocalTime.of(10, 0), LocalDate.of(2023, 4, 17), "Sala1", 25);
        // Aula com 30 inscritos e capacidade da sala de 25
        assertTrue(aula1.isSobrelotada());
        
        Aula aula2 = new Aula("Curso2", "UC2", "Tarde", "Turma2", 15, "Terça-feira",
            LocalTime.of(14, 0), LocalTime.of(16, 0), LocalDate.of(2023, 4, 18), "Sala2", 20);
        // Aula com 15 inscritos e capacidade da sala de 20
        assertFalse(aula2.isSobrelotada());
    }

    @Test
    public void testIsSobreposta() {
        Aula aula1 = new Aula("Curso1", "UC1", "Manhã", "Turma1", 30, "Segunda-feira",
            LocalTime.of(8, 0), LocalTime.of(10, 0), LocalDate.of(2023, 4, 17), "Sala1", 25);
        
        Aula aula2 = new Aula("Curso2", "UC2", "Manhã", "Turma2", 25, "Segunda-feira",
            LocalTime.of(9, 0), LocalTime.of(11, 0), LocalDate.of(2023, 4, 17), "Sala2", 30);
        
        Aula aula3 = new Aula("Curso3", "UC3", "Tarde", "Turma3", 20, "Terça-feira",
            LocalTime.of(14, 0), LocalTime.of(16, 0), LocalDate.of(2023, 4, 18), "Sala3", 15);
        
        assertTrue(aula1.isSobreposta(aula2));
        assertFalse(aula1.isSobreposta(aula3));
    }

}
