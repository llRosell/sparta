package senario1.directBinding;

public class Car {

    // 속성
    private GasEngine engine;
    private ElectricEngine electricEngine;


    // 생성자
    public  Car() {
        this.engine = new GasEngine();
        this.electricEngine = new ElectricEngine();
    }

    public void drive() {
        System.out.println("자동차가 주행됩니다.");
        this.engine.run();
    }

    public  void driveWithElectricEngine() {
        System.out.println("자동차가 주행됩니다.");
        this.electricEngine.run();
    }
}
