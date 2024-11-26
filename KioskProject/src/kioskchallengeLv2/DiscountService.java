package kioskchallengelv2;

// 할인 서비스를 처리하는 클래스
public class DiscountService {

    /**
     * 주어진 등급(Grade)과 가격(price)에 따라 할인된 금액을 반환하는 메서드
     * @param grade 등급 (VIP, 일반 등)
     * @param price 원래 가격
     * @return 할인된 가격
     */
    public int applyDiscount(Grade grade, int price) {
        // 주어진 등급에 해당하는 할인 비율을 적용하여 할인된 금액 반환
        return grade.discount(price);  // Grade enum의 discount 메서드를 호출하여 할인 금액을 계산
    }

    /**
     * 장바구니에 담긴 모든 항목에 대해 각 등급에 따른 할인 금액을 계산하고 출력하는 메서드
     * @param cart 장바구니 객체
     */
    public void calculateAndPrintDiscounts(Cart cart) {
        // 장바구니가 비어 있는 경우 처리
        if (cart.isEmpty()) {
            System.out.println("장바구니가 비어 있어 할인을 계산할 수 없습니다.");  // 장바구니가 비었으면 메시지 출력하고 종료
            return;
        }

        // 장바구니에 담긴 모든 항목의 총 가격을 계산
        // CartItem에서 가격을 합산하여 총 가격을 구함
        int totalPrice = (int) cart.getCartItems().stream()  // cart.getCartItems()로 장바구니의 모든 아이템을 가져옴
                .mapToDouble(CartItem::getTotalPrice)  // 각 CartItem의 총 가격을 구함
                .sum();  // 모든 가격을 더함

        // 총 가격 출력 (단위는 원(KRW))
        System.out.println("총 금액: W " + (totalPrice / 1000.0));  // 원화를 천 단위로 나누어 W 단위로 출력

        // 각 등급별로 할인 금액과 할인 후 금액 계산
        for (Grade grade : Grade.values()) {  // Grade enum의 모든 값을 반복 (예: GOLD, SILVER, etc.)
            int discountAmount = applyDiscount(grade, totalPrice);  // 각 등급에 따른 할인 금액을 계산
            int finalPrice = totalPrice - discountAmount;  // 총 가격에서 할인 금액을 빼서 할인 후 금액을 계산
            printDiscountDetails(grade, discountAmount, finalPrice);  // 계산된 결과를 출력
        }
    }

    /**
     * 할인 결과를 출력하는 메서드
     * @param grade 할인 적용된 등급
     * @param discountAmount 할인 금액
     * @param finalPrice 할인 후 금액
     */
    private void printDiscountDetails(Grade grade, int discountAmount, int finalPrice) {
        // 할인 결과를 출력
        System.out.println(grade.name() + " 등급:");  // 등급의 이름을 출력 (예: GOLD 등급)
        System.out.println("- 할인 금액: W " + (discountAmount / 1000.0));  // 할인 금액을 W 단위로 출력
        System.out.println("- 할인 후 금액: W " + (finalPrice / 1000.0));  // 할인 후 최종 금액을 W 단위로 출력
    }
}