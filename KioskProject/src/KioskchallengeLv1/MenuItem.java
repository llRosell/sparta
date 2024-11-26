package kioskchallengelv1;

class MenuItem {
    private String name;
    private int price;
    private String description;

    public MenuItem(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name + " | W " + (price / 1000.0) + " | " + description;
    }
}