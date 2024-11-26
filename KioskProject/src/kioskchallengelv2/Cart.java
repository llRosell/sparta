package kioskchallengelv2;

import java.util.ArrayList;
import java.util.List;

class Cart {
    // 장바구니에 담을 항목들을 관리하는 리스트
    private List<CartItem> cartItems;

    // 생성자: 새로운 장바구니를 만들 때 호출되며, 비어있는 cartItems 리스트를 초기화
    public Cart() {
        cartItems = new ArrayList<>();  // 빈 리스트로 장바구니 초기화
    }

    // 장바구니에 메뉴 항목을 추가하는 메서드
    // 메뉴 항목은 CartItem 객체로 추가된다.
    public void addItem(MenuItem menu) {
        // CartItem은 MenuItem과 수량(1)을 받아서 장바구니에 추가한다
        cartItems.add(new CartItem(menu, 1));
        System.out.println(menu.getName() + "이(가) 장바구니에 추가되었습니다.");  // 메뉴가 장바구니에 추가된 후 알림 메시지 출력
    }

    // 장바구니에 있는 모든 항목을 출력하는 메서드
    public void displayCart() {
        // 장바구니가 비어 있으면 비어 있다는 메시지 출력
        if (cartItems.isEmpty()) {
            System.out.println("장바구니가 비어 있습니다.");
            return;  // 비어있으면 더 이상 실행하지 않고 메서드를 종료
        }

        // 장바구니에 있는 항목들을 출력
        System.out.println("[ Orders ]");
        // cartItems 리스트의 각 항목을 출력
        for (CartItem item : cartItems) {
            System.out.println(item);  // CartItem의 toString() 메서드 호출로 항목을 출력
        }

        // 장바구니 총액을 계산하여 출력
        System.out.println("\n[ Total ]");
        // cartItems에서 각 항목의 가격을 합산하여 총액을 구함
        double total = cartItems.stream().mapToDouble(CartItem::getTotalPrice).sum();
        // 총액을 천 원 단위로 출력
        System.out.println("W " + total / 1000.0);  // 총액을 1000으로 나누어 출력
    }

    // 장바구니의 내용을 확인하고 주문을 처리하는 메서드
    public void checkout() {
        // 장바구니가 비어 있으면 주문을 진행할 수 없다고 알림
        if (cartItems.isEmpty()) {
            System.out.println("장바구니가 비어 있습니다. 주문을 할 수 없습니다.");
            return;  // 장바구니가 비어 있으면 메서드 종료
        }

        // 장바구니의 항목을 출력
        displayCart();

        // 주문 완료 메시지와 함께 총 금액을 출력
        System.out.println("주문이 완료되었습니다. 금액은 W " +
                cartItems.stream().mapToDouble(CartItem::getTotalPrice).sum() / 1000.0 + " 입니다.");
        System.out.println();

        // 주문 완료 후 장바구니를 비움
        cartItems.clear();
    }

    // 장바구니에 담긴 항목들을 모두 지우는 메서드
    public void clearCart() {
        // 장바구니 비우기
        cartItems.clear();
        // 비워졌다는 메시지 출력
        System.out.println("진행 중인 주문이 취소되었습니다.");
    }

    // 장바구니가 비어 있는지 확인하는 메서드
    public boolean isEmpty() {
        // cartItems가 비어있으면 true 반환, 아니면 false 반환
        return cartItems.isEmpty();
    }

    // 장바구니에 담긴 항목 리스트를 반환하는 메서드
    public List<CartItem> getCartItems() {
        return cartItems;  // cartItems 리스트를 반환
    }
}