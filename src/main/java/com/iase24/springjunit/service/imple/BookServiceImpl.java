package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.BookDataDTO;
import com.iase24.springjunit.dto.BookUpdateDTO;
import com.iase24.springjunit.entities.DescriptionCategory;
import com.iase24.springjunit.entities.Node;
import com.iase24.springjunit.entities.Product;
import com.iase24.springjunit.entities.Status;
import com.iase24.springjunit.mapper.book.BookMapper;
import com.iase24.springjunit.repository.BookRepository;
import com.iase24.springjunit.repository.CartRepository;
import com.iase24.springjunit.repository.NodeRepository;
import com.iase24.springjunit.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CartRepository cartRepository;
    private final NodeRepository nodeRepository;
    private final BookMapper bookMapper;

    @Override
    public Optional<Product> getBookByIdStatusActive(Long id, Status status) {

        if (status == Status.ACTIVE) {
            return bookRepository.findById(id);
        }
        return Optional.empty();
    }


    //TODO
    @Override
    @Transactional
    public void deleteBookFromCart(Long cartId, Long bookId) {
        cartRepository.findById(cartId);
        bookRepository.deleteById(bookId);
    }

    @Override
    public List<Product> getAll(PageRequest pageRequest) {
        return bookRepository.findAll(pageRequest).toList();
    }

    @Override
    @Transactional
    public void createNewBook(List<Product> product) {
        if (product != null) {
            bookRepository.saveAllAndFlush(product);
        }
    }

    @Override
    public Product getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " not found"));
    }

    @Override
    @Transactional
    public void updateBookCount(Long id, BookUpdateDTO bookUpdateDTO) {
        Product product = getBookById(id);
        product.setCount(bookUpdateDTO.getCount());
        if (product.getCount() > 0) {
            product.setStatus(Status.ACTIVE);
        } else if (product.getCount() == 0) {
            product.setStatus(Status.INACTIVE);
        } else {
            throw new IllegalArgumentException("IllegalAccessException");
        }
        Product updateProduct = bookRepository.save(product);
        new BookUpdateDTO(updateProduct.getCount(), updateProduct.getStatus());
    }

    //TODO
    @Override
    @Transactional
    public void updateBookCounter(Long id, int count) {
        Product product = getBookById(id);
        if (product != null) {
            if (count <= 0) {
                product.setStatus(Status.INACTIVE);
            } else {
                product.setStatus(Status.ACTIVE);
            }
            bookRepository.saveAndFlush(product);
        }
    }

    @Override
    public List<BookDataDTO> search(String text) {
        return bookRepository.search(text)
                .stream()
                .map(bookMapper::map)
                .collect(Collectors.toList());
    }


//===========================================================Tree=======================================================

    @Override
    @Transactional
    public void createNewCategory(List<Node> node) {
        nodeRepository.saveAllAndFlush(node);
    }

    @Override
    @Transactional
    public void addChildrenIdInParentId(Long childrenId, Node parentNode) {
        Node node = findNodeById(childrenId);
        node.setParent(parentNode);
    }

    @Override
    @Transactional
    public void addBookInCategory(Long bookId, Node categoryId) {
        Product product = getBookById(bookId);
        product.setNode(categoryId);
        bookRepository.saveAndFlush(product);
    }

    @Override
    @Transactional
    public void addBooksInCategoryByName(String categoryName) {
        List<Product> products = bookRepository.findBooksByCategoryName(categoryName);
        Node node = nodeRepository.findByCategory(categoryName);
        products.forEach(book -> {
            book.setNode(node);
        });
        bookRepository.saveAllAndFlush(products);
    }

    @Override
    public Node findNodeById(Long nodeId) {
        return nodeRepository.findById(nodeId)
                .orElseThrow(() -> new IllegalArgumentException("Node with id " + nodeId + "not found"));
    }

    @Override
    public List<Product> findBooksChildCategoryId(Long categoryId, boolean parent, PageRequest pageRequest) {
        if (parent) {
            return bookRepository.findBooksParentCategoryId(categoryId, pageRequest).stream()
                    .sorted(Comparator.comparing(Product::getGenre))
                    .collect(Collectors.toList());
        } else {
            return bookRepository.findBooksChildCategoryId(categoryId, pageRequest).stream()
                    .sorted(Comparator.comparing(Product::getAuthor))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<DescriptionCategory> findDescriptionCategory(Long category) {
        return bookRepository.findDescriptionCategory(category);
    }
}
