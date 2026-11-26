package dev.brutils;

/**
 * Validação e formatação de CEP.
 *
 * <p>Valida apenas o formato (8 dígitos); não consulta se o CEP existe.</p>
 */
public final class Cep {

    private static final int LENGTH = 8;

    private Cep() {
    }

    /**
     * Retorna {@code true} se o CEP tiver formato válido ({@code 00000-000} ou {@code 00000000}).
     */
    public static boolean isValid(String cep) {
        if (cep == null) {
            return false;
        }
        String trimmed = cep.trim();
        return trimmed.matches("\\d{5}-?\\d{3}") && !trimmed.replace("-", "").equals("00000000");
    }

    /**
     * Formata como {@code 00000-000}.
     *
     * @throws IllegalArgumentException se não tiver 8 dígitos
     */
    public static String format(String cep) {
        String d = Strings.onlyDigits(Strings.requireNonNull(cep, "cep"));
        if (d.length() != LENGTH) {
            throw new IllegalArgumentException("CEP deve ter 8 dígitos: " + cep);
        }
        return d.substring(0, 5) + "-" + d.substring(5);
    }

    /**
     * Remove a máscara, devolvendo só os dígitos.
     */
    public static String unformat(String cep) {
        return Strings.onlyDigits(Strings.requireNonNull(cep, "cep"));
    }
}
