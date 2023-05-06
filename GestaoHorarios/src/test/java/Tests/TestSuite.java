package Tests;


import org.junit.runner.RunWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;

@RunWith(JUnitPlatform.class)
@SelectClasses({AulaTest.class, HorarioTest.class, ValidacaoTest.class})
public class TestSuite {
    // Esta classe não precisa ter nenhum método ou conteúdo adicional
}

