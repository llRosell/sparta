import java.util.Scanner;

// 추상 클래스: 연산을 위한 기본 인터페이스 역할
abstract class AbstractOperation {
    public abstract double operate(int firstNumber, int secondNumber);
}

// 더하기 연산 클래스
class AddOperation extends AbstractOperation {
    @Override
    public double operate(int firstNumber, int secondNumber) {
        return firstNumber + secondNumber;
    }
}

// 빼기 연산 클래스
class SubstractOperation extends AbstractOperation {
    @Override
    public double operate(int firstNumber, int secondNumber) {
        return firstNumber - secondNumber;
    }
}

// 곱하기 연산 클래스
class MultiplyOperation extends AbstractOperation {
    @Override
    public double operate(int firstNumber, int secondNumber) {
        return firstNumber * secondNumber;
    }
}

// 나누기 연산 클래스
class DivideOperation extends AbstractOperation {
    @Override
    public double operate(int firstNumber, int secondNumber) {
        // 분모가 0일 때 오류 발생
        if (secondNumber == 0) {
            throw new ArithmeticException("오류: 0으로 나눌 수 없습니다.");
        }
        return firstNumber / (double) secondNumber;
    }
}

// 계산기 클래스
public class Calculator {
    private AbstractOperation operation;

    // 생성자를 통한 연산 객체 주입
    public Calculator(AbstractOperation operation) {
        this.operation = operation;
    }

    // Setter를 통한 연산 객체 주입
    public void setOperation(AbstractOperation operation) {
        this.operation = operation;
    }

    // 연산을 수행하는 메서드
    public double calculate(int firstNumber, int secondNumber) {
        return operation.operate(firstNumber, secondNumber);
    }

    // main 메서드: 사용자 입력을 받고 계산 결과 출력
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("첫 번째 숫자를 입력하세요: ");
        int firstNumber = scanner.nextInt();
        System.out.println("두 번째 숫자를 입력하세요: ");
        int secondNumber = scanner.nextInt();

        System.out.println("연산자를 입력하세요 (+, -, *, /): ");
        String operator = scanner.next();

        // 연산자에 맞는 연산 클래스 선택
        AbstractOperation operation = null;
        switch (operator) {
            case "+":
                operation = new AddOperation();
                break;
            case "-":
                operation = new SubstractOperation();
                break;
            case "*":
                operation = new MultiplyOperation();
                break;
            case "/":
                operation = new DivideOperation();
                break;
            default:
                System.out.println("유효하지 않은 연산자입니다. +, -, *, /만 사용 가능합니다.");
                return;  // 유효하지 않은 연산자일 경우 종료
        }

        // 생성자에서 연산 클래스를 주입받음
        Calculator calc = new Calculator(operation);
        // 또는 calc.setOperation(operation); // Setter를 사용해 주입할 수도 있습니다.

        try {
            // 결과 출력
            double result = calc.calculate(firstNumber, secondNumber);
            System.out.println("결과: " + result);
        } catch (ArithmeticException e) {
            // 예외 발생 시 메시지 출력
            System.out.println(e.getMessage());
        }

        scanner.close();
    }
}
