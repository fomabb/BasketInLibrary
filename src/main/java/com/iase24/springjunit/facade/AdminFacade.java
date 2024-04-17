package com.iase24.springjunit.facade;

import com.iase24.springjunit.dto.BookUpdateDTO;
import com.iase24.springjunit.dto.DescriptionDataDTO;
import com.iase24.springjunit.dto.FaqAnswerDTO;
import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.Cart;
import com.iase24.springjunit.entities.Faq;
import com.iase24.springjunit.entities.Node;
import com.iase24.springjunit.repository.BookRepository;
import com.iase24.springjunit.service.AdminService;
import com.iase24.springjunit.service.BookService;
import com.iase24.springjunit.service.CartService;
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
    private final CartService cartService;
    private final BookService bookService;
    private final BookRepository bookRepository;

    public FaqAnswerDTO answerForFaq(Long faqId, FaqAnswerDTO answer) {
        adminService.answerForFaq(faqId, answer);
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

    public ResponseEntity<String> createDescriptionByCategoryName(String categoryName, DescriptionDataDTO descriptionCategory) {
        adminService.createDescriptionByCategoryName(categoryName, descriptionCategory);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(String.format("Category with name %s successfully created", categoryName));
    }

//=======================================================Book===========================================================

    public List<Book> createNewBook(List<Book> book) {
        bookService.createNewBook(book);
        return book;
    }

    public BookUpdateDTO updateBookCount(Long id, BookUpdateDTO bookUpdateDTO) {
        if (id != null) {
            bookService.updateBookCount(id, bookUpdateDTO);
        }
        return bookUpdateDTO;
    }

//=======================================================User===========================================================

    public List<UserDataDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    public Cart getCartByUser(String username) {
        return cartService.getCartByLogin(username);
    }

    public Optional<UserDataDTO> getUserById(Long id) {
        return userService.getUserById(id);
    }

    public void updateUserRole(Long userId) {
        adminService.updateUserRolesByUsername(userId);
    }

//=======================================================Cart===========================================================

    public List<Cart> getCarts() {
        return cartService.getCarts();
    }

//=======================================================Tree===========================================================

    public List<Node> createNewCategory(List<Node> node) {
        bookService.createNewCategory(node);
        return node;
    }

    public Node addChildrenIdInParentId(Long childrenId, Node parentNode) {
        bookService.addChildrenIdInParentId(childrenId, parentNode);
        return parentNode;
    }

    public Node addBookInCategory(Long bookId, Node categoryId) {
        bookService.addBookInCategory(bookId, categoryId);
        return categoryId;
    }

    public ResponseEntity<?> addBooksInCategoryByName(String categoryName) {

        List<Book> books = bookRepository.findBooksByCategoryName(categoryName);

        if (categoryName.equals(books.get(0).getGenre())) {
            bookService.addBooksInCategoryByName(categoryName);
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(String.format("Books added to Category with name %s successfully", categoryName));
        } else {
            throw new IllegalArgumentException("Invalid category name");
        }
    }
}
