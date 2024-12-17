/**
 * 사칙연산 연산자 Enum
 */
public enum OperatorType {
    ADD('+'), // 덧셈
    SUBTRACT('-'), // 뺄셈
    MULTIPLY('*'), // 곱셈
    DIVIDE('/'); // 나눗셈

    private final char symbol; // 연산자 기호

    // 생성자: 연산자 기호를 초기화
    OperatorType(char symbol) {
        this.symbol = symbol;
    }

    /**
     * 연산자 기호를 반환
     * @return 연산자 기호
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * 기호를 Enum 값으로 변환
     * @param symbol 입력받은 연산자 기호
     * @return 대응되는 OperatorType
     * @throws IllegalArgumentException 유효하지 않은 연산자 기호일 경우 예외 발생
     */
    public static OperatorType fromSymbol(char symbol) {
        for (OperatorType op : OperatorType.values()) {
            if (op.getSymbol() == symbol) {
                return op; // 입력 기호와 일치하는 Enum 반환
            }
        }
        throw new IllegalArgumentException("유효하지 않은 연산자 기호입니다: " + symbol);
    }
}
