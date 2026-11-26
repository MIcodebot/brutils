package dev.brutils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CepTest {

    @ParameterizedTest
    @ValueSource(strings = {"14400-000", "14400000", "01310-100"})
    void aceitaCepsValidos(String cep) {
        assertTrue(Cep.isValid(cep));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"1440-000", "144000000", "14400-00A", "00000-000", "14.400-000"})
    void rejeitaCepsInvalidos(String cep) {
        assertFalse(Cep.isValid(cep));
    }

    @Test
    void formata() {
        assertEquals("14400-000", Cep.format("14400000"));
    }

    @Test
    void formatarComTamanhoErradoLancaExcecao() {
        assertThrows(IllegalArgumentException.class, () -> Cep.format("123"));
    }

    @Test
    void removeMascara() {
        assertEquals("14400000", Cep.unformat("14400-000"));
    }
}
