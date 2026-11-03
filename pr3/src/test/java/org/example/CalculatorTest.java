package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    private final Calculator calculator = new Calculator();

    @Test
    void testAdd() {
        Assertions.assertEquals(3, calculator.add(7, -4));
    }

    @Test
    void testSubtract() {
        Assertions.assertEquals(7, calculator.subtract(4, -3));
    }

    @Test
    void testMultiply() {
        Assertions.assertEquals(5, calculator.multiply(-2, -2.5));
    }

    @Test
    void testDivide() {
        Assertions.assertEquals(-2.5, calculator.divide(10, -4));
    }

    @Test
    void testDivisionByZero() {
        Assertions.assertThrows(ArithmeticException.class, () -> calculator.divide(1, 0));
    }

    @Test
    void testInvalidInput() {
        Assertions.assertThrows(InvalidInputException.class, () -> calculator.add(Double.NaN, 5));
    }
}