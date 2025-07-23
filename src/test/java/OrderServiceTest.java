import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import school.sorokin.javacore.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private OrderRepository orderRepository;
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderService = new OrderService(orderRepository);
    }

    // ✅ Успешная обработка заказа
    @Test
    void testProcessOrderSuccess() {
        Order order = new Order(0, "Laptop", 2, 1500.0);
        when(orderRepository.saveOrder(order)).thenReturn(2);

        String result = orderService.processOrder(order);

        assertEquals("Order processed successfully", result);
        verify(orderRepository, times(1)).saveOrder(order);
    }

    // ❌ Неудачная обработка заказа (исключение)
    @Test
    void testProcessOrderFailureDueToException() {
        Order order = new Order(0, "Monitor", 1, 200.0);
        when(orderRepository.saveOrder(order)).thenThrow(new RuntimeException("Order processing failed"));

        String result;
        try {
            result = orderService.processOrder(order);
        } catch (Exception e) {
            result = e.getMessage();
        }

        assertEquals("Order processing failed", result);
    }

    // ✅ Успешное вычисление стоимости
    @Test
    void testCalculateTotalSuccess() {
        Order order = new Order(5, "Mouse", 3, 100.0);
        when(orderRepository.getOrderById(5)).thenReturn(Optional.of(order));

        double total = orderService.calculateTotal(5);

        assertEquals(300.0, total);
        verify(orderRepository, times(1)).getOrderById(5);
    }

    // ⚠ Заказ не найден
    @Test
    void testCalculateTotalOrderNotFound() {
        when(orderRepository.getOrderById(99)).thenReturn(Optional.empty());

        double total = orderService.calculateTotal(99);

        assertEquals(0.0, total);
        verify(orderRepository, times(1)).getOrderById(99);
    }

    // 🧮 Корректное вычисление с нулевым количеством
    @Test
    void testCalculateTotalZeroQuantity() {
        Order order = new Order(7, "Keyboard", 0, 100.0);
        when(orderRepository.getOrderById(7)).thenReturn(Optional.of(order));

        double total = orderService.calculateTotal(7);

        assertEquals(0.0, total);
    }

    // 🧮 Корректное вычисление с нулевой ценой
    @Test
    void testCalculateTotalZeroPrice() {
        Order order = new Order(8, "Speaker", 2, 0.0);
        when(orderRepository.getOrderById(8)).thenReturn(Optional.of(order));

        double total = orderService.calculateTotal(8);

        assertEquals(0.0, total);
    }

    // ❌ Обработка null-заказа
    @Test
    void testProcessOrderNullOrder() {
        assertThrows(IllegalArgumentException.class, () -> orderService.processOrder(null));
    }
}
