package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.ProductOrderDataDTO;
import com.iase24.springjunit.dto.UpdateDeliveryDTO;
import com.iase24.springjunit.entities.ProductOrder;
import com.iase24.springjunit.entities.Status;
import com.iase24.springjunit.entities.enumerated.DeliveryReport;
import com.iase24.springjunit.exception.EntityNotFoundException;
import com.iase24.springjunit.mapper.book_cart.BookCartMapper;
import com.iase24.springjunit.repository.ProductOrderRepository;
import com.iase24.springjunit.service.ProductOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductOrderServiceImpl implements ProductOrderService {

    private final ProductOrderRepository productOrderRepository;
    private final BookCartMapper bookCartMapper;

    @Override
    public List<ProductOrderDataDTO> findAllByOrderId(Long cartId) {
        return productOrderRepository.findAllByOrder_Id(cartId)
                .stream()
                .map(bookCartMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deliveryReport(Long cartId, UpdateDeliveryDTO updateDeliveryDTO) {

        // находим карточку заказа по ID
        ProductOrder productOrder = findByCartId(cartId);

        // устанавливаем статус от 1 до 3 в зависимости от case
        productOrder.setStatusDeliveryId(updateDeliveryDTO.getStatusDeliveryId());

        switch (productOrder.getStatusDeliveryId()) {

            // 1. Отчет о доставке на пункт пропуска
            case 1:
                productOrder.setDeliveryReport(DeliveryReport.DELIVERED);
                productOrder.setDeliveryReportDate(LocalDateTime.now());
                break;
            // 2. Еслии пользователь забрал продукт
            case 2:
                if (productOrder.getDeliveryReport() == DeliveryReport.DELIVERED) {
                    productOrder.setDeliveryReport(DeliveryReport.RECEIVING);
                    productOrder.setReportOnTheEventDate(LocalDateTime.now());
                    break;
                }
                // 3. Отмена заказа
            case 3:
                if (productOrder.getDeliveryReport() == DeliveryReport.DELIVERED) {
                    productOrder.setDeliveryReport(DeliveryReport.CANCELLED);
                    productOrder.getProduct().setCount(productOrder.getProduct().getCount() + 1);
                    productOrder.setReportOnTheEventDate(LocalDateTime.now());
                    if (productOrder.getProduct().getCount() > 0) {
                        productOrder.getProduct().setStatus(Status.ACTIVE);
                    }
                    break;
                } else {
                    throw new IllegalArgumentException("Status CANCELLED");
                }
            default:
                throw new IllegalStateException("Unexpected value: " + productOrder.getStatusDeliveryId());
        }
    }

    @Override
    public List<ProductOrder> findDeliveryReportByOrderId(Long orderId) {
        return productOrderRepository.findAllByOrder_Id(orderId)
                .stream()
                .filter(bookCart -> bookCart.getStatusDeliveryId() == null || bookCart.getStatusDeliveryId() == 1)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductOrder> findArchiveOrdersByCartId(Long orderId) {
        if (orderId != null) {
            return productOrderRepository.findAllByOrder_Id(orderId)
                    .stream()
                    .filter(bookCart -> bookCart.getDeliveryReport().equals(DeliveryReport.RECEIVING)
                            || bookCart.getDeliveryReport().equals(DeliveryReport.CANCELLED)
                    )
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Order ID is not exist");
        }
    }

    private ProductOrder findByCartId(Long cartId) {
        return productOrderRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product order with id %s not found", cartId)));
    }
}
