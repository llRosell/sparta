package kioskchallengelv2;

class CartItem {
    // CartItem은 장바구니에 담길 메뉴 항목을 나타내는 클래스입니다.
    // 이 클래스는 메뉴 아이템과 그 아이템의 수량을 관리합니다.

    // 메뉴 아이템을 나타내는 필드: MenuItem 객체
    private MenuItem menu;

    // 메뉴 아이템의 수량을 나타내는 필드
    private int quantity;

    // 생성자: 메뉴 아이템과 수량을 받아 CartItem 객체를 초기화
    public CartItem(MenuItem menu, int quantity) {
        this.menu = menu;  // 메뉴 아이템을 설정
        this.quantity = quantity;  // 수량을 설정
    }

    // MenuItem 객체를 반환하는 getter 메서드
    public MenuItem getMenu() {
        return menu;  // 메뉴 아이템을 반환
    }

    // 수량을 반환하는 getter 메서드
    public int getQuantity() {
        return quantity;  // 수량을 반환
    }

    // 해당 CartItem의 총 가격을 계산하는 메서드
    // 가격 = 메뉴의 가격 * 수량
    public double getTotalPrice() {
        return menu.getPrice() * quantity;  // 메뉴의 가격과 수량을 곱한 총 가격 반환
    }

    // CartItem을 문자열 형태로 출력하는 메서드
    @Override
    public String toString() {
        // 메뉴의 이름, 가격, 설명을 반환하는 문자열
        return menu.getName() + " | W " + menu.getPrice() + " | " + menu.getDescription();
    }
}