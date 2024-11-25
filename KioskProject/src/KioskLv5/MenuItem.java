package kioskLv5;

// MenuItem 클래스: 개별 메뉴 항목에 대한 정보를 저장
class MenuItem {
    private String name; // 메뉴 항목의 이름
    private int price; // 가격 (단위: 원)
    private String description; // 메뉴 항목의 설명

    // 생성자: 메뉴 항목의 이름, 가격, 설명을 초기화
    public MenuItem(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    // 각 속성에 대한 getter 메서드
    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}