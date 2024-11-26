package kiosklv5;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Kiosk 클래스: 전체 프로그램 로직을 관리
class Kiosk {
    private List<Menu> menus = new ArrayList<>(); // 카테고리 목록
    private Scanner scanner = new Scanner(System.in); // 사용자 입력을 받기 위한 Scanner 객체

    // 생성자: 메뉴 카테고리 초기화
    public Kiosk() {
        // 메뉴 카테고리 추가
        menus.add(new Menu("Burgers"));
        menus.add(new Menu("Drinks"));
        menus.add(new Menu("Desserts"));
    }

    // 메인 메뉴 출력: 카테고리 목록을 출력
    public void printMainMenu() {
        System.out.println("[ MAIN MENU ]");
        for (int i = 0; i < menus.size(); i++) {
            System.out.println((i + 1) + ". " + menus.get(i).getName());
        }
        System.out.println("0. 종료");
    }

    // 프로그램 시작 및 메인 메뉴 처리
    public void start() {
        boolean isRunning = true; // 프로그램이 종료되지 않도록 설정

        while (isRunning) {
            // 메인 메뉴 출력
            printMainMenu();
            System.out.print("메뉴 번호를 입력하세요: ");
            int menuInput = getValidInput(menus.size()); // 유효한 입력을 받는 메서드 호출

            if (menuInput == 0) {
                // 0 입력 시 종료
                System.out.println("프로그램을 종료합니다.");
                isRunning = false; // 프로그램 종료
            } else {
                // 카테고리 메뉴 선택
                selectCategoryMenu(menuInput - 1); // 선택한 카테고리로 이동
            }
        }
        scanner.close(); // Scanner 자원 해제
    }

    // 카테고리 메뉴 선택 처리
    public void selectCategoryMenu(int categoryIndex) {
        boolean isCategoryRunning = true; // 카테고리 메뉴가 실행 중인 상태
        Menu selectedMenu = menus.get(categoryIndex); // 선택된 카테고리 메뉴

        while (isCategoryRunning) {
            // 카테고리 이름과 메뉴 항목 출력
            System.out.println("\n[ " + selectedMenu.getName() + " MENU ]");
            selectedMenu.printMenu();
            System.out.print("메뉴 번호를 입력하세요: ");
            int menuInput = getValidInput(selectedMenu.getMenuItems().size()); // 유효한 입력을 받는 메서드 호출

            if (menuInput == 0) {
                // 0 입력 시 뒤로가기 (메인 메뉴로 돌아감)
                isCategoryRunning = false;
            } else {
                // 선택한 메뉴 항목의 상세 정보 출력
                selectedMenu.printSelectedMenuInfo(menuInput);
            }
        }
    }

    // 입력값 검증 메서드: 유효한 범위 내에서 숫자를 입력 받기
    private int getValidInput(int size) {
        int input = -1; // 초기 값 설정
        while (input < 0 || input > size) {
            try {
                // 사용자 입력 받기
                input = scanner.nextInt();
                if (input < 0 || input > size) {
                    System.out.println("잘못된 입력입니다. 다시 시도해주세요.");
                }
            } catch (Exception e) {
                // 숫자가 아닌 값이 들어오면 예외 처리
                System.out.println("잘못된 입력입니다. 숫자만 입력해주세요.");
                scanner.next(); // 잘못된 입력 버퍼 비우기
            }
        }
        return input;
    }
}