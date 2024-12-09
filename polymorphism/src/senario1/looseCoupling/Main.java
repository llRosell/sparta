package senario1.looseCoupling;

public class Main {

    public static void main(String[] args) {
        GasEngine gasEngine = new GasEngine();
        ElectricEngine electricEngine = new ElectricEngine();

        Car gasCar = new Car(electricEngine); // 의존성 주입!
        gasCar.drive();
    }
}
