package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.ProductUpdateDTO;
import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.dto.request.MakingAnOrderDataDtoRequest;
import com.iase24.springjunit.entities.Order;
import com.iase24.springjunit.entities.Product;
import com.iase24.springjunit.entities.ProductOrder;
import com.iase24.springjunit.entities.Status;
import com.iase24.springjunit.entities.enumerated.DeliveryReport;
import com.iase24.springjunit.repository.OrderRepository;
import com.iase24.springjunit.repository.ProductOrderRepository;
import com.iase24.springjunit.repository.ProductRepository;
import com.iase24.springjunit.service.OrderService;
import com.iase24.springjunit.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final ProductOrderRepository productOrderRepository;

    @Override
    @Transactional
    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .peek(cart -> {
                    UserDataDTO userDataDTO = new UserDataDTO();
                    userDataDTO.setId(cart.getUser().getId());
                    userDataDTO.setUsername(cart.getUser().getUsername());
                    userDataDTO.setEmail(cart.getUser().getEmail());
                })
                .collect(Collectors.toList());
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + orderId + " not found"));
    }

    @Override
    @Transactional
    public void updateProductInOrder(Long bookId, ProductUpdateDTO productUpdateDTO) {
        Product product = productService.getBookById(bookId);
        if (product.getId() != null) {
            if (product.getCount() <= 0) {
                product.setCount(productUpdateDTO.getCount());
            }
            Product updateCount = productRepository.save(product);
            new ProductUpdateDTO(bookId, updateCount.getCount(), updateCount.getStatus());
        } else {
            throw new EntityNotFoundException("Product with id " + bookId + " not found");
        }
    }

    /**
     * Метод добавляющий книгу в карточку заказов пользователя
     */
    @Override
    @Transactional
    public Order addProductInOrder(MakingAnOrderDataDtoRequest dataDtoRequest) {
        Order order = getOrderById(dataDtoRequest.getOrderId());
        Product product = productService.getBookById(dataDtoRequest.getProductId());
        if (product.getCount() > 0) {

            // Уменьшаем количество книги на складе
            product.setCount(product.getCount() - 1);

            // Сохраняем изменения в книге
            productRepository.save(product);
            if (product.getCount() <= 0) {
                product.setStatus(Status.INACTIVE);
            }

            ProductOrder productOrder = ProductOrder.builder()
                    .product(product)
                    .order(order)
                    .creationTime(now())
                    .deliveryReport(DeliveryReport.HALFWAY_THROUGH)
                    .build();

            productOrderRepository.saveAndFlush(productOrder);
            return order;
        } else {
            throw new EntityNotFoundException(String.format("Product with ID %s not found", dataDtoRequest.getProductId()));
        }
    }

    //TODO: необходимо изменить логику, для того, чтобы не изменялся ID и LocalDateTime
    @Override
    @Transactional
    public void removeFromOrder(Long orderId, Long bookId) {
        Order order = getOrderById(orderId);

        //TODO: в процессе изменения
        // сохранение изначального ID и даты====================
        Long originCartId = order.getId();
        LocalDateTime originCreationTime = order.getDateTime();
        //======================================================

        Product productToRemove = productService.getBookById(bookId);

        // Проверяем, была ли книга в корзине до удаления
        boolean wasInCart = order.getProducts().contains(productToRemove);

        // Удаление книги из корзины
        if (order.getProducts().remove(productToRemove)) {
            order.setId(originCartId);
            order.setDateTime(originCreationTime);
            orderRepository.save(order);

            // Проверка, остались ли еще книги в карте
            if (order.getProducts().isEmpty()) {

                // Если карта стала пустой, но книга была в ней до удаления,
                // возвращаем книгу на склад
                if (wasInCart) {
                    returnBookToStock(productToRemove.getId());
                }
            } else {

                // Если в карте еще остались книги, возвращаем книгу на склад
                returnBookToStock(productToRemove.getId());
            }
        } else {
            throw new EntityNotFoundException("Product with id " + bookId + " not found");
        }
    }

    /**
     * Метод добавляющий книгу на склад после удаления из заказов
     */
    public void returnBookToStock(Long bookId) {
        Product product = productService.getBookById(bookId);
        product.setCount(product.getCount() + 1);
        if (product.getCount() > 0) {
            product.setStatus(Status.ACTIVE);
        }
        productRepository.save(product);
    }

    @Override
    public Order getOrderByLogin(String username) {
        return orderRepository.findCartByUser_Username(username)
                .orElseThrow(() -> new EntityNotFoundException("User with name: " + username + " not found"));
    }
}
