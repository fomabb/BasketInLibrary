package com.iase24.springjunit.service;

import com.iase24.springjunit.dto.ProductOrderDataDTO;
import com.iase24.springjunit.dto.UpdateDeliveryDTO;
import com.iase24.springjunit.entities.ProductOrder;

import java.util.List;

public interface ProductOrderService {
    List<ProductOrderDataDTO> findAllByOrderId(Long cartId);

    void deliveryReport(Long cartId, UpdateDeliveryDTO updateDeliveryDTO);

    List<ProductOrder> findDeliveryReportByOrderId(Long cartId);

    List<ProductOrder> findArchiveOrdersByCartId(Long cartId);
}
