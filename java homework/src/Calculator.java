// Calculator.java
public class Calculator {
    private int firstNumber;
    private int secondNumber;
    private AbstractOperation operation;

    public Calculator() {}

    public void setOperation(AbstractOperation operation) {
        this.operation = operation;
    }

    public void setFirstNumber(int firstNumber) {
        this.firstNumber = firstNumber;
    }

    public void setSecondNumber(int secondNumber) {
        this.secondNumber = secondNumber;
    }

    public double calculate() {
        if (operation == null) {
            throw new IllegalStateException("연산이 설정되지 않았습니다.");
        }
        return operation.operate(this.firstNumber, this.secondNumber);
    }
}
