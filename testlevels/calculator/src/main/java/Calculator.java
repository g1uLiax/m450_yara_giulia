public class Calculator {
    public double add(double sum1, double sum2) {
        return sum1 + sum2;
    }

    public double substract(double num1, double num2) {
        return num1 - num2;
    }

    public double multiply(double num1, double num2) {
        return num1 * num2;
    }

    public double divide(double num1, double num2) {
        if (num2 == 0) {
            throw new ArithmeticException("Divide by zero not defined");
        }
        return num1 / num2;
    }

}
