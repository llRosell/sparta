package kioskLv1;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // 메뉴판을 보여주고 선택한 메뉴 입력받기
        while (true) {
            System.out.println("[SHAKESHACK MENU]");
            System.out.println("1. ShackBurger | W 6.9 | 토마토, 양상추, 쉑소스가 토핑된 치즈버거");
            System.out.println("2. SmokeShack | W 8.9 | 베이컨, 체리 페퍼에 쉑소스가 토핑된 치즈버거");
            System.out.println("3. Cheeseburger | W 6.9 | 포테이토 번과 비프패티, 치즈가 토핑된 치즈버거");
            System.out.println("4. Hamburger | W 5.4 | 비프패티를 기반으로 야채가 들어간 기본버거");
            System.out.println("0. 종료 | 종료");
            System.out.println();
            System.out.print("메뉴를 선택하세요: ");
            int menuInput = scanner.nextInt(); // 입력받기

            // 선택한 메뉴 보여주기
            switch (menuInput) {
                case 1:
                    System.out.println("1. ShackBurger를 선택하셨습니다.");
                    break;
                case 2:
                    System.out.println("2. SmokeShack를 선택하셨습니다.");
                    break;
                case 3:
                    System.out.println("3. Cheeseburger를 선택하셨습니다.");
                    break;
                case 4:
                    System.out.println("4. Hamburger를 선택하셨습니다.");
                    break;
                case 0:
                    System.out.println("0. 프로그램을 종료합니다.");
                    scanner.close(); // 프로그램 종료 후 Scanner 닫기
                    return; // 프로그램 종료
                default:
                    System.out.println("메뉴판에 없는 번호입니다. 다시 입력하세요.");
                    break; // 루프계속
            }
        }
    }
}