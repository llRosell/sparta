package senario2.directBinding;

public class Brake {
    private String name;
    private boolean isInspected; // 검수 여부(true/false)

    public Brake(String name){
        this.name = name;
        this.isInspected = false;
    }

    public String getIsInspected() {
        return this.isInspected;
    }

    public void setInspected() (boolean isInspected) {
        this.isInspected = isInspected;
    }

    public boolean isInspected() {
        return isInspected;
    }

    public void setInspected(boolean inspected) {
        isInspected = inspected;
    }
}
