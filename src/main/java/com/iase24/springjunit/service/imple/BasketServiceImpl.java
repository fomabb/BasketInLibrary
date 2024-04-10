package com.iase24.springjunit.service.imple;

import com.iase24.springjunit.dto.BookInBasketDataDTO;
import com.iase24.springjunit.entities.Basket;
import com.iase24.springjunit.repository.BasketRepository;
import com.iase24.springjunit.repository.BookRepository;
import com.iase24.springjunit.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final BookRepository bookRepository;

    @Override
    public Basket findBasketById(Long id) {
        return basketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Basket with id: %s not found", id)));
    }

    @Override
    public List<BookInBasketDataDTO> findBooksInBasketById(Long basketId) {
        return bookRepository.findBooksByBookBasketsId(basketId)
                .stream()
                .map(book -> new BookInBasketDataDTO(book.getId(), book.getTitle(), book.getCount())).collect(Collectors.toList());
    }
}
