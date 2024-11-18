import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Calculator calculator = new Calculator();  // Calculator 인스턴스 생성

        while (true) {
            // 첫 번째 숫자 입력 받기
            int firstNumber = 0;
            while (true) {
                try {
                    System.out.println("첫 번째 숫자를 입력하세요.");
                    firstNumber = scanner.nextInt();

                    if (firstNumber < 0) {
                        System.out.println("양수를 입력하세요.");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("숫자가 아닌 값을 입력했습니다. 다시 시도해주세요.");
                    scanner.nextLine(); // 버퍼 비워줍니다.
                }
            }

            scanner.nextLine(); // 남은 줄바꿈 문자 제거

            // 사칙연산 기호 입력 받기
            char operator;
            while (true) {
                System.out.println("사칙연산 기호를 입력하세요 (+, -, *, /): ");
                String operatorInput = scanner.nextLine();

                if (operatorInput.length() == 1 && "+-*/".indexOf(operatorInput.charAt(0)) != -1) {
                    operator = operatorInput.charAt(0);
                    break;
                } else {
                    System.out.println("유효한 사칙연산 기호가 아닙니다. 다시 입력하세요.");
                }
            }

            // 두 번째 숫자 입력 받기
            int secondNumber = 0;
            while (true) {
                try {
                    System.out.println("두 번째 숫자를 입력하세요.");
                    secondNumber = scanner.nextInt();

                    if (secondNumber < 0) {
                        System.out.println("양수를 입력하세요.");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("숫자가 아닌 값을 입력했습니다. 다시 시도해주세요.");
                    scanner.nextLine(); // 버퍼 비워줍니다.
                }
            }

            scanner.nextLine(); // 남은 \n 제거

            // 계산 실행
            double result = calculator.calculate(firstNumber, secondNumber, operator);
            System.out.println("결과: " + result);

            // 사용자가 원하는 옵션 입력
            System.out.println("계산 결과를 조회하려면 'view'를 입력하세요.");
            System.out.println("계속 계산하시려면 아무 키나 입력하세요. 종료하시려면 'exit'을 입력하세요.");
            System.out.println("첫 번째 연산 결과를 삭제하시려면 'remove'를 입력하세요.");

            // 사용자 입력에 따라 연산을 계속하거나 결과를 조회하거나 삭제하거나 종료
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            } else if (input.equalsIgnoreCase("view")) {
                // 계산된 결과 출력
                List<Double> results = calculator.getResults();
                if (results.isEmpty()) {
                    System.out.println("계산된 결과가 없습니다.");
                } else {
                    System.out.println("저장된 계산 결과:");
                    for (int i = 0; i < results.size(); i++) {
                        System.out.println((i + 1) + ": " + results.get(i));
                    }
                }
            } else if (input.equalsIgnoreCase("remove")) {
                // removeResult() 호출하여 첫 번째 연산 결과 삭제
                calculator.removeResult();
            }
        }

        scanner.close(); // 프로그램 종료 후 Scanner 닫기
    }
}