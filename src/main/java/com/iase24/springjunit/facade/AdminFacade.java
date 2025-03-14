package com.iase24.springjunit.facade;

import com.iase24.springjunit.dto.DescriptionDataDTO;
import com.iase24.springjunit.dto.FaqAnswerDTO;
import com.iase24.springjunit.dto.ProductUpdateDTO;
import com.iase24.springjunit.dto.UpdateDeliveryDTO;
import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.dto.request.BookToCategoryDataDtoRequest;
import com.iase24.springjunit.dto.request.ChildrenCategoryToParentDataDtoRequest;
import com.iase24.springjunit.entities.Faq;
import com.iase24.springjunit.entities.Node;
import com.iase24.springjunit.entities.Order;
import com.iase24.springjunit.entities.Product;
import com.iase24.springjunit.repository.ProductOrderRepository;
import com.iase24.springjunit.repository.ProductRepository;
import com.iase24.springjunit.service.AdminService;
import com.iase24.springjunit.service.OrderService;
import com.iase24.springjunit.service.ProductOrderService;
import com.iase24.springjunit.service.ProductService;
import com.iase24.springjunit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminFacade {

    private final AdminService adminService;
    private final UserService userService;
    private final OrderService orderService;
    private final ProductOrderService productOrderService;
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final ProductOrderRepository productOrderRepository;

    public FaqAnswerDTO answerForFaq(FaqAnswerDTO answer) {
        adminService.answerForFaq(answer);
        return answer;
    }

    public List<Faq> getFaqQuestionNotRead() {
        return adminService.findAllFaqIsQuestionNotRead();
    }

    public ResponseEntity<String> deleteFaq(Long categoryId, Long faqId) {
        adminService.deleteFaq(categoryId, faqId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Faq with ID " + faqId + " successfully deleted from category with ID " + categoryId);
    }

    public ResponseEntity<String> createDescriptionByCategoryName(DescriptionDataDTO descriptionCategory) {
        adminService.createDescriptionByCategoryName(descriptionCategory);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(String.format("Category with name %s successfully created", descriptionCategory.getCategoryRequestName()));
    }

//=======================================================Product===========================================================

    public List<Product> createNewBook(List<Product> product) {
        productService.createNewBook(product);
        return product;
    }

    public ProductUpdateDTO updateBookCount(ProductUpdateDTO dto) {
        return productService.updateBookCount(dto);
    }

//=======================================================User===========================================================

    public List<UserDataDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    public Order getCartByUser(String username) {
        return orderService.getOrderByLogin(username);
    }

    public Optional<UserDataDTO> getUserById(Long id) {
        return userService.getUserById(id);
    }

    public void updateUserRole(Long userId) {
        adminService.updateUserRolesByUsername(userId);
    }

//=======================================================Order===========================================================

    public List<Order> getCarts() {
        return orderService.getOrders();
    }

//=======================================================Tree===========================================================

    public List<Node> createNewCategory(List<Node> node) {
        productService.createNewCategory(node);
        return node;
    }

    public void addChildNodeToParent(ChildrenCategoryToParentDataDtoRequest request) {
        productService.addChildNodeToParent(request);
    }

    public void addBookInCategory(BookToCategoryDataDtoRequest request) {
        productService.addBookInCategory(request);
    }

    public ResponseEntity<?> addBooksInCategoryByName(String categoryName) {

        List<Product> products = productRepository.findBooksByCategoryName(categoryName);

        if (categoryName.equals(products.get(0).getGenre())) {
            productService.addBooksInCategoryByName(categoryName);
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(String.format("Books added to Category with name %s successfully", categoryName));
        } else {
            throw new IllegalArgumentException("Invalid category name");
        }
    }

//=======================================================ProductOrder=======================================================

    public ResponseEntity<?> deliveryReportController(Long cartId, UpdateDeliveryDTO updateDeliveryDTO) {
        if (cartId != null) {
            productOrderService.deliveryReport(cartId, updateDeliveryDTO);
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(String.format("Order with ID %s successfully delivered", cartId));
        }
        throw new IllegalArgumentException("Invalid cartId");
    }
}
