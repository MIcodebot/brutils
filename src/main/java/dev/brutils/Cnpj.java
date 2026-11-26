package dev.brutils;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

/**
 * Validação, formatação e geração de CNPJ, numérico e alfanumérico.
 *
 * <p>Desde 31 de julho de 2026 a Receita Federal passou a emitir CNPJs alfanuméricos:
 * as 12 primeiras posições podem conter letras (A–Z) e números, e os 2 dígitos
 * verificadores continuam numéricos. O cálculo usa o valor ASCII de cada caractere
 * menos 48, o que mantém compatibilidade total com os CNPJs só numéricos.</p>
 */
public final class Cnpj {

    private static final int LENGTH = 14;
    private static final int[] WEIGHTS_1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] WEIGHTS_2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final String ALPHANUMERIC = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random RANDOM = new SecureRandom();

    private Cnpj() {
    }

    /**
     * Retorna {@code true} se o CNPJ (numérico ou alfanumérico) for válido.
     * Aceita com ou sem máscara e com letras minúsculas.
     */
    public static boolean isValid(String cnpj) {
        if (cnpj == null || !cnpj.matches("[0-9A-Za-z./\\-\\s]+")) {
            return false;
        }
        String clean = unformat(cnpj);
        if (!clean.matches("[0-9A-Z]{12}\\d{2}") || Strings.allSameChar(clean)) {
            return false;
        }
        String base = clean.substring(0, 12);
        return clean.substring(12).equals(checkDigits(base));
    }

    /**
     * Formata como {@code AA.AAA.AAA/AAAA-00}.
     *
     * @throws IllegalArgumentException se não tiver 14 caracteres alfanuméricos
     */
    public static String format(String cnpj) {
        String c = unformat(Strings.requireNonNull(cnpj, "cnpj"));
        if (c.length() != LENGTH) {
            throw new IllegalArgumentException("CNPJ deve ter 14 caracteres: " + cnpj);
        }
        return c.substring(0, 2) + "." + c.substring(2, 5) + "." + c.substring(5, 8)
                + "/" + c.substring(8, 12) + "-" + c.substring(12);
    }

    /**
     * Remove a máscara e converte para maiúsculas.
     */
    public static String unformat(String cnpj) {
        return Strings.requireNonNull(cnpj, "cnpj").replaceAll("[^0-9A-Za-z]", "").toUpperCase(Locale.ROOT);
    }

    /**
     * Gera um CNPJ numérico válido aleatório (só dígitos), com filial 0001.
     */
    public static String generate() {
        return generate("0123456789", 8);
    }

    /**
     * Gera um CNPJ alfanumérico válido aleatório (sem máscara).
     */
    public static String generateAlphanumeric() {
        return generate(ALPHANUMERIC, 12);
    }

    private static String generate(String alphabet, int randomLength) {
        String base;
        do {
            StringBuilder sb = new StringBuilder(12);
            for (int i = 0; i < randomLength; i++) {
                sb.append(alphabet.charAt(RANDOM.nextInt(alphabet.length())));
            }
            if (randomLength == 8) {
                sb.append("0001");
            }
            base = sb.toString();
        } while (Strings.allSameChar(base));
        return base + checkDigits(base);
    }

    /** Calcula os 2 dígitos verificadores para uma base de 12 caracteres. */
    static String checkDigits(String base) {
        int dv1 = digit(base, WEIGHTS_1);
        int dv2 = digit(base + dv1, WEIGHTS_2);
        return "" + dv1 + dv2;
    }

    private static int digit(String chars, int[] weights) {
        int sum = 0;
        for (int i = 0; i < weights.length; i++) {
            sum += (chars.charAt(i) - '0') * weights[i];
        }
        int rest = sum % 11;
        return rest < 2 ? 0 : 11 - rest;
    }
}
