package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.BookDataDTO;
import com.iase24.springjunit.dto.ProductUpdateDTO;
import com.iase24.springjunit.entities.DescriptionCategory;
import com.iase24.springjunit.entities.Node;
import com.iase24.springjunit.entities.Product;
import com.iase24.springjunit.entities.Status;
import com.iase24.springjunit.exceptionhandler.exceptions.BusinessException;
import com.iase24.springjunit.mapper.book.BookMapper;
import com.iase24.springjunit.repository.NodeRepository;
import com.iase24.springjunit.repository.OrderRepository;
import com.iase24.springjunit.repository.ProductRepository;
import com.iase24.springjunit.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final NodeRepository nodeRepository;
    private final BookMapper bookMapper;

    @Override
    public Product getBookByIdStatusActive(Long id, Status status) {

        if (status == Status.ACTIVE) {
            return productRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException(String.format("Book with ID %s not found", id)));
        } else {
            throw new BusinessException(String.format("Book with ID %s is not active", id));
        }
    }


    //TODO
    @Override
    @Transactional
    public void deleteBookFromCart(Long cartId, Long bookId) {
        orderRepository.findById(cartId);
        productRepository.deleteById(bookId);
    }

    @Override
    public List<Product> getAll(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).toList();
    }

    @Override
    @Transactional
    public void createNewBook(List<Product> product) {
        if (product != null) {
            productRepository.saveAllAndFlush(product);
        } else {
            throw new EntityNotFoundException("Products not found");
        }
    }

    @Override
    public Product getBookById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with id %s not found", id)));
    }

    @Override
    @Transactional
    public void updateBookCount(Long id, ProductUpdateDTO productUpdateDTO) {
        Product product = getBookById(id);
        product.setCount(productUpdateDTO.getCount());
        if (product.getCount() > 0) {
            product.setStatus(Status.ACTIVE);
        } else if (product.getCount() == 0) {
            product.setStatus(Status.INACTIVE);
        } else {
            throw new BusinessException("Bad request");
        }
        Product updateProduct = productRepository.save(product);
        new ProductUpdateDTO(updateProduct.getCount(), updateProduct.getStatus());
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
            productRepository.saveAndFlush(product);
        }
    }

    @Override
    public List<BookDataDTO> search(String text) {
        return productRepository.search(text)
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
        productRepository.saveAndFlush(product);
    }

    @Override
    @Transactional
    public void addBooksInCategoryByName(String categoryName) {
        List<Product> products = productRepository.findBooksByCategoryName(categoryName);
        Node node = nodeRepository.findByCategory(categoryName);
        products.forEach(book -> {
            book.setNode(node);
        });
        productRepository.saveAllAndFlush(products);
    }

    @Override
    public Node findNodeById(Long nodeId) {
        return nodeRepository.findById(nodeId)
                .orElseThrow(() -> new IllegalArgumentException("Node with id " + nodeId + "not found"));
    }

    @Override
    public List<Product> findBooksChildCategoryId(Long categoryId, boolean parent, PageRequest pageRequest) {
        if (parent) {
            return productRepository.findBooksParentCategoryId(categoryId, pageRequest).stream()
                    .sorted(Comparator.comparing(Product::getGenre))
                    .collect(Collectors.toList());
        } else {
            return productRepository.findBooksChildCategoryId(categoryId, pageRequest).stream()
                    .sorted(Comparator.comparing(Product::getAuthor))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<DescriptionCategory> findDescriptionCategory(Long category) {
        return productRepository.findDescriptionCategory(category);
    }
}
