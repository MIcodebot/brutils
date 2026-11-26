package dev.brutils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Validação, formatação e geração de CPF.
 */
public final class Cpf {

    private static final int LENGTH = 11;
    private static final Random RANDOM = new SecureRandom();

    private Cpf() {
    }

    /**
     * Retorna {@code true} se o CPF for válido. Aceita com ou sem máscara.
     */
    public static boolean isValid(String cpf) {
        if (cpf == null) {
            return false;
        }
        String digits = Strings.onlyDigits(cpf);
        if (!cpf.matches("[\\d.\\-\\s]+") || digits.length() != LENGTH || Strings.allSameChar(digits)) {
            return false;
        }
        int[] nums = toInts(digits);
        return nums[9] == checkDigit(nums, 9) && nums[10] == checkDigit(nums, 10);
    }

    /**
     * Formata um CPF como {@code 000.000.000-00}.
     *
     * @throws IllegalArgumentException se não tiver 11 dígitos
     */
    public static String format(String cpf) {
        String d = Strings.onlyDigits(Strings.requireNonNull(cpf, "cpf"));
        if (d.length() != LENGTH) {
            throw new IllegalArgumentException("CPF deve ter 11 dígitos: " + cpf);
        }
        return d.substring(0, 3) + "." + d.substring(3, 6) + "." + d.substring(6, 9) + "-" + d.substring(9);
    }

    /**
     * Remove a máscara, devolvendo só os dígitos.
     */
    public static String unformat(String cpf) {
        return Strings.onlyDigits(Strings.requireNonNull(cpf, "cpf"));
    }

    /**
     * Gera um CPF válido aleatório (só dígitos).
     */
    public static String generate() {
        int[] nums = new int[LENGTH];
        do {
            for (int i = 0; i < 9; i++) {
                nums[i] = RANDOM.nextInt(10);
            }
        } while (allSame(nums, 9));
        nums[9] = checkDigit(nums, 9);
        nums[10] = checkDigit(nums, 10);
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int n : nums) {
            sb.append(n);
        }
        return sb.toString();
    }

    /** Calcula o dígito verificador na posição {@code pos} (9 ou 10). */
    static int checkDigit(int[] nums, int pos) {
        int sum = 0;
        int weight = pos + 1;
        for (int i = 0; i < pos; i++) {
            sum += nums[i] * weight--;
        }
        int rest = sum % 11;
        return rest < 2 ? 0 : 11 - rest;
    }

    private static int[] toInts(String digits) {
        int[] nums = new int[digits.length()];
        for (int i = 0; i < digits.length(); i++) {
            nums[i] = digits.charAt(i) - '0';
        }
        return nums;
    }

    private static boolean allSame(int[] nums, int len) {
        for (int i = 1; i < len; i++) {
            if (nums[i] != nums[0]) {
                return false;
            }
        }
        return true;
    }
}
