package senario2.looseCoupling;

public class Main {

    public static void main(String[] args) {
        Brake brake = new Brake("디스크 브레이크");
        InspectService inspectService = new InspectService();
        inspectService.inspect(brake);
    }
}
