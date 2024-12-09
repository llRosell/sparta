package senario1.looseCoupling;

public class Car {

    // 속성
    private Engine engine;

    // 생성자
    public Car(Engine engine) {
        this.engine = engine;
    }

    // 기능
    public void drive() {
        this.engine.run();
    }
}
