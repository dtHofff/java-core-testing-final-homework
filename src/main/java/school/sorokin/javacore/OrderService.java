package school.sorokin.javacore;

public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public String processOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        return "Order processed successfully";
    }

    public double calculateTotal(final int orderId) {
        return orderRepository.getOrderById(orderId)
                .map(order -> order.getQuantity() * order.getUnitPrice())
                .orElse(0.0);
    }
}