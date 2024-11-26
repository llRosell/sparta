package kioskchallengelv2;

class MenuItem {
    private String name; // 메뉴 항목의 이름
    private int price; // 메뉴 항목의 가격 (단위: 원)
    private String description; // 메뉴 항목에 대한 설명

    // 생성자: 메뉴 이름, 가격, 설명을 받아 객체 초기화
    public MenuItem(String name, int price, String description) {
        this.name = name; // 메뉴 이름 초기화
        this.price = price; // 가격 초기화
        this.description = description; // 설명 초기화
    }

    // 메뉴 이름 반환
    public String getName() {
        return name; // 메뉴 이름 반환
    }

    // 메뉴 가격 반환
    public int getPrice() {
        return price; // 가격 반환
    }

    // 메뉴 설명 반환
    public String getDescription() {
        return description; // 메뉴 설명 반환
    }

    // 메뉴 항목의 정보를 문자열로 반환 (예: "ShackBurger | W 6.9 | 토마토, 양상추, 쉑소스가 토핑된 치즈버거")
    @Override
    public String toString() {
        return name + " | W " + (price / 1000.0) + " | " + description;
        // 가격을 천 원 단위로 변환하여 출력
    }
}