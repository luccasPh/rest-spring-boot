package com.lucas.restspringboot.shared;

public class NumberConverter {

    public static boolean isNumeric(String strNumber) {
        if (strNumber == null)
            return false;

        String number = strNumber.replaceAll(",", ".");
        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

    public static Double convertToDouble(String strNumber) {
        if (strNumber == null)
            return 0D;

        String number = strNumber.replaceAll(",", ".");
        return Double.parseDouble(number);
    }
}
