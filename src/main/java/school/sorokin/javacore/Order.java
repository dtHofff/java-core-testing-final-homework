package school.sorokin.javacore;

public class Order {

    final int id;
    final String productName;
    final int quantity;
    final double unitPrice;

    public Order(int id, String productName, int quantity, double unitPrice) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
}
