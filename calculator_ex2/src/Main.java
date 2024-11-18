public class Main {
    public static void main(String[] args) {
        boolean calculateEnded = false; // 계산을 계속 수행할지 여부를 저장하는 플래그

        while (!calculateEnded) {
            try {
                calculateEnded = CalculatorApp.start(); // CalculatorApp을 실행하여 계산 수행
            } catch (BadInputException e) {
                System.out.println(e.getMessage()); // 잘못된 입력 예외 메시지를 출력
            } catch (Exception e) {
                System.out.println("알 수 없는 오류가 발생했습니다: " + e.getMessage());
            }
        }
    }
}
