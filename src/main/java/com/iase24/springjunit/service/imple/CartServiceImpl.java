package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.BookUpdateDTO;
import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.entities.Order;
import com.iase24.springjunit.entities.Product;
import com.iase24.springjunit.entities.ProductOrder;
import com.iase24.springjunit.entities.Status;
import com.iase24.springjunit.entities.enumerated.DeliveryReport;
import com.iase24.springjunit.repository.BookCartRepository;
import com.iase24.springjunit.repository.BookRepository;
import com.iase24.springjunit.repository.CartRepository;
import com.iase24.springjunit.service.BookService;
import com.iase24.springjunit.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final BookService bookService;
    private final BookRepository bookRepository;
    private final BookCartRepository bookCartRepository;

    @Override
    @Transactional
    public Order addCart(Order order) {
        return cartRepository.save(order);
    }

    @Override
    public List<Order> getCarts() {
        List<Order> orders = cartRepository.findAll();
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
    public Order getCartById(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("Order with id " + cartId + " not found"));
    }

    @Override
    @Transactional
    public void updateBookInCart(Long bookId, BookUpdateDTO bookUpdateDTO) {
        Product product = bookService.getBookById(bookId);
        if (product.getId() != null) {
            if (product.getCount() <= 0) {
                product.setCount(bookUpdateDTO.getCount());
            }
            Product updateCount = bookRepository.save(product);
            new BookUpdateDTO(updateCount.getCount(), updateCount.getStatus());
        } else {
            throw new IllegalArgumentException("Product with id " + bookId + " not found");
        }
    }

    /**
     * Метод добавляющий книгу в картачку заказов пользователя
     */
    @Override
    @Transactional
    public Order addBookInCart(Long cartId, Long bookId) {
        Order order = getCartById(cartId);
        Product product = bookService.getBookById(bookId);
        if (product.getCount() > 0) {

            // Уменьшаем количество книги на складе
            product.setCount(product.getCount() - 1);

            // Сохраняем изменения в книге
            bookRepository.save(product);
            if (product.getCount() <= 0) {
                product.setStatus(Status.INACTIVE);
            }
            ProductOrder productOrder = new ProductOrder();
            productOrder.setProduct(product);
            productOrder.setOrder(order);
            productOrder.setCreationTime(LocalDateTime.now());
            productOrder.setDeliveryReport(DeliveryReport.HALFWAY_THROUGH);
            bookCartRepository.saveAndFlush(productOrder);
            return order;
        } else {
            throw new IllegalArgumentException("Product with id " + bookId + " not found");
        }
    }

    //TODO: необходимо изменить логику, для того, чтобы не изменялся ID и LocalDateTime
    @Override
    @Transactional
    public void removeFromCart(Long cartId, Long bookId) {
        Order order = getCartById(cartId);

        //TODO: в процессе изменения
        // сохранение изначального ID и даты====================
        Long originCartId = order.getId();
        LocalDateTime originCreationTime = order.getDateTime();
        //======================================================

        Product productToRemove = bookService.getBookById(bookId);

        // Проверяем, была ли книга в корзине до удаления
        boolean wasInCart = order.getProducts().contains(productToRemove);

        // Удаление книги из корзины
        if (order.getProducts().remove(productToRemove)) {
            order.setId(originCartId);
            order.setDateTime(originCreationTime);
            cartRepository.save(order);

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
        Product product = bookService.getBookById(bookId);
        product.setCount(product.getCount() + 1);
        if (product.getCount() > 0) {
            product.setStatus(Status.ACTIVE);
        }
        bookRepository.save(product);
    }

    @Override
    public Order getCartByLogin(String username) {
        return cartRepository.findCartByUser_Username(username)
                .orElseThrow(() -> new EntityNotFoundException("User with name: " + username + " not found"));
    }
}
