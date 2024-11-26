package kioskchallengelv2;

// 할인 등급을 나타내는 enum 클래스
public enum Grade {
    // 각 등급별로 할인 비율을 정의
    DISABLED_PERSON(10),   // 장애인: 10% 할인
    SENIOR_CITIZEN(5),     // 노인: 5% 할인
    STUDENT(5),            // 학생: 5% 할인
    REGULAR(0);            // 일반: 0% 할인 (할인 없음)

    private final int discountGrade;  // 각 등급의 할인 비율

    /**
     * 생성자: 각 등급에 대해 할인 비율을 설정
     * @param discountGrade 해당 등급의 할인 비율 (예: 10, 5, 0 등)
     */
    Grade(int discountGrade) {
        this.discountGrade = discountGrade;  // 주어진 할인 비율을 할당
    }

    /**
     * 할인 비율을 반환하는 메서드
     * @return 해당 등급의 할인 비율
     */
    public int getDiscountGrade() {
        return discountGrade;  // 할인 비율을 반환
    }

    /**
     * 주어진 가격에 대해 해당 등급의 할인을 계산하는 메서드
     * @param price 원래 가격 (단위: 원)
     * @return 할인된 금액
     */
    public int discount(int price) {
        // 할인 금액 계산: 가격에 할인 비율을 곱하고 100으로 나누어 최종 할인 금액을 계산
        return price * discountGrade / 100;  // 가격에 비율을 곱하고 나눈 값이 할인 금액
    }
}