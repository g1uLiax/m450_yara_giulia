import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {

    @Test
    void add_TwoPlusTwo_equalFour() {
        Calculator calculator = new Calculator();
        assertEquals(4, calculator.add(2, 2));
    }

    @Test
    void add_TwoPlusMinFive_equalMinThree() {
        Calculator calculator = new Calculator();
        assertEquals(-3, calculator.add(2, -5));
    }

    @Test
    void substract_twoNegatives_Negative() {
        Calculator calculator = new Calculator();
        assertEquals(-13, calculator.substract(-3, 10));
    }

    @Test
    void multiply_twoNegatives_Positive() {
        Calculator calculator = new Calculator();
        assertEquals(27, calculator.multiply(-3, -9));
    }

    @Test
    void multiply_negativePositive_Negative() {
        Calculator calculator = new Calculator();
        assertEquals(-27, calculator.multiply(3, -9));
    }

    @Test
    void divide_twoNegatives_Positive() {
        Calculator calculator = new Calculator();
        assertEquals(12, calculator.divide(-24, -2));
    }

    @Test
    void divide_dividendZero_Exception() {
        Calculator calculator = new Calculator();
        assertThrows(ArithmeticException.class, () -> calculator.divide(12, 0));
    }
}
