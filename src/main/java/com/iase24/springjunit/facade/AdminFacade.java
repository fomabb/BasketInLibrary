package com.iase24.springjunit.facade;

import com.iase24.springjunit.dto.DescriptionDataDTO;
import com.iase24.springjunit.dto.FaqAnswerDTO;
import com.iase24.springjunit.dto.UpdateDeliveryDTO;
import com.iase24.springjunit.entities.Faq;
import com.iase24.springjunit.entities.Product;
import com.iase24.springjunit.exceptionhandler.exceptions.BusinessException;
import com.iase24.springjunit.exceptionhandler.exceptions.ValidationException;
import com.iase24.springjunit.repository.ProductRepository;
import com.iase24.springjunit.service.AdminService;
import com.iase24.springjunit.service.ProductOrderService;
import com.iase24.springjunit.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminFacade {

    private final AdminService adminService;
    private final ProductOrderService productOrderService;
    private final ProductService productService;
    private final ProductRepository productRepository;

    public FaqAnswerDTO answerForFaq(FaqAnswerDTO answer) {
        adminService.answerForFaq(answer);
        return answer;
    }

    public List<Faq> getFaqQuestionNotRead() {
        return adminService.findAllFaqIsQuestionNotRead();
    }

    public ResponseEntity<String> createDescriptionByCategoryName(DescriptionDataDTO descriptionCategory) {
        adminService.createDescriptionByCategoryName(descriptionCategory);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(String.format("Category with name %s successfully created", descriptionCategory.getCategoryRequestName()));
    }

//=======================================================Tree===========================================================

    public ResponseEntity<?> addBooksInCategoryByName(String categoryName) {

        List<Product> products = productRepository.findBooksByCategoryName(categoryName);

        if (categoryName.equals(products.get(0).getGenre())) {
            productService.addBooksInCategoryByName(categoryName);
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(String.format("Books added to Category with name %s successfully", categoryName));
        } else {
            throw new BusinessException("Invalid category name");
        }
    }

//=======================================================ProductOrder=======================================================

    public void deliveryReportController(Long cartId, UpdateDeliveryDTO updateDeliveryDTO) {
        if (cartId != null) {
            productOrderService.deliveryReport(cartId, updateDeliveryDTO);
            ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(String.format("Order with ID %s successfully delivered", cartId));
            return;
        }
        throw new ValidationException("Invalid cartId");
    }
}
