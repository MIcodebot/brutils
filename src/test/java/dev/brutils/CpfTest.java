package dev.brutils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CpfTest {

    @ParameterizedTest
    @ValueSource(strings = {"529.982.247-25", "52998224725", "111.444.777-35", " 529.982.247-25 "})
    void aceitaCpfsValidos(String cpf) {
        assertTrue(Cpf.isValid(cpf));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
        "529.982.247-24",   // DV errado
        "529.982.247-52",   // DVs trocados
        "111.111.111-11",   // todos iguais
        "00000000000",
        "5299822472",       // 10 dígitos
        "529982247250",     // 12 dígitos
        "529.982.247-2A",   // letra
        "abc"
    })
    void rejeitaCpfsInvalidos(String cpf) {
        assertFalse(Cpf.isValid(cpf));
    }

    @Test
    void formata() {
        assertEquals("529.982.247-25", Cpf.format("52998224725"));
        assertEquals("529.982.247-25", Cpf.format("529.982.247-25"));
    }

    @Test
    void formatarComTamanhoErradoLancaExcecao() {
        assertThrows(IllegalArgumentException.class, () -> Cpf.format("123"));
        assertThrows(IllegalArgumentException.class, () -> Cpf.format(null));
    }

    @Test
    void removeMascara() {
        assertEquals("52998224725", Cpf.unformat("529.982.247-25"));
    }

    @RepeatedTest(200)
    void geraCpfValido() {
        String cpf = Cpf.generate();
        assertEquals(11, cpf.length());
        assertTrue(Cpf.isValid(cpf), cpf);
    }
}
