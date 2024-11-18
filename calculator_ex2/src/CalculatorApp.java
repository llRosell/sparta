import java.util.Scanner;

public class CalculatorApp {
    public static boolean start() throws Exception {
        Parser parser = new Parser(); // Parser 객체 생성
        Scanner scanner = new Scanner(System.in); // 사용자 입력을 받기 위한 Scanner 객체

        System.out.println("첫 번째 숫자를 입력해주세요!");
        String firstInput = scanner.nextLine();
        parser.parseFirstNum(firstInput); // 첫 번째 숫자 파싱 및 예외처리

        System.out.println("연산자를 입력해주세요 (+, -, *, /): ");
        String operator = scanner.nextLine();
        parser.parseOperator(operator); // 연산자 파싱 및 예외처리

        System.out.println("두 번째 숫자를 입력해주세요!");
        String secondInput = scanner.nextLine();
        parser.parseSecondNum(secondInput); // 두 번째 숫자 파싱 및 예외처리

        System.out.println("연산 결과 : " + parser.executeCalculator());
        return true; // 계산이 완료되면 true를 반환하여 반복문 종료
    }
}
