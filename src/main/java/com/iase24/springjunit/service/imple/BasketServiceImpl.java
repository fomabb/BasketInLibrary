package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.BookInBasketDataDTO;
import com.iase24.springjunit.dto.UpdateBookQuantityInBasket;
import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.entities.Order;
import com.iase24.springjunit.entities.Product;
import com.iase24.springjunit.entities.ProductCart;
import com.iase24.springjunit.entities.ProductOrder;
import com.iase24.springjunit.entities.Status;
import com.iase24.springjunit.entities.enumerated.DeliveryReport;
import com.iase24.springjunit.repository.BasketRepository;
import com.iase24.springjunit.repository.BookBasketRepository;
import com.iase24.springjunit.repository.BookCartRepository;
import com.iase24.springjunit.repository.BookRepository;
import com.iase24.springjunit.service.BasketService;
import com.iase24.springjunit.service.BookService;
import com.iase24.springjunit.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasketServiceImpl implements BasketService {

    private final BookBasketRepository bookBasketRepository;
    private final BasketRepository basketRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final CartService cartService;
    private final BookCartRepository bookCartRepository;

    @Override
    public Cart findBasketById(Long id) {
        return basketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Cart with id: %s not found", id)));
    }

    @Override
    public List<BookInBasketDataDTO> findBooksInBasketById(Long basketId) {
        return bookRepository.findBooksByBookBasketsId(basketId)
                .stream()
                .map(book -> new BookInBasketDataDTO(book.getId(), book.getTitle(), book.getCount()))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Cart addBookInBasket(Long basketId, Long bookId) {
        Cart cart = findBasketById(basketId);
        Product product = bookService.getBookById(bookId);

        // Проверяем, есть ли уже книга в корзине
        Optional<ProductCart> existingBookBasket = bookBasketRepository.findByCartAndProduct(cart, product);
        if (existingBookBasket.isPresent()) {
            throw new IllegalArgumentException("Product is already in the cart");
        }
        if (product.getCount() > 0) {
            bookRepository.save(product);
            ProductCart productCart = new ProductCart();
            productCart.setProduct(product);
            productCart.setCart(cart);
            productCart.setQuantity(1);
            productCart.setPriceQuantity(BigDecimal.valueOf(product.getPrice()));

            bookBasketRepository.save(productCart);

            // устанавливаем общую сумму за все товары
            cart.setAllPrice(BigDecimal.valueOf(bookBasketRepository.findBooksByBasket(cart)));
            return cart;
        } else {
            throw new IllegalArgumentException("Cart count exceeded");
        }
    }

    @Transactional
    @Override
    public UpdateBookQuantityInBasket updateQuantityInBasket(
            Long basketId, Long bookId, UpdateBookQuantityInBasket updateBookQuantity
    ) {
        Cart cart = findBasketById(basketId);
        Product product = bookService.getBookById(bookId);

        // Найти существующий ProductCart для данной корзины и книги
        ProductCart productCart = bookBasketRepository.findByCartAndProduct(cart, product)
                .orElseThrow(() -> new RuntimeException("ProductCart not found"));

        // Проверить, не превышает ли новое количество доступное количество книги ии проверяем не меньше ли доступного
        if (updateBookQuantity.getQuantity() <= product.getCount() && updateBookQuantity.getQuantity() > 0) {

            // обновить количество в существующем ProductCart
            productCart.setQuantity(updateBookQuantity.getQuantity());

            // увеличиваем price в самой корзине по колличеству товара
            productCart.setPriceQuantity(BigDecimal.valueOf(productCart.getProduct().getPrice() * productCart.getQuantity()));

            bookBasketRepository.findBooksByBasket(cart);

            // устанавливаем общую сумму за все товары
            cart.setAllPrice(BigDecimal.valueOf(bookBasketRepository.findBooksByBasket(cart)));

            // сохраняем изменения в базе данных
            bookBasketRepository.save(productCart);
            return new UpdateBookQuantityInBasket(productCart.getQuantity());
        } else {
            throw new IllegalArgumentException("Product count exceeded");
        }
    }

    @Transactional
    @Override
    public void removeBookInBasket(Long basketId, Long bookId) {
        Product product = bookService.getBookById(bookId);
        Cart cart = findBasketById(basketId);
        ProductCart productCart = bookBasketRepository.findByCartAndProduct(cart, product)
                .orElseThrow(() -> new RuntimeException("ProductCart not found"));

        cart.getProductsCarts().remove(productCart);

        if (!cart.getProductsCarts().contains(productCart)) {
            basketRepository.save(cart);
            if (cart.getProductsCarts().isEmpty()) {
                cart.setAllPrice(BigDecimal.valueOf(0.0));
            } else {
                // устанавливаем общую сумму за все товары
                cart.setAllPrice(BigDecimal.valueOf(bookBasketRepository.findBooksByBasket(cart)));
            }
        } else {
            throw new IllegalArgumentException("Delete product failed");
        }
    }

    @Transactional
    @Override
    public void toDoOrdersInBasketByQuantity(Long basketId, Long bookId) {
        Cart cart = findBasketById(basketId);
        Order order = cartService.getCartById(basketId);
        Product product = bookService.getBookById(bookId);
        ProductCart productCart = bookBasketRepository.findByCartAndProduct(cart, product)
                .orElseThrow(() -> new RuntimeException("ProductCart not found")
                );

        // список всех заказов для сохранения в базу данных
        List<ProductOrder> allOrdersByQuantity = new ArrayList<>();
        for (int i = 0; i < productCart.getQuantity(); i++) {
            ProductOrder productOrder = new ProductOrder();
            product.setCount(product.getCount() - 1);
            productOrder.setProduct(product);
            productOrder.setOrder(order);
            productOrder.setCreationTime(LocalDateTime.now());
            productOrder.setDeliveryReport(DeliveryReport.HALFWAY_THROUGH);
            allOrdersByQuantity.add(productOrder);
        }
        bookCartRepository.saveAll(allOrdersByQuantity);
        if (cart.getProductsCarts().isEmpty()) {
            cart.setAllPrice(BigDecimal.valueOf(0.0));
        } else {
            // устанавливаем общую сумму за все товары
            cart.setAllPrice(BigDecimal.valueOf(bookBasketRepository.findBooksByBasket(cart)));
        }

        // проверяет, если колличество книг менше 1-го, тогда делается статус неактивным
        if (product.getCount() <= 0) {
            product.setStatus(Status.INACTIVE);
        }
        removeBookInBasket(basketId, product.getId());
    }
}
