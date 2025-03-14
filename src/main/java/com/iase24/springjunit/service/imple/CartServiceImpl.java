package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.ProductInCartDataDTO;
import com.iase24.springjunit.dto.UpdateBookQuantityInBasket;
import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.entities.Order;
import com.iase24.springjunit.entities.Product;
import com.iase24.springjunit.entities.ProductCart;
import com.iase24.springjunit.entities.ProductOrder;
import com.iase24.springjunit.entities.Status;
import com.iase24.springjunit.entities.enumerated.DeliveryReport;
import com.iase24.springjunit.repository.CartRepository;
import com.iase24.springjunit.repository.ProductCartRepository;
import com.iase24.springjunit.repository.ProductOrderRepository;
import com.iase24.springjunit.repository.ProductRepository;
import com.iase24.springjunit.service.CartService;
import com.iase24.springjunit.service.OrderService;
import com.iase24.springjunit.service.ProductService;
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
public class CartServiceImpl implements CartService {

    private final ProductCartRepository productCartRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final OrderService orderService;
    private final ProductOrderRepository productOrderRepository;

    @Override
    public Cart findCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Cart with id: %s not found", id)));
    }

    @Override
    public List<ProductInCartDataDTO> findProductInCartById(Long cartId) {
        return productRepository.findBooksByProductCartsId(cartId)
                .stream()
                .map(book -> new ProductInCartDataDTO(book.getId(), book.getTitle(), book.getCount()))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Cart addProductInCart(Long basketId, Long productId) {
        Cart cart = findCartById(basketId);
        Product product = productService.getBookById(productId);

        // Проверяем, есть ли уже книга в корзине
        Optional<ProductCart> existingBookBasket = productCartRepository.findByCartAndProduct(cart, product);
        if (existingBookBasket.isPresent()) {
            throw new IllegalArgumentException("Product is already in the cart");
        }
        if (product.getCount() > 0) {
            productRepository.save(product);
            ProductCart productCart = new ProductCart();
            productCart.setProduct(product);
            productCart.setCart(cart);
            productCart.setQuantity(1);
            productCart.setPriceQuantity(BigDecimal.valueOf(product.getPrice()));

            productCartRepository.save(productCart);

            // устанавливаем общую сумму за все товары
            cart.setAllPrice(BigDecimal.valueOf(productCartRepository.findBooksByBasket(cart)));
            return cart;
        } else {
            throw new IllegalArgumentException("Cart count exceeded");
        }
    }

    @Transactional
    @Override
    public UpdateBookQuantityInBasket updateQuantityInCart(
            Long basketId, Long productId, UpdateBookQuantityInBasket updateBookQuantity
    ) {
        Cart cart = findCartById(basketId);
        Product product = productService.getBookById(productId);

        // Найти существующий ProductCart для данной корзины и книги
        ProductCart productCart = productCartRepository.findByCartAndProduct(cart, product)
                .orElseThrow(() -> new RuntimeException("ProductCart not found"));

        // Проверить, не превышает ли новое количество доступное количество книги ии проверяем не меньше ли доступного
        if (updateBookQuantity.getQuantity() <= product.getCount() && updateBookQuantity.getQuantity() > 0) {

            // обновить количество в существующем ProductCart
            productCart.setQuantity(updateBookQuantity.getQuantity());

            // увеличиваем price в самой корзине по колличеству товара
            productCart.setPriceQuantity(BigDecimal.valueOf(productCart.getProduct().getPrice() * productCart.getQuantity()));

            productCartRepository.findBooksByBasket(cart);

            // устанавливаем общую сумму за все товары
            cart.setAllPrice(BigDecimal.valueOf(productCartRepository.findBooksByBasket(cart)));

            // сохраняем изменения в базе данных
            productCartRepository.save(productCart);
            return new UpdateBookQuantityInBasket(productCart.getQuantity());
        } else {
            throw new IllegalArgumentException("Product count exceeded");
        }
    }

    @Transactional
    @Override
    public void removeProductInCart(Long cartId, Long productId) {
        Product product = productService.getBookById(productId);
        Cart cart = findCartById(cartId);
        ProductCart productCart = productCartRepository.findByCartAndProduct(cart, product)
                .orElseThrow(() -> new RuntimeException("ProductCart not found"));

        cart.getProductsCarts().remove(productCart);

        if (!cart.getProductsCarts().contains(productCart)) {
            cartRepository.save(cart);
            if (cart.getProductsCarts().isEmpty()) {
                cart.setAllPrice(BigDecimal.valueOf(0.0));
            } else {
                // устанавливаем общую сумму за все товары
                cart.setAllPrice(BigDecimal.valueOf(productCartRepository.findBooksByBasket(cart)));
            }
        } else {
            throw new IllegalArgumentException("Delete product failed");
        }
    }

    @Transactional
    @Override
    public void toDoOrdersInCartByQuantity(Long cartId, Long productId) {
        Cart cart = findCartById(cartId);
        Order order = orderService.getOrderById(cartId);
        Product product = productService.getBookById(productId);
        ProductCart productCart = productCartRepository.findByCartAndProduct(cart, product)
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
        productOrderRepository.saveAll(allOrdersByQuantity);
        if (cart.getProductsCarts().isEmpty()) {
            cart.setAllPrice(BigDecimal.valueOf(0.0));
        } else {
            // устанавливаем общую сумму за все товары
            cart.setAllPrice(BigDecimal.valueOf(productCartRepository.findBooksByBasket(cart)));
        }

        // проверяет, если колличество книг менше 1-го, тогда делается статус неактивным
        if (product.getCount() <= 0) {
            product.setStatus(Status.INACTIVE);
        }
        removeProductInCart(cartId, product.getId());
    }
}
