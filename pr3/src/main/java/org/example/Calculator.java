package org.example;

public class Calculator {

    public double add(double a, double b) {
        validateInput(a, b);
        return a + b;
    }

    public double subtract(double a, double b) {
        validateInput(a, b);
        return a - b;
    }

    public double multiply(double a, double b) {
        validateInput(a, b);
        return a * b;
    }

    public double divide(double a, double b) {
        validateInput(a, b);

        if (b == 0) {
            throw new ArithmeticException("Не можна ділити на нуль");
        }

        return a / b;
    }

    private void validateInput(double a, double b) {
        if (Double.isNaN(a) || Double.isNaN(b) || Double.isInfinite(a) || Double.isInfinite(b)) {
            throw new InvalidInputException("Введено недійсні дані");
        }
    }
}