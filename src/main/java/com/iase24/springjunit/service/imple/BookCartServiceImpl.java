package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.BookCartDataDTO;
import com.iase24.springjunit.dto.UpdateDeliveryDTO;
import com.iase24.springjunit.entities.BookCart;
import com.iase24.springjunit.entities.Status;
import com.iase24.springjunit.entities.enumerated.DeliveryReport;
import com.iase24.springjunit.exception.EntityNotFoundException;
import com.iase24.springjunit.mapper.book_cart.BookCartMapper;
import com.iase24.springjunit.repository.BookCartRepository;
import com.iase24.springjunit.repository.BookRepository;
import com.iase24.springjunit.service.BookCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookCartServiceImpl implements BookCartService {

    private final BookCartRepository bookCartRepository;
    private final BookCartMapper bookCartMapper;
    private final BookRepository bookRepository;

    @Override
    public List<BookCartDataDTO> findAllByCartId(Long cartId) {
        return bookCartRepository.findAllByCart_Id(cartId)
                .stream()
                .map(bookCartMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deliveryReport(Long cartId, UpdateDeliveryDTO updateDeliveryDTO) {

        // находим карточку заказа по ID
        BookCart bookCart = findByCartId(cartId);

        // устанавливаем статус от 1 до 3 в зависимости от case
        bookCart.setStatusDeliveryId(updateDeliveryDTO.getStatusDeliveryId());

        switch (bookCart.getStatusDeliveryId()) {

            // 1. Отчет о доставке на пункт пропуска
            case 1:
                bookCart.setDeliveryReport(DeliveryReport.DELIVERED);
                bookCart.setDeliveryReportDate(LocalDateTime.now());
                break;
            // 2. Еслии пользователь забрал продукт
            case 2:
                if (bookCart.getDeliveryReport() == DeliveryReport.DELIVERED) {
                    bookCart.setDeliveryReport(DeliveryReport.RECEIVING);
                    bookCart.setReportOnTheEventDate(LocalDateTime.now());
                    break;
                }
                // 3. Отмена заказа
            case 3:
                if (bookCart.getDeliveryReport() == DeliveryReport.DELIVERED) {
                    bookCart.setDeliveryReport(DeliveryReport.CANCELLED);
                    bookCart.getBook().setCount(bookCart.getBook().getCount() + 1);
                    bookCart.setReportOnTheEventDate(LocalDateTime.now());
                    if (bookCart.getBook().getCount() > 0) {
                        bookCart.getBook().setStatus(Status.ACTIVE);
                    }
                    break;
                } else {
                    throw new IllegalArgumentException("Status CANCELLED");
                }
            default:
                throw new IllegalStateException("Unexpected value: " + bookCart.getStatusDeliveryId());
        }
    }

    @Override
    public List<BookCart> findDeliveryReportByCartId(Long cartId) {
        return bookCartRepository.findAllByCart_Id(cartId)
                .stream()
                .filter(bookCart -> bookCart.getStatusDeliveryId() == null || bookCart.getStatusDeliveryId() == 1)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookCart> findArchiveOrdersByCartId(Long cartId) {
        if (cartId != null) {
            return bookCartRepository.findAllByCart_Id(cartId)
                    .stream()
                    .filter(bookCart -> bookCart.getDeliveryReport().equals(DeliveryReport.RECEIVING)
                            || bookCart.getDeliveryReport().equals(DeliveryReport.CANCELLED)
                    )
                    .collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("Cart ID is not exist");
        }
    }


    public BookCart findByCartId(Long cartId) {
        return bookCartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Book cart with id %s not found", cartId)));
    }
}
