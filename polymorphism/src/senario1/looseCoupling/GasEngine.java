package senario1.looseCoupling;

public class GasEngine implements Engine {

    @Override
    public void run() {
        System.out.println("가스 엔진이 동작합니다.");
    }
}
