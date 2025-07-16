package school.sorokin.javacore;

import java.util.Optional;

public interface OrderRepository {

   int saveOrder(Order order);
    // сохраняет заказ и возвращает id заказа, если операция успешна

    Optional<Order> getOrderById(int id);
    // возвращает заказ по идентификатору. Если такого заказа нет, то пустой Optional
}
