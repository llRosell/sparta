package kioskChallengeLv1;

import java.util.ArrayList;
import java.util.List;

class Cart {
    private List<CartItem> cartItems;

    public Cart() {
        cartItems = new ArrayList<>();
    }

    public void addItem(MenuItem menu) {
        cartItems.add(new CartItem(menu, 1));
        System.out.println(menu.getName() + "이(가) 장바구니에 추가되었습니다.");
    }

    public void displayCart() {
        if (cartItems.isEmpty()) {
            System.out.println("장바구니가 비어 있습니다.");
            return;
        }
        System.out.println("[ Orders ]");
        for (CartItem item : cartItems) {
            System.out.println(item);
        }
        System.out.println("\n[ Total ]");
        double total = cartItems.stream().mapToDouble(CartItem::getTotalPrice).sum();
        System.out.println("W " + total / 1000.0);
    }

    public void checkout() {
        if (cartItems.isEmpty()) {
            System.out.println("장바구니가 비어 있습니다. 주문을 할 수 없습니다.");
            return;
        }
        displayCart();
        System.out.println("주문이 완료되었습니다. 금액은 W " +
                cartItems.stream().mapToDouble(CartItem::getTotalPrice).sum() / 1000.0 + " 입니다.");
        System.out.println();
        cartItems.clear();
    }

    public void clearCart() {
        cartItems.clear();
        System.out.println("진행 중인 주문이 취소되었습니다.");
    }

    public boolean isEmpty() {
        return cartItems.isEmpty();
    }
}