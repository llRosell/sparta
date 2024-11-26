package kioskchallengelv1;

class CartItem {
    private MenuItem menu;
    private int quantity;

    public CartItem(MenuItem menu, int quantity) {
        this.menu = menu;
        this.quantity = quantity;
    }

    public MenuItem getMenu() {
        return menu;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return menu.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return menu.getName() + " | W " + menu.getPrice() + " | " + menu.getDescription();
    }
}