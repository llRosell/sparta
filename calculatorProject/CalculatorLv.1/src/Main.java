import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // 첫 번째 숫자 입력
            System.out.println("첫 번째 숫자를 입력하세요.");
            int firstNumber = scanner.nextInt();

            // 버퍼 비우기: nextInt() 후 남은 줄바꿈 문자 제거
            scanner.nextLine();

            // 연산자 입력
            System.out.println("사칙연산 기호를 입력하세요 (+, -, *, /): ");
            char operator = scanner.nextLine().charAt(0);

            // 두 번째 숫자 입력
            System.out.println("두 번째 숫자를 입력하세요.");
            int secondNumber = scanner.nextInt();

            // 버퍼 비우기
            scanner.nextLine();

            // 계산 수행
            double result = calculate(operator, firstNumber, secondNumber);
            System.out.println("결과: " + result);

            // 계속할지 여부 확인
            System.out.println("더 계산하시겠습니까? (exit 입력 시 종료)");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            }
        }

        // 프로그램 종료 후 Scanner 닫기
        scanner.close();
    }

<<<<<<< HEAD
=======
    /**
     * 주어진 연산자와 피연산자를 사용해 연산을 수행하고 결과를 반환하는 메서드
     *
     * @param operator     사칙연산 기호
     * @param firstNumber  첫 번째 숫자
     * @param secondNumber 두 번째 숫자
     * @return 계산 결과
     */
>>>>>>> dd935483ed49e588fa23a13adc95600b78dbb84d
    public static double calculate(char operator, int firstNumber, int secondNumber) {
        double result = 0;

        switch (operator) {
            case '+':
                result = firstNumber + secondNumber;
                break;
            case '-':
                result = firstNumber - secondNumber;
                break;
            case '*':
                result = firstNumber * secondNumber;
                break;
            case '/':
                if (secondNumber == 0) {
                    System.out.println("Error: 0으로 나눌 수 없습니다.");
                    return 0;
                }
                result = (double) firstNumber / secondNumber;
                break;
            default:
                System.out.println("올바른 연산자를 입력하세요.");
                break;
        }
        return result;
    }
}