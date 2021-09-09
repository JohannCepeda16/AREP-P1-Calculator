package edu.escuelaing.arep.calculator;

public class Calculator {

    private static Calculator instance = new Calculator();

    private Calculator() {
    }

    public static Calculator getInstance() {
        return instance;
    }

    public static String cos(String number) {
        return "Coseno de " + number + " = " + Math.cos(Double.parseDouble(number));
    }

    public static String sen(String number) {
        return "Seno de " + number + " = " + Math.sin(Double.parseDouble(number));
    }

    public static String tan(String number) {

        return "Tangente de " + number + " = " + Math.tan(Double.parseDouble(number));

    }
}
