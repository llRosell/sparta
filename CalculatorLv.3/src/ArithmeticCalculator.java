import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 제네릭을 활용한 사칙연산 계산기 클래스
 * @param <T> Number 타입을 상속받는 피연산자 타입 (Integer, Double 등)
 */
public class ArithmeticCalculator<T extends Number> {
    private final List<Double> results = new ArrayList<>(); // 연산 결과를 저장하는 리스트

    /**
     * 두 숫자와 연산자를 받아 계산하는 메서드
     * @param firstOperand 첫 번째 숫자
     * @param secondOperand 두 번째 숫자
     * @param operator 연산자 (OperatorType Enum 타입)
     * @return 계산 결과
     */
    public double calculate(T firstOperand, T secondOperand, OperatorType operator) {
        double first = firstOperand.doubleValue(); // 첫 번째 숫자를 double로 변환
        double second = secondOperand.doubleValue(); // 두 번째 숫자를 double로 변환
        double result;

        // 연산자에 따라 계산 수행
        result = switch (operator) {
            case ADD -> first + second; // 덧셈
            case SUBTRACT -> first - second; // 뺄셈
            case MULTIPLY -> first * second; // 곱셈
            case DIVIDE -> {
                if (second == 0) {
                    throw new ArithmeticException("나눗셈에서 0으로 나눌 수 없습니다.");
                }
                yield first / second; // 나눗셈
            }
            default -> throw new IllegalArgumentException("지원하지 않는 연산자입니다: " + operator);
        };

        results.add(result); // 계산 결과를 리스트에 저장
        return result; // 계산 결과 반환
    }

    // 결과 리스트를 가져오는 getter 메서드
    public List<Double> getResults() {
        return new ArrayList<>(results); // 결과 리스트의 복사본 반환
    }

    // 특정 값보다 큰 계산 결과를 필터링하여 반환
    public List<Double> filterResultsGreaterThan(double threshold) {
        return results.stream() // Stream API를 사용하여 필터링
                .filter(result -> result > threshold) // 람다 표현식 - 조건: 결과가 threshold보다 클 때
                .collect(Collectors.toList()); // 필터링된 결과를 리스트로 반환
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
