import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArithmeticCalculator<Number> calculator = new ArithmeticCalculator<>(); // 제네릭 계산기 인스턴스 생성

        boolean running = true; // 프로그램 실행 상태

        while (running) {
            try {
                System.out.println("첫 번째 숫자를 입력하세요.");
                double firstNumber = scanner.nextDouble(); // 실수 입력 가능

                // 사칙연산 기호 입력 받기
                System.out.println("사칙연산 기호를 입력하세요 (+, -, *, /): ");
                char operatorSymbol = scanner.next().charAt(0); // 기호 입력 받기
                OperatorType operator = OperatorType.fromSymbol(operatorSymbol); // 기호를 OperatorType으로 변환

                double secondNumber = 0;
                boolean validInput = false;

                // 두 번째 숫자 입력 받기, 잘못된 입력 시 다시 받도록 처리
                while (!validInput) {
                    System.out.println("두 번째 숫자를 입력하세요:");
                    try {
                        secondNumber = scanner.nextDouble(); // 실수 입력
                        validInput = true; // 정상적으로 숫자를 입력받았으므로 반복 종료

                        // 두 번째 숫자가 정상적으로 입력되면 계산 수행
                        double result = calculator.calculate(firstNumber, secondNumber, operator);
                        System.out.println("계산 결과: " + result); // 계산 결과 출력

                    } catch (java.util.InputMismatchException e) {
                        System.out.println("오류가 발생했습니다. 숫자만 입력하세요."); // 숫자가 아닌 값이 들어오면 에러 메시지 출력
                        scanner.nextLine(); // 버퍼 클리어
                    }
                }

                // 계산 후 계속 여부 확인
                while (true) {
                    // 계산 후 선택 옵션
                    System.out.println("계산 결과를 조회하려면 'view'를 입력하세요.");
                    System.out.println("저장된 결과 중 특정 값보다 큰 값을 조회하려면 'select'를 입력하세요.");
                    System.out.println("계속 계산하시려면 아무 키나 입력하세요.");
                    System.out.println("종료하시려면 'exit'을 입력하세요.");
                    System.out.println("첫 번째 연산 결과를 삭제하시려면 'remove'를 입력하세요.");

                    scanner.nextLine(); // 남은 줄바꿈 문자 제거
                    String input = scanner.nextLine(); // 사용자의 선택 입력 받기

                    if (input.equalsIgnoreCase("view")) {
                        // 모든 계산 결과 출력
                        System.out.println("저장된 계산 결과들: " + calculator.getResults());

                        // 'view'로 결과를 보여준 후, 다시 선택 옵션을 출력
                        continue; // 계속해서 선택 옵션을 출력하도록 하기 위해 루프 시작

                    } else if (input.equalsIgnoreCase("select")) {
                        // 'select' 입력 시, 저장된 결과 중 특정 값보다 큰 값 조회
                        System.out.println("저장된 결과 중 특정 값보다 큰 값을 조회하려면 기준 숫자를 입력하세요:");
                        double threshold = scanner.nextDouble(); // 기준 값 입력
                        var filteredResults = calculator.filterResultsGreaterThan(threshold); // 필터된 결과 가져오기
                        System.out.println("조건에 맞는 결과: " + filteredResults);
                    } else if (input.equalsIgnoreCase("exit")) {
                        System.out.println("프로그램을 종료합니다.");
                        running = false; // 프로그램 종료
                        break; // while 루프 탈출
                    } else if (input.equalsIgnoreCase("remove")) {
                        // 첫 번째 연산 결과 삭제
                        calculator.removeResult();
                        System.out.println("첫 번째 연산 결과가 삭제되었습니다.");
                    } else {
                        System.out.println("계속 진행합니다.");
                        break; // 잘못된 입력은 루프를 종료하고 다시 계산 시작
                    }
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("오류가 발생했습니다. 숫자만 입력하세요."); // 첫 번째 숫자 입력 시 잘못된 값 처리
                scanner.nextLine(); // 버퍼 클리어
            } catch (IllegalArgumentException e) {
                System.out.println("오류가 발생했습니다. 정확한 사칙연산 기호를 입력해주세요."); // 잘못된 사칙연산 기호 입력 시
            } catch (Exception e) {
                System.out.println("오류가 발생했습니다: " + e.getMessage());
                scanner.nextLine(); // 버퍼 클리어
            }
        }
    }
}
