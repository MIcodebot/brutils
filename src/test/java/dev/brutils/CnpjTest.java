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

class CnpjTest {

    @ParameterizedTest
    @ValueSource(strings = {
        "11.222.333/0001-81",
        "11222333000181",
        "12.ABC.345/01DE-35",
        "12ABC34501DE35",
        "12abc34501de35"
    })
    void aceitaCnpjsValidos(String cnpj) {
        assertTrue(Cnpj.isValid(cnpj));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
        "11.222.333/0001-82",   // DV errado
        "12.ABC.345/01DE-36",   // DV errado (alfanumérico)
        "12.ABC.345/01DE-3A",   // DV com letra
        "00.000.000/0000-00",   // todos iguais
        "1122233300018",        // 13 caracteres
        "11.222.333/0001-81!",  // caractere inválido
        "11.222.333/0001-8ç"
    })
    void rejeitaCnpjsInvalidos(String cnpj) {
        assertFalse(Cnpj.isValid(cnpj));
    }

    @Test
    void formata() {
        assertEquals("11.222.333/0001-81", Cnpj.format("11222333000181"));
        assertEquals("12.ABC.345/01DE-35", Cnpj.format("12abc34501de35"));
    }

    @Test
    void formatarComTamanhoErradoLancaExcecao() {
        assertThrows(IllegalArgumentException.class, () -> Cnpj.format("123"));
        assertThrows(IllegalArgumentException.class, () -> Cnpj.format(null));
    }

    @Test
    void removeMascaraEConverteParaMaiusculas() {
        assertEquals("12ABC34501DE35", Cnpj.unformat("12.abc.345/01de-35"));
    }

    @RepeatedTest(200)
    void geraCnpjNumericoValido() {
        String cnpj = Cnpj.generate();
        assertTrue(cnpj.matches("\\d{14}"), cnpj);
        assertTrue(Cnpj.isValid(cnpj), cnpj);
    }

    @RepeatedTest(200)
    void geraCnpjAlfanumericoValido() {
        String cnpj = Cnpj.generateAlphanumeric();
        assertTrue(cnpj.matches("[0-9A-Z]{12}\\d{2}"), cnpj);
        assertTrue(Cnpj.isValid(cnpj), cnpj);
    }
}
