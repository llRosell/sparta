package kioskchallengelv1;

import java.util.ArrayList;
import java.util.List;

// 카테고리 메뉴 클래스: 특정 카테고리에 속한 메뉴 목록을 관리
class Menu {
    private String name; // 카테고리 이름
    private List<MenuItem> menuItems = new ArrayList<>(); // 카테고리 내 메뉴 항목

    // 생성자: 카테고리 이름과 메뉴 항목 초기화
    public Menu(String name) {
        this.name = name;

        if ("Burgers".equals(name)) {
            // 'Burgers' 카테고리에는 여러 버거 메뉴 항목을 추가
            menuItems.add(new MenuItem("ShackBurger", 6900, "토마토, 양상추, 쉑소스가 토핑된 치즈버거"));
            menuItems.add(new MenuItem("SmokeShack", 8900, "베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거"));
            menuItems.add(new MenuItem("Cheeseburger", 6900, "포테이토 번과 비프패티, 치즈가 토핑된 치즈버거"));
            menuItems.add(new MenuItem("Hamburger", 5400, "비프패티를 기반으로 야채가 들어간 기본버거"));
        } else if ("Drinks".equals(name)) {
            // 'Drinks' 카테고리에는 음료 메뉴 항목을 추가
            menuItems.add(new MenuItem("Coke", 1500, "코카콜라"));
            menuItems.add(new MenuItem("Pepsi", 1500, "펩시콜라"));
        } else if ("Desserts".equals(name)) {
            // 'Desserts' 카테고리에는 디저트 메뉴 항목을 추가
            menuItems.add(new MenuItem("Ice Cream", 2000, "초콜릿 아이스크림"));
            menuItems.add(new MenuItem("Cake", 3000, "치즈 케이크"));
        }
    }

    // 카테고리 이름 반환
    public String getName() {
        return name;
    }

    // 해당 카테고리 메뉴 항목 반환
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    // 카테고리 메뉴 항목 출력
    public void printMenu() {
        int index = 1; // 메뉴 번호
        for (MenuItem item : menuItems) {
            // 메뉴 항목 출력
            System.out.println(index + ". " + item.getName() + " | W " + (item.getPrice() / 1000.0) + " | " + item.getDescription());
            index++;
        }
        System.out.println("0. 뒤로가기");
    }
}