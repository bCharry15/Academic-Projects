package co.edu.unicauca.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordUtilTest {

    @Test
    void hashearDebeSerDeterminista() {
        String password = "admin123";

        String hash1 = PasswordUtil.hashear(password);
        String hash2 = PasswordUtil.hashear(password);

        assertEquals(hash1, hash2);
    }

    @Test
    void hashesDiferentesParaPasswordsDistintas() {
        String hash1 = PasswordUtil.hashear("a");
        String hash2 = PasswordUtil.hashear("b");

        assertNotEquals(hash1, hash2);
    }

    @Test
    void verificarDebeFuncionarCorrectamente() {
        String password = "admin123";
        String hash = PasswordUtil.hashear(password);

        assertTrue(PasswordUtil.verificar(password, hash));
        assertFalse(PasswordUtil.verificar("incorrecta", hash));
    }
}
