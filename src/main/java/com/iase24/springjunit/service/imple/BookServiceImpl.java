package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.BookUpdateDTO;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.Node;
import com.iase24.springjunit.entities.Status;
import com.iase24.springjunit.repository.BookRepository;
import com.iase24.springjunit.repository.CartRepository;
import com.iase24.springjunit.repository.NodeRepository;
import com.iase24.springjunit.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CartRepository cartRepository;
    private final NodeRepository nodeRepository;

    @Override
    public Optional<Book> getBookByIdStatusActive(Long id, Status status) {

        if (status == Status.ACTIVE) {
            return bookRepository.findById(id);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> getBookByTitle(String title) {

        return bookRepository.findBookByTitle(title);
    }

    @Override
    public List<Book> getAllByGenre(String genre) {

        return bookRepository.findAllByGenre(genre);
    }

    @Override
    public List<Book> getBookByAuthor(String author) {

        return bookRepository.findAllByAuthor(author);
    }

    @Override
    public void deleteBookFromCart(Long cartId, Long bookId) {
        cartRepository.findById(cartId);
        bookRepository.deleteById(bookId);
    }

    @Override
    public List<Book> getAll() {

        return bookRepository.findAll();
    }

    @Override
    public void createNewBook(List<Book> book) {
        if (book != null) {
            bookRepository.saveAllAndFlush(book);
        }
    }

    @Override
    public Book getBookById(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            return optionalBook.get();
        } else {
            throw new IllegalArgumentException("Book with id " + id + " not found");
        }
    }

    @Override
    public void updateBookCount(Long id, BookUpdateDTO bookUpdateDTO) {
        Book book = getBookById(id);

        book.setCount(bookUpdateDTO.getCount());

        if (book.getCount() > 0) {
            book.setStatus(Status.ACTIVE);
        } else if (book.getCount() == 0) {
            book.setStatus(Status.INACTIVE);
        } else {
            throw new IllegalArgumentException("IllegalAccessException");
        }

        Book updateBook = bookRepository.save(book);
        new BookUpdateDTO(updateBook.getCount(), updateBook.getStatus());
    }

    //TODO

    @Override
    public void updateBookCounter(Long id, int count) {

        Book book = getBookById(id);

        if (book != null) {
            if (count <= 0) {
                book.setStatus(Status.INACTIVE);
            } else {
                book.setStatus(Status.ACTIVE);
            }
            bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public List<Book> search(String text) {

        return bookRepository.search(text);
    }


//===========================================================Tree=======================================================

    @Override
    public void createNewCategory(Node node) {

        nodeRepository.saveAndFlush(node);
    }

    @Override
    public void addChildrenIdInParentId(Long childrenId, Node parentNode) {

        Node node = findNodeById(childrenId);

        node.setParent(parentNode);
    }

    @Override
    public void addBookInCategory(Long bookId, Node categoryId) {

        Book book = getBookById(bookId);

        book.setNode(categoryId);

        bookRepository.saveAndFlush(book);
    }

    @Override
    public Node findNodeById(Long nodeId) {

        Optional<Node> optionalNode = nodeRepository.findById(nodeId);

        if (optionalNode.isPresent()) {
            return optionalNode.get();
        } else {
            throw new IllegalArgumentException("Node with id " + nodeId + "not found");
        }
    }

    @Override
    public List<Book> findBooksChildCategoryId(Long categoryId, boolean parent) {

        if (parent) {
            return bookRepository.findBooksParentCategoryId(categoryId);
        } else {
            return bookRepository.findBooksChildCategoryId(categoryId);
        }
    }
}
