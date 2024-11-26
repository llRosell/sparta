package kioskchallengelv2;

public class Main {
    // 프로그램 실행의 진입점인 main 메서드
    public static void main(String[] args) {
        // Kiosk 객체를 생성. 이 객체는 메뉴를 출력하고 사용자의 입력을 처리하는 역할을 함.
        Kiosk kiosk = new Kiosk();

        // kiosk 객체의 start 메서드를 호출하여 프로그램을 실행
        // start 메서드에서 사용자 인터페이스가 제공되며, 메뉴 선택, 장바구니 추가, 할인 적용 등의 모든 흐름이 처리됨
        kiosk.start();
    }
}