package kioskchallengelv2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Kiosk {
    private List<Menu> menus = new ArrayList<>();  // 메뉴를 저장하는 리스트
    private Cart cart = new Cart();  // 장바구니 객체
    private DiscountService discountService = new DiscountService(); // 할인 서비스 객체
    private Scanner scanner = new Scanner(System.in);  // 사용자 입력을 받기 위한 Scanner 객체

    // Kiosk 생성자: 기본 메뉴 항목을 추가
    public Kiosk() {
        // 각 카테고리 메뉴를 초기화하여 메뉴 리스트에 추가
        menus.add(new Menu("Burgers"));
        menus.add(new Menu("Drinks"));
        menus.add(new Menu("Desserts"));
    }

    // 메인 메뉴를 출력하는 메서드
    public void printMainMenu() {
        System.out.println("[ MAIN MENU ]");
        // 메뉴 리스트를 출력 (1부터 시작하여 번호 부여)
        for (int i = 0; i < menus.size(); i++) {
            System.out.println((i + 1) + ". " + menus.get(i).getName());
        }
        System.out.println("0. 종료      | 종료");

        // 장바구니에 항목이 있으면 "ORDER MENU" 추가
        if (!cart.isEmpty()) {
            System.out.println("\n[ ORDER MENU ]");
            System.out.println("4. Orders       | 장바구니를 확인 후 주문합니다.");
            System.out.println("5. Cancel       | 진행중인 주문을 취소합니다.");
        }
    }

    // 프로그램을 실행하는 메서드
    public void start() {
        boolean isRunning = true;  // 프로그램 실행 여부

        while (isRunning) {
            printMainMenu();  // 메인 메뉴 출력
            System.out.print("\n아래 메뉴판을 보시고 메뉴를 골라 입력해주세요: ");
            int menuInput = getValidInput(menus.size() + 2);  // 유효한 입력을 받을 때까지 반복

            switch (menuInput) {
                case 0 -> {
                    // 종료 메뉴 선택 시 프로그램 종료
                    System.out.println("프로그램을 종료합니다.");
                    isRunning = false;  // while문 종료
                }
                case 4 -> handleOrders();  // 장바구니 확인 후 주문 처리
                case 5 -> handleCancel();  // 장바구니 비우기 (취소)
                default -> selectCategoryMenu(menuInput - 1);  // 카테고리 메뉴로 이동
            }
        }
        scanner.close();  // Scanner 자원 반납
    }

    // 선택한 메뉴 카테고리로 이동하는 메서드
    private void selectCategoryMenu(int categoryIndex) {
        boolean isCategoryRunning = true;  // 카테고리 메뉴 실행 여부
        Menu selectedMenu = menus.get(categoryIndex);  // 선택한 메뉴 카테고리

        while (isCategoryRunning) {
            System.out.println("\n[ " + selectedMenu.getName() + " MENU ]");
            selectedMenu.printMenu();  // 해당 카테고리의 메뉴 항목 출력
            System.out.print("메뉴 번호를 입력하세요: ");
            int menuInput = getValidInput(selectedMenu.getMenuItems().size());  // 메뉴 항목 번호 입력

            if (menuInput == 0) {
                // 메뉴로 돌아가려면 종료
                isCategoryRunning = false;
            } else {
                // 선택한 메뉴 항목
                MenuItem menuItem = selectedMenu.getMenuItems().get(menuInput - 1);
                System.out.println("선택한 메뉴: " + menuItem);
                System.out.print("\n위 메뉴를 장바구니에 추가하시겠습니까? 1. 확인  2. 취소: ");
                int confirmation = getValidInput(2);  // 장바구니 추가 여부 확인

                if (confirmation == 1) {
                    // 확인을 선택하면 장바구니에 추가
                    cart.addItem(menuItem);
                }
            }
        }
    }

    // 주문을 처리하는 메서드
    private void handleOrders() {
        // 장바구니가 비어있다면 예외를 던짐
        if (cart.isEmpty()) {
            throw new IllegalStateException("장바구니가 비어 있습니다.");
        }
        cart.displayCart();  // 장바구니 내용 출력

        System.out.print("\n1. 주문      2. 메뉴판: ");
        int orderInput = getValidInput(2);  // 주문 진행 여부 입력

        if (orderInput == 1) {
            // 주문을 진행할 경우, 할인 선택
            System.out.println("할인 정보를 입력해주세요.");
            System.out.println("1. 장애인   : 10%");
            System.out.println("2. 경로우대  :  5%");
            System.out.println("3. 학생     :  5%");
            System.out.println("4. 일반     :  0%");
            int discountInput = getValidInput(4);  // 할인 선택

            // 할인 등급 선택
            Grade grade = switch (discountInput) {
                case 1 -> Grade.DISABLED_PERSON;
                case 2 -> Grade.SENIOR_CITIZEN;
                case 3 -> Grade.STUDENT;
                default -> Grade.REGULAR;
            };

            // 총 가격 계산
            double totalPrice = cart.getCartItems().stream().mapToDouble(CartItem::getTotalPrice).sum();
            // 할인 금액 계산
            int discountAmount = discountService.applyDiscount(grade, (int) totalPrice);
            double finalPrice = (totalPrice - discountAmount) / 1000.0;

            // 결과 출력
            System.out.println("\n주문이 완료되었습니다. 금액은 W " + finalPrice + " 입니다.");
            cart.clearCart();  // 장바구니 비우기
        }
    }

    // 진행 중인 주문을 취소하는 메서드
    private void handleCancel() {
        // 장바구니가 비어있으면 취소할 내용이 없다는 예외 처리
        if (cart.isEmpty()) {
            throw new IllegalStateException("장바구니가 비어 있습니다.");
        }
        cart.clearCart();  // 장바구니 비우기
    }

    // 유효한 사용자 입력을 받는 메서드 (범위 검증)
    private int getValidInput(int size) {
        int input = -1;  // 유효한 입력을 받을 때까지 반복
        while (input < 0 || input > size) {
            try {
                input = scanner.nextInt();
                if (input < 0 || input > size) {
                    System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
                }
            } catch (Exception e) {
                System.out.println("잘못된 입력입니다. 숫자만 입력해주세요.");
                scanner.next();  // 잘못된 입력 처리
            }
        }
        return input;  // 유효한 입력 반환
    }
}