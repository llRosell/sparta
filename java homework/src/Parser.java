import java.util.regex.Pattern;

public class Parser {
    private static final String OPERATION_REG = "[+\\-*/]"; // 연산자에 대한 정규식
    private static final String NUMBER_REG = "^[0-9]+$";     // 양의 정수에 대한 정규식

    private final Calculator calculator = new Calculator();

    // 첫 번째 숫자를 파싱하여 설정
    public Parser parseFirstNum(String firstInput) throws BadInputException {
        if (!Pattern.matches(NUMBER_REG, firstInput)) {
            throw new BadInputException("숫자"); // 정규식에 맞지 않는 경우 예외 발생
        }
        calculator.setFirstNumber(Integer.parseInt(firstInput)); // 문자열을 정수로 변환 후 저장
        return this;
    }

    // 두 번째 숫자를 파싱하여 설정
    public Parser parseSecondNum(String secondInput) throws BadInputException {
        if (!Pattern.matches(NUMBER_REG, secondInput)) {
            throw new BadInputException("숫자"); // 정규식에 맞지 않는 경우 예외 발생
        }
        calculator.setSecondNumber(Integer.parseInt(secondInput)); // 문자열을 정수로 변환 후 저장
        return this;
    }

    // 연산자를 파싱하여 설정
    public Parser parseOperator(String operationInput) throws BadInputException {
        if (!Pattern.matches(OPERATION_REG, operationInput)) {
            throw new BadInputException("연산자 (+, -, *, /)"); // 정규식에 맞지 않는 경우 예외 발생
        }
        // 입력된 연산자에 따라 적절한 연산 클래스를 설정
        switch (operationInput) {
            case "+":
                calculator.setOperation(new AddOperation());
                break;
            case "-":
                calculator.setOperation(new SubtractOperation());
                break;
            case "*":
                calculator.setOperation(new MultiplyOperation());
                break;
            case "/":
                calculator.setOperation(new DivideOperation());
                break;
            default:
                throw new BadInputException("유효한 연산자");
        }
        return this;
    }

    // 계산기 실행 메서드
    public double executeCalculator() {
        return calculator.calculate();
    }
}
