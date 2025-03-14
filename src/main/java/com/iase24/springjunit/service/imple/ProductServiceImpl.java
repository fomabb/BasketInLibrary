package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.BookDataDTO;
import com.iase24.springjunit.dto.ProductUpdateDTO;
import com.iase24.springjunit.dto.request.BookToCategoryDataDtoRequest;
import com.iase24.springjunit.dto.request.ChildrenCategoryToParentDataDtoRequest;
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
    public Product getBookByIdStatusActive(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Book with ID %s not found", id)));

        if (product.getStatus().equals(Status.ACTIVE)) {
            return product;
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
    public ProductUpdateDTO updateBookCount(ProductUpdateDTO productUpdateDTO) {
        Product product = getBookById(productUpdateDTO.getId());
        product.setCount(productUpdateDTO.getCount());
        if (product.getCount() > 0) {
            product.setStatus(Status.ACTIVE);
        } else if (product.getCount() == 0) {
            product.setStatus(Status.INACTIVE);
        } else {
            throw new BusinessException("Bad request");
        }
        Product updateProduct = productRepository.save(product);
        return new ProductUpdateDTO(productUpdateDTO.getId(), updateProduct.getCount(), updateProduct.getStatus());
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
    public void addChildNodeToParent(ChildrenCategoryToParentDataDtoRequest dtoRequest) {
        Node node = findNodeById(dtoRequest.getChildrenId());
        node.setParent(dtoRequest.getParentNode());

        ChildrenCategoryToParentDataDtoRequest.builder()
                .childrenId(node.getId())
                .parentNode(dtoRequest.getParentNode().getParent())
                .build();

        nodeRepository.save(node);
    }

    @Override
    @Transactional
    public void addBookInCategory(BookToCategoryDataDtoRequest dataDtoRequest) {
        Product product = getBookById(dataDtoRequest.getBookId());
        product.setNode(dataDtoRequest.getCategoryId());

        BookToCategoryDataDtoRequest.builder()
                .bookId(product.getId())
                .categoryId(dataDtoRequest.getCategoryId().getParent())
                .build();

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
                .orElseThrow(() -> new EntityNotFoundException("Node with id " + nodeId + "not found"));
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
