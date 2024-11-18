// DivideOperation.java
public class DivideOperation extends AbstractOperation {
    @Override
    public double operate(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("0으로 나눌 수 없습니다.");
        }
        return (double) a / b;
    }
}