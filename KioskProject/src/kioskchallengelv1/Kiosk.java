package kioskchallengelv1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Kiosk {
    private List<Menu> menus = new ArrayList<>();
    private Cart cart = new Cart(); // 장바구니 객체 추가
    private Scanner scanner = new Scanner(System.in);

    public Kiosk() {
        menus.add(new Menu("Burgers"));
        menus.add(new Menu("Drinks"));
        menus.add(new Menu("Desserts"));
    }

    public void printMainMenu() {
        System.out.println("[ MAIN MENU ]");
        for (int i = 0; i < menus.size(); i++) {
            System.out.println((i + 1) + ". " + menus.get(i).getName());
        }
        System.out.println("0. 종료");
        if (!cart.isEmpty()) { // 장바구니에 아이템이 있을 때만 ORDER MENU 출력
            System.out.println("\n[ ORDER MENU ]");
            System.out.println("4. Orders       | 장바구니를 확인 후 주문합니다.");
            System.out.println("5. Cancel       | 진행중인 주문을 취소합니다.");
        }
    }

    public void start() {
        boolean isRunning = true;

        while (isRunning) {
            printMainMenu();
            System.out.print("메뉴 번호를 입력하세요: ");
            int menuInput = getValidInput(menus.size() + 2);

            switch (menuInput) {
                case 0 -> {
                    System.out.println("프로그램을 종료합니다.");
                    isRunning = false;
                }
                case 4 -> {
                    if (cart.isEmpty()) throw new IllegalStateException("장바구니가 비어 있습니다.");
                    cart.displayCart();
                    System.out.print("\n1. 주문      2. 메뉴판: ");
                    int orderInput = getValidInput(2);
                    if (orderInput == 1) cart.checkout();
                }
                case 5 -> {
                    if (cart.isEmpty()) throw new IllegalStateException("장바구니가 비어 있습니다.");
                    cart.clearCart();
                }
                default -> selectCategoryMenu(menuInput - 1);
            }
        }
        scanner.close();
    }

    public void selectCategoryMenu(int categoryIndex) {
        boolean isCategoryRunning = true;
        Menu selectedMenu = menus.get(categoryIndex);

        while (isCategoryRunning) {
            System.out.println("\n[ " + selectedMenu.getName() + " MENU ]");
            selectedMenu.printMenu();
            System.out.print("메뉴 번호를 입력하세요: ");
            int menuInput = getValidInput(selectedMenu.getMenuItems().size());

            if (menuInput == 0) {
                isCategoryRunning = false;
            } else {
                MenuItem menuItem = selectedMenu.getMenuItems().get(menuInput - 1);
                System.out.println("선택한 메뉴: " + menuItem);
                System.out.print("\n위 메뉴를 장바구니에 추가하시겠습니까? 1. 확인  2. 취소: ");
                int confirmation = getValidInput(2);
                if (confirmation == 1) cart.addItem(menuItem);
            }
        }
    }

    private int getValidInput(int size) {
        int input = -1;
        while (input < 0 || input > size) {
            try {
                input = scanner.nextInt();
                if (input < 0 || input > size) {
                    System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
                }
            } catch (Exception e) {
                System.out.println("잘못된 입력입니다. 숫자만 입력해주세요.");
                scanner.next();
            }
        }
        return input;
    }
}