package dev.brutils;

/**
 * Funções auxiliares internas.
 */
final class Strings {

    private Strings() {
    }

    static String onlyDigits(String value) {
        return value == null ? "" : value.replaceAll("\\D", "");
    }

    static boolean allSameChar(String value) {
        if (value.isEmpty()) {
            return false;
        }
        char first = value.charAt(0);
        for (int i = 1; i < value.length(); i++) {
            if (value.charAt(i) != first) {
                return false;
            }
        }
        return true;
    }

    static String requireNonNull(String value, String name) {
        if (value == null) {
            throw new IllegalArgumentException(name + " não pode ser nulo");
        }
        return value;
    }
}
