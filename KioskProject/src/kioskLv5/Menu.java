package kioskLv5;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Menu 클래스: 하나의 카테고리(버거, 음료 등)의 메뉴를 관리
class Menu {
    private String name; // 카테고리 이름 (Burgers, Drinks, Desserts 등)
    private List<MenuItem> menuItems = new ArrayList<>(); // 카테고리 내의 메뉴 항목을 관리하는 리스트

    // 생성자: 메뉴 이름에 따라 카테고리별 메뉴 항목을 초기화
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

    // 해당 카테고리의 메뉴 항목들을 반환
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    // 메뉴 항목 출력: 번호와 함께 메뉴 항목을 출력
    public void printMenu() {
        int itemCount = 1; // 메뉴 항목 번호 추적
        for (MenuItem menuItem : getMenuItems()) {
            // 메뉴 항목 출력 (이름, 가격, 설명)
            System.out.println(itemCount + ". " + menuItem.getName() + " | W " + (menuItem.getPrice() / 1000.0) + " | " + menuItem.getDescription());
            itemCount++; // 항목 번호 증가
        }
        // 뒤로가기 옵션 추가
        System.out.println("0. 뒤로가기");
    }

    // 선택한 메뉴 항목의 상세 정보 출력
    public void printSelectedMenuInfo(int index) {
        // 메뉴 항목의 인덱스를 1부터 시작하므로, 0 기반 인덱스로 변경
        MenuItem selectedItem = menuItems.get(index - 1);
        System.out.println("선택한 메뉴: " + selectedItem.getName() + " | W " + (selectedItem.getPrice() / 1000.0) + " | " + selectedItem.getDescription());
    }
}
