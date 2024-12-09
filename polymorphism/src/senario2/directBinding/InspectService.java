package senario2.directBinding;

public class InspectService {

    // 기능
    public inspect(Brake brake) {
        System.out.println("검수를 시작합니다.");
        System.out.println(brake.isInspected());

    }
}
