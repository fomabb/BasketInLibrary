package com.iase24.springjunit.service;

import com.iase24.springjunit.dto.BookCartDataDTO;
import com.iase24.springjunit.dto.UpdateDeliveryDTO;
import com.iase24.springjunit.entities.Book;
import com.iase24.springjunit.entities.BookCart;

import java.util.List;

public interface BookCartService {
    List<BookCartDataDTO> findAllByCartId(Long cartId);

    void deliveryReport(Long cartId, UpdateDeliveryDTO updateDeliveryDTO);

    List<BookCart> findDeliveryReportByCartId(Long cartId);

    List<BookCart> findArchiveOrdersByCartId(Long cartId);
}
