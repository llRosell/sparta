package senario2.looseCoupling;

public abstract class CarPart {

    private  boolean isInspected;

    public CarPart(boolean isInspected) {
        this.isInspected = isInspected;
    }

    public void inspect() {
        System.out.println("검수를 진행합니다.");
        this.isInspected = true;
    }
}
