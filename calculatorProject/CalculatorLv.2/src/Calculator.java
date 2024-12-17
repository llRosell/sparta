import java.util.ArrayList;
import java.util.List;

public class Calculator {
    private List<Double> results = new ArrayList<>(); // 연산 결과를 저장하는 리스트

    // 연산 메서드
    public double calculate(int firstNumber, int secondNumber, char operator) {
        double result;

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
                    System.out.println("나눗셈 연산에서 분모에 0이 입력될 수 없습니다.");
                    return 0;
                }
                result = (double) firstNumber / secondNumber;
                break;
            default:
                System.out.println("올바른 연산 기호가 아닙니다.");
                return 0;
        }

        results.add(result); // 연산 결과를 리스트에 저장
        return result; // 최종 결과 반환
    }

    // 결과 리스트를 가져오는 getter 메서드
    public List<Double> getResults() {
        return results;
    }

    // 결과 리스트를 수정하는 setter 메서드
    public void setResults(List<Double> results) {
        this.results = results;
    }

    // 가장 먼저 저장된 데이터를 삭제하는 메서드
    public void removeResult() {
        if (!results.isEmpty()) {
            results.remove(0); // 리스트의 첫 번째 요소를 제거
            System.out.println("첫 번째 연산 결과가 삭제되었습니다.");
        } else {
            System.out.println("삭제할 연산 결과가 없습니다.");
        }
    }
}